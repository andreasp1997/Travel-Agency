package classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by andreas on 2017-05-08.
 */
public class AccountSettingsController implements Initializable {

    @FXML private Button helpBtn;
    @FXML private TextField usernameField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField pickUserField;
    @FXML private Button pickUserBtn;
    @FXML private Text adminText;

    private ArrayList<String> usernameList;

    private DBHandler dbHandler = new DBHandler();
    private NormalAccountSettings normalAccountSettings = new NormalAccountSettings();
    private AdminChangeUserSettings adminChangeUserSettings = new AdminChangeUserSettings();

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
    public void help(ActionEvent ae){
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/accountSettingsHelpScreen.fxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(Singleton.getInstance().getUserRole().equals("1")){

            pickUserField.setVisible(true);
            pickUserBtn.setVisible(true);
            adminText.setVisible(true);

            usernameField.setDisable(true);
            passwordField.setDisable(true);
            emailField.setDisable(true);
            firstNameField.setDisable(true);
            lastNameField.setDisable(true);

        } else if (Singleton.getInstance().getUserRole().equals("2")){

            pickUserField.setVisible(false);
            pickUserBtn.setVisible(false);
            adminText.setVisible(false);

            dbHandler.getUserID(Singleton.getInstance().getUsername());
            dbHandler.getUserInfo(Singleton.getInstance().getUserID());

            usernameField.setText(NormalAccountSettings.getInstance().getUsername());
            emailField.setText(NormalAccountSettings.getInstance().getEmail());
            firstNameField.setText(NormalAccountSettings.getInstance().getFirstName());
            lastNameField.setText(NormalAccountSettings.getInstance().getLastName());
            passwordField.setText(NormalAccountSettings.getInstance().getPassword());

        }
    }

    public void updateSettings(ActionEvent ae){

        //Checks if any fields are left empty

        if(usernameField.getText().trim().isEmpty() || usernameField.getText() == null || passwordField.getText().trim().isEmpty()
                || passwordField.getText() == null || firstNameField.getText().trim().isEmpty() || firstNameField.getText() == null
                || lastNameField.getText() == null || lastNameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()
                || emailField.getText() == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Don't leave any fields empty!");
            alert.showAndWait();

        } else {

            if(Singleton.getInstance().getUserRole().equals("1")){

                //If user making changes is administrator:

                AdminChangeUserSettings.getInstance().setUserName(usernameField.getText());
                AdminChangeUserSettings.getInstance().setPassword(passwordField.getText());
                AdminChangeUserSettings.getInstance().setFirstName(firstNameField.getText());
                AdminChangeUserSettings.getInstance().setLastName(lastNameField.getText());

                try {
                    new InternetAddress(emailField.getText().toString()).validate();
                    AdminChangeUserSettings.getInstance().setEmail(emailField.getText());

                    adminChangeUserSettings.commitChange();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Account Information Updated!");
                    alert.setHeaderText("Your account information has been updated. You will now return to the menu");
                    alert.showAndWait();

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/userMenu.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (AddressException ex) {
                    AdminChangeUserSettings.getInstance().setEmail(null);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You didn't enter a correct email address");
                    alert.showAndWait();
                }

            } else if (Singleton.getInstance().getUserRole().equals("2")){

                //If user making changes is a normal user:

                NormalAccountSettings.getInstance().setUsername(usernameField.getText());
                NormalAccountSettings.getInstance().setPassword(passwordField.getText());
                NormalAccountSettings.getInstance().setFirstName(firstNameField.getText());
                NormalAccountSettings.getInstance().setLastName(lastNameField.getText());

                try {
                    new InternetAddress(emailField.getText().toString()).validate();
                    NormalAccountSettings.getInstance().setEmail(emailField.getText());

                    normalAccountSettings.commitChange();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Account Information Updated!");
                    alert.setHeaderText("Your account information has been updated. You will now return to the menu");
                    alert.showAndWait();

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/userMenu.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (AddressException ex) {
                    NormalAccountSettings.getInstance().setEmail(null);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You didn't enter a correct email address");
                    alert.showAndWait();
                }
            }
        }
    }

    public void deleteUser(ActionEvent ae){

        if(Singleton.getInstance().getUserRole().equals("1")){
            adminChangeUserSettings.deleteUser();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Deleted");
            alert.setHeaderText("The account has been deleted including all booking for the account. You will return to the menu");
            alert.showAndWait();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/userMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(Singleton.getInstance().getUserRole().equals("2")){
            normalAccountSettings.deleteAccount();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Deleted");
            alert.setHeaderText("Your account has been deleted including all booking for your account. You will now be logged out");
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

    public void pickUser(ActionEvent ae){
        dbHandler.checkIfUsernameExist();
        dbHandler.checkUserRole(pickUserField.getText());
        usernameList = Singleton.getInstance().getUsernameList();

        if(usernameList.contains(pickUserField.getText()) && Singleton.getInstance().getUserRole().equals("2")){
            Singleton.getInstance().setPickedUser(pickUserField.getText());

            dbHandler.getUserID(pickUserField.getText());
            dbHandler.getUserInfo(Singleton.getInstance().getUserID());

            usernameField.setDisable(false);
            passwordField.setDisable(false);
            emailField.setDisable(false);
            firstNameField.setDisable(false);
            lastNameField.setDisable(false);

            usernameField.setText(AdminChangeUserSettings.getInstance().getUserName());
            emailField.setText(AdminChangeUserSettings.getInstance().getEmail());
            firstNameField.setText(AdminChangeUserSettings.getInstance().getFirstName());
            lastNameField.setText(AdminChangeUserSettings.getInstance().getLastName());
            passwordField.setText(AdminChangeUserSettings.getInstance().getPassword());

        } else if (Singleton.getInstance().getUserRole().equals(1)){
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
            usernameField.setText(null);
            emailField.setText(null);
            firstNameField.setText(null);
            lastNameField.setText(null);
            passwordField.setText(null);
        }
    }
}
