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

    public double[] computeMeanAndVariance(double oldVar, double oldMean, double newValue, long n){
        double diff = (newValue -  oldMean);
        double[] MV = new double[2];
        MV[1] = oldVar + diff * diff * (n - 1) / n;
        MV[0] = oldMean + (diff / n);

        return MV;
    }


}
