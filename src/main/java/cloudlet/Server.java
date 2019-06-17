package cloudlet;

import job.Job;

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

    public int getId() {
        return id;
    }

    //GETTER

    Job getJobInService() {
        return jobInService;
    }

    public double getTimeBusy() {
        return timeBusy;
    }

    public long getJobProcessed() {
        return jobProcessed;
    }

    boolean isBusy() {
        return busy;
    }

    //SETTER

    void setJobInService(Job jobInService) {
        this.jobInService = jobInService;
        this.timeBusy += jobInService.getCompletionTime();
        this.jobProcessed++;
    }

    void setBusy(boolean busy) {
        this.busy = busy;
    }
}
