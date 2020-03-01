package lab4;

import java.util.Random;

public class ExpRandom {
    Random rand;

    public ExpRandom() {
        this.rand = new Random();
    }

    public double genExp(double lambda){
        return Math.log(1 - this.rand.nextDouble()) / (-lambda);
    }

    public double random(){
        return this.rand.nextDouble();
    }
}
