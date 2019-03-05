package cloudlet;

import event.Event;
import server.Server;
import variablesGenerator.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Cloudlet {

    //array dove vengono salvati i server
    private ArrayList<Server> serverList;
    //lista degli eventi, al momento inutilizzata
    private HashMap<Integer, Double> cloudletEventList;
    //numero di job di entrambe le classi, al momento non utilizzate
    private Integer n1; /*number of class 1 jobs*/
    private Integer n2; /*number of class 2 jobs*/


    /**
     * Costruttore del cloudlet. Prende in input il numero di server. Questi server vengono creati e inseriti
     * nella lista del cloudlet. Al termine della funzione vengono stampati gli id dei server creati
     * @param numServer
     */
    public Cloudlet(int numServer){
        this.serverList = new ArrayList<>();
        this.n1 = 0;
        this.n2 = 0;
        this.cloudletEventList = new HashMap<>();
        for (int i = 0; i<numServer; i++)
            serverList.add(new Server(i, 2.0, 3.0));
        for(Server i: serverList)
            System.out.println("Server " + i.getIdServer() + " OK");
    }

    /**
     * Funzione che gestisce gli arrivi
     * @param event
     * @return
     */
    public boolean putEvent(Event event){
        /*booleano che serve per il ciclo. Durante un ciclo isOk è uguale a false se devono essere
        liberati ancora dei server. Supponiamo di avere un arrivo al tempo t1. isOK è false se ci
        sono dei completamenti con t2 < t1 (ovvero non è ancora giunto quel momento). Una volta che
        isOk è a true, allora l'arrivo può essere effettivamente processato*/
        boolean isOk = false;
        //id e tempo minimo tra i prossimi completamenti dei server
        double minTime = 0;
        int id = -1;
        //prendo il server che ha tempo di completamento minimo (se esiste)
        while(!isOk){
            for(Server i: serverList){
                if(!i.getCurrentCompletionTime().equals(0.0) && (i.getCurrentCompletionTime() < minTime || minTime == 0)) {
                    minTime = i.getCurrentCompletionTime();
                    id = i.getIdServer();
                }
            }
            //se sono tutti vuoti o il completamento è successivo all'arrivo, esco
            if(id == -1 || minTime > event.getTime()) {
                isOk = true;
            }
            //altrimenti completo il job e svuoto il server
            else {
                //System.out.println("entro qui dove id: " + id);
                for (Server i : serverList) {
                    if (i.getIdServer() == id) {
                        i.setBusy(false);
                        i.setCurrentCompletionTime(0.0);
                        minTime=0;
                        id=-1;
                        break;
                    }
                }
                //printStatus();
            }
        }
        /*vengono aggiornati i tempi di completamento di tutti i server con completamento successivo all'arrivo
        * ad esempio se un server ha completamento 10 e l'arrivo è 8, dopo questo ciclo for il tempo di completamento
        * rimasto è pari a 2 (ed è una modifica importantissima al fine del prossimo ciclo)
        */
        for(Server i: serverList) {
            if(!i.getCurrentCompletionTime().equals(0.0)) {
                if (i.getCurrentCompletionTime() < event.getTime()) {
                    System.out.println("ERROR");
                    System.exit(-1);
                }
                i.setCurrentCompletionTime(i.getCurrentCompletionTime() - event.getTime());
            }
        }
        boolean rejected = true;
        for(Server i: serverList){
            if(!(i.isBusy())) {
                i.setBusy(true);
                i.setCurrentCompletionTime(event.getTime() + Services.getInstance().getServiceTime());
                rejected = false;
                break;
            }
        }
        /*if (rejected) {
            System.out.println("pacchetto rigettato");
        }*/
        //printStatus();
        return !rejected;
    }

    public void printStatus(){
        System.out.println("\tID\t|\tTotal Busy Time\t|\tNumber Completition");
        for(Server i: serverList)
            System.out.println("\t" + i.getIdServer() + "\t|\t" + i.getTotalTimeBusy() + "\t|\t" + i.getNumberCompletion1());
        System.out.println("\n");
    }
    /*
     * Return number of class 1 jobs
     */
    public Integer getN1(){
        return n1;
    }

    /*
     * Return number of class 2 jobs
     */
    public Integer getN2(){
        return n2;
    }








}
