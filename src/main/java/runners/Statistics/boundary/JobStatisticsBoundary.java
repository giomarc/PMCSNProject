package runners.Statistics.boundary;

import runners.Statistics.controller.JobStatisticsController;
import runners.Statistics.entity.JobStatisticsEntity;
import system.SystemConfiguration;
import variablesGenerator.Arrivals;

public class JobStatisticsBoundary {

    private JobStatisticsController jsc;
    private JobStatisticsEntity jse;

    private JobStatisticsBoundary(){
        jsc = JobStatisticsController.getInstance();
        jse = JobStatisticsEntity.getInstance();
    }

    public double getAnalyticCloudletThroughput() {
        return Arrivals.getInstance().getTotalRate() * (1 - jsc.calculatePLoss());
    }

    public double getEmpiricCloudletThroughput(){
        return (jse.getCompletedCloudletClass1() + jse.getCompletedCloudletClass2())/jse.getGlobalTime();
    }

    public double getAnalyticCloudletClass1Throughput() {
        return SystemConfiguration.ARRIVAL_RATE_1 * (1 - jsc.calculatePLoss());
    }

    public double getEmpiricCloudletClass1Throughput(){
        return jse.getCompletedCloudletClass1()/jse.getGlobalTime();
    }

    public double getAnalyticCloudletClass2Throughput() {
        return SystemConfiguration.ARRIVAL_RATE_2 * (1 - jsc.calculatePLoss());
    }

    public double getEmpiricCloudletClass2Throughput(){
        return jse.getCompletedCloudletClass2()/jse.getGlobalTime();
    }

    public double getAnalyticCloudThroughput(){
        return Arrivals.getInstance().getTotalRate()*(jsc.calculatePLoss());
    }

    public double getEmpiricCloudThroughput(){
        return (jse.getCompletedCloudClass1() + jse.getCompletedCloudClass2())/jse.getGlobalTime();
    }

    public double getAnalyticCloudClass1Throughput() {
        return SystemConfiguration.ARRIVAL_RATE_1 * (jsc.calculatePLoss());
    }

    public double getEmpiricCloudClass1Throughput(){
        return jse.getCompletedCloudClass1()/jse.getGlobalTime();
    }

    public double getAnalyticCloudClass2Throughput() {
        return SystemConfiguration.ARRIVAL_RATE_2 * (jsc.calculatePLoss());
    }

    public double getEmpiricCloudClass2Throughput(){
        return jse.getCompletedCloudClass2() /jse.getGlobalTime();
    }
}
