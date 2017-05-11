package classes;

import javafx.scene.control.Alert;

/**
 * Created by andreas on 2017-05-04.
 */
public class NormalUserBooking implements BookingTypes {

    DBHandler dbh = new DBHandler();

    int flightIDCount;
    int flightBookingIDCount;
    int cruiseIDCount;
    int cruiseBookingIDCount;
    int carIDCount;
    int carBookingIDCount;

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
                dbh.getUserID(Singleton.getInstance().getUsername());
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
                dbh.getUserID(Singleton.getInstance().getUsername());
                dbh.bookFlight(flightBookingIDCount, Singleton.getInstance().getFlightID(), Singleton.getInstance().getUserID());
            }
    }

    @Override
    public void makeHotelBooking(HotelBooking hotel) {

        dbh.setHotelBooking(hotel, Integer.parseInt(Singleton.getInstance().getUserID()));

    }

    @Override
    public void makeCarRentalBooking() {

        dbh.checkForCar(CarRentalBooking.getInstance().getCity(), CarRentalBooking.getInstance().getHireCarDate(), CarRentalBooking.getInstance().getReturnCarDate());
        dbh.carIDCount();
        dbh.carBookingIDCount();

        if(Singleton.getInstance().getCheckedCar() == null){

            if(Singleton.getInstance().getCarIDcount() == null){
                carIDCount = 1;
            } else {
                carIDCount = Integer.parseInt(Singleton.getInstance().getCarIDcount());
                carIDCount++;
            }

            if(Singleton.getInstance().getCarBookingsIDAmount() == null){
                carBookingIDCount = 1;
            } else {
                carBookingIDCount = Integer.parseInt(Singleton.getInstance().getCarBookingsIDAmount());
                carBookingIDCount++;
            }

            dbh.addCar(carIDCount, CarRentalBooking.getInstance().getCar(), CarRentalBooking.getInstance().getSeats(), CarRentalBooking.getInstance().getCity(), CarRentalBooking.getInstance().getPrice());
            dbh.getCarID( CarRentalBooking.getInstance().getHireCarDate(), CarRentalBooking.getInstance().getReturnCarDate());
            dbh.getUserID(Singleton.getInstance().getUsername());
            dbh.bookCar(carBookingIDCount, Singleton.getInstance().getCarID(),Singleton.getInstance().getUserID(),CarRentalBooking.getInstance().getHireCarDate(),CarRentalBooking.getInstance().getReturnCarDate());

        } else if (Singleton.getInstance().getCheckedCar() != null){

            if(Singleton.getInstance().getCarIDcount() == null){
                carIDCount = 1;
            } else {
                carIDCount = Integer.parseInt(Singleton.getInstance().getCarIDcount());
                carIDCount++;
            }

            if(Singleton.getInstance().getCarBookingsIDAmount() == null){
                carBookingIDCount = 1;
            } else {
                carBookingIDCount = Integer.parseInt(Singleton.getInstance().getCarBookingsIDAmount());
                carBookingIDCount++;
            }

            dbh.getCarID( CarRentalBooking.getInstance().getHireCarDate(), CarRentalBooking.getInstance().getReturnCarDate());
            dbh.getUserID(Singleton.getInstance().getUsername());
            dbh.bookCar(carBookingIDCount, Singleton.getInstance().getCarID(),Singleton.getInstance().getUserID(),CarRentalBooking.getInstance().getHireCarDate(),CarRentalBooking.getInstance().getReturnCarDate());
        }

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
