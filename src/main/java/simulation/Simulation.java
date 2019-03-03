package simulation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import variablesGenerator.InitGenerator;

import java.io.BufferedWriter;
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
        System.out.println(expo.toString());
        System.out.println(pois.toString());

       try{
           BufferedWriter writer1 = Files.newBufferedWriter(Paths.get("./TESTPOISSON.csv"));
           BufferedWriter writer2 = Files.newBufferedWriter(Paths.get("./TESTEXPO.csv"));

           CSVPrinter csvPrinter1 = new CSVPrinter(writer1, CSVFormat.DEFAULT.withHeader("Arrival"));
           CSVPrinter csvPrinter21 = new CSVPrinter(writer2, CSVFormat.DEFAULT.withHeader("Service"));
           csvPrinter1.printRecord(pois);
           csvPrinter21.printRecord(expo);
           csvPrinter1.flush();
           csvPrinter21.flush();
       } catch (IOException e) {
           e.printStackTrace();
       }


    }

}
