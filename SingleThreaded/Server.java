package SingleThreaded;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 */
public class Server {

    public void run() throws IOException{
        int PORT=8011;
        //CREATING A SOCKET AT PORT 8011
        //This socket is used for listening purpose only
        ServerSocket socket=new ServerSocket(PORT);
        //In every 10s gap it will throw a exception SocketTimeoutException
        socket.setSoTimeout(10000);
        while(true){
            try{
                System.out.println("Server is running at port: "+PORT);
                //Accept method will trigger only when a request will be sended
                //And whenever new request received another socket of acceptedConnection is created which we use for data transmission
                Socket acceptedConnection=socket.accept();
                //Printing the local socket address of the client who is sending the request
                System.out.println("Connection accepted from Client from"+acceptedConnection.getLocalSocketAddress());
                //Sending message from Server -> Client using Outstream [Printwriter=> Character oriented Stream]
                PrintWriter toCLient=new PrintWriter(acceptedConnection.getOutputStream(),true);
                //Receiving message from Client -> Server using InputStream[BufferedReader => Character Oriented Stream]
                BufferedReader fromClient=new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
                toCLient.println("Hello from Server");
                String line=fromClient.readLine();
                System.out.println("Request receivrd from client:"+line);
                toCLient.close();
                fromClient.close();
                //Note: It is closing that connection(acceptedConnection) and again start listening for request
                acceptedConnection.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Server s=new Server();
        try{
            s.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}