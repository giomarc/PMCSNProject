package event;

import job.Job;

public class Event {
    private int type; //0 arrival, 1 completion cloudlet, 2 completion cloud?
    private double time;
    private Job jobEvent;


    public Event(int type, double time){
        this.type = type;
        this.time = time;
    }


    public Event(Job jobEvent, double time){
        this.jobEvent = jobEvent;
        this.type = jobEvent.getJobclass();
        this.time = time;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Job getJobEvent(){
        return this.jobEvent;
    }

    public void setJobEvent(Job jobEvent){
        this.jobEvent = jobEvent;
    }
}
