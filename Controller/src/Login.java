import struct.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame {
	
    JPanel splitePane = new JPanel();
    Socket client;
    ObjectInputStream in;
    ObjectOutputStream out;

    public Login() throws IOException {
        this.setSize(400, 400);

        client = new Socket("192.168.43.3",2048);
        in = new ObjectInputStream(client.getInputStream());
        out = new ObjectOutputStream(client.getOutputStream());

        initPane();
        this.setTitle("登录");
        this.add(splitePane);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initPane() {
    	
    	
        JLabel name = new JLabel("账号");
        JLabel password = new JLabel("密码");

        final JTextField namet = new JTextField();
        final JTextField passwordt = new JTextField();

        JButton login = new JButton("登录");

        final JRadioButton u1 = new JRadioButton("控制员", true);
        JRadioButton u2 = new JRadioButton("管理员");
        JRadioButton u3 = new JRadioButton("裁判员");

        ButtonGroup group = new ButtonGroup();
        group.add(u1);
        group.add(u2);
        group.add(u3);

        
        
        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                String option = "controller"; // 控制人员
                if (u1.isSelected()) {
                	option = "controller";	// 控制人员
                } 
                else if(u2.isSelected()){
                	option = "admin"; // 管理员
                }
                else {
                	option = "referee";	//裁判员
                }

                String user = namet.getText();
                String pwd = passwordt.getText();
                
                
                //将输入的信息通过Socket传出去
                try {
					out.writeObject(TcpType.Login.toString());
			        out.writeObject(option);
			        out.writeObject(user);
			        out.writeObject(pwd);
			        
			        //out.close();
			        //client.close();
			        
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                try {
			        

                    TcpType head = TcpType.valueOf((String)in.readObject());
	                if(head == TcpType.LoginResult&&in.readBoolean()==true) {
	                	if (option == "controller") {
	                		Controller ctl = new Controller();
	                		hide();
	                		ctl.show();

	                		System.out.println("controller test");
	                    } 
	                    else if(option == "admin") {
	                    	//打开admin界面      目前还没有
	                		System.out.println("admin test");
	                    }
	                    else {
	                    	//打开referee界面      目前还没有
	                		System.out.println("referee test");
	                    }
	                }
	                else {
	                	System.out.println("Login in unsuccessfully");
	                }
	                
	                //in.close();
	                //client.close();
			        
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch(ClassNotFoundException cnfe) {
					System.out.println(cnfe);
				}
				/*
                user = new String();
                pwd = new String();
                */
                
                //获得来自Socket的反馈
                
 
            }

        });
        

        name.setBounds(100, 80, 100, 30);
        namet.setBounds(200, 80, 100, 30);

        password.setBounds(100, 130, 100, 30);
        passwordt.setBounds(200, 130, 100, 30);

        u1.setBounds(70, 180, 70, 30);
        u2.setBounds(170, 180, 70, 30);
        u3.setBounds(270, 180, 70, 30);

        //        group.setBounds(100, 300, 80, 30);
        login.setBounds(100, 230, 200, 30);

        splitePane.setLayout(null);

        splitePane.add(name);
        splitePane.add(namet);
        splitePane.add(password);
        splitePane.add(passwordt);
        splitePane.add(u1);
        splitePane.add(u2);
        splitePane.add(u3);
        splitePane.add(login);
    }

    
    
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
    	new Login();  	

    }

}
