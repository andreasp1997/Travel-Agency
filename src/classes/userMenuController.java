package classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by andreas on 2017-04-13.
 */
public class userMenuController {

    public void gotoMakeBooking(ActionEvent ae){

    }

    public void gotoAccountSettings(ActionEvent ae){

    }

    public void gotoEditBookings(ActionEvent ae){

    }

    public void help(ActionEvent ae){

    }

    public void logout(ActionEvent ae){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/welcomeScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
