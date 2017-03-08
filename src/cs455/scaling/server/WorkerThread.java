package cs455.scaling.server;

import cs455.scaling.protocol.Task;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerThread extends Thread {

    public Task task;
    public volatile boolean isAvailable;
    TaskQueue queue;

    public WorkerThread(TaskQueue queue) {
        isAvailable = true;
        task = null;
        this.queue = queue;
    }

    public synchronized void setTask(Task task){
        try {
            this.task = task;
            if (task == null) {
                wait();
            } else {
                notify();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isAvailable(){return isAvailable;}

    public synchronized void setAvailable(boolean b){
        isAvailable = b;
    }

    @Override
    public void run() {
        try {
            while(true){
                task = queue.poll();
                if(task != null){
                    task.executeTask();
                    task = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
