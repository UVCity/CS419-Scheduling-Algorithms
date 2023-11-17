/**
 * RR (Round Robin) scheduling algorithm.
 *
 */
 
import java.util.*;

public class RR implements Algorithm
{
    private final Queue<Process> readyQueue;

    /**
     * A list of processes that either have not arrived or have not been scheduled
     */
    private final Queue<Process> processesToSchedule;

    /**
     * The total number of processes in this simulation
     */
    private final int totalNumProcesses;

    public RR(List<Process> allProcessList) {
        readyQueue = new LinkedList<>();
        processesToSchedule = new LinkedList<>();
        totalNumProcesses = allProcessList.size();

    }

    public void schedule(){
        System.out.println("Round-Robin Scheduling \n");

        //to keep track of the total waiting time
        int totalWaitingTime = 0;

        Process currentProcess;

        /**
         * add first process to the ready queue
         */
        Process p = processesToSchedule.remove();
        if (CPU.getCurrentTime() < p.getArrivalTime()) {
            CPU.advanceTimeTo(p.getArrivalTime());
            System.out.println("we wanna know ," + CPU.getCurrentTime() + "\t"+ p.getArrivalTime()+ "\n");
        }
        readyQueue.add(p);

        while (!readyQueue.isEmpty()) {
            currentProcess = pickNextProcess();
            //CPU.run(currentProcess, currentProcess.getCPUBurstTime());

            System.out.println(currentProcess.getName() + " finished at time "+CPU.getCurrentTime() + ". Its waiting time is: " );
            while (!processesToSchedule.isEmpty()){
                p = processesToSchedule.peek();
                System.out.println(p.getName() + " finished at time " + CPU.getCurrentTime() + ". Its waiting time is: " );
            }
        }

        /**
         * We need to cast either the numerator or the denominator to the double type;
         * otherwise, when both are integers, their division result will always be rounded to integer
         */

        double averageWaitingTime = totalWaitingTime / (double) totalNumProcesses ;
        /**
         * use printf for formatted out (only show two digits after the decimal point).
         */

        System.out.printf("\nThe average waiting time is: %.2f\n", averageWaitingTime);
    }

    public Process pickNextProcess() {
        /**
         * Always pick the process that's at the front of the ready queue.
         */
        return readyQueue.remove();
    }

}
