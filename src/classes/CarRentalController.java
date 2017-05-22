package classes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by safaa on 2017-05-01.
 */
public class CarRentalController implements Initializable, ChangeCurrency {
    DBHandler dbh = new DBHandler();
    AdminBooking adminBooking = new AdminBooking();
    NormalUserBooking normalUserBooking = new NormalUserBooking();
    private ArrayList<String> usernameList;

    private ArrayList<String> cities;
    private ArrayList<String> carList;
    private LocalDate hireCarDate;
    private LocalDate returnCarDate;
    private int daysBetween;

    private ObservableList<String> citiesObservable;
    private ObservableList<String> carListObservable;

    @FXML ComboBox<String> cityComboBox;
    @FXML ComboBox<String> seatsComboBox;
    @FXML ComboBox<String> carsComboBox;
    @FXML DatePicker hireDate;
    @FXML DatePicker returnDate;
    @FXML private Text hireDateText;
    @FXML private Text returnDateText;
    @FXML Text priceText;
    @FXML Text priceValue;
    @FXML private TextField pickUserField;
    @FXML private Button pickUserBtn;
    @FXML private Text adminText;
    @FXML private Rectangle rectangle;
    @FXML private Text priceEUR;
    @FXML private Text priceUSD;
    @FXML private Text priceGBP;
    @FXML private ComboBox<String> currencyComboBox;
    @FXML private Button selectCurrencyBtn;
    @FXML private Button book;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        priceText.setVisible(false);
        hireDate.setVisible(false);
        returnDate.setVisible(false);
        priceValue.setVisible(false);
        rectangle.setVisible(false);
        hireDateText.setVisible(false);
        returnDateText.setVisible(false);
        carsComboBox.setVisible(false);
        priceEUR.setVisible(false);
        priceUSD.setVisible(false);
        priceGBP.setVisible(false);
        currencyComboBox.setVisible(false);
        selectCurrencyBtn.setVisible(false);
        book.setVisible(false);

        currencyComboBox.getItems().addAll("SEK", "USD", "GBP", "EUR");
        currencyComboBox.getSelectionModel().selectFirst();

        dbh.checkUserRole(Singleton.getInstance().getUsername());

        if(Singleton.getInstance().getUserRole().equals("1")){

            pickUserField.setVisible(true);
            pickUserBtn.setVisible(true);
            adminText.setVisible(true);

        } else if (Singleton.getInstance().getUserRole().equals("2")) {
            pickUserField.setVisible(false);
            pickUserBtn.setVisible(false);
            adminText.setVisible(false);
        }

        dbh.getCities();

        cities = Singleton.getInstance().getCities();
        citiesObservable = FXCollections.observableArrayList(cities);

        cityComboBox.setItems(citiesObservable);
        seatsComboBox.getItems().addAll("2", "5", "7");

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
    @FXML private Button helpBtn;
    public void help(ActionEvent ae){
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/carRentalHelpScreen.fxml"));
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

    public void search(ActionEvent ae) {

        currencyComboBox.getSelectionModel().select("SEK");
        priceEUR.setVisible(false);
        priceUSD.setVisible(false);
        priceGBP.setVisible(false);

        if(cityComboBox.getSelectionModel().getSelectedItem() != null && seatsComboBox.getSelectionModel().getSelectedItem() != null){

            hireDate.setVisible(true);
            returnDate.setVisible(true);
            priceText.setVisible(true);
            priceValue.setVisible(true);
            rectangle.setVisible(true);
            hireDateText.setVisible(true);
            returnDateText.setVisible(true);
            carsComboBox.setVisible(true);
            currencyComboBox.setVisible(true);
            selectCurrencyBtn.setVisible(true);
            book.setVisible(true);

            CarRentalBooking.getInstance().setCity(cityComboBox.getSelectionModel().getSelectedItem());
            CarRentalBooking.getInstance().setSeats(String.valueOf(Integer.parseInt(seatsComboBox.getSelectionModel().getSelectedItem())));

            dbh.getCityID(CarRentalBooking.getInstance().getCity());
            dbh.getCars(String.valueOf(CarRentalBooking.getInstance().getSeats()), Singleton.getInstance().getCityID());

            carList = Singleton.getInstance().getCars();
            carListObservable = FXCollections.observableArrayList(carList);

            carsComboBox.setItems(carListObservable);
            carsComboBox.getSelectionModel().selectFirst();

            dbh.getCarPrice(carsComboBox.getSelectionModel().getSelectedItem(), Singleton.getInstance().getCityID());
            priceValue.setText(Singleton.getInstance().getCarPrice());
            priceEUR.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.10354).split("\\.")[0]);
            priceUSD.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.11272).split("\\.")[0]);
            priceGBP.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.08752).split("\\.")[0]);

            carsComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    dbh.getCarPrice(carsComboBox.getSelectionModel().getSelectedItem(), Singleton.getInstance().getCityID());
                    priceValue.setText(Singleton.getInstance().getCarPrice());
                    priceEUR.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.10354).split("\\.")[0]);
                    priceUSD.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.11272).split("\\.")[0]);
                    priceGBP.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.08752).split("\\.")[0]);
                }
            });

            hireDate.setValue(LocalDate.now());

            LocalDate today = LocalDate.now();
            LocalDate returnStartDate = hireDate.getValue().plusDays(1);
            LocalDate next = today.plusMonths(8);
            LocalDate hireLength = today.plusWeeks(4);

            returnDate.setValue(returnStartDate);

            hireDate.setDayCellFactory((p) -> new DateCell() {
                @Override
                public void updateItem(LocalDate ld, boolean bln) {
                    super.updateItem(ld, bln);
                    setDisable(ld.isBefore(today) || ld.isAfter(next));
                    returnDate.setValue(hireDate.getValue().plusDays(1));
                }
            });

            returnDate.setDayCellFactory((p) -> new DateCell() {
                @Override
                public void updateItem(LocalDate ld, boolean bln) {
                    super.updateItem(ld, bln);
                    setDisable(ld.isBefore(returnStartDate) || ld.isAfter(hireLength));
                    setDisable(ld.isBefore(returnDate.getValue()));
                }
            });

            hireDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {

                    hireDate.setDayCellFactory((p) -> new DateCell() {
                        @Override
                        public void updateItem(LocalDate ld, boolean bln) {
                            super.updateItem(ld, bln);
                            setDisable(ld.isBefore(today) || ld.isAfter(next));
                            returnDate.setValue(hireDate.getValue().plusDays(1));
                        }
                    });

                    returnDate.setDayCellFactory((p) -> new DateCell() {
                        @Override
                        public void updateItem(LocalDate ld, boolean bln) {
                            super.updateItem(ld, bln);
                            setDisable(ld.isBefore(returnStartDate) || ld.isAfter(hireLength));
                            setDisable(ld.isBefore(returnDate.getValue()));
                        }
                    });

                    hireCarDate = hireDate.getValue();
                    returnCarDate = returnDate.getValue();
                    daysBetween = (int) DAYS.between(hireCarDate, returnCarDate);
                    priceValue.setText(String.valueOf(Integer.parseInt(Singleton.getInstance().getCarPrice()) * daysBetween));
                    priceEUR.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.10354).split("\\.")[0]);
                    priceUSD.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.11272).split("\\.")[0]);
                    priceGBP.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.08752).split("\\.")[0]);

                }
            });

            returnDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {

                    hireDate.setDayCellFactory((p) -> new DateCell() {
                        @Override
                        public void updateItem(LocalDate ld, boolean bln) {
                            super.updateItem(ld, bln);
                            setDisable(ld.isBefore(today) || ld.isAfter(next));
                        }
                    });

                    returnDate.setDayCellFactory((p) -> new DateCell() {
                        @Override
                        public void updateItem(LocalDate ld, boolean bln) {
                            super.updateItem(ld, bln);
                            setDisable(ld.isBefore(returnStartDate) || ld.isAfter(hireLength));
                        }
                    });

                    hireCarDate = hireDate.getValue();
                    returnCarDate = returnDate.getValue();
                    daysBetween = (int) DAYS.between(hireCarDate, returnCarDate);
                    priceValue.setText(String.valueOf(Integer.parseInt(Singleton.getInstance().getCarPrice()) * daysBetween));
                    priceEUR.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.10354).split("\\.")[0]);
                    priceUSD.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.11272).split("\\.")[0]);
                    priceGBP.setText(String.valueOf(Integer.parseInt(priceValue.getText()) * 0.08752).split("\\.")[0]);

                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Make sure you have entered both a city and the amount of seats you need for your car");
            alert.showAndWait();
        }

    }

    public void book() {

        CarRentalBooking.getInstance().setPrice(Double.parseDouble(priceValue.getText()));
        CarRentalBooking.getInstance().setHireCarDate(hireDate.getValue().toString());
        CarRentalBooking.getInstance().setReturnCarDate(returnDate.getValue().toString());
        CarRentalBooking.getInstance().setCar(carsComboBox.getSelectionModel().getSelectedItem());

        dbh.getCityID(CarRentalBooking.getInstance().getCity());
        dbh.checkCarBookingsBetweenDates(Singleton.getInstance().getCityID(), CarRentalBooking.getInstance().getCar(), CarRentalBooking.getInstance().getHireCarDate(), CarRentalBooking.getInstance().getReturnCarDate());
        dbh.getAmountOfCars(Singleton.getInstance().getCityID(), CarRentalBooking.getInstance().getCar());

        if(Integer.parseInt(Singleton.getInstance().getCarBookingsForDate()) >= Integer.parseInt(Singleton.getInstance().getCarAmount())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No more of this car type available for the date you specified");
            alert.showAndWait();
        } else if (Integer.parseInt(Singleton.getInstance().getCarBookingsForDate()) < Integer.parseInt(Singleton.getInstance().getCarAmount())) {

            if(Singleton.getInstance().getUserRole().equals("1")){

                if(Singleton.getInstance().getPickedUser() == null){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You need to enter a user to make a booking for");
                    alert.showAndWait();

                } else {

                    adminBooking.makeCarRentalBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Booking registered!");
                    alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                    alert.showAndWait();

                }

            } else if (Singleton.getInstance().getUserRole().equals("2")) {

                normalUserBooking.makeCarRentalBooking();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking registered!");
                alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                alert.showAndWait();

            }
        }
    }

    public void pickUser() {
        dbh.checkIfUsernameExists();
        dbh.checkUserRole(pickUserField.getText());
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
