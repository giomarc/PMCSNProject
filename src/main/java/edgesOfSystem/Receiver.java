package edgesOfSystem;

import event.Event;
import system.Printer;

public class Receiver {

    private long globalTimePassed                       = 0;
    private long completionCloudletClass1               = 0;
    private long completionCloudletClass2               = 0;
    private long completionCloudClass1                  = 0;
    private long completionCloudClass2                  = 0;
    private double meanCompletionTimeCloudletClass1     = 0;
    private double meanCompletionTimeCloudletClass2     = 0;
    private double meanCompletionTimeCloudClass1        = 0;
    private double meanCompletionTimeCloudClass2        = 0;

    private Receiver instance = new Receiver();

    private Receiver(){}

    public Receiver getInstance(){
        return instance;
    }

    public void receiveCompletion(Event e){
        //eseguo il retrieve dei valori richiesti
        int type = e.getType();
        double StartServiceTime = e.getJobEvent().getArrival();
        double EndServiceTime = e.getJobEvent().getService();
        int jobclass = e.getJobEvent().getJobclass();

        //aggiorno il tempo globale passato
        globalTimePassed += StartServiceTime;

        //completamento del cloudlet
        if(type == 1) {
            if(jobclass == 1)
                completionCloudletClass1 ++;
            else if(jobclass == 2)
                completionCloudletClass2++;
            else
                anomaly();
            updateMean(type, jobclass, EndServiceTime-StartServiceTime);
        }
        //completamento del cloud
        else if (type == 2){
            if(jobclass == 1)
                completionCloudClass1 ++;
            else if(jobclass == 2)
                completionCloudClass2++;
            else
                anomaly();
            updateMean(type, jobclass, EndServiceTime-StartServiceTime);
        }
    }

    /**
     * algoritmo del calcolo della media di welford
     * @param type can be 1 for cloudlet, 2 for cloud
     * @param jobclass can be 1 for class 1, 2 for class 2
     */
    private void updateMean(int type, int jobclass, double completionTime){
        //WELFORD
    }

    public void returnResults(){
        System.out.println("TODO");
    }

    private void anomaly(){
        Printer.getInstance().print("ANOMALIA RILEVATA", "red");
        System.exit(-1);
    }

}
