package classes;

import java.util.ArrayList;

/**
 * Created by Jun on 2017-04-12.
 */
public class AdminChangeUserSettings extends AccountSettings {

    private static AdminChangeUserSettings instance = null;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;

    private ArrayList<String> flightBookingForUser;
    private ArrayList<String> cruiseBookingForUser;
    private ArrayList<String> hotelBookingForUser;
    private ArrayList<String> carRentalBookingForUser;

    private DBHandler dbHandler = new DBHandler();

    public AdminChangeUserSettings() {

    }

    public static AdminChangeUserSettings getInstance() {
        if(instance == null) {
            instance = new AdminChangeUserSettings();
        }
        return instance;
    }

    @Override
    public void commitChange() {

        dbHandler.getUserID(Singleton.getInstance().getPickedUser());
        dbHandler.updateUserInfo(Singleton.getInstance().getUserID(), AdminChangeUserSettings.getInstance().getUserName(),
                AdminChangeUserSettings.getInstance().getPassword(), AdminChangeUserSettings.getInstance().getEmail(),
                AdminChangeUserSettings.getInstance().getFirstName(), AdminChangeUserSettings.getInstance().getLastName());

    }

    public void deleteUser() {

        dbHandler.getUserID(Singleton.getInstance().getPickedUser());

        //Deletes all booking for the user before deleting the user

        if(flightBookingForUser != null){
            for(int i = 0; i < flightBookingForUser.size(); i++){
                dbHandler.removeFlightBookingForDeletedAccount(Singleton.getInstance().getUserID(), String.valueOf(flightBookingForUser.get(i)));
                flightBookingForUser.remove(i);
            }
        }

        if(cruiseBookingForUser != null){
            for(int i = 0; i < cruiseBookingForUser.size(); i++){
                dbHandler.removeCruiseBookingForDeletedAccount(Singleton.getInstance().getUserID(), String.valueOf(cruiseBookingForUser.get(i)));
                cruiseBookingForUser.remove(i);
            }
        }

        if(hotelBookingForUser != null){
            for(int i = 0; i < hotelBookingForUser.size(); i++){
                dbHandler.removeHotelBookingForDeletedAccount(Singleton.getInstance().getUserID(), String.valueOf(hotelBookingForUser.get(i)));
                hotelBookingForUser.remove(i);
            }
        }

        if(carRentalBookingForUser != null){
            for(int i = 0; i < carRentalBookingForUser.size(); i++){
                dbHandler.removeCarRentalBookingForDeletedAccount(Singleton.getInstance().getUserID(), String.valueOf(carRentalBookingForUser.get(i)));
                carRentalBookingForUser.remove(i);
            }
        }

        dbHandler.deleteUser(Singleton.getInstance().getUserID());
        dbHandler.updateUserID(Singleton.getInstance().getUserID());

    }

    public String getUsername() { return username; }

    public void setUsername(String enteredUsername) { this.username = enteredUsername; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

}
