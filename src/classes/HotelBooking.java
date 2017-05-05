package classes;

/**
 * Created by Mattias on 2017-04-12.
 */
public class HotelBooking {
    //Variables
    private int hotelId;
    private int roomId;
    private int roomSize;
    private String checkinDate;
    private String checkoutDate;

    public HotelBooking(int hotelId, int roomSize, String checkinDate, String checkoutDate) {

        this.hotelId = hotelId;
        this.roomSize = roomSize;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;

    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
