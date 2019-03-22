package runners.simulation;

import variablesGenerator.InitGenerator;

import java.util.ArrayList;

public class ConfidenceInterval {


    private static ConfidenceInterval instance = new ConfidenceInterval();

    private ConfidenceInterval(){}

    public static ConfidenceInterval getInstance(){
        return instance;
    }

    private double confidence = 0.95;




    /**
     * funzione per il calcolo intervallo di confidenza
     *
     * @param givenNumbers
     * @return int[] - lower, upper
     */
    public double[] compute95percentCI(ArrayList<Double> givenNumbers) {

        double mean = 0.0;
        double variance = 0.0;
        long n = 1;
        double t_student = 0.0;
        double width = 0.0;

        for(Double actualValue : givenNumbers){
            double diff = actualValue - mean;
            variance += (diff * diff * (n-1)/n);
            mean += (diff/n);
            n++;
        }
        double stddev = Math.sqrt(variance);
        if(n>1){
            double u = 1.0 - 0.5*(1-0 - confidence);
            t_student = InitGenerator.getInstance().idfStudent(n-1,u);
            width = (t_student*stddev)/Math.sqrt(n-1);
        }

        double[] confidenceInterval = {mean - width, mean + width};
        findOutliers(mean - width, mean + width,givenNumbers);
        return confidenceInterval;
    }



    private void findOutliers(Double min, Double max, ArrayList<Double> givenNumbers){
        double outliers = 0.0;
        double tot = 1.0;
        for (Double d : givenNumbers){
            if((d< min) || (d > max))
                outliers++;
            tot++;
        }
        System.out.println("Outliers = " + outliers);
        System.out.println("Percentage of outliers = " + outliers/tot);
    }
}
