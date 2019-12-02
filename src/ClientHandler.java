import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Alaska Airlines class that extends Airline.java
 *
 * @author Lenny Meng, meng110@purdue.edu
 * @version Nov 23 2019
 *
 *
 * Resources(Janis Vuskalns):
 *
 * Questions on what must be included:
 * https://piazza.com/class/jzcqs9um7ga71y?cid=2317
 *
 * Restarting bufferedReader -
 * https://stackoverflow.com/questions/5421653/reset-buffer-with-bufferedreader-in-java
 *
 * Printwriter Resource -
 * https://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html
 *
 * Checking null strings -
 * https://stackoverflow.com/questions/2601978/how-to-check-if-my-string-is-equal-to-null
 *
 * Writing to file resource -
 * https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
 *
 * Overwriting text file -
 * https://stackoverflow.com/questions/13729625/overwriting-txt-file-in-java
 *
 * Add vs set in an arraylist -
 * https://stackoverflow.com/questions/7074402/how-to-insert-an-object-in-an-arraylist-at-a-specific-position
 *
 * Checking if string is an integer resource -
 * https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
 *
 * force stopping threads -
 * https://stackoverflow.com/questions/2804797/java-stop-server-thread
 *
 * @author Janis Vuskalns jvuskaln@purdue.edu
 * @version 11/29/2019
 */
public class ClientHandler implements Runnable {
    private File reservations;
    private Socket clientSocket;

    private FileInputStream fIs;
    private BufferedReader br;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private String deltaGate;
    private String swGate;
    private String alaskaGate;


    public ClientHandler(Socket clientSocket, File reservations) throws IOException {
        this.clientSocket = clientSocket;

        oos = new ObjectOutputStream(this.clientSocket.getOutputStream()); // Initialize object output stream.
        oos.flush();
        try {
            ois = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.reservations = reservations;

        fIs = new FileInputStream(this.reservations); // Initialize fileInputStream to read from file.
        br = new BufferedReader(new InputStreamReader(fIs)); // Initialize BFR to read from file.

        Gate gateObj = new Gate();

        this.deltaGate = gateObj.generateGate();
        this.swGate = gateObj.generateGate();
        this.alaskaGate = gateObj.generateGate();
    }

    public void run() {

        try {
            String query = "";

            while (!query.equals("END_PROGRAM")){ // Takes input until sent "END_PROGRAM"

                try { // Since the network is only accessible through OIS and OOS
                    query = (String) ois.readObject();
                } catch (NullPointerException | SocketException e) {
                    query = "END_PROGRAM";
                }

                switch (query) {
                    case "getGate" :
                        try {
                            Airline airline = (Airline) ois.readObject();
                            oos.writeObject(getGate(airline)); // This action does not access the file.
                            oos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "addPassenger" : //Client should query key addPassenger first, then Airline then Passenger
                        try {
                            Airline airline = (Airline) ois.readObject();
                            Passenger passenger = (Passenger) ois.readObject();

                            addPassenger(airline, passenger);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    case "getSouls" : //Returns an arraylist of passengers on the airline given.
                        Airline airline = (Airline) ois.readObject();
                        oos.writeObject(getSouls(airline));
                        oos.writeObject(getSouls(airline).size());
                        oos.flush();
                        break;
                    case "getNumPassengers" : // Just does getSouls() but returns the size of it.
                        airline = (Airline) ois.readObject();
                        oos.writeObject(getSouls(airline).size());
                        oos.flush();
                        break;
                    case "END_PROGRAM" : {
                        //don't act
                    }
                    default:
                        try {
                            oos.writeObject(null);
                            oos.flush();
                            break;
                        } catch (SocketException e) {
                            break;
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getGate(Airline airline) {
        if (airline.getName().equals(new Delta().getName())) {
            return deltaGate;
        } else if (airline.getName().equals(new Southwest().getName())) {
            return swGate;
        } else {
            return alaskaGate;
        }
    }

    private void addPassenger(Airline airline, Passenger passenger) throws IOException {
        synchronized (reservations) {
            fIs.getChannel().position(0); //Set the reader head to the start of the file.
            br = new BufferedReader(new InputStreamReader(fIs));
            ArrayList<String> fileContents = new ArrayList<>();

            while (true) { // Copy the entire file into an arrayList.
                String currLine = br.readLine();
                fileContents.add(currLine);
                if (currLine.equals("EOF")) {
                    break;
                }
            }

            FileWriter fw = new FileWriter(reservations); // This declaration will clear the file.
            PrintWriter pw = new PrintWriter(fw);

            int numPassengers = -1; //First find the airline's passenger capacity. Set to -1 to avoid false positives.
            int i = 0;
            while(true) {
                if (fileContents.get(i).equals(airline.getName().toUpperCase())) { // Loop until it hits the passenger's airline.
                    numPassengers = Integer.parseInt(fileContents.get(i + 1).substring
                            (0, fileContents.get(i + 1).indexOf('/'))); //Update numPassengers to latest value.
                } else {
                    i++;
                    continue;
                }
                if (numPassengers == Integer.parseInt(fileContents.get(i + 1).substring
                        (fileContents.get(i + 1).indexOf('/') + 1))) {
                    oos.writeObject("full");
                    oos.flush();
                    break;
                }

                // If the code is here, then we know that i is currently at AIRLINE_NAME.
                fileContents.add(i + 3, "---------------------" + airline.getName());
                fileContents.add(i + 3, passenger.toString());

                numPassengers++;
                fileContents.set(i + 1, numPassengers + "/" + airline.getCapacity());
                break;
            }

            for (int x = 0; x < fileContents.size(); x++) { //Now that fileContents is properly updated, write into file
                pw.println(fileContents.get(x));
            }
            //Close the pw and fw objects. This is fine because re-running this method should open it again.
            pw.close();
            fw.close();

            oos.writeObject("added");
            oos.flush();
        }
    }

    private ArrayList<String> getSouls(Airline airline) throws IOException {
        synchronized (reservations) {
            fIs.getChannel().position(0); // Set the reader head to the beginning of the file.
            br = new BufferedReader(new InputStreamReader(fIs));

            ArrayList<String> souls = new ArrayList<>();

            while (true) {
                String s = br.readLine(); //Read in the line.
                // Start the copying process only once we've reached the airline's passenger list.
                if (s.equals(airline.getName() + " passenger list")) { // We are currently on AIRLINE passenger list line.
                    String passengerInfo = br.readLine();
                    while (true) {
                        if (passengerInfo != null && passengerInfo.equals("End of passenger list")) { // Once we reach the end of the list, return.
                            return souls;
                        }
                        souls.add(passengerInfo); //Read in the passenger information.
                        br.readLine(); // Advance to skip ----------AIRLINE
                        passengerInfo = br.readLine();
                    }
                }
            }
        }
    }
}
