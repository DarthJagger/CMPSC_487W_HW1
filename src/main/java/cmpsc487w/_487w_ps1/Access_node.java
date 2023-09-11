package cmpsc487w._487w_ps1;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Access_node {
    public SimpleStringProperty Id = new SimpleStringProperty();
    public SimpleBooleanProperty active = new SimpleBooleanProperty();
    public SimpleBooleanProperty suspended = new SimpleBooleanProperty();


    public Access_node(SimpleStringProperty id, SimpleBooleanProperty active, SimpleBooleanProperty suspended) {
        Id = id;
        this.active = active;
        this.suspended = suspended;
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

    public Boolean getActive() {
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active.set(active);
    }

    public Boolean getSuspended() {
        return suspended.get();
    }

    public SimpleBooleanProperty suspendedProperty() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended.set(suspended);
    }
}
