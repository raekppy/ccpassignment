/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

/**
 *
 * @author Eric
 */
public class Timer extends Thread {
    public Cleaner cl;
    public Mechanic mec;
    public BusGenerator bug;
    public CleaningBay clg;
    public Weather wea;
    public Depot de;
    public Bus bu;
    public Ramp ram;
    public boolean closingtime;
    
    public Timer(Cleaner c, Mechanic m1, Mechanic m2, BusGenerator bg, CleaningBay cg, Weather w, Depot d, Ramp r){
        cl = c;
        mec = m1;
        mec = m2;
        bug = bg;
        clg = cg;
        wea = w;
        de = d;
        ram = r;
        closingtime = false;
    }

    public void run() {
    try {
        Thread.sleep(25000);
        notifyclosetime();
    }
    catch (InterruptedException e) {
    }
}
    public void notifyclosetime() {
        System.out.println("Timer : It's closing time now !");
        cl.setclosingtime();
        mec.setclosingtime();
        bug.setclosingtime();
        clg.setclosingtime();
        wea.setclosingtime();
        de.setclosingtime();
        ram.setclosingtime();
    }
}
