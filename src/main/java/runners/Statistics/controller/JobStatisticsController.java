package runners.Statistics.controller;

import runners.Statistics.Statistics;
import runners.Statistics.entity.JobStatisticsEntity;
import runners.simulation.BatchMeans;
import runners.simulation.ConfidenceInterval;
import system.CSVlogger;
import system.SystemConfiguration;
import variablesGenerator.Arrivals;

public class JobStatisticsController {


    private static JobStatisticsController instance = null;
    private static Statistics statistics;
    private static ConfidenceInterval confidenceInterval;
    private static BatchMeans batchMeans;
    private JobStatisticsEntity jse;



    private JobStatisticsController(){
        resetStatistics();
        statistics = Statistics.getInstance();
        batchMeans = BatchMeans.getInstance();
        confidenceInterval = ConfidenceInterval.getInstance();
        jse = JobStatisticsEntity.getInstance();
    }

    public static JobStatisticsController getInstance()
    {
        if(instance == null)
            instance = new JobStatisticsController();
        return instance;
    }


    public void updatePopulationMeansTest(int cloudletOrCloud, int jobClass,
                                          int[] cloudletPopulation,
                                          int[] cloudPopulation){


        updateGlobalIterations(cloudletOrCloud, jobClass);
        // aggiornamento della popolazione media senza considerare la classe
        int totcloudletPopulation = cloudletPopulation[0] + cloudletPopulation[1];
        int totcloudPopulation = cloudPopulation[0] + cloudPopulation[1];

        jse.setMeanGlobalPopulation(statistics.welfordMean(jse.getMeanGlobalPopulation(),
                totcloudletPopulation + totcloudPopulation, jse.getActualIteration()));
        jse.setVarGlobalPopulation(confidenceInterval.computeVariance(jse.getMeanGlobalPopulation(),
                totcloudletPopulation + totcloudPopulation, jse.getVarGlobalPopulation(), jse.getActualIteration()));

        jse.setMeanCloudletPopulation(statistics.welfordMean(jse.getMeanCloudletPopulation(),
                totcloudletPopulation, jse.getActualIteration()));
        jse.setVarCloudletPopulation(confidenceInterval.computeVariance(jse.getMeanCloudletPopulation(),
                totcloudletPopulation,jse.getVarCloudletPopulation(), jse.getActualIteration()));

        jse.setMeanCloudPopulation(statistics.welfordMean(jse.getMeanCloudPopulation(),
                totcloudPopulation, jse.getActualIteration()));
        jse.setVarCloudPopulation(confidenceInterval.computeVariance(jse.getMeanCloudPopulation(),
                totcloudPopulation,jse.getVarCloudPopulation(), jse.getActualIteration()));


        //aggiornamento della popolazione media per la classe 1
        if(jobClass == 1) {
            long iterationClass1 = jse.getCloudletIterationClass1() + jse.getCloudIterationClass1();
            jse.setMeanGlobalPopulationClass1(statistics.welfordMean(jse.getMeanGlobalPopulationClass1(),
                    cloudletPopulation[0] + cloudPopulation[0], iterationClass1));
            jse.setVarGlobalPopulationClass1(confidenceInterval.computeVariance(jse.getMeanGlobalPopulationClass1(),
                    cloudletPopulation[0] + cloudPopulation[0], jse.getVarGlobalPopulationClass1(),iterationClass1));

            jse.setMeanCloudletPopulationClass1(statistics.welfordMean(jse.getMeanCloudletPopulationClass1(),
                    cloudletPopulation[0], jse.getCloudletIterationClass1()));
            jse.setVarCloudletPopulationClass1(confidenceInterval.computeVariance(jse.getMeanCloudletPopulationClass1(),
                    cloudletPopulation[0], jse.getVarCloudletPopulationClass1(),jse.getCloudletIterationClass1()));

            jse.setMeanCloudPopulationClass1(statistics.welfordMean(jse.getMeanCloudPopulationClass1(),
                    cloudPopulation[0], jse.getCloudIterationClass1()));
            jse.setVarCloudPopulationClass1(confidenceInterval.computeVariance(jse.getMeanCloudPopulationClass1(),
                    cloudPopulation[0],jse.getVarCloudPopulationClass1(),iterationClass1));

        }

        //aggiornamento della popolazione media per la classe 2
        else if(jobClass == 2){
            long iterationClass2 = jse.getCloudletIterationClass2() + jse.getCloudIterationClass2();
            jse.setMeanGlobalPopulationClass2(statistics.welfordMean(jse.getMeanGlobalPopulationClass2(),
                    cloudletPopulation[1] + cloudPopulation[1], iterationClass2));
            jse.setVarGlobalPopulationClass2(confidenceInterval.computeVariance(jse.getMeanGlobalPopulationClass2(),
                    cloudletPopulation[1] + cloudPopulation[1], jse.getVarGlobalPopulationClass2(),iterationClass2));

            jse.setMeanCloudletPopulationClass2(statistics.welfordMean(jse.getMeanCloudletPopulationClass2(),
                    cloudletPopulation[1], jse.getCloudletIterationClass2()));
            jse.setVarCloudletPopulationClass2(confidenceInterval.computeVariance(jse.getMeanCloudletPopulationClass2(),
                    cloudletPopulation[1], jse.getVarCloudletPopulationClass2(),jse.getCloudletIterationClass2()));

            jse.setMeanCloudPopulationClass2(statistics.welfordMean(jse.getMeanCloudPopulationClass2(),
                    cloudPopulation[1], jse.getCloudIterationClass2()));
            jse.setVarCloudPopulationClass2(confidenceInterval.computeVariance(jse.getMeanCloudPopulationClass2(),
                    cloudPopulation[1],jse.getVarCloudPopulationClass2(),iterationClass2));

        }
        computeBatch();
//        CSVlogger.getInstance().writeMeansInOneSimulation(this); -----RIPORTA
    }

    public void computeBatch(){

        if(SystemConfiguration.BATCH && ((jse.getActualIteration() == batchMeans.getBatchSize()) || jse.getGlobalIteration() == SystemConfiguration.ITERATIONS)){

            batchMeans.updateBMGlobalPopulation(jse.getMeanGlobalPopulation(),0);
            batchMeans.updateBMGlobalPopulation(jse.getVarGlobalPopulation(),1);
            batchMeans.updateBMCloudletPopulation(jse.getMeanCloudletPopulation(),0);
            batchMeans.updateBMCloudletPopulation(jse.getVarCloudletPopulation(),1);
            batchMeans.updateBMCloudPopulation(jse.getMeanCloudPopulation(),0);
            batchMeans.updateBMCloudPopulation(jse.getVarCloudPopulation(),1);

            batchMeans.updateBMGlobalPopulationClass1(jse.getMeanGlobalPopulationClass1(),0);
            batchMeans.updateBMGlobalPopulationClass1(jse.getVarGlobalPopulationClass1(),1);
            batchMeans.updateBMCloudletPopulationClass1(jse.getMeanCloudletPopulationClass1(),0);
            batchMeans.updateBMCloudletPopulationClass1(jse.getVarCloudletPopulationClass1(),1);
            batchMeans.updateBMCloudPopulationClass1(jse.getMeanCloudPopulationClass1(),0);
            batchMeans.updateBMCloudPopulationClass1(jse.getVarCloudPopulationClass1(),1);

            batchMeans.updateBMGlobalPopulationClass2(jse.getMeanGlobalPopulationClass2(),0);
            batchMeans.updateBMGlobalPopulationClass2(jse.getVarGlobalPopulationClass2(),1);
            batchMeans.updateBMCloudletPopulationClass2(jse.getMeanCloudletPopulationClass2(),0);
            batchMeans.updateBMCloudletPopulationClass2(jse.getVarCloudletPopulationClass2(),1);
            batchMeans.updateBMCloudPopulationClass2(jse.getVarCloudPopulationClass2(),0);
            batchMeans.updateBMCloudPopulationClass2(jse.getVarCloudPopulationClass2(),1);

            resetMeans();
        }
    }

    private void updateGlobalIterations(int cloudletOrCloud, int jobClass){
        if(cloudletOrCloud == 1 && jobClass == 1)
            jse.setCloudletIterationClass1(jse.getCloudletIterationClass1() +1);
        if(cloudletOrCloud == 1 && jobClass == 2)
            jse.setCloudletIterationClass2(jse.getCloudletIterationClass2() +1);
        if(cloudletOrCloud == 2 && jobClass == 1)
            jse.setCloudIterationClass1(jse.getCloudIterationClass1() +1);
        if(cloudletOrCloud == 2 && jobClass == 2)
            jse.setCloudIterationClass2(jse.getCloudIterationClass2() +1);

        jse.setActualIteration(jse.getActualIteration() + 1);
        jse.setGlobalIteration(jse.getGlobalIteration() + 1);
    }

    //PLOSS
    public double calculatePLoss(){
        double allPackets = jse.getCompletedCloudletClass1()+
                            jse.getCompletedCloudletClass2()+
                            jse.getCompletedCloudClass1()+
                            jse.getCompletedCloudClass2();
        double packetLoss = jse.getCompletedCloudClass1()+
                            jse.getCompletedCloudClass2();
        return packetLoss/allPackets;
    }


    //INCREASE COMPLETED JOBS
    void increaseCompletedCloudClass1() {
        jse.setCompletedCloudClass1(jse.getCompletedCloudClass1() + 1);
    }

    void increaseCompletedCloudClass2() {
        jse.setCompletedCloudClass2(jse.getCompletedCloudClass2() +1);
    }

    void increaseCompletedCloudletClass1(){
        jse.setCompletedCloudletClass1 (jse.getCompletedCloudletClass1() +1);
    }

    void increaseCompletedCloudletClass2(){
        jse.setCompletedCloudletClass2 (jse.getCompletedCloudletClass2() +1);
    }


    //RESET STATISTICS
    public void resetStatistics(){
        jse.setCompletedCloudletClass1      (0);
        jse.setCompletedCloudClass1         (0);
        jse.setCompletedCloudletClass2      (0);
        jse.setCompletedCloudClass2         (0);
        jse.setGlobalTime                   (0);
        jse.setGlobalIteration              (0);
        resetMeans();
    }

    public void resetMeans(){
        jse.setMeanGlobalPopulation           (0);
        jse.setMeanCloudletPopulation         (0);
        jse.setMeanCloudPopulation            (0);
        jse.setMeanGlobalPopulationClass1     (0);
        jse.setMeanCloudletPopulationClass1   (0);
        jse.setMeanCloudPopulationClass1      (0);
        jse.setMeanGlobalPopulationClass2     (0);
        jse.setMeanCloudletPopulationClass2   (0);
        jse.setMeanCloudPopulationClass2      (0);
        jse.setVarGlobalPopulation            (0);
        jse.setVarCloudletPopulation          (0);
        jse.setVarCloudPopulation             (0);
        jse.setVarGlobalPopulationClass1      (0);
        jse.setVarCloudletPopulationClass1    (0);
        jse.setVarCloudPopulationClass1       (0);
        jse.setVarGlobalPopulationClass2      (0);
        jse.setVarCloudletPopulationClass2    (0);
        jse.setVarCloudPopulationClass2       (0);
        jse.setCloudletIterationClass1        (0);
        jse.setCloudletIterationClass2        (0);
        jse.setCloudIterationClass1           (0);
        jse.setCloudIterationClass2           (0);
        jse.setActualIteration                (0);
    }


}


