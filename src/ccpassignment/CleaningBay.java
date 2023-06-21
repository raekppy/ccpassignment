/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.Date;

public class CleaningBay implements Runnable {

    Cleaner clrg;
    public boolean closingtime = false;
    public static boolean rain = false;
    Depot depot;
    Weather w;

    public CleaningBay(Cleaner clrg, Depot depot, Weather w) {
        this.clrg = clrg;
        this.depot = depot;
        this.w = w;
    }

    public void run() {
        for (int count = 1; count < 2; count++) {
            Cleaner c = new Cleaner(clrg, depot, w);
            c.setCid(count);
            c.setTime(new Date());
            Thread cl = new Thread(c);
            c.setCname("Cleaner " + c.getCid());
            cl.start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException iex) {
            }
        }
        if (closingtime == true && depot.Clist.size() == 0) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            return;
        }
    }

    public synchronized void setstopTime() {
        rain = true;
        System.out.println("Cleaner Bay : is raining now, please wait until the rain stop then only continue allow bus enter to cleaning bay. ");
    }

    public synchronized void setclosingtime() {
        closingtime = true;
        System.out.println("Cleaning Bay : Closing? Ok.");
    }
}
