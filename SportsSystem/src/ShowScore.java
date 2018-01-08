// 赛事结果
import struct.*;
import struct.Event;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.Key;
import java.util.HashMap;
import java.util.ServiceConfigurationError;
import java.util.Vector;
import java.util.Map;

public class ShowScore extends JFrame {

    JPanel splitePane = new JPanel();



    //********************下拉框选中内容
    int proj;    // 项目编号
    AgeGroup selectedAge;   // 年龄组
    //********************输入内容
    int athleteNo;
    //String teamUser;


    //*******************获取内容
    Vector<Event> freeEventList;    // 项目列表
    AgeGroup[] age = {  new AgeGroup(7,8),
            new AgeGroup(9,10),
            new AgeGroup(11,12)};  // 年龄组
    Map<String,Float> scoreResults;
    //ScoreResult rs;
    //float scoreD;
    //float scoreP;
    //float score = 0;


    // socket
    Socket client;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ShowScore() {
        try {
            client = new Socket("192.168.43.3", 2048);

            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        this.setSize(700, 700);
        initPane();
        this.add(splitePane);
        this.setTitle("赛事结果");
        this.setResizable(true);
        this.setVisible(true);
    }

    //*************************获取单人成绩列表
    public void getScore() {

        // 发
        try {
            out.writeObject(TcpType.GetAthleteScore.toString());
            out.writeInt(athleteNo);
            out.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // 收
            try {
                TcpType head = TcpType.valueOf((String) in.readObject());
                if (head == TcpType.AthleteScore) {
                    scoreResults= (Map<String,Float>)in.readObject();
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ClassNotFoundException cnfe) {
                System.out.println(cnfe);
            }
        }



    public void initPane() {
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

        this.setBounds(500,500,720,500);

        JLabel solo_title = new JLabel("运动员编号");
        solo_title.setBounds(90,50,70,30);

        // 输入框
        JTextField solo = new JTextField();
        solo.setBounds(220,50,100,30);

        // 按钮
        JButton ensure_solo = new JButton("单人成绩");
        ensure_solo.setBounds(370,50,100,30);

        // 结果
        JTextArea result = new JTextArea();
        result.setBounds(0,100,700,400);
        //result.append("运动员编号" + "项目编号" + "成绩");

        /*
        solo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(solo.getText());
                athleteNo =  Integer.parseInt(solo.getText());
            }
        });*/

        // 显示成绩
        ensure_solo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                athleteNo =  Integer.parseInt(solo.getText());
                System.out.println(athleteNo);
                getScore();
                for(Map.Entry<String,Float> r1 : scoreResults.entrySet()) {
                    System.out.println(athleteNo +"                          "+ r1.getKey()
                            + "                          " + r1.getValue());
                }
                for(Map.Entry<String,Float> r : scoreResults.entrySet()) {
                    if(r.getKey() != null && r.getValue() != null) {
                        result.append(athleteNo
                                + " \t\t\t                     " + r.getKey()
                                + " \t\t\t                     " + r.getValue()
                                + "\n");
                    }
                }
                athleteNo = 0;
                Map<String,Float> scoreResults = new HashMap<>();
            }
        });


        splitePane.setLayout(null);

        splitePane.add(solo_title);
        splitePane.add(solo);
        splitePane.add(ensure_solo);


        splitePane.add(result);
    }

    public static void main(String[] args) {
        new ShowScore();
    }
}
