// Coded and Designed by franol geleta



package peertopeer;

import java.util.Scanner;

public class Main {

    Scanner in = new Scanner(System.in);

    public void display() {
        int choice;

        System.out.println("Welcome to Chatz, Please Choose\n  1. Create Chat\n  2. Join Chat\n  3. Exit\n  ->");
        choice = in.nextInt();

        switch (choice) {
            case 1:
                server();
                break;
            case 2:
                client();
                break;
            case 3:
                System.exit(0);
            default:
                display();
        }
    }

    public void server() {
        int port;
        System.out.println("Please enter port number (8000-10000)");
        try {
            port = in.nextInt();
            Server serv = new Server(port);

            System.out.println("Type exit to quit");
            System.out.println("Server created on port " + port);

            new Thread(() -> {
                while (true) {
                    String msg = in.next();

                    if (msg.equalsIgnoreCase("exit")) {
                        serv.sendMsg(msg);
                        display();
                        break;
                    } else {
                        serv.sendMsg(msg);
                    }
                }
            }).start();

        } catch (NumberFormatException ex) {
            System.out.println("Invalid Input.");
            display();
        }
    }

    public void client() {
        int port;
        String address;
        try {
            System.out.println("Please enter the IP address of the Host");
            address = in.nextLine();
            System.out.println("Please enter port number (8000-10000)");
            port = in.nextInt();

            System.out.println("Client connecting on port " + port);
            Client clt = new Client(address, port);

            System.out.println("Type exit to quit");

            new Thread(() -> {
                while (true) {
                    String msg = in.next();

                    if (msg.equalsIgnoreCase("exit")) {
                        clt.sendMsg(msg);
                        display();
                        break;
                    } else {
                        clt.sendMsg(msg);
                    }
                }
            }).start();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid Input.");
            display();
        }
    }

    public static void main(String[] args) {
        new Main().display();
    }
}
