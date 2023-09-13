package cmpsc487w._487w_ps1;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class TableView implements Initializable{
    @FXML
    javafx.scene.control.TableView <Swipe_node> Swipe_Table;
    @FXML TableColumn <Swipe_node,String> Swipe_ID;
    @FXML TableColumn <Swipe_node,String> date_time;
    ObservableList<Swipe_node> S_items = FXCollections.observableArrayList();


    @FXML
    javafx.scene.control.TableView <Access_node> access_table;
    @FXML TableColumn <Access_node,String> access_ID;
    @FXML TableColumn <Access_node,Boolean> active;
    @FXML TableColumn <Access_node,Boolean> suspended;
    ObservableList<Access_node> A_items = FXCollections.observableArrayList();

    Connection c = null;


        public void initialize(URL location, ResourceBundle resources){

            try {
                c = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Opened database successfully");


            Swipe_Table.setItems(S_items);
            Swipe_ID.setCellValueFactory(new PropertyValueFactory<Swipe_node, String>("Id"));
            date_time.setCellValueFactory(new PropertyValueFactory<Swipe_node,String>("date_time"));

            access_table.setItems(A_items);
            access_ID.setCellValueFactory(new PropertyValueFactory<Access_node, String>("Id"));
            active.setCellValueFactory(new PropertyValueFactory<Access_node,Boolean>("Active"));
            suspended.setCellValueFactory(new PropertyValueFactory<Access_node,Boolean>("Suspended"));


            baseSwipeTable();
            baseAccessTable();

        }

        public void baseSwipeTable(){
            S_items.clear();

            try {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Swipe");


                while (rs.next()) {
                    int id = rs.getInt("Id_number");
                    String date_time = rs.getString("date_time");

                    S_items.add(new Swipe_node(new SimpleStringProperty(Integer.toString(id)),new SimpleStringProperty(date_time)));
                }
                //Swipe_Table.refresh();
                rs.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }


    public void baseAccessTable(){
            A_items.clear();

        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Id_access");


            while (rs.next()) {
                int id = rs.getInt("Id_number");
                boolean active = rs.getBoolean("Active");
                boolean Suspended = rs.getBoolean("Suspended");

                A_items.add(new Access_node(new SimpleStringProperty(Integer.toString(id)),new SimpleBooleanProperty(active),new SimpleBooleanProperty(Suspended)));
            }

            rs.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


  @FXML TextField ID_Search;


    @FXML
    protected void onSwipeIdSearch() throws IOException {
        S_items.clear();
        A_items.clear();

        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Swipe");


            while (rs.next()) {
                int id = rs.getInt("Id_number");
                String date_time = rs.getString("date_time");
                if(Integer.toString(id).equals(ID_Search.getText())){


                    S_items.add(new Swipe_node(new SimpleStringProperty(Integer.toString(id)),new SimpleStringProperty(date_time)));
                }
            }
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Id_access");


            while (rs.next()) {
                int id = rs.getInt("Id_number");
                boolean active = rs.getBoolean("Active");
                boolean suspended = rs.getBoolean("Suspended");
                if(Integer.toString(id).equals(ID_Search.getText())){


                    A_items.add(new Access_node(new SimpleStringProperty(Integer.toString(id)),new SimpleBooleanProperty(active),new SimpleBooleanProperty(suspended)));
                }
            }

            rs.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }}



    @FXML DatePicker Date_Search;
    @FXML CheckBox All_day;
    @FXML TextField Time_search;
    @FXML Label Date_Format;

    @FXML
    protected void onSwipeDateSearch() throws IOException {
        Date_Format.setText("");
        S_items.clear();
        StringBuffer selectedDate = new StringBuffer(Date_Search.getEditor().getText());
        if (selectedDate.length()<=1){
            Date_Format.setText("Please enter date");
            return;
        }
        if(selectedDate.charAt(1) == '/')
            selectedDate.insert(0,0);
        if(selectedDate.charAt(4) == '/')
            selectedDate.insert(3,0);

        StringBuffer selectedTime = new StringBuffer(Time_search.getText());


        S_items.clear();

        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Swipe");


            while (rs.next()) {
                int id = rs.getInt("Id_number");
                String date_time = rs.getString("date_time");
                StringBuffer date_check = new StringBuffer(date_time);
                if(All_day.isSelected()){
                    date_check.delete(10,19);
                    if(date_check.toString().equals(selectedDate.toString())){
                        S_items.add(new Swipe_node(new SimpleStringProperty(Integer.toString(id)), new SimpleStringProperty(date_time)));
                    }
                }
                else {
                    if(selectedTime.length()==2){
                        date_check.delete(13,19);
                        if(date_check.toString().equals(selectedDate.toString()+" "+selectedTime.toString())){
                            S_items.add(new Swipe_node(new SimpleStringProperty(Integer.toString(id)), new SimpleStringProperty(date_time)));
                        }

                    }
                    else if (selectedTime.length()==5){
                        date_check.delete(16,19);
                        if(date_check.toString().equals(selectedDate.toString()+" "+selectedTime.toString())){
                            S_items.add(new Swipe_node(new SimpleStringProperty(Integer.toString(id)), new SimpleStringProperty(date_time)));
                        }

                    }
                    else{
                        Date_Format.setText("Your formatting \nwas incorrect");

                    }

                }
            }

            rs.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }}
    @FXML TextField ID_access;
    ID_Input id =new ID_Input();
    @FXML Label access_lable;
    @FXML
    protected void onActivateButtonClick() throws IOException {

        try {
            if(id.ID_Validate(new StringBuffer(ID_access.getText()))){
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Id_access");


                while (rs.next()) {
                    int id = rs.getInt("Id_number");
                    if (Integer.toString(id).equals(ID_access.getText())){
                        access_lable.setText("already in System");
                        return;
                    }}

                stmt = c.createStatement();
                stmt.executeUpdate("INSERT INTO Id_access VALUES ("+ID_access.getText()+",true,false)");
                baseAccessTable();
            }
            else{
                access_lable.setText("Not Proper Format");
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    @FXML
    protected void onSuspendButtonClick() throws IOException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Id_access");


            while (rs.next()) {
                int id = rs.getInt("Id_number");
                if (Integer.toString(id).equals(ID_access.getText())){

                    boolean active = rs.getBoolean("Active");
                    boolean suspend = rs.getBoolean("suspended");
                    if(active==true&&suspend==false){
                        stmt = c.createStatement();
                        stmt.executeUpdate("UPDATE Id_access SET suspended=true WHERE Id_number ="+id+";");

                        access_lable.setText("Account has been suspended");
                        baseAccessTable();

                    }
                    else if(active==true&&suspend==true){
                        access_lable.setText("Account was already suspended");
                    }
                    if(active==false){
                        access_lable.setText("Account needs to be activated first");
                    }
                    return;
                }

            }
            access_lable.setText("Account does not exist");
            rs.close();
        } catch(SQLException e) {
            System.out.println("SQL exception occured" + e);
        }
    }


    @FXML
    protected void onReactivateButtonClick() throws IOException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Id_access");


            while (rs.next()) {
                int id = rs.getInt("Id_number");
                if (Integer.toString(id).equals(ID_access.getText())){

                    boolean active = rs.getBoolean("Active");
                    boolean suspend = rs.getBoolean("suspended");
                    if(active==true&&suspend==false){
                        access_lable.setText("Account was already reactivated");
                    }
                    else if(active==true&&suspend==true){

                        stmt = c.createStatement();
                        stmt.executeUpdate("UPDATE Id_access SET suspended=false WHERE Id_number ="+id+";");

                        access_lable.setText("Account has been reactivated");
                        baseAccessTable();

                    }
                    if(active==false){
                        access_lable.setText("Account needs to be activated first");
                    }
                    return;
                }

            }
            access_lable.setText("Account does not exist");
            rs.close();
        } catch(SQLException e) {
            System.out.println("SQL exception occured" + e);
        }
    }


    @FXML TextField Admin_Add;
    @FXML PasswordField Password_1;
    @FXML PasswordField Password_2;
    @FXML
    protected void onNewAdminButtonClick() throws IOException, SQLException {
        if (Password_1.getText().equals(Password_2.getText())){
            Statement stmt = c.createStatement();

            stmt.executeUpdate("INSERT INTO Admin_signin VALUES ("+Admin_Add.getText()+",'"+Password_1.getText()+"')");
        }
    }





}





