package org.group3.cinemabooking_2.History;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {

    // Ticket History Fields   
    @FXML
    private VBox contentArea;
     
    @FXML
    private VBox ticketHistoryView;

    @FXML
    private TableView<TicketHistory> ticketHistoryTable;

    @FXML
    private TableColumn<TicketHistory, Integer> idTicketColumn;

    @FXML
    private TableColumn<TicketHistory, String> clientNameTicketColumn;

    @FXML
    private TableColumn<TicketHistory, String> dateTimeBookColumn;

    @FXML
    private TableColumn<TicketHistory, String> idSeatColumn;

    @FXML
    private TableColumn<TicketHistory, Integer> idTheaterColumn;

    @FXML
    private TableColumn<TicketHistory, Integer> idSeatShowTimeColumn;

    @FXML
    private TableColumn<TicketHistory, String> movieNameColumn;

    @FXML
    private TableColumn<TicketHistory, Float> totalTicketColumn;

    @FXML
    private TextField ticketSearchBox;

    @FXML
    private ComboBox<String> ticketSearchComboBox;

    private ObservableList<TicketHistory> ticketHistoryList;

    // Order History Fields
    @FXML
    private VBox orderHistoryView;

    @FXML
    private TableView<OrderHistory> orderHistoryTable;

    @FXML
    private TableColumn<OrderHistory, Integer> idOrderDetailColumn;

    @FXML
    private TableColumn<OrderHistory, String> clientNameOrderColumn;

    @FXML
    private TableColumn<OrderHistory, String> dateTimeColumn;

    @FXML
    private TableColumn<OrderHistory, Integer> idProductColumn;

    @FXML
    private TableColumn<OrderHistory, Integer> quantityColumn;

    @FXML
    private TableColumn<OrderHistory, Float> priceColumn;

    @FXML
    private TableColumn<OrderHistory, Float> totalOrderColumn;

    @FXML
    private TextField orderSearchBox;

    @FXML
    private ComboBox<String> orderSearchComboBox;

    private ObservableList<OrderHistory> orderHistoryList;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FXML
    public void initialize() {
        // Initialize Ticket History Table
        idTicketColumn.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        clientNameTicketColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        dateTimeBookColumn.setCellValueFactory(new PropertyValueFactory<>("dateTimeBook"));
        idSeatColumn.setCellValueFactory(new PropertyValueFactory<>("idSeat"));
        idTheaterColumn.setCellValueFactory(new PropertyValueFactory<>("idTheater"));
        idSeatShowTimeColumn.setCellValueFactory(new PropertyValueFactory<>("idSeatShowTime"));
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        totalTicketColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Initialize Order History Table
        idOrderDetailColumn.setCellValueFactory(new PropertyValueFactory<>("idOrderDetail"));
        clientNameOrderColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalOrderColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Load data for both tables
        loadTicketHistory();
        loadOrderHistory();

        // Set up search functionality
        ticketSearchBox.textProperty().addListener((observable, oldValue, newValue) -> filterTicketHistory(newValue));
        orderSearchBox.textProperty().addListener((observable, oldValue, newValue) -> filterOrderHistory(newValue));

        // Set up search field selection options
        ticketSearchComboBox.setItems(FXCollections.observableArrayList("ID Ticket", "Client Name", "Date Time Book", "ID Seat", "ID Theater", "ID Seat Showtime", "Movie Name", "Total"));
        ticketSearchComboBox.setValue("ID Ticket");  // Set a default value

        orderSearchComboBox.setItems(FXCollections.observableArrayList("Order Detail ID", "Client Name", "Date Time", "Product ID", "Quantity", "Total"));
        orderSearchComboBox.setValue("Order Detail ID");  // Set a default value

        // Show Order History by default
        showOrderHistory();
    }

    private <T> void filterHistory(TableView<T> table, ObservableList<T> list, String searchText, String field) {
        if (searchText == null || searchText.isEmpty()) {
            table.setItems(list);
        } else {
            ObservableList<T> filteredList = FXCollections.observableArrayList();
            for (T item : list) {
                String value = "";
                if (item instanceof TicketHistory) {
                    TicketHistory ticket = (TicketHistory) item;
                    switch (field) {
                        case "ID Ticket": value = String.valueOf(ticket.getIdTicket()); break;
                        case "Client Name": value = ticket.getClientName(); break;
                        case "Date Time Book": value = ticket.getDateTimeBook(); break;
                        case "ID Seat": value = ticket.getIdSeat(); break;
                        case "ID Theater": value = String.valueOf(ticket.getIdTheater()); break;
                        case "ID Seat Showtime": value = String.valueOf(ticket.getIdSeatShowTime()); break;
                        case "Movie Name": value = ticket.getMovieName(); break;
                        case "Total": value = String.valueOf(ticket.getTotal()); break;
                    }
                } else if (item instanceof OrderHistory) {
                    OrderHistory order = (OrderHistory) item;
                    switch (field) {
                        case "Order Detail ID": value = String.valueOf(order.getIdOrderDetail()); break;
                        case "Client Name": value = order.getClientName(); break;
                        case "Date Time": value = String.valueOf(order.getDateTime()); break;
                        case "Product ID": value = String.valueOf(order.getIdProduct()); break;
                        case "Quantity": value = String.valueOf(order.getQuantity()); break;               
                        case "Total": value = String.valueOf(order.getTotal()); break;
                    }
                }
                if (value.contains(searchText)) {
                    filteredList.add(item);
                }
            }
            table.setItems(filteredList);
        }
    }

    private void filterTicketHistory(String searchText) {
        String field = ticketSearchComboBox.getValue();
        filterHistory(ticketHistoryTable, ticketHistoryList, searchText, field);
    }

    private void filterOrderHistory(String searchText) {
        String field = orderSearchComboBox.getValue();
        filterHistory(orderHistoryTable, orderHistoryList, searchText, field);
    }

    // Ticket History Methods
    private void loadTicketHistory() {
        ticketHistoryList = FXCollections.observableArrayList(getTicketHistory());
        ticketHistoryTable.setItems(ticketHistoryList);
    }

    private List<TicketHistory> getTicketHistory() {
        List<TicketHistory> ticketHistories = new ArrayList<>();
        String query = "SELECT t.IDTicket, c.Name AS ClientName, t.DateTimeBook, t.IDSeat, st.IDTheater, t.IDSeatShowTime, m.MovieName, t.Total " +
                       "FROM Ticket t " +
                       "JOIN Client c ON t.EmailClient = c.EmailClient " +
                       "JOIN ShowTimes st ON t.IDSeatShowTime = st.IDShowTimes " +
                       "JOIN Movie m ON st.IDMovie = m.IDMovie";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idTicket = rs.getInt("IDTicket");
                String clientName = rs.getString("ClientName");
                String dateTimeBook = rs.getString("DateTimeBook");
                String idSeat = rs.getString("IDSeat");
                int idTheater = rs.getInt("IDTheater");
                int idSeatShowTime = rs.getInt("IDSeatShowTime");
                String movieName = rs.getString("MovieName");
                float total = rs.getFloat("Total");

                TicketHistory ticketHistory = new TicketHistory(idTicket, clientName, dateTimeBook, idSeat, idTheater, idSeatShowTime, movieName, total);
                ticketHistories.add(ticketHistory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ticketHistories;
    }

    @FXML
    private void handleTicketDelete() {
        TicketHistory selectedTicket = ticketHistoryTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            String cleanedDateTimeBook = selectedTicket.getDateTimeBook().split("\\.")[0]; // Remove the fractional part
            try {
                LocalDateTime ticketTime = LocalDateTime.parse(cleanedDateTimeBook, formatter);
                if (Duration.between(ticketTime, currentTime).toHours() >= 1) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Delete Error");
                    alert.setHeaderText("Cannot delete ticket history");
                    alert.setContentText("Cannot delete ticket history older than one hour.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText("Delete Ticket");
                    alert.setContentText("Are you sure you want to delete this ticket?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        deleteTicket(selectedTicket);
                        loadTicketHistory(); // Refresh the table after deletion
                    }
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Parsing Error");
                alert.setHeaderText("Date Parsing Error");
                alert.setContentText("There was an error parsing the date. Please check the format.");
                alert.showAndWait();
            }
        }
    }

    private void deleteTicket(TicketHistory ticket) {
        String query = "DELETE FROM Ticket WHERE IDTicket = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, ticket.getIdTicket());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Order History Methods
    private void loadOrderHistory() {
        orderHistoryList = FXCollections.observableArrayList(getOrderHistory());
        orderHistoryTable.setItems(orderHistoryList);
    }

    private List<OrderHistory> getOrderHistory() {
        List<OrderHistory> orderHistories = new ArrayList<>();
        String query = "SELECT od.IDOrdersDetail, c.Name AS clientName, o.DateTime AS dateTime, " +
                       "od.IDProduct, od.Quantity, od.Price, od.Total " +
                       "FROM OrdersDetail od " +
                       "JOIN Orders o ON od.IDOrder = o.IDOrders " +
                       "JOIN Client c ON o.EmailClient = c.EmailClient";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idOrderDetail = rs.getInt("IDOrdersDetail");
                String clientName = rs.getString("clientName");
                Timestamp dateTime = rs.getTimestamp("dateTime");  // Changed to Timestamp
                String dateTimeStr = dateTime.toLocalDateTime().format(formatter);  // Format to String
                int idProduct = rs.getInt("IDProduct");
                int quantity = rs.getInt("Quantity");
                float price = rs.getFloat("Price");
                float total = rs.getFloat("Total");

                OrderHistory orderHistory = new OrderHistory(idOrderDetail, clientName, dateTimeStr, idProduct, quantity, price, total);
                orderHistories.add(orderHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderHistories;
    }

    @FXML
    private void handleOrderDelete() {
        OrderHistory selectedOrder = orderHistoryTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime orderTime = LocalDateTime.parse(selectedOrder.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (Duration.between(orderTime, currentTime).toHours() >= 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Error");
                alert.setHeaderText("Cannot delete order history");
                alert.setContentText("Cannot delete order history older than one hour.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("Delete Order");
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    deleteOrder(selectedOrder);
                    loadOrderHistory(); // Refresh the table after deletion
                }
            }
        }
    }

    private void deleteOrder(OrderHistory order) {
        String query = "DELETE FROM OrdersDetail WHERE IDOrdersDetail = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, order.getIdOrderDetail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Switching between views
    @FXML
    private void showTicketHistory() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(ticketHistoryView);
    }

    @FXML
    private void showOrderHistory() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(orderHistoryView);
    }
}
