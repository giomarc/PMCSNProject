package event;

import job.Job;

public class Event {

    private int type;   // event type. 0 arrival -- 1 cloudlet completion -- 2 cloud completion
    private Job job;    //event job
    private double eventTime;
    private int additionalInfo;
    private boolean valid;

    public Event(int type, Job job){
        this.job = job;
        this.type = type;
        if(type == 0)
            eventTime = job.getArrivalTime();
        else
            eventTime = job.getCompletionTime();
        this.valid = true;
    }

    public int getType() {
        return type;
    }

    public Job getJob() {
        return job;
    }

    public double getEventTime() {
        return eventTime;
    }

    public void setEventTime(double eventTime) {
        this.eventTime = eventTime;
    }

    public int getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(int additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
