package cloudlet;

import event.Event;
import server.Server;
import variablesGenerator.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Cloudlet {

    private ArrayList<Server> serverList;
    private HashMap<Integer, Double> cloudletEventList;
    private Integer n1; /*number of class 1 jobs*/
    private Integer n2; /*number of class 2 jobs*/


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

    public boolean putEvent(Event event){
        System.out.println("event time: " + event.getTime());
        boolean isOk = false;
        double minTime = 0;
        int id = -1;
        while(!isOk){
            for(Server i: serverList){
                if(!i.getCurrentCompletionTime().equals(0.0) && (i.getCurrentCompletionTime() < minTime || minTime == 0)) {
                    minTime = i.getCurrentCompletionTime();
                    id = i.getIdServer();
                }
            }
            if(id == -1 || minTime > event.getTime()) {
                isOk = true;
            }
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
        for(Server i: serverList)
            i.setCurrentCompletionTime(i.getCurrentCompletionTime()-event.getTime());
        boolean rejected = true;
        for(Server i: serverList){
            if(!(i.isBusy())) {
                i.setBusy(true);
                i.setCurrentCompletionTime(event.getTime() + Services.getInstance().getServiceTime());
                rejected = false;
                System.out.println("aggiungo a " + i.getIdServer());
                break;
            }
            System.out.println(i.getIdServer() + " era pieno");
        }
        System.out.println("\n\n");
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
