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
        String command = String.format("INSERT INTO users (user_id, firstname, lastname, username, password, email, role_id) values ('" + userID +"', '" + firstname + "', '" + lastname + "', '" + username + "', '" + password+ "', '" + email+ "', '" + roleID + "')");

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

    public void getUserID(){
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
            ResultSet rs = statement.executeQuery("select password from users where username ='" + username + "'");

            while (rs.next()){
                s = rs.getString(1);

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
}