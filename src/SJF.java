/**
 * Non-preemptive SJF (Shortest-Job First) scheduling algorithm.
 *
 */

import java.util.*;

public class SJF implements Algorithm
{
    private final Queue<Process> readyQueue;

    public SJF(List<Process> allProcessList) {
        readyQueue = new LinkedList<>();
        System.out.println("Pass SJF");
    }

    public void schedule(){

    }

    public Process pickNextProcess() {
        return readyQueue.remove();
    }

}
