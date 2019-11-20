import java.io.Serializable;
import java.util.Random;

public class Gate implements Serializable {
    //gate letters
    private char[] letters = new char[] {'A', 'B', 'C'};
    //gate numbers
    private int[] numbers = new int[]
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15, 16, 17, 18};

    public Gate() {
    }//default constructor, no extra info needed

    public String generateGate() {//returns a random gate
        //randomNumberGenerator used to generate gate numbers
        Random rng = new Random();
        //where the gate will be stored
        String gate = "";

        //generates gate
        gate += letters[rng.nextInt(3)];
        gate += numbers[rng.nextInt(18)];

        //returns gate between A1 and C18
        return gate;
    }
}
