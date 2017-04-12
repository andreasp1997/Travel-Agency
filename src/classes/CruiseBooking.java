package classes;

/**
 * Created by Paolo9517 on 2017-04-12.
 */
public class CruiseBooking {

    private String origen;
    private String destination;
    private String date;

    public CruiseBooking(String origen , String destination, String date){
        this.origen=origen;
        this.destination= destination;
        this.date=date;

    }

    public void setDate(String date){this.date= date;}

    public String getOrigen(){return origen;}

    public String getDestination(){return destination;}

    public String getDate(){return date;}

}
