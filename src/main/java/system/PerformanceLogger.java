package system;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

public class PerformanceLogger implements Runnable {

    private static PerformanceLogger instance = new PerformanceLogger();
    private Instant start;
    private Instant end;
    private Duration duration;
    private static ArrayList<Long> memoryUsages;
    private long startMemory;
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

        long s_hours = (long) Math.floor(simulationTime/3600);
        long s_minutes = (long) Math.floor(simulationTime/60);
        long s_seconds = (long) Math.floor(simulationTime);
        System.out.println("Real time");
        System.out.println(hours + " hours, " + (minutes - 60*hours) + " min, " + (seconds - 60*minutes) + " seconds, " + (millis - 1000*seconds) + " millis");
        System.out.println("AVG RAM usage:  " + (calculateAverage(memoryUsages)) + " MB");
        System.out.println("Simulation time");
        System.out.println(s_hours + " hours, " + (s_minutes - 60*s_hours) + " min, " + (s_seconds - 60*s_minutes) + " seconds");
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
