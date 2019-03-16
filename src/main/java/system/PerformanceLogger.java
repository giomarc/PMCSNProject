package system;

import cloudlet.Cloudlet;
import runners.simulation.StatisticsGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

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
        System.out.println("AVG RAM usage:  " + (calculateAverage(memoryUsages)) + " MB");
    }

    private long calculateAverage(ArrayList<Long> marks) {
        Long sum = 0L;
        if(!marks.isEmpty()) {
            for (Long mark : marks) {
                sum += mark;
            }
            return (long) (sum.doubleValue() / marks.size());
        }
        return sum;
    }

    public void printInitialConfiguration(){
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
    }

    public void printFinalResults(StatisticsGenerator statistics, double globalTime){
        Printer.getInstance().print("\nP_LOSS", "yellow");
        System.out.println(statistics.calculatePLoss());
        if(SystemConfiguration.VERBOSE) {
            Printer.getInstance().print("\nANALYTIC THROUGHPUT", "yellow");
            System.out.println(statistics.getCloudletThroughput());
            Printer.getInstance().print("\nEMPIRICAL THROUGHPUT", "yellow");
            System.out.println(statistics.getSecondThroughput(globalTime));
            PerformanceLogger.getInstance().endTest(globalTime);
        }
    }

    public void updateProgress(long current, long max) {
        double progressPercentage = ((double)current)/((double)max);
        System.out.print("\r " + (int)(progressPercentage * 100) + "% [");
        for (int i = 0 ; i <= progressPercentage*100; i++) {
            System.out.print(".");
        }
        for (int i = 0 ; i < 100-(progressPercentage*100); i++) {
            System.out.print(" ");
        }
        System.out.print("]");
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
