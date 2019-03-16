package cloudlet;

import event.CloudletCompletionEvent;
import event.Event;
import job.Job;

import java.util.ArrayList;

public class Cloudlet {


    private ArrayList<Server> serverList;        /*array containing servers composing cloudlet*/
    private ArrayList<Event> cloudletEventList;  /*cloudlet event list: saves any kind of new event that occurrs*/
    private double globalTime;                   /*global clock, initializated since servers creation*/
    private int n1; //number of class 1 jobs
    private int n2; //number of class 2 jobs


    /**
     * Costruttore del cloudlet
     * Prende in input il numero di server per crearli ed inserirli nella lista del cloudlet
     *
     * @param numServer
     */
    public Cloudlet(int numServer) {
        this.serverList = new ArrayList<>();
        this.globalTime = 0.0;
        this.n1 = 0;
        this.n2 = 0;
        this.cloudletEventList = new ArrayList<>();
        Server.initServers(serverList, numServer);
    }

    /**
     * Funzione che gestisce gli arrivi
     *
     * @param event
     */
    public boolean putArrivalEvent(Event event) {
        //removeCompletedJobsFromServers(event.getTime()); /* rimuovo i job che hanno terminato il loro servizio */
        //updateRemainingServiceTimes(event.getTime()); /*aggiorno il tempo rimanente dei job ancora in servizio*/
        return processCurrentJob(event.getJobEvent());
    }

    /**
     * rimuovo i job completati dai server
     * @param time
     */
    public void removeCompletedJobsFromServers(double time) {
        /*
            serversAreNotBusy: durante un ciclo serversAreNotBusy è:
            - false se devono essere liberati ancora dei server: ad esempio, supponiamo di avere un arrivo al tempo t1, allora serversAreNotBusy è false se ci
            sono dei completamenti con t2 < t1 (ovvero non è ancora giunto quel momento).
            - true se l'arrivo può essere effettivamente processato
        */
        boolean serversAreNotBusy = false;
        double minTime = 0.0;
        //int position = 0;
        int id = -1;

        //prendo il server che ha tempo di completamento minimo (se esiste)
        while (!serversAreNotBusy) {

            for (Server i : serverList) {
                if (!i.getCurrentCompletionTime().equals(0.0) && (i.getCurrentCompletionTime() < minTime || minTime == 0)) {
                    minTime = i.getCurrentCompletionTime();

                    id = i.getIdServer();
                }
            }

            //se sono tutti vuoti o il completamento è successivo all'arrivo, esco
            if (id == -1 || minTime > time) {
                serversAreNotBusy = true;
            }

            //altrimenti completo il job e svuoto il server
            else {
                for (Server i : serverList) {
                    if (i.getIdServer() == id) {
                        //Event e = new CloudletCompletionEvent(type, globalTime + i.getCurrentCompletionTime());
                       // cloudletEventList.add(e);
                        i.setBusy(false);
                        i.setCurrentCompletionTime(-1);
                        minTime = 0;
                        id = -1;
                        handleComplitions(i.getCurrentJobInService().getJobclass());
                        break;
                    }
                }
            }
        }

    }

    /**
     * viene aggiornato il tempo rimanente dei job nei server
     *
     * @param time
     */
    public void updateRemainingServiceTimes(double time) {
        /*vengono aggiornati i tempi di completamento di tutti i server con completamento successivo all'arrivo
         * ad esempio se un server ha completamento 10 e l'arrivo è 8, dopo questo ciclo for il tempo di completamento
         * rimasto è pari a 2 (ed è una modifica importantissima al fine del prossimo ciclo)
         */
        for (Server i : serverList) {
            if (!i.getCurrentCompletionTime().equals(0.0)) {
                if (i.getCurrentCompletionTime() < time) {
                    System.out.println("ERROR");
                    System.exit(-1);
                }
                i.decreaseTime(time);
            }
        }
    }

    /**
     * processo il job corrente e ritorna se è stato preso in carico o rifiutato
     *
     * @param currentJob
     * @return
     */
    private boolean processCurrentJob(Job currentJob) {
        //System.out.println(currentJob.getArrival());
        globalTime += currentJob.getArrival();
        boolean rejected = true;
        for (Server i : serverList) {
            if (!(i.isBusy())) {
                i.setBusy(true);
                i.setCurrentJobInService(currentJob);
                i.setCurrentCompletionTime(currentJob.getJobclass());
                //cloudletEventList.add(new Event(currentJob, globalTime));
                rejected = false;
                incrementPopulation(currentJob.getJobclass());
                break;
            }
        }
        if (rejected){
            cloudletEventList.add(new Event(-1, globalTime));
            System.out.println("rejected");
        }

        return !rejected;
    }


    /**
     * Getter and Setter
     */

    public double getSimulationTime() {
        return globalTime;
    }

    public ArrayList<Event> getCloudletEventList() {
        return this.cloudletEventList;
    }



    public void incrementPopulation(int jobclass) {
        switch (jobclass) {
            case 1:
                this.n1++;
                break;
            case 2:
                this.n2++;
                break;

        }
        /*if(jobclass == 1)
            this.n1++;
        else if (jobclass == 2)
            this.n2 ++ ;
        else
            System.out.println("not this job");
            */
    }


    public void handleComplitions(int jobclass) {
        switch (jobclass) {
            case 1:
                this.n1--;
                break;
            case 2:
                this.n2--;
                break;

        }
    }


    /**
     * Getters
     */
    public int getN1(){
        return this.n1;
    }

    public int getN2(){
        return this.n2;
    }

}

