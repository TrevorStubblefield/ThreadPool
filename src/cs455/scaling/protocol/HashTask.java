package cs455.scaling.protocol;

import cs455.scaling.server.TaskQueue;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;

public class HashTask implements Task {

    ByteBuffer buffer;
    SocketChannel socketChannel;
    TaskQueue taskQueue;

    public HashTask(ByteBuffer buffer, SocketChannel socketChannel, TaskQueue taskQueue){
        this.buffer = buffer;
        this.socketChannel = socketChannel;
        this.taskQueue = taskQueue;
    }

    public String SHA1FromBytes(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] hash = digest.digest(data);
            BigInteger hashInt = new BigInteger(1, hash);
            return hashInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void executeTask(){



        WriteTask
    }
}
