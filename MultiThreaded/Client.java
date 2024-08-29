package MultiThreaded;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

//We are creating multiple threads in Client as there is only one client and we want to send multiple request to the server using this client
//So every thread will act as request 
public class Client {
    //Overriding rn method using annonymous class
    public Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run(){
                int port = 8010;
                try {
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address, port);
                    try (
                        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                    ) {
                        toSocket.println("Hello from Client " + socket.getLocalSocketAddress());
                        String line = fromSocket.readLine();
                        System.out.println("Response from Server " + line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // The socket will be closed automatically when leaving the try-with-resources block
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public static void main(String[] args) {
        Client c=new Client();
        //We are creating hundread threads for requesting into the server 
        for(int i=0;i<100;i++){
            try {
                //We are creating threads every time and every time run method is calling
                //In run method we are creating Socket(new Socket) so every time new socket is created with different different ports assingned by OS
                Thread thread=new Thread(c.getRunnable());
                thread.start();
                //This sleep is provided because server cannot properly working 
                //Server is overwhelmed and cannot accepts this number of request
                //So we provide sleep in order to provide some time to sever
                Thread.sleep(10);
            } catch (Exception e) {
                return;
            }
        }
    }
}
