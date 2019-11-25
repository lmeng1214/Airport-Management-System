import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A ClientHandler class that handles clients on a thread by thread basis
 *
 * @author Lenny Meng, meng110@purdue.edu
 * @version Nov 24 2019
 */

public class ClientHandler implements Runnable {

    private File reservations;
    private Socket clientSocket;


    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.reservations = new File("reservations.txt");
    }

    @Override
    public void run() {
        /**
         * reservations.txt will be structured as follows.
         *
         * AIRLINE_NAME: AIRLINER
         * BOOKED: X/YYY
         * PASSENGER
         * PASSENGER
         * ENDOFLIST
         * AIRLINE_NAME: AIRLINER
         * ... etc
         * ENDOFLIST
         * EOF
         */

        /**
         * It will look like this on start.
         * AIRLINE_NAME: DELTA AIRlINES
         * BOOKED: 0/200
         * ENDOFLIST
         * AIRLINE_NAME: ...
         * etc
         * ENDOFLIST
         * EOF
         */

        synchronized(reservations) {
            try (ObjectInputStream ois = new ObjectInputStream(this.clientSocket.getInputStream());
                 ObjectOutputStream oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
                 BufferedReader bfr = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                 BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
                 PrintWriter pw = new PrintWriter(reservations);
                 BufferedReader bfrFile = new BufferedReader(new FileReader(reservations))) {

                String query;

                while (!(query = bfr.readLine()).equals("END_PROGRAM")) { // Takes input until sent "END_PROGRAM"
                    switch (query) {
                        case "getFlights": //Returns an arraylist of airliner names that can be booked at this time.
                            oos.writeObject(getFlights(bfrFile));
                            break;
                        case "addPassenger": //Client should query key addPassenger first, then Airline then Passenger
                            addPassenger(bfrFile, ois, pw);
                            break;
                        case "getSouls": //Returns an arraylist of passengers on the airline given.
                            oos.writeObject(getSouls(bfrFile, ois));
                            break;
                        default:
                            bfw.write("UNKNOWN COMMAND");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<String> getSouls(BufferedReader bfr, ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        Airline airline = (Airline) ois.readObject();
        ArrayList<String> souls = new ArrayList<>();
        while(true) {
            String nextLine = bfr.readLine();
            if (nextLine.equals("EOF")) {
                break;
            }
            if (nextLine.contains(airline.getName())) {
                while(true) {
                    bfr.readLine(); //Skip over booked line
                    String passengerList = bfr.readLine();
                    if (passengerList.equals("ENDOFLIST")) {
                        break;
                    }
                    souls.add(passengerList);
                }
            }
        }

        return souls;
    }

    private void addPassenger(BufferedReader bfrFile, ObjectInputStream ois, PrintWriter pw)
            throws IOException, ClassNotFoundException {
        // Get passenger data and the flight they are boarding. Assumes client has done checking.
        Airline airline = (Airline) ois.readObject();
        Passenger passenger = (Passenger) ois.readObject();

        while (true) {
            String thisLine = bfrFile.readLine();
            if (bfrFile.readLine().contains(airline.getName())) {
                //Figure how to add text between two lines.
                //bfrFile.printLine(passenger.toString());
            }
        }

    }

    private ArrayList<String> getFlights(BufferedReader bfrFile) throws IOException {

        ArrayList<String> availAirliners = new ArrayList<>();

        while (true) {
            String nextLine = bfrFile.readLine();
            String airliner = "";

            if (nextLine.equals("EOF")) {
                break;
            }
            if (nextLine.contains("AIRLINE_NAME: ")) {
                airliner = nextLine.substring(14);
            }
            if (nextLine.contains("/")) {
                if (!nextLine.substring(8, nextLine.lastIndexOf("/")).
                        equals(nextLine.substring(nextLine.lastIndexOf("/")))) {
                    availAirliners.add(airliner);
                }
            }
        }

        return availAirliners;
    }
}
