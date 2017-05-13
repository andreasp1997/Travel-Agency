package classes;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by andreas on 2017-04-18.
 */
public class DBHandler {

    private final String dbName;
    private final String user;
    private final String password;
    private final String connectionURL;

    public DBHandler() {
        Properties p = loadProperties();
        dbName = p.getProperty("dbName");
        user = p.getProperty("user");
        password = p.getProperty("password");
        connectionURL = "jdbc:mysql://127.0.0.1:3306/" + dbName + "?user=" + user + "&password=" + password + "&useSSL=false";
    }

    private Properties loadProperties() {
        Properties appProp = new Properties();
        try(FileInputStream fis = new FileInputStream("app.properties")) {
            appProp.load(fis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  appProp;
    }

    public void register(String userID, String firstname, String lastname, String username, String password, String email, String roleID) {
        String command = String.format("INSERT INTO users (user_id, firstname, lastname, username, password, email, role_id) values ('" + userID + "', '" + firstname + "', '" + lastname + "', '" + username + "', '" + password+ "', '" + email+ "', '" + roleID + "')");

        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(command);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void checkIfUsernameExists(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT username FROM users");

            ArrayList <String> result = new ArrayList<String>();

            while(rs.next()){
                result.add(rs.getString(1));
                continue;
            }

            System.out.println(result.toString());
            Singleton.getInstance().setUsernameList(result.toString());
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
            ex.printStackTrace();
        }
    }

    public void getUserIDCount(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(user_id) from users");

            while (rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setUserIDnumber(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void checkUserRole(String username){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT role_id FROM users WHERE username='"+ username + "'");

            while (rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setUserRole(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public String checkLogin(String username){

        String s = null;

        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select user_id, username, password, role_id from users where username ='" + username + "'");

            while (rs.next()){

                s = rs.getString("password");

                Singleton.getInstance().setUserRole(rs.getString("role_id"));
                Singleton.getInstance().setUserID(rs.getString("user_id"));
                Singleton.getInstance().setUsername(rs.getString("username"));

            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }

        return s;
    }

    public void getCities(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select city from cities order by city");

            ArrayList <String> result = new ArrayList<String>();

            while(rs.next()){
                result.add(rs.getString(1));
                continue;
            }

            Singleton.getInstance().setCities(result);

        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public ArrayList<Hotel> getHotels(String city){

        ArrayList <Hotel> hotels = new ArrayList<Hotel>();

        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select h.hotel_id, h.hotel_name, c.city from hotels h left join cities c on h.location = c.city_id where c.city = '" + city + "'");

            while(rs.next()){

                hotels.add(new Hotel(rs.getInt("hotel_id"), rs.getString("hotel_name"), rs.getString("city")));

                continue;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return hotels;
    }

    public ArrayList<Integer> getRooms(int hotelId){

        ArrayList <Integer> rooms = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select for_people from rooms where hotel_id = '" + hotelId + "'");

            while(rs.next()){

                rooms.add(rs.getInt("for_people"));

                continue;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return rooms;
    }

    public int getRoomId(int hotelId, int roomSize){

        int roomId = 0;

        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select room_id from rooms where for_people = '" + roomSize + "' and hotel_id = '" + hotelId + "'");

            while(rs.next()){

                roomId = rs.getInt("room_id");

                continue;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return roomId;
    }

    public void setHotelBooking(HotelBooking booking, int userID){

        try(Connection conn = DriverManager.getConnection(connectionURL)) {

            String sql = "INSERT INTO hotel_bookings (user_id, room_id, `from`, `to`) values (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, userID);
            statement.setInt(2, booking.getRoomId());
            statement.setString(3, booking.getCheckinDate());
            statement.setString(4, booking.getCheckoutDate());

            statement.executeUpdate();

            conn.close();

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public void getEuropeanCities(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select city from cities where city in ('Rome', 'Stockholm', 'London', 'Berlin', 'Paris', 'Madrid', 'Milan', 'Moscow')");

            ArrayList <String> result = new ArrayList<String>();

            while(rs.next()){
                result.add(rs.getString(1));
                continue;
            }

            Singleton.getInstance().setEuropeanCities(result);

        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void getNorthAmericanCities(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select city from cities where city in ('New York', 'Los Angeles')");

            ArrayList <String> result = new ArrayList<String>();

            while(rs.next()){
                result.add(rs.getString(1));
                continue;
            }

            Singleton.getInstance().setNorthAmericanCities(result);

        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void getAsianCities(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select city from cities where city in ('Tokyo', 'Beijing', 'Bangkok')");

            ArrayList <String> result = new ArrayList<String>();

            while(rs.next()){
                result.add(rs.getString(1));
                continue;
            }

            Singleton.getInstance().setAsianCities(result);

        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void getAustralianCities(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select city from cities where city = 'Sydney'");

            ArrayList <String> result = new ArrayList<String>();

            while(rs.next()){
                result.add(rs.getString(1));
                continue;
            }

            Singleton.getInstance().setAustralianCities(result);

        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void checkForFlight(String airline_id, String from, String to, String date){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select flight_id from flights where flights.airline_id = '" + airline_id + "' and flights.from = '" + from + "' and flights.to = '" + to + "' and flights.take_off = '" + date + "'");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setCheckedFlight(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void getCityID(String cityName){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select city_id from cities where city = '" + cityName + "'");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setCityID(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void flightIDCount(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(flight_id) from flights");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setFlightIDCount(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void addFlight(int flight_id, String airline_id, String from, String to, int seats, double price, String take_off) {
        String command = String.format("insert into flights (flight_id, airline_id, flights.from, flights.to, seats, price, take_off) values ('" + flight_id + "', '" + airline_id + "', '" + from + "', '" + to + "', '" + seats + "', '" + price + "', '" + take_off + "')");

        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(command);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void bookFlight(int booking_id, String flight_id, String user_id) {
        String command = String.format("INSERT INTO flight_bookings (booking_id, flight_id, user_id ) values ('" + booking_id + "', '" + flight_id + "', '" + user_id + "')");

        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(command);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getFlightID(String airlineID, String from, String to, String date){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select flight_id from flights where flights.airline_id = '" + airlineID + "' and flights.from = '" + from + "' and flights.to = '" + to + "' and flights.take_off = '" + date + "'");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setFlightID(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void flightBookingIDCount(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(booking_id) from flight_bookings");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setFlightBookingIDCount(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void getUserID(String username){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select user_id from users where username = '" + username + "'");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setUserID(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void bookingAmountForFlight(String from, String to, String date){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(booking_id) from flight_bookings left join flights on flight_bookings.flight_id = flights.flight_id where flights.from = '" + from + "' and flights.to = '" + to + "' and flights.take_off = '" + date + "'");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setBookedTicketsForFlight(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void getCars(String seats, String city) {
        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();

            String c = "select car from cars where seats = '" + seats + "' and location = '" + city + "'";
            ResultSet rs = statement.executeQuery(c);

            ArrayList<String> result = new ArrayList<>();

            while (rs.next()) {
                result.add(rs.getString(1));
            }

            Singleton.getInstance().setCars(result);

        } catch (SQLException ex) {
            System.out.println("Error on executing the query");
        }
    }

    public void getCarPrice(String carName, String city) {
        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            String c = "select price from cars where car = '" + carName + "' and location = '" + city + "'";
            ResultSet rs = statement.executeQuery(c);

            while (rs.next()) {
                String s = rs.getString(1);
                Singleton.getInstance().setCarPrice(s);
            }

        } catch (SQLException ex) {
            System.out.println("Error on executing the query");
        }
    }

    public void checkCarBookingsBetweenDates(String city, String carName, String hireDate, String returnDate) {
        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(booking_id) from car_bookings left join cars on car_bookings.car_id = cars.car_id where car = '" + carName + "' and cars.location = '" + city + "' and starts and ends between '" + hireDate + "' and '" + returnDate + "'");

            while (rs.next()) {
                String s = rs.getString(1);
                Singleton.getInstance().setCarBookingsForDate(s);
            }
        } catch (SQLException ex) {
            System.out.println("Error on executing the query");
        }
    }

    public void getAmountOfCars(String city, String carName) {
        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            String c = "select amount from cars where car = '" + carName + "' and location = '" + city + "'";
            ResultSet rs = statement.executeQuery(c);

            while (rs.next()) {
                String s = rs.getString(1);
                Singleton.getInstance().setCarAmount(s);
            }
        } catch (SQLException ex) {
            System.out.println("Error on executing the query");
        }
    }

    public void bookCar(int bookingID, String carID, String userID, String starts, String ends, double price) {

        String command = String.format("INSERT INTO car_bookings (booking_id, car_id, user_id, starts, ends, price) values ('" + bookingID + "', '" + carID + "', '" + userID + "', '" + starts + "' , '" + ends + "', '" + price + "')");

        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(command);

        } catch (SQLException e) {
            System.out.println("Error executing the query");
        }
    }

    public void carBookingIDCount(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(booking_id) from car_bookings");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setCarBookingsIDAmount(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void getCarID(String carName, String city){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select car_id from cars where car = '" + carName + "' and location = '" + city + "'");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setCarID(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void bookCruise(int bookingID, String cruiseID, String userID, String rooms) {

        String command = String.format("INSERT INTO cruise_bookings (booking_id, cruise_id, user_id, rooms) values ('" + bookingID + "', '" + cruiseID + "', '" + userID + "', '" + rooms + "')");

        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(command);

        } catch (SQLException e) {
            System.out.println("Error executing the query");
        }
    }

    public void checkForCruise(String from, String to, String date){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select cruise_id from cruises where cruises.from = '" + from + "' and cruises.to = '" + to + "' and cruises.take_off = '" + date + "'");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setCheckedCruise(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void addCruise(int cruise_id, String from, String to, String take_off, int rooms, double price) {
        String command = String.format("insert into cruises (cruise_id, cruises.from, cruises.to, take_off, rooms, price) values ('" + cruise_id + "', '" + from + "', '" + to + "', '" + take_off + "', '" + rooms + "', '" + price + "')");

        try (Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(command);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cruiseIDCount(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(cruise_id) from cruises");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setCruiseIDcount(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void cruiseBookingIDCount(){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(booking_id) from cruise_bookings");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setCruiseBookingsIDAmount(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void cruiseRoomsLeft(String from, String to, String date){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select sum(cruise_bookings.rooms) from cruise_bookings left join cruises on cruise_bookings.cruise_id = cruises.cruise_id where cruises.from = '" + from +  "' and cruises.to = '" + to +  "' and cruises.take_off = '" + date +  "' ");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setRoomsBooked(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public void getCruiseID(String from, String to, String date){
        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select cruise_id from cruises where cruises.from = '" + from + "' and cruises.to = '" + to + "' and cruises.take_off = '" + date + "'");

            while(rs.next()){
                String s = rs.getString(1);
                Singleton.getInstance().setCruiseID(s);
            }
        }
        catch (SQLException ex){
            System.out.println("Error on executing the query");
        }
    }

    public ArrayList<HotelBooking> getHotelsBookings(int userID){

        ArrayList <HotelBooking> bookings = new ArrayList<HotelBooking>();

        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select h.hotel_name, r.for_people, b.from, b.to, r.price " +
                                                  "from hotel_bookings b " +
                                                  "left join rooms r on b.room_id = r.room_id " +
                                                  "left join hotels h on r.hotel_id = h.hotel_id " +
                                                  "where b.user_id = '" + userID + "'");

            while(rs.next()){

                HotelBooking booking = new HotelBooking(0, rs.getInt("for_people"), rs.getString("from"), rs.getString("to"));
                booking.setHotelName(rs.getString("hotel_name"));
                booking.setPrice(rs.getInt("price"));

                bookings.add(booking);

                continue;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return bookings;
    }

    public ArrayList<CarRentalBooking> getCarBookings(int userID){

        ArrayList <CarRentalBooking> bookings = new ArrayList<CarRentalBooking>();

        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select a.city, c.price, c.car, b.starts, b.ends " +
                    "from car_bookings b " +
                    "left join cars c on b.car_id = c.car_id " +
                    "left join cities a on c.location = a.city_id " +
                    "where b.user_id = '" + userID + "'");

            while(rs.next()){

                CarRentalBooking booking = new CarRentalBooking();
                booking.setCity(rs.getString("city"));
                booking.setCar(rs.getString("car"));
                booking.setHireCarDate(rs.getString("starts"));
                booking.setReturnCarDate(rs.getString("ends"));
                booking.setPrice(rs.getDouble("price"));

                bookings.add(booking);

                continue;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return bookings;
    }

    public ArrayList<CruiseBooking> getCruiseBookings(int userID){

        ArrayList<CruiseBooking> bookings = new ArrayList<CruiseBooking>();

        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select a.city, c.take_off, c.rooms, c.price " +
                    "from cruise_bookings b " +
                    "left join cruises c on b.cruise_id = c.cruise_id " +
                    "left join cities a on c.from = a.city_id " +
                    "where b.user_id = '" + userID + "'");

            while(rs.next()){

                CruiseBooking booking = new CruiseBooking();
                booking.setOrigin(rs.getString("city"));
                //booking.setDestination(rs.getString("to"));
                booking.setDate(rs.getString("take_off"));
                booking.setRoom(rs.getString("rooms"));
                booking.setPrice(rs.getDouble("price"));

                bookings.add(booking);

                continue;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return bookings;
    }

    public ArrayList<FlightBooking> getFlightBookings(int userID){

        ArrayList<FlightBooking> bookings = new ArrayList<FlightBooking>();

        try(Connection conn = DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select a.city, c.take_off, c.price " +
                    "from flight_bookings b " +
                    "left join flights c on b.flight_id = c.flight_id " +
                    "left join cities a on c.from = a.city_id " +
                    "where b.user_id = '" + userID + "'");

            while(rs.next()){

                FlightBooking booking = new FlightBooking();
                booking.setOrigin(rs.getString("city"));
                //booking.setDestination(rs.getString("to"));
                booking.setDate(rs.getString("take_off"));
                //booking.setRoom(rs.getString("rooms"));
                booking.setPrice(rs.getDouble("price"));

                bookings.add(booking);

                continue;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return bookings;
    }
}