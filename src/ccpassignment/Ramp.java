/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.ArrayList;

public class Ramp {

    Depot depot;
    ArrayList<Bus> buslist;
    public boolean allow = true;
    public boolean closingtime = false;

    Ramp() {
        buslist = new ArrayList<Bus>();
    }

    public void goRamp(Bus b) {
        if (!closingtime || depot.buslist.size() != 0 || depot.EDlist.size() != 0) {
            benter(b);
            bexit(b);
        }
        if (closingtime == true && depot.buslist.size() == 0 && depot.EDlist.size() == 0) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }
        return;

    }

    public synchronized void benter(Bus b) {
        if (allow == true) {
            System.out.println("Depot : Ramp available, Next bus please! ");
            System.out.println(b + " : " + "Acquiring Ramp!  ");
            System.out.println("Depot : Ramp is full, Please wait! ");
            if (buslist.size() != 0) {
                allow = false;
                buslist.notify();
            }
        } else {

            try {
                buslist.wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    public synchronized void bexit(Bus b) {
        try {
            Thread.sleep(500);
            allow = true;
        } catch (InterruptedException ex) {
        }
    }

    public synchronized void setclosingtime() {
        closingtime = true;
        System.out.println("Ramp : We're closing now! Please come back tomorrow! ");
    }
}
