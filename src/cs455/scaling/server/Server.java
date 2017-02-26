package cs455.scaling.server;

import java.net.ServerSocket;

public class Server {

    public static void main (String [] args){

        int port = Integer.parseInt(args[0]);
        int threadPoolSize = Integer.parseInt(args[1]);

        ThreadPoolManager threadPoolManager = new ThreadPoolManager(threadPoolSize);

        //TODO: use java.nio.channels.Selector class to register and deregister incoming SelectionKeys.
        try {
            ServerSocket serverSocket = new ServerSocket(port);
        }
        catch(Exception e){
            System.out.println("Failure starting Server.ServerSocket on port " + port);
            System.out.println(e.getMessage());
        }

    }
}
