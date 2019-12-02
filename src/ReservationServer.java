import java.io.*;
import java.lang.reflect.AnnotatedArrayType;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Where the server holds stores information.
 *
 * @author Lenny Meng, meng110@purdue.edu
 * @version Nov 25 2019
 *
 * @author Janis Vuskalns
 * @version 11/29/2019
 */

public class ReservationServer {
    private ServerSocket serverSocket;
    private File reservations;

    public ReservationServer() throws IOException {
        serverSocket = new ServerSocket(64114);
    }

    public void start() throws IOException {
        //Initialize reservations.txt
        /**
         * format
         * ALASKA
         * 3/100
         * Alaska passenger list
         *
         * FirstInitial LastName, Age
         * ---------------------ALASKA
         * FirstInitial LastName, Age
         * ---------------------ALASKA
         * FirstInitial LastName, Age
         * ---------------------ALASKA
         *
         * DELTA
         * 2/200
         * Delta passenger list
         * FirstInitial LastName, Age
         * ---------------------DELTA
         * FirstInitial LastName, Age
         * ---------------------DELTA
         *
         * SOUTHWEST
         * 0/100
         * Southwest passenger list
         *
         * EOF
         */

        reservations = new File("reservations.txt"); //Creating a new pw should clear the file.
        FileWriter fw = new FileWriter(reservations);
        PrintWriter pw = new PrintWriter(fw);

        Airline[] airlines = {new Delta(), new Alaska(), new Southwest()};
        for (int i = 0; i < 3; i++) { // Initialize the reservations.txt file with a clean template.
            pw.println(airlines[i].getName().toUpperCase());
            pw.println("0/" + airlines[i].getCapacity());
            pw.println(airlines[i].getName() + " passenger list");
            pw.println("End of passenger list");
            pw.println();
        }
        pw.println("EOF");

        pw.close();
        fw.close();

        System.out.println("Server started on port " + this.serverSocket.getLocalPort());

        while(true) {
            Socket clientSocket = serverSocket.accept(); // Waits for the client to connect
            ClientHandler clientHandler = new ClientHandler(clientSocket, reservations);
            new Thread(clientHandler).start();
        }
    }

}
