package org.group3.cinemabooking_2.Food;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderSummaryController implements Initializable {

    @FXML
    private TableView<Food> tvOrderSummary;
    @FXML
    private TableColumn<Food, String> colOrderFood;
    @FXML
    private TableColumn<Food, Integer> colOrderQuantity;
    @FXML
    private TableColumn<Food, Float> colOrderPrice;
    @FXML
    private Label orderTotal;
    @FXML
    private Label dateTimeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colOrderFood.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colOrderQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityLeft"));
    }

    public void setOrderList(ObservableList<Food> orderList) {
        tvOrderSummary.setItems(orderList);
    }

    public void setTotalPrice(float totalPrice) {
        orderTotal.setText(String.format("$" + "%.2f", totalPrice));
    }

    public void setDateTime(String dateTime) {
        dateTimeLabel.setText(dateTime);
    }
}
