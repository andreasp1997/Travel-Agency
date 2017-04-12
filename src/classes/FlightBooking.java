package classes;

/**
 * Created by Jun on 2017-04-12.
 */
public class FlightBooking {
    private String airline;
    private String origin;
    private String destination;
    private String date;

    public FlightBooking(String airline, String origin, String destination, String date) {
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAirline() {
        return airline;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }
}
