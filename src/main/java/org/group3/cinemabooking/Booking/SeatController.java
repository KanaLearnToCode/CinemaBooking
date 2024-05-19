package org.group3.cinemabooking.Booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeatController implements Initializable {
    @FXML
    private Circle seat;

    @FXML
    void onBooking(MouseEvent event) {
        EntityTransaction entityTransaction = null;
        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            entityTransaction.commit();
        } catch (Exception e) {
            Logger.getLogger(SeatController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            assert entityTransaction != null;
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public void setID(String id) {
        seat.setId(id);
    }

    public Circle getCircle(){
        return seat;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
