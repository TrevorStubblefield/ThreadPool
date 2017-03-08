package cs455.scaling.server;

import cs455.scaling.protocol.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


public class Server {

    public static ServerStatisticsWrapper serverStatisticsWrapper = new ServerStatisticsWrapper();

    public static void main (String [] args){

        Timer timer = new Timer();
        timer.schedule(new ServerMessageOutput(), 0, 5000);

        int a = 0;
        TaskQueue taskQueue = new TaskQueue();

        int port = Integer.parseInt(args[0]);
        int threadPoolSize = Integer.parseInt(args[1]);

        new ThreadPoolManager(threadPoolSize,taskQueue).start();

        try {

            Selector selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), port));
            //System.out.println(InetAddress.getLocalHost().getHostAddress());
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true){
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();

                    if(key.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        serverStatisticsWrapper.incrementConnections();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }
                    if(key.isReadable()){
                        ReadTask task = new ReadTask(key,taskQueue);
                        taskQueue.add(task);
                        key.interestOps(SelectionKey.OP_WRITE);

                    }

                    iterator.remove();
                    selector.selectedKeys().remove(key);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

class ServerMessageOutput extends TimerTask {

    @Override
    public void run(){
        Server.serverStatisticsWrapper.printOutput();
        Server.serverStatisticsWrapper.resetMessages();
    }
}