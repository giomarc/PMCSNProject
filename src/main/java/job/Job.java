package job;

public class Job {

    private double  arrivalTime;
    private double  completionTime;
    private int     jobClass;

    public Job(int jobClass, double arrivalTime){
        this.completionTime = 0.0;
        this.arrivalTime = arrivalTime;
        this.jobClass = jobClass;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
    }

    public int getJobClass() {
        return jobClass;
    }

    public void setJobClass(int jobClass) {
        this.jobClass = jobClass;
    }
}
