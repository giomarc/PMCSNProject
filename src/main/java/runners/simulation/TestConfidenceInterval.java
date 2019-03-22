package runners.simulation;

import system.ReadStatisticsCSV;
import system.SystemConfiguration;

import java.util.ArrayList;

public class TestConfidenceInterval {


    public static void main(String[] args) {

        SystemConfiguration.getConfigParams();
        ReadStatisticsCSV rdc = new ReadStatisticsCSV();
        ArrayList<Double> cg = rdc.getCloudletGeneral();
        double[] result = ConfidenceInterval.getInstance().compute95percentCI(cg);

        System.out.println("[ " + result[0] + " , " + result[1] + " ]");
    }
}
