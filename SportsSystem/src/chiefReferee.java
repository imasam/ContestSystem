/**
 * Created by Henry Kao on 2017/12/7.
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
import java.util.*;

public class chiefReferee extends Frame implements ActionListener , Serializable {

    String Refereet = "-";
    int Playert = 0;
    boolean toNext;
    int EventNo = 0;

    public ScoreResult rs;
    Queue<String> refereeQueue = new LinkedList<>();
    Queue<ScoreResult> rsQueue = new LinkedList<>();


    private Label RefereeL = new Label("���б��:");
    private Label RefereeT = new Label(Refereet);


    private Label PlayerL = new Label("�˶�Ա���:");
    private Label PlayerT = new Label("" + Playert);



    private Label ScoreL = new Label("����");
    private Label RewardL = new Label("������");
    private Label PunishL = new Label("�ͷ���");

    private TextField score = new TextField(10);

    private TextField RewardT = new TextField(10);
    private TextField PunishT = new TextField(10);


    private JRadioButton pass = new JRadioButton("ͨ��",true);
    private JRadioButton reset = new JRadioButton("�ش�");


    private Button send = new Button ("����");

    String selfUser;

    Socket client;
    public ObjectInputStream in;
    public ObjectOutputStream out;
    private CMsgHandle CMsgHandle;



    public chiefReferee(String user, Socket client, ObjectInputStream in, ObjectOutputStream out){
        super("�ܲ���-"+user);
        this.selfUser = user;


        setLayout(null);
        pack();
        setVisible(true);
        setSize(400, 350);

        setResizable(false);
        addWindowListener(new WindowCloser());		//���Ͻǵ�x��ť

        add(RefereeL).setBounds(40, 40, 80, 30);
        add(RefereeT).setBounds(120, 40, 180, 30);

        add(PlayerL).setBounds(40, 80, 80, 30);
        add(PlayerT).setBounds(120, 80, 180, 30);

        add(ScoreL).setBounds(20, 140, 40, 30);
        add(RewardL).setBounds(20, 190, 40, 30);
        add(PunishL).setBounds(190, 190, 40, 30);

        add(score).setBounds(70, 140, 80, 30);
        add(RewardT).setBounds(70, 190, 80, 30);
        add(PunishT).setBounds(240, 190, 80, 30);

        add(send).setBounds(140,260,120,40);

        ButtonGroup Bgroup = new ButtonGroup();
        Bgroup.add(pass);
        Bgroup.add(reset);
        add(pass).setBounds(160, 140, 100, 30);
        add(reset).setBounds(280, 140, 100, 30);		//��ӿؼ����䲼��



        send.addActionListener(this);

        this.client = client;
        this.in = in;
        this.out = out;
        System.out.println("connected!");
        toNext = true;

        CMsgHandle = new CMsgHandle(this);
        CMsgHandle.start();



    }

    public void updateView() {
        if(!toNext) return;
        this.Refereet = refereeQueue.poll();
        if(Refereet == null)
            return;

        RefereeT.setText(Refereet);

        this.rs = rsQueue.poll();
        if(Playert != rs.getAthleteNo()){
            if (RewardT.getText() == "" || PunishT.getText() == "")
                JOptionPane.showMessageDialog(null, "������ͷ��ֺͽ�����", "WARNING!", JOptionPane.WARNING_MESSAGE);
            else{
                ScoreResult DScore = new ScoreResult();
                DScore.setAthleteNo(Playert);
                DScore.setScore(Float.parseFloat(RewardT.getText().trim
                        ()));
                DScore.setScoreType("D");
                DScore.setEventNo(EventNo);
                DScore.setRefereeUser(selfUser);

                ScoreResult PScore = new ScoreResult();
                PScore.setAthleteNo(Playert);
                PScore.setScore(Float.parseFloat(RewardT.getText().trim
                        ()));
                PScore.setScoreType("P");
                PScore.setEventNo(EventNo);
                PScore.setRefereeUser(selfUser);
                try{
                    out.writeObject(TcpType.SaveScore.toString());
                    out.writeObject(DScore);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try{
                    out.writeObject(TcpType.SaveScore.toString());
                    out.writeObject(PScore);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }

        Playert = rs.getAthleteNo();
        score.setText("" + rs.getScore());
        PlayerT.setText("" + Playert);
        EventNo = rs.getEventNo();
        System.out.print("Score");
        toNext = false;
    }

    private class WindowCloser extends WindowAdapter{	//���Ͻǵ�X��ť���Թرմ���
        public void windowClosing(WindowEvent we)
        {
            try {
                CMsgHandle.stop();
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
        if(e.getSource() == send){
            Boolean succeed = false;

            if(pass.isSelected()) {
                succeed = true;  // ���ͨ��
                try {   // ������
                    out.writeObject(TcpType.SaveScore.toString());
                    out.writeObject(rs);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            else if (reset.isSelected())
                succeed = false ;

            try{
                out.writeObject(TcpType.ScoreResult.toString());
                out.writeObject(selfUser);
                out.writeObject(Refereet);
                out.writeBoolean(succeed);
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            toNext = true;
            updateView();    // ��һ��

        }
    }

    public void saveScore(String user, ScoreResult rs)
    {
        refereeQueue.offer(user);
        rsQueue.offer(rs);
        System.out.println("size "+rsQueue.size());
        updateView();
    }
}
