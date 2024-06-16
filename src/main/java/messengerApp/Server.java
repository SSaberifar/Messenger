package messengerApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.runtime.TemplateRuntime;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
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

    private static HashMap<String, MessageQueue<String>> messages = new HashMap<>();
    private static HashMap<User, String> userStatus = new HashMap<>();

    public static void addAll() {
        users.add(new User("Alice", "Smith", "1", "alice", "1"));
        users.add(new User("Bob", "Johnson", "2", "bob", "12"));
        users.add(new User("Rob", "Walker", "3", "RobW", "123"));
    }
    public static void getMessage(String username , PrintWriter out) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getMessages().isEmpty()) {
                    out.println("No new messages!");
                } else {
                    for (String message : user.getMessages())
                        out.println("Message: " +message);
                }
                user.getMessages().clear();
            }
            return;
        }

        System.out.println("User not found!");
    }

    public static void sendMessage(String sender , String recipient , String messageContent , PrintWriter out) {
        for (User user : users){
            if (user.getUsername().equals(recipient)){
                user.addMessage("from_" + sender + ": " + messageContent);
                out.println("Message sent to " + recipient);
                return;
            }
        }
        out.println("User " + recipient + " not found.");
    }

    public static void LogIn(String username, PrintWriter out, BufferedReader in) throws IOException {
       while(true){
           out.println("1-Send message:");
           out.println("2-get message");
           out.println("3-Log out:");
           out.println("please enter number:");
           int ans2 = scanner.nextInt();
           switch (ans2) {
               case 1:
                   out.println("Enter recipient username:");
                   String recipient = in.readLine();
                   out.println("Enter message:");
                   String message = in.readLine();
                   sendMessage(username, recipient, message, out);
                   break;
               case 2:
                   getMessage(username, out);
                   break;
               case 3:
                   userStatus.put(new User(username, null, null, null, null), "offline");
                   return;
           }
       }
    }


    public static void mainMenu(String username , PrintWriter out , BufferedReader in) throws IOException {
        while(true){
            out.println("1-Log in:");
            out.println("2-Delete:");
            out.println("Please enter number:");
            int choice = Integer.parseInt(in.readLine());
            switch (choice) {
                case 1:
                    userStatus.put(new User(username, null, null, null, null), "online");
                    LogIn(username, out, in);
                    break;
                case 2:
                    synchronized (users){
                    for (User user1 : users) {
                        if (user1.getUsername().equals(username)) {
                            users.remove(user1);
                        }
                    }
                }
                    return;

            }
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
                    out.println("user added");
                    users.add(new User(fisrtname, lastname, id, username, password));
                    mainMenu(username , out, in);
                }

//                String message;
//                while ((message = in.readLine()) != null) {
//                    out.println("Echo: " + message);
//                }

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
