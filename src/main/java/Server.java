import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static List<User> users = new ArrayList<>();
    private static final int port = 8888;

    public static void main(String args[]){

        String hostname = "127.0.0.1";

        try(ServerSocket serverSocket = new ServerSocket(port)){

            while(true){
                Socket socket = serverSocket.accept();
                new ClientHandler(socket);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static class ClientHandler extends Thread {

        private Socket socket;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){

                out.println("Enter name:");
                String name = in.readLine();

                out.println("Enter password:");
                String password = in.readLine();

                synchronized (users) {
                    users.add(new User(name, password));
                }
                String message;
                while ((message = in.readLine()) != null) {
                    out.println("Echo: " + message);
                }
            }catch (IOException e){
                e.printStackTrace();
            }


        }
    }
}
