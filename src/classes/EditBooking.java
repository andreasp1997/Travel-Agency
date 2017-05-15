package classes;

/**
 * Created by Paolo9517 on 2017-05-15.
 */
public abstract class EditBooking {

    abstract void commitChangesForFlightBooking();

    abstract void commitChangesForCruiseBooking();

    abstract void commitChangesForHotelBooking();

    abstract void commitChangesForCarRentalBooking();

    abstract void deleteFlightBooking();

    abstract void deleteCruiseBooking();

    abstract void deleteHotelBooking();

    abstract void deleteCarRentalBooking();



}
