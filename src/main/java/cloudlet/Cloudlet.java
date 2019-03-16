package cloudlet;

import event.Event;
import job.Job;
import system.SystemConfiguration;
import variablesGenerator.Services;

import java.util.ArrayList;

public class Cloudlet {

    private static Cloudlet instance = new Cloudlet();
    private static int n1;
    private static int n2;
    private static int numberOfServers;
    private static ArrayList<Server> serverList;


    private Cloudlet(){
        numberOfServers = SystemConfiguration.N;
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
                s.setBusy(true);
                s.setJobInService(job);
                s.getJobInService().setCompletionTime(completionTime);
                break;
            }
        }
    }

    void removeCompletedJobs(double arrivalTime){
        for(Server s: serverList){
            if(s.isBusy()) {
                if (s.getJobInService().getCompletionTime() <= arrivalTime) {
                    s.setBusy(false);
                    decreaseN(s.getJobInService().getJobClass());
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
}
