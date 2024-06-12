package messengerApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static final int PORT = 5000;
    private static final int THREAD_POOL_SIZE = 10;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void LogIn(String user) {
        System.out.println("1-Send message:");
        System.out.println("2-get message");
        System.out.println("3-Log out:");
        System.out.println("please enter number:");
        int ans2 = scanner.nextInt();
        switch (ans2) {
            case 1:

                break;
            case 2:

                break;
            case 3:
                menu1(user);
                break;
        }
    }

    public static void menu1(String user) {
        System.out.println("1-Log in:");
        System.out.println("2-Delete:");
        System.out.println("Please enter number:");
        int ans1 = scanner.nextInt();
        switch (ans1) {
            case 1:
                LogIn(user);
                break;
            case 2:
                for (User user1 : users) {
                    if (user1.getUsername().equals(user)) {
                        users.remove(user1);
                    }
                }
                break;

        }
    }

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

                out.println("Enter first name:");
                String fisrtname = in.readLine();

                out.println("Enter last name:");
                String lastname = in.readLine();

                out.println("Enter id:");
                String id = in.readLine();

                out.println("Enter password:");
                String password = in.readLine();

                out.println("Enter username:");
                String username = in.readLine();


                synchronized (users) {
                    users.add(new User(fisrtname, lastname, id, username, password));
                    menu1(username);
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
