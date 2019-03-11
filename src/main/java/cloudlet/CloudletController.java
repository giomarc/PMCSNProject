package cloudlet;

import cloud.Cloud;
import event.ArrivalEvent;
import job.Job;
import simulation.StatisticsGenerator;
import system.SystemConfiguration;

public class CloudletController {


    private Cloudlet cloudlet;
    private StatisticsGenerator statistics;
    private Cloud cloud;
    private static  int n1;
    private static  int n2;
    //private static CloudletController instance;

    /**
     * Cloudlet controller
     * @param cloudlet cloudlet instance
     * @param cloud cloud instance
     */
    public CloudletController(Cloudlet cloudlet, Cloud cloud){
        statistics = StatisticsGenerator.getInstance();
        this.cloudlet = cloudlet;
        this.cloud = cloud;
        n1 = 0;
        n2 = 0;
    }


    /**
     * Algorithm 1
     */
    public void dispatchArrivals(ArrivalEvent e){
        /*countJobs(e);
        if((n1 + n2) <= SystemConfiguration.N){
            cloudlet.putArrivalEvent(e);
        }else{
            statistics.increasePacketLoss();
            cloud.processArrivals(e);
        }*/

        if(!cloudlet.putArrivalEvent(e)){
            statistics.increasePacketLoss();
            cloud.processArrivals(e);
        }
    }


    public void countJobs(ArrivalEvent e){
        switch (e.getJobEvent().getJobclass()){
            case 1:
                n1++;
            case 2:
                n2++;
        }
        //System.out.println("Job is of class: " + e.getType() + " n1: " + n1 + " n2: " + n2);

    }

    public int getN1(){
        return n1;
    }

    public int getN2(){
        return n2;
    }

    public void decreaseJobs(Job job){
        switch (job.getJobclass()){
            case 1:
                n1--;
            case 2:
                n2--;
        }
    }
    /**
     * Algorithm 2
     */
}
