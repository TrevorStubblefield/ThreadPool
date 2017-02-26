package cs455.scaling.server;

import cs455.scaling.protocol.Task;

import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkerThread extends Thread {

    ConcurrentLinkedQueue<Task> taskQueue;

    public WorkerThread(ConcurrentLinkedQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run(){
        
    }
}
