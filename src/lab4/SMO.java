package lab4;


import java.util.ArrayList;

public class SMO {
    private int numChannel;
    private int maxQueue;
    private ArrayList<Double> timeFinServ = new ArrayList<Double>(); //
    private ArrayList<Integer> stateChannel = new ArrayList<Integer>(); //поточний стан пристроїв
    private int stateQueue; //поточний стан черги
    private double serv;
    private boolean entry;
    private boolean exit;
    private double tmin;
    private int minChannel;
    private boolean block;
    private ExpRandom random;

    public SMO(int num, int max, double timeMod, double serv) {
        this.random = new ExpRandom();
        this.stateQueue = 0; //початковий стан черги
        this.numChannel = num;
        this.maxQueue = max;
        this.serv = serv;
        this.entry = false;
        this.exit = false;
        this.block = false; // в початковий момент блокування маршруту відсутнє
        for (int i = 0; i < numChannel; i++) {
            this.timeFinServ.add(timeMod);
            this.stateChannel.add(0);
        }
        this.nextTime();
    }
    public void seize(double t) {
        int i = 0;
        if (this.entry == true){
            boolean j = false;
            i = 0;
            while ((i < this.numChannel) && (j == false)){
                if (this.stateChannel.get(i) == 0){
                    j = true;
                    //System.out.printf(Integer.toString(i));
                    this.seizeChannel(i,t);
                    this.nextTime(); // змінити найближчу подію в СМО
                    this.entry = false;
                }
                else i = i + 1;
            }
            if ((j == false) && (this.maxQueue > 0)){
                this.stateQueue = this.stateQueue + 1;
                this.entry = false;
                if (this.stateQueue == this.maxQueue) {
                    this.block = true;
                }
            }
        }
    }

    public void releize(int channel, double timeMod, double t){
        if (this.stateQueue > 0) {
            this.stateQueue = this.stateQueue - 1;
            if (this.block = true) {
                this.block = false;
            }
            this.seizeChannel(channel, t);
            this.nextTime();
        } else {
          //  System.out.println(this.stateChannel);
          //  System.out.println(channel);
            this.releizeChannel(channel, timeMod);
            this.nextTime();
        }
        this.exit = true;
    }

    public int getStateQue(){
        return this.stateQueue;
    }

    public void seizeChannel(int channel, double t){
        this.stateChannel.set(channel,  1);
        this.timeFinServ.set(channel, t + random.genExp(this.serv));
    }

    public void releizeChannel(int channel, double timeMod){
        this.stateChannel.set(channel,  0);
        this.timeFinServ.set(channel, timeMod);
    }

    public int getStateChannel(int num){
        return this.stateChannel.get(num);
    }

    public double getAverLoadChannel(){
        int sum = 0;
        int i = 0;
        for (i = 0; i < this.numChannel; i++){
            sum = sum + this.stateChannel.get(i);
        }
        return sum;
    }

    public int getNumChannel(){
        return this.numChannel;
    }

    public boolean getBlock(){
        return this.block;
    }

    public void setExit(boolean ent){
        this.exit = ent;
    }

    public void setEntry(boolean ex){
        this.entry = ex;
    }

    public void nextTime(){
        this.tmin = this.timeFinServ.get(0);
        this.minChannel = 0;
        if (this.numChannel > 1){
            for (int i = 0; i < this.numChannel; i++){
                if (this.timeFinServ.get(i) < this.tmin){
                    this.tmin = this.timeFinServ.get(i);
                    this.minChannel = i;
                }
            }
        }
    }
    public double getMinTime(){
        return this.tmin;
    }

    public int getMinChannel(){
        return this.minChannel;
    }

    public void setTimeFinServ(int channel, double t){
        this.timeFinServ.set(channel, t);
    }
}
