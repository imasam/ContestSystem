package run;

import struct.*;

import java.util.Map;
import sql.Dao;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class SocketHandle extends Thread{
	private Socket client;
    private Dao dao;
    private Vector<String> freeList;
    private Map<String, SocketHandle> refereeList;
    public ObjectOutputStream out;
    private ObjectInputStream in;


    SocketHandle(Socket client, Dao dao, Vector<String> freeList, Map<String, SocketHandle> refereeList)
    {
    	this.client = client;
        this.dao = dao;
        this.freeList = freeList;
        this.refereeList = refereeList;

        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in =new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 接受客户端消息并进行处理
    public void run()
    {
        boolean loop = true;
        while(loop) {
            try {
                TcpType head = TcpType.valueOf((String)in.readObject());
                System.out.println();
                System.out.println(head.toString());
                switch (head) {
                    case Login:
                        login();
                        break;
                    case Score:
                        score();
                        break;
                    case SetFree:
                        setFree();
                        break;
                    case GetEvent:
                        getEvent();
                        break;
                    case GetOrderInfo:
                        getOrderInfo();
                        break;
                    case GetTeamScore:
                        getTeamScore();
                        break;
                    case ScoreInstr:
                        scoreInstr();
                        break;
                    case ScoreResult:
                        scoreResult();
                        break;
                    case GetFreeReferee:
                        getFreeList();
                        break;
                    case GetAthleteScore:
                        getAthleteScore();
                        break;
                }

            } catch (Exception e) {
                System.out.println("Exception:" + e);
                System.out.println(client.toString() + " Disconnect");
                try {
					client.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                loop = false;
                out = null;
                in = null;

                for(Map.Entry<String, SocketHandle> r : refereeList.entrySet()) {
                    if(r.getValue()==this) {
                        refereeList.remove(r.getKey());
                        freeList.remove(r.getKey());
                        break;
                    }
                }

            }
        }
    }


    // 登录
    private void login()
    {
        try {
            String option = (String)in.readObject();
            String user = (String)in.readObject();
            String pwd = (String)in.readObject();

            boolean succeed = dao.login(option,user,pwd);
            out.writeObject(TcpType.LoginResult.toString());
            out.writeBoolean(succeed);
            out.flush();

            if(succeed) {
                System.out.println(option+" "+user+" logins successfully.");
                if(option.equals("referee")) {
                    freeList.add(user);
                    refereeList.put(user, this);
                }
            }
            else {
                System.out.println(option+" "+user+" fails to login.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 控制人员发出打分指令
    private void scoreInstr()
    {
        try {
            String ctrlUser = (String)in.readObject();
            String mainRefereeUser = (String)in.readObject();
            try {
                Vector<String> refereeUser = (Vector<String>)in.readObject();
                Vector<OrderInfo> order = (Vector<OrderInfo>)in.readObject();

                System.out.println("OrderInfo size is " + order.size());

                // 发送到总裁判
                freeList.remove(mainRefereeUser);
                refereeList.get(mainRefereeUser).out.writeObject(TcpType.ScoreInstr.toString());
                refereeList.get(mainRefereeUser).out.writeObject(ctrlUser);
                refereeList.get(mainRefereeUser).out.writeObject(mainRefereeUser);
                refereeList.get(mainRefereeUser).out.writeObject(refereeUser);
                refereeList.get(mainRefereeUser).out.writeObject(order);

                // 发送到各个裁判，并从空闲列表中移除
                for(int i=0;i<refereeUser.size();i++)
                {
                    freeList.remove(refereeUser.get(i));
                    refereeList.get(refereeUser.get(i)).out.writeObject(TcpType.ScoreInstr.toString());
                    refereeList.get(refereeUser.get(i)).out.writeObject(ctrlUser);
                    refereeList.get(refereeUser.get(i)).out.writeObject(mainRefereeUser);
                    refereeList.get(refereeUser.get(i)).out.writeObject(refereeUser);
                    refereeList.get(refereeUser.get(i)).out.writeObject(order);
                }

                out.flush();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 裁判的打分分数
    private void score()
    {
        try {
            String refereeUser = (String)in.readObject();
            String mainRefereeUser = (String)in.readObject();
            ScoreResult rs = (ScoreResult)in.readObject();

            refereeList.get(mainRefereeUser).out.writeObject(TcpType.Score.toString());
            refereeList.get(mainRefereeUser).out.writeObject(refereeUser);
            refereeList.get(mainRefereeUser).out.writeObject(mainRefereeUser);
            refereeList.get(mainRefereeUser).out.writeObject(rs);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 总裁判返回的打分结果
    private void scoreResult()
    {
        try {
            String mainRefereeUser = (String)in.readObject();
            String refereeUser = (String)in.readObject();
            boolean succeed = in.readBoolean();

            refereeList.get(refereeUser).out.writeObject(TcpType.ScoreResult.toString());
            refereeList.get(refereeUser).out.writeObject(mainRefereeUser);
            refereeList.get(refereeUser).out.writeObject(refereeUser);
            refereeList.get(refereeUser).out.writeObject(succeed);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 控制人员获取空闲裁判列表
    private void getFreeList()
    {
        try {
            out.writeObject(TcpType.FreeList.toString());
            out.writeObject(freeList);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 裁判一轮比赛结束后将自己设为空闲
    private void setFree()
    {
        try {
            String user = (String)in.readObject();
            freeList.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 获取项目列表
    private void getEvent()
    {
        Vector<Event> event = dao.getEvent();
        System.out.println("Events' length is "+event.size());
        try {
            out.writeObject(TcpType.EventList.toString());
            out.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("getEvent Wrong");
        }

    }

    // 服务器根据项目编号返回的出场顺序列表
    private void getOrderInfo()
    {
        try {
            int eventNo = in.readInt();

            AgeGroup ageGroup = (AgeGroup)in.readObject();
            if(ageGroup == null)
                System.out.println("AgeGroup is null");

            Vector<OrderInfo> order = dao.getOrderInfo(eventNo, ageGroup);

            if(order == null)
                System.out.println("OrderInfo is null");

            out.writeObject(TcpType.OrderInfo.toString());
            out.writeObject(order);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 根据代表队账号获取其团体成绩
    private void getTeamScore()
    {

    }

    // 根据运动员编号获取其所有项目成绩
    private void getAthleteScore()
    {

    }
}
