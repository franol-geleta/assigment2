// Coded and Designed by franol geleta



package peertopeer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private int port;
    private String address;
    
    private Socket conn;
    private DataInputStream in;
    private DataOutputStream out;
    
    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        connectToHost();
    }
    
    private void connectToHost() {
        new Thread(() -> {
            while(true) {
                try {
                    conn = new Socket(address, port);
                    
                    in = new DataInputStream(conn.getInputStream());
                    out = new DataOutputStream(conn.getOutputStream());
                    
                    System.out.println("Connected to Server");
                    
                    // read for incoming messages constantly
                    getMsgs();
                    
                    break;
                } catch(IOException ex) {
                    ex.printStackTrace();
                    connectToHost();
                }
            }
        }).start();
    }
    
    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
            System.out.println("Client: " + msg);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void getMsgs() {
        new Thread(() -> {
            while(true) {
                try {
                    String msg = in.readUTF();
                    
                    if(msg.equalsIgnoreCase("exit")) {
                        conn.close();
                        break;
                    } else {
                        System.out.println("Server: " + msg);
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
