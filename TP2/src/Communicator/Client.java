package Communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

// gère les interaction

public class Client {
    private String address;
    private Socket server = null;
    private DataOutputStream out;
    private DataInputStream in;

    public Client(String address, int port) throws IOException {
        server = new Socket(address,port);
        System.out.println("Connected");

        in = new DataInputStream(System.in);

        out = new DataOutputStream(server.getOutputStream());

        String line= "";

        Scanner scan = new Scanner(in);

        while(!line.equals("Fini")){
            try{
                line =  scan.nextLine();
                out.writeUTF(line);
            }
            catch(IOException e){
                System.out.println(e);
            }

        }
        in.close();
        out.close();
        server.close();
    }
}
