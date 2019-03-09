package cloudlet;

import event.Event;
import server.Server;

import java.util.ArrayList;

public class Cloudlet_NEW {


    private ArrayList<Server> serverList;        /*array containing servers composing cloudlet*/
    private ArrayList<Event> cloudletEventList;  /*cloudlet event list: saves any kind of new event that occurrs*/
    private double globalTime;                   /*global clock, initializated since servers creation*/


    /**
     * Costruttore del cloudlet. Prende in input il numero di server. Questi server vengono creati e inseriti
     * nella lista del cloudlet. Al termine della funzione vengono stampati gli id dei server creati
     * @param numServer
     */
    public Cloudlet_NEW(int numServer){
        this.serverList = new ArrayList<>();
        this.globalTime = 0.0;
        this.cloudletEventList = new ArrayList<>();
        initServers(numServer);
    }

    /**
     * Funzione che gestisce gli arrivi
     * @param event
     * @return
     */
    public boolean putEvent(Event event){

        removeCompletedJobsFromServers(event.getType(), event.getTime()); /* rimuovo i job che hanno terminato il loro servizio */

        updateRemainingServiceTimes(event.getTime()); /*aggiorno il tempo rimanente dei job ancora in servizio*/

        return processCurrentJob(event.getType(), event.getTime());
    }




    public void printStatus(){
        System.out.println("\tID\t|\tTotal Busy Time\t|\tNumber Completition");
        for(Server i: serverList)
            System.out.println("\t" + i.getIdServer() + "\t|\t" + i.getTotalTimeBusy() + "\t|\t" + i.getNumberCompletion1());
        System.out.println("\n");
    }

    public ArrayList<Event> getCloudletEventList(){
        return this.cloudletEventList;
    }

    /**
     * inizializzo i server
     * @param numServer
     */
    private void initServers(int numServer){
        for (int i = 0; i<numServer; i++) {
            serverList.add(new Server(i));
            System.out.println("Server " + serverList.get(i).getIdServer() + " OK");
        }
    }

    /**
     * rimuovo i job completati dai server
     * @param type
     * @param time
     */
    private void removeCompletedJobsFromServers(int type, double time){
        /*
            serversAreNotBusy: durante un ciclo serversAreNotBusy è:
            - false se devono essere liberati ancora dei server: ad esempio, supponiamo di avere un arrivo al tempo t1, allora serversAreNotBusy è false se ci
            sono dei completamenti con t2 < t1 (ovvero non è ancora giunto quel momento).
            - true se l'arrivo può essere effettivamente processato
        */
        boolean serversAreNotBusy = false;
        double minTime = 0.0;
        int id = -1;

        //prendo il server che ha tempo di completamento minimo (se esiste)
        while(!serversAreNotBusy){

            for(Server i: serverList){
                if(!i.getCurrentCompletionTime().equals(0.0) && (i.getCurrentCompletionTime() < minTime || minTime == 0)) {
                    minTime = i.getCurrentCompletionTime();
                    id = i.getIdServer();
                }
            }

            //se sono tutti vuoti o il completamento è successivo all'arrivo, esco
            if(id == -1 || minTime > time) {
                serversAreNotBusy = true;
            }

            //altrimenti completo il job e svuoto il server
            else {
                for (Server i : serverList) {
                    if (i.getIdServer() == id) {
                        cloudletEventList.add(new Event(type, globalTime + i.getCurrentCompletionTime()));
                        i.setBusy(false);
                        i.setCurrentCompletionTime(-1);
                        minTime = 0;
                        id = -1;
                        break;
                    }
                }
            }
        }

    }

    /**
     * viene aggiornato il tempo rimanente dei job nei server
     * @param time
     */
    private void updateRemainingServiceTimes(double time) {
        /*vengono aggiornati i tempi di completamento di tutti i server con completamento successivo all'arrivo
         * ad esempio se un server ha completamento 10 e l'arrivo è 8, dopo questo ciclo for il tempo di completamento
         * rimasto è pari a 2 (ed è una modifica importantissima al fine del prossimo ciclo)
         */
        for(Server i: serverList) {
            if(!i.getCurrentCompletionTime().equals(0.0)) {
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
     * @param type
     * @param time
     * @return
     */
    private boolean processCurrentJob(int type, double time) {
        globalTime += time;
        boolean rejected = true;
        for(Server i: serverList){
            if(!(i.isBusy())) {
                i.setBusy(true);
                i.setCurrentCompletionTime(type);
                cloudletEventList.add(new Event(type, globalTime));
                rejected = false;
                break;
            }
        }
        if (rejected)
            cloudletEventList.add(new Event(-1, globalTime));
        return !rejected;
    }





}

