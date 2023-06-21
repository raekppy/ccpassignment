/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.Date;

public class BusGenerator implements Runnable {

    private int count = 1;
    Depot Busdepot;
    public boolean closingtime = false;

    public BusGenerator(Depot Busdepot) {
        this.Busdepot = Busdepot;
    }

    public void run() {
        while (!closingtime) {
            Bus b = new Bus(Busdepot);
            b.setBid(count);
            b.setTime(new Date());
            Thread bu = new Thread(b);
            b.setBname("Bus " + b.getBid());
            bu.start();
            count++;

            try {
                Thread.sleep(500);
            } catch (InterruptedException iex) {
            }
        }
        if (closingtime == true && Busdepot.buslist.size() == 0) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    public synchronized void setclosingtime() {
        closingtime = true;
        System.out.println("Closing? Ok.");
    }

}
