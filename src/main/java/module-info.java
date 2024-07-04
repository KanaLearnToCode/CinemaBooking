module org.group.cinemabooking_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires java.mail;
    requires jakarta.persistence;
    requires java.desktop;
    requires com.microsoft.sqlserver.jdbc;

    opens entity.entity;
    exports entity.entity to javafx.fxml;
    opens org.group3.cinemabooking_2 to javafx.fxml;
    exports org.group3.cinemabooking_2;
    exports org.group3.cinemabooking_2.Account;
    opens org.group3.cinemabooking_2.Account to javafx.fxml;
    exports org.group3.cinemabooking_2.Admin;
    opens org.group3.cinemabooking_2.Admin to javafx.fxml;
    opens org.group3.cinemabooking_2.Booking to javafx.fxml;
    exports org.group3.cinemabooking_2.Booking;
    exports org.group3.cinemabooking_2.Booking.MovieCard;
    opens org.group3.cinemabooking_2.Booking.MovieCard to javafx.fxml;
    exports org.group3.cinemabooking_2.EditProfile;
    opens org.group3.cinemabooking_2.EditProfile to javafx.fxml;
    opens org.group3.cinemabooking_2.Dashboard;
    exports org.group3.cinemabooking_2.Dashboard;
}