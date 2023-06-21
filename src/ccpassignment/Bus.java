/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Eric
 */
public class Bus implements Runnable {

    String Bname;
    int bid;
    Date Time;
    Depot Busdepot;

    public Bus() {

    }

    public Bus(Depot Busdepot) {
        this.Busdepot = Busdepot;
    }

    public String getBname() {
        return Bname;
    }

    public void setBname(String Bname) {
        this.Bname = Bname;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date Time) {
        this.Time = Time;
    }

    public Depot getBusdepot() {
        return Busdepot;
    }

    public void setBusdepot(Depot Busdepot) {
        this.Busdepot = Busdepot;
    }

    public void run() {
        if (!Busdepot.closingtime) {
            goToRamp();
        }
        if (Busdepot.Dlist.size() == 0) {
            System.out.println("Depot: Empty, time to go home. ");
        }
    }

    private synchronized void goToRamp() {
        Busdepot.go(this);
    }

    @Override
    public String toString() {
        return "Thread-Bus " + getBid();
    }

}
