package classes;

/**
 * Created by Paolo9517 on 2017-05-15.
 */
public class NormalAccountEditBooking extends EditBooking{
    DBHandler dbHandler = new DBHandler();
    @Override
    void commitChangesForFlightBooking() {

    }

    @Override
    void commitChangesForCruiseBooking() {

    }

    @Override
    void commitChangesForHotelBooking() {

    }

    @Override
    void commitChangesForCarRentalBooking() {

    }

    @Override
    void deleteFlightBooking() {
        dbHandler.getCityID(FlightBooking.getInstance().getOrigin());
        String origin = Singleton.getInstance().getCityID();
        dbHandler.getCityID(FlightBooking.getInstance().getDestination());
        String destination = Singleton.getInstance().getCityID();
        dbHandler.getFlightID(FlightBooking.getInstance().getAirline(),origin,destination,FlightBooking.getInstance().getDate());
        dbHandler.getUserID(Singleton.getInstance().getUsername());
        dbHandler.getFlightBookingID(Singleton.getInstance().getUserID(),Singleton.getInstance().getFlightID());
        dbHandler.deleteFlightBooking(Singleton.getInstance().getFlightBookingID());

    }

    @Override
    void deleteCruiseBooking() {
        dbHandler.getCityID(CruiseBooking.getInstance().getOrigin());
        String origin = Singleton.getInstance().getCityID();
        dbHandler.getCityID(CruiseBooking.getInstance().getDestination());
        String destination = Singleton.getInstance().getCityID();
        dbHandler.getCruiseID(origin,destination,CruiseBooking.getInstance().getDate());
        dbHandler.getUserID(Singleton.getInstance().getUsername());

        dbHandler.getCruiseBookingID(Singleton.getInstance().getUserID(),Singleton.getInstance().getCruiseID());
        dbHandler.deleteCruiseBooking(Singleton.getInstance().getCruiseBookingID());

    }

    @Override
    void deleteHotelBooking() {
        int roomID;
        dbHandler.getHotelID(Singleton.getInstance().getHotelName());
        roomID= dbHandler.getRoomId(Integer.parseInt(Singleton.getInstance().getHotelID()),Integer.parseInt(Singleton.getInstance().getHotelRoomSize()));

        dbHandler.getUserID(Singleton.getInstance().getUsername());

        dbHandler.getHotelBookingID(Singleton.getInstance().getUserID(),roomID,Singleton.getInstance().getHotelCheckInDate(),Singleton.getInstance().getHotelCheckOutDate());
        dbHandler.deleteHotelBooking(Singleton.getInstance().getHotelBookingID());

    }

    @Override
    void deleteCarRentalBooking() {
        dbHandler.getCityID(CarRentalBooking.getInstance().getCity());
        dbHandler.getCarID(CarRentalBooking.getInstance().getCar(),Singleton.getInstance().getCityID());
        dbHandler.getUserID(Singleton.getInstance().getUsername());
        dbHandler.getCarRentalBookingID(Singleton.getInstance().getCarID(),Singleton.getInstance().getUserID(), CarRentalBooking.getInstance().getHireCarDate(),
                CarRentalBooking.getInstance().getReturnCarDate());
        dbHandler.deleteCarRentalBooking(Singleton.getInstance().getCarRentalBookingID());

    }
}


