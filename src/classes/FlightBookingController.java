package classes;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by andreas on 2017-04-24.
 */
public class FlightBookingController implements Initializable {

    DBHandler dbh = new DBHandler();
    Singleton singleton = new Singleton();

    @FXML TextField pickUserField;
    @FXML Button pickUserBtn;
    @FXML Text adminText;
    @FXML Button bookAirline1Btn;
    @FXML Button bookAirline2Btn;
    @FXML Button bookAirline3Btn;
    @FXML ComboBox selectSeats1;
    @FXML ComboBox selectSeats2;
    @FXML ComboBox selectSeats3;
    @FXML Rectangle rectangle1;
    @FXML Rectangle rectangle2;
    @FXML Rectangle rectangle3;
    @FXML Text seatsAvailable1;
    @FXML Text seatsAvailable2;
    @FXML Text seatsAvailable3;
    @FXML Text price1;
    @FXML Text price2;
    @FXML Text price3;
    @FXML ImageView img1;
    @FXML ImageView img2;
    @FXML ImageView img3;
    @FXML ComboBox<String> travelOrigin;
    @FXML ComboBox<String> travelDestination;

    private ArrayList<String> cities;
    private ObservableList<String> citiesObservable;
    private ObservableList<String> citiesObservable2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookAirline1Btn.setVisible(false);
        bookAirline2Btn.setVisible(false);
        bookAirline3Btn.setVisible(false);
        selectSeats1.setVisible(false);
        selectSeats2.setVisible(false);
        selectSeats3.setVisible(false);
        rectangle1.setVisible(false);
        rectangle2.setVisible(false);
        rectangle3.setVisible(false);
        seatsAvailable1.setVisible(false);
        seatsAvailable2.setVisible(false);
        seatsAvailable3.setVisible(false);
        price1.setVisible(false);
        price2.setVisible(false);
        price3.setVisible(false);
        img1.setVisible(false);
        img2.setVisible(false);
        img3.setVisible(false);

        dbh.checkUserRole(singleton.getInstance().getUsername());

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
        citiesObservable2 = FXCollections.observableArrayList(cities);

        travelOrigin.setItems(citiesObservable);
        travelOrigin.getSelectionModel().selectFirst();

        for(int i = 0; i < citiesObservable2.size(); i++){
            if(i == travelOrigin.getSelectionModel().getSelectedIndex()){
                citiesObservable2.remove(i);
            }
        }

        Platform.runLater(() -> travelDestination.setItems(citiesObservable2));

        //lägg till listener för combobox

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
        bookAirline1Btn.setVisible(true);
        bookAirline2Btn.setVisible(true);
        bookAirline3Btn.setVisible(true);
        selectSeats1.setVisible(true);
        selectSeats2.setVisible(true);
        selectSeats3.setVisible(true);
        rectangle1.setVisible(true);
        rectangle2.setVisible(true);
        rectangle3.setVisible(true);
        seatsAvailable1.setVisible(true);
        seatsAvailable2.setVisible(true);
        seatsAvailable3.setVisible(true);
        price1.setVisible(true);
        price2.setVisible(true);
        price3.setVisible(true);
        img1.setVisible(true);
        img2.setVisible(true);
        img3.setVisible(true);
    }
}
