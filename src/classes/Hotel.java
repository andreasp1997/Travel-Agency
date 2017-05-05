package classes;

import java.util.ArrayList;

/**
 * Created by Mattias on 2017-05-03.
 */
public class Hotel {

    public int hotelId;
    public String hotelName;
    public String hotelCity;
    public ArrayList<Integer> rooms = new ArrayList<>();

    public Hotel(int hotelId, String hotelName, String hotelCity) {

        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelCity = hotelCity;

    }

    public ArrayList<Integer> rooms() {

        DBHandler dbh = new DBHandler();

        rooms = dbh.getRooms(this.hotelId);

        return rooms;

    }

}