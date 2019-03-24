package system;

import runners.Statistics.JobStatistics;
import runners.Statistics.TimeStatistics;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class PerformanceLogger implements Runnable {

    private static PerformanceLogger instance = new PerformanceLogger();
    private Instant start;
    private Instant end;
    private Duration duration;
    private static ArrayList<Long> memoryUsages;
    private static boolean stop;
    private Thread son;



    private PerformanceLogger(){}

    public static PerformanceLogger getInstance(){
        return instance;
    }

    public void startTest(){
        //only Java 8
        start = Instant.now();
        stop = false;
        son = new Thread(new PerformanceLogger());
        son.start();
    }

    public void endTest(double simulationTime){
        long memoryUsage = (calculateAverage(memoryUsages));
        stop = true;
        end = Instant.now();
        duration = Duration.between(start, end);
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds();
        long millis = duration.toMillis();

        long s_days = (long) Math.floor(simulationTime/(3600 * 24));
        long s_hours = (long) Math.floor(simulationTime/3600);
        long s_minutes = (long) Math.floor(simulationTime/60);
        long s_seconds = (long) Math.floor(simulationTime);
        Printer.getInstance().print("\nTIME", "yellow");
        Printer.getInstance().print("Real time", "green");
        System.out.println(hours + " hours, " + (minutes - 60*hours) + " min, " + (seconds - 60*minutes) + " seconds, " + (millis - 1000*seconds) + " millis");
        Printer.getInstance().print("Simulation time", "green");
        System.out.println(s_days + " days, " + (s_hours - 24*s_days)+ " hours, " + (s_minutes - 60*s_hours) + " min, " + (s_seconds - 60*s_minutes) + " seconds");
        Printer.getInstance().print("\nSYSTEM", "yellow");
        System.out.println("AVG RAM usage:  " + memoryUsage + " MB");
    }

    private long calculateAverage(ArrayList<Long> marks) {
        if(marks != null) {
            Long sum = 0L;
            if (!marks.isEmpty()) {
                for (Long mark : marks) {
                    sum += mark;
                }
                return (long) (sum.doubleValue() / marks.size());
            }
            return sum;
        }
        else return -1;
    }

    public void printInitialConfiguration(){
        if(SystemConfiguration.VERBOSE) {
            Printer.getInstance().print("Execution started with VERBOSE=true. To reduce logs start again the execution setting VERBOSE=false", "cyan");
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
            startTest();
        }
    }

    public void printFinalResults(JobStatistics js, TimeStatistics ts){
        System.out.print("\r100%");
        Printer.getInstance().print("\n\nCLOUDLET P_LOSS", "yellow");
        System.out.println(js.calculatePLoss());
        if(SystemConfiguration.VERBOSE) {
            Printer.getInstance().print("\nTHROUGHPUT", "yellow");
            Printer.getInstance().print("Analytic cloudlet throughput", "green");
            System.out.println(js.getAnalyticCloudletThroughput());
            Printer.getInstance().print("Empirical cloudlet throughput", "green");
            System.out.println(js.getEmpiricCloudletThroughput());
            Printer.getInstance().print("Analytic cloud throughput", "green");
            System.out.println(js.getAnalyticCloudThroughput());
            Printer.getInstance().print("Empirical cloud throughput", "green");
            System.out.println(js.getEmpiricCloudThroughput());

            Printer.getInstance().print("\nCOMPLETIONS", "yellow");
            Printer.getInstance().print("Job completed", "green");
            System.out.println(((int) js.getAllpackets()));
            Printer.getInstance().print("Job class 1 completed", "green");
            System.out.println(((int) js.getPacket1()));
            Printer.getInstance().print("Job class 2 completed", "green");
            System.out.println(((int) js.getPacket2()));

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

            Printer.getInstance().print("\nAVG POPULATION", "yellow");
            Printer.getInstance().print("Mean global population", "green");
            System.out.println(js.getMeanGlobalPopulation());
            Printer.getInstance().print("Mean Cloudlet population", "green");
            System.out.println(js.getMeanCloudletPopulation());
            Printer.getInstance().print("Mean Cloud population", "green");
            System.out.println(js.getMeanCloudPopulation());
            Printer.getInstance().print("Mean class 1 population", "green");
            System.out.println(js.getMeanGlobalPopulationClass1());
            Printer.getInstance().print("Mean class 2 population", "green");
            System.out.println(js.getMeanGlobalPopulationClass2());
            Printer.getInstance().print("Mean cloudlet class 1 population", "green");
            System.out.println(js.getMeanCloudletPopulationClass1());
            Printer.getInstance().print("Mean cloudlet class 2 population", "green");
            System.out.println(js.getMeanCloudletPopulationClass2());
            Printer.getInstance().print("Mean cloud class 1 population", "green");
            System.out.println(js.getMeanCloudPopulationClass1());
            Printer.getInstance().print("Mean cloud class 2 population", "green");
            System.out.println(js.getMeanCloudPopulationClass2());

            PerformanceLogger.getInstance().endTest(js.getGlobalTime());

            CSVlogger.getInstance().writeOnFiles(ts);
        }
    }

    public void updateProgress(long current, long max) {
        if(current%10000 == 0) {
            double progressPercentage = ((double) current) / ((double) max);
            System.out.print("\r" + (int) (progressPercentage * 100) + "%");
        }
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
