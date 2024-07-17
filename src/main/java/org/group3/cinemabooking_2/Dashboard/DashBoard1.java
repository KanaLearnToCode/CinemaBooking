package org.group3.cinemabooking_2.Dashboard;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;



public class DashBoard1 {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> tableComboBox;

    @FXML
    private ComboBox<String> periodComboBox;

    @FXML
    private Label totalIncomeLabel;


    @FXML
    private Label newCustomersLabel;
    @FXML
    private Label totalTicketsSoldLabel;
    @FXML
    private Label totalYearlyRevenueLabel;






    private Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=CinemaBooking;encrypt=true;trustServerCertificate=true", "sa", "123");
    }

    @FXML
    public void ticketdashboardByHours(ActionEvent actionEvent) {
        String tempTable = "WITH AllHours AS ( " +
                "  SELECT 0 AS Hour " +
                "  UNION ALL " +
                "  SELECT Hour + 1 FROM AllHours WHERE Hour < 24 " +
                ") ";


        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = tempTable +
                "SELECT COALESCE(AH.Hour, DATEPART(HOUR, t.dateTimeBook)) AS Period, " +
                "       SUM(COALESCE(t.Total, 0)) AS Revenue " +
                "FROM AllHours AH " +
                "LEFT JOIN Ticket t ON AH.Hour = DATEPART(HOUR, t.dateTimeBook) " +
                "WHERE CAST(t.dateTimeBook AS DATE) = '" + formattedDate + "' " +
                "GROUP BY COALESCE(AH.Hour, DATEPART(HOUR, t.dateTimeBook)) " +
                "ORDER BY COALESCE(AH.Hour, DATEPART(HOUR, t.dateTimeBook))";

        displayChart(query, "Hour", "Period","TicKet Revenue");
        setTableComboBox("Ticket");
    }
    @FXML
    public void ticketdashboardByWeek(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedStartOfWeek = startOfWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "SELECT DATENAME(WEEKDAY, t.dateTimeBook) AS DayOfWeek, " +
                "       SUM(COALESCE(t.Total, 0)) AS Revenue " +
                "FROM Ticket t " +
                "WHERE CAST(t.dateTimeBook AS DATE) BETWEEN '" + formattedStartOfWeek + "' AND '" + formattedCurrentDate + "' " +
                "GROUP BY DATENAME(WEEKDAY, t.dateTimeBook), DATEPART(WEEKDAY, t.dateTimeBook) " +
                "ORDER BY DATEPART(WEEKDAY, t.dateTimeBook)";

        displayChart(query, "Day of Week", "DayOfWeek", "TicKet Revenue");
        setTableComboBox("Ticket");
    }
    @FXML
    public void ticketdashboardByDay(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String query = "SELECT DATEPART(DAY, dateTimeBook) AS Period, SUM(Total) AS Revenue " +
                "FROM Ticket " +
                "WHERE FORMAT(dateTimeBook, 'yyyy-MM') = '" + formattedMonth + "' " +
                "GROUP BY DATEPART(DAY, dateTimeBook) " +
                "ORDER BY Period";

        displayChart(query, "Day", "Period", "TicKet Revenue");
    }
    @FXML
    public void ticketdashboardByMonth(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));

        String query = "SELECT DATEPART(MONTH, dateTimeBook) AS Period, SUM(Total) AS Revenue " +
                "FROM Ticket " +
                "WHERE FORMAT(dateTimeBook, 'yyyy') = '" + formattedYear + "' " +
                "GROUP BY DATEPART(MONTH, dateTimeBook) " +
                "ORDER BY Period";

        displayChart(query, "Month", "Period", "TicKet Revenue");
    }
    @FXML
    public void fooddashboardByHours(ActionEvent actionEvent) {
        String tempTable = "WITH AllHours AS ( " +
                "  SELECT 0 AS Hour " +
                "  UNION ALL " +
                "  SELECT Hour + 1 FROM AllHours WHERE Hour < 24 " +
                ") ";


        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = tempTable +
                "SELECT COALESCE(AH.Hour, DATEPART(HOUR, o.DateTime)) AS Period, " +
                "       SUM(COALESCE(o.Total, 0)) AS Revenue " +
                "FROM AllHours AH " +
                "LEFT JOIN Orders o ON AH.Hour = DATEPART(HOUR, o.DateTime) " +
                "WHERE CAST(o.DateTime AS DATE) = '" + formattedDate + "' " +
                "GROUP BY COALESCE(AH.Hour, DATEPART(HOUR, o.DateTime)) " +
                "ORDER BY COALESCE(AH.Hour, DATEPART(HOUR, o.DateTime))";

        displayChart(query, "Hour", "Period", "Food Revenue");
        setTableComboBox("Food");
    }
    @FXML
    public void fooddashboardByWeek(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedStartOfWeek = startOfWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "SELECT DATENAME(WEEKDAY, o.DateTime) AS DayOfWeek, " +
                "       SUM(COALESCE(o.Total, 0)) AS Revenue " +
                "FROM Orders o " +
                "WHERE CAST(o.DateTime AS DATE) BETWEEN '" + formattedStartOfWeek + "' AND '" + formattedCurrentDate + "' " +
                "GROUP BY DATENAME(WEEKDAY, o.DateTime), DATEPART(WEEKDAY, o.DateTime) " +
                "ORDER BY DATEPART(WEEKDAY, o.DateTime)";

        displayChart(query, "Day of Week", "DayOfWeek", "Food Revenue");
        setTableComboBox("Food");
    }
    @FXML
    public void fooddashboardByDay(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String query = "SELECT DATEPART(DAY, DateTime) AS Period, SUM(Total) AS Revenue " +
                "FROM Orders " +
                "WHERE FORMAT(DateTime, 'yyyy-MM') = '" + formattedMonth + "' " +
                "GROUP BY DATEPART(DAY, DateTime) " +
                "ORDER BY Period";

        displayChart(query, "Day", "Period", "Food Revenue");
    }
    @FXML
    public void fooddashboardByMonth(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));

        String query = "SELECT DATEPART(MONTH, DateTime) AS Period, SUM(Total) AS Revenue " +
                "FROM Orders " +
                "WHERE FORMAT(DateTime, 'yyyy') = '" + formattedYear + "' " +
                "GROUP BY DATEPART(MONTH, DateTime) " +
                "ORDER BY Period";

        displayChart(query, "Month", "Period", "Food Revenue");
    }
    @FXML
    public void totaldashboardByHours(ActionEvent actionEvent) {
        String tempTable = "WITH AllHours AS ( " +
                "  SELECT 0 AS Hour " +
                "  UNION ALL " +
                "  SELECT Hour + 1 FROM AllHours WHERE Hour < 24 " +
                ") ";


        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = tempTable +
                "SELECT AH.Hour AS Period, " +
                "       SUM(COALESCE(t.Total, 0)) + SUM(COALESCE(o.Total, 0)) AS TotalRevenue " +
                "FROM AllHours AH " +
                "LEFT JOIN Ticket t ON AH.Hour = DATEPART(HOUR, t.dateTimeBook) AND CAST(t.dateTimeBook AS DATE) = '" + formattedDate + "' " +
                "LEFT JOIN Orders o ON AH.Hour = DATEPART(HOUR, o.DateTime) AND CAST(o.DateTime AS DATE) = '" + formattedDate + "' " +
                "GROUP BY AH.Hour " +
                "HAVING SUM(COALESCE(t.Total, 0)) + SUM(COALESCE(o.Total, 0)) > 0 " +
                "ORDER BY AH.Hour";


        displayChart(query, "Hour", "Period","Total Revenue");
        setTableComboBox("Total");
    }
    @FXML
    public void totaldashboardByWeek(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));


        String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedStartOfWeek = startOfWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "SELECT DATENAME(WEEKDAY, COALESCE(t.dateTimeBook, o.DateTime)) AS DayOfWeek, " +
                "       SUM(COALESCE(t.Total, 0)) + SUM(COALESCE(o.Total, 0)) AS TotalRevenue " +
                "FROM Ticket t " +
                "FULL OUTER JOIN Orders o ON CAST(t.dateTimeBook AS DATE) = CAST(o.DateTime AS DATE) " +
                "WHERE CAST(COALESCE(t.dateTimeBook, o.DateTime) AS DATE) BETWEEN '" + formattedStartOfWeek + "' AND '" + formattedCurrentDate + "' " +
                "GROUP BY DATENAME(WEEKDAY, COALESCE(t.dateTimeBook, o.DateTime)), DATEPART(WEEKDAY, COALESCE(t.dateTimeBook, o.DateTime)) " +
                "ORDER BY DATEPART(WEEKDAY, COALESCE(t.dateTimeBook, o.DateTime))";

        displayChart(query, "Day of Week", "DayOfWeek", "Total Revenue");
        setTableComboBox("Total");
    }
    @FXML
    public void totaldashboardByDay(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String query = "SELECT DATEPART(DAY, COALESCE(t.dateTimeBook, o.DateTime)) AS Period, " +
                "       SUM(COALESCE(t.Total, 0)) + SUM(COALESCE(o.Total, 0)) AS TotalRevenue " +
                "FROM Ticket t " +
                "FULL OUTER JOIN Orders o ON CAST(t.dateTimeBook AS DATE) = CAST(o.DateTime AS DATE) " +
                "WHERE FORMAT(COALESCE(t.dateTimeBook, o.DateTime), 'yyyy-MM') = '" + formattedMonth + "' " +
                "GROUP BY DATEPART(DAY, COALESCE(t.dateTimeBook, o.DateTime)) " +
                "ORDER BY Period";

        displayChart(query, "Day", "Period", "Total Revenue");
    }
    @FXML
    public void totaldashboardByMonth(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));

        String query = "SELECT DATEPART(MONTH, COALESCE(t.dateTimeBook, o.DateTime)) AS Period, " +
                "       SUM(COALESCE(t.Total, 0)) + SUM(COALESCE(o.Total, 0)) AS TotalRevenue " +
                "FROM Ticket t " +
                "FULL OUTER JOIN Orders o ON CAST(t.dateTimeBook AS DATE) = CAST(o.DateTime AS DATE) " +
                "WHERE FORMAT(COALESCE(t.dateTimeBook, o.DateTime), 'yyyy') = '" + formattedYear + "' " +
                "GROUP BY DATEPART(MONTH, COALESCE(t.dateTimeBook, o.DateTime)) " +
                "ORDER BY Period";

        displayChart(query, "Month", "Period", "Total Revenue");
    }
    public void initialize() {

        ObservableList<String> tableList = FXCollections.observableArrayList(
                "Ticket",
                "Food",
                "Total"
        );
        tableComboBox.setItems(tableList);
        tableComboBox.setValue("Ticket");

        ObservableList<String> periodList = FXCollections.observableArrayList(
                "Hours",
                "Week",
                "Day",
                "Month"
        );
        periodComboBox.setItems(periodList);
        periodComboBox.setValue("Hours");
        periodComboBox.setOnAction(this::handlePeriodComboBoxAction);
        updateTotalIncomeLabel();
        updateNewCustomersLabel();
        updateTotalTicketsSoldLabel();
        updateTotalYearlyRevenueLabel();
        loadData();
        Platform.runLater(() -> {
            String cssPath = "/org/group3/cinemabooking_2/CSS/Dashboard/styles.css";
            URL url = getClass().getResource(cssPath);
            if (url == null) {
                System.err.println("Could not find CSS file: " + cssPath);
            } else {
                anchorPane.getScene().getStylesheets().add(url.toExternalForm());
            }
            anchorPane.getStyleClass().add("anchor-pane");
        });
        anchorPane.prefWidthProperty().bind(anchorPane.widthProperty());
        anchorPane.prefHeightProperty().bind(anchorPane.heightProperty());
    }
    private void setTableComboBox(String tableName) {
        tableComboBox.setValue(tableName);
    }
    @FXML
    private void handleTableComboBoxAction(ActionEvent event) {
        loadData();
    }
    @FXML
    private void handlePeriodComboBoxAction(ActionEvent event) {
        loadData();
    }

    @FXML
    private void loadData() {
        updateTotalIncomeLabel();
        updateNewCustomersLabel();
        updateTotalTicketsSoldLabel();
        updateTotalYearlyRevenueLabel();

        String selectedTable = tableComboBox.getValue();
        String selectedPeriod = periodComboBox.getValue();

        // Tạo FadeTransition cho anchorPane
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), anchorPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), anchorPane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(event -> {
            // Xóa biểu đồ cũ
            anchorPane.getChildren().clear();

            // Tải biểu đồ mới
            switch (selectedTable) {
                case "Ticket":
                    switch (selectedPeriod) {
                        case "Hours": ticketdashboardByHours(null); break;
                        case "Week": ticketdashboardByWeek(null); break;
                        case "Day": ticketdashboardByDay(null); break;
                        case "Month": ticketdashboardByMonth(null); break;
                    }
                    break;
                case "Food":
                    switch (selectedPeriod) {
                        case "Hours": fooddashboardByHours(null); break;
                        case "Week": fooddashboardByWeek(null); break;
                        case "Day": fooddashboardByDay(null); break;
                        case "Month": fooddashboardByMonth(null); break;
                    }
                    break;
                case "Total":
                    switch (selectedPeriod) {
                        case "Hours": totaldashboardByHours(null); break;
                        case "Week": totaldashboardByWeek(null); break;
                        case "Day": totaldashboardByDay(null); break;
                        case "Month": totaldashboardByMonth(null); break;
                    }
                    break;
            }
            fadeIn.play();
        });

        fadeOut.play();
    }

    private double calculateTotalIncomeToday() {
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "SELECT COALESCE(SUM(t.Total), 0) + COALESCE(SUM(o.Total), 0) AS TotalIncome " +
                "FROM Ticket t " +
                "FULL OUTER JOIN Orders o ON CAST(t.dateTimeBook AS DATE) = CAST(o.DateTime AS DATE) " +
                "WHERE CAST(COALESCE(t.dateTimeBook, o.DateTime) AS DATE) = '" + formattedDate + "'";

        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getDouble("TotalIncome");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
    private void updateTotalIncomeLabel() {
        double totalIncome = calculateTotalIncomeToday();
        totalIncomeLabel.setText(String.format("$%.2f", totalIncome));
    }


    private int calculateNewCustomersThisMonth() {
        LocalDate today = LocalDate.now();
        String firstDayOfMonth = today.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String lastDayOfMonth = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "SELECT COUNT(*) AS NewCustomers " +
                "FROM Client " +
                "WHERE EmailClient IN " +
                "(SELECT DISTINCT EmailClient FROM Ticket " +
                "WHERE CAST(dateTimeBook AS DATE) BETWEEN '" + firstDayOfMonth + "' AND '" + lastDayOfMonth + "') " +
                "AND EmailClient NOT IN " +
                "(SELECT DISTINCT EmailClient FROM Ticket " +
                "WHERE CAST(dateTimeBook AS DATE) < '" + firstDayOfMonth + "')";

        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("NewCustomers");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }
    private void updateNewCustomersLabel() {
        int newCustomers = calculateNewCustomersThisMonth();
        newCustomersLabel.setText(String.valueOf(newCustomers));
    }
    private int calculateTotalTicketsSoldThisMonth() {
        LocalDate today = LocalDate.now();
        String firstDayOfMonth = today.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String lastDayOfMonth = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "SELECT COUNT(*) AS TotalTickets " +
                "FROM Ticket " +
                "WHERE CAST(dateTimeBook AS DATE) BETWEEN '" + firstDayOfMonth + "' AND '" + lastDayOfMonth + "'";

        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("TotalTickets");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }
    private void updateTotalTicketsSoldLabel() {
        int totalTickets = calculateTotalTicketsSoldThisMonth();
        totalTicketsSoldLabel.setText(String.valueOf(totalTickets));
    }
    private double calculateTotalYearlyRevenue() {
        int currentYear = LocalDate.now().getYear();
        String startDate = currentYear + "-01-01";
        String endDate = currentYear + "-12-31";

        String query = "SELECT COALESCE(SUM(t.Total), 0) + COALESCE(SUM(o.Total), 0) AS TotalRevenue " +
                "FROM Ticket t " +
                "FULL OUTER JOIN Orders o ON CAST(t.dateTimeBook AS DATE) = CAST(o.DateTime AS DATE) " +
                "WHERE CAST(COALESCE(t.dateTimeBook, o.DateTime) AS DATE) BETWEEN '" + startDate + "' AND '" + endDate + "'";

        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getDouble("TotalRevenue");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
    private void updateTotalYearlyRevenueLabel() {
        double totalRevenue = calculateTotalYearlyRevenue();
        totalYearlyRevenueLabel.setText(String.format("$%.2f", totalRevenue));
    }
    private record ChartDimensions(double barWidth, double categoryGap) {}

    private ChartDimensions calculateChartDimensions(double chartWidth, int dataCount) {
        double maxBarWidth = 50.0;
        double minBarWidth = 10.0;
        double totalGapWidth = chartWidth * 0.2;
        double availableWidth = chartWidth - totalGapWidth;
        double calculatedBarWidth = availableWidth / Math.max(1, dataCount);
        double barWidth = Math.max(minBarWidth, Math.min(maxBarWidth, calculatedBarWidth));

        double categoryGap = (chartWidth - (barWidth * dataCount)) / (dataCount + 1);

        return new ChartDimensions(barWidth, categoryGap);
    }
    private void displayChart(String query, String xAxisLabel, String periodLabel, String yAxisLabel) {
        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();

            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setAnimated(false);
            barChart.setTitle(yAxisLabel);
            barChart.setLegendVisible(false);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            int dataCount = 0;
            while (rs.next()) {
                String timePeriod = rs.getString(periodLabel);
                double revenue = rs.getDouble(2);
                series.getData().add(new XYChart.Data<>(timePeriod, revenue));
                dataCount++;
            }

            if (dataCount == 0) {
                series.getData().add(new XYChart.Data<>("No Data", 0));
            }

            barChart.getData().add(series);

            ChartDimensions dimensions = calculateChartDimensions(anchorPane.getWidth(), dataCount);

            barChart.setBarGap(0);
            barChart.setCategoryGap(dimensions.categoryGap());
            barChart.setPrefSize(anchorPane.getWidth(), anchorPane.getHeight());
            barChart.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            barChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            for (XYChart.Series<String, Number> s : barChart.getData()) {
                for (XYChart.Data<String, Number> data : s.getData()) {
                    Node node = data.getNode();
                    if (node != null) {
                        node.setStyle("-fx-bar-fill: #4682B4;");

                        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(node.scaleYProperty(), 0)),
                                new KeyFrame(Duration.millis(1000), new KeyValue(node.scaleYProperty(), 1))
                        );
                        timeline.play();
                    }
                }
            }

            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(barChart);
            AnchorPane.setTopAnchor(barChart, 0.0);
            AnchorPane.setBottomAnchor(barChart, 0.0);
            AnchorPane.setLeftAnchor(barChart, 0.0);
            AnchorPane.setRightAnchor(barChart, 0.0);

            if (dataCount == 0) {
                Label noDataLabel = new Label("No data available");
                noDataLabel.setStyle("-fx-font-size: 20px;");
                anchorPane.getChildren().add(noDataLabel);
                AnchorPane.setTopAnchor(noDataLabel, 0.0);
                AnchorPane.setBottomAnchor(noDataLabel, 0.0);
                AnchorPane.setLeftAnchor(noDataLabel, 0.0);
                AnchorPane.setRightAnchor(noDataLabel, 0.0);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}