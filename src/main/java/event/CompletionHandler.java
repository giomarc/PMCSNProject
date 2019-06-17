package event;

import results.CSVlogger;
import statistics.ConfidenceInterval;
import statistics.JobStatistics;
import statistics.TimeStatistics;



public class CompletionHandler {

    private static CompletionHandler instance = new CompletionHandler();
    private ConfidenceInterval s = ConfidenceInterval.getInstance();
    private TimeStatistics ts = TimeStatistics.getInstance();
    private JobStatistics js = JobStatistics.getInstance();

    private CompletionHandler(){}

    public static CompletionHandler getInstance(){
        return instance;
    }

    /**
     * receive the completion event from cloudlet and cloud and redirect to the correct function.
     * update the means related to the response time and update the throughputs
     * @param e
     */
    public void handleCompletion(Event e){
        int jobClass = e.getJob().getJobClass();
        double serviceTime = e.getJob().getCompletionTime();

        updateResponseTime(jobClass,serviceTime);

        if(e.getType() == 1) {                                  //cloudlet
            handleCloudletCompletion(jobClass,serviceTime);
            CSVlogger.getInstance().writResponseTimeMeanInOneSimulation(jobClass,1,serviceTime);
            js.updateThroughputStatistics();
        }
        else if(e.getType() == 2) {                             //cloud
            handleCloudCompletion(jobClass,serviceTime);
            CSVlogger.getInstance().writResponseTimeMeanInOneSimulation(jobClass,2,serviceTime);
            js.updateThroughputStatistics();
        }
        else {
            System.exit(-1);
        }
    }

    /**
     * handle the completion related to the cloudlet. update the mean response time and the
     * number of jobs completed by the cloudlet
     * @param jobclass
     * @param serviceTime
     */
    private void handleCloudletCompletion(int jobclass, double serviceTime){

        if(jobclass == 1){
            js.updateCompletedCloudlet(1);
            ts.setMeanResponseTimeClass1Cloudlet(s.computeWelfordMean(ts.getMeanResponseTimeClass1Cloudlet(), serviceTime, (int) js.getCompletedCloudlet(1)));
        }
        else if(jobclass == 2){
            js.updateCompletedCloudlet(2);
            ts.setMeanResponseTimeClass2Cloudlet(s.computeWelfordMean(ts.getMeanResponseTimeClass2Cloudlet(), serviceTime, (int) js.getCompletedCloudlet(2)));
        }
        ts.setMeanResponseTimeCloudlet(s.computeWelfordMean(ts.getMeanResponseTimeCloudlet(), serviceTime, (int) js.getCompletedCloudlet(0)));
    }

    /**
     * handle the completion related to the cloud. update the mean response time and the
     * number of jobs completed by the cloud
     * @param jobclass
     * @param serviceTime
     */
    private void handleCloudCompletion(int jobclass, double serviceTime){

        if(jobclass == 1){
            js.updateCompletedCloud(1);
            ts.setMeanResponseTimeClass1Cloud(s.computeWelfordMean(ts.getMeanResponseTimeClass1Cloud(), serviceTime, (int) js.getCompletedCloud(1)));
        }
        else if(jobclass == 2){
            js.updateCompletedCloud(2);
            ts.setMeanResponseTimeClass2Cloud(s.computeWelfordMean(ts.getMeanResponseTimeClass2Cloud(), serviceTime, (int) js.getCompletedCloud(2)));
        }
        ts.setMeanResponseTimeCloud(s.computeWelfordMean(ts.getMeanResponseTimeCloud(), serviceTime, (int) js.getCompletedCloud(0)));

    }

    /**
     * update the response time for the whole system and for each class
     * @param jobclass
     * @param serviceTime
     */
    private void updateResponseTime(int jobclass, double serviceTime){
        if(jobclass == 1)
            ts.setMeanResponseTimeClass1(s.computeWelfordMean(ts.getMeanResponseTimeClass1(), serviceTime, (int) (js.getCompletedCloudlet(1) + js.getCompletedCloud(1))));
        else if(jobclass == 2)
            ts.setMeanResponseTimeClass2(s.computeWelfordMean(ts.getMeanResponseTimeClass2(), serviceTime, (int) (js.getCompletedCloudlet(2) + js.getCompletedCloud(2))));
        ts.setMeanResponseTime(s.computeWelfordMean(ts.getMeanResponseTime(), serviceTime, (int) (js.getCompletedCloudlet(0) + js.getCompletedCloud(0) )));
    }




}

