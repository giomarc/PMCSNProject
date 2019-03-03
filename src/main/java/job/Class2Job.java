package job;

public class Class2Job extends AbstractJob {


    public Class2Job(double arrival){
        super(arrival, 0.0);
    }


    @Override
    public double getCompletitionTime() {
        return this.getArrival() + this.getService();
    }
}
