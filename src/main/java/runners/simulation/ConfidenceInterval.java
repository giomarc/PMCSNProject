package runners.simulation;

import runners.Statistics.Statistics;
import runners.Statistics.TimeStatistics;
import variablesGenerator.InitGenerator;

import java.util.ArrayList;

public class ConfidenceInterval {


    private static ConfidenceInterval instance = new ConfidenceInterval();

    private ConfidenceInterval(){}

    public static ConfidenceInterval getInstance(){
        return instance;
    }

    private double confidence = 0.95;



    public double computeVariance(double valueToUpdate, double valueToInsert, double variance, long n)
    {
        if(n!=0){
            double diff = valueToInsert - valueToUpdate;
            variance += (diff * diff * (n-1)/n);
        }
        return variance;
    }

    /**
     * funzione per il calcolo intervallo di confidenza
     * @return int[] - lower, upper
     */
    /*public double[] computeConfidenceInterval(ArrayList<Double> givenNumbers) {

        double sampleMean = 0.0;
        double variance = 0.0;
        double stddev;
        double t_student;
        double width = 0.0;
        long n = 1;

        for(Double actualValue : givenNumbers){
            double diff = actualValue - sampleMean;
            variance += (diff * diff * (n-1)/n);
            sampleMean += (diff/n);
            n++;
        }
        stddev = Math.sqrt(variance);

        if(n>1){
            double u = 1.0 - 0.5*(1-0 - confidence);
            t_student = InitGenerator.getInstance().idfStudent(n-1,u);
            width = (t_student*stddev)/Math.sqrt(n-1);
        }
        double[] confidenceInterval = {sampleMean - width, sampleMean + width};
        //findOutliers(sampleMean - width, sampleMean + width,givenNumbers);
        return confidenceInterval;
    }*/

    public double[] computeConfidenceInterval(double[] val, int batchsize) {

        double sampleMean = val[0];
        System.out.println("media: " + sampleMean);
        double variance = val[1];
        System.out.println("variance: " + variance);
        double stddev;
        double t_student;
        double width;

        stddev = Math.sqrt(variance);
        System.out.println("stddev " + stddev);


        double u = 1.0 - 0.5*(1-0 - confidence);
        t_student = InitGenerator.getInstance().idfStudent(batchsize-1,u);
        width = (t_student*stddev)/Math.sqrt(batchsize-1);

        double[] confidenceInterval = {sampleMean - width, sampleMean + width};
        return confidenceInterval;
    }



    /*private void findOutliers(Double min, Double max, ArrayList<Double> givenNumbers){
        double outliers = 0.0;
        double tot = 1.0;
        for (Double d : givenNumbers){
            if((d< min) || (d > max))
                outliers++;
            tot++;
        }
        System.out.println("Percentage of outliers = " + outliers/tot);
        System.out.println("Mean Interval: " + ((min + max) /2));
        System.out.println("Cloudlet service time" + TimeStatistics.getInstance().getMeanResponseTimeCloudlet());

    }*/


}
