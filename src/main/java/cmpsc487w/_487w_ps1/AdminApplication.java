package cmpsc487w._487w_ps1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AdminApplication {


    @FXML
    TextField Admin_ID;
    @FXML
    PasswordField Password;
    @FXML
    Label tf;

    @FXML
    protected void onSigninButtonClick() throws IOException {

        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Admin_signin");


            while (rs.next()) {
                String Stored_password = rs.getString("Admin_password");
                if (Stored_password.equals(Password.getText())){
                    tf.setText("Good Job");

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TableView.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage primaryStage = (Stage) tf.getScene().getWindow();
                    primaryStage.setTitle("Table Viewer");
                    primaryStage.setScene(scene);
                    rs.close();
                    break;
                }
                tf.setText("Please try again");
            }

        } catch(SQLException e) {
            System.out.println("SQL exception occured" + e);
        }

    }




}