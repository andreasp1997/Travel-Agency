package classes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeScreen {

    public void loginBtnPressed(ActionEvent ae){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/loginScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerBtnPressed(ActionEvent ae){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/registerScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void helpBtnPressed(ActionEvent ae){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/helpScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void quitBtnPressed(){
        Platform.exit();
        System.exit(0);
    }
}
