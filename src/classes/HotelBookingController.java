package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Mattias on 2017-05-02.
 */
public class HotelBookingController implements Initializable {

    DBHandler dbh = new DBHandler();
    HotelBooking booking;

    Singleton singleton = new Singleton();

    ArrayList<Integer> hotelIds;
    ArrayList<String> rooms;

    private ArrayList<String> allCities;

    @FXML TextField pickUserField;
    @FXML Button pickUserBtn;
    @FXML Text adminText;

    @FXML public DatePicker checkin;
    @FXML public DatePicker checkout;
    @FXML public ComboBox cities;
    @FXML public Label message;
    @FXML public Label name1;
    @FXML public Label city1;
    @FXML public ComboBox combo1;
    @FXML public Label name2;
    @FXML public Label city2;
    @FXML public ComboBox combo2;
    @FXML public Label name3;
    @FXML public Label city3;
    @FXML public ComboBox combo3;
    @FXML public Rectangle rectangle1;
    @FXML public Rectangle rectangle2;
    @FXML public Rectangle rectangle3;
    @FXML public Button btn1;
    @FXML public Button btn2;
    @FXML public Button btn3;
    @FXML public Label room1;
    @FXML public Label room2;
    @FXML public Label room3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbh.getCities();
        allCities = Singleton.getInstance().getCities();
        cities.getItems().addAll(allCities);

        btn1.setVisible(false);
        btn2.setVisible(false);
        btn3.setVisible(false);
        room1.setVisible(false);
        room2.setVisible(false);
        room3.setVisible(false);
        rectangle1.setVisible(false);
        rectangle2.setVisible(false);
        rectangle3.setVisible(false);
        combo1.setVisible(false);
        combo2.setVisible(false);
        combo3.setVisible(false);

        message.setText("Please choose check in, checkout and city.");

    }

    public void back(ActionEvent ae){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/userMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(ActionEvent ae){

        rooms = new ArrayList<>();
        hotelIds = new ArrayList<>();

        hotelIds.clear();
        rooms.clear();

        btn1.setVisible(false);
        btn2.setVisible(false);
        btn3.setVisible(false);
        room1.setVisible(false);
        room2.setVisible(false);
        room3.setVisible(false);
        rectangle1.setVisible(false);
        rectangle2.setVisible(false);
        rectangle3.setVisible(false);
        combo1.setVisible(false);
        combo2.setVisible(false);
        combo3.setVisible(false);
        message.setVisible(false);
        name1.setVisible(false);
        name2.setVisible(false);
        name3.setVisible(false);

        try {

            ArrayList<Hotel> hotels = dbh.getHotels(cities.getSelectionModel().getSelectedItem().toString());

            if (hotels.size() == 0) {

                message.setVisible(true);
                message.setText("There is no hotels in this city.");

            } else {

                for (int i = 0; i < hotels.size(); i++) {

                    hotelIds.add(hotels.get(i).hotelId);

                    hotels.get(i).rooms();

                    for (int a = 0; a < hotels.get(i).rooms.size(); a++) {
                        rooms.add(hotels.get(i).rooms.get(a).toString());
                    }

                    if (i == 0) {

                        message.setText("");

                        btn1.setVisible(true);
                        rectangle1.setVisible(true);
                        combo1.setVisible(true);
                        room1.setVisible(true);
                        name1.setVisible(true);

                        name1.setText(hotels.get(i).hotelName);
                        city1.setText(hotels.get(i).hotelCity);
                        combo1.getItems().addAll(rooms);

                    } else if (i == 1) {

                        btn2.setVisible(true);
                        rectangle2.setVisible(true);
                        combo2.setVisible(true);
                        room2.setVisible(true);
                        name2.setVisible(true);

                        name2.setText(hotels.get(i).hotelName);
                        city2.setText(hotels.get(i).hotelCity);
                        combo2.getItems().addAll(rooms);

                    } else if (i == 2) {

                        btn3.setVisible(true);
                        rectangle3.setVisible(true);
                        combo3.setVisible(true);
                        room3.setVisible(true);
                        name3.setVisible(true);

                        name3.setText(hotels.get(i).hotelName);
                        city3.setText(hotels.get(i).hotelCity);
                        combo3.getItems().addAll(rooms);

                    }

                    rooms.clear();

                }
            }

        } catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter all options in search.");
            alert.showAndWait();
        }
    }

    public void btn1(ActionEvent ae) {

        try {
            booking = new HotelBooking(hotelIds.get(0), Integer.parseInt(combo1.getSelectionModel().getSelectedItem().toString()), checkin.getValue().toString(), checkout.getValue().toString());

            this.setHotelBooking(booking);

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter all options.");
            alert.showAndWait();
        }
    }

    public void btn2(ActionEvent ae) {

        try {

            booking = new HotelBooking(hotelIds.get(1), Integer.parseInt(combo2.getSelectionModel().getSelectedItem().toString()), checkin.getValue().toString(), checkout.getValue().toString());

            this.setHotelBooking(booking);

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter all options.");
            alert.showAndWait();
        }
    }

    public void btn3(ActionEvent ae) {

        try {

            booking = new HotelBooking(hotelIds.get(2), Integer.parseInt(combo3.getSelectionModel().getSelectedItem().toString()), checkin.getValue().toString(), checkout.getValue().toString());

            this.setHotelBooking(booking);

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter all options.");
            alert.showAndWait();
        }

    }

    private void setHotelBooking(HotelBooking booking) {

        booking.setRoomId(dbh.getRoomId(booking.getHotelId(), booking.getRoomSize()));

        dbh.setHotelBooking(booking);

    }
}
