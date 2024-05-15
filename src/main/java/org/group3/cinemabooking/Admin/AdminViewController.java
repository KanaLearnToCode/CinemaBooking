package org.group3.cinemabooking.Admin;

import entity.Account;
import jakarta.persistence.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.group3.cinemabooking.App;
import org.group3.cinemabooking.Booking.BookingController;
import org.group3.cinemabooking.LoginController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminViewController implements Initializable {

    @FXML
    private Circle avatar;

    @FXML
    private AnchorPane detailTableEvent;

    @FXML
    private Text userName;

    @FXML
    void onBooking(MouseEvent event) throws Exception {
        String url = "/org/group3/cinemabooking/Booking/Booking.fxml";
        App.setTableEventVBox(url, detailTableEvent, 5, 0);
    }

    @FXML
    void onFood(MouseEvent event) {

    }

    @FXML
    void onEditProfile(MouseEvent event) throws Exception {
        String urlFXML = "/org/group3/cinemabooking/EditProfile/EditProfile.fxml";
        App.setTableEventHBox(urlFXML, detailTableEvent, 0, 0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String urlFXML = "/org/group3/cinemabooking/Booking/Booking.fxml";

        EntityTransaction entityTransaction = null;
        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();
            Account accountLogin = LoginController.getLoggedInUser();
            Account account = entityManager.find(Account.class, accountLogin.getIDAccount());
            InputStream inputStream = new FileInputStream(accountLogin.getAvatar());
            Image image = new Image(inputStream, 50, 50, false, false);
            avatar.setFill(new ImagePattern(image));
            userName.setText(account.getName());
            entityTransaction.commit();

            App.setTableEventVBox(urlFXML, detailTableEvent, 5, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoResultException e) {
            throw new NoResultException();
        } catch (Exception e) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            assert entityTransaction != null;
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
