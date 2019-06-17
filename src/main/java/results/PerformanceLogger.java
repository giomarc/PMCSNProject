package results;

import statistics.BatchMeans;
import statistics.JobStatistics;
import statistics.TimeStatistics;
import system.SystemConfiguration;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class PerformanceLogger implements Runnable {

    private static PerformanceLogger instance = new PerformanceLogger();
    private Instant start;
    private Instant end;
    private Duration duration;
    private long realTimeInSeconds;
    private long simulationTimeInSeconds;
    private static ArrayList<Long> memoryUsages;
    private static boolean stop;
    private Thread son;



    private PerformanceLogger(){}

    public static PerformanceLogger getInstance(){
        return instance;
    }

    private void startTest(){
        start = Instant.now();
        stop = false;
        son = new Thread(new PerformanceLogger());
        son.start();
    }

    private void endTest(double simulationTime){
        long memoryUsage = (calculateAverage(memoryUsages));
        stop = true;
        end = Instant.now();
        duration = Duration.between(start, end);
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds();
        long millis = duration.toMillis();
        realTimeInSeconds = seconds;

        long s_days = (long) Math.floor(simulationTime/(3600 * 24));
        long s_hours = (long) Math.floor(simulationTime/3600);
        long s_minutes = (long) Math.floor(simulationTime/60);
        long s_seconds = (long) Math.floor(simulationTime);
        simulationTimeInSeconds = s_seconds;

        Printer.getInstance().print("\nTIME", "yellow");
        Printer.getInstance().print("Real time", "green");
        System.out.println(hours + " hours, " + (minutes - 60*hours) + " min, " + (seconds - 60*minutes) + " seconds, " + (millis - 1000*seconds) + " millis");
        Printer.getInstance().print("Simulation time", "green");
        System.out.println(s_days + " days, " + (s_hours - 24*s_days)+ " hours, " + (s_minutes - 60*s_hours) + " min, " + (s_seconds - 60*s_minutes) + " seconds");
        Printer.getInstance().print("\nSYSTEM", "yellow");
        System.out.println("AVG RAM usage:  " + memoryUsage + " MB");
    }



    private long calculateAverage(ArrayList<Long> marks) {
        if(!SystemConfiguration.MULTI_RUN) {
            if (marks != null) {
                Long sum = 0L;
                if (!marks.isEmpty()) {
                    for (Long mark : marks) {
                        sum += mark;
                    }
                    return (long) (sum.doubleValue() / marks.size());
                }
                return sum;
            } else return -1;
        }
        else
            return -1;
    }

    public void printInitialConfiguration(){
        if(!SystemConfiguration.MULTI_RUN) {
            if (SystemConfiguration.CSVLOGGER)
                Printer.getInstance().print("Execution started with CSVLOGGER=true. To avoid writing results on CSV start again the execution setting CSVLOGGER=false", "cyan");
            if (SystemConfiguration.VERBOSE) {
                Printer.getInstance().print("Execution started with VERBOSE=true. To reduce logs start again the execution setting VERBOSE=false", "cyan");
                printSystemConfiguration();
            }
        }
        startTest();
    }

    public void printFinalResults(JobStatistics js, TimeStatistics ts, BatchMeans bm){
        System.out.print("\r100%");
        Printer.getInstance().print("\n\nCLOUDLET P_LOSS", "yellow");
        System.out.println(js.calculatePLoss());
        if(SystemConfiguration.VERBOSE && !SystemConfiguration.MULTI_RUN) {
            printThroughputResults(js);
            printCompletedJobs(js);
            printResponseTime(ts);
            if(SystemConfiguration.BATCH) {
                printMeanBatchPopulation(bm);
                printThroughputBatch(bm);
                printResponseTimeBatch(bm);
            }else{
                printMeanPopulation(js);
            }
        }
        PerformanceLogger.getInstance().endTest(js.getGlobalTime());
        CSVlogger.getInstance().writeOnFiles(js.getGlobalTime());
    }


    private void printSystemConfiguration(){
        Printer.getInstance().print("\nARRIVAL RATES", "yellow");
        System.out.println("lambda_1 = " + SystemConfiguration.ARRIVAL_RATE_1);
        System.out.println("lambda_2 = " + SystemConfiguration.ARRIVAL_RATE_2);
        Printer.getInstance().print("\nSERVICE RATES", "yellow");
        Printer.getInstance().print("Cloudlet", "green");
        System.out.println("mu_1 = " + SystemConfiguration.CLOUDLET_M1);
        System.out.println("mu_2 = " + SystemConfiguration.CLOUDLET_M2);
        Printer.getInstance().print("Cloud", "green");
        System.out.println("mu_1 = " + SystemConfiguration.CLOUD_M1);
        System.out.println("mu_2 = " + SystemConfiguration.CLOUD_M2);
        Printer.getInstance().print("\n# CLOUDLET", "yellow");
        System.out.println(SystemConfiguration.N);
        Printer.getInstance().print("\nSEED", "yellow");
        System.out.println(SystemConfiguration.SEED);
        Printer.getInstance().print("\nCompletion Percentage:", "blue");
    }

    private void printThroughputResults(JobStatistics js){
        //THROUGHPUT GLOBALI
        Printer.getInstance().print("\nTHROUGHPUT", "yellow");
        Printer.getInstance().print("System throughput", "green");
        System.out.println(js.getSystemThroughput());
        Printer.getInstance().print("System class 1 throughput", "green");
        System.out.println(js.getSystemClass1Throughput());
        Printer.getInstance().print("System class 2 throughput", "green");
        System.out.println(js.getSystemClass2Throughput());
        Printer.getInstance().print("Cloudlet throughput", "green");
        System.out.println(js.getCloudletThroughput());
        Printer.getInstance().print("Cloud throughput", "green");
        System.out.println(js.getCloudThroughput());

        //THROUGHPUT PER-CLASS CLOUDLET
        Printer.getInstance().print("Cloudlet class 1 throughput", "green");
        System.out.println(js.getCloudletClass1Throughput());
        Printer.getInstance().print("Cloudlet class 2 throughput", "green");
        System.out.println(js.getCloudletClass2Throughput());

        //THROUGHPUT PER-CLASS CLOUD
        Printer.getInstance().print("Cloud class 1 throughput", "green");
        System.out.println(js.getCloudClass1Throughput());
        Printer.getInstance().print("Cloud class 2 throughput", "green");
        System.out.println(js.getCloudClass2Throughput());
    }

    private void printCompletedJobs(JobStatistics js) {
        Printer.getInstance().print("\nCOMPLETIONS", "yellow");
        Printer.getInstance().print("Job completed", "green");
        System.out.println(((int) js.getPackets(0)));
        Printer.getInstance().print("Job class 1 completed", "green");
        System.out.println(((int) js.getPackets(1)));
        Printer.getInstance().print("Job class 2 completed", "green");
        System.out.println(((int) js.getPackets(2)));
        Printer.getInstance().print("Cloudlet class 1 completed", "green");
        System.out.println(((int) js.getCompletedCloudlet(1)));
        Printer.getInstance().print("Cloudlet class 2 completed", "green");
        System.out.println(((int) js.getCompletedCloudlet(2)));
        Printer.getInstance().print("Cloud class 1 completed", "green");
        System.out.println(((int) js.getCompletedCloud(1)));
        Printer.getInstance().print("Cloud class 2 completed", "green");
        System.out.println(((int) js.getCompletedCloud(2)));
        Printer.getInstance().print("Cloudlet completed", "green");
        System.out.println(((int) js.getCompletedCloudlet(0)));
        Printer.getInstance().print("Cloud completed", "green");
        System.out.println(((int) js.getCompletedCloud(0)));
    }

    private void printResponseTime(TimeStatistics ts){
        Printer.getInstance().print("\nSERVICE TIME", "yellow");
        Printer.getInstance().print("Mean global response time", "green");
        System.out.println(ts.getMeanResponseTime());
        Printer.getInstance().print("Mean Cloudlet response time", "green");
        System.out.println(ts.getMeanResponseTimeCloudlet());
        Printer.getInstance().print("Mean Cloud response time", "green");
        System.out.println(ts.getMeanResponseTimeCloud());
        Printer.getInstance().print("Mean class 1 response time", "green");
        System.out.println(ts.getMeanResponseTimeClass1());
        Printer.getInstance().print("Mean class 2 response time", "green");
        System.out.println(ts.getMeanResponseTimeClass2());
        Printer.getInstance().print("Mean cloudlet class 1 response time", "green");
        System.out.println(ts.getMeanResponseTimeClass1Cloudlet());
        Printer.getInstance().print("Mean cloudlet class 2 response time", "green");
        System.out.println(ts.getMeanResponseTimeClass2Cloudlet());
        Printer.getInstance().print("Mean cloud class 1 response time", "green");
        System.out.println(ts.getMeanResponseTimeClass1Cloud());
        Printer.getInstance().print("Mean cloud class 2 response time", "green");
        System.out.println(ts.getMeanResponseTimeClass2Cloud());
    }



    private void printMeanBatchPopulation(BatchMeans js){
        Printer.getInstance().print("\nAVG POPULATION", "yellow");
        Printer.getInstance().print("Mean global population", "green");
        System.out.println(js.getMeanBMGlobalPopulation(0)[0] + " \u00B1 " + js.getMeanBMGlobalPopulation(0)[1]);
        Printer.getInstance().print("Mean Cloudlet population", "green");
        System.out.println(js.getMeanBMCloudletPopulation(0)[0] + " \u00B1 " +  js.getMeanBMCloudletPopulation(0)[1]);
        Printer.getInstance().print("Mean Cloud population", "green");
        System.out.println(js.getMeanBMCloudPopulation(0)[0] + " \u00B1 " + js.getMeanBMCloudPopulation(0)[1]);
        Printer.getInstance().print("Mean class 1 population", "green");
        System.out.println(js.getMeanBMGlobalPopulation(1)[0] + " \u00B1 " +  js.getMeanBMGlobalPopulation(1)[1]);
        Printer.getInstance().print("Mean class 2 population", "green");
        System.out.println(js.getMeanBMGlobalPopulation(2)[0] + " \u00B1 " +  js.getMeanBMGlobalPopulation(2)[1]);
        Printer.getInstance().print("Mean cloudlet class 1 population", "green");
        System.out.println(js.getMeanBMCloudletPopulation(1)[0] + " \u00B1 " +  js.getMeanBMCloudletPopulation(1)[1] );
        Printer.getInstance().print("Mean cloudlet class 2 population", "green");
        System.out.println(js.getMeanBMCloudletPopulation(2)[0] + " \u00B1 " +  js.getMeanBMCloudletPopulation(2)[1]);
        Printer.getInstance().print("Mean cloud class 1 population", "green");
        System.out.println(js.getMeanBMCloudPopulation(1)[0] + " \u00B1 " +  js.getMeanBMCloudPopulation(1)[1]);
        Printer.getInstance().print("Mean cloud class 2 population", "green");
        System.out.println(js.getMeanBMCloudPopulation(2)[0] + " \u00B1 " +  js.getMeanBMCloudPopulation(2)[1] );
    }



    //THROUGHPUT BATCH PRINTSSSSSSSSS

    private void printThroughputBatch(BatchMeans bm){
        System.out.println("\n");
        Printer.getInstance().print("Throughput System batch", "green");
        System.out.println(bm.getBMThroughput("sys",0)[0] + " \u00B1 " + bm.getBMThroughput("sys",0)[1]);
        Printer.getInstance().print("Throughput System Class 1 batch", "green");
        System.out.println(bm.getBMThroughput("sys",1)[0] + " \u00B1 " + bm.getBMThroughput("sys",1)[1]);
        Printer.getInstance().print("Throughput System Class 2batch", "green");
        System.out.println(bm.getBMThroughput("sys",2)[0] + " \u00B1 " + bm.getBMThroughput("sys",2)[1]);

        Printer.getInstance().print("Throughput Cloudlet batch", "green");
        System.out.println(bm.getBMThroughput("clet",0)[0] + " \u00B1 " + bm.getBMThroughput("clet",0)[1]);
        Printer.getInstance().print("Throughput Cloudlet class 1 batch", "green");
        System.out.println(bm.getBMThroughput("clet",1)[0] + " \u00B1 " + bm.getBMThroughput("clet",1)[1]);
        Printer.getInstance().print("Throughput Cloudlet class 2 batch ", "green");
        System.out.println(bm.getBMThroughput("clet",2)[0] + " \u00B1 " + bm.getBMThroughput("clet",2)[1]);

        Printer.getInstance().print("Throughput Cloud batch", "green");
        System.out.println(bm.getBMThroughput("cloud",0)[0] + " \u00B1 " + bm.getBMThroughput("cloud",0)[1]);
        Printer.getInstance().print("Throughput Cloud class 1 batch", "green");
        System.out.println(bm.getBMThroughput("cloud",1)[0] + " \u00B1 " + bm.getBMThroughput("cloud",1)[1]);
        Printer.getInstance().print("Throughput Cloud class 2 batch", "green");
        System.out.println(bm.getBMThroughput("cloud",2)[0] + " \u00B1 " + bm.getBMThroughput("cloud",2)[1]);


    }



    //RESPONSE TIME BATCH PRINTS
    private void printResponseTimeBatch(BatchMeans bm){
        System.out.println("\n");
        Printer.getInstance().print("Response Time System batch ", "green");
        System.out.println(bm.getBMTime("sys",0)[0] + " \u00B1 " + bm.getBMTime("sys",0)[1]);
        Printer.getInstance().print("Response Time System Class 1 batch", "green");
        System.out.println(bm.getBMTime("sys",1)[0] + " \u00B1 " + bm.getBMTime("sys",1)[1]);
        Printer.getInstance().print("Response Time System Class 2 batch", "green");
        System.out.println(bm.getBMTime("sys",2)[0] + " \u00B1 " + bm.getBMTime("sys",2)[1]);

        Printer.getInstance().print("Response Time Cloudlet batch ", "green");
        System.out.println(bm.getBMTime("clet",0)[0] + " \u00B1 " + bm.getBMTime("clet",0)[1]);
        Printer.getInstance().print("Response Time Cloudlet class 1 batch", "green");
        System.out.println(bm.getBMTime("clet",1)[0] + " \u00B1 " + bm.getBMTime("clet",1)[1]);
        Printer.getInstance().print("Response Time Cloudlet class 2 batch", "green");
        System.out.println(bm.getBMTime("clet",2)[0] + " \u00B1 " + bm.getBMTime("clet",2)[1]);

        Printer.getInstance().print("Response Time Cloud batch ", "green");
        System.out.println(bm.getBMTime("cloud",0)[0] + " \u00B1 " + bm.getBMTime("cloud",0)[1]);
        Printer.getInstance().print("Response Time Cloud class 1 batch", "green");
        System.out.println(bm.getBMTime("cloud",1)[0] + " \u00B1 " + bm.getBMTime("cloud",1)[1]);
        Printer.getInstance().print("Response Time Cloud class 2 ", "green");
        System.out.println(bm.getBMTime("cloud",2)[0] + " \u00B1 " + bm.getBMTime("cloud",2)[1]);


    }


    private void printMeanPopulation(JobStatistics js){
        Printer.getInstance().print("\nAVG POPULATION", "yellow");
        Printer.getInstance().print("Mean global population", "green");
        System.out.println(js.getMeanGlobalPopulation(0));
        Printer.getInstance().print("Mean Cloudlet population", "green");
        System.out.println(js.getMeanCloudletPopulation(0));
        Printer.getInstance().print("Mean Cloud population", "green");
        System.out.println(js.getMeanCloudPopulation(0));
        Printer.getInstance().print("Mean class 1 population", "green");
        System.out.println(js.getMeanGlobalPopulation(1));
        Printer.getInstance().print("Mean class 2 population", "green");
        System.out.println(js.getMeanGlobalPopulation(2));
        Printer.getInstance().print("Mean cloudlet class 1 population", "green");
        System.out.println(js.getMeanCloudletPopulation(1));
        Printer.getInstance().print("Mean cloudlet class 2 population", "green");
        System.out.println(js.getMeanCloudletPopulation(2));
        Printer.getInstance().print("Mean cloud class 1 population", "green");
        System.out.println(js.getMeanCloudPopulation(1));
        Printer.getInstance().print("Mean cloud class 2 population", "green");
        System.out.println(js.getMeanCloudPopulation(2));
    }



    public void updateProgress(long current, long max) {
        if(current%10000 == 0) {
            double progressPercentage = ((double) current) / ((double) max);
            System.out.print("\r" + (int) (progressPercentage * 100) + "%");
        }
    }

    long getRealTime() {
        return realTimeInSeconds;
    }

    long getSimulationTime() {
        return simulationTimeInSeconds;
    }

    double getRam(){
        return (calculateAverage(memoryUsages));
    }

    @Override
    public void run() {
        Runtime rt = Runtime.getRuntime();
        int mb = 1024 * 1024;
        memoryUsages = new ArrayList<>();
        while(!stop){
            try {
                Thread.sleep(1000);
                memoryUsages.add((rt.totalMemory() - rt.freeMemory()) / mb);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
