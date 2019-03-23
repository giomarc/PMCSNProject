package runners.Statistics;

import event.Event;
import variablesGenerator.Arrivals;

public class Statistics {

    private double sampleMean;
    private double variance;
    private static Statistics instance = null;

    private Statistics(){
        sampleMean = 0.0;
        variance = 0.0;
    }

    public static Statistics getInstance(){
        if(instance == null)
            instance = new Statistics();
        return instance;
    }


    /**
     * sample mean basing on Welford's algorithm
     */
    public void calculateSampleMean(double actualValue, int n)
    {
        double diff = (actualValue - sampleMean);
        double i = (double) n;
        //calculateVariance(diff, i);
        sampleMean = sampleMean + (diff / i);
    }

    /**
     * calculateSampleMean VERSION 2
     */
    public double welfordMean(double valueToUpdate, double currentValueToInsert, long n)
    {
        double diff = (currentValueToInsert -  valueToUpdate);
        double i = (double) n;
        if(i!=0)
            valueToUpdate = valueToUpdate + (diff / i);
        return valueToUpdate;
    }


    /*private void calculateVariance(double diff, double n){
        variance = variance + ((diff*diff)*((n-1)/n));
        iterations = (int) n;
    }*/




    public void receiveCompletion(Event e){
        JobStatistics js = JobStatistics.getInstance();
        TimeStatistics ts = TimeStatistics.getInstance();
        int jobClass = e.getJob().getJobClass();
        double serviceTime = e.getJob().getServiceTime();

        if(e.getType() == 1) {//cloudlet
            js.increaseCompletedCloudlet();
            ts.setMeanResponseTimeCloudlet(welfordMean(ts.getMeanResponseTimeCloudlet(), serviceTime, (int) js.getCompletedCloudlet()));
            if(jobClass == 1){
                js.increaseCompletedCloudletClass1();
                ts.setMeanResponseTimeClass1Cloudlet(welfordMean(ts.getMeanResponseTimeClass1Cloudlet(), serviceTime, (int) js.getCompletedCloudletClass1()));
            }
            else if(jobClass == 2){
                js.increaseCompletedCloudletClass2();
                ts.setMeanResponseTimeClass2Cloudlet(welfordMean(ts.getMeanResponseTimeClass2Cloudlet(), serviceTime, (int) js.getCompletedCloudletClass2()));
            }
        }

        else if(e.getType() == 2) {     //cloud
            js.increaseCompletedCloud();
            ts.setMeanResponseTimeCloud(welfordMean(ts.getMeanResponseTimeCloud(), serviceTime, (int) js.getCompletedCloud()));
            if(jobClass == 1){
                js.increaseCompletedCloudClass1();
                ts.setMeanResponseTimeClass1Cloud(welfordMean(ts.getMeanResponseTimeClass1Cloud(), serviceTime, (int) js.getCompletedCloudClass1()));
            }
            else if(jobClass == 2){
                js.increaseCompletedCloudClass2();
                ts.setMeanResponseTimeClass2Cloud(welfordMean(ts.getMeanResponseTimeClass2Cloud(), serviceTime, (int) js.getCompletedCloudClass2()));

            }
        }
        else
            System.exit(-1);
        if(jobClass == 1)
            ts.setMeanResponseTimeClass1(welfordMean(ts.getMeanResponseTimeClass1(), serviceTime, (int) (js.getCompletedCloudletClass1() + js.getCompletedCloudClass1())));
        else if(jobClass == 2)
            ts.setMeanResponseTimeClass2(welfordMean(ts.getMeanResponseTimeClass2(), serviceTime, (int) (js.getCompletedCloudletClass2() + js.getCompletedCloudClass2())));

        ts.setMeanResponseTime(welfordMean(ts.getMeanResponseTime(), serviceTime, (int) (js.getCompletedCloudletClass1() + js.getCompletedCloudClass1() + js.getCompletedCloudletClass2() + js.getCompletedCloudClass2())));
    }



}
