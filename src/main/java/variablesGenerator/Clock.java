package variablesGenerator;

public class Clock {

    private static Clock instance = null;

    private double arrival; /* Last arrival time*/
    private double current; /* Current time*/
    private double next; /*Next-event time*/



    private Clock(){
        this.current = 0.0;
        this. next = 0.0;
    }


    /**
     * Return singleton instance of Time
     */
    public static Clock getInstance(){
        if(instance == null){
            instance = new Clock();
        }
        return instance;
    }



    /**
     * Getter methods
     */
    public double getArrival(){
        return arrival;
    }

    public double getCurrent(){
        return current;
    }

    public double getNext(){
        return next;
    }



    /**
     * Setter methods
     */
    public void setArrival(double arrival){
        this.arrival = arrival;
    }

    public void setCurrent(double current){
        this.current = current;
    }

    public void setNext(double next) {
        this.next = next;
    }


    public void restart(){
        instance = null;
    }
}
