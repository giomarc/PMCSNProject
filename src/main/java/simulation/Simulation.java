package simulation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import variablesGenerator.InitGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Simulation {

    //classe che implementerà il metodo run per avviare la simulazione

    public static void main(String[] args) {
       InitGenerator init =  InitGenerator.getInstance();
       ArrayList<Double> expo = new ArrayList<Double>();
       ArrayList<Double> pois = new ArrayList<Double>();
       Integer i;
       for(i = 0; i<100;i++) {
           double e = init.exponential(0.25, 0);
           expo.add(e);
           double p = init.poisson(6.0, 1);
           pois.add(p);
       }

        try {
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


    }

}
