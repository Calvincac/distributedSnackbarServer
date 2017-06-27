/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedsnackbarserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author calvin
 */
public class DistributedSnackbarServer {
    


    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        ServerSocket serverSocket;
        serverSocket = null;
        Socket listenSocket = null;   

        serverSocket = new ServerSocket(6800);
        System.out.println("Waiting for connection...");
        listenSocket = serverSocket.accept();
        System.out.println("Client connected!!");
        
        new UsersThread(listenSocket).start();
    }    

}
