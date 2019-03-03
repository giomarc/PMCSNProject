package job;

public class Class1Job extends AbstractJob{

    public Class1Job(double arrival){
        super(arrival, 0.0);
    }

    @Override
    public double getCompletitionTime() {
        return this.getArrival() + this.getService();
    }
}
