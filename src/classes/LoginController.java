package classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Label loginError;

    //Password from database
    String dbPass;
    //Password from form input
    String pass;

    //Connection to database
    DBHandler dbh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbh = new DBHandler();

    }

    @FXML
    public void login(ActionEvent ae) {

        dbPass = dbh.checkLogin(username.getText());
        pass = password.getText();


        if (username.getText().isEmpty() || password.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setHeaderText("Please enter both username and password");
            alert.showAndWait();

        } else if (pass.equals(dbPass)) {

            //Change scene to userMenu if login is successful
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/userMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setHeaderText("The username or password is incorrect");
            alert.showAndWait();

        }

    }

    public void back(ActionEvent ae) {
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
