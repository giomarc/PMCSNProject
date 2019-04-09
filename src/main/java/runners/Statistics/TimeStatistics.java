package runners.Statistics;

public class TimeStatistics {

    private static TimeStatistics instance = null;
    //RESPONSE TIME
    private double meanResponseTime;
    private double meanResponseTimeCloudlet;
    private double meanResponseTimeCloud;
    private double meanResponseTimeClass1;
    private double meanResponseTimeClass2;
    private double meanResponseTimeClass1Cloudlet;


    private TimeStatistics(){
        resetStatistics();
    }

    public static TimeStatistics getInstance()
    {
        if(instance == null)
            instance = new TimeStatistics();
        return instance;
    }


    public static void setInstance(TimeStatistics instance) {
        TimeStatistics.instance = instance;
    }

    public void setMeanResponseTime(double meanResponseTime) {
        this.meanResponseTime = meanResponseTime;
    }

    public void setMeanResponseTimeCloudlet(double meanResponseTimeCloudlet) {
        this.meanResponseTimeCloudlet = meanResponseTimeCloudlet;
    }

    public void setMeanResponseTimeCloud(double meanResponseTimeCloud) {
        this.meanResponseTimeCloud = meanResponseTimeCloud;
    }

    public void setMeanResponseTimeClass1(double meanResponseTimeClass1) {
        this.meanResponseTimeClass1 = meanResponseTimeClass1;
    }

    public void setMeanResponseTimeClass2(double meanResponseTimeClass2) {
        this.meanResponseTimeClass2 = meanResponseTimeClass2;
    }

    public void setMeanResponseTimeClass1Cloudlet(double meanResponseTimeClass1Cloudlet) {
        this.meanResponseTimeClass1Cloudlet = meanResponseTimeClass1Cloudlet;
    }

    public void setMeanResponseTimeClass2Cloudlet(double meanResponseTimeClass2Cloudlet) {
        this.meanResponseTimeClass2Cloudlet = meanResponseTimeClass2Cloudlet;
    }

    public void setMeanResponseTimeClass1Cloud(double meanResponseTimeClass1Cloud) {
        this.meanResponseTimeClass1Cloud = meanResponseTimeClass1Cloud;
    }

    public void setMeanResponseTimeClass2Cloud(double meanResponseTimeClass2Cloud) {
        this.meanResponseTimeClass2Cloud = meanResponseTimeClass2Cloud;
    }

    private double meanResponseTimeClass2Cloudlet;
    private double meanResponseTimeClass1Cloud;
    private double meanResponseTimeClass2Cloud;



    /**
     * Getters and Setters
     */
    public double getMeanResponseTime() {
        return meanResponseTime;
    }

    public double getMeanResponseTimeCloudlet() {
        return meanResponseTimeCloudlet;
    }

    public double getMeanResponseTimeCloud() {
        return meanResponseTimeCloud;
    }

    public double getMeanResponseTimeClass1() {
        return meanResponseTimeClass1;
    }

    public double getMeanResponseTimeClass2() {
        return meanResponseTimeClass2;
    }

    public double getMeanResponseTimeClass1Cloudlet() {
        return meanResponseTimeClass1Cloudlet;
    }

    public double getMeanResponseTimeClass2Cloudlet() {
        return meanResponseTimeClass2Cloudlet;
    }

    public double getMeanResponseTimeClass1Cloud() {
        return meanResponseTimeClass1Cloud;
    }

    public double getMeanResponseTimeClass2Cloud() {
        return meanResponseTimeClass2Cloud;
    }

    public void resetStatistics(){
        this.meanResponseTime                = 0.0;
        this.meanResponseTimeCloudlet        = 0.0;
        this.meanResponseTimeCloud           = 0.0;
        this.meanResponseTimeClass1          = 0.0;
        this.meanResponseTimeClass2          = 0.0;
        this.meanResponseTimeClass1Cloudlet  = 0.0;
        this.meanResponseTimeClass2Cloudlet  = 0.0;
        this.meanResponseTimeClass1Cloud     = 0.0;
        this.meanResponseTimeClass2Cloud     = 0.0;
    }

}
