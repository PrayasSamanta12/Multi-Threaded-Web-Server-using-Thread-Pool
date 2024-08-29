package MultiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer(){
        //This lamda expression overrides the accept method of Cosumer functional interface
        return (clientsocket)->{
            try{
                PrintWriter toSocket = new PrintWriter(clientsocket.getOutputStream(), true);
                toSocket.println("Hello from server " + clientsocket.getInetAddress());
                BufferedReader fromSocket=new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
                String line=fromSocket.readLine();
                System.out.println(line+"\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port " + port);
            while (true) {
                //Creating a new socket for communication whenever request is received
                Socket clientSocket = serverSocket.accept();

                // Create and start a new thread for each client
                //We are overriding the run method using lambda expression[Approach 1]
                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                //[Approch 2]-> Using Annonymous CLass
                // Thread thread2 = new Thread(new Runnable(){
                //     @Override
                //     public void run() {
                //         server.getConsumer().accept(clientSocket);
                //     }
                // });
                thread.start();
                //We are not closing the connection because if connection is closed then it will not accept any further requests
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
