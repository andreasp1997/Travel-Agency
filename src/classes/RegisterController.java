package classes;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.jvm.hotspot.debugger.AddressException;

import javax.mail.internet.InternetAddress;
import java.io.IOException;

public class RegisterController {


    @FXML TextField firstNameField;
    @FXML TextField lastNameField;
    @FXML TextField usernameField;
    @FXML TextField emailField;
    @FXML TextField passwordField;
    @FXML TextField confirmPasswordField;

    Register register = new Register();

    DBHandler dbh = new DBHandler();

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

    public void createAccount(ActionEvent ae){

        dbh.handleUserID();
        dbh.checkIfUsernameExists();

        int userid = Integer.parseInt(Singleton.getInstance().getUserIDnumber());
        userid++;

        register.setFirstName(firstNameField.getText());
        register.setLastName(lastNameField.getText());
        register.setEmail(emailField.getText());


        if(Singleton.getInstance().getUsernameList().contains(usernameField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username already exists");
            alert.showAndWait();
        } else {
            register.setUsername(usernameField.getText());
        }

        if(passwordField.getText().toString().equals(confirmPasswordField.getText().toString())){
            register.setPassword(confirmPasswordField.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Strings in the password fields do not match");
            alert.showAndWait();
        }

        try {
            new InternetAddress(emailField.getText().toString()).validate();
            register.setEmail(emailField.getText().toString());
        } catch (AddressException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You didn't enter a correct email address");
            alert.showAndWait();
        } catch (javax.mail.internet.AddressException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You didn't enter a correct email address");
            alert.showAndWait();
        }

        if(register.getFirstName() != null && register.getLastName() != null && register.getUsername() != null && register.getEmail() != null && register.getPassword() != null){
            dbh.register(Integer.toString(userid), register.getFirstName(), register.getLastName(), register.getUsername(), register.getPassword(), "Normal", register.getEmail());
        }


    }
}
