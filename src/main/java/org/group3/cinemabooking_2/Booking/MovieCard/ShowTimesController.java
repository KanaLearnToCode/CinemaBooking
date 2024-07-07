package org.group3.cinemabooking_2.Booking.MovieCard;

import entity.entity.Movie;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.group3.cinemabooking_2.App;
import org.group3.cinemabooking_2.Booking.BookingController;
import org.group3.cinemabooking_2.Connection.JDBCUtil;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ShowTimesController {

    @FXML
    private Label author;

    @FXML
    private Label categoryMovie;

    @FXML
    private Label movieName;

    @FXML
    private ImageView posterMovie;

    private Movie movie;

    @FXML
    void onBookingDetail(MouseEvent event) throws Exception {
        BookingController.setMovie(movie);
        App.setRoot("Booking/BookingDetail");
    }

    public void setData(int idMovie) {
        InputStream inputStreamImage = null;
        Connection connection = null;
        Movie movie = new Movie();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from Movie where IDMovie = ?";
            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setInt(1, idMovie);
            ResultSet rS = pS.executeQuery();

            while (rS.next()) {
                movie.setId(idMovie);
                movie.setAuthor(rS.getString("Author"));
                movie.setMovieName(rS.getString("MovieName"));
                movie.setTypeOfMovie(rS.getString("TypeOfMovie"));
                movie.setImagesPoster(rS.getString("ImagesPoster"));
                movie.setImagesBackdrop(rS.getString("ImagesBackdrop"));
                movie.setAmoutOfLimit(rS.getInt("AmoutOfLimit"));
            }

            inputStreamImage = new FileInputStream(movie.getImagesBackdrop());
        } catch (FileNotFoundException e) {
            System.out.println("not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }

        assert inputStreamImage != null;
        Image image = new Image(inputStreamImage);
        posterMovie.setImage(image);
        movieName.setText(movie.getMovieName());
        categoryMovie.setText(movie.getTypeOfMovie());
        author.setText(movie.getAmoutOfLimit() + " mins");
        this.movie = movie;
    }

}
