package cs455.scaling.server;

import cs455.scaling.protocol.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPoolManager {

    ConcurrentLinkedQueue<Task> taskQueue;
    AtomicBoolean execute;
    List<WorkerThread> threads;

    public ThreadPoolManager(int threadPoolSize){
        this.taskQueue = new ConcurrentLinkedQueue<>();
        this.execute = new AtomicBoolean(true);
        this.threads = new ArrayList<>();
        for (int i = 0; i < threadPoolSize; i++){
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            this.threads.add(workerThread);
        }
    }
}
