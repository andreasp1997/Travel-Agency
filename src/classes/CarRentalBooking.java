package classes;

/**
 * Created by safaa on 2017-04-12.
 */
public class CarRentalBooking {
    private static CarRentalBooking instance = null;
    private String hireCarDate;
    private String returnCarDate;
    private String seats;
    private String city;
    private String car;
    private double price;

    public static CarRentalBooking getInstance() {
        if(instance == null) {
            instance = new CarRentalBooking();
        }
        return instance;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
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

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
}
