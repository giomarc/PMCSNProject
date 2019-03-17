package cloud;

import cloudlet.CloudletController;
import event.Event;
import event.EventGenerator;
import job.Job;
import runners.simulation.StatisticsGenerator;
import variablesGenerator.Services;
import java.util.ArrayList;
import java.util.Iterator;

public class Cloud {
    private static Cloud instance = new Cloud();
    private static ArrayList<Job> jobsInService;


    private Cloud(){
        jobsInService = new ArrayList<>();
    }

    public static Cloud getInstance(){ return instance;}


    public void processArrival(Event e) {
        removeCompletedJobs(e);
        processCurrentJob(e.getJob());
    }


    public void removeCompletedJobs(Event e){
        double arrivalTime = e.getJob().getArrivalTime();
        for(Job j: jobsInService){
            double updated = j.getCompletionTime() - arrivalTime;
            j.setCompletionTime(updated);
            if(j.getCompletionTime() < 0){
                StatisticsGenerator.getInstance().receiveCompletion(EventGenerator.getInstance().generateCompletion(2, j));
            }
        }
        removeFromList();
    }

    private void processCurrentJob(Job j){
        int jobclass = j.getJobClass();
        j.setCompletionTime(Services.getInstance().getCloudServiceTime(jobclass));
        jobsInService.add(j);
    }


    public void removeFromList(){
        Iterator itr = jobsInService.iterator();
        Job jobExaminated;
        while(itr.hasNext()){
            jobExaminated = (Job) itr.next();
            double x = jobExaminated.getCompletionTime();
            if( x < 0 ){
                itr.remove();
            }
        }
    }

    public double endSimulation() {
        double max = 0.0;
        for(Job j: jobsInService){
            if(j.getCompletionTime() > max)
                max = j.getCompletionTime();
            StatisticsGenerator.getInstance().receiveCompletion(EventGenerator.getInstance().generateCompletion(2, j));
        }
        return max;
    }
}
