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
            String authentication = authenticateUser(newUser);
            output.writeUTF(authentication);
                        
            while(choice != "exit") {
                String menuMessage = showMenu();
                output.writeUTF(menuMessage);
                String responseMenuMsg = input.readUTF();
                
                if (responseMenuMsg.equalsIgnoreCase("1")) {
                    output.writeUTF(showBalance());
                } else if (responseMenuMsg.equalsIgnoreCase("2")) {
                    String message = showMenuOrder();
                    output.writeUTF(message);
                    choice = input.readUTF();
                    float option = Float.parseFloat(choice);
                    String response = checkMoney(option);
                    output.writeUTF(response);
                    
                }                         
            }           
            
        } catch (IOException | CloneNotSupportedException ex) {
            Logger.getLogger(UsersThread.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
     
    public String authenticateUser(User user) throws CloneNotSupportedException {
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
                return "User authenticated!";
            }
        }
        System.out.println("Registration or password is wrong");
        return "Registration or password is wrong";
     }
     
    public void log(String message) {
        System.out.println("Server message: " + message);
    }
    
    public void sendingLog(String message) {
        System.out.println("Sending message: " + message);
    }
    
    public String showMenuOrder() {
        String entrance = "What is the price of your dish? \n"
                + "Fish with carrots (R$: 399.00) \n"
                + "Pasta with potatoes (R$: 566.00) \n"
                + "Rice and beans (R$: 622.00) \n"
                + "Type 'exit' to leave the menu!";
        return entrance;
    }
    
    public String checkMoney(float price) {
        if(price > authenticatedUser.getMoney()) {
            return "\n\nYou don't have enough money to buy it\n\n";
        }
        authenticatedUser.discountMoney(price);
        return "\n\nYou will have it soon.\n\n";
    }
    
    public String showBalance() {
        Float money = authenticatedUser.getMoney();
        String convertedMoney = Float.toString(money);
        
        return "Your balance is " + convertedMoney;
    }
    
    public String showMenu() {
        String menu = "Choose one of the options below: \n"
                + "1- Check balance \n"
                + "2- Order dish \n"
                + "3- Exit";
        return menu;
    }
}
