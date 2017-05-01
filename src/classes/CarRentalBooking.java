package classes;

/**
 * Created by safaa on 2017-04-12.
 */
public class CarRentalBooking {
    private String carRentalCompany;
    private String hireCarDate;
    private String returnCarDate;
    private int seats;
    private String city;

    public String getCarRentalCompany() {
        return carRentalCompany;
    }

    public void setCarRentalCompany(String carRentalCompany) {
        this.carRentalCompany = carRentalCompany;
    }

    public String getHireCarDate() {
        return hireCarDate;
    }

    public void setHireCarDate(String hireCarDate) {
        this.hireCarDate = hireCarDate;
    }

    public String getReturnCarDate() {
        return returnCarDate;
    }

    public void setReturnCarDate(String returnCarDate) {
        this.returnCarDate = returnCarDate;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
