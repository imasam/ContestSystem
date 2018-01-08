// 控制人员
import struct.*;
import struct.Event;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class Controller extends JFrame {

    JPanel splitePane = new JPanel();

    //********************下拉框选中内容
    int proj;    // 项目编号
    AgeGroup selectedAge;   // 年龄组
    String mainRefereeUser; // 主裁判
    Vector<String> refereeUser = new Vector<>();   // 裁判


    //*******************发送内容
    Vector<Event> freeEventList;    // 项目列表
    AgeGroup[] age = {  new AgeGroup(7,8),
            new AgeGroup(9,10),
            new AgeGroup(11,12)};  // 年龄组
    Vector<String> freeRefereeList;    // 空闲裁判列表
    Vector<OrderInfo> sendOrderInfo;    // 出场顺序列表

    // socket
    Socket client;
    ObjectOutputStream out;
    ObjectInputStream in;

    public Controller() {
        try {
            client = new Socket("192.168.43.3", 2048);

            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
            this.setSize(700, 700);
            getEvent();
            getFree();
            //getOrder();
            initPane();
            this.add(splitePane);
            this.setTitle("小组控制人员");
            this.setResizable(true);
            this.setVisible(true);
    }

    //*************************获取项目列表
    public void getEvent(){
        // 发
        try {
            out.writeObject(TcpType.GetEvent.toString());
            out.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // 收
        try {
            TcpType head = TcpType.valueOf((String)in.readObject());
            if(head == TcpType.EventList){
                freeEventList = (Vector<Event>)in.readObject();   // 获取空闲列表
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        }
    }

    //*************************获取空闲裁判列表
    public void getFree(){
        // 发
        try {
            out.writeObject(TcpType.GetFreeReferee.toString());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // 收
        try {
            TcpType head = TcpType.valueOf((String)in.readObject());
            if(head == TcpType.FreeList) {
                freeRefereeList = (Vector<String>) in.readObject();
                System.out.println(freeRefereeList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //*************************获取出场信息
    public void getOrder(){
        // 发
        try {
            out.writeObject(TcpType.GetOrderInfo.toString());
            out.writeInt(proj);
            out.writeObject(selectedAge);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // 收
        try {
            TcpType head = TcpType.valueOf((String)in.readObject());
            if(head == TcpType.OrderInfo) {
                sendOrderInfo = (Vector<OrderInfo>) in.readObject();
                System.out.println(sendOrderInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initPane() {
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    in.close();
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });

        this.setBounds(500,500,700,500);

        JLabel label_project = new JLabel("选择项目");
        label_project.setBounds(110,10,150,30);

        JLabel label_age = new JLabel("选择年龄组");
        label_age.setBounds(110,130,150,30);

        JLabel label_chiefReferee = new JLabel("选择主裁判");
        label_chiefReferee.setBounds(100,230,150,30);

        JLabel label_referee1 = new JLabel("选择裁判1");
        label_referee1.setBounds(300,10,150,30);

        JLabel label_referee2 = new JLabel("选择裁判2");
        label_referee2.setBounds(300,90,150,30);

        JLabel label_referee3 = new JLabel("选择裁判3");
        label_referee3.setBounds(300,170,150,30);

        JLabel label_referee4 = new JLabel("选择裁判4");
        label_referee4.setBounds(300,250,150,30);

        JLabel label_referee5 = new JLabel("选择裁判5");
        label_referee5.setBounds(300,330,150,30);


        JComboBox comboBox_project = new JComboBox();
        comboBox_project.addItem("----------------请选择----------------");
        for (int i = 0;i < freeEventList.size();i++) {
            comboBox_project.addItem(freeEventList.get(i).getEventNo()
                    + ")  " + freeEventList.get(i).getEventName()
                    + "  " + freeEventList.get(i).getSex());
        }
        comboBox_project.setBounds(50,50,200,30);

        JComboBox comboBox_age = new JComboBox();
        comboBox_age.addItem("----请选择----");
        for (int i = 0;i < age.length;i++) {
            comboBox_age.addItem((i + 1) + ")  " + age[i].getAgeMin()
                    + " ~ " + age[i].getAgeMax() + "岁");
        }
        comboBox_age.setBounds(100,170,100,30);

        // 主裁判
        JComboBox comboBox_chiefReferee = new JComboBox();
        comboBox_chiefReferee.addItem("----请选择----");
        for (int i = 0;i< freeRefereeList.size();i++) {
            comboBox_chiefReferee.addItem(freeRefereeList.get(i));
        }
        comboBox_chiefReferee.setBounds(70,260,150,30);

        JComboBox comboBox_referee1 = new JComboBox();
        comboBox_referee1.addItem("----请选择----");
        for (int i = 0;i < freeRefereeList.size();i++) {
            comboBox_referee1.addItem(freeRefereeList.get(i));
        }
        comboBox_referee1.setBounds(300,50,100,30);


        JComboBox comboBox_referee2 = new JComboBox();
        comboBox_referee2.addItem("----请选择----");
        for (int i = 0;i < freeRefereeList.size();i++) {
            comboBox_referee2.addItem(freeRefereeList.get(i));
        }
        comboBox_referee2.setBounds(300,125,100,30);

        JComboBox comboBox_referee3 = new JComboBox();
        comboBox_referee3.addItem("----请选择----");
        for (int i = 0;i < freeRefereeList.size();i++) {
            comboBox_referee3.addItem(freeRefereeList.get(i));
        }
        comboBox_referee3.setBounds(300,200,100,30);

        JComboBox comboBox_referee4 = new JComboBox();
        comboBox_referee4.addItem("----请选择----");
        for (int i = 0;i < freeRefereeList.size();i++) {
            comboBox_referee4.addItem(freeRefereeList.get(i));
        }
        comboBox_referee4.setBounds(300,275,100,30);

        JComboBox comboBox_referee5 = new JComboBox();
        comboBox_referee5.addItem("----请选择----");
        for (int i = 0;i < freeRefereeList.size();i++) {
            comboBox_referee5.addItem(freeRefereeList.get(i));
        }
        comboBox_referee5.setBounds(300,350,100,30);

        JButton ensure = new JButton("打分指令");
        ensure.setBounds(500,350,100,30);

        //****************************** 选择项目
        comboBox_project.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取项目编号
                String a1 = comboBox_project.getSelectedItem().toString().substring(0,1);
                String a2 = comboBox_project.getSelectedItem().toString().substring(1,2);
                if(!a2.equals(")"))
                    proj = 10 * Integer.parseInt(a1) + Integer.parseInt(a2);
                else
                    proj = Integer.parseInt(a1);
                //System.out.println(proj);
            }
        });

        //****************************** 选择年龄组
        comboBox_age.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取年龄组编号
                int tmp = Integer.parseInt(comboBox_age.getSelectedItem().toString().substring(0,1));
                selectedAge = age[tmp - 1];
            }
        });



        //****************************** 选择裁判
        comboBox_referee1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取裁判1
                refereeUser.add(comboBox_referee1.getSelectedItem().toString());
                System.out.println("Referee1 is " + refereeUser.get(0));
            }
        });

        comboBox_referee2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判2
                refereeUser.add(comboBox_referee2.getSelectedItem().toString());
                System.out.println("Referee2 is " + refereeUser.get(1));
            }
        });

        comboBox_referee3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判3
                refereeUser.add(comboBox_referee3.getSelectedItem().toString());
                System.out.println("Referee3 is " + refereeUser.get(2));
            }
        });
        /*
        comboBox_referee4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判4
                refereeUser.add(comboBox_referee4.getSelectedItem().toString());
                System.out.println("Referee4 is " + refereeUser.get(3));
            }
        });

        comboBox_referee5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判5
                refereeUser.add(comboBox_referee5.getSelectedItem().toString());
                System.out.println("Referee5 is " + refereeUser.get(4));
            }
        });
        */

        comboBox_chiefReferee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取主裁判
                mainRefereeUser = comboBox_chiefReferee.getSelectedItem().toString();
                System.out.println("mainReferee is " + mainRefereeUser);
            }
        });

        // 确认
        ensure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getOrder();
                // 提交项目编号、年龄组到裁判处
                try {
                    out.writeObject(TcpType.ScoreInstr.toString());
                    out.writeObject("Luke,I am your father.");  // 控制人员账号
                    out.writeObject(mainRefereeUser);
                    out.writeObject(refereeUser);
                    out.writeObject(sendOrderInfo);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                refereeUser = new Vector<String>();
                sendOrderInfo = new Vector<OrderInfo>();
                System.out.println("succeed");
            }
        });


        splitePane.setLayout(null);

        splitePane.add(label_project);
        splitePane.add(label_age);

        splitePane.add(label_chiefReferee);
        splitePane.add(label_referee1);
        splitePane.add(label_referee2);
        splitePane.add(label_referee3);
        splitePane.add(label_referee4);
        splitePane.add(label_referee5);
        
        splitePane.add(comboBox_project);
        splitePane.add(comboBox_age);
        splitePane.add(comboBox_referee1);
        splitePane.add(comboBox_referee2);
        splitePane.add(comboBox_referee3);
        splitePane.add(comboBox_referee4);
        splitePane.add(comboBox_referee5);
        splitePane.add(comboBox_chiefReferee);

        splitePane.add(ensure);
    }
/*
    public static void main(String[] args) {
        new Controller();
    }*/
}
