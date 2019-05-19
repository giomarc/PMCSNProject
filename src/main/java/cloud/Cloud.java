package cloud;

import event.CompletionHandler;
import event.Event;
import event.EventGenerator;
import job.Job;
import simulation.Simulation;
import statistics.Statistics;
import variablesGenerator.Services;

public class Cloud {

    private static Cloud instance = new Cloud();
    private int n1;
    private int n2;

    private double meanServiceTime = 0;
    private int iteration = 0;


    private Cloud(){
        n1 = 0;
        n2 = 0;
    }

    public static Cloud getInstance(){ return instance;}

    public void processArrival(Event e) {
        Job j = e.getJob();
        int jobclass = j.getJobClass();
        double completionTime = Services.getInstance().getCloudServiceTime(jobclass, j.getOperations());

//        if(jobclass == 1) {
//            iteration++;
//            meanServiceTime = Statistics.getInstance().computeMean(meanServiceTime, completionTime, iteration);
//            if(iteration % 50000 == 0)
//                System.out.println(meanServiceTime);
//        }

        j.setCompletionTime(completionTime);
        increaseN(jobclass);
        sendComplitionToSimulation(j);
    }

    public void processCompletion(Event e){
        decreaseN(e.getJob().getJobClass());
        CompletionHandler.getInstance().handleCompletion(EventGenerator.getInstance().generateCompletion(2, e.getJob()));
    }

    public void reset(){
        n1 = 0;
        n2 = 0;
    }

    private void decreaseN(int jobClass){
        if(jobClass == 1) n1--;
        else n2--;
    }

    private void increaseN(int jobClass){
        if(jobClass == 1) n1++;
        else n2++;
    }

    public int[] returnJobsInCloud(){
        return new int[]{n1, n2};
    }

    private void sendComplitionToSimulation(Job j){
        Event e = new Event(2, j);
        Simulation.addComplitionToEventList(e);
    }
}
