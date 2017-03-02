package cs455.scaling.server;

import cs455.scaling.protocol.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class Server {

    public static void main (String [] args){

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
            //ByteBuffer buffer = ByteBuffer.allocate(8000);

            while(true){
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();

                    if(key.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }
                    if(key.isReadable()){
                        ReadTask task = new ReadTask(key,taskQueue);
                        taskQueue.add(task);

                    }
                    iterator.remove();
                }
            }



        }
        catch(Exception e){
            e.printStackTrace();
        }



    }
}
