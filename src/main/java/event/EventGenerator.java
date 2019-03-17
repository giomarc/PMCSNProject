package event;

import job.Job;
import variablesGenerator.Arrivals;

public class EventGenerator {

    private static EventGenerator instance = new EventGenerator();

    private EventGenerator(){}

    public static EventGenerator getInstance(){return instance;}

    public Event generateArrival(){
        double arrival = Arrivals.getInstance().getArrival();
        Job job = new Job(Arrivals.getInstance().determineJobClass(), arrival);
        return new Event(0, job);
    }

    public Event generateCompletion(int cloudletOrCloud, Job job){
        if(cloudletOrCloud == 1 || cloudletOrCloud == 2)
            return new Event(cloudletOrCloud, job);
        else
            return null;
    }

}
