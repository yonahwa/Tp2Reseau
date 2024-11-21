import Communicator.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try{
            Server serv = new Server(23334);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}