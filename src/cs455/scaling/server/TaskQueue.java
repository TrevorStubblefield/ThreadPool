package cs455.scaling.server;

import cs455.scaling.protocol.Task;

import java.util.ArrayList;

public class TaskQueue {

    private ArrayList<Task> tasks = new ArrayList<>();

    public synchronized void add(Task task){
        try {
            tasks.add(task);
            notify();
        }
        catch(Exception e){}
    }

    public synchronized Task poll(){
        try {
            Task task;

            while (tasks.size() < 1) {wait(10);}

            task = tasks.get(0);
            tasks.remove(0);
            return task;
        }
        catch(Exception e){}
        return null;
    }

    public synchronized boolean isEmpty(){
        return tasks.isEmpty();
    }
}
