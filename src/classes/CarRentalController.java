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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by safaa on 2017-05-01.
 */
public class CarRentalController implements Initializable {
    DBHandler dbh = new DBHandler();
    Singleton singleton = new Singleton();
    AdminBooking adminBooking = new AdminBooking();
    NormalUserBooking normalUserBooking = new NormalUserBooking();

    private boolean isEuropeanCity = false;
    private boolean isNorthAmericanCity = false;
    private boolean isAsianCity = false;
    private boolean isAustralianCity = false;
    private boolean isSeatsNumber4 = false;
    private boolean isSeatsNumber5 = false;
    private boolean isSeatsNumber7 = false;

    private ArrayList<String> cities;
    private ArrayList<String> cars;
    private ArrayList<String> europeanCitiesList;
    private ArrayList<String> northAmericanCitiesList;
    private ArrayList<String> asianCitiesList;
    private ArrayList<String> australianCitiesList;
    private ArrayList<String> seatsNumberList;
    private ArrayList<String> carNumber4List;
    private ArrayList<String> carNumber5List;
    private ArrayList<String> carNumber7List;

    private ObservableList<String> citiesObservable;
    private ObservableList<String> carsObservable;

    @FXML
    ComboBox<String> city;
    @FXML ComboBox<String> seats;
    @FXML ComboBox<String> seatsNr;
    @FXML
    DatePicker hireDate;
    @FXML DatePicker returnDate;
    @FXML
    Text priceText;
    @FXML Text seatsText;
    @FXML Text type;
    @FXML
    Label price;
    @FXML Label carInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.setVisible(false);
        seatsNr.setVisible(false);
        priceText.setVisible(false);
        price.setVisible(false);

        dbh.getCities();
        cities = Singleton.getInstance().getCities();
        citiesObservable = FXCollections.observableArrayList(cities);
        city.setItems(citiesObservable);
        city.getSelectionModel().selectFirst();

        dbh.getCars();
        cars = Singleton.getInstance().getCars();
        carsObservable = FXCollections.observableArrayList(cars);
        seats.setItems(carsObservable);
        seats.getSelectionModel().selectFirst();


        dbh.getCarsNumber4();
        dbh.getCarsNumber5();
        dbh.getCarsNumber7();

        carNumber4List = Singleton.getInstance().getCarsNumber4();
        carNumber5List = Singleton.getInstance().getCarsNumber5();
        carNumber7List = Singleton.getInstance().getCarsNumber7();

        System.out.println(carNumber4List);
        System.out.println(carNumber5List);
        System.out.println(carNumber7List);


        dbh.getEuropeanCities();
        dbh.getAustralianCities();
        dbh.getNorthAmericanCities();
        dbh.getAsianCities();

        europeanCitiesList = Singleton.getInstance().getEuropeanCities();
        australianCitiesList = Singleton.getInstance().getAustralianCities();
        northAmericanCitiesList = Singleton.getInstance().getNorthAmericanCities();
        asianCitiesList = Singleton.getInstance().getAsianCities();

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

    public void search(ActionEvent ae) {

        price.setText("100");

        if (city.getSelectionModel().getSelectedItem() != null && hireDate.getValue().toString() != null && returnDate.getValue().toString() != null) {
            //type.setVisible(true);
            //seatsNr.setVisible(true);
            priceText.setVisible(true);
            price.setVisible(true);

            if(europeanCitiesList.contains(city.getSelectionModel().getSelectedItem())){
                isEuropeanCity = true;
            } else{
                isEuropeanCity = false;
            }

            if(northAmericanCitiesList.contains(city.getSelectionModel().getSelectedItem())){
                isNorthAmericanCity= true;
            } else{
                isNorthAmericanCity = false;
            }

            if(asianCitiesList.contains(city.getSelectionModel().getSelectedItem())){
                isAsianCity = true;
            } else{
                isAsianCity = false;
            }

            if(australianCitiesList.contains(city.getSelectionModel().getSelectedItem())){
                isAustralianCity = true;
            } else{
                isAustralianCity = false;
            }

            if(carNumber4List.contains(seats.getSelectionModel().getSelectedItem())){
                isSeatsNumber4 = true;
            } else{
                isSeatsNumber4 = false;
            }

            if(carNumber5List.contains(seats.getSelectionModel().getSelectedItem())){
                isSeatsNumber5 = true;
            } else{
                isSeatsNumber5 = false;
            }

            if(carNumber7List.contains(seats.getSelectionModel().getSelectedItem())){
                isSeatsNumber7 = true;
            } else{
                isSeatsNumber7 = false;
            }

            if(isEuropeanCity == true && isSeatsNumber4 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 799));
            }
            if(isAsianCity == true && isSeatsNumber4 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 699));
            }
            if(isAustralianCity == true && isSeatsNumber4 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 799));
            }
            if(isNorthAmericanCity== true && isSeatsNumber4 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 799));
            }

            if(isEuropeanCity == true && isSeatsNumber5 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 899));
            }
            if(isAsianCity == true && isSeatsNumber5 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 799));
            }
            if(isAustralianCity == true && isSeatsNumber5 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 899));
            }
            if(isNorthAmericanCity== true && isSeatsNumber5 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 799));
            }

            if(isEuropeanCity == true && isSeatsNumber7 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 999));
            }
            if(isAsianCity == true && isSeatsNumber7 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 899));
            }
            if(isAustralianCity == true && isSeatsNumber7 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 999));
            }
            if(isNorthAmericanCity== true && isSeatsNumber7 == true){
                price.setText(String.valueOf(Integer.parseInt(price.getText()) + 999));
            }

            dbh.getCityID(city.getSelectionModel().getSelectedItem());
            CarRentalBooking.getInstance().setCity(Singleton.getInstance().getCityID());

            CarRentalBooking.getInstance().setHireCarDate(hireDate.getValue().toString());
            CarRentalBooking.getInstance().setReturnCarDate(returnDate.getValue().toString());
        }
    }

    public void carBooking(ActionEvent ae){

        CarRentalBooking.getInstance().setCar("1");
        CarRentalBooking.getInstance().setPrice(Double.parseDouble(price.getText()));
        dbh.bookingForCar(CarRentalBooking.getInstance().getCity(), CarRentalBooking.getInstance().getHireCarDate(), CarRentalBooking.getInstance().getReturnCarDate());
        normalUserBooking.makeCarRentalBooking();
    }
}
