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
    private char[][] grille = new char [3][3];
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

    private void close(){
        //close les sockets
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
            String[] answer = new String[2];

            if (!start) {
                start = true;
                for (int i = 0; i < 3; i++) {
                    for (int y = 0; y < 3; y++) {
                        grille[i][y] = ' ';
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
                    out1.writeUTF(" |0|1|2|\n--------\n0|" + grille[0][0] + "|" + grille[0][1] + "|" + grille[0][2] + "|\n--------\n1|" + grille[1][0] + "|" + grille[1][1] + "|" + grille[1][2] + "|\n--------\n2|" + grille[2][0] + "|" + grille[2][1] + "|" + grille[2][2] + "|\n");
                    out1.writeUTF("what's your move write the answer like that: 0,0 or 2,1");
                    answer = scan1.nextLine().split(",");
                    int x = Integer.parseInt(answer[0]);
                    int y = Integer.parseInt(answer[1]);
                    grille[y][x] = 'X';

                    for(int i = 0; i < 2; i++){
                        for(int w = 0; w < 2; w++){
                            if(grille[i][w] == 'X'){
                                if(w == 2){
                                    out1.writeUTF("You Win");
                                }
                            }
                            else{
                                break;
                            }
                        }
                    }
                    for(int i = 0; i < 2; i++){
                        for(int w = 0; w < 2; w++){
                            if(grille[w][i] == 'X'){
                                if(i == 2){
                                    out.writeUTF("You Win");
                                }
                            }
                            else{
                                break;
                            }
                        }
                    }
                    for(int w = 0; w < 2; w++){
                        if(grille[w][w] == 'X'){
                            if(w == 2){
                                out.writeUTF("You Win");
                            }
                        }

                    }
                    for(int w = 2; w > 0; w--){
                        if(grille[w][w] == 'X'){
                            if(w == 2){
                                out.writeUTF("You Win");

                            }
                        }

                    }

                    P1Turn = false;
                }
                else {
                    if (scan1.hasNextLine()){
                        out1.writeUTF("Not your turn wait until i say you can play.");
                    }
                    out1.writeUTF(" |0|1|2|\n--------\n0|" + grille[0][0] + "|" + grille[0][1] + "|" + grille[0][2] + "|\n--------\n1|" + grille[1][0] + "|" + grille[1][1] + "|" + grille[1][2] + "|\n--------\n2|" + grille[2][0] + "|" + grille[2][1] + "|" + grille[2][2] + "|\n");
                    out1.writeUTF("what's your move write the answer like that: 0,0 or 2,1");
                    answer = scan1.nextLine().split(",");
                    int x = Integer.parseInt(answer[0]);
                    int y = Integer.parseInt(answer[1]);
                    grille[y][x] = 'O';

                    for(int i = 0; i < 2; i++){
                        for(int w = 0; w < 2; i++){
                            if(grille[i][w] == 'O'){
                                if(w == 2){
                                    out1.writeUTF("You Win");
                                }
                            }
                            else{
                                break;
                            }
                        }
                    }
                    for(int i = 0; i < 2; i++){
                        for(int w = 0; w < 2; i++){
                            if(grille[w][i] == 'O'){
                                if(i == 2){
                                    out.writeUTF("You Win");
                                }
                            }
                            else{
                                break;
                            }
                        }
                    }
                    for(int w = 0; w < 2; w++){
                        if(grille[w][w] == 'O'){
                            if(w == 2){
                                out1.writeUTF("You Win");
                            }
                        }

                    }
                    for(int w = 2; w > 0; w--){
                        if(grille[w][w] == 'O'){
                            if(w == 2){
                                out.writeUTF("You Win");
                            }
                        }

                    }
                    P1Turn = true;
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