/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedsnackbarserver;

/**
 *
 * @author calvin
 */
public class User implements Cloneable{
    
    private String registration;
    private String password;
    private float money;
    
    public User(String registration, String password) {
        this.registration = registration;
        this.password = password;
    }    

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }    
    
    public String getRegistration() {
        return registration;
    }

    public void setMatricula(String registration) {
        this.registration = registration;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    

    @Override
    public String toString() {
        return "User{" + "registration=" + registration + ", password=" + password + ", money=" + money + '}';
    }
    
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    
    public void discountMoney(Float value) {
        this.money = this.money - value;
    }
}
