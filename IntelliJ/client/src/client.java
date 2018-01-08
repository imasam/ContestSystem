import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import struct.OrderInfo;


public class client {
    public static void main(String[] args)
    {
        try {
            Socket socket = new Socket("localhost", 2048);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(TcpType.Login.toString());
            out.writeObject("referee");
            out.writeObject("r2");
            out.writeObject("r2");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            try {
                while(true)
                {
                    TcpType head = TcpType.valueOf((String)in.readObject());
                    if(head == TcpType.LoginResult) {
                        boolean succeed = in.readBoolean();
                        System.out.println(succeed);
                    }
                    else if(head == TcpType.ScoreInstr) {
                        String ctrlUser = (String)in.readObject();
                        String mainRefereeUser = (String)in.readObject();
                        Vector<String> refereeUser = (Vector<String>)in.readObject();
                        Vector<OrderInfo> order = (Vector<OrderInfo>)in.readObject();
                        System.out.println(mainRefereeUser);
                        for(int i=0; i<refereeUser.size(); i++) {
                            System.out.println(refereeUser.get(i));
                        }

                        for(int i=0; i<order.size(); i++) {
                            OrderInfo o = order.get(i);
                            System.out.println(o.getAthleteNo()+" "+o.getEventNo());
                        }
                    }

                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
