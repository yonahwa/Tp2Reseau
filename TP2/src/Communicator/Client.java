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
        String servline = "";

        Scanner scan = new Scanner(in);


        while(true){
            try{
                if (scan.hasNextLine()){
                    line =  scan.nextLine();
                    out.writeUTF(line);
                }
                servline = in.readUTF();
                System.out.println(servline);
            }
            catch(IOException e){
                System.out.println(e);
            }
            if (server == null){
                server.close();
            }
        }

    }
}
