package cs455.scaling.protocol;

import cs455.scaling.server.TaskQueue;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WriteTask implements Task {

    SocketChannel socketChannel;
    ByteBuffer buffer;
    TaskQueue taskQueue;

    public WriteTask(ByteBuffer buffer, SocketChannel socketChannel, TaskQueue taskQueue){
        this.buffer = buffer;
        this.socketChannel = socketChannel;
        this.taskQueue = taskQueue;
    }

    @Override
    public void executeTask(){
        try {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
