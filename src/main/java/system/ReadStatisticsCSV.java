package system;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ReadStatisticsCSV {

        private static final String SAMPLE_CSV_FILE_PATH = "./LOGS/Response_time.csv";
        private ArrayList<Double> cloudlet_general;

        public void readFromCSV()  {
            cloudlet_general  = new ArrayList<>();
            try (
                    Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                            .withHeader("iterations", "lambda1", "lambda2", "mu1cloudlet", "mu2cloudlet", "mu1cloud", "mu2cloud", "n", "seed", "system general",
                                    "cloud general", "cloudlet general", "system class 1", "cloud class 1", "cloudlet class 1" ,
                                    "system class 2", "cloud class 2", "cloudlet class 2")
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim())
            ) {
                // Accessing values by the names assigned to each column
                for (CSVRecord csvRecord : csvParser) {
                    // cv = csvRecord.get("cloudlet general");
                    //cloudlet_general.add(Double.parseDouble(cv));
                    cloudlet_general.add(Double.valueOf(csvRecord.get("cloudlet general")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<Double> getCloudletGeneral(){
            readFromCSV();
            return cloudlet_general;
        }

    }




