package classes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by andreas on 2017-04-19.
 */
public class Singleton {
    private static Singleton instance = null;

    private SimpleStringProperty s1 = new SimpleStringProperty();
    private SimpleStringProperty s2 = new SimpleStringProperty();

    public Singleton() {

    }

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void setUsernameList (String s){
        this.s1.set(s);
    }

    public String getUsernameList(){
        return s1.get();
    }

    public void setUserIDnumber (String s){
        this.s2.set(s);
    }

    public String getUserIDnumber(){
        return s2.get();
    }


}
