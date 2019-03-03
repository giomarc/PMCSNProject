package cloud;

public class Cloud {

    private Integer n1; /*number of class 1 jobs*/
    private Integer n2; /*number of class 2 jobs*/


    public Cloud(){
        this.n1 = 0;
        this.n2 = 0;
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
