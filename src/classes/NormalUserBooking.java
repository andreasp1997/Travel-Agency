package classes;

/**
 * Created by andreas on 2017-05-04.
 */
public class NormalUserBooking implements BookingTypes {

    private DBHandler dbHandler = new DBHandler();

    private int flightIDCount;
    private int flightBookingIDCount;
    private int cruiseIDCount;
    private int cruiseBookingIDCount;
    private int carIDCount;
    private int carBookingIDCount;

    @Override
    public void makeFlightBooking() {
        dbHandler.checkForFlight(FlightBooking.getInstance().getAirline(), FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());
        dbHandler.flightIDCount();
        dbHandler.flightBookingIDCount();

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

                dbHandler.addFlight(flightIDCount, FlightBooking.getInstance().getAirline(), FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), 200, FlightBooking.getInstance().getPrice(), FlightBooking.getInstance().getDate());
                dbHandler.getFlightID(FlightBooking.getInstance().getAirline(), FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());
                dbHandler.getUserID(Singleton.getInstance().getUsername());
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

                dbHandler.getFlightID(FlightBooking.getInstance().getAirline(), FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());
                dbHandler.getUserID(Singleton.getInstance().getUsername());
                dbHandler.bookFlight(flightBookingIDCount, Singleton.getInstance().getFlightID(), Singleton.getInstance().getUserID());
            }
    }

    @Override
    public void makeHotelBooking(HotelBooking hotel) {

        dbHandler.setHotelBooking(hotel, Integer.parseInt(Singleton.getInstance().getUserID()));

    }

    @Override
    public void makeCarRentalBooking() {

        dbHandler.carBookingIDCount();
        dbHandler.getCityID(CarRentalBooking.getInstance().getCity());
        dbHandler.getCarID(CarRentalBooking.getInstance().getCar(), Singleton.getInstance().getCityID());
        dbHandler.getUserID(Singleton.getInstance().getUsername());

        if(Singleton.getInstance().getCarBookingsIDAmount() == null){
            carBookingIDCount = 1;
        } else {
            carBookingIDCount = Integer.parseInt(Singleton.getInstance().getCarBookingsIDAmount());
            carBookingIDCount++;
        }

        System.out.println(carBookingIDCount);
        System.out.println(Singleton.getInstance().getCarID());
        System.out.println(Singleton.getInstance().getUserID());
        System.out.println(CarRentalBooking.getInstance().getHireCarDate());
        System.out.println(CarRentalBooking.getInstance().getReturnCarDate());
        System.out.println(CarRentalBooking.getInstance().getPrice());

        dbHandler.bookCar(carBookingIDCount, Singleton.getInstance().getCarID(), Singleton.getInstance().getUserID(), CarRentalBooking.getInstance().getHireCarDate(), CarRentalBooking.getInstance().getReturnCarDate(), CarRentalBooking.getInstance().getPrice());
    }

    @Override
    public void makeCruiseBooking() {

        dbHandler.checkForCruise(CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), CruiseBooking.getInstance().getDate());
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

            dbHandler.addCruise(cruiseIDCount, CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), CruiseBooking.getInstance().getDate(), 150, CruiseBooking.getInstance().getPrice());
            dbHandler.getCruiseID(CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), CruiseBooking.getInstance().getDate());
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

            dbHandler.getCruiseID(CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), CruiseBooking.getInstance().getDate());
            dbHandler.getUserID(Singleton.getInstance().getUsername());
            dbHandler.bookCruise(cruiseBookingIDCount, Singleton.getInstance().getCruiseID(),Singleton.getInstance().getUserID(),CruiseBooking.getInstance().getRoom());
        }

    }
}
