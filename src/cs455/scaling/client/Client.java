package cs455.scaling.client;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.util.*;

public class Client {

    static volatile int totalSent;
    static volatile int totalReceived;

    static LinkedList<String> hashedMessages = new LinkedList<>();

    public static String SHA1FromBytes(byte[] data) {
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

    public static byte[] generateMessage(){
        byte[] bytes = new byte[8000];
        Random random = new Random();
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)random.nextInt(128);
        }
        return bytes;
    }

    public static void main (String [] args){

        totalSent = 0;
        totalReceived = 0;

        SocketChannel socketChannel;
        ByteBuffer buffer;
        String serverIP = args[0];
        int serverPort = Integer.parseInt(args[1]);
        int messageRate = Integer.parseInt(args[2]);

        try {

            socketChannel = SocketChannel.open(new InetSocketAddress(serverIP, serverPort));

            Timer timer = new Timer();
            timer.schedule(new ClientMessageOutput(), 0, 10000);

            while(true){

                byte[] sentMessage = generateMessage();
                String hashedSentMessage = SHA1FromBytes(sentMessage);
                hashedMessages.add(hashedSentMessage);

                buffer = ByteBuffer.wrap(sentMessage);
                socketChannel.write(buffer);
                totalSent++;
                buffer.clear();

                Thread.sleep(1000/messageRate);

                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try {
                            ByteBuffer buffer2 = ByteBuffer.allocate(8000);
                            int length = socketChannel.read(buffer2);
                            totalReceived++;
                            String receivedMessage = new String(Arrays.copyOfRange(buffer2.array(), 0, length));
                            if (hashedMessages.contains(receivedMessage)) {
                                hashedMessages.remove(receivedMessage);
                            } else {
                                System.out.println(receivedMessage);
                            }
                            buffer2.clear();
                        }
                        catch(Exception e){}
                    }
                }).start();


            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

class ClientMessageOutput extends TimerTask {

    @Override
    public void run(){
        System.out.println("[" + System.nanoTime() + "] Total Sent Count: " + Client.totalSent + ", Total Received Count: " + Client.totalReceived);
        Client.totalSent = 0;
        Client.totalReceived = 0;
    }
}