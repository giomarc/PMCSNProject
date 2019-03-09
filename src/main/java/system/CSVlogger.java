package system;

import java.io.*;

public class CSVlogger {

    private static CSVlogger instance = new CSVlogger();

    private CSVlogger(){}

    public static CSVlogger getInstance(){
        return instance;
    }

    public void createFileIfNotExists(String fileResponseTime, String fileThroughput, String fileAVGjobs){
        try {
            File directory = new File("./LOGS/");
            if (! directory.exists()){
                directory.mkdir();
            }
            BufferedWriter outRT = new BufferedWriter(new FileWriter("./LOGS/" + fileResponseTime, true));
            BufferedWriter outX = new BufferedWriter(new FileWriter("./LOGS/" + fileThroughput, true));
            BufferedWriter outEN = new BufferedWriter(new FileWriter("./LOGS/" + fileAVGjobs, true));


            File fileRT = new File("./LOGS/" + fileResponseTime);
            File fileX = new File("./LOGS/" + fileThroughput);
            File fileEN = new File("./LOGS/" + fileAVGjobs);
            if (fileRT.createNewFile()) {
                System.out.println("File Response Time has been created.");
                outRT.write("system general, cloud general, cloudlet general, " +
                        "system class 1, cloud class 1, cloudlet class 1 , " +
                        "system class 2, cloud class 2, cloudlet class 2");
                outRT.flush();
            }
            if (fileX.createNewFile()) {
                System.out.println("File Throughput has been created.");
                outX.write("system general, cloud general, cloudlet general, " +
                        "system class 1, cloud class 1, cloudlet class 1 , " +
                        "system class 2, cloud class 2, cloudlet class 2");
                outX.flush();
            }
            if (fileEN.createNewFile()) {
                System.out.println("File AVG jobs has been created.");
                outEN.write("system general, cloud general, cloudlet general, " +
                        "system class 1, cloud class 1, cloudlet class 1 , " +
                        "system class 2, cloud class 2, cloudlet class 2");
                outEN.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
