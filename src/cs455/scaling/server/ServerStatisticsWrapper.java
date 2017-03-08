package cs455.scaling.server;

public class ServerStatisticsWrapper {

    int sentMessages;
    int connections;

    public ServerStatisticsWrapper(){
        sentMessages = 0;
        connections = 0;
    }

    public synchronized void incrementSent(){
        sentMessages++;
    }

    public synchronized void incrementConnections(){
        connections++;
    }

    public synchronized void resetMessages(){
        sentMessages = 0;
    }

    public synchronized void printOutput(){
        System.out.println("["+System.nanoTime()+"] Current Server Throughput: " + (double)sentMessages/5 + " messages/s, Active Client Connections: "  + connections);
    }
}
