module org.group3.cinemabooking {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.mail;

    opens entity;
    opens org.group3.cinemabooking to javafx.fxml;
    exports org.group3.cinemabooking;
    exports org.group3.cinemabooking.Account;
    opens org.group3.cinemabooking.Account to javafx.fxml;
}