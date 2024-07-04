package org.group3.cinemabooking_2.Dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;


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


    private Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=CinemaBooking;encrypt=true;trustServerCertificate=true", "sa", "123");
    }

    @FXML
    public void ticketdashboardByDay(ActionEvent actionEvent) {
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

        displayChart(query, "Hour", "Period", "Revenue");
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

        displayChart(query, "Day of Week", "DayOfWeek", "Revenue");
        setTableComboBox("Ticket");
    }

    @FXML
    public void ticketdashboardByMonth(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String query = "SELECT DATEPART(DAY, dateTimeBook) AS Period, SUM(Total) AS Revenue " +
                "FROM Ticket " +
                "WHERE FORMAT(dateTimeBook, 'yyyy-MM') = '" + formattedMonth + "' " +
                "GROUP BY DATEPART(DAY, dateTimeBook) " +
                "ORDER BY Period";

        displayChart(query, "Day", "Period", "Revenue");
    }

    @FXML
    public void ticketdashboardByYear(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));

        String query = "SELECT DATEPART(MONTH, dateTimeBook) AS Period, SUM(Total) AS Revenue " +
                "FROM Ticket " +
                "WHERE FORMAT(dateTimeBook, 'yyyy') = '" + formattedYear + "' " +
                "GROUP BY DATEPART(MONTH, dateTimeBook) " +
                "ORDER BY Period";

        displayChart(query, "Month", "Period", "Revenue");
    }

    @FXML
    public void fooddashboardByDay(ActionEvent actionEvent) {
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

        displayChart(query, "Hour", "Period", "Revenue");
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

        displayChart(query, "Day of Week", "DayOfWeek", "Revenue");
        setTableComboBox("Food");
    }

    @FXML
    public void fooddashboardByMonth(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String query = "SELECT DATEPART(DAY, DateTime) AS Period, SUM(Total) AS Revenue " +
                "FROM Orders " +
                "WHERE FORMAT(DateTime, 'yyyy-MM') = '" + formattedMonth + "' " +
                "GROUP BY DATEPART(DAY, DateTime) " +
                "ORDER BY Period";

        displayChart(query, "Day", "Period", "Revenue");
    }

    @FXML
    public void fooddashboardByYear(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));

        String query = "SELECT DATEPART(MONTH, DateTime) AS Period, SUM(Total) AS Revenue " +
                "FROM Orders " +
                "WHERE FORMAT(DateTime, 'yyyy') = '" + formattedYear + "' " +
                "GROUP BY DATEPART(MONTH, DateTime) " +
                "ORDER BY Period";

        displayChart(query, "Month", "Period", "Revenue");
    }

    @FXML
    public void totaldashboardByDay(ActionEvent actionEvent) {
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
        displayChart(query, "Hour", "Period", "TotalRevenue");
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

        displayChart(query, "Day of Week", "DayOfWeek", "TotalRevenue");
        setTableComboBox("Total");
    }

    @FXML
    public void totaldashboardByMonth(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String query = "SELECT DATEPART(DAY, COALESCE(t.dateTimeBook, o.DateTime)) AS Period, " +
                "       SUM(COALESCE(t.Total, 0)) + SUM(COALESCE(o.Total, 0)) AS TotalRevenue " +
                "FROM Ticket t " +
                "FULL OUTER JOIN Orders o ON CAST(t.dateTimeBook AS DATE) = CAST(o.DateTime AS DATE) " +
                "WHERE FORMAT(COALESCE(t.dateTimeBook, o.DateTime), 'yyyy-MM') = '" + formattedMonth + "' " +
                "GROUP BY DATEPART(DAY, COALESCE(t.dateTimeBook, o.DateTime)) " +
                "ORDER BY Period";

        displayChart(query, "Day", "Period", "Revenue");
    }

    @FXML
    public void totaldashboardByYear(ActionEvent actionEvent) {
        LocalDate currentDate = LocalDate.now();
        String formattedYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));

        String query = "SELECT DATEPART(MONTH, COALESCE(t.dateTimeBook, o.DateTime)) AS Period, " +
                "       SUM(COALESCE(t.Total, 0)) + SUM(COALESCE(o.Total, 0)) AS TotalRevenue " +
                "FROM Ticket t " +
                "FULL OUTER JOIN Orders o ON CAST(t.dateTimeBook AS DATE) = CAST(o.DateTime AS DATE) " +
                "WHERE FORMAT(COALESCE(t.dateTimeBook, o.DateTime), 'yyyy') = '" + formattedYear + "' " +
                "GROUP BY DATEPART(MONTH, COALESCE(t.dateTimeBook, o.DateTime)) " +
                "ORDER BY Period";

        displayChart(query, "Month", "Period", "Revenue");
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
                "Day",
                "Week",
                "Month",
                "Year"
        );
        periodComboBox.setItems(periodList);
        periodComboBox.setValue("Day");
        periodComboBox.setOnAction(this::handlePeriodComboBoxAction);

        loadData();
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
        String selectedTable = tableComboBox.getValue();
        String selectedPeriod = periodComboBox.getValue();

        switch (selectedTable) {
            case "Ticket":
                switch (selectedPeriod) {
                    case "Day":
                        ticketdashboardByDay(null);
                        break;
                    case "Week":
                        ticketdashboardByWeek(null);
                        break;
                    case "Month":
                        ticketdashboardByMonth(null);
                        break;
                    case "Year":
                        ticketdashboardByYear(null);
                        break;
                }
                break;
            case "Food":
                switch (selectedPeriod) {
                    case "Day":
                        fooddashboardByDay(null);
                        break;
                    case "Week":
                        fooddashboardByWeek(null);
                        break;
                    case "Month":
                        fooddashboardByMonth(null);
                        break;
                    case "Year":
                        fooddashboardByYear(null);
                        break;
                }
                break;
            case "Total":
                switch (selectedPeriod) {
                    case "Day":
                        totaldashboardByDay(null);
                        break;
                    case "Week":
                        totaldashboardByWeek(null);
                        break;
                    case "Month":
                        totaldashboardByMonth(null);
                        break;
                    case "Year":
                        totaldashboardByYear(null);
                        break;
                }
                break;
        }
    }

    private void displayChart(String query, String xAxisLabel, String periodLabel, String yAxisLabel) {
        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            xAxis.setLabel(xAxisLabel);
            yAxis.setLabel(yAxisLabel);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            while (rs.next()) {
                String timePeriod = rs.getString(periodLabel);
                double revenue = rs.getDouble(2);
                series.getData().add(new XYChart.Data<>(timePeriod, revenue));
            }
            barChart.getData().add(series);

            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(barChart);
            AnchorPane.setTopAnchor(barChart, 0.0);
            AnchorPane.setBottomAnchor(barChart, 0.0);
            AnchorPane.setLeftAnchor(barChart, 0.0);
            AnchorPane.setRightAnchor(barChart, 0.0);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}