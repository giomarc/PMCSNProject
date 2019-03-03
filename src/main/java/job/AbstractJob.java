package job;

public abstract class AbstractJob {

    private double arrival;
    private double service;
    private double completion;
    private int servedBy;


    public AbstractJob(){
        this.arrival = 0.0;
        this.service = 0.0;
    }

    public AbstractJob(double arrival, double service){
        this.arrival = arrival;
        this.service = service;
    }



    /**
     *  Abstract method to compute completition time
     */

    public abstract double getCompletitionTime();


    /**
     * Getter and Setter
     */
    public double getService(){ return service;}
    public  double getArrival(){return  arrival;}
    public  void setArrival(double arrival){ this.arrival = arrival;}
    public  void setService(double service){this.service = service;}

}
