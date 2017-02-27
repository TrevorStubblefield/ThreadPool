package cs455.scaling.protocol;

import cs455.scaling.server.TaskQueue;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadTask implements Task {

    ByteBuffer buffer;
    SocketChannel socketChannel;
    TaskQueue taskQueue;

    public ReadTask(ByteBuffer buffer, SocketChannel socketChannel, TaskQueue taskQueue){
        this.buffer = buffer;
        this.socketChannel = socketChannel;
        this.taskQueue = taskQueue;
    }

    @Override
    public void executeTask(){
        try {
            socketChannel.read(buffer);
            HashTask hashTask = new HashTask(buffer,socketChannel,taskQueue);
            taskQueue.add(hashTask);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
