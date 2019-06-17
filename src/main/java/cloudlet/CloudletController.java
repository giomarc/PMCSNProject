package cloudlet;

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

    /**
     * choose an algorithm to redirect the packet to the correct system
     * @param e
     */
    public void dispatchArrivals(Event e){
        DispatchAlgorithm.getInstance().getAlgorithm(e);
    }

}
