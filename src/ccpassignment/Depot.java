package ccpassignment;

import static ccpassignment.Cleaner.closingtime;
import java.util.ArrayList;

public class Depot {

    int dbay = 8;
    private static int count = 0;
    public boolean closingtime = false;
    private static int arrived = 0;
    Ramp ramp2 = new Ramp();
    Mechanic mac;
    Cleaner c;
    ArrayList<Bus> buslist;
    ArrayList<Bus> Dlist;
    ArrayList<Bus> Mlist;
    ArrayList<Bus> Clist;
    ArrayList<Bus> EDlist;
    ArrayList<Bus> Slist;

    public Depot() {
        Dlist = new ArrayList<Bus>();
        buslist = new ArrayList<Bus>();
        Mlist = new ArrayList<Bus>();
        Clist = new ArrayList<Bus>();
        EDlist = new ArrayList<Bus>();
        Slist = new ArrayList<Bus>();
    }

    public static int getArrived() {
        return arrived;
    }

    public static void setArrived(int arrived) {
        Depot.arrived = arrived;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Depot.count = count;
    }

    public void go(Bus b) {
        synchronized (buslist) {
            this.setCount(this.count + 1);
            this.setArrived(this.arrived + 1);
            System.out.println("Thread-Bus " + b.getBid() + " : " + "Request for entrance " + b.getTime());
            System.out.println("Thread-Bus " + b.getBid() + " : " + "Request entrance on Ramp. " + b.getTime());
            ramp2.goRamp(b);
            System.out.println("Number of buses arriving for the day: " + this.getArrived());

            if (this.getCount() > dbay) {
                this.setCount(this.count - 1);
                System.out.println("No bay in depot available for " + b.getBname());
                System.out.println("Depot : " + b.getBname() + " Back to waiting area! ");
                return;
            }
            buslist.add(b);
            if (buslist.size() <= dbay) {
                System.out.println("Depot: " + b.getBname() + " you are clear to proceed to waiting bay. ");
                Dlist.add(buslist.get(0));
                buslist.remove(0);
                if (b.getBid() % 2 == 0) {
                    Mlist.add(Dlist.get(0));
                    Dlist.remove(0);
                } else {
                    Clist.add(Dlist.get(0));
                    Dlist.remove(0);
                }
            }
            if (ramp2.allow == true && EDlist.size() != 0) {
                System.out.println(EDlist.get(0) + " : " + "Request exit on Ramp. ");
                System.out.println(EDlist.get(0) + " : " + "Acquiring Ramp!  ");
                System.out.println(EDlist.get(0) + " : " + "I have cleared the ramp, Goodbye! " + EDlist.get(0) + " Exit the terminal. ");
                this.setCount(this.count - 1);
                EDlist.remove(0);
            }
        }

        synchronized (Mlist) {
            if (Mlist.size() <= 2) {
                Mlist.notify();
            }
            if (Mechanic.getMbay() == 0) {
                System.out.println("MECHANIC BAY CURRENTLY FULL, PLEASE WAIT! ");
            }
        }
        synchronized (Clist) {
            if (Clist.size() <= 4) {
                Clist.notify();
            }
            if (Cleaner.getCbay() == 0) {
                System.out.println("CLEANING BAY CURRENTLY FULL, PLEASE WAIT! ");
            }
        }
    }

    public synchronized void setclosingtime() {
        closingtime = true;
        System.out.println("Depot: Closing? Ok.");
    }

}
