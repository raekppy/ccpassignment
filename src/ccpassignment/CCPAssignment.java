package ccpassignment;

public class CCPAssignment {

    public static void main(String[] args) {

        Depot Busdepot = new Depot();
        Cleaner clr = new Cleaner(Busdepot);
        Weather wea = new Weather(clr, Busdepot);
        Ramp ram = new Ramp();

        BusGenerator gen = new BusGenerator(Busdepot);
        Mechanic mac1 = new Mechanic(Busdepot);
        Mechanic mac2 = new Mechanic(Busdepot);
        CleaningBay clg = new CleaningBay(clr, Busdepot, wea);

        Thread m1 = new Thread(mac1);
        Thread m2 = new Thread(mac2);
        Thread g = new Thread(gen);
        Thread c = new Thread(clr);
        Thread clrg = new Thread(clg);
        Thread wth = new Thread(wea);
        Thread r = new Thread();

        System.out.println("Bus Terminal Opened! Bus Starting Approach!! \n*******************************************");
        g.start();
        r.start();
        m1.start();
        m2.start();
        clrg.start();
        wth.start();
        Timer t = new Timer (clr, mac1, mac2, gen, clg, wea, Busdepot, ram);
        t.start();
    }
}
