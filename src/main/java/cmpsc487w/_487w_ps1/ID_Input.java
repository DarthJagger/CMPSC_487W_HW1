package cmpsc487w._487w_ps1;

import java.sql.*;

public class ID_Input {

    public boolean ID_Format(StringBuffer ID){
        int length = ID.length();

        if (length>=11) {
            ID.delete(11, length);

            return ID_Validate(ID);
        }
        else{
            System.out.println("\nNot a valid input, please Try again later\n\n");
            return false;
        }
    }
    public boolean ID_Validate(StringBuffer ID){

            try{
                if(ID.charAt(0)=='%'&&ID.charAt(1)=='A'&&ID.charAt(2)=='9'){
                    ID.delete(0, 2);
                    return true;

                }
                else if(ID.length() == 9 && ID.charAt(0)=='9'){
                    return true;
                }
                else{
                        System.out.println("\nNot a valid PSU ID, please Try again later\n\n");
                    return false;
                    }
            }
            catch (Exception e){
                System.out.println("\nNot a valid PSU ID, please Try again later\n\n");

            }
        return false;
        }


    public void ID_add(int ID, StringBuffer time, Connection c) throws SQLException {
        Statement stmt = c.createStatement();

        stmt.executeUpdate("INSERT INTO Swipe VALUES ("+ID+",'"+time.toString()+"')");
    }

    public void ID_access (int ID, StringBuffer time, Connection c){
       try{
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Id_access");


        while (rs.next()) {
            int id = rs.getInt("Id_number");
            if (id == ID){

                boolean active = rs.getBoolean("Active");
                boolean suspend = rs.getBoolean("suspended");
                if(active==true&&suspend==false){
                    System.out.println("\nYou swipped in\n\n");
                }
                else{
                    System.out.println("\nYour access has been suspended\n\n");
                }
                rs.close();
                return;
            }

        }
           System.out.println("\nyou do not have access\n\n");
           rs.close();
    } catch(SQLException e) {
        System.out.println("SQL exception occured" + e);
    }
    }


}

