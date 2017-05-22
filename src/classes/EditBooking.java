package classes;

/**
 * Created by Paolo9517 on 2017-05-15.
 */
public abstract class EditBooking {

    abstract void commitChangeForFlightBooking();

    abstract void commitChangeForCruiseBooking();

    abstract void commitChangeForHotelBooking();

    abstract void commitChangeForCarRentalBooking();

    abstract void deleteFlightBooking();

    abstract void deleteCruiseBooking();

    abstract void deleteHotelBooking();

    abstract void deleteCarRentalBooking();



}
