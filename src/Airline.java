import java.io.Serializable;
import java.lang.reflect.Array;
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
    private String description;
    private int maxCapacity;

    private ArrayList<String> souls;

    public Airline(String description, ArrayList<String> passengerFNames, ArrayList<String> passengerLNames,
                   ArrayList<Integer> passengerAges, int maxCapacity)
            throws NullPointerException, IllegalArgumentException {
        if (description == null || passengerFNames == null || passengerLNames == null ||
                passengerAges == null) { //Check that @params are not null
            throw new NullPointerException();
        } else if (maxCapacity <= 0) { //Check that the capacity of the plane is not null
            throw new IllegalArgumentException();
        }

        /**
         * No need to check if passengerAge, passengerFName, or passengerLName are invalid, as they should be checked
         * upon input.
         *
         * souls should be formatted as follows.
         * { "LASTINITIAL. FIRSTNAME, AGE", "LASTINITIAL. FIRSTNAME, AGE" }
         */

        int counter = 0;
        while (passengerFNames.get(counter) != null && passengerLNames.get(counter) != null) {
            souls.add(String.format("%s. %s, %d",
                    passengerLNames.get(counter).substring(0, 1).toUpperCase(),
                    passengerFNames.get(counter).toUpperCase(), passengerAges.get(counter)));
        }

        this.description = description;
        this.maxCapacity = maxCapacity;


    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public ArrayList<String> getSouls() {
        return this.souls;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isFull() {
        return (this.getMaxCapacity() == souls.size());
    }
}
