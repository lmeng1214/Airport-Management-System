import java.io.Serializable;
import java.util.ArrayList;

/**
 * Airline interface that is to be extended by the different Airliners:
 * - Delta
 * - Southwest
 * - Alaska
 *
 * @author Lenny Meng, meng110@purdue.edu
 * @version Nov 15 2019
 */

public abstract class Airline implements Serializable {
    private String introduction;
    private String name;

    private int capacity;

    private ArrayList<Passenger> souls;

    public Airline(String introduction, String name, int capacity) {
        this.introduction = introduction;
        this.name = name;

        this.capacity = capacity;

        this.souls = new ArrayList<>();
    }

    // Add accessor methods

    public boolean addPassenger(Passenger addPassenger) {
        if (this.souls.size() == capacity) {
            return false;
        }
        this.souls.add(addPassenger);
        return true;
    }

    public String getName() {
        return this.name;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean containsPassenger(Passenger passenger) {
        return this.souls.contains(passenger);
    }

    public ArrayList<Passenger> getSouls() {
        return this.souls;
    }


}
