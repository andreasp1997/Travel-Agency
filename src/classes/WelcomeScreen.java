package classes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreen {

    Register register = new Register();

    @FXML Button helpBtn;

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

        register.setUsername(null);
        register.setEmail(null);
        register.setPassword(null);
        register.setFirstName(null);
        register.setLastName(null);

    }

    public void helpBtnPressed(ActionEvent ae){

            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/mainMenuHelpScreen.fxml"));
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


    public void quitBtnPressed(){
        Platform.exit();
        System.exit(0);
    }
}
