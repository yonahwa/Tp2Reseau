package Communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


//gere les connection

public class Server {
    private Socket client = null;
    private ServerSocket server = null;

    private DataOutputStream out = null;


    public Server(int port) throws IOException {

        this.server = new ServerSocket(port);
        // threads

        ArrayList<MyRunable> runners = new ArrayList<MyRunable>();
        ArrayList<Thread> pool = new ArrayList<Thread>();

        for (int i = 0; i < 16; i++){
            runners.add(new MyRunable());
            pool.add(new Thread(runners.get(i),"Game" + Integer.toString(i)));
        }

        while(true){
            client = server.accept();
            Thread t = pool.get(0);
            if(!t.isAlive()){
                t.start();
            }
            MyRunable r = runners.get(0);
            if(r.needClient()){
                r.addClient(client);
            }
            else {
                out = new DataOutputStream(client.getOutputStream());
                out.writeUTF("No more connection available.");
                out.close();
                client.close();
            }

        }


    }
}