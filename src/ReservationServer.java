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
    private String deltaGate;
    private String southWestGate;
    private String alaskaGate;

    public ReservationServer() throws IOException {
        serverSocket = new ServerSocket(64114);
        Gate gateMaker = new Gate();
        deltaGate = gateMaker.generateGate();
        southWestGate = gateMaker.generateGate();
        alaskaGate = gateMaker.generateGate();
    }

    public void start() throws IOException, ClassNotFoundException {
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

        reservations = new File("reservations.txt");
        PrintWriter pw = new PrintWriter(reservations);

        Airline airlines[] = {new Delta(), new Alaska(), new Southwest()};
        for (int i = 0; i < 3; i++) {
            pw.println(airlines[i].getName().toUpperCase());
            pw.println("0/" + airlines[i].getCapacity());
            pw.println(airlines[i].getName() + " passenger list");
            pw.println();
        }
        pw.println("EOF");
        pw.close();

        FileInputStream fis = new FileInputStream(reservations);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String s = br.readLine();
        while (true) {
            if(!(s == null || s.isEmpty() || s.isBlank())) {
                if (s.equals("EOF")) {
                    break;
                }
                s = br.readLine();
            } else {
                s = br.readLine();
            }
        }




        System.out.println("Server started on port " + this.serverSocket.getLocalPort());

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, reservations,
                    deltaGate, southWestGate, alaskaGate);
            new Thread(clientHandler).start();

            /**
             *         ServerSocket serverSocket = new ServerSocket(4343);
             *
             *         Loop{
             * Socket socket = serverSocket.accept();
             *             EchoServer server = new EchoServer(socket);
             *                         new Thread(server).start();
             *                         }
             */
        }
    }

}
