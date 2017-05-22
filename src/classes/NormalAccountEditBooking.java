package classes;

import sun.management.jmxremote.SingleEntryRegistry;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by Paolo9517 on 2017-05-15.
 */
public class NormalAccountEditBooking extends EditBooking{
    private DBHandler dbHandler = new DBHandler();
    private String origin;
    private String destination;
    @Override
    void commitChangeForFlightBooking() {
        int flightIDCount;
        int flightBookingIDCount;

        dbHandler.getCityID(FlightBooking.getInstance().getOrigin());
        origin = Singleton.getInstance().getCityID();
        dbHandler.getCityID(FlightBooking.getInstance().getDestination());
        destination = Singleton.getInstance().getCityID();
        dbHandler.checkForFlight(FlightBooking.getInstance().getAirline(),origin,destination, Singleton.getInstance().getNewFlightBookingDate());

        dbHandler.flightIDCount();
        dbHandler.flightBookingIDCount();

        if(Singleton.getInstance().getCheckedFlight() == null) {

            if (Singleton.getInstance().getFlightIDCount() == null) {
                flightIDCount = 1;
            } else {
                flightIDCount = Integer.parseInt(Singleton.getInstance().getFlightIDCount());
                flightIDCount++;
            }

            if (Singleton.getInstance().getFlightBookingIDCount() == null) {
                flightBookingIDCount = 1;
            } else {
                flightBookingIDCount = Integer.parseInt(Singleton.getInstance().getFlightBookingIDCount());
                flightBookingIDCount++;
            }
            dbHandler.getFlightID(FlightBooking.getInstance().getAirline(),origin,destination,FlightBooking.getInstance().getDate());
            dbHandler.getUserID(Singleton.getInstance().getUsername());
            dbHandler.getFlightBookingID(Singleton.getInstance().getUserID(),Singleton.getInstance().getFlightID());
            dbHandler.deleteFlightBooking(Singleton.getInstance().getFlightBookingID());
            dbHandler.addFlight(flightIDCount, FlightBooking.getInstance().getAirline(),origin, destination, 200, FlightBooking.getInstance().getPrice(),Singleton.getInstance().getNewFlightBookingDate());
            dbHandler.getFlightID(FlightBooking.getInstance().getAirline(), origin, destination, Singleton.getInstance().getNewFlightBookingDate());
            dbHandler.bookFlight(flightBookingIDCount, Singleton.getInstance().getFlightID(), Singleton.getInstance().getUserID());

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
            dbHandler.getFlightID(FlightBooking.getInstance().getAirline(),origin,destination,FlightBooking.getInstance().getDate());
            dbHandler.getUserID(Singleton.getInstance().getUsername());
            dbHandler.getFlightBookingID(Singleton.getInstance().getUserID(),Singleton.getInstance().getFlightID());
            dbHandler.deleteFlightBooking(Singleton.getInstance().getFlightBookingID());
            dbHandler.getFlightID(FlightBooking.getInstance().getAirline(),origin, destination, Singleton.getInstance().getNewFlightBookingDate());
            dbHandler.bookFlight(flightBookingIDCount, Singleton.getInstance().getFlightID(), Singleton.getInstance().getUserID());
        }



    }

    @Override
    void commitChangeForCruiseBooking() {
        int cruiseIDCount;
        int cruiseBookingIDCount;
        dbHandler.getCityID(CruiseBooking.getInstance().getOrigin());
        String origin = Singleton.getInstance().getCityID();
        dbHandler.getCityID(CruiseBooking.getInstance().getDestination());
        String destination = Singleton.getInstance().getCityID();

        dbHandler.checkForCruise(origin, destination,Singleton.getInstance().getNewCruiseBookingDate());
        dbHandler.cruiseIDCount();
        dbHandler.cruiseBookingIDCount();

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


            dbHandler.getCruiseID(origin,destination,CruiseBooking.getInstance().getDate());
            dbHandler.getUserID(Singleton.getInstance().getUsername());
            dbHandler.getCruiseBookingID(Singleton.getInstance().getUserID(),Singleton.getInstance().getCruiseID());
            dbHandler.deleteCruiseBooking(Singleton.getInstance().getCruiseBookingID());
            dbHandler.addCruise(cruiseIDCount, origin, destination,Singleton.getInstance().getNewCruiseBookingDate(), 150, CruiseBooking.getInstance().getPrice());
            dbHandler.getCruiseID(origin,destination,Singleton.getInstance().getNewCruiseBookingDate());
            dbHandler.getUserID(Singleton.getInstance().getUsername());
            dbHandler.bookCruise(cruiseBookingIDCount, Singleton.getInstance().getCruiseID(),Singleton.getInstance().getUserID(),CruiseBooking.getInstance().getRoom());

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
            dbHandler.getCruiseID(origin,destination,CruiseBooking.getInstance().getDate());
            dbHandler.getUserID(Singleton.getInstance().getUsername());
            dbHandler.getCruiseBookingID(Singleton.getInstance().getUserID(),Singleton.getInstance().getCruiseID());
            dbHandler.deleteCruiseBooking(Singleton.getInstance().getCruiseBookingID());
            dbHandler.getCruiseID(origin, destination,Singleton.getInstance().getNewCruiseBookingDate());
            dbHandler.getUserID(Singleton.getInstance().getUsername());
            dbHandler.bookCruise(cruiseBookingIDCount, Singleton.getInstance().getCruiseID(),Singleton.getInstance().getUserID(),CruiseBooking.getInstance().getRoom());
        }


    }

    @Override
    void commitChangeForHotelBooking() {
        int roomID;
        dbHandler.getHotelID(Singleton.getInstance().getHotelName());
        roomID= dbHandler.getRoomId(Integer.parseInt(Singleton.getInstance().getHotelID()),Integer.parseInt(Singleton.getInstance().getHotelRoomSize()));

        dbHandler.getUserID(Singleton.getInstance().getUsername());

        dbHandler.getHotelBookingID(Singleton.getInstance().getUserID(),roomID,Singleton.getInstance().getHotelCheckInDate(),Singleton.getInstance().getHotelCheckOutDate());
        dbHandler.editHotel(Singleton.getInstance().getHotelBookingID(),Singleton.getInstance().getNewHotelCheckInDate(),Singleton.getInstance().getNewHotelCheckOutDate());

    }

    @Override
    void commitChangeForCarRentalBooking() {
        int carPrice;
        int totalPrice;
        LocalDate hireDate = LocalDate.parse(Singleton.getInstance().getNewCarRentalStartDate());
        LocalDate returnDate = LocalDate.parse(Singleton.getInstance().getNewCarRentalReturnDate());
        int daysBetween = (int) DAYS.between(hireDate,returnDate);

        dbHandler.getCityID(CarRentalBooking.getInstance().getCity());
        dbHandler.getCarID(CarRentalBooking.getInstance().getCar(),Singleton.getInstance().getCityID());
        dbHandler.getCarPrice(Singleton.getInstance().getCarID());

        carPrice = Integer.parseInt(Singleton.getInstance().getCarPrice());
        totalPrice = carPrice * daysBetween;

        dbHandler.getUserID(Singleton.getInstance().getUsername());
        dbHandler.getCarRentalBookingID(Singleton.getInstance().getCarID(),Singleton.getInstance().getUserID(), CarRentalBooking.getInstance().getHireCarDate(),
                CarRentalBooking.getInstance().getReturnCarDate());
        dbHandler.editCarRental(Singleton.getInstance().getCarRentalBookingID(),Singleton.getInstance().getNewCarRentalStartDate(),Singleton.getInstance().getNewCarRentalReturnDate(),totalPrice);



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


