/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedsnackbarserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author calvin
 */
public class UsersThread extends Thread {
    
    private String choice = null;
    private User authenticatedUser;
    private Socket listenSocket = null;

    public UsersThread(Socket listenSocket) {
        this.listenSocket = listenSocket;
    }

    @Override
    public void run() {
         
        try {
            DataInputStream input = new DataInputStream(listenSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(listenSocket.getOutputStream());
            
            String incomingRegistration  = input.readUTF();
            log(incomingRegistration);
            
            String incomingPassword  = input.readUTF();
            log(incomingPassword);
            
            User newUser = new User(incomingRegistration, incomingPassword);
            authenticateUser(newUser);
            String message = showMenu();
            output.writeUTF(message);
            
            while(choice != "exit") {
                choice = input.readUTF();
                float option = Float.parseFloat(choice);
                String response = checkMoney(option);
                output.writeUTF(response);
            }
            
            
            
        } catch (IOException | CloneNotSupportedException ex) {
            Logger.getLogger(UsersThread.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
     
    public void authenticateUser(User user) throws CloneNotSupportedException {
        UsersBuilder users = new UsersBuilder();
        ArrayList usersFromDb = users.getUsers();       
        
        for(int i=0; i<usersFromDb.size(); i++) {
            User chosen = (User) usersFromDb.get(i);
            if(
                    chosen.getRegistration().equalsIgnoreCase(user.getRegistration()) && 
                    chosen.getPassword().equalsIgnoreCase(user.getPassword())
               ) {
                authenticatedUser = (User) chosen.clone();
                System.out.println("User authenticated!");
                return;
            }
        }
        System.out.println("Registration or password is wrong");
     }
     
    public void log(String message) {
        System.out.println("Server message: " + message);
    }
    
    public void sendingLog(String message) {
        System.out.println("Sending message: " + message);
    }
    
    public String showMenu() {
        String entrance = "What is the price of your dish:\n";
        String menu = 
                "Fish with carrots (R$: 3.00) "
                + "\nPasta with potatoes (R$: 5.00) "
                + "\nRice and beans (R$: 6.00)";
        String result = entrance + menu;
        return result;
    }
    
    public String checkMoney(float price) {
        if(price > authenticatedUser.getMoney()) {
            return "You don't have enough money to buy it";
        }
        authenticatedUser.discountMoney(price);
        return "You will have it soon.";
    }
}
