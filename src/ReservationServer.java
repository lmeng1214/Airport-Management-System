import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.AnnotatedArrayType;
import java.net.ServerSocket;
import java.net.Socket;

public class ReservationServer {
    private ServerSocket serverSocket;
    private File reservations;

    public ReservationServer() throws IOException {
        serverSocket = new ServerSocket(0);
    }

    public void start() {
        //Initialize reservations.txt to
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

        reservations = new File("reservations.txt");
        Delta delta = new Delta();
        Southwest southwest = new Southwest();
        Alaska alaska = new Alaska();

        Airline[] airliners = {delta, southwest, alaska};

        try (PrintWriter pw = new PrintWriter(reservations)) {
            for (int i = 0; i < 3; i++) {
                pw.println("AIRLINE_NAME: " + airliners[i].getName());
                pw.println("BOOKED: 0/" + airliners[i].getCapacity());
                pw.println("ENDOFLIST");
            }
            pw.println("EOF");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Socket clientSocket;
        Thread clientThread;
        ClientHandler clientHandler;

        System.out.println("Server started on port " + this.serverSocket.getLocalPort());

        try {
            clientSocket = this.serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            clientHandler = new ClientHandler(clientSocket);
            clientThread = new Thread(clientHandler);

            clientThread.start();
        }
    }

}
