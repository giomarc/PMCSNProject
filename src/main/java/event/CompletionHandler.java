package event;

import results.CSVlogger;
import simulation.Simulation;
import statistics.JobStatistics;
import statistics.Statistics;
import statistics.TimeStatistics;

/**
 * Author : Simone D'Aniello
 * Date :  18/05/2019.
 */
public class CompletionHandler {

    private static CompletionHandler instance = new CompletionHandler();
    Statistics s = Statistics.getInstance();
    private TimeStatistics ts = TimeStatistics.getInstance();
    private JobStatistics js = JobStatistics.getInstance();

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
        }
        else if(e.getType() == 2) {                             //cloud
            handleCloudCompletion(jobClass,serviceTime);
            CSVlogger.getInstance().writResponseTimeMeanInOneSimulation(jobClass,2,serviceTime);
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
}
