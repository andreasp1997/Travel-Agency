package classes;

import javafx.scene.control.Alert;

/**
 * Created by Paolo9517 on 2017-04-12.
 */
public class AdminBooking implements BookingTypes {

    DBHandler dbh = new DBHandler();

    int flightIDCount;
    int flightBookingIDCount;
    int cruiseIDCount;
    int cruiseBookingIDCount;

    @Override
    public void makeFlightBooking() {

        dbh.checkForFlight(FlightBooking.getInstance().getAirline(), FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());
        dbh.flightIDCount();
        dbh.flightBookingIDCount();

            if(Singleton.getInstance().getCheckedFlight() == null){

                if(Singleton.getInstance().getFlightIDCount() == null){
                    flightIDCount = 1;
                } else {
                    flightIDCount = Integer.parseInt(Singleton.getInstance().getFlightIDCount());
                    flightIDCount++;
                }

                if(Singleton.getInstance().getFlightBookingIDCount() == null){
                    flightBookingIDCount = 1;
                } else {
                    flightBookingIDCount = Integer.parseInt(Singleton.getInstance().getFlightBookingIDCount());
                    flightBookingIDCount++;
                }

                dbh.addFlight(flightIDCount, FlightBooking.getInstance().getAirline(), FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), 200, FlightBooking.getInstance().getPrice(), FlightBooking.getInstance().getDate());
                dbh.getFlightID(FlightBooking.getInstance().getAirline(), FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());
                dbh.getUserID(Singleton.getInstance().getPickedUser());
                dbh.bookFlight(flightBookingIDCount, Singleton.getInstance().getFlightID(), Singleton.getInstance().getUserID());

            } else if(Singleton.getInstance().getCheckedFlight() != null){

                if(Singleton.getInstance().getFlightIDCount() == null){
                    flightIDCount = 1;
                } else {
                    flightIDCount = Integer.parseInt(Singleton.getInstance().getFlightIDCount());
                    flightIDCount++;
                }

                if(Singleton.getInstance().getFlightBookingIDCount() == null){
                    flightBookingIDCount = 1;
                } else {
                    flightBookingIDCount = Integer.parseInt(Singleton.getInstance().getFlightBookingIDCount());
                    flightBookingIDCount++;
                }

                dbh.getFlightID(FlightBooking.getInstance().getAirline(), FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());
                dbh.getUserID(Singleton.getInstance().getPickedUser());
                dbh.bookFlight(flightBookingIDCount, Singleton.getInstance().getFlightID(), Singleton.getInstance().getPickedUser());
            }
    }

    @Override
    public void makeHotelBooking(HotelBooking hotel) {

        dbh.getUserID(Singleton.getInstance().getPickedUser());
        dbh.setHotelBooking(hotel, Integer.parseInt(Singleton.getInstance().getUserID()));
    }

    @Override
    public void makeCarRentalBooking() {

    }

    @Override
    public void makeCruiseBooking() {

        dbh.checkForCruise(CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), CruiseBooking.getInstance().getDate());
        dbh.cruiseIDCount();
        dbh.cruiseBookingIDCount();

        if(Singleton.getInstance().getCheckedCruise() == null){

            if(Singleton.getInstance().getCruiseIDcount() == null){
                cruiseIDCount = 1;
            } else {
                cruiseIDCount = Integer.parseInt(Singleton.getInstance().getCruiseIDcount());
                cruiseIDCount++;
            }

            if(Singleton.getInstance().getCruiseBookingsIDAmount() == null){
                cruiseBookingIDCount = 1;
            } else {
                cruiseBookingIDCount = Integer.parseInt(Singleton.getInstance().getCruiseBookingsIDAmount());
                cruiseBookingIDCount++;
            }

            dbh.addCruise(cruiseIDCount, CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), CruiseBooking.getInstance().getDate(), 150, CruiseBooking.getInstance().getPrice());
            dbh.getCruiseID(CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), CruiseBooking.getInstance().getDate());
            dbh.getUserID(Singleton.getInstance().getUsername());
            dbh.bookCruise(cruiseBookingIDCount, Singleton.getInstance().getCruiseID(),Singleton.getInstance().getUserID(),CruiseBooking.getInstance().getRoom());

        } else if (Singleton.getInstance().getCheckedCruise() != null){

            if(Singleton.getInstance().getCruiseIDcount() == null){
                cruiseIDCount = 1;
            } else {
                cruiseIDCount = Integer.parseInt(Singleton.getInstance().getCruiseIDcount());
                cruiseIDCount++;
            }

            if(Singleton.getInstance().getCruiseBookingsIDAmount() == null){
                cruiseBookingIDCount = 1;
            } else {
                cruiseBookingIDCount = Integer.parseInt(Singleton.getInstance().getCruiseBookingsIDAmount());
                cruiseBookingIDCount++;
            }

            dbh.getCruiseID(CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), CruiseBooking.getInstance().getDate());
            dbh.getUserID(Singleton.getInstance().getUsername());
            dbh.bookCruise(cruiseBookingIDCount, Singleton.getInstance().getCruiseID(),Singleton.getInstance().getUserID(),CruiseBooking.getInstance().getRoom());
        }
    }
}
