package messengerApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Alice {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 5000;
    private static final Logger logger = Logger.getLogger(Alice.class.getName());

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOSTNAME, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            // Read and display server messages
            System.out.println(in.readLine());
            out.println(console.readLine()); // fistname prompt response

            System.out.println(in.readLine());
            out.println(console.readLine()); // lastname prompt response

            System.out.println(in.readLine());
            out.println(console.readLine()); // id prompt response

            System.out.println(in.readLine());
            out.println(console.readLine()); // pass prompt response

            System.out.println(in.readLine());
            out.println(console.readLine()); // username prompt response

            String message;
            while (true) {
                System.out.print("Enter message: ");
                message = console.readLine();
                if (message == null || message.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(message);
                System.out.println("Server response: " + in.readLine());
            }

        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Unknown host: " + HOSTNAME, e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O error: ", e);
        }
    }
}