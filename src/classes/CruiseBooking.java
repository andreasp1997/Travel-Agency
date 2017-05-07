package classes;

/**
 * Created by Paolo9517 on 2017-04-12.
 */
public class CruiseBooking   {
    public static CruiseBooking instance = null;
    private String origin;
    private String destination;
    private String date;
    private String room;

    public static CruiseBooking getInstance() {
        if(instance == null) {
            instance = new CruiseBooking();
        }
        return instance;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String rooms) {
        this.room = rooms;
    }
}
