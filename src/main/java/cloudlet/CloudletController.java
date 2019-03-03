package cloudlet;

import config.SystemConfiguration;

public class CloudletController {

    Cloudlet cloudlet;

    /**
     * Algorithm 1
     */

    public void dispatch(){
        if (cloudlet.getN1() + cloudlet.getN2() >= SystemConfiguration.N){
            //send on cloud
        }
        else{
            //if class 1 job ->
            // n1 ++
            // serve it with mu1
            //else
            // n2 ++
            // serve it with mu2
        }
    }


    /**
     * Algorithm 2
     */
}
