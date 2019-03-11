package event;

import job.Job;
import variablesGenerator.Arrivals;

public class ArrivalEvent extends Event {

    public ArrivalEvent(int type, double time) {
        super(type, time);
    }

    public ArrivalEvent(Job jobEvent, double time){
        super(jobEvent,time);
    }


    public static ArrivalEvent createNewArrivalEvent(){
        double arrival_time = Arrivals.getInstance().getArrival();
        int job_class = Arrivals.getInstance().determineJobClass();
        Job job = new Job(job_class);
        return new ArrivalEvent(job,arrival_time);
    }


}
