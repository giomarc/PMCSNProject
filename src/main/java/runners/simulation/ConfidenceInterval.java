package runners.simulation;

import java.util.ArrayList;

public class ConfidenceInterval {


    private static ConfidenceInterval instance = new ConfidenceInterval();

    private ConfidenceInterval(){}

    public ConfidenceInterval getInstance(){
        return instance;
    }




    /**
     * funzione per il calcolo intervallo di confidenza
     *
     * @param givenNumbers
     * @return int[] - lower, upper
     */
    private static double[] compute95percentCI(ArrayList<Double> givenNumbers) {

        // calculate the mean value (= average)
        double sum = 0.0;
        for (double num : givenNumbers) {
            sum += num;
        }
        double mean = sum / givenNumbers.size();

        // calculate standard deviation
        double squaredDifferenceSum = 0.0;
        for (double num : givenNumbers) {
            squaredDifferenceSum += (num - mean) * (num - mean);
        }
        double variance = squaredDifferenceSum / givenNumbers.size();
        double standardDeviation = Math.sqrt(variance);

        // value for 95% confidence interval, source: https://en.wikipedia.org/wiki/Confidence_interval#Basic_Steps
        double confidenceLevel = 1.96;
        double temp = confidenceLevel * standardDeviation / Math.sqrt(givenNumbers.size());
        return new double[]{mean - temp, mean + temp};
    }
}
