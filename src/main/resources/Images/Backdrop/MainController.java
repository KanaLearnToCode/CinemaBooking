package cinemaboking.doanx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TableColumn<OrderHistory, Date> dateTimeColumn;

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

    private ObservableList<OrderHistory> orderHistoryList;

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

        // Show Order History by default
        showOrderHistory();
    }

    // Unified filtering method
    private <T> void filterHistory(TableView<T> table, ObservableList<T> list, String searchText, String field) {
        if (searchText == null || searchText.isEmpty()) {
            table.setItems(list);
        } else {
            ObservableList<T> filteredList = FXCollections.observableArrayList();
            for (T item : list) {
                String value = "";
                if (item instanceof TicketHistory) {
                    if ("idTicket".equals(field)) value = String.valueOf(((TicketHistory) item).getIdTicket());
                } else if (item instanceof OrderHistory) {
                    if ("idOrderDetail".equals(field)) value = String.valueOf(((OrderHistory) item).getIdOrderDetail());
                }
                if (value.contains(searchText)) {
                    filteredList.add(item);
                }
            }
            table.setItems(filteredList);
        }
    }

    private void filterTicketHistory(String searchText) {
        filterHistory(ticketHistoryTable, ticketHistoryList, searchText, "idTicket");
    }

    private void filterOrderHistory(String searchText) {
        filterHistory(orderHistoryTable, orderHistoryList, searchText, "idOrderDetail");
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete Ticket");
            alert.setContentText("Are you sure you want to delete this ticket?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String query = "DELETE FROM Ticket WHERE IDTicket = ?";
                try (Connection conn = DBConnect.getConnection();
                     PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, selectedTicket.getIdTicket());
                    ps.executeUpdate();
                    ticketHistoryList.remove(selectedTicket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert("No Selection", "No Ticket Selected", "Please select a ticket in the table.");
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
                Date dateTime = rs.getDate("dateTime");
                int idProduct = rs.getInt("IDProduct");
                int quantity = rs.getInt("Quantity");
                float price = rs.getFloat("Price");
                float total = rs.getFloat("Total");

                OrderHistory orderHistory = new OrderHistory(idOrderDetail, clientName, dateTime, idProduct, quantity, price, total);
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete Order History");
            alert.setContentText("Are you sure you want to delete this order history record?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String query = "DELETE FROM OrdersDetail WHERE IDOrdersDetail = ?";
                try (Connection conn = DBConnect.getConnection();
                     PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, selectedOrder.getIdOrderDetail());
                    ps.executeUpdate();
                    orderHistoryList.remove(selectedOrder);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert("No Selection", "No Order Selected", "Please select an order in the table.");
        }
    }

    @FXML
    private void showTicketHistory() {
        ticketHistoryView.setVisible(true);
        orderHistoryView.setVisible(false);
        contentArea.getChildren().setAll(ticketHistoryView);
    }

    @FXML
    private void showOrderHistory() {
        orderHistoryView.setVisible(true);
        ticketHistoryView.setVisible(false);
        contentArea.getChildren().setAll(orderHistoryView);
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
