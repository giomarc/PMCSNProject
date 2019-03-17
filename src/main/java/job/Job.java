package job;

public class Job {

    private double  arrivalTime;
    private double  completionTime;
    private double  serviceTime;
    private int     jobClass;

    public Job(int jobClass, double arrivalTime){
        this.completionTime = 0.0;
        this.serviceTime = 0.0;
        this.arrivalTime = arrivalTime;
        this.jobClass = jobClass;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(double completionTime) {
        if(this.completionTime == 0.0)
            serviceTime = completionTime;
        this.completionTime = completionTime;
    }

    public int getJobClass() {
        return jobClass;
    }

    public double getServiceTime() {
        return serviceTime;
    }
}
