package classes;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Created by andreas on 2017-04-19.
 */
public class Singleton {
    private static Singleton instance = null;

    private SimpleStringProperty usernameList = new SimpleStringProperty();
    private SimpleStringProperty userIDnumber = new SimpleStringProperty();
    private SimpleStringProperty userRole = new SimpleStringProperty();
    private SimpleStringProperty username = new SimpleStringProperty();
    private ArrayList<String> cities = new ArrayList<>();
    private ArrayList<String> europeanCities = new ArrayList<>();
    private ArrayList<String> northAmericanCities = new ArrayList<>();
    private ArrayList<String> asianCities = new ArrayList<>();
    private ArrayList<String> australianCities = new ArrayList<>();
    private SimpleStringProperty cityID = new SimpleStringProperty();
    private SimpleStringProperty pickedUser = new SimpleStringProperty();
    private SimpleStringProperty checkedFlight = new SimpleStringProperty();
    private SimpleStringProperty flightIDCount = new SimpleStringProperty();
    private SimpleStringProperty flightID = new SimpleStringProperty();
    private SimpleStringProperty flightBookingIDCount = new SimpleStringProperty();
    private SimpleStringProperty userID = new SimpleStringProperty();
    private SimpleStringProperty airlines = new SimpleStringProperty();
    private SimpleStringProperty bookedTicketsForFlight = new SimpleStringProperty();
    private SimpleStringProperty cruiseID = new SimpleStringProperty();
    private ArrayList<String> cars = new ArrayList<>();
    private SimpleStringProperty carID = new SimpleStringProperty();
    private SimpleStringProperty carBookingsIDAmount = new SimpleStringProperty();
    private SimpleStringProperty checkedCruise = new SimpleStringProperty();
    private SimpleStringProperty cruiseIDcount = new SimpleStringProperty();
    private SimpleStringProperty cruiseBookingsIDAmount = new SimpleStringProperty();
    private SimpleStringProperty roomsBooked = new SimpleStringProperty();
    private SimpleStringProperty carPrice = new SimpleStringProperty();
    private SimpleStringProperty carBookingsForDate = new SimpleStringProperty();
    private SimpleStringProperty carAmount = new SimpleStringProperty();

    public Singleton() {

    }

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void setUsernameList (String s) { this.usernameList.set(s); }

    public String getUsernameList(){
        return usernameList.get();
    }

    public void setUserIDnumber (String s){
        this.userIDnumber.set(s);
    }

    public String getUserIDnumber(){ return userIDnumber.get(); }

    public void setUserRole (String s) { this.userRole.set(s); }

    public String getUserRole() { return userRole.get(); }

    public void setUsername (String s) { this.username.set(s); }

    public String getUsername() { return username.get(); }

    public void setCities (ArrayList<String> s) { this.cities = s; }

    public ArrayList<String> getCities() { return cities; }

    public void setEuropeanCities (ArrayList<String> s) { this.europeanCities = s; }

    public ArrayList<String> getEuropeanCities() { return europeanCities; }

    public void setNorthAmericanCities (ArrayList<String> s) { this.northAmericanCities = s; }

    public ArrayList<String> getNorthAmericanCities() { return northAmericanCities; }

    public void setAsianCities (ArrayList<String> s) { this.asianCities = s; }

    public ArrayList<String> getAsianCities() { return asianCities; }

    public void setAustralianCities (ArrayList<String> s) { this.australianCities = s; }

    public ArrayList<String> getAustralianCities() { return australianCities; }

    public String getCityID() { return cityID.get(); }

    public void setCityID(String cityID) { this.cityID.set(cityID); }

    public String getPickedUser() { return pickedUser.get(); }

    public void setPickedUser(String pickedUser) { this.pickedUser.set(pickedUser); }

    public String getCheckedFlight() { return checkedFlight.get(); }

    public void setCheckedFlight(String checkedFlight) { this.checkedFlight.set(checkedFlight); }

    public String getFlightIDCount() { return flightIDCount.get(); }

    public void setFlightIDCount(String flightIDCount) { this.flightIDCount.set(flightIDCount); }

    public String getFlightID() { return flightID.get(); }

    public void setFlightID(String flightID) { this.flightID.set(flightID); }

    public String getFlightBookingIDCount() { return flightBookingIDCount.get(); }

    public void setFlightBookingIDCount(String flightBookingIDCount) { this.flightBookingIDCount.set(flightBookingIDCount); }

    public String getUserID() { return userID.get(); }

    public void setUserID(String userID) { this.userID.set(userID); }

    public String getBookedTicketsForFlight() { return bookedTicketsForFlight.get(); }

    public void setBookedTicketsForFlight(String bookedTicketsForFlight) { this.bookedTicketsForFlight.set(bookedTicketsForFlight);}

    public void setCars (ArrayList<String> s) { this.cars = s; }

    public ArrayList<String> getCars() { return cars; }

    public String getCarID() { return carID.get(); }

    public void setCarID(String carID) { this.carID.set(carID); }

    public String getCarBookingsIDAmount() {
        return carBookingsIDAmount.get();
    }

    public void setCarBookingsIDAmount(String carBookingsIDAmount) { this.carBookingsIDAmount.set(carBookingsIDAmount);}

    public String getCruiseID() { return cruiseID.get(); }

    public void setCruiseID(String cruiseID) { this.cruiseID.set(cruiseID); }

    public String getCheckedCruise() {
        return checkedCruise.get();
    }

    public void setCheckedCruise(String checkedCruise) {
        this.checkedCruise.set(checkedCruise);
    }

    public String getCruiseIDcount() {
        return cruiseIDcount.get();
    }

    public void setCruiseIDcount(String cruiseIDcount) {this.cruiseIDcount.set(cruiseIDcount);}

    public String getCruiseBookingsIDAmount() {
        return cruiseBookingsIDAmount.get();
    }

    public void setCruiseBookingsIDAmount(String cruiseBookingsIDAmount) {this.cruiseBookingsIDAmount.set(cruiseBookingsIDAmount);}

    public String getRoomsBooked() {
        return roomsBooked.get();
    }

    public void setRoomsBooked(String roomsBooked) {
        this.roomsBooked.set(roomsBooked);
    }

    public String getCarPrice() { return carPrice.get(); }

    public void setCarPrice(String carPrice) { this.carPrice.set(carPrice); }

    public String getCarBookingsForDate() { return carBookingsForDate.get(); }

    public void setCarBookingsForDate(String carBookingsForDate) { this.carBookingsForDate.set(carBookingsForDate); }

    public String getCarAmount() { return carAmount.get(); }

    public void setCarAmount(String carAmount) { this.carAmount.set(carAmount); }
}
