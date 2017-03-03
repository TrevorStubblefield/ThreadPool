package cs455.scaling.protocol;

import cs455.scaling.server.TaskQueue;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.MessageDigest;

public class ReadTask implements Task {

    ByteBuffer buffer = ByteBuffer.allocate(8000);
    SelectionKey key;
    SocketChannel socketChannel;
    TaskQueue taskQueue;

    public String SHA1FromBytes(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] hash = digest.digest(data);
            BigInteger hashInt = new BigInteger(1, hash);
            return hashInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ReadTask(SelectionKey key, TaskQueue taskQueue){
        this.key = key;
        this.taskQueue = taskQueue;
        this.socketChannel = (SocketChannel) key.channel();
    }

    @Override
    public void executeTask(){
        try {
            socketChannel.read(buffer);
            byte[] messageHash = SHA1FromBytes(buffer.array()).getBytes();
            WriteTask task = new WriteTask(key,messageHash,taskQueue);
            taskQueue.add(task);
            key.interestOps(SelectionKey.OP_READ);
        }
        catch(Exception e){}

    }
}
