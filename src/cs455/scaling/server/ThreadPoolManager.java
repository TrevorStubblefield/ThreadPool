package cs455.scaling.server;

import cs455.scaling.protocol.*;

import java.util.ArrayList;

public class ThreadPoolManager extends Thread{

    TaskQueue taskQueue;
    ArrayList<WorkerThread> threads;
    int threadPoolSize;

    public ThreadPoolManager(int threadPoolSize, TaskQueue taskQueue){
        this.taskQueue = taskQueue;
        this.threads = new ArrayList<>();
        this.threadPoolSize = threadPoolSize;
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

    public void startThreads(){
        for (int i = 0; i < threadPoolSize; i++){
            WorkerThread workerThread = new WorkerThread(taskQueue);
            workerThread.start();
            this.threads.add(workerThread);
        }
    }

//    @Override
//    public void run() {
//        try{
//            while(true){
//                if(!taskQueue.isEmpty()) {
//                    Task task = taskQueue.poll();
//                    WorkerThread thread;
//                    while ((thread = getAvailableThread()) == null) {
//                        Thread.sleep(1);
//                    }
//                    if (thread != null) {
//                        thread.setTask(task);
//                    } else {
//                        System.out.println("Fatal FLAW!");
//                    }
//                }
//            }
//        }catch(Exception e){}
//    }
}
