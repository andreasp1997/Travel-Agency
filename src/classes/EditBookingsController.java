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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.management.jmxremote.SingleEntryRegistry;

import javax.swing.text.View;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by andreas on 2017-05-08.
 */
public class EditBookingsController implements Initializable {

    DBHandler dbh = new DBHandler();
    Singleton singleton = new Singleton();

    @FXML TextField pickUserField;
    @FXML Button pickUserBtn;
    @FXML Text adminText;

    private String usernameList;

    @FXML private TableView hotelTable;
    @FXML private TableColumn hotel;
    @FXML private TableColumn hotelRoom;
    @FXML private TableColumn hotelCheckin;
    @FXML private TableColumn hotelCheckout;
    @FXML private TableColumn hotelPrice;

    @FXML private TableView carTable;
    @FXML private TableColumn carCity;
    @FXML private TableColumn car;
    @FXML private TableColumn carHireDate;
    @FXML private TableColumn carReturnDate;
    @FXML private TableColumn carPrice;

    @FXML private TableView cruiseTable;
    @FXML private TableColumn cruiseOrigin;
    @FXML private TableColumn cruiseDestination;
    @FXML private TableColumn cruiseDate;
    @FXML private TableColumn cruisePrice;
    @FXML private TableColumn cruiseRooms;

    @FXML private TableView flightTable;
    @FXML private TableColumn flightOrigin;
    @FXML private TableColumn flightDestination;
    @FXML private TableColumn flightDate;
    @FXML private TableColumn flightPrice;
    @FXML private TableColumn flightRooms;
    @FXML private Button helpBtn;

    ArrayList<HotelBooking> hotelBooking;
    ArrayList<CarRentalBooking> carBooking;
    ArrayList<CruiseBooking> cruiseBooking;
    ArrayList<FlightBooking> flightBooking;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hotelTable.setVisible(false);
        flightTable.setVisible(false);
        carTable.setVisible(false);
        cruiseTable.setVisible(false);

        if(singleton.getInstance().getUserRole().equals("1")){

            pickUserField.setVisible(true);
            pickUserBtn.setVisible(true);
            adminText.setVisible(true);

        } else if (singleton.getInstance().getUserRole().equals("2")) {
            pickUserField.setVisible(false);
            pickUserBtn.setVisible(false);
            adminText.setVisible(false);
        }

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

    public void pickUser(ActionEvent ae){
        dbh.checkIfUsernameExists();
        usernameList = Singleton.getInstance().getUsernameList();

        if(usernameList.contains(pickUserField.getText())){
            Singleton.getInstance().setPickedUser(pickUserField.getText());

            dbh.getUserID(pickUserField.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("You have picked a user");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The username you entered does not exist");
            alert.showAndWait();
            Singleton.getInstance().setPickedUser(null);
        }
    }

    public void help(ActionEvent ae){
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/viewEditHelpScreen.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            helpBtn.setDisable(true);

            stage.setOnCloseRequest(event -> {
                helpBtn.setDisable(false);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showHotelBookings(ActionEvent ae) {

        hotelTable.setVisible(true);

        hotelBooking = dbh.getHotelsBookings(Integer.parseInt(singleton.getInstance().getUserID()));

        final ObservableList<HotelBooking> data = FXCollections.observableArrayList(hotelBooking);

        hotel.setCellValueFactory(
                new PropertyValueFactory<HotelBooking,String>("hotelName")
        );

        hotelRoom.setCellValueFactory(
                new PropertyValueFactory<HotelBooking,Integer>("roomSize")
        );

        hotelCheckin.setCellValueFactory(
                new PropertyValueFactory<HotelBooking,String>("checkinDate")
        );

        hotelCheckout.setCellValueFactory(
                new PropertyValueFactory<HotelBooking,String>("checkoutDate")
        );

        hotelPrice.setCellValueFactory(
                new PropertyValueFactory<HotelBooking,String>("price")
        );

        hotelTable.setItems(data);
        hotelTable.getColumns().clear();
        hotelTable.getColumns().addAll(hotel, hotelRoom, hotelCheckin, hotelCheckout, hotelPrice);

    }

    @FXML
    public void showCarBookings(ActionEvent ae) {

        carTable.setVisible(true);

        carBooking = dbh.getCarBookings(Integer.parseInt(singleton.getInstance().getUserID()));

        final ObservableList<CarRentalBooking> data = FXCollections.observableArrayList(carBooking);

        carCity.setCellValueFactory(
                new PropertyValueFactory<CarRentalBooking,String>("city")
        );

        car.setCellValueFactory(
                new PropertyValueFactory<CarRentalBooking,String>("car")
        );

        carHireDate.setCellValueFactory(
                new PropertyValueFactory<CarRentalBooking,String>("hireCarDate")
        );

        carReturnDate.setCellValueFactory(
                new PropertyValueFactory<CarRentalBooking,String>("returnCarDate")
        );

        carPrice.setCellValueFactory(
                new PropertyValueFactory<CarRentalBooking,Double>("price")
        );

        carTable.setItems(data);
        carTable.getColumns().clear();
        carTable.getColumns().addAll(carCity, car, carHireDate, carReturnDate, carPrice);

    }

    @FXML
    public void showCruiseBookings(ActionEvent ae) {

        cruiseTable.setVisible(true);

        cruiseBooking = dbh.getCruiseBookings(Integer.parseInt(singleton.getInstance().getUserID()));

        final ObservableList<CruiseBooking> data = FXCollections.observableArrayList(cruiseBooking);

        cruiseOrigin.setCellValueFactory(
                new PropertyValueFactory<CruiseBooking,String>("origin")
        );

        cruiseDestination.setCellValueFactory(
                new PropertyValueFactory<CruiseBooking,String>("destination")
        );

        cruiseDate.setCellValueFactory(
                new PropertyValueFactory<CruiseBooking,String>("date")
        );

        cruiseRooms.setCellValueFactory(
                new PropertyValueFactory<CruiseBooking,String>("rooms")
        );

        cruisePrice.setCellValueFactory(
                new PropertyValueFactory<CruiseBooking,Double>("price")
        );

        cruiseTable.setItems(data);
        cruiseTable.getColumns().clear();
        cruiseTable.getColumns().addAll(cruiseOrigin, cruiseDestination, cruiseDate, cruiseRooms, cruisePrice);

    }

    @FXML
    public void showFlightBookings(ActionEvent ae) {

        flightTable.setVisible(true);

        flightBooking = dbh.getFlightBookings(Integer.parseInt(singleton.getInstance().getUserID()));

        final ObservableList<FlightBooking> data = FXCollections.observableArrayList(flightBooking);

        flightOrigin.setCellValueFactory(
                new PropertyValueFactory<FlightBooking,String>("origin")
        );

        flightDestination.setCellValueFactory(
                new PropertyValueFactory<FlightBooking,String>("destination")
        );

        flightDate.setCellValueFactory(
                new PropertyValueFactory<FlightBooking,String>("date")
        );

        flightPrice.setCellValueFactory(
                new PropertyValueFactory<FlightBooking,Double>("price")
        );

        flightTable.setItems(data);
        flightTable.getColumns().clear();
        flightTable.getColumns().addAll(flightOrigin, flightDestination, flightDate, flightPrice);

    }
}
