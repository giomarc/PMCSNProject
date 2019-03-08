package variablesGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Services {

    private static InitGenerator init;
    private double serviceRate1;
    private double serviceRate2;

    private static Services instance = new Services();

    private Services(){
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/config.properties");
            prop.load(input);
            serviceRate1 = Double.parseDouble(prop.getProperty("CLOUDLET_M1"));
            serviceRate2 = Double.parseDouble(prop.getProperty("CLOUDLET_M2"));
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

    public static Services getInstance(){
        return instance;
    }

    /**
     * Return service times
     * @param i
     */
    public double getServiceTime(int i){
        double exporate;
        if(i == 1)
             exporate = serviceRate1;
        else
            exporate = serviceRate2;
        return init.exponential(exporate, i);
    }
}
