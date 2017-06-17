/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedsnackbarserver;

import java.util.ArrayList;

/**
 *
 * @author calvin
 */
public class UsersBuilder {
    
    private final ArrayList<User> users = new ArrayList<>();
    private User numberOne;
    private User numberTwo;
    private User numberThree;

    public UsersBuilder() {        
        this.buildUsers();
    }      
    
    private void buildUsers()
    {
        numberOne = new User("1234", "1");
        numberOne.setMoney(3000);
        numberTwo = new User("2345", "12");
        numberTwo.setMoney(4000);
        numberThree = new User("9090", "123");
        numberThree.setMoney(0);
        
        users.add(numberOne);
        users.add(numberTwo);
        users.add(numberThree);  
    }

    public ArrayList<User> getUsers() {
        return users;
    }    
}
