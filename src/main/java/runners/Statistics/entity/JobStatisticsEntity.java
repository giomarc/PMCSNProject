package runners.Statistics.entity;

public class JobStatisticsEntity {

    private static JobStatisticsEntity instance = new JobStatisticsEntity();

    private JobStatisticsEntity(){}

    public static JobStatisticsEntity getInstance(){
        return instance;
    }

    private double globalTime;

    //POPULATION COUNTERS
    private long completedCloudletClass1;
    private long completedCloudletClass2;
    private long completedCloudClass1;
    private long completedCloudClass2;


    //POPULATION MEANS
    private double meanGlobalPopulation;
    private double meanCloudletPopulation;
    private double meanCloudPopulation;
    private double meanGlobalPopulationClass1;
    private double meanCloudletPopulationClass1;
    private double meanCloudPopulationClass1;
    private double meanGlobalPopulationClass2;
    private double meanCloudletPopulationClass2;
    private double meanCloudPopulationClass2;

    //POPULATION VARIANCE
    private double varGlobalPopulation;
    private double varCloudletPopulation;
    private double varCloudPopulation;
    private double varGlobalPopulationClass1;
    private double varCloudletPopulationClass1;
    private double varCloudPopulationClass1;
    private double varGlobalPopulationClass2;
    private double varCloudletPopulationClass2;
    private double varCloudPopulationClass2;


    //ITERATIONS
    private long cloudletIterationClass1;
    private long cloudletIterationClass2;
    private long cloudIterationClass1;
    private long cloudIterationClass2;
    private long globalIteration;
    private long actualIteration;



    //POPULATION COUNTERS GETTER & SETTER
    public long getCompletedCloudletClass1() {
        return completedCloudletClass1;
    }

    public void setCompletedCloudletClass1(long completedCloudletClass1) {
        this.completedCloudletClass1 = completedCloudletClass1;
    }

    public long getCompletedCloudletClass2() {
        return completedCloudletClass2;
    }

    public void setCompletedCloudletClass2(long completedCloudletClass2) {
        this.completedCloudletClass2 = completedCloudletClass2;
    }

    public long getCompletedCloudClass1() {
        return completedCloudClass1;
    }

    public void setCompletedCloudClass1(long completedCloudClass1) {
        this.completedCloudClass1 = completedCloudClass1;
    }

    public long getCompletedCloudClass2() {
        return completedCloudClass2;
    }

    public void setCompletedCloudClass2(long completedCloudClass2) {
        this.completedCloudClass2 = completedCloudClass2;
    }


    //POPULATION MEANS GETTER & SETTER
    public double getMeanGlobalPopulation() {
        return meanGlobalPopulation;
    }

    public void setMeanGlobalPopulation(double meanGlobalPopulation) {
        this.meanGlobalPopulation = meanGlobalPopulation;
    }

    public double getMeanCloudletPopulation() {
        return meanCloudletPopulation;
    }

    public void setMeanCloudletPopulation(double meanCloudletPopulation) {
        this.meanCloudletPopulation = meanCloudletPopulation;
    }

    public double getMeanCloudPopulation() {
        return meanCloudPopulation;
    }

    public void setMeanCloudPopulation(double meanCloudPopulation) {
        this.meanCloudPopulation = meanCloudPopulation;
    }

    public double getMeanGlobalPopulationClass1() {
        return meanGlobalPopulationClass1;
    }

    public void setMeanGlobalPopulationClass1(double meanGlobalPopulationClass1) {
        this.meanGlobalPopulationClass1 = meanGlobalPopulationClass1;
    }

    public double getMeanCloudletPopulationClass1() {
        return meanCloudletPopulationClass1;
    }

    public void setMeanCloudletPopulationClass1(double meanCloudletPopulationClass1) {
        this.meanCloudletPopulationClass1 = meanCloudletPopulationClass1;
    }

    public double getMeanCloudPopulationClass1() {
        return meanCloudPopulationClass1;
    }

    public void setMeanCloudPopulationClass1(double meanCloudPopulationClass1) {
        this.meanCloudPopulationClass1 = meanCloudPopulationClass1;
    }

    public double getMeanGlobalPopulationClass2() {
        return meanGlobalPopulationClass2;
    }

    public void setMeanGlobalPopulationClass2(double meanGlobalPopulationClass2) {
        this.meanGlobalPopulationClass2 = meanGlobalPopulationClass2;
    }

    public double getMeanCloudletPopulationClass2() {
        return meanCloudletPopulationClass2;
    }

    public void setMeanCloudletPopulationClass2(double meanCloudletPopulationClass2) {
        this.meanCloudletPopulationClass2 = meanCloudletPopulationClass2;
    }

    public double getMeanCloudPopulationClass2() {
        return meanCloudPopulationClass2;
    }

    public void setMeanCloudPopulationClass2(double meanCloudPopulationClass2) {
        this.meanCloudPopulationClass2 = meanCloudPopulationClass2;
    }


    //POPULATION VARIANCE GETTER & SETTER
    public double getVarGlobalPopulation() {
        return varGlobalPopulation;
    }

    public void setVarGlobalPopulation(double varGlobalPopulation) {
        this.varGlobalPopulation = varGlobalPopulation;
    }

    public double getVarCloudletPopulation() {
        return varCloudletPopulation;
    }

    public void setVarCloudletPopulation(double varCloudletPopulation) {
        this.varCloudletPopulation = varCloudletPopulation;
    }

    public double getVarCloudPopulation() {
        return varCloudPopulation;
    }

    public void setVarCloudPopulation(double varCloudPopulation) {
        this.varCloudPopulation = varCloudPopulation;
    }

    public double getVarGlobalPopulationClass1() {
        return varGlobalPopulationClass1;
    }

    public void setVarGlobalPopulationClass1(double varGlobalPopulationClass1) {
        this.varGlobalPopulationClass1 = varGlobalPopulationClass1;
    }

    public double getVarCloudletPopulationClass1() {
        return varCloudletPopulationClass1;
    }

    public void setVarCloudletPopulationClass1(double varCloudletPopulationClass1) {
        this.varCloudletPopulationClass1 = varCloudletPopulationClass1;
    }

    public double getVarCloudPopulationClass1() {
        return varCloudPopulationClass1;
    }

    public void setVarCloudPopulationClass1(double varCloudPopulationClass1) {
        this.varCloudPopulationClass1 = varCloudPopulationClass1;
    }

    public double getVarGlobalPopulationClass2() {
        return varGlobalPopulationClass2;
    }

    public void setVarGlobalPopulationClass2(double varGlobalPopulationClass2) {
        this.varGlobalPopulationClass2 = varGlobalPopulationClass2;
    }

    public double getVarCloudletPopulationClass2() {
        return varCloudletPopulationClass2;
    }

    public void setVarCloudletPopulationClass2(double varCloudletPopulationClass2) {
        this.varCloudletPopulationClass2 = varCloudletPopulationClass2;
    }

    public double getVarCloudPopulationClass2() {
        return varCloudPopulationClass2;
    }

    public void setVarCloudPopulationClass2(double varCloudPopulationClass2) {
        this.varCloudPopulationClass2 = varCloudPopulationClass2;
    }


    //ITERATIONS GETTER & SETTER
    public long getCloudletIterationClass1() {
        return cloudletIterationClass1;
    }

    public void setCloudletIterationClass1(long cloudletIterationClass1) {
        this.cloudletIterationClass1 = cloudletIterationClass1;
    }

    public long getCloudletIterationClass2() {
        return cloudletIterationClass2;
    }

    public void setCloudletIterationClass2(long cloudletIterationClass2) {
        this.cloudletIterationClass2 = cloudletIterationClass2;
    }

    public long getCloudIterationClass1() {
        return cloudIterationClass1;
    }

    public void setCloudIterationClass1(long cloudIterationClass1) {
        this.cloudIterationClass1 = cloudIterationClass1;
    }

    public long getCloudIterationClass2() {
        return cloudIterationClass2;
    }

    public void setCloudIterationClass2(long cloudIterationClass2) {
        this.cloudIterationClass2 = cloudIterationClass2;
    }

    public long getGlobalIteration() {
        return globalIteration;
    }

    public void setGlobalIteration(long globalIteration) {
        this.globalIteration = globalIteration;
    }

    public long getActualIteration() {
        return actualIteration;
    }

    public void setActualIteration(long actualIteration) {
        this.actualIteration = actualIteration;
    }

    public double getGlobalTime() {
        return globalTime;
    }

    public void setGlobalTime(double globalTime) {
        this.globalTime = globalTime;
    }
}
