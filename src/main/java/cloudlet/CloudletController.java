package cloudlet;

import cloud.Cloud;
import event.ArrivalEvent;
import runners.simulation.StatisticsGenerator;
import system.SystemConfiguration;

public class CloudletController {


    private Cloudlet cloudlet;
    private StatisticsGenerator statistics;
    private Cloud cloud;
    /**
     * Cloudlet controller
     * @param cloudlet cloudlet instance
     * @param cloud cloud instance
     */
    public CloudletController(Cloudlet cloudlet, Cloud cloud){
        statistics = StatisticsGenerator.getInstance();
        this.cloudlet = cloudlet;
        this.cloud = cloud;
    }


    /**
     * Algorithm 1
     */
    public void dispatchArrivals(ArrivalEvent e){
        int max_population = SystemConfiguration.N;
        cloudlet.removeCompletedJobsFromServers(e.getTime());
        cloudlet.updateRemainingServiceTimes(e.getTime());
        int n1 = cloudlet.getN1();
        int n2 = cloudlet.getN2();
        //System.out.println("n1 = " + n1 + " n2 " + n2);

        if(((n1 + n2) >= max_population )) {

            statistics.increasePacketLoss();
            cloud.processJobs(e);
        }
        else
            cloudlet.putArrivalEvent(e);

        /*if(!cloudlet.putArrivalEvent(e)){
            statistics.increasePacketLoss();
            cloud.processJobs(e);
        }*/
    }




    /**
     * Algorithm 2
     */
}
