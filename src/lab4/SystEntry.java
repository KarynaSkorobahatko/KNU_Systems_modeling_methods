package lab4;

import java.util.Random;

public class SystEntry {
    private boolean exit;
    private boolean entry;
    private double interval; //інтервал часу, з яким мають надходити вимоги з вхідного по-токуTimeExit:double;//момент часу, в який вимога має вийти з вхідного потокуNumArrival: integer;//загальна кількість вимог, що створена вхідним потокомвимогpublic
    private double timeExit;//момент часу, в який вимога має вийти з вхідного потоку
    private int numArrival;
    private ExpRandom random;

    public SystEntry(double interval) {
        this.entry = true;
        this.exit = false;
        this.interval = interval;
        this.random = new ExpRandom();
    }

    public void arrival(double t) {
        this.timeExit = t + random.genExp(this.interval);
        this.exit = true;
        this.numArrival = this.numArrival + 1;
    }

    public void entryInSyst() {
        this.exit = true;
        this.entry = true;
    }

    public double getMinTime() {
        return this.timeExit;
    }

    public int getNumArrival() {
        return this.numArrival;
    }

    public void setExit(boolean ent) {
        this.exit = ent;
    }

    public void setEntry(boolean ex) {
        this.entry = ex;
    }
}
