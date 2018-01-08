/**
 * Created by Henry Kao on 2017/12/28.
 */
import struct.*;

import javax.swing.*;
import java.awt. *;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Vector;


public class referee extends Frame implements ActionListener , Serializable{



    private Label groupL = new Label("С���");
    public Label groupT = new Label();

    private Label orderL = new Label("����˳��");
    public Label orderT = new Label();



    public int Player1t = 0;


    private Label Player1L = new Label(" �˶�Ա���: ");


    public Label Player1T = new Label(""+Player1t);


    private Label Score1L = new Label("����");


    public JTextField Score1T = new JTextField(10);


    public Button confirm = new Button("ȷ��");
    private Button reset = new Button("�ش�");

    public Vector<OrderInfo> order;
    String ctrlUser;
    public String selfUser;
    String mainRef;

    Socket client;
    public ObjectInputStream in;
    public ObjectOutputStream out;
    private MsgHandle msgHandle;

    public int i = 0;        //����


    public referee(String user, Socket client, ObjectInputStream in, ObjectOutputStream out){
        super("����-"+user);
        this.selfUser = user;
        System.out.println(selfUser);



        setLayout(null);
        pack();
        setVisible(true);
        setSize(500, 220);
        setResizable(false);
        addWindowListener(new WindowCloser());		//���Ͻǵ�x��ť



        add(groupL).setBounds(60, 60, 40, 30);
        add(groupT).setBounds(110, 60, 80, 30);

        add(orderL).setBounds(200, 60, 60, 30);
        add(orderT).setBounds(270, 60, 80, 30);



        add(Player1L).setBounds(60, 120, 80, 30);


        add(Player1T).setBounds(150, 120, 110, 30);





        add(Score1L).setBounds(280, 110, 40, 30);


        add(Score1T).setBounds(330, 110, 80, 30);


        add(confirm).setBounds(110, 165, 110, 30);		//��ӿؼ����䲼��
        add(reset).setBounds(240, 165, 110, 30);

        reset.addActionListener(this);				//���ش��¼�����
        confirm.addActionListener(this);              //��ȷ�ϡ��¼�����

        this.client = client;
        this.in = in;
        this.out = out;
        System.out.println("connected!");

        msgHandle = new MsgHandle(this);
        msgHandle.start();
    }

    public void setMainRef(String mainRefereeUser){
        this.mainRef = mainRefereeUser;
        if(mainRef.equals(selfUser)){
            hide();
            JOptionPane.showMessageDialog(null, "��Ϊ�����ܲ���", "THANKS!", JOptionPane.WARNING_MESSAGE);
            //msgHandle.stop();
            chiefReferee chiefReferee = new chiefReferee(selfUser, client, in, out);
            chiefReferee.show();

        }
    }

    public void setOrderInfo(Vector<OrderInfo> o) {
        this.order = o;
        OrderInfo oi = order.get(0);
        orderT.setText(""+oi.getOrder());
        groupT.setText(""+oi.getGroupNo());
        Player1t = oi.getAthleteNo();
        Player1T.setText("" + Player1t);
    }



    public void setOrderInfo(int i) {

        if(i<order.size()){
            OrderInfo oi = order.get(i);
            orderT.setText(""+oi.getOrder());
            groupT.setText(""+oi.getGroupNo());
            Player1t = oi.getAthleteNo();
            Player1T.setText("" + Player1t);

        }else{
            JOptionPane.showMessageDialog(null, "�����������", "THANKS!", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void updateView(boolean toNext){
        if (!toNext){
            JOptionPane.showMessageDialog(null, "���δͨ���������´��", "WARNING!", JOptionPane.WARNING_MESSAGE);
        }else if(toNext){
            i++;
            setOrderInfo(i);
        }

    }

    private class WindowCloser extends WindowAdapter{	//���Ͻǵ�X��ť���Թرմ���
        public void windowClosing(WindowEvent we)
        {
            try {
                msgHandle.stop();
                client.close();
                in.close();
                out.close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("can not close!");
                System.exit(0);
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         if(e.getSource()==confirm){						//�ӷ���ť
             System.out.println("damn");

             try {
                 ScoreResult rs = new ScoreResult();
                 rs.setAthleteNo(Player1t);
                 rs.setScore(Float.parseFloat(Score1T.getText().trim
                         ()));
                 rs.setScoreType("��ͨ");
                 rs.setEventNo(order.get(i).getEventNo());
                 rs.setRefereeUser(selfUser);

                 out.writeObject(TcpType.Score.toString());
                 out.writeObject(selfUser);
                 System.out.println(selfUser);
                 out.writeObject(mainRef);
                 out.writeObject(rs);




             } catch (IOException e1) {
                 // TODO Auto-generated catch block
                 e1.printStackTrace();
             }

         }
         else if(e.getSource()==reset){
             Score1T.setText("");


         }


    }




}


