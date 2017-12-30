// 控制人员
import struct.*;
import struct.Event;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class Controller extends JFrame {
    final int  MAXSIZE = 100;

    JPanel splitePane = new JPanel();
    Event[] event = new Event[MAXSIZE];
    String proj;
    String mainRefereeUser;
    String[] refereeUser = new String[5];
    Referee[] freeRefereeList;
    Socket client;
    ObjectOutputStream out;
    ObjectInputStream in;


    public Controller() {
        try {
            client = new Socket("192.168.137.1", 2048);

            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
            this.setSize(700, 700);
            //getEvent();
            getFree();
            /*initPane();
            this.add(splitePane);
            this.setTitle("小组控制人员");
            this.setResizable(true);
            this.setVisible(true);*/
            while(true);

    }

    public void getEvent(){
        // 发
        try {
            out.writeObject(TcpType.GetEvent.toString());
            out.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            String msg = (String)in.readObject();
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*
        // 收
        try {
            while(true) {
                TcpType head = TcpType.valueOf((String)in.readObject());

                if(in.readObject() == "EventList" && in.readBoolean()) {
                    freeRefereeList = (Referee[])in.readObject();   // 获取空闲列表
                }
            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        }*/
    }

    public void getFree(){
        // 发
        try {
            out.writeObject(TcpType.GetFreeReferee.toString());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            TcpType head = TcpType.valueOf((String)in.readObject());
            Vector<String> freelist = null;
            if(head == TcpType.FreeList)
                freelist = (Vector<String>) in.readObject();
            System.out.println(freelist);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*
        // 收
        try {
            TcpType head = TcpType.valueOf((String)in.readObject());

            if(in.readObject() == "FreeList" && in.readBoolean()) {
                freeRefereeList = (Referee[])in.readObject();   // 获取空闲列表

            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        }*/
    }

    public void initPane() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(500,500,700,500);

        JLabel label_project = new JLabel("选择项目");
        label_project.setBounds(110,10,150,30);

        JLabel label_age = new JLabel("选择年龄组");
        label_age.setBounds(110,210,150,30);

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

        JLabel label_chiefReferee = new JLabel("选择主裁判");
        label_chiefReferee.setBounds(500,170,150,30);

        JComboBox comboBox_project = new JComboBox();
        comboBox_project.addItem("----请选择----");
        comboBox_project.addItem("项目1");
        comboBox_project.addItem("项目2");
        comboBox_project.addItem("项目3");
        comboBox_project.setBounds(50,50,200,30);

        JComboBox comboBox_age = new JComboBox();
        comboBox_age.addItem("----请选择----");
        comboBox_age.addItem("年龄组1");
        comboBox_age.addItem("年龄组2");
        comboBox_age.addItem("年龄组3");
        comboBox_age.setBounds(100,250,100,30);

        JComboBox comboBox_referee1 = new JComboBox();
        comboBox_referee1.addItem("----请选择----");
       /* for(int i = 0;i < freeRefereeList.length;i++)
            comboBox_referee1.addItem(freeRefereeList[i].getName());*/
            /*
        comboBox_referee1.addItem("裁判1");
        comboBox_referee1.addItem("裁判2");
        comboBox_referee1.addItem("裁判3");
        */
        comboBox_referee1.setBounds(300,50,100,30);

        JComboBox comboBox_referee2 = new JComboBox();
        comboBox_referee2.addItem("----请选择----");
        comboBox_referee2.addItem("裁判1");
        comboBox_referee2.addItem("裁判2");
        comboBox_referee2.addItem("裁判3");
        comboBox_referee2.setBounds(300,125,100,30);

        JComboBox comboBox_referee3 = new JComboBox();
        comboBox_referee3.addItem("----请选择----");
        comboBox_referee3.addItem("裁判1");
        comboBox_referee3.addItem("裁判2");
        comboBox_referee3.addItem("裁判3");
        comboBox_referee3.setBounds(300,200,100,30);

        JComboBox comboBox_referee4 = new JComboBox();
        comboBox_referee4.addItem("----请选择----");
        comboBox_referee4.addItem("裁判1");
        comboBox_referee4.addItem("裁判2");
        comboBox_referee4.addItem("裁判3");
        comboBox_referee4.setBounds(300,275,100,30);

        JComboBox comboBox_referee5 = new JComboBox();
        comboBox_referee5.addItem("----请选择----");
        comboBox_referee5.addItem("裁判1");
        comboBox_referee5.addItem("裁判2");
        comboBox_referee5.addItem("裁判3");
        comboBox_referee5.setBounds(300,350,100,30);

        JComboBox comboBox_chiefReferee = new JComboBox();
        comboBox_chiefReferee.addItem("----请选择----");
        comboBox_chiefReferee.addItem("主裁判1");
        comboBox_chiefReferee.addItem("主裁判2");
        comboBox_chiefReferee.addItem("主裁判3");
        comboBox_chiefReferee.setBounds(500,200,150,30);

        JButton ensure = new JButton("确定");
        ensure.setBounds(500,350,100,30);

        comboBox_project.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取项目编号
                try {
                    TcpType head = TcpType.valueOf((String)in.readObject());

                   if(in.readObject() == "EventList" && in.readBoolean()) {
                       String[] projList = (String[])in.readObject();

                       for(int i = 0;i < projList.length;i++)
                           comboBox_project.addItem(projList[i]);

                   }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }catch(ClassNotFoundException cnfe) {
                    System.out.println(cnfe);
                }

                proj = comboBox_project.getSelectedItem().toString();
                System.out.println("fuck u bitch");
            }
        });

        comboBox_age.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取年龄组编号
                System.out.println("fuck u bitch");
            }
        });

        // 选择裁判
        comboBox_referee1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判1
                try {
                    TcpType head = TcpType.valueOf((String)in.readObject());
                    if(in.readObject() == /*"FreeList"*/"GetFreeReferee" && in.readBoolean()) {
                        String[] projList = (String[])in.readObject();

                        for(int i = 0;i < projList.length;i++)
                            comboBox_referee1.addItem(projList[i]);
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }catch(ClassNotFoundException cnfe) {
                    System.out.println(cnfe);
                }

                refereeUser[0] = comboBox_referee1.getSelectedItem().toString();
                System.out.println("Referee1 is " + refereeUser[0]);
            }
        });

        comboBox_referee2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判2
                refereeUser[1] = comboBox_referee2.getSelectedItem().toString();
                System.out.println("Referee2 is " + refereeUser[1]);
            }
        });

        comboBox_referee3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判3
                refereeUser[2] = comboBox_referee3.getSelectedItem().toString();
                System.out.println("Referee3 is " + refereeUser[2]);
            }
        });

        comboBox_referee4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判4
                refereeUser[3] = comboBox_referee4.getSelectedItem().toString();
                System.out.println("Referee4 is " + refereeUser[3]);
            }
        });

        comboBox_referee5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取裁判5
                refereeUser[4] = comboBox_referee5.getSelectedItem().toString();
                System.out.println("Referee5 is " + refereeUser[4]);
            }
        });

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
                // 提交项目编号、年龄组到裁判处
                try {
                    out.writeObject(TcpType.ScoreInstr.toString());
                    out.writeObject(mainRefereeUser);
                    out.writeObject(refereeUser);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println("fuck u bitch");
            }
        });




        splitePane.setLayout(null);

        splitePane.add(label_project);
        splitePane.add(label_age);

        splitePane.add(label_referee1);
        splitePane.add(label_referee2);
        splitePane.add(label_referee3);
        splitePane.add(label_referee4);
        splitePane.add(label_referee5);
        splitePane.add(label_chiefReferee);
        
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

    public static void main(String[] args) {
        new Controller();
    }
}
