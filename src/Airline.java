import java.io.IOException;
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

    public Airline(String introduction, String name, int capacity) {
        this.introduction = introduction;
        this.name = name;

        this.capacity = capacity;
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
}
