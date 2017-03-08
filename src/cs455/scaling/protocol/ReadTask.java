package cs455.scaling.protocol;

import cs455.scaling.server.TaskQueue;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.MessageDigest;
import java.util.Arrays;

public class ReadTask implements Task {

    ByteBuffer buffer = ByteBuffer.allocate(8000);
    ByteBuffer incoming = ByteBuffer.allocate(8000);
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
            int totalLength = 0;
            int incomingLength;
            byte[] totalBytes;
            byte[] attachment = (byte[])key.attachment();

            incomingLength = socketChannel.read(incoming);
            byte[] incomingBytes = new byte[incomingLength];
            for(int i = 0; i < incomingLength; i++){
                incomingBytes[i] = incoming.array()[i];
            }
            totalLength+=incomingLength;


            if(attachment != null) {
                int aLen = attachment.length;
                int bLen = incomingBytes.length;
                totalBytes= new byte[aLen+bLen];
                System.arraycopy(attachment, 0, totalBytes, 0, aLen);
                System.arraycopy(incomingBytes, 0, totalBytes, aLen, bLen);
                totalLength+=attachment.length;
            }
            else{
                totalBytes = incomingBytes;
            }

            if (totalLength < 8000){
                key.attach(totalBytes);
            }
            else {
                key.attach(null);
                byte[] messageHash = SHA1FromBytes(totalBytes).getBytes();
                WriteTask task = new WriteTask(key, messageHash, taskQueue);
                taskQueue.add(task);
            }
            key.interestOps(SelectionKey.OP_READ);
        }
        catch(Exception e){}

    }
}
