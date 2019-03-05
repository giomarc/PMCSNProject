package simulation;

import cloudlet.Cloudlet;
import event.ArrivalEvent;
import event.Event;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import variablesGenerator.Arrivals;
import variablesGenerator.InitGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Simulation {

    //classe che implementerà il metodo run per avviare la simulazione

    /*public static void main(String[] args) {
       InitGenerator init =  InitGenerator.getInstance();
       ArrayList<Double> expo = new ArrayList<Double>();
       ArrayList<Double> pois = new ArrayList<Double>();
       Integer i;
       for(i = 0; i<1000;i++) {
           double poisrate = 25;
           double exporate = 0.25;
           double e = init.exponential(exporate, 0);
           expo.add(e);
           double p = init.poisson(poisrate, 1);
           pois.add(p);
       }

        try {
            File file1 = new File("./expo.txt");
            File file2 = new File("./pois.txt");
            if(file1.delete() && file2.delete()) System.out.println("files deleted");
            BufferedWriter writerexpo = new BufferedWriter(new FileWriter("./expo.txt"));
            BufferedWriter writerpois = new BufferedWriter(new FileWriter("./pois.txt"));
            for (Double j : expo) {
                writerexpo.write(j.toString() + " ");
            }
            for (Double j : pois) {
                writerpois.write(j.toString() + " ");
            }
            writerexpo.flush();
            writerpois.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/

    public static void main(String[] args) {
        Cloudlet c = new Cloudlet(3);
        double packetsloss = 0.0;
        double allpackets = 0.0;
        for(int i = 0; i < 100; i++){
            if(!c.putEvent(new Event(1, Arrivals.getInstance().getArrival()))){
                packetsloss++;
            }
            allpackets ++;
        }
        System.out.println("ploss = " + packetsloss/allpackets);
        c.printStatus();
    }



}
