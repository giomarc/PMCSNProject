package statistics;

import variablesGenerator.InitGenerator;
import java.util.ArrayList;

public class ConfidenceInterval {

    private static ConfidenceInterval instance = null;
    private double confidence = 0.95;


    private ConfidenceInterval(){}

    public static ConfidenceInterval getInstance(){
        if(instance == null)
            instance = new ConfidenceInterval();
        return instance;
    }


    public double[] computeConfidenceInterval(ArrayList<Double> givenNumbers) {

        double sampleMean = 0.0;
        double variance = 0.0;
        double stddev;
        double t_student;
        double width = 0.0;
        long n = 0;

        for(Double actualValue : givenNumbers) {
            n++;
            double diff = actualValue - sampleMean;
            variance += (diff * diff * (n - 1) / n);
            sampleMean += (diff / n);
        }
        stddev = Math.sqrt(variance);

        if(n>1){
            double u = 1.0 - 0.5*(1.0 - confidence);
            t_student = InitGenerator.getInstance().idfStudent(n-1,u);
            width = (t_student*stddev)/Math.sqrt(n-1);
        }
        double[] confidenceInterval = {sampleMean,width};
        return confidenceInterval;
    }


    /**
     * Returns number of outliers respect confidence interval
     */
    private void findOutliers(Double min, Double max, ArrayList<Double> givenNumbers){
        //System.out.println("( " + min + " , " + max + " )");
        double outliers = 0.0;
        double tot = 1.0;
        for (Double d : givenNumbers){
            if((d< min) || (d > max))
                outliers++;
            tot++;
        }
        //System.out.println("percentage of outliers " + outliers/(tot-1));
        //System.out.println("mean: " + ((min+max)/2));

    }
}
