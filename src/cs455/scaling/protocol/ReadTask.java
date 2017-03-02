package cs455.scaling.protocol;

import cs455.scaling.server.TaskQueue;

import java.nio.ByteBuffer;
import java.nio.channels.*;

public class ReadTask implements Task {

    ByteBuffer buffer = ByteBuffer.allocate(11);
    SelectionKey key;
    SocketChannel socketChannel;
    TaskQueue taskQueue;

    public ReadTask(SelectionKey key, TaskQueue taskQueue){
        this.key = key;
        this.taskQueue = taskQueue;
        this.socketChannel = (SocketChannel) key.channel();
    }

    @Override
    public void executeTask(){
        try {
            if( socketChannel.read(buffer) != 0) {
                byte[] message = buffer.array();
                System.out.println(new String(message));
                WriteTask writeTask = new WriteTask(key, message, taskQueue);
                taskQueue.add(writeTask);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
