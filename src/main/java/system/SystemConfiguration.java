package system;

import java.io.FileInputStream;
import java.util.Properties;

public class SystemConfiguration {


    public static final String FILENAME = "src/main/resources/config.properties";
    public static int N = 0;
    public static double ARRIVAL_RATE_1 = 0.0;
    public static double ARRIVAL_RATE_2 = 0.0;
    public static double CLOUDLET_M1 = 0.0;
    public static double CLOUDLET_M2 = 0.0;
    public static double CLOUD_M1 = 0.0;
    public static double CLOUD_M2 = 0.0;
    public static double PHASE_P = 0.0;
    public static boolean VERBOSE = true;
    public static boolean MULTI_RUN = false;
    public static long SEED = 0;
    public static long ITERATIONS = 0;
    public static boolean CSVLOGGER = false;
    public static int ALGORITHM = 1;
    public static int THRESHOLD = 14;
    public static int RUNS = 1;
    public static boolean HYPEREXPO_ENABLED = true;
    public static boolean OPERATIONS_ENABLED = true;
    public static boolean BATCH = false;
    public static int NUM_BATCH;


    /**
     * Read config params from config.properties file and parse parameters
     */
    public static void getConfigParams() {
        try {

            Properties prop = new Properties();
            FileInputStream inputStream = new FileInputStream(FILENAME);
            prop.load(inputStream);

            ARRIVAL_RATE_1 = Double.parseDouble(prop.getProperty("ARRIVAL_RATE_1"));
            ARRIVAL_RATE_2 = Double.parseDouble(prop.getProperty("ARRIVAL_RATE_2"));
            CLOUDLET_M1 = Double.parseDouble(prop.getProperty("CLOUDLET_M1"));
            CLOUDLET_M2 = Double.parseDouble(prop.getProperty("CLOUDLET_M2"));
            CLOUD_M1 = Double.parseDouble(prop.getProperty("CLOUD_M1"));
            CLOUD_M2 = Double.parseDouble(prop.getProperty("CLOUD_M2"));
            PHASE_P = Double.parseDouble(prop.getProperty("PHASE_P"));
            N = Integer.parseInt(prop.getProperty("N"));
            RUNS = Integer.parseInt(prop.getProperty("RUNS"));
            NUM_BATCH = Integer.parseInt(prop.getProperty("NUM_BATCH"));
            MULTI_RUN = Boolean.valueOf(prop.getProperty("MULTI_RUN"));
            SEED = Long.parseLong(prop.getProperty("SEED"));
            ITERATIONS = Long.parseLong((prop.getProperty("ITERATIONS")));
            VERBOSE = Boolean.parseBoolean(prop.getProperty("VERBOSE"));
            CSVLOGGER = Boolean.parseBoolean(prop.getProperty("CSVLOGGER"));
            ALGORITHM = Integer.parseInt(prop.getProperty("ALGORITHM"));
            THRESHOLD = Integer.parseInt(prop.getProperty("THRESHOLD"));
            HYPEREXPO_ENABLED = Boolean.parseBoolean(prop.getProperty("HYPEREXPO_ENABLED"));
            OPERATIONS_ENABLED = Boolean.parseBoolean(prop.getProperty("OPERATIONS_ENABLED"));
            BATCH = Boolean.parseBoolean(prop.getProperty("BATCH"));

        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
