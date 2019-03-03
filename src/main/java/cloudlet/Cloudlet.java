package cloudlet;

import event.Event;
import server.Server;

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
    }

    public void putEvent(Event event){
        boolean isOk = false;
        double minTime = 0;
        int id = -1;
        while(!isOk){
            for(Server i: serverList){
                if(!i.getCurrentCompletionTime().isNaN() && (i.getCurrentCompletionTime() < minTime || minTime == 0)) {
                    minTime = i.getCurrentCompletionTime();
                    id = i.getIdServer();
                }
            }
            if(id == -1 || minTime > event.getTime())
                isOk = true;
            else
                for(Server i: serverList){
                    if (i.getIdServer() == id){
                        i.setBusy(false);
                        i.setCurrentCompletionTime(null);
                    }
                }
        }
        for(Server i: serverList){
            if(!(i.isBusy())) {
                i.setBusy(true);
                i.setCurrentCompletionTime(event.getTime() + 10.0); // ricordati di mettere il tempo di servizio expo o hyper expo
                break;
            }
        }

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
