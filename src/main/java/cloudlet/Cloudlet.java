package cloudlet;

import event.CompletionHandler;
import event.Event;
import event.EventGenerator;
import job.Job;
import simulation.Simulation;
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
        int i = 0;
        for(Server s: serverList){
            if(!s.isBusy()){
                increaseN(job.getJobClass());
                s.setBusy(true);
                job.setCompletionTime(completionTime);
                s.setJobInService(job);
                sendComplitionToSimulation(job, i);
                break;
            }
            i++;
        }
    }

    public void processCompletion(Event e){
        Server s = serverList.get(e.getAdditionalInfo());
        if(!s.isBusy())
            System.exit(-1);
        s.setBusy(false);
        decreaseN(s.getJobInService().getJobClass());
        CompletionHandler.getInstance().handleCompletion(EventGenerator.getInstance().generateCompletion(1, s.getJobInService()));
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

    private void sendComplitionToSimulation(Job j, int serverID){
        Event e = new Event(1, j);
        e.setAdditionalInfo(serverID);
        Simulation.addComplitionToEventList(e);
    }
}
