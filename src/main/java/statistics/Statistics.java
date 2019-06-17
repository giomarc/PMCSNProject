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
