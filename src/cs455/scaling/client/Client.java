package cs455.scaling.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;

public class Client {

    public static byte[] generateMessage(){
        byte[] bytes = new byte[8000];
        Random random = new Random();
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)random.nextInt(128);
        }
        return bytes;
    }

    public static void main (String [] args){

        SocketChannel client;
        ByteBuffer buffer;
        String serverIP = args[0];
        int serverPort = Integer.parseInt(args[1]);
        int messageRate = Integer.parseInt(args[2]);

        try {
            client = SocketChannel.open(new InetSocketAddress(serverIP, serverPort));

            //for(int i = 0; i < 10; i++){
            while(true){

                Thread.sleep(1000/messageRate);

                buffer = ByteBuffer.wrap("Random Data".getBytes());
                client.write(buffer);
                buffer.clear();
                client.read(buffer);
                System.out.println(new String(buffer.array()).trim());
                buffer.clear();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
