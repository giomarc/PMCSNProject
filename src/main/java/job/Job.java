package job;

public class Job {

    private double arrival;
    private double service;
    private double completion;
    private int servedBy;
    private int jobclass;


    public Job(){
        this.arrival = 0.0;
        this.service = 0.0;
    }

    public Job(double arrival, double service){
        this.arrival = arrival;
        this.service = service;
    }

    public Job(int jobclass){
        this.arrival = 0.0;
        this.service = 0.0;
        this.completion = 0.0;
        this.jobclass = jobclass;
    }



    /**
     *  Abstract method to compute completition time
     */

    public void getCompletitionTime(){};


    /**
     * Getter and Setter
     */
    public double getService(){ return service;}
    public  double getArrival(){
        return  arrival;
    }
    public  void setArrival(double arrival){ this.arrival = arrival;}
    public  void setService(double service){this.service = service;}
    public int getJobclass(){ return jobclass;}
    public void setJobclass(int jobclass){this.jobclass = jobclass;}


}
