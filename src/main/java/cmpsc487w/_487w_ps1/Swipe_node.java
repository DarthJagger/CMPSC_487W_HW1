package cmpsc487w._487w_ps1;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Swipe_node {

    public SimpleStringProperty Id = new SimpleStringProperty();
    public SimpleStringProperty date_time = new SimpleStringProperty();

    public Swipe_node(SimpleStringProperty id, SimpleStringProperty date_time) {
        Id = id;
        this.date_time = date_time;
    }

    public String getId() {
        return Id.get();
    }

    public SimpleStringProperty idProperty() {
        return Id;
    }

    public void setId(String id) {
        this.Id.set(id);
    }

    public String getDate_time() {
        return date_time.get();
    }

    public SimpleStringProperty date_timeProperty() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time.set(date_time);
    }
}
