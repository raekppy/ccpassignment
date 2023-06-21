/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Mechanic implements Runnable {

    public static boolean closingtime = false;
    public boolean dirty = false;
    Date time = new Date();
    Ramp ramp = new Ramp();
    Random num = new Random();
    String[] ran = {"Dirty", "Not Dirty"};
    int r = num.nextInt(2);
    int n = num.nextInt(3);
    String[] ch = {"Mechanical Malfunctions", "Fuel Shortages", "Service Engine"};
    private static int mbay = 2;
    Object i;
    ArrayList<Bus> Mlist;
    Depot depot;
    Bus bus;
    Cleaner c;

    Mechanic(Depot depot) {
        this.depot = depot;
//        this.bus = bus;
    }

    public static int getMbay() {
        return mbay;
    }

    public static void setMbay(int mbay) {
        Mechanic.mbay = mbay;
    }

    Mechanic(Depot depot, Bus bus) {
        this.depot = depot;
        this.bus = bus;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Mechanic(Bus bus) {

    }

    public void FixBus() {
        synchronized (depot.Mlist) {
            while (depot.Mlist.size() == 0) {
                System.out.println("Mechanic Bay Opened, Ready For Duty!\n******************************************* ");
                try {
                    mbay = mbay - 2;
                    if (closingtime) {
                        return;
                    }
                    depot.Mlist.wait(2000);
                    if (closingtime && depot.Mlist.size() == 0) {
                        return;
                    }
                } catch (InterruptedException ex) {
                }
            }
            System.out.println("Mechanics found a bus in the queue.");
            depot.Mlist.get(0);
        }
        i = depot.Mlist.remove(0);
        mbay = mbay + 1;
        try {
            System.out.println(i + " " + ch[n]);
            if (n == 0 || n == 1) {
                if (depot.Mlist.size() == 0) {
                    System.out.println(i + " " + "Checking and prepare to fix the bus. " + time);
                    System.out.println(i + " " + "Fixing the bus. " + time);
                } else {
//                    depot.Mlist.add(1, (Bus) i);
                    System.out.println("Special Case! EMERGENCY!");
                    System.out.println(i + " " + "Checking and prepare to fix the bus. " + time);
                    System.out.println(i + " " + "Fixing the bus. " + time);
                }
            }
            if (n == 2) {
                System.out.println(i + " " + "Servicing the bus. " + time);
            }
            Thread.sleep(3000);
            mbay = mbay - 1;
        } catch (InterruptedException iex) {
        }
        System.out.println(i + " Done servicing, waiting instruction to leave. " + time);
        depot.Slist.add((Bus) i);
        System.out.println("Number of buses served: " + depot.Slist.size());
        System.out.println(i + " " + ran[r]);
        if(closingtime == true && depot.Mlist.size() == 0){
            System.out.println("Mechanical Bay: Empty, time to go home. ");
        }

        if (r == 0) {
            depot.Clist.add(0, (Bus) i);
        }

        if (r == 1) {
            ramp.goRamp((Bus) i);
            depot.EDlist.add((Bus) i);
            return;
        }
    }

    public void run() {
        while (!closingtime || depot.Mlist.size() != 0) {
            FixBus();
        }
        if (closingtime && depot.Mlist.size() == 0) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void setclosingtime() {
        closingtime = true;
        System.out.println("Mechanic Bay : We're closing now! Please come back tomorrow! ");
    }

}
