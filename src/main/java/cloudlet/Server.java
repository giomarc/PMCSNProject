package cloudlet;

import variablesGenerator.Services;

import java.util.ArrayList;

/**
 * Author : Simone D'Aniello
 * Date :  03-Mar-19.
 */
public class Server {

    private int idServer;
    private Double currentCompletionTime;
    private int numberCompletion1;
    private boolean busy;
    private double totalTimeBusy;
    private double utilization;
    private int avgNumberOfJobs;
    private int numberCompletion2;


    public Server(int idServer){
        this.idServer = idServer;
        this.numberCompletion1 = 0;
        this.numberCompletion2 = 0;
        this.utilization = 0;
        this.avgNumberOfJobs = 0;
        this.currentCompletionTime = 0.0;
        this.busy = false;
    }


    /**
     * Method that initialized a server list
     * @param numServer
     */
    public static void initServers(ArrayList<Server> serverList, int numServer){
        for (int i = 0; i<numServer; i++) {
            serverList.add(new Server(i));
        }
    }


    /**
     * Getter and Setters
     */
    public int getIdServer() {
        return idServer;
    }

    public int getNumberCompletion1() {
        return numberCompletion1;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        if(!busy)
            numberCompletion1++;
        this.busy = busy;
    }

    public Double getCurrentCompletionTime() {
        return currentCompletionTime;
    }

    public void setCurrentCompletionTime(int eventType) {
        if(eventType == 1)
            this.currentCompletionTime = Services.getInstance().getCloudletServiceTime(1);
        else if(eventType == 2)
            this.currentCompletionTime = Services.getInstance().getCloudletServiceTime(2);
        else if(eventType == -1)
            this.currentCompletionTime = 0.0;
        else{
            System.out.println("Event not recognized");
            System.exit(-1);
        }
    }

    public void decreaseTime(double time){
        this.currentCompletionTime -= time;
    }

    public double getTotalTimeBusy() {
        return totalTimeBusy;
    }

    public void setTotalTimeBusy(double partialBusyTime) {

        this.totalTimeBusy += partialBusyTime;
    }
}
