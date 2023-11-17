/**
 * FCFS (First-Come First-Served) scheduling algorithm.
 */

import java.util.*;

public class SJF implements Algorithm
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

    public SJF(List<Process> allProcessList) {
        readyQueue = new LinkedList<>();
        processesToSchedule = new LinkedList<>();
        totalNumProcesses = allProcessList.size();
        // Create a copy of the allProcessList to avoid modifying the original list
        List<Process> sortedProcesses = new LinkedList<>(allProcessList);

        // Sort the processes based on their burst times using a custom comparator
        sortedProcesses.sort((p1, p2) -> p1.getCPUBurstTime() - p2.getCPUBurstTime());

        // Add the sorted processes to processesToSchedule
        for (Process p : sortedProcesses) {
            processesToSchedule.add(p);
        }
    }

    public void schedule() {
        System.out.println("Shortest Job First \n");

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

            /**
             * Calculate the waiting time of the selected process
             */
            int wTime = 0;
            if (CPU.getCurrentTime() > currentProcess.getArrivalTime()){
                wTime = CPU.getCurrentTime() - currentProcess.getArrivalTime();
            }

            totalWaitingTime += wTime;

            CPU.run(currentProcess, currentProcess.getCPUBurstTime());

            System.out.println(currentProcess.getName() + " finished at time "+CPU.getCurrentTime() + ". Its waiting time is: " + wTime);

            while (!processesToSchedule.isEmpty()){
                p = processesToSchedule.peek();
                if (p.getArrivalTime() <= CPU.getCurrentTime()) {
                    /**
                     * process p has already arrived
                     */
                    readyQueue.add(processesToSchedule.remove());
                }else{
                    /**
                     * Process p arrives in the future.
                     * If the ready queue is empty, advance the simulation clock so that p will arrive;
                     */
                    if (readyQueue.isEmpty()) {
                        CPU.advanceTimeTo(p.getArrivalTime());
                        readyQueue.add(processesToSchedule.remove());
                    }
                    break;
                }
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
