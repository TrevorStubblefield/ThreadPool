package cs455.scaling.server;

import cs455.scaling.protocol.Task;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerThread extends Thread {

    Task task;
    boolean isAvailable;

    public WorkerThread() {
        isAvailable = true;
        task = null;
    }

    public void setTask(Task task){
        this.task = task;
    }

    public Task getTask(){return task;}

    public boolean isAvailable(){return isAvailable;}


    @Override
    public void run() {
        try {
            while (true) {
                while (task != null) {
                    isAvailable = false;
                    task.executeTask();
                    isAvailable = true;
                    task = null;
                }
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
