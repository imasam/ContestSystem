/**
 * Created by Henry Kao on 2018/1/6.
 */
import struct.OrderInfo;
import struct.ScoreResult;
import struct.TcpType;

import javax.swing.*;
import java.io.IOException;
import java.util.Vector;

public class CMsgHandle extends Thread {
    chiefReferee r;

    CMsgHandle(chiefReferee r){
        this.r = r;
    }

    public void run() {
        try {
            while (true) {
                TcpType tcpType = TcpType.valueOf((String) r.in.readObject());
                if (tcpType == tcpType.Score) {
                    String refereeUser = (String) r.in.readObject();
                    r.in.readObject();
                    ScoreResult rs = (ScoreResult) r.in.readObject();

                    r.saveScore(refereeUser, rs);
                    //r.setReferee(refereeUser);
                    //r.setRS(rs);


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
