package org.group3.cinemabooking_2.Food;

import entity.entity.Account;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import org.group3.cinemabooking_2.LoginController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoodController implements Initializable {

    @FXML
    private TableView<Food> tvFood;
    @FXML
    private TableColumn<Food, ImageView> colImg;
    @FXML
    private TableColumn<Food, String> colFood;
    @FXML
    private TableColumn<Food, Integer> colQuantity;
    @FXML
    private TableColumn<Food, Float> colPrice;

    @FXML
    private TextField orderAmount;

    @FXML
    private Label orderTotal;

    @FXML
    private TableColumn<Food, String> orderFood;

    @FXML
    private TableColumn<Food, Float> orderPrice;

    @FXML
    private TableColumn<Food, Integer> orderQuantity;

    @FXML
    private TableView<Food> tvOrder;

    @FXML
    private Label dateTimeLabel;

    @FXML
    private TextField emailClient;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField clientName;

    private Connection connection;
    private ObservableList<Food> foodList;
    private ObservableList<Food> orderList;
    private Account logginAccount;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String dburl = "jdbc:sqlserver://localhost:1433;databaseName=CinemaBooking;encrypt=true;trustServerCertificate=true;";
        String username = "sa";
        String password = "123";
        logginAccount = LoginController.getLoggedInUser();
        try {
            connection = DriverManager.getConnection(dburl, username, password);
//            System.out.println("Connection successful!");
        } catch (SQLException e) {
            showAlert("Error", "Failed to connect to database.");
        }

        colImg.setCellValueFactory(new PropertyValueFactory<>("imageProduct"));
        colFood.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityLeft"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        colQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colQuantity.setOnEditCommit(event -> {
            Food food = event.getRowValue();
            food.setQuantityLeft(event.getNewValue());
        });

        foodList = FXCollections.observableArrayList();
        tvFood.setItems(foodList);

        orderList = FXCollections.observableArrayList();
        orderFood.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityLeft"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvOrder.setItems(orderList);

        tvFood.setEditable(true);

        loadFoods();
        setDateTime();
    }

    private void loadFoods() {
        foodList.clear();
        String query = "SELECT * FROM Product";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Food food = new Food(rs.getInt("IDProduct"), rs.getString("ImageProduct"), rs.getString("NameProduct"), rs.getInt("QuantityLeft"), rs.getFloat("Price"));
                foodList.add(food);
            }
        } catch (SQLException e) {
            Logger.getLogger(FoodController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void order() {
        orderList = FXCollections.observableArrayList();
        orderFood.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityLeft"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvOrder.setItems(orderList);
    }

    @FXML
    public void add(ActionEvent e) {
        Food selectedFood = tvFood.getSelectionModel().getSelectedItem();
        if (selectedFood == null) {
            showAlert("No Selection", "Please select a product item to add.");
            return;
        }

        int orderedQuantity;
        try {
            orderedQuantity = Integer.parseInt(orderAmount.getText());
            if (orderedQuantity <= 0) {
                showAlert("Invalid Quantity", "Please enter a valid quantity.");
                return;
            }
        } catch (NumberFormatException ex) {
            showAlert("Invalid Input", "Please enter a valid numerical value for quantity.");
            return;
        }

        int currentQuantity = selectedFood.getQuantityLeft();
        if (orderedQuantity > currentQuantity) {
            showAlert("Insufficient Quantity", "Not enough quantity available.");
            return;
        }

        int newQuantity = currentQuantity - orderedQuantity;
        selectedFood.setQuantityLeft(newQuantity);

        boolean foodExists = false;
        for (Food food : orderList) {
            if (food.getName().equals(selectedFood.getName())) {
                food.setQuantityLeft(food.getQuantityLeft() + orderedQuantity);
                foodExists = true;
                break;
            }
        }

        if (!foodExists) {
            Food newFood = new Food();
            newFood.setIdProduct(selectedFood.getIdProduct());
            newFood.setImageProduct(selectedFood.getImageProduct());
            newFood.setName(selectedFood.getName());
            newFood.setQuantityLeft(orderedQuantity);
            newFood.setPrice(selectedFood.getPrice());
            orderList.add(newFood);
        }

        tvFood.refresh();
        tvOrder.refresh();
        updateTotalPrice();
    }

    private float updateTotalPrice() {
        float total = 0;
        for (Food food : orderList) {
            total += food.getPrice() * food.getQuantityLeft();
        }
        orderTotal.setText(String.format("$" + "%.2f", total));
        return total;
    }

    @FXML
    public void delete(ActionEvent e) {
        Food selectedOrder = tvOrder.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            for (Food food : foodList) {
                if (food.getName().equals(selectedOrder.getName())) {
                    food.setQuantityLeft(food.getQuantityLeft() + selectedOrder.getQuantityLeft());
                    break;
                }
            }
            orderList.remove(selectedOrder);
            tvFood.refresh();
            tvOrder.refresh();
            updateTotalPrice();
        } else {
            showAlert("No Selection", "Please select an order item to delete.");
        }
    }

    @FXML
    private void pay(ActionEvent e) {
        try {
            String email = emailClient.getText().trim();
            String phone = phoneNumber.getText().trim();
            String name = clientName.getText().trim();

            if (email.isEmpty() || phone.isEmpty() || name.isEmpty()) {
                showAlert("Error", "Email, Phone Number, and Name cannot be empty.");
                return;
            }

            boolean clientEmailExists = false;
            String checkClientIdQuery = "SELECT COUNT(*) FROM Client WHERE EmailClient = ?";
            PreparedStatement checkClientIdStmt = connection.prepareStatement(checkClientIdQuery);
            checkClientIdStmt.setString(1, email);
            ResultSet rs = checkClientIdStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                clientEmailExists = true;
            }

            if (!clientEmailExists) {
                String insertClientQuery = "INSERT INTO Client (EmailClient, PhoneNumber, Name) VALUES (?, ?, ?)";
                PreparedStatement insertClientStmt = connection.prepareStatement(insertClientQuery);
                insertClientStmt.setString(1, email);
                insertClientStmt.setString(2, phone);
                insertClientStmt.setString(3, name);
                insertClientStmt.executeUpdate();
            }

            float totalPrice = updateTotalPrice();

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(dateTimeFormatter);

            String insertOrderQuery = "INSERT INTO Orders (EmailClient, DateTime, IDAdminOrder, Total) VALUES (?, ?, ?, ?)";
            PreparedStatement insertOrderStmt = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
            insertOrderStmt.setString(1, email);
            insertOrderStmt.setString(2, formattedDateTime);
            insertOrderStmt.setInt(3, logginAccount.getId());
            insertOrderStmt.setFloat(4, totalPrice);
            insertOrderStmt.executeUpdate();

            String findID = "select IDOrders from Orders order by IDOrders asc";
            PreparedStatement findIDOrders = connection.prepareStatement(findID);
            ResultSet resultSets = findIDOrders.executeQuery();
            int idOrders = 0;
            while (resultSets.next()) {
                idOrders = resultSets.getInt("IDOrders");
            }
//            System.out.println(idOrders);

            String insertOrderDetailsQuery = "INSERT INTO OrdersDetail (IDProduct, IDOrder, Quantity, Price, Total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertOrderDetailsStmt = connection.prepareStatement(insertOrderDetailsQuery);

            for (Food order : orderList) {
//                System.out.println(order.getQuantityLeft());
                insertOrderDetailsStmt.setInt(1, order.getIdProduct());
                insertOrderDetailsStmt.setInt(2, idOrders);
                insertOrderDetailsStmt.setInt(3, order.getQuantityLeft());
                insertOrderDetailsStmt.setFloat(4, order.getPrice());
                insertOrderDetailsStmt.setFloat(5, order.getPrice() * order.getQuantityLeft());
                insertOrderDetailsStmt.executeUpdate();
//                System.out.println(order);
            }

            connection.commit();

            String updateFoodQuery = "UPDATE Product SET QuantityLeft = ? WHERE IDProduct = ?";
            PreparedStatement updateFoodStmt = connection.prepareStatement(updateFoodQuery);

            for (Food newFood : foodList) {
                updateFoodStmt.setInt(1, newFood.getQuantityLeft());
                updateFoodStmt.setInt(2, newFood.getIdProduct());
                updateFoodStmt.executeUpdate();
            }

            connection.commit();

            showAlert("Payment", "Pay successfully!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderSummary.fxml"));
            Parent orderSummaryView = loader.load();

            OrderSummaryController controller = loader.getController();
            controller.setOrderList(orderList);
            controller.setTotalPrice(totalPrice);
            controller.setDateTime(formattedDateTime);

            Scene orderSummaryScene = new Scene(orderSummaryView);
            Stage orderSummaryStage = new Stage();
            orderSummaryStage.setTitle("Bill");
            orderSummaryStage.setScene(orderSummaryScene);
            orderSummaryStage.show();

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to update the database: " + ex.getMessage());

        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load order summary: " + ex.getMessage());
        }

    }

    public void setFood(Food food) {
        orderFood.setText(food.getName());
        orderQuantity.setText(String.valueOf(food.getQuantityLeft()));
        orderPrice.setText(String.valueOf(food.getPrice()));
    }

    public void setDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            dateTimeLabel.setText(now.format(dateTimeFormatter));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
