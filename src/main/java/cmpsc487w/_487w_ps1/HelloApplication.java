package cmpsc487w._487w_ps1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import java.sql.*;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Admin Viewer");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws SQLException {



        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

            int count = 0;
            ID_Input id =new ID_Input();
            Scanner scan = new Scanner(System.in);
            int input = 0;
            String str;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

            while (input != 3) {

                //Selecting Admin or Swiping in
                System.out.println("Please enter a number below to continue;" +
                        "\n1) To swipe in\n2)To access Admin sign in\n3) To close program");

                input = scan.nextInt();


                switch (input) {
                    //Swipe
                    case 1:
                        System.out.println("Please enter %A followed by your 9 digit ID (Or swipe)");
                        str = scan.next();
                        StringBuffer ID = new StringBuffer(str);
                        if(id.ID_Format(ID)){
                            id.ID_add(Integer.parseInt(ID.toString()), new StringBuffer(dtf.format(LocalDateTime.now())),c);
                            id.ID_access(Integer.parseInt(ID.toString()), new StringBuffer(dtf.format(LocalDateTime.now())),c);
                        }



                        break;

                    //Admin
                    case 2:
                        if (count == 1){
                            System.exit(0);
                        }
                        launch();

                         count++;
                         break;
                    case 3:
                        System.exit(0);
                }




        }
    }
}

