import java.io.IOException;

/**
 * An initializer for running the server.
 *
 * @author Lenny Meng, meng110@purdue.edu
 * @version Nov 25 2019
 */

public class ReservationRunner {
    public static void main(String[] args) throws IOException {
        ReservationServer rs;

        try {
            rs = new ReservationServer();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        rs.start();
    }
}
