package classes;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Created by andreas on 2017-04-19.
 */
public class Singleton {
    private static Singleton instance = null;

    private SimpleStringProperty s1 = new SimpleStringProperty();
    private SimpleStringProperty s2 = new SimpleStringProperty();
    private SimpleStringProperty s3 = new SimpleStringProperty();
    private SimpleStringProperty s4 = new SimpleStringProperty();
    private ArrayList<String> cities = new ArrayList<>();

    public Singleton() {

    }

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void setUsernameList (String s) { this.s1.set(s); }

    public String getUsernameList(){
        return s1.get();
    }

    public void setUserIDnumber (String s){
        this.s2.set(s);
    }

    public String getUserIDnumber(){
        return s2.get();
    }

    public void setUserRole (String s) { this.s3.set(s); }

    public String getUserRole() { return s3.get(); }

    public void setUsername (String s) { this.s4.set(s); }

    public String getUsername() { return s4.get(); }

    public void setCities (ArrayList<String> s) { this.cities = s; }

    public ArrayList<String> getCities() { return cities; }


}
