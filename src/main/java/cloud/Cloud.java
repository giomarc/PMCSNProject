package cloud;

import event.Event;
import job.Job;
import variablesGenerator.Services;
import java.util.ArrayList;
import java.util.Iterator;

public class Cloud {

    private Integer n1; /*number of class 1 jobs*/
    private Integer n2; /*number of class 2 jobs*/
    private int cloudComplition;
    private ArrayList<Double> arrivalsToCloud;
    private ArrayList<Double> remainingComplitionTime;


    public Cloud(){
        this.n1 = 0;
        this.n2 = 0;
        this.arrivalsToCloud = new ArrayList<>();
        this.remainingComplitionTime = new ArrayList<>();
        //this.remainingComplitionTime = new TreeSet<>();
    }


    public void processJobs(Event event){

        int removed = 0;
        putNewArrival(event.getTime());
        incrementPopulation(event.getJobEvent());

        for(int i = 0; i < remainingComplitionTime.size(); i++){
            Double new_value = remainingComplitionTime.get(i) - event.getTime();
            remainingComplitionTime.set(i,new_value );
            if (remainingComplitionTime.get(i) < 0) {
                removed++;
                handleComplitions(event.getJobEvent());
            }
        }
        removeFromList();
        remainingComplitionTime.add(Services.getInstance().getCloudServiceTime(event.getType()));
    }


    public void putNewArrival(Double arrivalTime){

        arrivalsToCloud.add(arrivalTime);
    }

    public void putRemainingComplitionTime(Double remainingTime){
        remainingComplitionTime.add(remainingTime);
    }



    public void handleComplitions(Job job)
    {
        this.cloudComplition++;
        switch (job.getJobclass())
        {
            case 1:
                this.n1--;
            case 2:
                this.n2--;
        }
    }

    public void removeFromList(){
        Iterator itr = remainingComplitionTime.iterator();
        while(itr.hasNext()){
            double x = (Double) itr.next();
            if( x < 0 ){
                itr.remove();
            }
        }
    }

    public void incrementPopulation(Job job){
            switch (job.getJobclass()){
                case 1:
                    this.n1++;
                case 2:
                    this.n2++;
            }
    }







}
