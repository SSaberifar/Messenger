import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.util.Formatter;
import java.util.Scanner;

public class Alice {

    public static void main(String args[]) throws IOException {

        String hostname = "localhost";
        int port = 1234;
        try (Socket socket = new Socket(hostname, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println(in.readLine());
            out.println("Alice");

            System.out.println(in.readLine());
            out.println(console.readLine());

            String message;
            while ((message = console.readLine()) != null) {
                out.println(message);
                System.out.println(in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + hostname);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
