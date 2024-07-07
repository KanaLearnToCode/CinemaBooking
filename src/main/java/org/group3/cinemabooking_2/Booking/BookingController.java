package org.group3.cinemabooking_2.Booking;


import entity.entity.Movie;
import jakarta.persistence.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.util.StringConverter;
import org.group3.cinemabooking_2.App;
import org.group3.cinemabooking_2.Booking.MovieCard.ComingSoonController;
import org.group3.cinemabooking_2.Booking.MovieCard.ShowTimesController;
import org.group3.cinemabooking_2.Connection.JDBCUtil;
import org.group3.cinemabooking_2.LoginController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @FXML
    private ScrollPane spCS;

    @FXML
    private ScrollPane spST;

    @FXML
    private TextField searchButton;

    @FXML
    private ImageView clearBtn;

    @FXML
    private DatePicker dateSelected;

    private static Movie choosenMovie;


    @FXML
    void onClearSortCondition(MouseEvent event) {
        dateSelected.setValue(null);
        String condition = searchButton.getText();
        if (condition.isBlank()) {
            loadMovieCards(showTimesList(), comingSoonList());
        } else {
            loadMovieCards(updateSTList(condition), updateCSList(condition));
        }
    }

    @FXML
    void onSearchMovieBooking(KeyEvent event) {
        String condition = searchButton.getText();
        if (condition.isBlank()) {
            clearBtn.setOpacity(0);
        } else {
            clearBtn.setOpacity(0.5);
        }
        loadMovieCards(updateSTList(condition), updateCSList(condition));
    }

    @FXML
    void onClearSearchMovie(MouseEvent event) {
        searchButton.setText("");
        clearBtn.setOpacity(0);
        loadMovieCards(showTimesList(), comingSoonList());
    }

    private List<Integer> updateSTList(String condition) {
        List<Integer> movieList = new ArrayList<>();
        if (condition.isBlank()) {
            movieList = showTimesList();
        } else {
            Connection connection = null;
            try {
                connection = JDBCUtil.getConnection();
                String sql = "select distinct Movie.IDMovie from Movie join ShowTimes on ShowTimes.IDMovie = Movie.IDMovie where " +
                        "CONVERT(date, ShowTimes.date) = ? " +
                        "AND CONVERT(time, ShowTimes.StartTime) > CONVERT(time, ?)" +
                        "AND Movie.MovieName LIKE ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(LocalDate.now()));
                ps.setString(2, String.valueOf(LocalTime.now()));
                ps.setString(3, "%" + condition + "%");

                ResultSet rs = ps.executeQuery();
                int idMovie;
                while (rs.next()) {
                    idMovie = rs.getInt("IDMovie");
                    movieList.add(idMovie);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                assert connection != null;
                JDBCUtil.closeConnection(connection);
            }
        }
        return movieList;
    }

    private List<Integer> updateCSList(String condition) {
        List<Integer> movieList = new ArrayList<>();

        if (condition.isBlank()) {
            movieList = comingSoonList();
        } else {
            Connection connection = null;
            try {
                connection = JDBCUtil.getConnection();
                String sql = "select distinct Movie.IDMovie from Movie join ShowTimes on ShowTimes.IDMovie = Movie.IDMovie where " +
                        "CONVERT(date, ShowTimes.date) > CONVERT(DATE,?) AND Movie.MovieName LIKE ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(LocalDate.now()));
                ps.setString(2, "%" + condition + "%");

                ResultSet rs = ps.executeQuery();
                int idMovie;
                while (rs.next()) {
                    idMovie = rs.getInt("IDMovie");
                    movieList.add(idMovie);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                assert connection != null;
                JDBCUtil.closeConnection(connection);
            }
        }
        return movieList;
    }

    private List<Integer> updateCSList(String condition, LocalDate specificDate) {
        List<Integer> movieList = new ArrayList<>();

        if (condition.isBlank()) {
            movieList = comingSoonList();
        } else {
            Connection connection = null;
            try {
                connection = JDBCUtil.getConnection();
                String sql = "select distinct Movie.IDMovie from Movie join ShowTimes on ShowTimes.IDMovie = Movie.IDMovie where " +
                        "CONVERT(date, ShowTimes.date) = ? AND Movie.MovieName LIKE ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(specificDate));
                ps.setString(2, "%" + condition + "%");

                ResultSet rs = ps.executeQuery();
                int idMovie;
                while (rs.next()) {
                    idMovie = rs.getInt("IDMovie");
                    movieList.add(idMovie);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                assert connection != null;
                JDBCUtil.closeConnection(connection);
            }
        }
        return movieList;
    }

    private List<Integer> showTimesList() {
        List<Integer> movieList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();

            String sql = "SELECT distinct IDMovie from ShowTimes WHERE CONVERT(date, ShowTimes.date) = ?" +
                    "AND CONVERT(time, StartTime) > CONVERT(time, ?)";

            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setString(1, String.valueOf(App.currentDay));
            pS.setString(2, String.valueOf(App.currentTime));
            ResultSet rs = pS.executeQuery();

            int idMovie;
            while (rs.next()) {
                idMovie = rs.getInt("IDMovie");
                movieList.add(idMovie);
            }

        } catch (NoResultException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            assert connection != null;
            JDBCUtil.closeConnection(connection);
        }
        return movieList;
    }

    private List<Integer> comingSoonList() {
        List<Integer> movieList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT DISTINCT IDMovie from ShowTimes WHERE CONVERT(date, ShowTimes.date) > ?";

            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setString(1, String.valueOf(App.currentDay));
            ResultSet rs = pS.executeQuery();
            int idMovie;
            while (rs.next()) {
                idMovie = rs.getInt("IDMovie");
                movieList.add(idMovie);
            }
        } catch (NoResultException e) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            assert connection != null;
            JDBCUtil.closeConnection(connection);
        }
        return movieList;
    }

    private List<Integer> setDefulatComingSoonList() {
        return new ArrayList<>();
    }

    private List<Integer> comingSoonList(LocalDate specificDate) {
        List<Integer> movieList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT DISTINCT IDMovie from ShowTimes WHERE CONVERT(date, ShowTimes.date) > ?";

            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setString(1, String.valueOf(specificDate));
            ResultSet rs = pS.executeQuery();
            int idMovie;
            while (rs.next()) {
                idMovie = rs.getInt("IDMovie");
                movieList.add(idMovie);
            }
        } catch (NoResultException e) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            assert connection != null;
            JDBCUtil.closeConnection(connection);
        }
        return movieList;
    }

    private void loadMovieCards(List<Integer> showTimesList, List<Integer> comingSoonList) {
        hBoxShowTimes.getChildren().clear();
        hBoxComingSoon.getChildren().clear();

        try {
            if (showTimesList.size() < 6) {
                spST.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                spST.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            } else {
                spST.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                spST.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            }

            for (int s : showTimesList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/group3/cinemabooking_2/Booking/MovieCard/ShowTimes.fxml"));
                AnchorPane movieCard = fxmlLoader.load();
                ShowTimesController showTimesController = fxmlLoader.getController();
                showTimesController.setData(s);
                hBoxShowTimes.getChildren().add(movieCard);
            }

            if (comingSoonList.size() < 6) {
                spCS.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                spCS.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            } else {
                spCS.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                spCS.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            }

            for (int s : comingSoonList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/group3/cinemabooking_2/Booking/MovieCard/ComingSoon.fxml"));
                AnchorPane movieCard = fxmlLoader.load();
                ComingSoonController comingSoonController = fxmlLoader.getController();
                comingSoonController.setData(s);
                hBoxComingSoon.getChildren().add(movieCard);
            }
        } catch (IOException e) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearBtn.setStyle("-fx-cursor: hand");
        clearBtn.setOpacity(0);
        loadMovieCards(showTimesList(), comingSoonList());

        dateSelected.setConverter(new StringConverter<>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    DateTimeFormatter desiredFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
                    return LocalDate.parse(string, desiredFormatter);
                } else {
                    return null;
                }
            }
        });

        dateSelected.valueProperty().

                addListener((observable, oldValue, newValue) ->

                {
                    String condition = searchButton.getText();
                    if (newValue != null) {
                        if (newValue.isAfter(LocalDate.now()))
                            if (condition.isBlank()) {
                                loadMovieCards(showTimesList(), comingSoonList(newValue));
                            } else {
                                loadMovieCards(updateSTList(condition), updateCSList(condition, newValue));
                            }
                        else {
                            loadMovieCards(showTimesList(), setDefulatComingSoonList());
                        }
                    }
                });
    }

    public static Movie getMovie() {
        return choosenMovie;
    }

    public static void setMovie(Movie movie) {
        choosenMovie = movie;
    }
}
