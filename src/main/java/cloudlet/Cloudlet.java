package cloudlet;

import event.Event;
import event.EventGenerator;
import job.Job;
import runners.simulation.StatisticsGenerator;
import system.SystemConfiguration;
import variablesGenerator.Services;

import java.util.ArrayList;

public class Cloudlet {

    private static Cloudlet instance = new Cloudlet();
    private static int n1;
    private static int n2;
    private static int numberOfServers;
    private static ArrayList<Server> serverList;
    //private int iteration;



    private Cloudlet(){
        numberOfServers = SystemConfiguration.N;
        //iteration = 1;
        initServers();
    }

    public static Cloudlet getInstance(){
        return instance;
    }

    public void processArrival(Event e) {
        Job job = e.getJob();
        double completionTime = Services.getInstance().getCloudletServiceTime(job.getJobClass());
        for(Server s: serverList){
            if(!s.isBusy()){
                increaseN(job.getJobClass());
                //calculateCloudletStatistics((n1+n2),iteration);
                s.setBusy(true);
                s.setJobInService(job);
                s.getJobInService().setCompletionTime(completionTime);
                break;
            }
        }
        //iteration++;
    }

    void removeCompletedJobs(double arrivalTime){
        for(Server s: serverList){
            if(s.isBusy()) {
                if (s.getJobInService().getCompletionTime() <= arrivalTime) {
                    s.setBusy(false);
                    StatisticsGenerator.getInstance().receiveCompletion(EventGenerator.getInstance().generateCompletion(1, s.getJobInService()));
                    decreaseN(s.getJobInService().getJobClass());
                    //calculateCloudletStatistics((n1+n2),iteration);
                }
                else
                    s.getJobInService().setCompletionTime(s.getJobInService().getCompletionTime() - arrivalTime);
            }
        }
    }

    int numberOfJobsInCloudlet(double arrival){
        removeCompletedJobs(arrival);
        return n1 + n2;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public int getJobInCloudletAfterComplition(){
        return (n1+n2);
    }

    public static void decreaseN(int jobClass){
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
                StatisticsGenerator.getInstance().receiveCompletion(EventGenerator.getInstance().generateCompletion(1, s.getJobInService()));
                s.setBusy(false);

                if(s.getJobInService().getCompletionTime() > max)
                    max = s.getJobInService().getCompletionTime();
            }
        }
        return max;
    }


    public static void calculateCloudletStatistics(double actualvalue,int n){
        StatisticsGenerator.getInstance().calculateSampleMean(actualvalue,n);
    }

    public void reset(){
        n1 = 0;
        n2 = 0;
        serverList = null;
        numberOfServers = SystemConfiguration.N;
        initServers();
    }
}
