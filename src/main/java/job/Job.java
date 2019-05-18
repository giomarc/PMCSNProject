package job;

public class Job {

    private double  arrivalTime;
    private double  completionTime;
    private int     jobClass;
    private double operations;

    public Job(int jobClass, double arrivalTime, double operations){
        this.completionTime = 0.0;
        this.arrivalTime = arrivalTime;
        this.jobClass = jobClass;
        this.operations = operations;
    }

    public double getArrivalTime() {
        return arrivalTime;
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

    public double getOperations() {
        return operations;
    }
}
