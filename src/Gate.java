import java.io.Serializable;
import java.util.Random;

public class Gate implements Serializable {
    //gate letters
    private char[] letters = new char[] {'A', 'B', 'C'};

    public Gate() {
    }//default constructor, no extra info needed

    public String generateGate() {//returns a random gate
        //randomNumberGenerator used to generate gate numbers
        Random rng = new Random();
        //where the gate will be stored
        String gate = "";

        //generates gate
        gate += letters[rng.nextInt(3)];
        gate += (rng.nextInt(18) + 1);

        //returns gate between A1 and C18
        return gate;
    }
}
