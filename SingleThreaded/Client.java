package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public void run() throws UnknownHostException,IOException{
        int port=8011;
        //We are acquiring IP address of our localhost using this 
        InetAddress address=InetAddress.getByName("localhost");//localhost -> loopback address[127.0.0.1]
        Socket socket=new Socket(address,port);
        PrintWriter toSocket=new PrintWriter(socket.getOutputStream(),true);
        BufferedReader fromSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toSocket.println("Hi from Client");
        String line=fromSocket.readLine();
        System.out.println("Response from Server: "+line);
        toSocket.close();
        fromSocket.close();
        socket.close();
    }
    public static void main(String[] args) {
        Client c1=new Client();
        try {
            c1.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
