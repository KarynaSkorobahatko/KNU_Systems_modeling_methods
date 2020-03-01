package lab4;

public class JoinOUT {
    private SMO entrySMO;
    private boolean state;
    private int numService;

    public JoinOUT(SMO smoEntry){
        this.entrySMO = smoEntry;
        this.numService = 0;
        this.state = true;
    }

    public void send(){
        if (this.state == true){
            this.entrySMO.setExit(false);
            this.numService += 1;
        }
    }

    public int getNumService(){
        return this.numService;
    }
}
