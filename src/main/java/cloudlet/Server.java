package cloudlet;

import job.Job;
import variablesGenerator.Services;

import java.util.ArrayList;

/**
 * Author : Simone D'Aniello
 * Date :  03-Mar-19.
 */
public class Server {

    private int id;
    private Job jobInService;
    private boolean busy;

    private double timeBusy;
    private long jobProcessed;


    public Server(int idServer){
        this.id = idServer;
        this.busy = false;
        this.timeBusy = 0.0;
        this.jobProcessed = 0L;
    }



    /**
     * Getter and Setters
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Job getJobInService() {
        return jobInService;
    }

    public void setJobInService(Job jobInService) {
        this.jobInService = jobInService;
        this.timeBusy += jobInService.getCompletionTime();
        this.jobProcessed++;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }


    public double getTimeBusy() {
        return timeBusy;
    }

    public long getJobProcessed() {
        return jobProcessed;
    }
}
