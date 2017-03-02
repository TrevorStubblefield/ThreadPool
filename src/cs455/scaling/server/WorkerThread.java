package cs455.scaling.server;

import cs455.scaling.protocol.Task;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerThread extends Thread {

    public Task task;
    public volatile boolean isAvailable;

    public WorkerThread() {
        isAvailable = true;
        task = null;
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
            while (true) {
                while (task != null) {
                    setAvailable(false);
                    task.executeTask();
                    setAvailable(true);
                    setTask(null);
                }
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
