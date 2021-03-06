package classes;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterController{


    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField confirmPasswordField;
    @FXML private Button helpBtn;

    private Register register = new Register();

    private DBHandler dbHandler = new DBHandler();

    private ArrayList<String> usernameList = Singleton.getInstance().getUsernameList();

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

    public void help(ActionEvent ae){
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/registerHelpScreen.fxml"));
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

    public void createAccount(ActionEvent ae){

        dbHandler.getUserIDCount();
        dbHandler.checkIfUsernameExist();

        int userid;

        if(Singleton.getInstance().getUserIDnumber() == null){
            userid = 0;
        } else {
            userid = Integer.parseInt(Singleton.getInstance().getUserIDnumber());
        }

        userid++;

        register.setFirstName(firstNameField.getText());
        register.setLastName(lastNameField.getText());
        register.setEmail(emailField.getText());

        if(Singleton.getInstance().getUsernameList() == null){
            usernameList = null;
        } else {
            usernameList = Singleton.getInstance().getUsernameList();
        }

        if(usernameList.contains(usernameField.getText())){
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
            register.setEmail(null);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You didn't enter a correct email address");
            alert.showAndWait();
        }

        if(register.getFirstName() != null && register.getLastName() != null && register.getUsername() != null && register.getEmail() != null && register.getPassword() != null){
            dbHandler.register(Integer.toString(userid), register.getFirstName(), register.getLastName(), register.getUsername(), register.getPassword(),  register.getEmail(), "2");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Created!");

            alert.setHeaderText("Your account has successfully been created! You can now log in with your created account!");


            alert.setHeaderText("Your account has successfuly been created! You can now log in with your created account!");

            alert.showAndWait();

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
}
