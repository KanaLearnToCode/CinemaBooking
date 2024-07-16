package org.group3.cinemabooking_2.Booking;

import entity.entity.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.group3.cinemabooking_2.Account.verificationemail.EmailValidator;
import org.group3.cinemabooking_2.App;
import org.group3.cinemabooking_2.Connection.JDBCUtil;
import org.group3.cinemabooking_2.EditProfile.EditProfileController;
import org.group3.cinemabooking_2.LoginController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingDetailController implements Initializable {

    private static BookingDetailController instance;
    private LocalDate choosenDay;
    private String choosenTime;
    private String choosenTheater;
    private static String idSeat;
    private static List<String> reservedSeatList;
    private ObservableList<TicketJ> seatList;
    private ArrayList<TicketJ> seatIDArrayList = new ArrayList<>();
    private Movie movie;

    @FXML
    private VBox checkingSeatBooked;

    @FXML
    private Label NameAdminBooked;

    @FXML
    private Text author;

    @FXML
    private ComboBox<LocalDate> dateOfST;

    @FXML
    private Label dateTimeBooked;

    @FXML
    private Text durationMovie;

    @FXML
    private Label emailCustomerBooked;

    @FXML
    private Text movieName;

    @FXML
    private Label movieNameBooked;

    @FXML
    private Label seatIDBooked;

    @FXML
    private VBox bookingSeat;

    @FXML
    private VBox payment;

    @FXML
    private VBox seatManage;

    @FXML
    private Label theaterNameBooked;

    @FXML
    private ComboBox<String> theaterOfST;

    @FXML
    private ComboBox<String> timeOfST;

    @FXML
    private Text typeOfMovie;

    @FXML
    private Text namePaymentErr;

    @FXML
    private Text emailPaymentErr;

    @FXML
    private Text phonePaymentErr;

    @FXML
    private TextField txEmail;

    @FXML
    private TextField txName;

    @FXML
    private TextField txPhoneNumber;

    @FXML
    private TableColumn<TicketJ, Button> deleteBtn;

    @FXML
    private TableColumn<TicketJ, Integer> price;

    @FXML
    private TableColumn<TicketJ, String> seatNumber;

    @FXML
    private TableView<TicketJ> tbPayment;

    @FXML
    private Button btnCheckOut;

    @FXML
    private Button btnPayment;

    @FXML
    private ImageView moviePoster;

    @FXML
    void onBackToBookDetail(MouseEvent event) {
        checkingSeatBooked.setVisible(false);
        bookingSeat.setVisible(true);
    }

    @FXML
    void onBackToBooking(MouseEvent event) {
        payment.setVisible(false);
        checkingSeatBooked.setVisible(false);
        bookingSeat.setVisible(true);
    }

    @FXML
    void onCheckOut(ActionEvent event) {
        checkOutAction();
    }

    @FXML
    void onPayment(ActionEvent event) {
        paymentAction();
    }

    @FXML
    void onbackToBookingTable(MouseEvent event) throws Exception {
        App.setRoot("Admin/AdminView");
    }

    @FXML
    void onOK(ActionEvent event) {
        checkingSeatBooked.setVisible(false);
        bookingSeat.setVisible(true);
    }

    private void createSeats() throws IOException {
        seatManage.getChildren().clear();
        char[] rows = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        int numberOfSeatsPerRow = 10;
        seatManage.setAlignment(Pos.CENTER);
        seatManage.setSpacing(27);
        for (char row : rows) {
            HBox rowContainer = new HBox(40); // HBox for each row with spacing
            rowContainer.setPrefWidth(200);
            rowContainer.setPrefHeight(100);
            rowContainer.setAlignment(Pos.CENTER);
            for (int seatNumber = 1; seatNumber <= numberOfSeatsPerRow; seatNumber++) {
                idSeat = row + String.valueOf(seatNumber);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/group3/cinemabooking_2/Booking/Seat.fxml"));
                AnchorPane seat = fxmlLoader.load();
                rowContainer.getChildren().add(seat); // Add seat to the row
            }
            seatManage.getChildren().add(rowContainer); // Add row to the container
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        bookingSeat.setVisible(true);
        payment.setVisible(false);
        checkingSeatBooked.setVisible(false);
        movie = BookingController.getMovie();
        btnCheckOut.setDisable(true);

        movieName.setText(movie.getMovieName());
        durationMovie.setText(movie.getAmoutOfLimit() + " mins");
        author.setText(movie.getAuthor());
        typeOfMovie.setText(movie.getTypeOfMovie());
        try {
            InputStream inputStreamImage = new FileInputStream(movie.getImagesPoster());
            Image image = new Image(inputStreamImage);
            moviePoster.setImage(image);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        bookingSeat.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!seatIDArrayList.isEmpty()) {
                    checkOutAction();
                }
            }
        });

        payment.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                paymentAction();
            }
        });

        try {
            String sql = "SELECT DISTINCT date FROM ShowTimes WHERE IDMovie = ? AND CONVERT(date, ShowTimes.date) >= ? " +
                    "ORDER BY date ASC";
            List<LocalDate> listDateOfST = getDatesForShowTimes(String.valueOf(movie.getId()), String.valueOf(App.currentDay), sql);
            ObservableList<LocalDate> listDateST = FXCollections.observableArrayList(listDateOfST);
            dateOfST.setItems(listDateST);
            if (!listDateST.isEmpty()) {
                dateOfST.getSelectionModel().select(listDateST.getFirst());
                updateTheaterComboBox(movie, listDateST.getFirst());
                updateTimeComboBox(String.valueOf(movie.getId()), dateOfST.getSelectionModel().getSelectedItem(), theaterOfST.getSelectionModel().getSelectedItem());

                LocalDate dateSelected = dateOfST.getSelectionModel().getSelectedItem();
                String theaterSelected = theaterOfST.getSelectionModel().getSelectedItem();
                String timeSelected = timeOfST.getSelectionModel().getSelectedItem();

                reservedSeatList = getReservedSeat(String.valueOf(movie.getId()), dateSelected, theaterSelected, timeSelected.split(" ")[0]);
                createSeats();


                choosenDay = dateOfST.getSelectionModel().getSelectedItem();
                choosenTheater = theaterOfST.getSelectionModel().getSelectedItem().split(" ")[1];
                choosenTime = timeOfST.getSelectionModel().getSelectedItem().split(" ")[0];
            }

            dateOfST.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    try {
                        choosenDay = newValue;
                        updateTheaterComboBox(movie, newValue);
                        updateTimeComboBox(String.valueOf(movie.getId()), newValue, theaterOfST.getSelectionModel().getSelectedItem());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            theaterOfST.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    updateTimeComboBox(String.valueOf(movie.getId()), dateOfST.getSelectionModel().getSelectedItem(), newValue);
                    choosenTheater = newValue.split(" ")[1];
                }
            });

            timeOfST.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                String[] getTime;
                if (newValue != null) getTime = newValue.split(" ");
                else getTime = oldValue.split(" ");
                choosenTime = getTime[0];
                LocalDate dateSelected = dateOfST.getSelectionModel().getSelectedItem();
                String theaterSelected = theaterOfST.getSelectionModel().getSelectedItem();
                reservedSeatList = getReservedSeat(String.valueOf(movie.getId()), dateSelected, theaterSelected, choosenTime);

                try {
                    createSeats();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            Logger.getLogger(BookingDetailController.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BookingDetailController getInstance() {
        return instance;
    }

    private void checkOutAction() {
        setEmpty();
        payment.setVisible(true);
        checkingSeatBooked.setVisible(false);
        bookingSeat.setVisible(false);
        countPrice();
    }

    private void paymentAction() {
        setEmpty();

        //check validation
        if (!EditProfileController.checkName(txName.getText())) {
            namePaymentErr.setText("Invalid Name");
        }
        //check validation
        if (!EditProfileController.checkPhoneNumber(txPhoneNumber.getText())) {
            phonePaymentErr.setText("Invalid Number");
        }
        //check validation
        if (!EmailValidator.isValid(txEmail.getText())) {
            emailPaymentErr.setText("Invalid Email");
        }

        if (phonePaymentErr.getText().isBlank() && namePaymentErr.getText().isBlank()
                && emailPaymentErr.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("");
            alert.setContentText("Confirm Booking ?");
            alert.setTitle("Notification");

            try {
                alert.getDialogPane().getStylesheets().add(LoginController.class.getResource("CSS/EditProfile/EditProfile.css").toString());
                alert.getDialogPane().getStyleClass().add("dialog");
                InputStream inputStream = new FileInputStream("D:\\T1.2308.A0\\7. Java\\3.JP2\\JavaFX\\CinemaBooking_2\\CinemaBooking_2\\src\\main\\resources\\Images\\success.png");
                Image icon = new Image(inputStream);
                ImageView imageView = new ImageView(icon);
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                alert.setGraphic(imageView);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Platform.runLater(alert::close);
                    Connection connection = null;
                    try {
                        connection = JDBCUtil.getConnection();
                        String querySelect = "SELECT * from Client where EmailClient = ?"; //check email client
                        PreparedStatement pS = connection.prepareStatement(querySelect);
                        pS.setString(1, txEmail.getText());
                        ResultSet resultSet = pS.executeQuery();

                        if (!resultSet.next()) { //insert email client
                            String queryInsertClient = "INSERT INTO Client(EmailClient, PhoneNumber, Name) VALUES (?, ?, ?)";
                            pS = connection.prepareStatement(queryInsertClient);
                            pS.setString(1, txEmail.getText());
                            pS.setString(2, txPhoneNumber.getText());
                            pS.setString(3, txName.getText());
                            pS.executeUpdate();
                        }

                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        for (TicketJ ticket : seatIDArrayList) {
                            String idST = getIDShowTimes(choosenDay, choosenTheater, choosenTime, movie.getId());
                            String queryInsertSeat = "INSERT INTO Seat (IDSeat, IDShowTime) VALUES (?, ?)"; //insert seat
                            pS = connection.prepareStatement(queryInsertSeat);
                            pS.setString(1, ticket.getSeatID());
                            pS.setString(2, idST);
                            pS.execute();

                            String queryInsertTicket = "INSERT INTO Ticket " +
                                    "(DateTimeBook, EmailClient, IDSeat, IDSeatShowTime, IDAccountBook, Total) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)"; // insert ticket
                            pS = connection.prepareStatement(queryInsertTicket);
                            pS.setString(1, LocalDateTime.now().format(dateTimeFormatter));
                            pS.setString(2, txEmail.getText());
                            pS.setString(3, ticket.getSeatID());
                            pS.setString(4, idST);
                            pS.setInt(5, LoginController.getLoggedInUser().getId());
                            pS.setFloat(6, ticket.getTotal());
                            pS.execute();

                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } finally {
                        assert connection != null;
                        JDBCUtil.closeConnection(connection);
                    }
                    App.setRoot("Admin/AdminView");
                }
            } catch (Exception e) {
                System.err.println("Could not load CSS file: " + e.getMessage());
            }
        }
    }

    private void setEmpty() {
        namePaymentErr.setText("");
        emailPaymentErr.setText("");
        phonePaymentErr.setText("");
    }

    private void countPrice() {
        float totalPrice = 0;

        seatNumber.setCellValueFactory(new PropertyValueFactory<>("seatID"));
        seatNumber.setStyle("-fx-background-color: transparent;");
        price.setCellValueFactory(new PropertyValueFactory<>("Total"));
        price.setStyle("-fx-background-color: transparent;");

        deleteBtn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Button item, boolean empty) {
                deleteButton.setStyle("""
                        -fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #4c67ec, #35a7ec);
                        -fx-text-fill: white;
                         -fx-cursor: hand;""");
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    deleteButton.setOnAction(event -> {
                        TicketJ ticket = getTableView().getItems().get(getIndex());
                        seatIDArrayList.remove(ticket);
                        seatList.remove(ticket);
                        ticket.getSeatCircle().setFill(Color.web("#1e90ff"));

                        if (seatIDArrayList.isEmpty()) {
                            btnCheckOut.setDisable(true);
                            payment.setVisible(false);
                            checkingSeatBooked.setVisible(false);
                            bookingSeat.setVisible(true);
                        }

                        countPrice();
                    });
                    setGraphic(deleteButton);
                    setText(null);
                }
            }
        });
        seatList = FXCollections.observableArrayList(seatIDArrayList);
        tbPayment.setItems(seatList);

        for (TicketJ ticket : seatIDArrayList) {
            totalPrice += ticket.getTotal();
        }
        btnPayment.setText("Check Out: " + totalPrice);
    }

    private String getIDShowTimes(LocalDate specificDate, String specificTheater, String timeStart, int idMovie) {
        String id = "";
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            String queryST = "select IDShowTimes from ShowTimes where IDMovie = ?" +
                    "and CONVERT(date, date) = ? and CONVERT(time, startTime) = ?" +
                    "and IDTheater = ?";
            PreparedStatement pS = connection.prepareStatement(queryST);
            pS.setInt(1, idMovie);
            pS.setString(2, String.valueOf(specificDate));
            pS.setString(3, timeStart);
            pS.setString(4, specificTheater);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                id = rS.getString("IDShowTimes");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            assert connection != null;
            JDBCUtil.closeConnection(connection);
        }
        return id;
    }

    private List<LocalDate> getDatesForShowTimes(String movieId, String specificDate, String sql) throws SQLException {
        List<LocalDate> listDateOfST = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setString(1, movieId);
            pS.setString(2, specificDate);

            ResultSet rs = pS.executeQuery();
            while (rs.next()) {
                listDateOfST.add(LocalDate.parse(rs.getString("date")));
            }
        }
        return listDateOfST;
    }

    private List<String> getReservedSeat(String movieId, LocalDate specificDate, String selectedTheater, String timeSelected) {
        List<String> reservedSeatList = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            String getSeat = "SELECT IDSeat FROM Seat " +
                    "JOIN ShowTimes ON ShowTimes.IDShowTimes = Seat.IDShowTime " +
                    "WHERE ShowTimes.IDMovie = ? AND CONVERT(DATE, ShowTimes.date) = ? AND CONVERT(TIME, ShowTimes.startTime) = ? AND ShowTimes.IDTheater = ?";
            PreparedStatement pS = connection.prepareStatement(getSeat);
            pS.setString(1, movieId);
            pS.setString(2, specificDate.toString());
            pS.setString(3, timeSelected.split("\\.")[0]); // Handle fractional seconds
            pS.setString(4, selectedTheater.split(" ")[1]);

            ResultSet rs = pS.executeQuery();
            while (rs.next()) {
                reservedSeatList.add(rs.getString("IDSeat"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservedSeatList;
    }

    private void updateTheaterComboBox(Movie movie, LocalDate selectedDate) throws SQLException {
        List<String> listTheaterOfST = new ArrayList<>();

        try (Connection connection = JDBCUtil.getConnection()) {
            String getTheater = "SELECT DISTINCT IDTheater FROM ShowTimes WHERE IDMovie = ? AND CONVERT(date, ShowTimes.date) = ?";
            PreparedStatement pSGetTheater = connection.prepareStatement(getTheater);
            pSGetTheater.setString(1, String.valueOf(movie.getId()));
            pSGetTheater.setString(2, selectedDate.toString());

            ResultSet rsTheater = pSGetTheater.executeQuery();
            while (rsTheater.next()) {
                listTheaterOfST.add("Theater: " + rsTheater.getString("IDTheater"));
            }

            ObservableList<String> listTheaterST = FXCollections.observableArrayList(listTheaterOfST);
            theaterOfST.setItems(listTheaterST);
            if (!listTheaterST.isEmpty()) {
                theaterOfST.getSelectionModel().select(listTheaterST.getFirst());
            }
        }
    }

    private void updateTimeComboBox(String idMovie, LocalDate selectedDate, String selectedTheater) {
        List<String> listTimeOfSt = new ArrayList<>();
        try (Connection finalConnection = JDBCUtil.getConnection()) {
            String getDate = "";
            if (selectedDate.equals(LocalDate.now())) {
                getDate = "SELECT DISTINCT date, startTime, endTime\n" +
                        "FROM ShowTimes\n" +
                        "WHERE IDMovie = ?\n" +
                        "AND IDTheater = ?\n" +
                        "AND (" +
                        "(date = ?) AND startTime >= CONVERT(time, GETDATE()))\n" +
                        "ORDER BY startTime";
                PreparedStatement pS = finalConnection.prepareStatement(getDate);
                pS.setString(1, idMovie);
                pS.setString(2, selectedTheater.split(" ")[1]);
                pS.setString(3, String.valueOf(selectedDate));
                ResultSet rs = pS.executeQuery();
                while (rs.next()) {
                    String startTime = rs.getString("startTime").split("\\.")[0]; // Remove fractional seconds
                    String endTime = rs.getString("endTime").split("\\.")[0]; // Remove fractional seconds
                    listTimeOfSt.add(startTime + " - " + endTime);
                }

                if (listTimeOfSt.isEmpty()) {
                    String sql = "SELECT DISTINCT date FROM ShowTimes WHERE IDMovie = ? AND CONVERT(date, ShowTimes.date) > ? " +
                            "ORDER BY date ASC";
                    List<LocalDate> listDateOfST = getDatesForShowTimes(String.valueOf(movie.getId()), String.valueOf(App.currentDay), sql);
                    ObservableList<LocalDate> listDateST = FXCollections.observableArrayList(listDateOfST);
                    dateOfST.setItems(listDateST);
                    dateOfST.getSelectionModel().select(listDateST.getFirst());
                    updateTheaterComboBox(movie,dateOfST.getSelectionModel().getSelectedItem());

                    getDate = "SELECT DISTINCT date, startTime, endTime\n" +
                            "FROM ShowTimes\n" +
                            "WHERE IDMovie = ?\n" +
                            "AND IDTheater = ?\n" +
                            "AND (date = ?)\n" +
                            "ORDER BY date, startTime";
                    pS = finalConnection.prepareStatement(getDate);
                    pS.setString(1, idMovie);
                    pS.setString(2, selectedTheater.split(" ")[1]);
                    pS.setString(3, String.valueOf(dateOfST.getSelectionModel().getSelectedItem()));
                    rs = pS.executeQuery();
                    while (rs.next()) {
                        String startTime = rs.getString("startTime").split("\\.")[0]; // Remove fractional seconds
                        String endTime = rs.getString("endTime").split("\\.")[0]; // Remove fractional seconds
                        listTimeOfSt.add(startTime + " - " + endTime);
                    }
                    for (String s : listTimeOfSt) {
                        System.out.println(s);
                    }
                }
            } else {
                getDate = "SELECT DISTINCT date, startTime, endTime\n" +
                        "FROM ShowTimes\n" +
                        "WHERE IDMovie = ?\n" +
                        "AND IDTheater = ?\n" +
                        "AND (date = ?)\n" +
                        "ORDER BY date, startTime";
                PreparedStatement pS = finalConnection.prepareStatement(getDate);
                pS.setString(1, idMovie);
                pS.setString(2, selectedTheater.split(" ")[1]);
                pS.setString(3, String.valueOf(selectedDate));
                ResultSet rs = pS.executeQuery();
                while (rs.next()) {
                    String startTime = rs.getString("startTime").split("\\.")[0]; // Remove fractional seconds
                    String endTime = rs.getString("endTime").split("\\.")[0]; // Remove fractional seconds
                    listTimeOfSt.add(startTime + " - " + endTime);
                }
                for (String s : listTimeOfSt) {
                    System.out.println(s);
                }
            }


            ObservableList<String> listTimeST = FXCollections.observableArrayList(listTimeOfSt);
            timeOfST.setItems(listTimeST);

            if (!listTimeST.isEmpty()) {
                timeOfST.getSelectionModel().select(listTimeOfSt.getFirst());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getInforSeat(Circle seat) {
        Connection connection = null;
        String nameAccountBooked = "";
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select Seat.IDSeat, Ticket.dateTimeBook, Ticket.EmailClient, Ticket.IDAccountBook," +
                    "ShowTimes.IDTheater from Ticket join Seat on Ticket.IDSeat = Seat.IDSeat join ShowTimes " +
                    "on ShowTimes.IDShowTimes = Seat.IDShowTime " +
                    "where convert(date, ShowTimes.date) = convert(date,?) " +
                    "and convert(time, ShowTimes.StartTime) = convert(time, ?) " +
                    "and ShowTimes.IDTheater = ? and Seat.IDSeat = ?";
            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setString(1, String.valueOf(dateOfST.getSelectionModel().getSelectedItem()));
            pS.setString(2, timeOfST.getSelectionModel().getSelectedItem().split(" ")[0]);
            pS.setString(3, theaterOfST.getSelectionModel().getSelectedItem().split(" ")[1]);
            pS.setString(4, seat.getId());
            ResultSet rS = pS.executeQuery();

            while (rS.next()) {
                seatIDBooked.setText(rS.getString("IDSeat"));
                movieNameBooked.setText(BookingController.getMovie().getMovieName());
                theaterNameBooked.setText(theaterOfST.getSelectionModel().getSelectedItem().split(" ")[1]);
                dateTimeBooked.setText(rS.getString("dateTimeBook").split("\\.")[0]);
                emailCustomerBooked.setText(rS.getString("EmailClient"));
                nameAccountBooked = rS.getString("IDAccountBook");
            }

            String queryNameAccount = "select name from Account where IDAccount = ?";
            pS = connection.prepareStatement(queryNameAccount);
            pS.setString(1, nameAccountBooked);
            rS = pS.executeQuery();
            while (rS.next()) {
                NameAdminBooked.setText(rS.getString("Name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            assert connection != null;
            JDBCUtil.closeConnection(connection);
        }
    }

    private TicketJ findTicket(Circle seat, ArrayList<TicketJ> list) {
        TicketJ ticketJ = new TicketJ();
        for (TicketJ ticket : list) {
            if (ticket.getSeatID().equals(seat.getId())) {
                return ticket;
            }
        }
        return ticketJ;
    }

    private float getPriceShowTimes(LocalDate specificDate, String specificTheater, String timeStart, int idMovie) {
        float price = 0;
        try (Connection connection = JDBCUtil.getConnection()) {
            String queryST = "select Price from ShowTimes where IDMovie = ?" +
                    "and CONVERT(date, date) = ? and CONVERT(time, startTime) = ?" +
                    "and IDTheater = ?";
            PreparedStatement pS = connection.prepareStatement(queryST);
            pS.setInt(1, idMovie);
            pS.setString(2, String.valueOf(specificDate));
            pS.setString(3, timeStart);
            pS.setString(4, specificTheater);
            ResultSet rS = pS.executeQuery();
            while (rS.next()) {
                price = rS.getFloat("Price");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return price;
    }

    public void checkSeat(Circle seat) {
        TicketJ ticket = new TicketJ();
        if (seat.getFill().equals(Color.web("#ed6775"))) {
            bookingSeat.setVisible(false);
            checkingSeatBooked.setVisible(true);
            getInforSeat(seat);

            checkingSeatBooked.setOnKeyPressed(event -> bookingSeat.setOnKeyPressed(null));
        } else if (seat.getFill().equals(Color.web("#1e90ff"))) {
            seat.setFill(Color.web("#FFA500"));
            ticket.setSeatID(seat.getId());
            ticket.setTotal(getPriceShowTimes(choosenDay, choosenTheater, choosenTime, BookingController.getMovie().getId()));
            ticket.setSeatCircle(seat);
            seatIDArrayList.add(ticket);
            btnCheckOut.setDisable(false);
        } else {
            seat.setFill(Color.web("#1e90ff"));
            seatIDArrayList.remove(findTicket(seat, seatIDArrayList));
            if (seatIDArrayList.isEmpty()) {
                btnCheckOut.setDisable(true);
            }
        }
    }

    public static List<String> getReservedSeatList() {
        return reservedSeatList;
    }

    public static String getIdSeat() {
        return idSeat;
    }

}