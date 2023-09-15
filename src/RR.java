/**
 * RR (Round Robin) scheduling algorithm.
 *
 */
 
import java.util.*;

public class RR implements Algorithm
{
    private final Queue<Process> readyQueue;


    public RR(List<Process> allProcessList) {
        readyQueue = new LinkedList<>();
        System.out.println("Pass RR");
    }

    public void schedule(){

    }

    public Process pickNextProcess() {
        return readyQueue.remove();
    }

}
