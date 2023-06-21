/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.ArrayList;
import java.util.Date;

public class Cleaner implements Runnable {

    public static boolean closingtime = false;
    private static int cbay = 2;
    Ramp ramp = new Ramp();
    private int cid = 1;
    Object i;
    private String Cname;
    ArrayList<Bus> Clist;
    Date time;
    Depot depot;
    Bus bus;
    Cleaner c;
    Weather w;

    public static int getCbay() {
        return cbay;
    }

    public static void setCbay(int cbay) {
        Cleaner.cbay = cbay;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String Cname) {
        this.Cname = Cname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    Cleaner(Depot depot) {
        this.depot = depot;
    }

    Cleaner(Depot depot, Bus bus) {
        this.depot = depot;
        this.bus = bus;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Cleaner(Bus bus) {

    }

    public Cleaner(Cleaner clrg, Depot depot, Weather w) {
        this.c = clrg;
        this.depot = depot;
        this.w = w;
    }

    public synchronized void setstopTime() {
        closingtime = true;
    }

    public synchronized void setWeather() {
        System.out.println("Cleaning Bay : is raining now, please wait until the rain stop then only we can continue. ");
    }

    public void CleanBus() {
        synchronized (depot.Clist) {
            while (depot.Clist.size() == 0) {
                System.out.println("Cleaning Bay " + getCid() + " Opened, Ready For Duty! ");
                try {
                    if (w.rain == true) {
                        depot.Clist.wait(2000);
                    }
                    cbay = cbay - 2;
                    depot.Clist.wait(2000);
                    if (closingtime && depot.Clist.size() == 0) {
                        return;
                    }

                } catch (InterruptedException ex) {
                }
            }

            System.out.println("Cleaners found a bus in the queue.");
            depot.Clist.get(0);
        }
        cbay = cbay + 1;
        i = depot.Clist.remove(0);
        try {
            System.out.println(this.getCname() + " : " + i + " I need a bus wash! " + time);
            System.out.println(this.getCname() + " : " + i + " is washing bus. " + time);
            //Confingured the task time to 2seconds to finish the cleaning task
            Thread.sleep(2000);
            cbay = cbay - 1;
        } catch (InterruptedException iex) {
        }
        System.out.println(this.getCname() + " : " + i + " Done washing, waiting instruction to leave. " + time);
       
        ramp.goRamp((Bus) i);
        depot.EDlist.add((Bus) i);
        depot.Slist.add((Bus) i);

        if (closingtime == true && depot.Clist.size() == 0) {
            System.out.println("Cleaning Bay: Empty, time to go home. ");
        }
        System.out.println("Number of buses served: " + depot.Slist.size());
        return;
    }

    public void run() {
        while (!closingtime || depot.Clist.size() != 0) {
            CleanBus();
            if (closingtime && depot.Clist.size() == 0) {
                return;
            }

        }
        if (closingtime && depot.Clist.size() == 0) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void setclosingtime() {
        closingtime = true;
        System.out.println("Cleaner : We're closing now! Please come back tomorrow! ");
    }

}
