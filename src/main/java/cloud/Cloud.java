package cloud;

import cloudlet.Cloudlet;
import event.Event;
import event.EventGenerator;
import job.Job;
import runners.Statistics.JobStatistics;
import runners.Statistics.Statistics;
import variablesGenerator.Services;
import java.util.ArrayList;
import java.util.Iterator;

public class Cloud {
    private static Cloud instance = new Cloud();
    private static ArrayList<Job> jobsInService;
    private int n1;
    private int n2;


    private Cloud(){
        jobsInService = new ArrayList<>();
        n1 = 0;
        n2 = 0;
    }

    public static Cloud getInstance(){ return instance;}

    public void processArrival(Event e) {
//        removeCompletedJobs(e.getJob().getArrivalTime());
        processCurrentJob(e.getJob());
    }

    private void removeCompletedJobs(double arrivalTime){
        double updated;
        for(Job j: jobsInService){
            updated = j.getCompletionTime() - arrivalTime;
            j.setCompletionTime(updated);
            if(j.getCompletionTime() < 0){
                Statistics.getInstance().receiveCompletion(EventGenerator.getInstance().generateCompletion(2, j));
            }
        }
        removeFromList();
    }

    private void processCurrentJob(Job j){
        int jobclass = j.getJobClass();
        double completionTime = Services.getInstance().getCloudServiceTime(jobclass, j.getOperations());
        j.setCompletionTime(completionTime);
        jobsInService.add(j);
        increaseN(jobclass);
    }

    private void removeFromList(){
        Iterator itr = jobsInService.iterator();
        Job jobExaminated;
        double x;
        while(itr.hasNext()){
            jobExaminated = (Job) itr.next();
            x = jobExaminated.getCompletionTime();
            if( x < 0 ){
                itr.remove();
                decreaseN(jobExaminated.getJobClass());
            }
        }
    }

    public double endSimulation() {
        double max = 0.0;
        for(Job j: jobsInService){
            if(j.getCompletionTime() > max)
                max = j.getCompletionTime();
            Statistics.getInstance().receiveCompletion(EventGenerator.getInstance().generateCompletion(2, j));
        }
        return max;
    }

    public void reset(){
        n1 = 0;
        n2 = 0;
        jobsInService = new ArrayList<>();
    }

    public void timeHasPassed(double arrivalTime) {
        removeCompletedJobs(arrivalTime);
    }

    private void decreaseN(int jobClass){
        if(jobClass == 1)
            n1--;
        else
            n2--;
    }

    private void increaseN(int jobClass){
        if(jobClass == 1)
            n1++;
        else
            n2++;
    }

    public int[] returnJobsInCloud(){
        return new int[]{n1, n2};
    }

}
