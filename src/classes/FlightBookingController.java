package classes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by andreas on 2017-04-24.
 */
public class FlightBookingController implements Initializable {

    @FXML TextField pickUserField;
    @FXML Button pickUserBtn;
    @FXML Text adminText;

    @FXML Button bookAirline1;
    @FXML Button bookAirline2;
    @FXML Button bookAirline3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
