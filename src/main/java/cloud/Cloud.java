package cloud;

import event.CompletionHandler;
import event.Event;
import event.EventGenerator;
import job.Job;
import simulation.Simulation;
import variablesGenerator.Services;

public class Cloud {

    private static Cloud instance = new Cloud();
    private int n1;
    private int n2;


    private Cloud(){
        n1 = 0;
        n2 = 0;
    }

    public static Cloud getInstance(){ return instance;}

    /**
     * Process a new arrival
     * @param e
     */
    public void processArrival(Event e) {
        Job j = e.getJob();
        int jobclass = j.getJobClass();
        double completionTime = Services.getInstance().getCloudServiceTime(jobclass, j.getOperations());

        j.setCompletionTime(completionTime);
        increaseN(jobclass);
        sendComplitionToSimulation(j);
    }

    /**
     * Process a new completion
     * @param e
     */
    public void processCompletion(Event e){
        decreaseN(e.getJob().getJobClass());
        CompletionHandler.getInstance().handleCompletion(EventGenerator.getInstance().generateCompletion(2, e.getJob()));
    }

    /**
     * Reset the system state (the number of jobs currently in the Cloud)
     */
    public void reset(){
        n1 = 0;
        n2 = 0;
    }

    /**
     * Decrease the number of jobs in service
     * @param jobClass
     */
    private void decreaseN(int jobClass){
        if(jobClass == 1) n1--;
        else n2--;
    }

    /**
     * Increment the number of jobs in service
     * @param jobClass
     */
    private void increaseN(int jobClass){
        if(jobClass == 1) n1++;
        else n2++;
    }

    /**
     * get the number of jobs currently in the Cloud
     * @return
     */
    public int[] returnJobsInCloud(){
        return new int[]{n1, n2};
    }

    /**
     * Create a completion event and append it on the eventList
     * @param j
     */
    private void sendComplitionToSimulation(Job j){
        Event e = new Event(2, j);
        Simulation.addComplitionToEventList(e);
    }
}
