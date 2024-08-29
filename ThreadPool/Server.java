package ThreadPool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {
    private final ExecutorService threadPool;

    //It takes argument of integer type which is the size of thread pool
    public Server(int poolSize) {
        //Thread Pool in java is implemented by using ExecutorService
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    //This method handles each request that receives from client side
    public void handleClient(Socket clientSocket) {
        //try-with-resource is used so that the output stream closed automatically
        try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            toSocket.println("Hello from server " + clientSocket.getInetAddress());
            BufferedReader fromClient=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println(fromClient.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 50; // More pool size -> more request handling
        Server server = new Server(poolSize);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Use the thread pool to handle the client
                server.threadPool.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            server.handleClient(clientSocket);
                        }
                    }                    
                );
                //This execute method is used to submit the task for execution
                //The parameter accepts Runnable object and it can be provided using lamda expression or annonymos class
                //Actually annonymous class implements the runnable interface internally for that reason it is possible
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Shutdown the thread pool when the server exits
            server.threadPool.shutdown();
        }
    }
}
