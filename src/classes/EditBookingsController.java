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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by andreas on 2017-05-08.
 */
public class EditBookingsController implements Initializable {

    private DBHandler dbh = new DBHandler();
    private AdminAccountEditBooking adminAccountEditBooking = new AdminAccountEditBooking();
    private NormalAccountEditBooking normalAccountEditBooking = new NormalAccountEditBooking();

    @FXML TextField pickUserField;
    @FXML Button pickUserBtn;
    @FXML Text adminText;

    private LocalDate today = LocalDate.now();
    private LocalDate next = today.plusMonths(8);
    private LocalDate tomorrow = today.plusDays(1);

    private ArrayList<String> usernameList;

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
    @FXML private Button editBtn;
    @FXML private Button deleteBtn;
    @FXML private DatePicker datePicker;
    @FXML private DatePicker datePicker2;
    @FXML private Button saveBtn;


    private ArrayList<HotelBooking> hotelBooking;
    private ArrayList<CarRentalBooking> carBooking;
    private ArrayList<CruiseBooking> cruiseBooking;
    private ArrayList<FlightBooking> flightBooking;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hotelTable.setVisible(false);
        flightTable.setVisible(false);
        carTable.setVisible(false);
        cruiseTable.setVisible(false);
        datePicker.setVisible(false);
        datePicker2.setVisible(false);
        saveBtn.setVisible(false);

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

        datePicker.setValue(today);
        datePicker2.setValue(tomorrow);

        datePicker.setDayCellFactory((p) -> new DateCell() {
            @Override
            public void updateItem(LocalDate ld, boolean bln) {
                super.updateItem(ld, bln);
                setDisable(ld.isBefore(today) || ld.isAfter(next));
            }
        });

        datePicker2.setDayCellFactory((p) -> new DateCell() {
            @Override
            public void updateItem(LocalDate ld, boolean bln) {
                super.updateItem(ld, bln);
                setDisable(ld.isBefore(tomorrow) || ld.isAfter(next));
            }
        });

        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                datePicker.setDayCellFactory((p) -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate ld, boolean bln) {
                        super.updateItem(ld, bln);
                        setDisable(ld.isBefore(today) || ld.isAfter(next));
                        datePicker2.setValue(datePicker.getValue().plusDays(1));
                    }
                });

                datePicker2.setDayCellFactory((p) -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate ld, boolean bln) {
                        super.updateItem(ld, bln);
                        setDisable(ld.isBefore(tomorrow) || ld.isAfter(next));
                        setDisable(ld.isBefore(datePicker2.getValue()));
                    }
                });
            }
        });
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
        dbh.checkIfUsernameExist();
        dbh.checkUserRole(pickUserField.getText());
        usernameList = Singleton.getInstance().getUsernameList();

        if(usernameList.contains(pickUserField.getText()) && Singleton.getInstance().getUserRole().equals("2")){
            Singleton.getInstance().setPickedUser(pickUserField.getText());

            dbh.getUserID(pickUserField.getText());
            Singleton.getInstance().setPickedUser(Singleton.getInstance().getUserID());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("You have picked a user");
            alert.showAndWait();

        } else if(Singleton.getInstance().getUserRole().equals("1")){
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
        carTable.setVisible(false);
        flightTable.setVisible(false);
        cruiseTable.setVisible(false);

        hotelBooking = dbh.getHotelBooking(Integer.parseInt(Singleton.getInstance().getUserID()));

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

        editBtn.setOnAction(e -> {
            editHotelBookingDate();
        });

        deleteBtn.setOnAction(e -> {
            deleteHotelBooking();
        });
    }

    @FXML
    public void showCarBookings(ActionEvent ae) {

        hotelTable.setVisible(false);
        carTable.setVisible(true);
        flightTable.setVisible(false);
        cruiseTable.setVisible(false);

        carBooking = dbh.getCarBooking(Integer.parseInt(Singleton.getInstance().getUserID()));

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

        editBtn.setOnAction(e -> {
            editCarRentalBookingDate();
        });

        deleteBtn.setOnAction(e -> {
            deleteCarRentalBooking();
        });
    }

    @FXML
    public void showCruiseBookings(ActionEvent ae) {

        hotelTable.setVisible(false);
        carTable.setVisible(false);
        flightTable.setVisible(false);
        cruiseTable.setVisible(true);

        cruiseBooking = dbh.getCruiseBooking(Integer.parseInt(Singleton.getInstance().getUserID()));

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

        editBtn.setOnAction(e -> {
            editCruiseBookingDate();
        });

        deleteBtn.setOnAction(e -> {
            deleteCruiseBooking();
        });
    }

    @FXML
    public void showFlightBookings(ActionEvent ae) {

        hotelTable.setVisible(false);
        carTable.setVisible(false);
        flightTable.setVisible(true);
        cruiseTable.setVisible(false);

        flightBooking = dbh.getFlightBooking(Integer.parseInt(Singleton.getInstance().getUserID()));

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

        editBtn.setOnAction(e -> {
            editFlightBookingDate();
        });

        deleteBtn.setOnAction(e -> {
            deleteFlightBooking();
        });

    }

    public void editFlightBookingDate(){
        datePicker.setVisible(true);
        saveBtn.setVisible(true);

        ObservableList<FlightBooking> flightBookingSelected;
        flightBookingSelected = flightTable.getSelectionModel().getSelectedItems();
        for (FlightBooking flightBooking : flightBookingSelected) {
            FlightBooking.getInstance().setOrigin(flightBooking.getOrigin());
            FlightBooking.getInstance().setDate(flightBooking.getDate());
            FlightBooking.getInstance().setPrice(flightBooking.getPrice());
            FlightBooking.getInstance().setDestination(flightBooking.getDestination());
            FlightBooking.getInstance().setAirline(flightBooking.getAirline());
            saveBtn.setOnAction(e -> {
                Singleton.getInstance().setNewFlightBookingDate(String.valueOf(datePicker.getValue()));
                dbh.checkUserRole(Singleton.getInstance().getUsername());

                if(Singleton.getInstance().getUserRole().equals("2")) {
                    normalAccountEditBooking.commitChangeForFlightBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("You have successfully edited a booking");
                    alert.showAndWait();

                } else {
                    adminAccountEditBooking.commitChangeForCarRentalBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("You have successfully edited a booking");
                    alert.showAndWait();

                }

                datePicker.setVisible(false);
                saveBtn.setVisible(false);
            });
        }

    }

    public void editCruiseBookingDate(){
        datePicker.setVisible(true);
        saveBtn.setVisible(true);
        datePicker.setVisible(true);
        saveBtn.setVisible(true);
        ObservableList<CruiseBooking> cruiseBookingSelected;
        cruiseBookingSelected = cruiseTable.getSelectionModel().getSelectedItems();

        for (CruiseBooking cruiseBooking : cruiseBookingSelected) {
            CruiseBooking.getInstance().setOrigin(cruiseBooking.getOrigin());
            CruiseBooking.getInstance().setDestination(cruiseBooking.getDestination());
            CruiseBooking.getInstance().setDate(cruiseBooking.getDate());
            CruiseBooking.getInstance().setPrice(cruiseBooking.getPrice());
            CruiseBooking.getInstance().setRoom(cruiseBooking.getRoom());
            saveBtn.setOnAction(e -> {
                Singleton.getInstance().setNewCruiseBookingDate(String.valueOf(datePicker.getValue()));
                dbh.checkUserRole(Singleton.getInstance().getUsername());

                if (Singleton.getInstance().getUserRole().equals("2")) {
                    normalAccountEditBooking.commitChangeForCruiseBooking();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("You have successfully deleted a booking");
                    alert.showAndWait();

                } else {
                    adminAccountEditBooking.commitChangeForCruiseBooking();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("You have successfully deleted a booking");
                    alert.showAndWait();

                }
                datePicker.setVisible(false);
                saveBtn.setVisible(false);
            });
        }
    }

    public void editHotelBookingDate(){
        datePicker.setVisible(true);
        datePicker2.setVisible(true);
        saveBtn.setVisible(true);
        datePicker.setVisible(true);
        datePicker2.setVisible(true);
        saveBtn.setVisible(true);

        ObservableList<HotelBooking> hotelBookingSelected;
        hotelBookingSelected = hotelTable.getSelectionModel().getSelectedItems();
        for (HotelBooking hotelBooking : hotelBookingSelected) {
            Singleton.getInstance().setHotelCheckInDate(hotelBooking.getCheckinDate());
            Singleton.getInstance().setHotelName(hotelBooking.getHotelName());
            Singleton.getInstance().setHotelCheckOutDate(hotelBooking.getCheckoutDate());
            Singleton.getInstance().setHotelRoomSize(String.valueOf(hotelBooking.getRoomSize()));

            saveBtn.setOnAction(e -> {
                Singleton.getInstance().setNewHotelCheckInDate(String.valueOf(datePicker.getValue()));
                Singleton.getInstance().setNewHotelCheckOutDate(String.valueOf(datePicker2.getValue()));
                dbh.checkUserRole(Singleton.getInstance().getUsername());

                if (Singleton.getInstance().getUserRole().equals("2")) {
                    normalAccountEditBooking.commitChangeForHotelBooking();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("You have successfully edited a booking");
                    alert.showAndWait();

                } else {
                    adminAccountEditBooking.commitChangeForHotelBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("You have successfully edited a booking");
                    alert.showAndWait();
                }

                datePicker.setVisible(false);
                datePicker2.setVisible(false);
                saveBtn.setVisible(false);
            });
        }
    }

    public void editCarRentalBookingDate(){
        datePicker.setVisible(true);
        datePicker2.setVisible(true);
        saveBtn.setVisible(true);

        ObservableList<CarRentalBooking> carRentalBookingSelected;
        carRentalBookingSelected = carTable.getSelectionModel().getSelectedItems();

        for (CarRentalBooking carRentalBooking: carRentalBookingSelected) {
            carRentalBooking.getInstance().setCity(carRentalBooking.getCity());
            carRentalBooking.getInstance().setCar(carRentalBooking.getCar());
            carRentalBooking.getInstance().setHireCarDate(carRentalBooking.getHireCarDate());
            carRentalBooking.getInstance().setReturnCarDate(carRentalBooking.getReturnCarDate());
            carRentalBooking.getInstance().setPrice(carRentalBooking.getPrice());
            carRentalBooking.getInstance().setSeats(carRentalBooking.getSeats());
            saveBtn.setOnAction(e -> {
               Singleton.getInstance().setNewCarRentalStartDate(String.valueOf(datePicker.getValue()));
                Singleton.getInstance().setNewCarRentalReturnDate(String.valueOf(datePicker2.getValue()));

                dbh.checkUserRole(Singleton.getInstance().getUsername());


                if(Singleton.getInstance().getUserRole().equals("2")) {
                    normalAccountEditBooking.commitChangeForCarRentalBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("You have successfully edited a booking");
                    alert.showAndWait();

                } else {
                    adminAccountEditBooking.commitChangeForCarRentalBooking();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("You have successfully edited a booking");
                    alert.showAndWait();

                }
                datePicker.setVisible(false);
                datePicker2.setVisible(false);
                saveBtn.setVisible(false);
            });
        }
    }

    private void deleteFlightBooking() {
        ObservableList<FlightBooking> flightBookingSelected, allFlightBookings;
        allFlightBookings = flightTable.getItems();
        flightBookingSelected = flightTable.getSelectionModel().getSelectedItems();

        for (FlightBooking flightBooking : flightBookingSelected) {
            FlightBooking.getInstance().setOrigin(flightBooking.getOrigin());
            FlightBooking.getInstance().setDate(flightBooking.getDate());
            FlightBooking.getInstance().setPrice(flightBooking.getPrice());
            FlightBooking.getInstance().setDestination(flightBooking.getDestination());
            FlightBooking.getInstance().setAirline(flightBooking.getAirline());

            dbh.checkUserRole(Singleton.getInstance().getUsername());

            if(Singleton.getInstance().getUserRole().equals("2")) {
                normalAccountEditBooking.deleteFlightBooking();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have successfully deleted a booking");
                alert.showAndWait();

            } else {
                adminAccountEditBooking.deleteFlightBooking();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have successfully deleted a booking");
                alert.showAndWait();
            }
            allFlightBookings.remove(flightBooking);
        }
    }

    public void deleteCruiseBooking(){
        ObservableList<CruiseBooking> cruiseBookingSelected, allCruiseBookings;
        allCruiseBookings = cruiseTable.getItems();
        cruiseBookingSelected = cruiseTable.getSelectionModel().getSelectedItems();

        for (CruiseBooking cruiseBooking : cruiseBookingSelected) {
            CruiseBooking.getInstance().setOrigin(cruiseBooking.getOrigin());
            CruiseBooking.getInstance().setDestination(cruiseBooking.getDestination());
            CruiseBooking.getInstance().setDate(cruiseBooking.getDate());

            dbh.checkUserRole(Singleton.getInstance().getUsername());

            if(Singleton.getInstance().getUserRole().equals("2")) {
                normalAccountEditBooking.deleteCruiseBooking();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have successfully deleted a booking");
                alert.showAndWait();

            } else {
                adminAccountEditBooking.deleteCruiseBooking();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have successfully deleted a booking");
                alert.showAndWait();

            }

            allCruiseBookings.remove(cruiseBooking);
        }
    }

    public void deleteCarRentalBooking() {
        ObservableList<CarRentalBooking> carRentalBookingSelected, allCarRentalBookings;
        allCarRentalBookings = carTable.getItems();
        carRentalBookingSelected = carTable.getSelectionModel().getSelectedItems();

        for (CarRentalBooking carRentalBooking: carRentalBookingSelected) {
            carRentalBooking.getInstance().setCity(carRentalBooking.getCity());
            carRentalBooking.getInstance().setCar(carRentalBooking.getCar());
            carRentalBooking.getInstance().setHireCarDate(carRentalBooking.getHireCarDate());
            carRentalBooking.getInstance().setReturnCarDate(carRentalBooking.getReturnCarDate());
            carRentalBooking.getInstance().setPrice(carRentalBooking.getPrice());
            carRentalBooking.getInstance().setSeats(carRentalBooking.getSeats());

            dbh.checkUserRole(Singleton.getInstance().getUsername());

            if(Singleton.getInstance().getUserRole().equals("2")) {
                normalAccountEditBooking.deleteCarRentalBooking();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have successfully deleted a booking");
                alert.showAndWait();

            } else {
                adminAccountEditBooking.deleteCarRentalBooking();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have successfully deleted a booking");
                alert.showAndWait();

            }
            allCarRentalBookings.remove(carRentalBooking);
        }
    }

    public void deleteHotelBooking() {
        ObservableList<HotelBooking> hotelBookingSelected, allHotelBookings;
        allHotelBookings = hotelTable.getItems();
        hotelBookingSelected = hotelTable.getSelectionModel().getSelectedItems();

        for (HotelBooking hotelBooking : hotelBookingSelected) {
            Singleton.getInstance().setHotelCheckInDate(hotelBooking.getCheckinDate());
            Singleton.getInstance().setHotelName(hotelBooking.getHotelName());
            Singleton.getInstance().setHotelCheckOutDate(hotelBooking.getCheckoutDate());
            Singleton.getInstance().setHotelRoomSize(String.valueOf(hotelBooking.getRoomSize()));
            dbh.checkUserRole(Singleton.getInstance().getUsername());

            if(Singleton.getInstance().getUserRole().equals("2")) {
                normalAccountEditBooking.deleteHotelBooking();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have successfully deleted a booking");
                alert.showAndWait();

            } else {
                adminAccountEditBooking.deleteHotelBooking();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have successfully deleted a booking");
                alert.showAndWait();
            }

            allHotelBookings.remove(hotelBooking);

        }
    }
}
