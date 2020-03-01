package lab4;

public class JoinIN {
    private SystEntry entry;
    private SMO exitSmo;
    private boolean state;
    private int numUnServ;

    public JoinIN(SystEntry entry, SMO smoExit){
        this.entry = entry;
        this.exitSmo = smoExit;
        this.state = true;
        this.numUnServ = 0;
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

    public void send(){
        if (this.getState() == true) {
            this.entry.setExit(false);
            this.exitSmo.setEntry(true);
        } else {
            this.numUnServ = this.numUnServ + 1;
        }
    }
}
