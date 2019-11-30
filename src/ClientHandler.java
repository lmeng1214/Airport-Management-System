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
    private FileInputStream fIs;
    private BufferedReader br;
    private Socket clientSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private int numPassengers = 0;
    private String deltaGate;
    private String swGate;
    private String alaskaGate;


    public ClientHandler(Socket clientSocket, File reservations, String deltaGate,
                            String swGate, String alaskaGate)
            throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        oos.flush();
        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (SocketException e) {
            clientSocket.close();
        }

        this.reservations = reservations;
        fIs = new FileInputStream(this.reservations);
        br = new BufferedReader(new InputStreamReader(fIs));
        //to reset line count
        //fIs.getChannel().position(0);
        //br = new BufferedReader(new InputStreamReader(fIs));

        this.deltaGate = deltaGate;
        this.swGate = swGate;
        this.alaskaGate = alaskaGate;
    }

    public void run() {

        try  {
            String query = "";

            while (!(query.equals("END_PROGRAM"))){ // Takes input until sent "END_PROGRAM"
                try {
                    query = (String) ois.readObject();
                } catch (NullPointerException | SocketException e) {
                    query = "END_PROGRAM";
                }
                switch (query) {
                    case "getGate" :
                        try {
                            Airline airline = (Airline) ois.readObject();
                            oos.writeObject(getGate(airline));
                            oos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "addPassenger" : //Client should query key addPassenger first, then Airline then Passenger
                        try {
                            Airline airline = (Airline) ois.readObject();
                            Passenger passenger = (Passenger) ois.readObject();
                            synchronized (reservations) {
                                addPassenger(airline, passenger);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "getSouls" : //Returns an arraylist of passengers on the airline given.
                        Airline airline = (Airline) ois.readObject();
                        oos.writeObject(getSouls(airline));
                        oos.flush();
                        synchronized (reservations) {
                            oos.writeObject(numPassengers);
                        }
                        oos.flush();
                        break;
                    case "getNumPassengers" :
                        airline = (Airline) ois.readObject();
                        synchronized (reservations) {//updates the number of passengers
                            ArrayList<String> update = getSouls(airline);
                        }
                        oos.writeObject(numPassengers);
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
        try {
            oos.close();
            ois.close();
            br.close();
            fIs.close();
        } catch (SocketException e) {
            //do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getGate(Airline airline) {
        String gate = "";

        if (airline.getName().equals(new Delta().getName())) {
            gate = deltaGate;
        } else if (airline.getName().equals(new Southwest().getName())) {
            gate = swGate;
        } else {
            gate = alaskaGate;
        }

        return gate;
    }

    public void addPassenger(Airline airline, Passenger passenger)
            throws IOException, ClassNotFoundException {
        ArrayList<String> fileContents = new ArrayList<>();
        while (true) { // Copy the entire file into an arrayList.
            String currLine = br.readLine();
            fileContents.add(currLine);
            if (currLine.equals("EOF")) {
                break;
            }
        }
        fIs.getChannel().position(0);
        br = new BufferedReader(new InputStreamReader(fIs));


        ArrayList throwAway = getSouls(airline); //updates numPassengers

        FileWriter fw = new FileWriter(reservations, false);
        PrintWriter pw = new PrintWriter(fw);
        boolean added = false;
        int position = 0;
        if (numPassengers == airline.getCapacity()) {
            oos.writeObject("full");
            oos.flush();
        } else {
            if (airline.getName().equals(new Delta().getName())) {
                while (!added) {
                    if (fileContents.get(position).equals(airline.getName().toUpperCase())) {
                        fileContents.add
                                (position + 3, "---------------------DELTA");
                        fileContents.add(position + 3, passenger.toString());
                        added = true;
                    } else {
                        position++;
                    }
                }
            } else if (airline.getName().equals(new Southwest().getName())) {
                while (!added) {
                    if (fileContents.get(position).equals(airline.getName().toUpperCase())) {
                        fileContents.add
                                (position + 3, "---------------------Southwest");
                        fileContents.add(position + 3, passenger.toString());
                        added = true;
                    } else {
                        position++;
                    }
                }
            } else if (airline.getName().equals(new Alaska().getName())) {
                while (!added) {
                    if (fileContents.get(position).equals(airline.getName().toUpperCase())) {
                        fileContents.add(position + 3, "---------------------DELTA");
                        fileContents.add(position + 3, passenger.toString());
                        added = true;
                    } else {
                        position++;
                    }
                }
            }
            numPassengers++;
            fileContents.set
                    (position + 1, numPassengers + "/" + airline.getCapacity());

            for (int i = 0; i < fileContents.size(); i++) {
                pw.println(fileContents.get(i));
            }
            pw.close();

            oos.writeObject("added");
            oos.flush();

            fIs.getChannel().position(0);
            br = new BufferedReader(new InputStreamReader(fIs));
        }
    }

    public ArrayList<String> getSouls(Airline airline) throws IOException {
        ArrayList<String> souls = new ArrayList<>();
        String s = br.readLine();
        boolean read = true;
        int numPassengers = 0;
        boolean initializedCapacity = false;

        while(!(s.equals("EOF")) && read) {
            if (s.contains(airline.getName().toUpperCase())) {
                while(true) {
                    if (!initializedCapacity) {
                        if (s.contains("/")) {
                            numPassengers = Integer.parseInt(s.substring(0, s.indexOf("/")));
                            if (numPassengers > airline.getCapacity()) {
                                numPassengers = airline.getCapacity();
                            }
                            initializedCapacity = true;
                        }
                    }
                    if (((s == null || s.isBlank() || s.isEmpty()) && (numPassengers == souls.size()))) {
                        read = false;
                        break;
                    }
                    if ((s.contains(".") && s.contains(","))) {
                        souls.add(s);
                    }
                    s = br.readLine();
                    if((s.equals("ALASKA") || s.equals("DELTA") || s.equals("SOUTHWEST"))) {
                        read = false;
                        break;
                    }

                }
            }
            s = br.readLine();
            if (s == null) {
                break;
            }
        }
        fIs.getChannel().position(0);
        br = new BufferedReader(new InputStreamReader(fIs));

        this.numPassengers = numPassengers;
        return souls;
    }
}
