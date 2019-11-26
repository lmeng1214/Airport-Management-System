import java.io.*;
import java.net.*;

public class ReservationClient {
    public static void main(String[] args)
            throws UnknownHostException, IOException, ClassNotFoundException {
        ResponseListener responseListener = new ResponseListener();

        responseListener.setHostName();
        responseListener.setPortNumber();
        Socket socket = new Socket
                (responseListener.getHostName(), responseListener.getPortNumber());

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();

        responseListener.run(oos, ois);
    }
}
