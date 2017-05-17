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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Jun on 2017-05-07.
 */
public class CruiseBookingController implements Initializable, ChangeCurrency {
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
    @FXML private Text priceValue;
    @FXML private Text roomsLeftValue;
    @FXML private Text priceEUR;
    @FXML private Text priceUSD;
    @FXML private Text priceGBP;
    @FXML private ComboBox<String> currencyComboBox;
    @FXML private Button selectCurrencyBtn;

    NormalUserBooking normalUserBooking = new NormalUserBooking();
    AdminBooking adminBooking = new AdminBooking();

    DBHandler dbHandler = new DBHandler();
    private ObservableList<String> travels;
    Singleton singleton = new Singleton();
    private ObservableList<Integer> roomOption;
    private ArrayList<String> usernameList;
    private String originID;
    private String destinationID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priceText.setVisible(false);
        roomLeftText.setVisible(false);
        rectangle.setVisible(false);
        selectRoom.setVisible(false);
        roomText.setVisible(false);
        bookButton.setVisible(false);
        priceValue.setVisible(false);
        roomsLeftValue.setVisible(false);
        priceEUR.setVisible(false);
        priceUSD.setVisible(false);
        priceGBP.setVisible(false);
        currencyComboBox.setVisible(false);
        selectCurrencyBtn.setVisible(false);

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

        currencyComboBox.getItems().addAll("SEK", "USD", "GBP", "EUR");
        currencyComboBox.getSelectionModel().selectFirst();

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

    @FXML private Button helpBtn;
    public void help(ActionEvent ae){
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/cruiseHelpScreen.fxml"));
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

    public void search() {

        priceValue.setText("5000");
        currencyComboBox.getSelectionModel().select("SEK");
        priceEUR.setVisible(false);
        priceUSD.setVisible(false);
        priceGBP.setVisible(false);

        if (tour.getValue().equals("London -> New York")) {
            priceValue.setText(String.valueOf(Integer.parseInt(priceValue.getText()) + 7000));
            priceEUR.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.10354).split("\\.")[0]);
            priceUSD.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.11272).split("\\.")[0]);
            priceGBP.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.08752).split("\\.")[0]);
            CruiseBooking.getInstance().setPrice(Double.parseDouble(priceValue.getText()));
            CruiseBooking.getInstance().setOrigin("London");
            CruiseBooking.getInstance().setDestination("New York");

            dbHandler.getCityID(CruiseBooking.getInstance().getOrigin());
            originID = Singleton.getInstance().getCityID();
            CruiseBooking.getInstance().setOrigin(originID);

            dbHandler.getCityID(CruiseBooking.getInstance().getDestination());
            destinationID = Singleton.getInstance().getCityID();
            CruiseBooking.getInstance().setDestination(destinationID);

        }else if (tour.getValue().equals("New York -> London")) {
            priceValue.setText(String.valueOf(Integer.parseInt(priceValue.getText()) + 7500));
            priceEUR.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.10354).split("\\.")[0]);
            priceUSD.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.11272).split("\\.")[0]);
            priceGBP.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.08752).split("\\.")[0]);
            CruiseBooking.getInstance().setPrice(Double.parseDouble(priceValue.getText()));
            CruiseBooking.getInstance().setOrigin("New York");
            CruiseBooking.getInstance().setDestination("London");

            dbHandler.getCityID(CruiseBooking.getInstance().getOrigin());
            originID = Singleton.getInstance().getCityID();
            CruiseBooking.getInstance().setOrigin(originID);

            dbHandler.getCityID(CruiseBooking.getInstance().getDestination());
            destinationID = Singleton.getInstance().getCityID();
            CruiseBooking.getInstance().setDestination(destinationID);

        }else if (tour.getValue().equals("Los Angeles -> Sydney")){
            priceValue.setText(String.valueOf(Integer.parseInt(priceValue.getText()) + 8000));
            priceEUR.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.10354).split("\\.")[0]);
            priceUSD.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.11272).split("\\.")[0]);
            priceGBP.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.08752).split("\\.")[0]);
            CruiseBooking.getInstance().setPrice(Double.parseDouble(priceValue.getText()));
            CruiseBooking.getInstance().setOrigin("Los Angeles");
            CruiseBooking.getInstance().setDestination("Sydney");

            dbHandler.getCityID(CruiseBooking.getInstance().getOrigin());
            originID = Singleton.getInstance().getCityID();
            CruiseBooking.getInstance().setOrigin(originID);

            dbHandler.getCityID(CruiseBooking.getInstance().getDestination());
            destinationID = Singleton.getInstance().getCityID();
            CruiseBooking.getInstance().setDestination(destinationID);

        }else if (tour.getValue().equals("Sydney -> Los Angeles")){
            priceValue.setText(String.valueOf(Integer.parseInt(priceValue.getText()) + 8250));
            priceEUR.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.10354).split("\\.")[0]);
            priceUSD.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.11272).split("\\.")[0]);
            priceGBP.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.08752).split("\\.")[0]);
            CruiseBooking.getInstance().setPrice(Double.parseDouble(priceValue.getText()));
            CruiseBooking.getInstance().setOrigin("Sydney");
            CruiseBooking.getInstance().setDestination("Los Angeles");

            dbHandler.getCityID(CruiseBooking.getInstance().getOrigin());
            originID = Singleton.getInstance().getCityID();
            CruiseBooking.getInstance().setOrigin(originID);

            dbHandler.getCityID(CruiseBooking.getInstance().getDestination());
            destinationID = Singleton.getInstance().getCityID();
            CruiseBooking.getInstance().setDestination(destinationID);
        }

        if (datePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a date");
            alert.showAndWait();

        } else {

            selectRoom.setItems(roomOption);
            selectRoom.getSelectionModel().selectFirst();
            rectangle.setVisible(true);
            priceText.setVisible(true);
            roomLeftText.setVisible(true);
            selectRoom.setVisible(true);
            roomText.setVisible(true);
            bookButton.setVisible(true);
            priceValue.setVisible(true);
            roomsLeftValue.setVisible(true);
            currencyComboBox.setVisible(true);
            selectCurrencyBtn.setVisible(true);

            dbHandler.cruiseRoomsLeft(CruiseBooking.getInstance().getOrigin(), CruiseBooking.getInstance().getDestination(), datePicker.getValue().toString());

            if(Singleton.getInstance().getRoomsBooked() != null){
                roomsLeftValue.setText(String.valueOf(150 - Integer.parseInt(Singleton.getInstance().getRoomsBooked())));
            } else {
                roomsLeftValue.setText("150");
            }
        }
    }
    public void book() {

        CruiseBooking.getInstance().setRoom(selectRoom.getSelectionModel().getSelectedItem().toString());

        CruiseBooking.getInstance().setDate(datePicker.getValue().toString());

        if (Integer.parseInt(roomsLeftValue.getText()) == -1 || Integer.parseInt(roomsLeftValue.getText()) == -2 || Integer.parseInt(roomsLeftValue.getText()) == -3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not enough rooms available");
            alert.showAndWait();
        }else {
            dbHandler.checkUserRole(singleton.getInstance().getUsername());

            if(singleton.getInstance().getUserRole().equals("2")) {
                normalUserBooking.makeCruiseBooking();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking registered!");
                alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                alert.showAndWait();
            } else {
                adminBooking.makeCruiseBooking();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking registered!");
                alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                alert.showAndWait();
            }
        }
    }

    public void pickUser() {
        dbHandler.checkIfUsernameExists();
        dbHandler.checkUserRole(pickUserField.getText());
        usernameList = Singleton.getInstance().getUsernameList();

        if(usernameList.contains(pickUserField.getText()) && Singleton.getInstance().getUserRole().equals("2")){
            Singleton.getInstance().setPickedUser(pickUserField.getText());
        } else if (Singleton.getInstance().getUserRole().equals("1")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The username you entered is an administrator account");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The username you entered does not exist");
            alert.showAndWait();
            Singleton.getInstance().setPickedUser(null);
        }
    }

    @Override
    public void selectCurrency() {

        if(currencyComboBox.getSelectionModel().getSelectedItem().equals("SEK")){
            priceValue.setVisible(true);
            priceGBP.setVisible(false);
            priceUSD.setVisible(false);
            priceEUR.setVisible(false);
        } else if (currencyComboBox.getSelectionModel().getSelectedItem().equals("USD")){
            priceValue.setVisible(false);
            priceGBP.setVisible(false);
            priceUSD.setVisible(true);
            priceEUR.setVisible(false);
        } else if(currencyComboBox.getSelectionModel().getSelectedItem().equals("GBP")){
            priceValue.setVisible(false);
            priceGBP.setVisible(true);
            priceUSD.setVisible(false);
            priceEUR.setVisible(false);
        } else if (currencyComboBox.getSelectionModel().getSelectedItem().equals("EUR")){
            priceValue.setVisible(false);
            priceGBP.setVisible(false);
            priceUSD.setVisible(false);
            priceEUR.setVisible(true);
        }

    }
}

