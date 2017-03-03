package cs455.scaling.server;

import cs455.scaling.protocol.Task;

import java.util.ArrayList;

public class TaskQueue {

    private ArrayList<Task> tasks = new ArrayList<>();

    public synchronized void add(Task task){
        tasks.add(task);
    }

    public synchronized Task poll(){
        Task task;

        if(tasks.size() > 0){
            task = tasks.get(0);
            tasks.remove(0);
            return task;
        }
        return null;
    }

    public synchronized boolean isEmpty(){
        return tasks.isEmpty();
    }
}
