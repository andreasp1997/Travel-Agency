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
import java.util.ResourceBundle;

/**
 * Created by Jun on 2017-05-07.
 */
public class CruiseBookingController implements Initializable {
    @FXML private ComboBox tour;
    @FXML private ComboBox selectRoom;
    @FXML private Text priceText;
    @FXML private Text roomLeftText;
    @FXML private Text adminText;
    @FXML private Text roomText;
    @FXML private TextField pickUserField;
    @FXML private Button pickUserBtn;
    @FXML private Button bookButton;
    @FXML private DatePicker datePicker;
    @FXML private Rectangle rectangle;

    NormalUserBooking normalUserBooking = new NormalUserBooking();
    AdminBooking adminBooking = new AdminBooking();

    DBHandler dbHandler = new DBHandler();
    private ObservableList<String> travels;
    Singleton singleton = new Singleton();
    private ObservableList<Integer> roomOption;
    private String usernameList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priceText.setVisible(false);
        roomLeftText.setVisible(false);
        rectangle.setVisible(false);
        selectRoom.setVisible(false);
        roomText.setVisible(false);
        bookButton.setVisible(false);

        dbHandler.checkUserRole(singleton.getInstance().getUsername());

        if(singleton.getInstance().getUserRole().equals("1")){

            pickUserField.setVisible(true);
            pickUserBtn.setVisible(true);
            adminText.setVisible(true);

        } else if (singleton.getInstance().getUserRole().equals("2")) {
            pickUserField.setVisible(false);
            pickUserBtn.setVisible(false);
            adminText.setVisible(false);
        }

        travels = FXCollections.observableArrayList("London -> New York","New York -> London","Los Angeles -> Sydney",
                "Sydney -> Los Angeles");
        tour.setItems(travels);
        tour.getSelectionModel().selectFirst();
        roomOption = FXCollections.observableArrayList(1,2,3);
    }

    public void back(ActionEvent ae) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/userMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search() {
        if (tour.getValue().equals("London -> New York")) {
            dbHandler.cruiseSearch( "New York",datePicker.getValue());
        }else if (tour.getValue().equals("New York -> London")) {
            dbHandler.cruiseSearch( "London",datePicker.getValue());
        }else if (tour.getValue().equals("Los Angeles -> Sydney")){
            dbHandler.cruiseSearch( "Sydney",datePicker.getValue());
        }else{
            dbHandler.cruiseSearch( "Los Angeles",datePicker.getValue());

        }
        if (datePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a date");
            alert.showAndWait();

        } else if (Singleton.getInstance().getCruiseID() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your tour was not found please choose another date");
            alert.showAndWait();
        }
        else {

            dbHandler.cruiseInformation(Singleton.getInstance().getCruiseID());
            priceText.setText("Price: " + Singleton.getInstance().getCruisePrice());
            roomLeftText.setText("Room: " + Singleton.getInstance().getCruiseRoom());
            selectRoom.setItems(roomOption);
            selectRoom.getSelectionModel().selectFirst();
            rectangle.setVisible(true);
            priceText.setVisible(true);
            roomLeftText.setVisible(true);
            selectRoom.setVisible(true);
            roomText.setVisible(true);
            bookButton.setVisible(true);

        }

    }
    public void book() {
        int updatedRooms;
        CruiseBooking.getInstance().setRoom(selectRoom.getValue().toString());
        CruiseBooking.getInstance().setDate(datePicker.getValue().toString());
        updatedRooms = Integer.parseInt(Singleton.getInstance().getCruiseRoom()) - Integer.parseInt(CruiseBooking.getInstance().getRoom().toString());
        if (updatedRooms < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not enough rooms available");
            alert.showAndWait();
        }else {
            dbHandler.checkUserRole(singleton.getInstance().getUsername());

            if(singleton.getInstance().getUserRole().equals("2")) {
                normalUserBooking.makeCruiseBooking();
                dbHandler.updateCruises(Singleton.getInstance().getCruiseID(), updatedRooms);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking registered!");
                alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                alert.showAndWait();
            } else {
                adminBooking.makeCruiseBooking();
                dbHandler.updateCruises(Singleton.getInstance().getCruiseID(), updatedRooms);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking registered!");
                alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                alert.showAndWait();
            }
        }
    }

    public void pickUser() {
        dbHandler.checkIfUsernameExists();
        usernameList = Singleton.getInstance().getUsernameList();

        if(usernameList.contains(pickUserField.getText())){
            Singleton.getInstance().setPickedUser(pickUserField.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The username you entered does not exist");
            alert.showAndWait();
            Singleton.getInstance().setPickedUser(null);
        }
    }
}

