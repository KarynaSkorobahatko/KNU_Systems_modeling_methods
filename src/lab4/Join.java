package lab4;

public class Join {
    private SMO entrySmo;
    private SMO exitSmo;
    private boolean state;
    private int numUnServ;
    private int numServ;
    public Join(SMO smoEntry, SMO smoExit){
        this.entrySmo = smoEntry;
        this.exitSmo = smoExit;
        this.state = true;
        this.numUnServ = 0;
        this.numServ = 0;
    }


    public void send(){
        if (this.getState() == true) {
            this.entrySmo.setExit(false);
            this.exitSmo.setEntry(true);
            this.numServ += 1;
        } else {
            this.numUnServ = this.numUnServ + 1;
        }
    }

    public boolean getState(){
        if (this.exitSmo.getBlock() == true){
            return false;
        } else {
            return true;
        }
    }

    public int getNumUnServ(){
        return this.numUnServ;
    }

    public int getNumServ(){
        return this.numServ;
    }
}
