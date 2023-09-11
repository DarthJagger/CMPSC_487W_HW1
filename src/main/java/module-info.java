module cmpsc487w._487w_ps1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens cmpsc487w._487w_ps1 to javafx.fxml;
    exports cmpsc487w._487w_ps1;
}