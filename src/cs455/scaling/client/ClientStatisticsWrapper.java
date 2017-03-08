package cs455.scaling.client;

public class ClientStatisticsWrapper {

    int totalSent;
    int totalReceived;

    public ClientStatisticsWrapper(){
        totalSent = 0;
        totalReceived = 0;
    }

    public synchronized void incrementSent(){
        totalSent++;
    }

    public synchronized void incrementReceived(){
        totalReceived++;
    }

    public synchronized void resetMessages(){
        totalSent = 0;
        totalReceived = 0;
    }

    public synchronized void printOutput(){
        System.out.println("[" + System.nanoTime() + "] Total Sent Count: " + totalSent + ", Total Received Count: " + totalReceived);
    }
}
