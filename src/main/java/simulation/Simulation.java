package simulation;


import cloud.Cloud;
import cloudlet.Cloudlet_NEW;
import config.SystemConfiguration;
import event.Event;
import job.Job;
import system.PerformanceLogger;
import variablesGenerator.Arrivals;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Simulation {

    public static void main(String[] args) {
            run();
    }



    public static void run(){
        PerformanceLogger.getInstance().startTest();
        SystemConfiguration.getConfigParams();
        Cloudlet_NEW c = new Cloudlet_NEW(3);
        Cloud cloud = new Cloud();
        StatisticsGenerator statistics = new StatisticsGenerator();

        for(int i = 0; i < SystemConfiguration.ITERATIONS; i++){

            double arrival_time = Arrivals.getInstance().getArrival();
            int job_class = Arrivals.getInstance().determineJobClass();
            Job job = new Job(job_class);
            Event e = new Event(job,arrival_time);
            if(!c.putEvent(e)){
                statistics.increasePacketLoss();
                cloud.processArrivals(e);
            }
            statistics.increaseAllPackets();
            if(i%1000 == 0)
                System.out.println(i);
        }
        System.out.println("ploss = " + statistics.calculatePLoss());
        System.out.println("throughput = " + statistics.getCloudletThroughput());
        System.out.println("Second thoughput = " + statistics.getSecondThroughput(c));
        c.printStatus();
        PerformanceLogger.getInstance().endTest(c.getSimulationTime());
    }



    private static void saveEventsOnCSV(Cloudlet_NEW c){
        File file1 = new File("./events.txt");
        if(file1.delete()) System.out.println("files deleted");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./events.txt"));
            for(Event e : c.getCloudletEventList()) {
                //if(e.getType()==1)
                if(e.getJobEvent().getJobclass()==1)
                    writer.write(String.valueOf(e.getTime()) + ", ");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
