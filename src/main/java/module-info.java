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
    exports entity;
    exports org.group3.cinemabooking.Admin;
    opens org.group3.cinemabooking.Admin to javafx.fxml;
    exports org.group3.cinemabooking.Client;
    opens org.group3.cinemabooking.Client to javafx.fxml;
    opens org.group3.cinemabooking.Booking to javafx.fxml;
    exports org.group3.cinemabooking.Booking;
    exports org.group3.cinemabooking.Booking.MovieCard;
    opens org.group3.cinemabooking.Booking.MovieCard to javafx.fxml;
    exports org.group3.cinemabooking.EditProfile;
    opens org.group3.cinemabooking.EditProfile to javafx.fxml;

}