import struct.OrderInfo;
import struct.TcpType;

import javax.swing.*;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by Henry Kao on 2018/1/5.
 */
public class MsgHandle extends Thread {
    referee r;

    MsgHandle(referee r){
        this.r = r;
    }

    public void run() {
        try {
            while (true) {
                TcpType tcpType = TcpType.valueOf((String) r.in.readObject());
                if (tcpType == tcpType.ScoreInstr) {
                    r.in.readObject();
                    String mainRefereeUser = (String) r.in.readObject();
                    Vector<String> refereeUser = (Vector<String>) r.in.readObject();
                    Vector<OrderInfo> order = (Vector<OrderInfo>) r.in.readObject();
                    r.setOrderInfo(order);
                    r.setMainRef(mainRefereeUser);
                    if(r.selfUser.equals(mainRefereeUser)) {
                        break;
                    }

                    System.out.print(mainRefereeUser);
                }
                else if(tcpType == tcpType.ScoreResult) {
                    r.in.readObject();
                    r.in.readObject();
                    Boolean re = r.in.readBoolean();
                    System.out.println(re);
                    r.updateView(re);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        //break;
    }
}