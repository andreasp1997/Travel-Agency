package classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private CheckBox checkBox;
    @FXML private Label loginError;

    //Password from database
    String dbPass;
    //Password from form input
    String pass;

    //Connection to database
    DBHandler dbh;

    Singleton singleton = new Singleton();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbh = new DBHandler();
        Properties properties = loadProperties();
        username.setText(properties.getProperty("username"));
    }

    @FXML
    public void login(ActionEvent ae) {

        singleton.getInstance().setUsername(username.getText());

        dbPass = dbh.checkLogin(username.getText());
        pass = password.getText();
        Properties properties = loadProperties();


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

                if (checkBox.isSelected()) {
                    FileOutputStream fileOutputStream = new FileOutputStream("app.properties");
                    properties.setProperty("username", username.getText());
                    properties.store(fileOutputStream, null);
                    fileOutputStream.close();
                } else {
                    FileOutputStream fileOutputStream = new FileOutputStream("app.properties");
                    properties.setProperty("username","");
                    properties.store(fileOutputStream, null);
                    fileOutputStream.close();
                }


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

    private Properties loadProperties() {
        Properties appProp = new Properties();
        try (FileInputStream fis = new FileInputStream("app.properties")) {
            appProp.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appProp;
    }

}
