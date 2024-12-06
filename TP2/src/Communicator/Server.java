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
        int i = 0;
        while(true){
            client = server.accept();
            Thread t = pool.get(i);
            if(!t.isAlive()){
                t.start();
            }
            MyRunable r = runners.get(i);
            if(r.needClient()){
                r.addClient(client);
            } else if (i<16) {
                i++;
                for (int y = 0; y < 16; y++){
                    MyRunable verif = runners.get(y);
                    if(verif.needClient()){
                        if(y<i){
                            i = y;
                            break;
                        }
                    }
                }
            } else {
                out = new DataOutputStream(client.getOutputStream());
                out.writeUTF("No more connection available.");
                out.close();
                client.close();
            }

        }


    }
}