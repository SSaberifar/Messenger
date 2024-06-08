package messengerApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final List<User> users = new ArrayList<>();
    private static final int PORT = 8888;
    private static final int THREAD_POOL_SIZE = 10;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new ClientHandler(socket));
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server exception: ", e);
        } finally {
            executorService.shutdown();
        }
    }

    public static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.println("Enter name:");
                String name = in.readLine();

                out.println("Enter id:");
                String id = in.readLine();

                synchronized (users) {
                    users.add(new User(name, id));
                }

                String message;
                while ((message = in.readLine()) != null) {
                    out.println("Echo: " + message);

                }

            } catch (IOException e) {
                logger.log(Level.WARNING, "Client handler exception: ", e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Failed to close socket: ", e);
                }
            }
        }
    }
}
