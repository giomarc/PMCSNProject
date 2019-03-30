package system;

import java.io.FileInputStream;
import java.util.Properties;

public class SystemConfiguration {

    public static final String FILENAME = "src/main/resources/config.properties";
    public static double SETUP_TIME = 0.0;
    public static int N = 0;
    public static int S = 0;
    public static double ARRIVAL_RATE_1 = 0.0;
    public static double ARRIVAL_RATE_2 = 0.0;
    public static double CLOUDLET_M1 = 0.0;
    public static double CLOUDLET_M2 = 0.0;
    public static double CLOUD_M1 = 0.0;
    public static double CLOUD_M2 = 0.0;
    public static double PHASE_P = 0.0;
    public static double START = 0.0;
    public static double STOP = 0.0;
    public static boolean VERBOSE = true;
    //public static int BATCH_SIZE = 256;
    //public static int NUM_BATCH = 64;
    public static boolean MULTI_RUN = false;
    public static long SEED = 0;
    public static long ITERATIONS = 0;
    public static boolean CSVLOGGER = false;
    public static int ALGORITHM = 1;
    public static int THRESHOLD = 14;

    public static void getConfigParams() {
        try {

            Properties prop = new Properties();
            FileInputStream inputStream = new FileInputStream(FILENAME);
            prop.load(inputStream);

            SETUP_TIME = Double.parseDouble(prop.getProperty("SETUP_TIME"));
            ARRIVAL_RATE_1 = Double.parseDouble(prop.getProperty("ARRIVAL_RATE_1"));
            ARRIVAL_RATE_2 = Double.parseDouble(prop.getProperty("ARRIVAL_RATE_2"));
            CLOUDLET_M1 = Double.parseDouble(prop.getProperty("CLOUDLET_M1"));
            CLOUDLET_M2 = Double.parseDouble(prop.getProperty("CLOUDLET_M2"));
            CLOUD_M1 = Double.parseDouble(prop.getProperty("CLOUD_M1"));
            CLOUD_M2 = Double.parseDouble(prop.getProperty("CLOUD_M2"));
            PHASE_P = Double.parseDouble(prop.getProperty("PHASE_P"));
            START = Double.parseDouble(prop.getProperty("START"));
            STOP = Double.parseDouble(prop.getProperty("STOP"));
            //S = Integer.parseInt(prop.getProperty("S"));
            N = Integer.parseInt(prop.getProperty("N"));
            //BATCH_SIZE = Integer.parseInt(prop.getProperty("BATCH_SIZE"));
            //NUM_BATCH = Integer.parseInt(prop.getProperty("NUM_BATCH"));
            MULTI_RUN = Boolean.valueOf(prop.getProperty("MULTI_RUN"));
            SEED = Long.parseLong(prop.getProperty("SEED"));
            ITERATIONS = Long.parseLong((prop.getProperty("ITERATIONS")));
            VERBOSE = Boolean.parseBoolean(prop.getProperty("VERBOSE"));
            CSVLOGGER = Boolean.parseBoolean(prop.getProperty("CSVLOGGER"));
            ALGORITHM = Integer.parseInt(prop.getProperty("ALGORITHM"));
            THRESHOLD = Integer.parseInt(prop.getProperty("THRESHOLD"));

        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
