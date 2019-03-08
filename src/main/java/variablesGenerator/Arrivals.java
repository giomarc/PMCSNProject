package variablesGenerator;

import config.SystemConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Arrivals {

    private static InitGenerator init;
    private double arrivalRate1;
    private double arrivalRate2;

    private static Arrivals instance = new Arrivals();

    private Arrivals(){
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/config.properties");
            prop.load(input);
            arrivalRate1 = Double.parseDouble(prop.getProperty("ARRIVAL_RATE_1"));
            arrivalRate2 = Double.parseDouble(prop.getProperty("ARRIVAL_RATE_2"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        init =  InitGenerator.getInstance();
    }

    public static Arrivals getInstance(){
        return instance;
    }


    /**
     * Return arrival times
     */
    public double getArrival(int i){
        double exporate = 0;
        if(i == 1)
            exporate = arrivalRate1;
        else if (i == 2)
            exporate = arrivalRate2;
        else{
            System.out.println("packet not recognized");
            System.exit(-1);
        }
        return init.exponential(exporate, 0);
    }
}

