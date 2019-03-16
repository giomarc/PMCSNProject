package cloudlet;

import cloud.Cloud;
import event.ArrivalEvent;
import runners.simulation.StatisticsGenerator;
import system.SystemConfiguration;

public class CloudletController {


    private Cloudlet cloudlet;
    private StatisticsGenerator statistics;
    private Cloud cloud;
    private int max_population;
    /**
     * Cloudlet controller
     * @param cloudlet cloudlet instance
     * @param cloud cloud instance
     */
    public CloudletController(Cloudlet cloudlet, Cloud cloud){
        statistics = StatisticsGenerator.getInstance();
        this.cloudlet = cloudlet;
        this.cloud = cloud;
        this.max_population = SystemConfiguration.N;
    }


    /**
     * Algorithm 1
     */
    public void dispatchArrivals(ArrivalEvent e){
        statistics.increaseAllPackets();

        if(e.getJobEvent().getJobclass() == 1) statistics.increasePacket1();
        else statistics.increasePacket2();

        cloudlet.removeCompletedJobsFromServers(e.getTime());
        cloudlet.updateRemainingServiceTimes(e.getTime());
        if(((cloudlet.getN1() + cloudlet.getN2()) >= max_population )) {
            statistics.increasePacketLoss();
            cloud.processJobs(e);
        }
        else {
            cloudlet.putArrivalEvent(e);
        }

        /*if(!cloudlet.putArrivalEvent(e)){
            statistics.increasePacketLoss();
            cloud.processJobs(e);
        }*/
    }




    /**
     * Algorithm 2
     */
}
