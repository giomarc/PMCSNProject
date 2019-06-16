package statistics;


public class Statistics {


    private static Statistics instance = null;


    private Statistics(){
    }

    public static Statistics getInstance(){
        if(instance == null)
            instance = new Statistics();
        return instance;
    }


    /**
     * calculateSampleMean VERSION 2
     */
    public double computeMean(double valueToUpdate, double newValue, long n)
    {

        double diff = (newValue -  valueToUpdate);
        double i = (double) n;
        if(i!=0) {
            valueToUpdate += (diff / i);
        }

        return valueToUpdate;
    }


    public double computeWelfordMean(double oldMean, double newValue, long n)
    {
        double diff = (newValue -  oldMean);
        double mean = 0.0;
        if(n!=0){
            mean = oldMean + (diff / n);
        }
        return mean;
    }



}
