package run;

import sql.Dao;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Server {
    private static Dao dao = new Dao();
    private static Vector<String> freeList = new Vector<>();
    private static Map<String, SocketHandle> refereeList = new HashMap<>();

    public static void main(String[] args) {
        try {
            // 创建ServerSocket进行侦听
            ServerSocket serverSocket = new ServerSocket(2048);
            System.out.println("Listen on localhost:2048...");
            while (true) {
                // 新建线程以处理客户端连接
                Socket client = serverSocket.accept();
                System.out.println("\nNew " + client.toString());
                SocketHandle handle = new SocketHandle(client, dao, freeList, refereeList);
                handle.start();
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }
}
