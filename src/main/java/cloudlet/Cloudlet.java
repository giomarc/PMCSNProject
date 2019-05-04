package cloudlet;

import event.Event;
import event.EventGenerator;
import job.Job;
import statistics.Statistics;
import system.SystemConfiguration;
import variablesGenerator.Services;

import java.util.ArrayList;

public class Cloudlet {

    private static Cloudlet instance;
    private int n1;
    private int n2;
    private int numberOfServers;
    private ArrayList<Server> serverList;



    private Cloudlet(){
        n1 = 0;
        n2 = 0;
        numberOfServers = SystemConfiguration.N;
        initServers();
    }


    public static Cloudlet getInstance(){
        if(instance == null)
            instance = new Cloudlet();
        return instance;
    }



    public void processArrival(Event e) {
        Job job = e.getJob();
        double completionTime = Services.getInstance().getCloudletServiceTime(job.getJobClass(), job.getOperations());
        for(Server s: serverList){
            if(!s.isBusy()){
                increaseN(job.getJobClass());
                s.setBusy(true);
                job.setCompletionTime(completionTime);
                s.setJobInService(job);
                break;
            }
        }
    }



    void timeHasPassed(double arrival){
        removeCompletedJobs(arrival);
    }


    void removeCompletedJobs(double arrivalTime){
        for(Server s: serverList){
            if(s.isBusy()) {
                if (s.getJobInService().getCompletionTime() <= arrivalTime) {
                    s.setBusy(false);
                    Statistics.getInstance().handleCompletion(EventGenerator.getInstance().generateCompletion(1, s.getJobInService()));
                    decreaseN(s.getJobInService().getJobClass());
                }
                else
                    s.getJobInService().setCompletionTime(s.getJobInService().getCompletionTime() - arrivalTime);
            }
        }
    }




    public int [] getJobsInCloudlet(){
        return new int[]{n1, n2};
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


    private void initServers(){
        serverList = new ArrayList<>();
        for (int i = 0; i<numberOfServers; i++) {
            serverList.add(new Server(i));
        }
    }

    double endSimulation() {
        double max = 0.0;
        for(Server s: serverList){
            if(s.isBusy()) {
                Statistics.getInstance().handleCompletion(EventGenerator.getInstance().generateCompletion(1, s.getJobInService()));
                decreaseN(s.getJobInService().getJobClass());
                s.setBusy(false);

                if(s.getJobInService().getCompletionTime() > max)
                    max = s.getJobInService().getCompletionTime();
            }
        }
        return max;
    }


    public void reset(){
        n1 = 0;
        n2 = 0;
        serverList = null;
        numberOfServers = SystemConfiguration.N;
        initServers();
    }

    public ArrayList<Server> getServerList(){
        return this.serverList;
    }
}
