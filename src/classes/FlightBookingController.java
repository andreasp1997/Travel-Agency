package classes;

import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by andreas on 2017-04-24.
 */
public class FlightBookingController implements Initializable, ChangeCurrency {

    DBHandler dbh = new DBHandler();
    Singleton singleton = new Singleton();
    AdminBooking adminBooking = new AdminBooking();
    NormalUserBooking normalUserBooking = new NormalUserBooking();

    private boolean isEuropeanCity = false;
    private boolean isNorthAmericanCity = false;
    private boolean isAsianCity = false;
    private boolean isAustralianCity = false;

    private ArrayList<String> usernameList;

    @FXML private TextField pickUserField;
    @FXML private Button pickUserBtn;
    @FXML private Text adminText;
    @FXML private Button bookAirline1Btn;
    @FXML private Button bookAirline2Btn;
    @FXML private Button bookAirline3Btn;
    @FXML private Rectangle rectangle1;
    @FXML private Rectangle rectangle2;
    @FXML private Rectangle rectangle3;
    @FXML private Text seatsAvailable1;
    @FXML private Text seatsAvailable2;
    @FXML private Text seatsAvailable3;
    @FXML private Text price1;
    @FXML private Text price2;
    @FXML private Text price3;
    @FXML private Text priceAmount1;
    @FXML private Text priceAmount2;
    @FXML private Text priceAmount3;
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img3;
    @FXML private ComboBox<String> travelOrigin;
    @FXML private ComboBox<String> travelDestination;
    @FXML private DatePicker date;
    @FXML private ComboBox<String> currencyComboBox;
    @FXML private Text priceEUR1;
    @FXML private Text priceEUR2;
    @FXML private Text priceEUR3;
    @FXML private Text priceUSD1;
    @FXML private Text priceUSD2;
    @FXML private Text priceUSD3;
    @FXML private Text priceGBP1;
    @FXML private Text priceGBP2;
    @FXML private Text priceGBP3;
    @FXML private Button selectCurrencyBtn;

    private ArrayList<String> cities;
    private ObservableList<String> citiesObservable;
    private ObservableList<String> citiesObservable2;
    private ArrayList<String> europeanCitiesList;
    private ArrayList<String> northAmericanCitiesList;
    private ArrayList<String> asianCitiesList;
    private ArrayList<String> australianCitiesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookAirline1Btn.setVisible(false);
        bookAirline2Btn.setVisible(false);
        bookAirline3Btn.setVisible(false);
        rectangle1.setVisible(false);
        rectangle2.setVisible(false);
        rectangle3.setVisible(false);
        price1.setVisible(false);
        price2.setVisible(false);
        price3.setVisible(false);
        priceAmount1.setVisible(false);
        priceAmount2.setVisible(false);
        priceAmount3.setVisible(false);
        img1.setVisible(false);
        img2.setVisible(false);
        img3.setVisible(false);
        priceEUR1.setVisible(false);
        priceEUR2.setVisible(false);
        priceEUR3.setVisible(false);
        priceUSD1.setVisible(false);
        priceUSD2.setVisible(false);
        priceUSD3.setVisible(false);
        priceGBP1.setVisible(false);
        priceGBP2.setVisible(false);
        priceGBP3.setVisible(false);
        currencyComboBox.setVisible(false);
        selectCurrencyBtn.setVisible(false);

        currencyComboBox.getItems().addAll("SEK", "USD", "GBP", "EUR");
        currencyComboBox.getSelectionModel().selectFirst();

        dbh.checkUserRole(singleton.getInstance().getUsername());

        date.setValue(LocalDate.now());

        LocalDate today = LocalDate.now();
        LocalDate next = today.plusMonths(8);

        date.setDayCellFactory((p) -> new DateCell() {
            @Override
            public void updateItem(LocalDate ld, boolean bln) {
                super.updateItem(ld, bln);
                setDisable(ld.isBefore(today) || ld.isAfter(next));
            }
        });

        if(singleton.getInstance().getUserRole().equals("1")){

            pickUserField.setVisible(true);
            pickUserBtn.setVisible(true);
            adminText.setVisible(true);

        } else if (singleton.getInstance().getUserRole().equals("2")) {
            pickUserField.setVisible(false);
            pickUserBtn.setVisible(false);
            adminText.setVisible(false);
        }

        dbh.getCities();
        cities = Singleton.getInstance().getCities();
        citiesObservable = FXCollections.observableArrayList(cities);

        travelOrigin.setItems(citiesObservable);

        travelOrigin.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                travelDestination.getSelectionModel().select(null);
                citiesObservable2 = FXCollections.observableArrayList(cities);

                for(int i = 0; i < citiesObservable2.size(); i++){
                    if(i == travelOrigin.getSelectionModel().getSelectedIndex()){
                        citiesObservable2.remove(i);
                        travelDestination.setItems(citiesObservable2);
                    }
                }
            }
        });

        dbh.getEuropeanCities();
        dbh.getAustralianCities();
        dbh.getNorthAmericanCities();
        dbh.getAsianCities();

        europeanCitiesList = Singleton.getInstance().getEuropeanCities();
        australianCitiesList = Singleton.getInstance().getAustralianCities();
        northAmericanCitiesList = Singleton.getInstance().getNorthAmericanCities();
        asianCitiesList = Singleton.getInstance().getAsianCities();

        System.out.println(europeanCitiesList);
        System.out.println(australianCitiesList);
        System.out.println(northAmericanCitiesList);
        System.out.println(asianCitiesList);

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
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/flightHelpScreen.fxml"));
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

    public void search(ActionEvent ae){

        priceAmount1.setText("1800");
        priceAmount2.setText("1500");
        priceAmount3.setText("2100");

        currencyComboBox.getSelectionModel().select("SEK");
        priceEUR1.setVisible(false);
        priceEUR2.setVisible(false);
        priceEUR3.setVisible(false);
        priceUSD1.setVisible(false);
        priceUSD2.setVisible(false);
        priceUSD3.setVisible(false);
        priceGBP1.setVisible(false);
        priceGBP2.setVisible(false);
        priceGBP3.setVisible(false);

        if(travelOrigin.getSelectionModel().getSelectedItem() != null && travelDestination.getSelectionModel().getSelectedItem() != null && date.getValue().toString() != null){

            bookAirline1Btn.setVisible(true);
            bookAirline2Btn.setVisible(true);
            bookAirline3Btn.setVisible(true);
            rectangle1.setVisible(true);
            rectangle2.setVisible(true);
            rectangle3.setVisible(true);
            price1.setVisible(true);
            price2.setVisible(true);
            price3.setVisible(true);
            priceAmount1.setVisible(true);
            priceAmount2.setVisible(true);
            priceAmount3.setVisible(true);
            img1.setVisible(true);
            img2.setVisible(true);
            img3.setVisible(true);
            currencyComboBox.setVisible(true);
            selectCurrencyBtn.setVisible(true);

            if(europeanCitiesList.contains(travelOrigin.getSelectionModel().getSelectedItem()) || europeanCitiesList.contains(travelDestination.getSelectionModel().getSelectedItem())){
                isEuropeanCity = true;
            } else{
                isEuropeanCity = false;
            }

            if(northAmericanCitiesList.contains(travelOrigin.getSelectionModel().getSelectedItem()) || northAmericanCitiesList.contains(travelDestination.getSelectionModel().getSelectedItem())){
                isNorthAmericanCity= true;
            } else{
                isNorthAmericanCity = false;
            }

            if(asianCitiesList.contains(travelOrigin.getSelectionModel().getSelectedItem()) || asianCitiesList.contains(travelDestination.getSelectionModel().getSelectedItem())){
                isAsianCity = true;
            } else{
                isAsianCity = false;
            }

            if(australianCitiesList.contains(travelOrigin.getSelectionModel().getSelectedItem()) || australianCitiesList.contains(travelDestination.getSelectionModel().getSelectedItem())){
                isAustralianCity = true;
            } else{
                isAustralianCity = false;
            }

            if(isEuropeanCity == true && isNorthAmericanCity == true){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 1200));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 1200));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 1200));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isEuropeanCity == true && isAsianCity == true){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 1800));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 1800));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 1800));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isEuropeanCity == true && isAustralianCity == true){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 2000));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 2000));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 2000));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isNorthAmericanCity == true && isAsianCity == true){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 2100));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 2100));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 2100));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isNorthAmericanCity == true && isAustralianCity == true){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 1500));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 1500));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 1500));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isAsianCity == true && isAustralianCity == true){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 2050));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 2050));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 2050));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isEuropeanCity == true && isAsianCity == false && isNorthAmericanCity == false && isAustralianCity == false){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 500));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 500));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 500));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isNorthAmericanCity == true && isAsianCity == false && isEuropeanCity == false && isAustralianCity == false){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 300));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 300));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 300));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isAsianCity == true && isEuropeanCity == false && isNorthAmericanCity == false && isAustralianCity == false){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 600));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 600));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 600));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            if(isAustralianCity == true && isAsianCity == false && isNorthAmericanCity == false && isEuropeanCity == false){
                priceAmount1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) + 400));
                priceAmount2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) + 400));
                priceAmount3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) + 400));
                priceEUR1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.10354).split("\\.")[0]);
                priceEUR2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.10354).split("\\.")[0]);
                priceEUR3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.10354).split("\\.")[0]);
                priceUSD1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.11272).split("\\.")[0]);
                priceUSD2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.11272).split("\\.")[0]);
                priceUSD3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.11272).split("\\.")[0]);
                priceGBP1.setText(String.valueOf(Integer.parseInt(priceAmount1.getText()) * 0.08752).split("\\.")[0]);
                priceGBP2.setText(String.valueOf(Integer.parseInt(priceAmount2.getText()) * 0.08752).split("\\.")[0]);
                priceGBP3.setText(String.valueOf(Integer.parseInt(priceAmount3.getText()) * 0.08752).split("\\.")[0]);
            }

            dbh.getCityID(travelOrigin.getSelectionModel().getSelectedItem());
            FlightBooking.getInstance().setOrigin(Singleton.getInstance().getCityID());
            dbh.getCityID(travelDestination.getSelectionModel().getSelectedItem());
            FlightBooking.getInstance().setDestination(Singleton.getInstance().getCityID());

            FlightBooking.getInstance().setDate(date.getValue().toString());

        }
    }

    public void pickUser(ActionEvent ae){
        dbh.checkIfUsernameExists();
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

    public void airlineBooking1(ActionEvent ae){

        FlightBooking.getInstance().setAirline("1");
        FlightBooking.getInstance().setPrice(Double.parseDouble(priceAmount1.getText()));

        dbh.bookingAmountForFlight(FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());

        if(Singleton.getInstance().getBookedTicketsForFlight().equals("200")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No more seats available. Check another airline or date");
            alert.showAndWait();
        } else {
            if(singleton.getInstance().getUserRole().equals("1")){

                if(Singleton.getInstance().getPickedUser() == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You didn't enter a user to make a booking for");
                    alert.showAndWait();
                } else {
                    adminBooking.makeFlightBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Booking registered!");
                    alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                    alert.showAndWait();
                }

            } else if (singleton.getInstance().getUserRole().equals("2")) {

                normalUserBooking.makeFlightBooking();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking registered!");
                alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                alert.showAndWait();
            }
        }
    }

    public void airlineBooking2(ActionEvent ae){

        FlightBooking.getInstance().setAirline("2");
        FlightBooking.getInstance().setPrice(Double.parseDouble(priceAmount2.getText()));

        dbh.bookingAmountForFlight(FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());

        if(Singleton.getInstance().getBookedTicketsForFlight().equals("200")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No more seats available. Check another airline or date");
            alert.showAndWait();
        } else {
            if(singleton.getInstance().getUserRole().equals("1")){

                if(Singleton.getInstance().getPickedUser() == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You didn't enter a user to make a booking for");
                    alert.showAndWait();
                } else {
                    adminBooking.makeFlightBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Booking registered!");
                    alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                    alert.showAndWait();
                }

            } else if (singleton.getInstance().getUserRole().equals("2")) {

                normalUserBooking.makeFlightBooking();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking registered!");
                alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                alert.showAndWait();
            }
        }
    }

    public void airlineBooking3(ActionEvent ae){

        FlightBooking.getInstance().setAirline("3");
        FlightBooking.getInstance().setPrice(Double.parseDouble(priceAmount3.getText()));

        dbh.bookingAmountForFlight(FlightBooking.getInstance().getOrigin(), FlightBooking.getInstance().getDestination(), FlightBooking.getInstance().getDate());

        if(Singleton.getInstance().getBookedTicketsForFlight().equals("200")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No more seats available. Check another airline or date");
            alert.showAndWait();
        } else {
            if(singleton.getInstance().getUserRole().equals("1")){

                if(Singleton.getInstance().getPickedUser() == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You didn't enter a user to make a booking for");
                    alert.showAndWait();
                } else {
                    adminBooking.makeFlightBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Booking registered!");
                    alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                    alert.showAndWait();
                }

            } else if (singleton.getInstance().getUserRole().equals("2")) {

                normalUserBooking.makeFlightBooking();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking registered!");
                alert.setHeaderText("The booking has now been registered and can be viewed in the 'Edit Bookings' menu!");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void selectCurrency() {

        if(currencyComboBox.getSelectionModel().getSelectedItem().equals("SEK")){

            priceEUR1.setVisible(false);
            priceEUR2.setVisible(false);
            priceEUR3.setVisible(false);
            priceUSD1.setVisible(false);
            priceUSD2.setVisible(false);
            priceUSD3.setVisible(false);
            priceGBP1.setVisible(false);
            priceGBP2.setVisible(false);
            priceGBP3.setVisible(false);
            priceAmount1.setVisible(true);
            priceAmount2.setVisible(true);
            priceAmount3.setVisible(true);

        } else if (currencyComboBox.getSelectionModel().getSelectedItem().equals("USD")){

            priceEUR1.setVisible(false);
            priceEUR2.setVisible(false);
            priceEUR3.setVisible(false);
            priceUSD1.setVisible(true);
            priceUSD2.setVisible(true);
            priceUSD3.setVisible(true);
            priceGBP1.setVisible(false);
            priceGBP2.setVisible(false);
            priceGBP3.setVisible(false);
            priceAmount1.setVisible(false);
            priceAmount2.setVisible(false);
            priceAmount3.setVisible(false);

        } else if (currencyComboBox.getSelectionModel().getSelectedItem().equals("GBP")){

            priceEUR1.setVisible(false);
            priceEUR2.setVisible(false);
            priceEUR3.setVisible(false);
            priceUSD1.setVisible(false);
            priceUSD2.setVisible(false);
            priceUSD3.setVisible(false);
            priceGBP1.setVisible(true);
            priceGBP2.setVisible(true);
            priceGBP3.setVisible(true);
            priceAmount1.setVisible(false);
            priceAmount2.setVisible(false);
            priceAmount3.setVisible(false);

        } else if (currencyComboBox.getSelectionModel().getSelectedItem().equals("EUR")){

            priceEUR1.setVisible(true);
            priceEUR2.setVisible(true);
            priceEUR3.setVisible(true);
            priceUSD1.setVisible(false);
            priceUSD2.setVisible(false);
            priceUSD3.setVisible(false);
            priceGBP1.setVisible(false);
            priceGBP2.setVisible(false);
            priceGBP3.setVisible(false);
            priceAmount1.setVisible(false);
            priceAmount2.setVisible(false);
            priceAmount3.setVisible(false);

        }
    }
}
