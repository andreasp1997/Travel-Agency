package classes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Jun on 2017-04-12.
 */
public class FlightBooking {

    private static FlightBooking instance = null;

    private String airline;
    private String origin;
    private String destination;
    private String date;
    private double price;
    private int seats;

    public FlightBooking() {

    }

    public static FlightBooking getInstance() {
        if(instance == null) {
            instance = new FlightBooking();
        }
        return instance;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public int getSeats() { return seats; }

    public void setSeats(int seats) { this.seats = seats; }
}
