package event;

import results.CSVlogger;
import statistics.JobStatistics;
import statistics.Statistics;
import statistics.TimeStatistics;



public class CompletionHandler {

    private static CompletionHandler instance = new CompletionHandler();
    Statistics s = Statistics.getInstance();
    private TimeStatistics ts = TimeStatistics.getInstance();
    private JobStatistics js = JobStatistics.getInstance();
    private double meanSystemThroughput;
    private double meanSystemThroughput1;
    private double meanSystemThroughput2;
    private double meanCletThroughput;
    private double meanCletThroughput1;
    private double meanCletThroughput2;
    private double meanCloudThroughput;
    private double meanCloudThroughput1;
    private double meanCloudThroughput2;


    private CompletionHandler(){}

    public static CompletionHandler getInstance(){
        return instance;
    }



    public void handleCompletion(Event e){
        int jobClass = e.getJob().getJobClass();
        double serviceTime = e.getJob().getCompletionTime();

        updateResponseTime(jobClass,serviceTime);

        if(e.getType() == 1) {                                  //cloudlet
            handleCloudletCompletion(jobClass,serviceTime);
            CSVlogger.getInstance().writResponseTimeMeanInOneSimulation(jobClass,1,serviceTime);
            updateThroughputStatistics();
        }
        else if(e.getType() == 2) {                             //cloud
            handleCloudCompletion(jobClass,serviceTime);
            CSVlogger.getInstance().writResponseTimeMeanInOneSimulation(jobClass,2,serviceTime);
            updateThroughputStatistics();
        }
        else {
            System.exit(-1);
        }
    }



    public void handleCloudletCompletion(int jobclass, double serviceTime){

        if(jobclass == 1){
            js.updateCompletedCloudlet(1);
            ts.setMeanResponseTimeClass1Cloudlet(s.computeMean(ts.getMeanResponseTimeClass1Cloudlet(), serviceTime, (int) js.getCompletedCloudlet(1)));
        }
        else if(jobclass == 2){
            js.updateCompletedCloudlet(2);
            ts.setMeanResponseTimeClass2Cloudlet(s.computeMean(ts.getMeanResponseTimeClass2Cloudlet(), serviceTime, (int) js.getCompletedCloudlet(2)));
        }
        ts.setMeanResponseTimeCloudlet(s.computeMean(ts.getMeanResponseTimeCloudlet(), serviceTime, (int) js.getCompletedCloudlet(0)));
    }



    public void handleCloudCompletion(int jobclass, double serviceTime){

        if(jobclass == 1){
            js.updateCompletedCloud(1);
            ts.setMeanResponseTimeClass1Cloud(s.computeMean(ts.getMeanResponseTimeClass1Cloud(), serviceTime, (int) js.getCompletedCloud(1)));
        }

        else if(jobclass == 2){
            js.updateCompletedCloud(2);
            ts.setMeanResponseTimeClass2Cloud(s.computeMean(ts.getMeanResponseTimeClass2Cloud(), serviceTime, (int) js.getCompletedCloud(2)));
        }
        ts.setMeanResponseTimeCloud(s.computeMean(ts.getMeanResponseTimeCloud(), serviceTime, (int) js.getCompletedCloud(0)));

    }



    public void updateResponseTime(int jobclass, double serviceTime){

        if(jobclass == 1)
            ts.setMeanResponseTimeClass1(s.computeMean(ts.getMeanResponseTimeClass1(), serviceTime, (int) (js.getCompletedCloudlet(1) + js.getCompletedCloud(1))));
        else if(jobclass == 2)
            ts.setMeanResponseTimeClass2(s.computeMean(ts.getMeanResponseTimeClass2(), serviceTime, (int) (js.getCompletedCloudlet(2) + js.getCompletedCloud(2))));
        ts.setMeanResponseTime(s.computeMean(ts.getMeanResponseTime(), serviceTime, (int) (js.getCompletedCloudlet(0) + js.getCompletedCloud(0) )));
    }




    public void updateThroughputStatistics(){

        double sysT = JobStatistics.getInstance().getSystemThroughput();
        double sysT1 = JobStatistics.getInstance().getSystemClass1Throughput();
        double sysT2 = JobStatistics.getInstance().getSystemClass2Throughput();
        double cletT = JobStatistics.getInstance().getCloudletThroughput();
        double cletT1 = JobStatistics.getInstance().getCloudletClass1Throughput();
        double cletT2 = JobStatistics.getInstance().getCloudletClass2Throughput();
        double cloudT = JobStatistics.getInstance().getCloudThroughput();
        double cloudT1 = JobStatistics.getInstance().getCloudClass1Throughput();
        double cloudT2 = JobStatistics.getInstance().getCloudClass2Throughput();
        long iterations = JobStatistics.getInstance().getActuallIteration();

        meanSystemThroughput = Statistics.getInstance().computeWelfordMean(meanSystemThroughput,sysT, iterations);
        meanSystemThroughput1 = Statistics.getInstance().computeWelfordMean(meanSystemThroughput1,sysT1, iterations);
        meanSystemThroughput2 = Statistics.getInstance().computeWelfordMean(meanSystemThroughput2,sysT2, iterations);

        meanCletThroughput = Statistics.getInstance().computeWelfordMean(meanCletThroughput,cletT, iterations);
        meanCletThroughput = Statistics.getInstance().computeWelfordMean(meanCletThroughput1,cletT1, iterations);
        meanCletThroughput = Statistics.getInstance().computeWelfordMean(meanCletThroughput2,cletT2, iterations);

        meanCloudThroughput = Statistics.getInstance().computeWelfordMean(meanCloudThroughput,cloudT, iterations);
        meanCloudThroughput1 = Statistics.getInstance().computeWelfordMean(meanCloudThroughput1,cloudT1, iterations);
        meanCloudThroughput2 = Statistics.getInstance().computeWelfordMean(meanCloudThroughput2,cloudT2, iterations);


    }


    public double getThroughputStatistics(int index, String s){
        double res = 0.0;
        switch (s){
            case "sys":
                switch(index){
                    case 0: res = meanSystemThroughput;break;
                    case 1: res = meanSystemThroughput1; break;
                    case 2: res = meanSystemThroughput2; break;
                }break;
            case "clet":
                switch(index){
                    case 0: res = meanCletThroughput;break;
                    case 1: res = meanCletThroughput1; break;
                    case 2: res = meanCletThroughput2; break;
                }break;
            case "cloud":
                switch(index){
                    case 0: res = meanCloudThroughput;break;
                    case 1: res = meanCloudThroughput1; break;
                    case 2: res = meanCloudThroughput2; break;
                }break;
        }
        return res;
    }

    public void resetThroughputStatistics(){
        this.meanSystemThroughput       = 0;
        this.meanSystemThroughput1      = 0;
        this.meanSystemThroughput2      = 0;
        this.meanCletThroughput         = 0;
        this.meanCletThroughput1        = 0;
        this.meanCletThroughput2        = 0;
        this.meanCloudThroughput        = 0;
        this.meanCloudThroughput1       = 0;
        this.meanCloudThroughput2       = 0;

    }
}

