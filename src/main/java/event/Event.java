package event;

import job.Job;

public class Event {

    private int type;   // event type. 0 arrival -- 1 cloudlet completion -- 2 cloud completion
    private Job job;    //event job

    public Event(int type, Job job){
        this.job = job;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Job getJob() {
        return job;
    }

}
