package Communicator;

import java.io.FileWriter;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.Scanner;

//logique du jeu tic tac toe

public class MyRunable implements Runnable{
    private boolean isStopped = false;
    private FileWriter f = null;

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
        try{
            f = new FileWriter(Thread.currentThread().getName()+".txt");
            f.write("Hello World !");
        }
        catch (Exception e){}
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
}/*