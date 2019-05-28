// Coded and Designed by franol geleta




package peertopeer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    
    private ServerSocket server;
    private Socket conn;
    private DataInputStream in;
    private DataOutputStream out;
    
    public Server(int port) {
        this.port = port;
        createServer();
    }
    
    private void createServer() {
        new Thread(()->{
            while(true) {
                try {
                    server = new ServerSocket(port);
                    
                    // wait till someone connects
                    new Thread(() -> {
                        while(true) {
                            try {
                                System.out.println("Waiting for client to connect...");
                                conn = server.accept();
                                
                                in = new DataInputStream(conn.getInputStream());
                                out = new DataOutputStream(conn.getOutputStream());
                                
                                System.out.println("Client Connected");
                                
                                // read for incoming messages constantly
                                getMsgs();
                                
                                break;
                            } catch(IOException ex) {
                                
                            }
                        }
                    }).start();
                    
                    break;
                } catch(IOException ex) {
                    port++;
                }
            }
        }).start();
    }
    
    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
            System.out.println("Server: " + msg);
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
                        System.out.println("Client: " + msg);
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
