package server;

import variablesGenerator.Services;

/**
 * Author : Simone D'Aniello
 * Date :  03-Mar-19.
 */
public class Server {

    private int idServer;
    private Double currentCompletionTime;
    private int numberCompletion1;
    private int numberCompletion2;
    private double utilization;
    private int avgNumberOfJobs;
    private boolean busy;
    private double totalTimeBusy;

    public Server(int idServer){
        this.idServer = idServer;
        this.numberCompletion1 = 0;
        this.numberCompletion2 = 0;
        this.utilization = 0;
        this.avgNumberOfJobs = 0;
        this.currentCompletionTime = 0.0;
        this.busy = false;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public int getNumberCompletion1() {
        return numberCompletion1;
    }

    public void setNumberCompletion1(int numberCompletion1) {
        this.numberCompletion1 = numberCompletion1;
    }

    public int getNumberCompletion2() {
        return numberCompletion2;
    }

    public void setNumberCompletion2(int numberCompletion2) {
        this.numberCompletion2 = numberCompletion2;
    }

    public double getUtilization() {
        return utilization;
    }

    public void setUtilization(double utilization) {
        this.utilization = utilization;
    }

    public int getAvgNumberOfJobs() {
        return avgNumberOfJobs;
    }

    public void setAvgNumberOfJobs(int avgNumberOfJobs) {
        this.avgNumberOfJobs = avgNumberOfJobs;
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
            System.out.println("event not recognized");
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
