/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccpassignment;

import java.util.Date;
import java.util.Random;

public class Weather implements Runnable {

    public Cleaner cl;
    public Depot depot;
    Random num;
    int n;
    String[] wh = {"Weather : Sunny", "Weather : Cloudy", "Weather : Raining"};
    public static boolean rain = false;
    public boolean closingtime;
    Date time;

    public Weather() {

    }

    public Weather(Cleaner c, Depot depot) {
        cl = c;
        this.depot = depot;
        num = new Random();
        rain = false;
        closingtime = false;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void run() {
        try {
            while (cl.closingtime == false || depot.Clist.size() != 0) {
                n = num.nextInt(3);
                System.out.println(wh[n]);
                if (n == 2) {
                    rain = true;
                    notifystopTime();
                }
                if (n == 0 || n == 1) {
                    rain = false;
                    synchronized (depot.Clist) {
                        depot.Clist.notify();
                    }
                }
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
        }

        if (closingtime == true && depot.Clist.size() == 0 && depot.Mlist.size() == 0) {
            return;
        }
    }

    public void notifystopTime() {
        System.out.println("It's raining now! Please wait until the rain stop only continue! ");
        cl.setWeather();
    }

    public synchronized void setclosingtime() {
        closingtime = true;
        System.out.println("Weather : Closing? Ok.");
    }

}
