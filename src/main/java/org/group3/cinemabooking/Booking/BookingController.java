package org.group3.cinemabooking.Booking;

import entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.group3.cinemabooking.Booking.MovieCard.ComingSoonController;
import org.group3.cinemabooking.Booking.MovieCard.ShowTimesController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingController implements Initializable {

    @FXML
    private HBox hBoxComingSoon;

    @FXML
    private HBox hBoxShowTimes;

    private List<Movie> showTimesList;
    private List<Movie> comingSoonList;

    @FXML
    void onSeeAllCSMovie(MouseEvent event) {

    }

    private List<Movie> showTimesList() {
        List<Movie> movieList = new ArrayList<>();
        EntityTransaction entityTransaction = null;
        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            movieList = entityManager.createQuery("SELECT m from Movie m").getResultList();

            entityTransaction.commit();
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
        return movieList;
    }

    private List<Movie> comingSoonList() {
        List<Movie> movieList = new ArrayList<>();
        EntityTransaction entityTransaction = null;
        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            movieList = entityManager.createQuery("SELECT m from Movie m").getResultList();

            entityTransaction.commit();
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
        return movieList;
    }

    private void loadMovieCards() {
        hBoxShowTimes.getChildren().clear();
        hBoxComingSoon.getChildren().clear();
        showTimesList = showTimesList();
        comingSoonList = comingSoonList();
//        try {
//            for (int i = 0; i < 5; i++) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/group3/cinemabooking/Booking/MovieCard/ShowTimes.fxml"));
//                AnchorPane movieCard = fxmlLoader.load();
//                ShowTimesController showTimesController = fxmlLoader.getController();
//                showTimesController.setData(showTimesList.get(i));
//                hBoxShowTimes.getChildren().add(movieCard);
//            }
//            for (int i = 0; i < 5; i++) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/group3/cinemabooking/Booking/MovieCard/ComingSoon.fxml"));
//                AnchorPane movieCard = fxmlLoader.load();
//                ComingSoonController comingSoonController = fxmlLoader.getController();
//                comingSoonController.setData(comingSoonList.get(i));
//                hBoxComingSoon.getChildren().add(movieCard);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMovieCards();
    }
}
