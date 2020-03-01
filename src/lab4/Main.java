package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {



    public static void main(String[] args){
        ArrayList<SMO> smo = new ArrayList<SMO>();
        int numSMO;
        SystEntry systEntry;
        ArrayList<Join> join = new ArrayList<Join>();
        JoinIN joinIn;
        JoinOUT joinOut;
        double tmin;
        double t;
        int event;
        ArrayList<Double> averQue = new ArrayList<Double>();
        ArrayList<Double> averDevice = new ArrayList<Double>();
        double prob;
        double choice;
        double timeMod = 1000.0;
        double timeNow = 0.0;
        ExpRandom random = new ExpRandom();
        numSMO = 2; //кількість СМО
        choice = 1; //ймовірність вибору маршруту
        prob = 0;
        for (int i = 0; i < numSMO; i++){
            averQue.add(0.0);
            averDevice.add(0.0);
        }
        //структура мережі масового обслуговування
        systEntry = new SystEntry(1/0.2); //створення вхідного потоку вимог
        smo.add(0, new SMO(2,6, timeMod,1/0.1)); //створення СМО1
        joinIn = new JoinIN(systEntry,smo.get(0));// створення маршруту до СМО1
        smo.add(1, new SMO(1,0, timeMod,1/0.2)); //створення СМО2
        join.add(new Join(smo.get(0), smo.get(1))); //створення маршруту від СМО1 до СМО2
        joinOut = new JoinOUT(smo.get(numSMO-1)); //створення маршруту на вихід системи
        t = 0; // початкове значення модельного часу
        boolean tmp = false;
        while (t < timeMod){
            tmin = systEntry.getMinTime();
            event = 0;
            for (int i = 0; i < numSMO; i++){
                if (smo.get(i).getMinTime() < tmin){
                    tmin = smo.get(i).getMinTime();
                    event = i + 1;
                    tmp = false;
                }
                if ((smo.get(i).getMinTime() == tmin)){
                    if  (tmp == true){
                        event = i + 1;
                    }
                    else {
                        tmp = true;
                    }
                }
            }

            for (int i = 0; i < numSMO; i++){
                averQue.set(i, averQue.get(i)+((tmin-t)/timeMod)*smo.get(i).getStateQue());
                averDevice.set(i, averDevice.get(i)+((tmin-t)/timeMod)*smo.get(i).getAverLoadChannel());
            }

            t = tmin; //просування часу в момент найближчої події
            switch(event){
                case(0):
                    systEntry.arrival(t);
                    joinIn.send();
                    smo.get(0).seize(t);
                    break;
                case(1):
                    if (smo.get(1).getStateChannel(0) == 0) {
                     //   System.out.println(1);
                        smo.get(0).releize(smo.get(0).getMinChannel(),timeMod,t);
                        join.get(0).send();
                        smo.get(1).seize(t);
                    } else {
                     //   System.out.println(2);
                        smo.get(0).setTimeFinServ(smo.get(0).getMinChannel(), smo.get(1).getMinTime());
                        smo.get(0).nextTime();
                    }
                    break;
                case(2):
                    smo.get(1).releize(smo.get(1).getMinChannel(),timeMod,t);
                    joinOut.send();
                    break;
            }
        }

        prob = joinIn.getNumUnServ();
        for (int i = 0; i < numSMO-1; i++){
            prob = prob + join.get(i).getNumUnServ();
        }
        prob = prob/systEntry.getNumArrival();

        System.out.println("Modeling time: " + t);
        System.out.println("Probability of Refusal: " + prob);
        System.out.println("NumServ: " + joinOut.getNumService());
        System.out.println("NumArriv: " + systEntry.getNumArrival());
        System.out.println("AverQue1: " + averQue.get(0));
        System.out.println("AverDev1: " + averDevice.get(0));
        System.out.println("UnservIn: " + joinIn.getNumUnServ());
        System.out.println("AverDev2: " + averDevice.get(1));
        System.out.println("Unserv2: " + join.get(0).getNumUnServ());

    }
}
