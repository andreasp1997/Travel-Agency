package classes;

import java.util.ArrayList;

/**
 * Created by Jun on 2017-04-12.
 */
public class NormalAccountSettings extends AccountSettings {

    private static NormalAccountSettings instance = null;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    private ArrayList<String> flightBookingForUser;
    private ArrayList<String> cruiseBookingForUser;
    private ArrayList<String> hotelBookingForUser;
    private ArrayList<String> carRentalBookingForUser;

    DBHandler dbh = new DBHandler();

    public NormalAccountSettings() {

    }

    public static NormalAccountSettings getInstance() {
        if(instance == null) {
            instance = new NormalAccountSettings();
        }
        return instance;
    }

    @Override
    public void commitChanges() {

        dbh.getUserID(Singleton.getInstance().getUsername());
        dbh.updateUserInfo(Singleton.getInstance().getUserID(), NormalAccountSettings.getInstance().getUsername(),
                NormalAccountSettings.getInstance().getPassword(), NormalAccountSettings.getInstance().getEmail(),
                NormalAccountSettings.getInstance().getFirstName(), NormalAccountSettings.getInstance().getLastName());

    }

    public void deleteAccount() {

        dbh.getUserID(Singleton.getInstance().getUsername());

        dbh.getFlightBookingIDforUser(Singleton.getInstance().getUserID());
        dbh.getCruiseBookingIDforUser(Singleton.getInstance().getUserID());
        dbh.getHotelBookingIDforUser(Singleton.getInstance().getUserID());
        dbh.getCarRentalBookingIDforUser(Singleton.getInstance().getUserID());

        flightBookingForUser = Singleton.getInstance().getFlightBookingIDforUser();
        cruiseBookingForUser = Singleton.getInstance().getCruiseBookingIDforUser();
        hotelBookingForUser = Singleton.getInstance().getHotelBookingIDforUser();
        carRentalBookingForUser = Singleton.getInstance().getCarRentalBookingIDforUser();

        if(flightBookingForUser != null){
            for(int i = 0; i < flightBookingForUser.size(); i++){
                dbh.removeFlightBookingForDeletedAccount(Singleton.getInstance().getUserID(), String.valueOf(flightBookingForUser.get(i)));
                flightBookingForUser.remove(i);
            }
        }

        if(cruiseBookingForUser != null){
            for(int i = 0; i < cruiseBookingForUser.size(); i++){
                dbh.removeCruiseBookingForDeletedAccount(Singleton.getInstance().getUserID(), String.valueOf(cruiseBookingForUser.get(i)));
                cruiseBookingForUser.remove(i);
            }
        }

        if(hotelBookingForUser != null){
            for(int i = 0; i < hotelBookingForUser.size(); i++){
                dbh.removeHotelBookingForDeletedAccount(Singleton.getInstance().getUserID(), String.valueOf(hotelBookingForUser.get(i)));
                hotelBookingForUser.remove(i);
            }
        }

        if(carRentalBookingForUser != null){
            for(int i = 0; i < carRentalBookingForUser.size(); i++){
                dbh.removeCarRentalBookingForDeletedAccount(Singleton.getInstance().getUserID(), String.valueOf(carRentalBookingForUser.get(i)));
                carRentalBookingForUser.remove(i);
            }
        }

        dbh.deleteUser(Singleton.getInstance().getUserID());
        dbh.updateUserID(Singleton.getInstance().getUserID());

    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

}
