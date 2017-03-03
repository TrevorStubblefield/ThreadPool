package cs455.scaling.protocol;

import cs455.scaling.server.TaskQueue;

import java.nio.ByteBuffer;
import java.nio.channels.*;

public class WriteTask implements Task {

    SelectionKey key;
    SocketChannel socketChannel;
    ByteBuffer buffer = ByteBuffer.allocate(8000);
    TaskQueue taskQueue;
    byte[] message;

    public WriteTask(SelectionKey key, byte[] message, TaskQueue taskQueue){
        this.key = key;
        this.message = message;
        this.taskQueue = taskQueue;
        this.socketChannel = (SocketChannel) key.channel();
    }

    @Override
    public void executeTask(){
        try {
                key.interestOps(SelectionKey.OP_READ);
                buffer = ByteBuffer.wrap(message);
                socketChannel.write(buffer);
                buffer.clear();
        }
        catch(Exception e){
            try {
                socketChannel.close();
            }
            catch(Exception ex){}
        }
    }
}
