package cloudlet;

import cloud.Cloud;
import event.Event;


public class CloudletController {
    private static CloudletController instance;


    private CloudletController(){
    }

    public static CloudletController getInstance(){
        if(instance == null)
            instance = new CloudletController();
        return instance;
    }


    public void dispatchArrivals(Event e){

        DispatchAlgorithm.getInstance().getAlgorithm(e);

    }


    public double endSimulation(){
        double cloudletEndTime = Cloudlet.getInstance().endSimulation();
        double cloudEndTime = Cloud.getInstance().endSimulation();
        return Math.max(cloudletEndTime, cloudEndTime);
    }


}
