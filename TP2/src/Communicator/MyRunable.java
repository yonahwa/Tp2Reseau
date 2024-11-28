package Communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Runnable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

//logique du jeu tic tac toe

public class MyRunable implements Runnable{
    private boolean isStopped = false;
    private FileWriter f = null;
    private ArrayList<Socket> clients = null;
    private boolean start = false;
    private char[][] colonne = new char [3][3];
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private boolean P1Turn = true;


    public synchronized void stop(){ //quand la game stop
        this.isStopped =true;
        try{
            f.close();
        }
        catch (Exception e){}
    }

    public synchronized void send(String message){

        if(f!=null){
            System.out.println(message);
            try{
                f.write(message);
            }
            catch(Exception e){
                System.err.println("Couldn't write message : " + message + "to file");
            }
        }
    }

    @Override
    public void run() {
        if(!needClient()) {
            //Start game
            DataOutputStream out1 = null;
            DataOutputStream out2 = null;
            DataInputStream in1 = null;
            DataInputStream in2 = null;
            Scanner scan1 = new Scanner(in1);
            Scanner scan2 = new Scanner(in2);

            if (!start) {
                start = true;
                for (int i = 0; i < 3; i++) {
                    for (int y = 0; y < 3; y++) {
                        colonne[i][y] = ' ';
                    }
                }


                try {
                    in1 = (DataInputStream) clients.get(0).getInputStream();

                    in2 = (DataInputStream) clients.get(1).getInputStream();


                    out1 = (DataOutputStream) clients.get(0).getOutputStream();


                    out2 = (DataOutputStream) clients.get(1).getOutputStream();



                    out1.writeUTF("Hi, you are Player1 you play the X. It's your turn");
                    out2.writeUTF("Hi, you are Player2 you play the O. Wait until it's your turn ");

                    P1Turn = true;

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            //Do game loop
            try {
                if (P1Turn){
                    if (scan2.hasNextLine()){
                        out2.writeUTF("Not your turn wait until i say you can play.");
                    }
                    

                }
                else {
                    if (scan1.hasNextLine()){
                        out1.writeUTF("Not your turn wait until i say you can play.");
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            //Wait for clients
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public synchronized void addClient(Socket client) {
        clients.add(client);
    }
    public synchronized boolean needClient() {
        return clients.size() < 2;
    }
}
/*import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<MyRunable> runners = new ArrayList<MyRunable>();
        ArrayList<Thread> pool = new ArrayList<Thread>();

        runners.add(new MyRunable());
        runners.add(new MyRunable());
        runners.add(new MyRunable());

        pool.add(new Thread(runners.get(0), "Gab"));
        pool.add(new Thread(runners.get(1), "Jonah"));
        pool.add(new Thread(runners.get(2), "Vincent"));

        for(int i =0; i< pool.size(); ++i){
            pool.get(i).start();
        }

        Scanner in = new Scanner(System.in);
        System.out.println("Ready !");

        while(true){
            try{
                if(in.hasNextLine()){
                    String cmd = in.next();
                    if(cmd.equals("End")){
                        String test =in.next();
                        for(int i =0; i< pool.size(); ++i){
                            if(test.equals(pool.get(i).getName()) || test.equals("All")){
                                runners.get(i).stop();
                            }

                        }
                    }
                    if(cmd.equals("Msg")){
                        String test =in.next();
                        String message = in.next();
                        for(int i =0; i< pool.size(); ++i){
                            if(test.equals(pool.get(i).getName()) || test.equals("All")){
                                runners.get(i).send(in.next(message));
                            }

                        }
                    }
                }
            }
            catch (Exception e){}
        }

    }
}*/