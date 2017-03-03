package cs455.scaling.server;

import cs455.scaling.protocol.*;

import java.util.ArrayList;

public class ThreadPoolManager extends Thread{

    TaskQueue taskQueue;
    ArrayList<WorkerThread> threads;

    public ThreadPoolManager(int threadPoolSize, TaskQueue taskQueue){
        this.taskQueue = taskQueue;
        this.threads = new ArrayList<>();
        for (int i = 0; i < threadPoolSize; i++){
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            this.threads.add(workerThread);
        }
    }

    public WorkerThread getAvailableThread(){
        for (int i = 0; i < threads.size(); i++){
            WorkerThread thread = threads.get(i);
            if (thread.isAvailable()) {
                return thread;
            }
        }
        return null;
    }

    @Override
    public void run() {
        try{
            while(true){
                if(!taskQueue.isEmpty()) {
                    Task task = taskQueue.poll();
                    WorkerThread thread;
                    while ((thread = getAvailableThread()) == null) {
                        Thread.sleep(5);
                    }
                    if (thread != null) {
                        thread.setTask(task);
                    } else {
                        System.out.println("Fatal FLAW!");
                    }
                }
            }
        }catch(Exception e){}
    }
}
