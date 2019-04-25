package statistics;

import event.Event;

import java.sql.Time;

public class Statistics {


    private static Statistics instance = null;

    private Statistics(){
    }

    public static Statistics getInstance(){
        if(instance == null)
            instance = new Statistics();
        return instance;
    }


    /**
     * calculateSampleMean VERSION 2
     */
    public double computeMean(double valueToUpdate, double newValue, long n)
    {
        double diff = (newValue -  valueToUpdate);
        double i = (double) n;
        if(i!=0)
            valueToUpdate += (diff/i);

        return valueToUpdate;
    }


    public double computeVariance(double valueToUpdate, double mean, double newValue, long n){
        double i = (double) n;
        double diff = (newValue -  mean);
        valueToUpdate += (diff * diff)*((i-1)/i);
        return valueToUpdate;
    }

    public double[] computeMeanAndVariance(double oldVar, double oldMean, double newValue, long n){
        double diff = (newValue -  oldMean);
        double[] MV = new double[2];
        double i = (double) n;
        if(i!= 0){
            MV[1] = oldVar + diff * diff*(i-1)/i;
            MV[0] = oldMean + (diff/i);
        }
        return MV;
    }


    public void handleCompletion(Event e){
        int jobClass = e.getJob().getJobClass();
        double serviceTime = e.getJob().getServiceTime();

        if(e.getType() == 1) {//cloudlet
            handleCloudletCompletion(e,jobClass,serviceTime);
        }
        else if(e.getType() == 2) {     //cloud
            handleCloudCompletion(e,jobClass,serviceTime);
        }
        else
            System.exit(-1);
        updateResponseTime(jobClass,serviceTime);
    }



    public void handleCloudletCompletion(Event e, int jobclass, double serviceTime){

        TimeStatistics ts = TimeStatistics.getInstance();
        JobStatistics js = JobStatistics.getInstance();
        ts.setMeanResponseTimeCloudlet(computeMean(ts.getMeanResponseTimeCloudlet(), serviceTime, (int) js.getCompletedCloudlet(0)));
        if(jobclass == 1){
            js.updateCompletedCloudlet(1);
            ts.setMeanResponseTimeClass1Cloudlet(computeMean(ts.getMeanResponseTimeClass1Cloudlet(), serviceTime, (int) js.getCompletedCloudlet(1)));
        }
        else if(jobclass == 2){
            js.updateCompletedCloudlet(2);
            ts.setMeanResponseTimeClass2Cloudlet(computeMean(ts.getMeanResponseTimeClass2Cloudlet(), serviceTime, (int) js.getCompletedCloudlet(2)));
        }
    }



    public void handleCloudCompletion(Event e, int jobclass, double serviceTime){
        TimeStatistics ts = TimeStatistics.getInstance();
        JobStatistics js = JobStatistics.getInstance();
        ts.setMeanResponseTimeCloud(computeMean(ts.getMeanResponseTimeCloud(), serviceTime, (int) js.getCompletedCloud(0)));
        if(jobclass == 1){
            js.updateCompletedCloud(1);
            ts.setMeanResponseTimeClass1Cloud(computeMean(ts.getMeanResponseTimeClass1Cloud(), serviceTime, (int) js.getCompletedCloud(1)));
        }
        else if(jobclass == 2){
            js.updateCompletedCloud(2);
            ts.setMeanResponseTimeClass2Cloud(computeMean(ts.getMeanResponseTimeClass2Cloud(), serviceTime, (int) js.getCompletedCloud(2)));

        }
    }

    public void updateResponseTime(int jobclass, double serviceTime){
        TimeStatistics ts = TimeStatistics.getInstance();
        JobStatistics js = JobStatistics.getInstance();
        if(jobclass == 1)
            ts.setMeanResponseTimeClass1(computeMean(ts.getMeanResponseTimeClass1(), serviceTime, (int) (js.getCompletedCloudlet(1) + js.getCompletedCloud(1))));
        else if(jobclass == 2)
            ts.setMeanResponseTimeClass2(computeMean(ts.getMeanResponseTimeClass2(), serviceTime, (int) (js.getCompletedCloudlet(2) + js.getCompletedCloud(2))));
        ts.setMeanResponseTime(computeMean(ts.getMeanResponseTime(), serviceTime, (int) (js.getCompletedCloudlet(0) + js.getCompletedCloud(0) )));
    }



}
