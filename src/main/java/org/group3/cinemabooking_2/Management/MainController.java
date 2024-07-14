package org.group3.cinemabooking_2.Management;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.time.LocalDate;

import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.DatePicker;
import org.group3.cinemabooking_2.LoginController;

public class MainController {
    @FXML
    private TableView<ShowTimes> showTimesTableView;

    private final ObservableList<ShowTimes> showTimesData = FXCollections.observableArrayList();
    private final ObservableList<Integer> movieIds = FXCollections.observableArrayList();
    private final ObservableList<Integer> theaterIds = FXCollections.observableArrayList();


    @FXML
    private ComboBox<String> tableComboBox;

    @FXML
    private TableView<Account> accountTableView;

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TableView<Client> clientTableView;


    @FXML
    private TableView<CategoryProduct> categoryProductTableView;

    @FXML
    private TextField searchField;

    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    private Timer searchTimer;
    private final ObservableList<Account> accountData = FXCollections.observableArrayList();
    private final ObservableList<Movie> movieData = FXCollections.observableArrayList();
    private final ObservableList<Client> clientData = FXCollections.observableArrayList();
    private final ObservableList<CategoryProduct> categoryProductData = FXCollections.observableArrayList();

    private Account accountLogin = new Account();

    //    public void setaccountLogin.getRole()(String role) {
//        this.accountLogin = role;
//    }
    @FXML
    public void initialize() {
        entity.entity.Account account = LoginController.getLoggedInUser();
        accountLogin.setRole(account.getRole());

        ObservableList<String> tableOptions = FXCollections.observableArrayList("Account", "Movie", "Client", "CategoryProduct", "ShowTimes");
        tableComboBox.setItems(tableOptions);
        tableComboBox.setValue("Account");
        loadAccountData();
        loadMovieData();
        loadClientData();
        loadCategoryProductData();
        loadShowTimesData();


        updateTableView("Account");
        accountTableView.refresh();
        setupTableSelectionListeners();
        accountTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        movieTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        clientTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        categoryProductTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        showTimesTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setupAccountTableView();
        setupMovieTableView();
        setupClientTableView();
        setupCategoryProductTableView();
        setupShowTimesTableView();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch();
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (searchTimer != null) {
                searchTimer.cancel();
            }
            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> onSearch());
                }
            }, 300); //
        });
        updateTableView("Account");
        accountTableView.refresh();
        setupTableSelectionListeners();
        updateAddButtonVisibility("Account");
        updateButtonStates();


    }

    private void setupTableSelectionListeners() {
        accountTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateButtonStates();
        });
        movieTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateButtonStates();
        });
        clientTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateButtonStates();
        });
        categoryProductTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateButtonStates();
        });
        showTimesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateButtonStates();
        });
    }

    private void updateTableView(String tableName) {
        accountTableView.setVisible(false);
        movieTableView.setVisible(false);
        clientTableView.setVisible(false);
        categoryProductTableView.setVisible(false);
        showTimesTableView.setVisible(false);

        switch (tableName) {
            case "Account":
                accountTableView.setVisible(true);
                setupAccountTableView();
                accountTableView.setItems(accountData);
                break;
            case "Movie":
                movieTableView.setVisible(true);
                setupMovieTableView();
                movieTableView.setItems(movieData);
                break;
            case "Client":
                clientTableView.setVisible(true);
                setupClientTableView();
                clientTableView.setItems(clientData);
                break;
            case "CategoryProduct":
                categoryProductTableView.setVisible(true);
                setupCategoryProductTableView();
                categoryProductTableView.setItems(categoryProductData);
                break;
            case "ShowTimes":
                showTimesTableView.setVisible(true);
                setupShowTimesTableView();
                showTimesTableView.setItems(showTimesData);
                break;
        }
    }

    private void setupAccountTableView() {
        TableColumn<Account, Integer> idAccountCol = new TableColumn<>("IDAccount");
        idAccountCol.setCellValueFactory(new PropertyValueFactory<>("idAccount"));
        TableColumn<Account, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Account, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Account, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        TableColumn<Account, String> phoneNumberCol = new TableColumn<>("PhoneNumber");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Account, String> dateOfBirthCol = new TableColumn<>("DateOfBirth");
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        TableColumn<Account, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        TableColumn<Account, String> avatarCol = new TableColumn<>("Avatar");
        avatarCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        accountTableView.getColumns().setAll(idAccountCol, nameCol, emailCol, passwordCol, phoneNumberCol, dateOfBirthCol, roleCol, avatarCol);
    }

    private void updateButtonStates() {
        boolean isItemSelected = false;
        String selectedTable = tableComboBox.getValue();

        switch (selectedTable) {
            case "Account":
                isItemSelected = accountTableView.getSelectionModel().getSelectedItem() != null;
                break;
            case "Movie":
                isItemSelected = movieTableView.getSelectionModel().getSelectedItem() != null;
                break;
            case "Client":
                isItemSelected = clientTableView.getSelectionModel().getSelectedItem() != null;
                break;
            case "CategoryProduct":
                isItemSelected = categoryProductTableView.getSelectionModel().getSelectedItem() != null;
                break;
            case "ShowTimes":
                isItemSelected = showTimesTableView.getSelectionModel().getSelectedItem() != null;
                break;
        }

        editButton.setDisable(!isItemSelected);
        deleteButton.setDisable(!isItemSelected);
        boolean canEdit = true;
        boolean canDelete = true;
        boolean canAdd = true;

        if ("admin".equals(accountLogin.getRole())) {
            Account selectedAccount = accountTableView.getSelectionModel().getSelectedItem();
            if (selectedAccount != null && "owner".equals(selectedAccount.getRole())) {
                canEdit = false;
                canDelete = false;
            }
        } else if (!"owner".equals(accountLogin.getRole())) {
            canEdit = false;
            canDelete = false;
            canAdd = false;
        }

        editButton.setDisable(!isItemSelected || !canEdit);
        deleteButton.setDisable(!isItemSelected || !canDelete);
        addButton.setDisable(!canAdd);
    }

    private void setupMovieTableView() {
        TableColumn<Movie, Integer> idMovieCol = new TableColumn<>("IDMovie");
        idMovieCol.setCellValueFactory(new PropertyValueFactory<>("idMovie"));
        TableColumn<Movie, String> movieNameCol = new TableColumn<>("MovieName");
        movieNameCol.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        TableColumn<Movie, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<Movie, Integer> amountOfLimitCol = new TableColumn<>("AmountOfLimit");
        amountOfLimitCol.setCellValueFactory(new PropertyValueFactory<>("amountOfLimit"));
        TableColumn<Movie, String> typeOfMovieCol = new TableColumn<>("TypeOfMovie");
        typeOfMovieCol.setCellValueFactory(new PropertyValueFactory<>("typeOfMovie"));
        TableColumn<Movie, String> imagesPosterCol = new TableColumn<>("ImagesPoster");
        imagesPosterCol.setCellValueFactory(new PropertyValueFactory<>("imagesPoster"));
        TableColumn<Movie, String> imagesBackdropCol = new TableColumn<>("ImagesBackdrop");
        imagesBackdropCol.setCellValueFactory(new PropertyValueFactory<>("imagesBackdrop"));
        movieTableView.getColumns().setAll(idMovieCol, movieNameCol, authorCol, amountOfLimitCol, typeOfMovieCol, imagesPosterCol, imagesBackdropCol);
    }

    private void setupClientTableView() {
        TableColumn<Client, String> emailClientCol = new TableColumn<>("EmailClient");
        emailClientCol.setCellValueFactory(new PropertyValueFactory<>("emailClient"));
        TableColumn<Client, String> phoneNumberCol = new TableColumn<>("PhoneNumber");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Client, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientTableView.getColumns().setAll(emailClientCol, phoneNumberCol, nameCol);
    }

    private void setupCategoryProductTableView() {
        TableColumn<CategoryProduct, Integer> idCategoryCol = new TableColumn<>("IDCategory");
        idCategoryCol.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        TableColumn<CategoryProduct, String> productNameCol = new TableColumn<>("ProductName");
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryProductTableView.getColumns().setAll(idCategoryCol, productNameCol);
    }

    private void setupShowTimesTableView() {
        TableColumn<ShowTimes, Integer> idShowTimesCol = new TableColumn<>("IDShowTimes");
        idShowTimesCol.setCellValueFactory(new PropertyValueFactory<>("idShowTimes"));
        TableColumn<ShowTimes, Time> startTimeCol = new TableColumn<>("StartTime");
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<ShowTimes, Time> endTimeCol = new TableColumn<>("EndTime");
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<ShowTimes, Date> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<ShowTimes, Integer> idMovieCol = new TableColumn<>("IDMovie");
        idMovieCol.setCellValueFactory(new PropertyValueFactory<>("idMovie"));
        TableColumn<ShowTimes, Integer> idTheaterCol = new TableColumn<>("IDTheater");
        idTheaterCol.setCellValueFactory(new PropertyValueFactory<>("idTheater"));
        showTimesTableView.getColumns().setAll(idShowTimesCol, startTimeCol, endTimeCol, dateCol, idMovieCol, idTheaterCol);
    }


    private void loadAccountData() {
        String query = "SELECT * FROM Account";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            accountData.clear();
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("idAccount"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phoneNumber"),
                        rs.getString("dateOfBirth"),
                        rs.getString("role"),
                        rs.getString("avatar")
                );
                accountData.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMovieData() {
        String query = "SELECT * FROM Movie";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            movieData.clear();
            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("idMovie"),
                        rs.getString("movieName"),
                        rs.getString("author"),
                        rs.getInt("amoutOfLimit"),
                        rs.getString("typeOfMovie"),
                        rs.getString("imagesPoster"),
                        rs.getString("imagesBackdrop")
                );
                movieData.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadClientData() {
        String query = "SELECT * FROM Client";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            clientData.clear();
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("emailClient"),
                        rs.getString("phoneNumber"),
                        rs.getString("name")
                );
                clientData.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCategoryProductData() {
        String query = "SELECT * FROM CategoryProduct";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            categoryProductData.clear();
            while (rs.next()) {
                CategoryProduct categoryProduct = new CategoryProduct(
                        rs.getInt("idCategory"),
                        rs.getString("productName")
                );
                categoryProductData.add(categoryProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadShowTimesData() {
        String query = "SELECT * FROM ShowTimes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            showTimesData.clear();
            while (rs.next()) {
                ShowTimes showTime = new ShowTimes(
                        rs.getInt("IDShowTimes"),
                        rs.getTime("StartTime"),
                        rs.getTime("EndTime"),
                        rs.getDate("Date"),
                        rs.getInt("IDMovie"),
                        rs.getInt("IDTheater")
                );
                showTimesData.add(showTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String DEFAULT_AVATAR_PATH = "D:\\T1.2308.A0\\7. Java\\3.JP2\\JavaFX\\CinemaBooking_2\\CinemaBooking_2\\src\\main\\resources\\Images\\CinemaPLUSLogo.png";

    @FXML
    private void onAdd() {
        if (!"owner".equals(accountLogin.getRole()) && !"admin".equals(accountLogin.getRole())) {
            showAlert(Alert.AlertType.WARNING, "Permission Denied", "You don't have permission to add new items.");
            return;
        }
        String selectedTable = tableComboBox.getValue();
        if (selectedTable.equals("Account")) {
            Dialog<Account> dialog = new Dialog<>();
            DialogPane dialogPane = dialog.getDialogPane();
//            dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
//            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setTitle("Add New Account");
            dialog.setHeaderText("Enter details for the new account");


            ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            TextField emailField = new TextField();
            emailField.setPromptText("Email");
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");
            TextField phoneNumberField = new TextField();
            phoneNumberField.setPromptText("Phone Number");
            DatePicker dateOfBirthPicker = new DatePicker();
            dateOfBirthPicker.setPromptText("Date of Birth");
            ComboBox<String> roleComboBox = new ComboBox<>();
            roleComboBox.getItems().addAll("admin", "staff");
            roleComboBox.setValue("staff");

            TextField avatarField = new TextField();
            avatarField.setEditable(false);
            Button chooseAvatarButton = new Button("Choose Avatar");
            chooseAvatarButton.setOnAction(e -> {
                String imagePath = openFileChooser("Choose Avatar");
                if (imagePath != null) {
                    avatarField.setText(imagePath);
                }
            });

            if ("admin".equals(accountLogin.getRole()) && "owner".equals(roleComboBox.getValue())) {
                showAlert(Alert.AlertType.WARNING, "Permission Denied", "Admin cannot add owner accounts.");
                return;
            }

            GridPane grid = new GridPane();
            grid.add(new Label("Name:"), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("Email:"), 0, 1);
            grid.add(emailField, 1, 1);
            grid.add(new Label("Password:"), 0, 2);
            grid.add(passwordField, 1, 2);
            grid.add(new Label("Phone Number:"), 0, 3);
            grid.add(phoneNumberField, 1, 3);
            grid.add(new Label("Date of Birth:"), 0, 4);
            grid.add(dateOfBirthPicker, 1, 4);
            grid.add(roleComboBox, 0, 5);
            grid.add(new Label("Avatar:"), 0, 6);
            grid.add(avatarField, 1, 6);
            grid.add(chooseAvatarButton, 2, 6);


            dialog.getDialogPane().setContent(grid);

            Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
            addButton.setDisable(true);

            addButton.addEventFilter(ActionEvent.ACTION, event -> {
                if (!validateAccountInput(nameField, emailField, passwordField, phoneNumberField, dateOfBirthPicker, roleComboBox)) {
                    event.consume();
                }
            });
            nameField.textProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(false));
            emailField.textProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(false));
            passwordField.textProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(false));
            phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(false));
            dateOfBirthPicker.valueProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(false));

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    String avatarPath = avatarField.getText().isEmpty() ? DEFAULT_AVATAR_PATH : avatarField.getText();
                    return new Account(
                            0,
                            nameField.getText(),
                            emailField.getText(),
                            passwordField.getText(),
                            phoneNumberField.getText(),
                            dateOfBirthPicker.getValue().toString(),
                            roleComboBox.getValue(),
                            avatarPath
                    );
                }
                return null;
            });

            Optional<Account> result = dialog.showAndWait();

            result.ifPresent(newAccount -> {
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO Account (name, email, password, phoneNumber, dateOfBirth, role, avatar) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                    stmt.setString(1, newAccount.getName());
                    stmt.setString(2, newAccount.getEmail());
                    stmt.setString(3, newAccount.getPassword());
                    stmt.setString(4, newAccount.getPhoneNumber());
                    stmt.setString(5, newAccount.getDateOfBirth());
                    stmt.setString(6, newAccount.getRole());
                    stmt.setString(7, newAccount.getAvatar().equals(DEFAULT_AVATAR_PATH) ? null : newAccount.getAvatar());
                    stmt.executeUpdate();
                    loadAccountData();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Could not add account to database. Please try again.");
                }
            });


        } else if (selectedTable.equals("Movie")) {
            Dialog<Movie> dialog = new Dialog<>();
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setTitle("Add New Movie");
            dialog.setHeaderText("Enter details for the new movie");

            ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

            TextField movieNameField = new TextField();
            movieNameField.setPromptText("Movie Name");
            TextField authorField = new TextField();
            authorField.setPromptText("Author");
            TextField amountOfLimitField = new TextField();
            amountOfLimitField.setPromptText("Amount of Limit");
            TextField typeOfMovieField = new TextField();
            typeOfMovieField.setPromptText("Type of Movie");
            TextField imagesPosterField = new TextField();
            imagesPosterField.setEditable(false);
            Button chooseImagesPosterButton = new Button("Choose Poster");
            chooseImagesPosterButton.setOnAction(e -> {
                String imagePath = openFileChooser("Choose Poster Image");
                if (imagePath != null) {
                    imagesPosterField.setText(imagePath);
                }
            });

            TextField imagesBackdropField = new TextField();
            imagesBackdropField.setEditable(false);
            Button chooseImagesBackdropButton = new Button("Choose Backdrop");
            chooseImagesBackdropButton.setOnAction(e -> {
                String imagePath = openFileChooser("Choose Backdrop Image");
                if (imagePath != null) {
                    imagesBackdropField.setText(imagePath);
                }
            });


            GridPane grid = new GridPane();
            grid.add(new Label("Movie Name:"), 0, 0);
            grid.add(movieNameField, 1, 0);
            grid.add(new Label("Author:"), 0, 1);
            grid.add(authorField, 1, 1);
            grid.add(new Label("Amount of Limit:"), 0, 2);
            grid.add(amountOfLimitField, 1, 2);
            grid.add(new Label("Type of Movie:"), 0, 3);
            grid.add(typeOfMovieField, 1, 3);
            grid.add(new Label("Poster Image:"), 0, 4);
            grid.add(imagesPosterField, 1, 4);
            grid.add(chooseImagesPosterButton, 2, 4);
            grid.add(new Label("Backdrop Image:"), 0, 5);
            grid.add(imagesBackdropField, 1, 5);
            grid.add(chooseImagesBackdropButton, 2, 5);

            dialog.getDialogPane().setContent(grid);

            Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
            addButton.setDisable(false);

            addButton.addEventFilter(ActionEvent.ACTION, event -> {
                if (!validateMovieInput(movieNameField, authorField, amountOfLimitField, typeOfMovieField)) {
                    event.consume();
                }
            });

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    return new Movie(
                            0,
                            movieNameField.getText(),
                            authorField.getText(),
                            Integer.parseInt(amountOfLimitField.getText()),
                            typeOfMovieField.getText(),
                            imagesPosterField.getText(),
                            imagesBackdropField.getText()
                    );
                }
                return null;
            });

            Optional<Movie> result = dialog.showAndWait();

            result.ifPresent(newMovie -> {
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO Movie (movieName, author, amoutOfLimit, typeOfMovie, imagesPoster, imagesBackdrop) VALUES (?, ?, ?, ?, ?, ?)")) {
                    stmt.setString(1, newMovie.getMovieName());
                    stmt.setString(2, newMovie.getAuthor());
                    stmt.setInt(3, newMovie.getAmountOfLimit());
                    stmt.setString(4, newMovie.getTypeOfMovie());
                    stmt.setString(5, newMovie.getImagesPoster());
                    stmt.setString(6, newMovie.getImagesBackdrop());
                    stmt.executeUpdate();
                    loadMovieData();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Could not add movie to database. Please try again.");
                }
            });
        } else if (selectedTable.equals("CategoryProduct")) {
            Dialog<CategoryProduct> dialog = new Dialog<>();
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setTitle("Add New Category");
            dialog.setHeaderText("Enter details for the new category");

            ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

            TextField categoryNameField = new TextField();
            categoryNameField.setPromptText("Category Name");

            GridPane grid = new GridPane();
            grid.add(new Label("Category Name:"), 0, 0);
            grid.add(categoryNameField, 1, 0);

            dialog.getDialogPane().setContent(grid);

            Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
            addButton.setDisable(false);

            addButton.addEventFilter(ActionEvent.ACTION, event -> {
                if (!validateCategoryProductInput(categoryNameField)) {
                    event.consume();
                }
            });

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    return new CategoryProduct(0, categoryNameField.getText());
                }
                return null;
            });

            while (true) {
                Optional<CategoryProduct> result = dialog.showAndWait();
                if (result.isPresent()) {
                    CategoryProduct newCategory = result.get();
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("INSERT INTO CategoryProduct (productName) VALUES (?)")) {
                        stmt.setString(1, newCategory.getProductName());
                        stmt.executeUpdate();
                        loadCategoryProductData();
                        break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not add category to database. Please try again.");
                    }
                } else {
                    // User cancelled the dialog
                    break;
                }
            }
        } else if (selectedTable.equals("ShowTimes")) {
            ShowTimes selectedShowTime = showTimesTableView.getSelectionModel().getSelectedItem();
            if (selectedShowTime != null) {
                if (isDateInPast(selectedShowTime.getDate()) || isTimeInPast(selectedShowTime.getStartTime(), selectedShowTime.getDate())) {
                    showAlert(Alert.AlertType.WARNING, "Cannot Update", "Cannot update showtime from the past.");
                    return;
                }

                Dialog<ShowTimes> dialog = new Dialog<>();
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                dialog.setTitle("Edit ShowTime");
                dialog.setHeaderText("Edit the details for the selected showtime");

                ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                // Tạo các trường nhập liệu và đặt giá trị ban đầu
                ComboBox<Integer> idMovieComboBox = new ComboBox<>();
                ComboBox<Integer> idTheaterComboBox = new ComboBox<>();
                populateComboBoxes(idMovieComboBox, idTheaterComboBox);
                idMovieComboBox.setValue(selectedShowTime.getIdMovie());
                idTheaterComboBox.setValue(selectedShowTime.getIdTheater());

                TextField startTimeField = new TextField(selectedShowTime.getStartTime().toString().substring(0, 8));
                DatePicker datePicker = new DatePicker(selectedShowTime.getDate().toLocalDate());

                GridPane grid = new GridPane();
                grid.add(new Label("Movie:"), 0, 0);
                grid.add(idMovieComboBox, 1, 0);
                grid.add(new Label("Theater:"), 0, 1);
                grid.add(idTheaterComboBox, 1, 1);
                grid.add(new Label("Start Time:"), 0, 2);
                grid.add(startTimeField, 1, 2);
                grid.add(new Label("Date:"), 0, 3);
                grid.add(datePicker, 1, 3);

                dialog.getDialogPane().setContent(grid);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == editButtonType) {
                        try {
                            Time startTime = validateTime(startTimeField.getText());
                            Date date = Date.valueOf(datePicker.getValue());

                            if (isDateInPast(date) || isTimeInPast(startTime, date)) {
                                throw new IllegalArgumentException("Date or time cannot be in the past.");
                            }

                            int idMovie = idMovieComboBox.getValue();
                            int idTheater = idTheaterComboBox.getValue();

                            int amountOfLimit = fetchAmountOfLimit(idMovie);
                            Time endTime = calculateEndTime(startTime, amountOfLimit);

                            return new ShowTimes(selectedShowTime.getIdShowTimes(), startTime, endTime, date, idMovie, idTheater);
                        } catch (IllegalArgumentException | SQLException e) {
                            e.printStackTrace();
                            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid time and date values.");
                            return null;
                        }
                    }
                    return null;
                });

                Optional<ShowTimes> result = dialog.showAndWait();

                result.ifPresent(editedShowTime -> {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("UPDATE ShowTimes SET StartTime=?, EndTime=?, Date=?, IDMovie=?, IDTheater=? WHERE IDShowTimes=?")) {
                        stmt.setTime(1, editedShowTime.getStartTime());
                        stmt.setTime(2, editedShowTime.getEndTime());
                        stmt.setDate(3, editedShowTime.getDate());
                        stmt.setInt(4, editedShowTime.getIdMovie());
                        stmt.setInt(5, editedShowTime.getIdTheater());
                        stmt.setInt(6, editedShowTime.getIdShowTimes());
                        stmt.executeUpdate();
                        loadShowTimesData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update showtime in database. Please try again.");
                    }
                });
            }
        }

    }

    @FXML
    private void onEdit() {
        if (!"owner".equals(accountLogin.getRole()) && !"admin".equals(accountLogin.getRole())) {
            showAlert(Alert.AlertType.WARNING, "Permission Denied", "You don't have permission to edit items.");
            return;
        }
        String selectedTable = tableComboBox.getValue();
        if (selectedTable.equals("Account")) {
            Account selectedAccount = accountTableView.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                if ("admin".equals(accountLogin.getRole()) && "owner".equals(selectedAccount.getRole())) {
                    showAlert(Alert.AlertType.WARNING, "Permission Denied", "Admin cannot edit owner accounts.");
                    return;
                }
                Dialog<Account> dialog = new Dialog<>();
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                dialog.setTitle("Edit Account");
                dialog.setHeaderText("Edit the details for the selected account");

                // Set the button types (OK and Cancel)
                ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                // Create text fields and set the dialog content
                TextField nameField = new TextField(selectedAccount.getName());
                nameField.setPromptText("Name");
                TextField emailField = new TextField(selectedAccount.getEmail());
                emailField.setPromptText("Email");
                PasswordField passwordField = new PasswordField();
                passwordField.setText(selectedAccount.getPassword());
                passwordField.setPromptText("Password");
                TextField phoneNumberField = new TextField(selectedAccount.getPhoneNumber());
                phoneNumberField.setPromptText("Phone Number");
                DatePicker dateOfBirthPicker = new DatePicker();
                dateOfBirthPicker.setValue(LocalDate.parse(selectedAccount.getDateOfBirth()));
                ComboBox<String> roleComboBox = new ComboBox<>();
                roleComboBox.getItems().addAll("admin", "staff");
                roleComboBox.setValue("staff");
                TextField avatarField = new TextField(selectedAccount.getAvatar());
                avatarField.setEditable(false);
                Button chooseAvatarButton = new Button("Choose Avatar");
                chooseAvatarButton.setOnAction(e -> {
                    String imagePath = openFileChooser("Choose Avatar");
                    if (imagePath != null) {
                        avatarField.setText(imagePath);
                    }
                });

                GridPane grid = new GridPane();
                grid.add(new Label("Name:"), 0, 0);
                grid.add(nameField, 1, 0);
                grid.add(new Label("Email:"), 0, 1);
                grid.add(emailField, 1, 1);
                grid.add(new Label("Password:"), 0, 2);
                grid.add(passwordField, 1, 2);
                grid.add(new Label("Phone Number:"), 0, 3);
                grid.add(phoneNumberField, 1, 3);
                grid.add(new Label("Date of Birth:"), 0, 4);
                grid.add(dateOfBirthPicker, 1, 4);
                grid.add(roleComboBox, 0, 5);
                grid.add(new Label("Avatar:"), 0, 6);
                grid.add(avatarField, 1, 6);
                grid.add(chooseAvatarButton, 2, 6);

                dialog.getDialogPane().setContent(grid);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == editButtonType) {
                        if (validateAccountInput(nameField, emailField, passwordField, phoneNumberField, dateOfBirthPicker, roleComboBox)) {
                            String avatarPath = avatarField.getText().isEmpty() ? DEFAULT_AVATAR_PATH : avatarField.getText();
                            return new Account(
                                    selectedAccount.getIdAccount(),
                                    nameField.getText(),
                                    emailField.getText(),
                                    passwordField.getText(),
                                    phoneNumberField.getText(),
                                    dateOfBirthPicker.getValue().toString(),
                                    roleComboBox.getValue(),
                                    avatarPath
                            );
                        }
                    }
                    return null;
                });

                Optional<Account> result = dialog.showAndWait();

                result.ifPresent(editedAccount -> {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("UPDATE Account SET name=?, email=?, password=?, phoneNumber=?, dateOfBirth=?, role=?, avatar=? WHERE idAccount=?")) {
                        stmt.setString(1, editedAccount.getName());
                        stmt.setString(2, editedAccount.getEmail());
                        stmt.setString(3, editedAccount.getPassword());
                        stmt.setString(4, editedAccount.getPhoneNumber());
                        stmt.setString(5, editedAccount.getDateOfBirth());
                        stmt.setString(6, editedAccount.getRole());
                        stmt.setString(7, editedAccount.getAvatar().equals(DEFAULT_AVATAR_PATH) ? null : editedAccount.getAvatar());
                        stmt.setInt(8, editedAccount.getIdAccount());
                        stmt.executeUpdate();
                        loadAccountData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update account in database. Please try again.");
                    }
                });
            }
        } else if (selectedTable.equals("Movie")) {
            Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                Dialog<Movie> dialog = new Dialog<>();
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                dialog.setTitle("Edit Movie");
                dialog.setHeaderText("Edit the details for the selected movie");

                // Set the button types (OK and Cancel)
                ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                // Create text fields and set the dialog content
                TextField movieNameField = new TextField(selectedMovie.getMovieName());
                movieNameField.setPromptText("Movie Name");
                TextField authorField = new TextField(selectedMovie.getAuthor());
                authorField.setPromptText("Author");
                TextField amountOfLimitField = new TextField(String.valueOf(selectedMovie.getAmountOfLimit()));
                amountOfLimitField.setPromptText("Amount of Limit");
                TextField typeOfMovieField = new TextField(selectedMovie.getTypeOfMovie());
                typeOfMovieField.setPromptText("Type of Movie");
                TextField imagesPosterField = new TextField(selectedMovie.getImagesPoster());
                imagesPosterField.setEditable(false);
                Button chooseImagesPosterButton = new Button("Choose Poster");
                chooseImagesPosterButton.setOnAction(e -> {
                    String imagePath = openFileChooser("Choose Poster Image");
                    if (imagePath != null) {
                        imagesPosterField.setText(imagePath);
                    }
                });
                TextField imagesBackdropField = new TextField(selectedMovie.getImagesBackdrop());
                imagesBackdropField.setEditable(false);
                Button chooseImagesBackdropButton = new Button("Choose Backdrop");
                chooseImagesBackdropButton.setOnAction(e -> {
                    String imagePath = openFileChooser("Choose Backdrop Image");
                    if (imagePath != null) {
                        imagesBackdropField.setText(imagePath);
                    }
                });

                GridPane grid = new GridPane();
                grid.add(new Label("Movie Name:"), 0, 0);
                grid.add(movieNameField, 1, 0);
                grid.add(new Label("Author:"), 0, 1);
                grid.add(authorField, 1, 1);
                grid.add(new Label("Amount of Limit:"), 0, 2);
                grid.add(amountOfLimitField, 1, 2);
                grid.add(new Label("Type of Movie:"), 0, 3);
                grid.add(typeOfMovieField, 1, 3);
                grid.add(new Label("Poster Image:"), 0, 4);
                grid.add(imagesPosterField, 1, 4);
                grid.add(chooseImagesPosterButton, 2, 4);
                grid.add(new Label("Backdrop Image:"), 0, 5);
                grid.add(imagesBackdropField, 1, 5);
                grid.add(chooseImagesBackdropButton, 2, 5);

                dialog.getDialogPane().setContent(grid);

                // Convert the result to a Movie object when the edit button is clicked
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == editButtonType) {
                        if (validateMovieInput(movieNameField, authorField, amountOfLimitField, typeOfMovieField)) {
                            return new Movie(
                                    selectedMovie.getIdMovie(),
                                    movieNameField.getText(),
                                    authorField.getText(),
                                    Integer.parseInt(amountOfLimitField.getText()),
                                    typeOfMovieField.getText(),
                                    imagesPosterField.getText(),
                                    imagesBackdropField.getText()
                            );
                        }
                    }
                    return null;
                });

                Optional<Movie> result = dialog.showAndWait();

                result.ifPresent(editedMovie -> {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("UPDATE Movie SET movieName=?, author=?, amoutOfLimit=?, typeOfMovie=?, imagesPoster=?, imagesBackdrop=? WHERE idMovie=?")) {
                        stmt.setString(1, editedMovie.getMovieName());
                        stmt.setString(2, editedMovie.getAuthor());
                        stmt.setInt(3, editedMovie.getAmountOfLimit());
                        stmt.setString(4, editedMovie.getTypeOfMovie());
                        stmt.setString(5, editedMovie.getImagesPoster());
                        stmt.setString(6, editedMovie.getImagesBackdrop());
                        stmt.setInt(7, editedMovie.getIdMovie());
                        stmt.executeUpdate();
                        loadMovieData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else if (selectedTable.equals("Client")) {
            Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                Dialog<Client> dialog = new Dialog<>();
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                dialog.setTitle("Edit Client");
                dialog.setHeaderText("Edit the details for the selected client");

                // Set the button types (OK and Cancel)
                ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                // Create text fields and set the dialog content
                TextField emailField = new TextField(selectedClient.getEmailClient());
                emailField.setPromptText("Email");
                TextField phoneNumberField = new TextField(selectedClient.getPhoneNumber());
                phoneNumberField.setPromptText("Phone Number");
                TextField nameField = new TextField(selectedClient.getName());
                nameField.setPromptText("Name");

                GridPane grid = new GridPane();
                grid.add(new Label("Email:"), 0, 0);
                grid.add(emailField, 1, 0);
                grid.add(new Label("Phone Number:"), 0, 1);
                grid.add(phoneNumberField, 1, 1);
                grid.add(new Label("Name:"), 0, 2);
                grid.add(nameField, 1, 2);

                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == editButtonType) {
                        if (validateClientInput(emailField, phoneNumberField, nameField)) {
                            return new Client(
                                    emailField.getText(),
                                    phoneNumberField.getText(),
                                    nameField.getText()
                            );
                        }
                    }
                    return null;
                });

                Optional<Client> result = dialog.showAndWait();

                result.ifPresent(editedClient -> {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("UPDATE Client SET emailClient=?, phoneNumber=?, name=? WHERE emailClient=?")) {
                        stmt.setString(1, editedClient.getEmailClient());
                        stmt.setString(2, editedClient.getPhoneNumber());
                        stmt.setString(3, editedClient.getName());
                        stmt.setString(4, selectedClient.getEmailClient());
                        stmt.executeUpdate();
                        loadClientData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else if (selectedTable.equals("CategoryProduct")) {
            CategoryProduct selectedCategory = categoryProductTableView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                Dialog<CategoryProduct> dialog = new Dialog<>();
                DialogPane dialogPane = dialog.getDialogPane();

                dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                dialog.setTitle("Edit Category");
                dialog.setHeaderText("Edit the details for the selected category");

                ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                TextField categoryNameField = new TextField(selectedCategory.getProductName());
                categoryNameField.setPromptText("Category Name");

                GridPane grid = new GridPane();
                grid.add(new Label("Category Name:"), 0, 0);
                grid.add(categoryNameField, 1, 0);

                dialog.getDialogPane().setContent(grid);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == editButtonType) {
                        if (validateCategoryProductInput(categoryNameField)) {
                            return new CategoryProduct(
                                    selectedCategory.getIdCategory(),
                                    categoryNameField.getText()
                            );
                        }
                    }
                    return null;
                });

                Optional<CategoryProduct> result = dialog.showAndWait();

                result.ifPresent(editedCategory -> {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("UPDATE CategoryProduct SET ProductName=? WHERE IdCategory=?")) {
                        stmt.setString(1, editedCategory.getProductName());
                        stmt.setInt(2, editedCategory.getIdCategory());
                        stmt.executeUpdate();
                        loadCategoryProductData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update category in database. Please try again.");
                    }
                });
            }
        } else if (selectedTable.equals("ShowTimes")) {
            ShowTimes selectedShowTime = showTimesTableView.getSelectionModel().getSelectedItem();
            if (selectedShowTime != null) {
                if (isDateInPast(selectedShowTime.getDate()) || isTimeInPast(selectedShowTime.getStartTime(), selectedShowTime.getDate())) {
                    showAlert(Alert.AlertType.WARNING, "Cannot Update", "Cannot update showtime from the past.");
                    return;
                }

                Dialog<ShowTimes> dialog = new Dialog<>();
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("dialog-styles.css").toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                dialog.setTitle("Edit ShowTime");
                dialog.setHeaderText("Edit the details for the selected showtime");

                ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

                // Tạo các trường nhập liệu và đặt giá trị ban đầu
                ComboBox<Integer> idMovieComboBox = new ComboBox<>();
                ComboBox<Integer> idTheaterComboBox = new ComboBox<>();
                populateComboBoxes(idMovieComboBox, idTheaterComboBox);
                idMovieComboBox.setValue(selectedShowTime.getIdMovie());
                idTheaterComboBox.setValue(selectedShowTime.getIdTheater());

                TextField startTimeField = new TextField(selectedShowTime.getStartTime().toString().substring(0, 8));
                DatePicker datePicker = new DatePicker(selectedShowTime.getDate().toLocalDate());

                GridPane grid = new GridPane();
                grid.add(new Label("Movie:"), 0, 0);
                grid.add(idMovieComboBox, 1, 0);
                grid.add(new Label("Theater:"), 0, 1);
                grid.add(idTheaterComboBox, 1, 1);
                grid.add(new Label("Start Time:"), 0, 2);
                grid.add(startTimeField, 1, 2);
                grid.add(new Label("Date:"), 0, 3);
                grid.add(datePicker, 1, 3);

                dialog.getDialogPane().setContent(grid);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == editButtonType) {
                        try {
                            Time startTime = validateTime(startTimeField.getText());
                            Date date = Date.valueOf(datePicker.getValue());

                            if (isDateInPast(date) || isTimeInPast(startTime, date)) {
                                throw new IllegalArgumentException("Date or time cannot be in the past.");
                            }

                            int idMovie = idMovieComboBox.getValue();
                            int idTheater = idTheaterComboBox.getValue();

                            int amountOfLimit = fetchAmountOfLimit(idMovie);
                            Time endTime = calculateEndTime(startTime, amountOfLimit);

                            return new ShowTimes(selectedShowTime.getIdShowTimes(), startTime, endTime, date, idMovie, idTheater);
                        } catch (IllegalArgumentException | SQLException e) {
                            e.printStackTrace();
                            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid time and date values.");
                            return null;
                        }
                    }
                    return null;
                });

                Optional<ShowTimes> result = dialog.showAndWait();

                result.ifPresent(editedShowTime -> {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("UPDATE ShowTimes SET StartTime=?, EndTime=?, Date=?, IDMovie=?, IDTheater=? WHERE IDShowTimes=?")) {
                        stmt.setTime(1, editedShowTime.getStartTime());
                        stmt.setTime(2, editedShowTime.getEndTime());
                        stmt.setDate(3, editedShowTime.getDate());
                        stmt.setInt(4, editedShowTime.getIdMovie());
                        stmt.setInt(5, editedShowTime.getIdTheater());
                        stmt.setInt(6, editedShowTime.getIdShowTimes());
                        stmt.executeUpdate();
                        loadShowTimesData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update showtime in database. Please try again.");
                    }
                });
            }
        }
    }

    @FXML
    private void onDelete() {
        if (!"owner".equals(accountLogin.getRole()) && !"admin".equals(accountLogin.getRole())) {
            showAlert(Alert.AlertType.WARNING, "Permission Denied", "You don't have permission to delete items.");
            return;
        }
        String selectedTable = tableComboBox.getValue();
        if (selectedTable.equals("Account")) {
            Account selectedAccount = accountTableView.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                if ("admin".equals(accountLogin.getRole()) && "owner".equals(selectedAccount.getRole())) {
                    showAlert(Alert.AlertType.WARNING, "Permission Denied", "Admin cannot delete owner accounts.");
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this account?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("DELETE FROM Account WHERE idAccount=?")) {
                        stmt.setInt(1, selectedAccount.getIdAccount());
                        stmt.executeUpdate();
                        loadAccountData();
                    } catch (SQLServerException e) {
                        handleDeleteException(e, "account");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the account.");
                    }
                }
            }
        } else if (selectedTable.equals("Movie")) {
            Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this movie?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("DELETE FROM Movie WHERE idMovie=?")) {
                        stmt.setInt(1, selectedMovie.getIdMovie());
                        stmt.executeUpdate();
                        loadMovieData();
                    } catch (SQLServerException e) {
                        handleDeleteException(e, "movie");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the movie.");
                    }
                }
            }
        } else if (selectedTable.equals("Client")) {
            Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this client?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("DELETE FROM Client WHERE emailClient=?")) {
                        stmt.setString(1, selectedClient.getEmailClient());
                        stmt.executeUpdate();
                        loadClientData();
                    } catch (SQLServerException e) {
                        handleDeleteException(e, "client");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the client.");
                    }
                }
            }
        } else if (selectedTable.equals("CategoryProduct")) {
            CategoryProduct selectedCategory = categoryProductTableView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this category?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("DELETE FROM CategoryProduct WHERE IdCategory=?")) {
                        stmt.setInt(1, selectedCategory.getIdCategory());
                        stmt.executeUpdate();
                        loadCategoryProductData();
                    } catch (SQLServerException e) {
                        handleDeleteException(e, "category");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the category.");
                    }
                }
            }
        } else if (selectedTable.equals("ShowTimes")) {
            ShowTimes selectedShowTime = showTimesTableView.getSelectionModel().getSelectedItem();
            if (selectedShowTime != null) {
                if (isDateInPast(selectedShowTime.getDate()) || isTimeInPast(selectedShowTime.getStartTime(), selectedShowTime.getDate())) {
                    showAlert(Alert.AlertType.WARNING, "Cannot Delete", "Cannot delete showtime from the past.");
                    return;
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this showtime?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement("DELETE FROM ShowTimes WHERE IDShowTimes=?")) {
                        stmt.setInt(1, selectedShowTime.getIdShowTimes());
                        stmt.executeUpdate();
                        loadShowTimesData();
                    } catch (SQLServerException e) {
                        handleDeleteException(e, "showtime");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the showtime.");
                    }
                }
            }
        }

    }

    @FXML
    private void onSearch() {
        String searchTerm = searchField.getText().trim();
        String selectedTable = tableComboBox.getValue();

        switch (selectedTable) {
            case "Account":
                searchAccounts(searchTerm);
                break;
            case "Movie":
                searchMovies(searchTerm);
                break;
            case "Client":
                searchClients(searchTerm);
                break;
            case "CategoryProduct":
                searchCategoryProducts(searchTerm);
                break;
            case "ShowTimes":
                searchShowTimes(searchTerm);
                break;

        }
    }

    @FXML
    private void onTableSelectionChanged() {
        String selectedTable = tableComboBox.getValue();
        updateTableView(selectedTable);
        updateButtonStates();
        updateAddButtonVisibility(selectedTable);
    }

    private void updateAddButtonVisibility(String selectedTable) {
        addButton.setVisible(!selectedTable.equals("Client"));
    }

    private void showAlert(Alert.AlertType error, String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String extractTableName(String errorMessage) {
        int startIndex = errorMessage.indexOf("table \"") + 7;
        int endIndex = errorMessage.indexOf("\", column");
        if (startIndex != -1 && endIndex != -1) {
            return errorMessage.substring(startIndex, endIndex).replace("dbo.", "");
        }
        return "unknown";
    }

    private void handleDeleteException(SQLServerException e, String itemType) {
        if (e.getMessage().contains("The DELETE statement conflicted with the REFERENCE constraint")) {
            String referencedTable = extractTableName(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot delete this " + itemType + " as it is referenced in the " + referencedTable + " table.");
        } else {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the " + itemType + ".");
        }
    }

    private boolean validateAccountInput(TextField nameField, TextField emailField, PasswordField passwordField,
                                         TextField phoneNumberField, DatePicker dateOfBirthPicker, ComboBox<String> roleComboBox) {
        StringBuilder errorMessage = new StringBuilder();
        Node firstErrorField = null;

        if (nameField.getText().isEmpty()) {
            errorMessage.append("Name is required.\n");
            if (firstErrorField == null) firstErrorField = nameField;
        }
        if (emailField.getText().isEmpty()) {
            errorMessage.append("Email is required.\n");
            if (firstErrorField == null) firstErrorField = emailField;
        } else if (!isValidEmail(emailField.getText())) {
            errorMessage.append("Invalid email format.\n");
            if (firstErrorField == null) firstErrorField = emailField;
        }
        if (passwordField.getText().isEmpty()) {
            errorMessage.append("Password is required.\n");
            if (firstErrorField == null) firstErrorField = passwordField;
        }
        if (phoneNumberField.getText().isEmpty()) {
            errorMessage.append("Phone number is required.\n");
            if (firstErrorField == null) firstErrorField = phoneNumberField;
        } else if (!isValidPhoneNumber(phoneNumberField.getText())) {
            errorMessage.append("Invalid phone number format.\n");
            if (firstErrorField == null) firstErrorField = phoneNumberField;
        }
        if (dateOfBirthPicker.getValue() == null) {
            errorMessage.append("Date of birth is required.\n");
            if (firstErrorField == null) firstErrorField = dateOfBirthPicker;
        } else if (dateOfBirthPicker.getValue().isAfter(LocalDate.now())) {
            errorMessage.append("Date of birth cannot be in the future.\n");
            if (firstErrorField == null) firstErrorField = dateOfBirthPicker;
        }
        if (roleComboBox.getValue() == null) {
            errorMessage.append("Role is required.\n");
            if (firstErrorField == null) firstErrorField = roleComboBox;
        }

        if (errorMessage.length() > 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", errorMessage.toString());
            if (firstErrorField != null) {
                firstErrorField.requestFocus();
            }
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\d{10}$";  // Assumes a 10-digit phone number
        return phoneNumber.matches(phoneRegex);
    }

    private boolean validateMovieInput(TextField movieNameField, TextField authorField, TextField amountOfLimitField, TextField typeOfMovieField) {
        StringBuilder errorMessage = new StringBuilder();
        TextField firstErrorField = null;

        if (movieNameField.getText().isEmpty()) {
            errorMessage.append("Movie Name is required.\n");
            if (firstErrorField == null) firstErrorField = movieNameField;
        }
        if (authorField.getText().isEmpty()) {
            errorMessage.append("Author is required.\n");
            if (firstErrorField == null) firstErrorField = authorField;
        }
        if (amountOfLimitField.getText().isEmpty()) {
            errorMessage.append("Amount of Limit is required.\n");
            if (firstErrorField == null) firstErrorField = amountOfLimitField;
        } else if (!isValidNumber(amountOfLimitField.getText())) {
            errorMessage.append("Amount of Limit must be a valid number.\n");
            if (firstErrorField == null) firstErrorField = amountOfLimitField;
        }
        if (typeOfMovieField.getText().isEmpty()) {
            errorMessage.append("Type of Movie is required.\n");
            if (firstErrorField == null) firstErrorField = typeOfMovieField;
        }

        if (errorMessage.length() > 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", errorMessage.toString());
            if (firstErrorField != null) {
                firstErrorField.requestFocus();
            }
            return false;
        }
        return true;
    }

    private boolean isValidNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateClientInput(TextField emailField, TextField phoneNumberField, TextField nameField) {
        StringBuilder errorMessage = new StringBuilder();
        TextField firstErrorField = null;

        if (emailField.getText().isEmpty()) {
            errorMessage.append("Email is required.\n");
            if (firstErrorField == null) firstErrorField = emailField;
        } else if (!isValidEmail(emailField.getText())) {
            errorMessage.append("Invalid email format.\n");
            if (firstErrorField == null) firstErrorField = emailField;
        }
        if (phoneNumberField.getText().isEmpty()) {
            errorMessage.append("Phone number is required.\n");
            if (firstErrorField == null) firstErrorField = phoneNumberField;
        } else if (!isValidPhoneNumber(phoneNumberField.getText())) {
            errorMessage.append("Invalid phone number format.\n");
            if (firstErrorField == null) firstErrorField = phoneNumberField;
        }
        if (nameField.getText().isEmpty()) {
            errorMessage.append("Name is required.\n");
            if (firstErrorField == null) firstErrorField = nameField;
        }

        if (errorMessage.length() > 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", errorMessage.toString());
            if (firstErrorField != null) {
                firstErrorField.requestFocus();
            }
            return false;
        }
        return true;
    }

    private boolean validateCategoryProductInput(TextField categoryNameField) {
        if (categoryNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Category name is required.");
            categoryNameField.requestFocus();
            return false;
        }
        return true;
    }


    private void searchAccounts(String searchTerm) {
        String query = "SELECT * FROM Account WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            accountData.clear();
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("idAccount"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phoneNumber"),
                        rs.getString("dateOfBirth"),
                        rs.getString("role"),
                        rs.getString("avatar")
                );
                accountData.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error searching accounts.");
        }
    }

    private void searchMovies(String searchTerm) {
        String query = "SELECT * FROM Movie WHERE movieName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            movieData.clear();
            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("idMovie"),
                        rs.getString("movieName"),
                        rs.getString("author"),
                        rs.getInt("amoutOfLimit"),
                        rs.getString("typeOfMovie"),
                        rs.getString("imagesPoster"),
                        rs.getString("imagesBackdrop")
                );
                movieData.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error searching movies.");
        }
    }

    private void searchClients(String searchTerm) {
        String query = "SELECT * FROM Client WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            clientData.clear();
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("emailClient"),
                        rs.getString("phoneNumber"),
                        rs.getString("name")
                );
                clientData.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error searching clients.");
        }
    }

    private void searchShowTimes(String searchTerm) {
        String query = "SELECT * FROM ShowTimes WHERE CAST(IDShowTimes AS VARCHAR) LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            showTimesData.clear();
            while (rs.next()) {
                ShowTimes showTime = new ShowTimes(
                        rs.getInt("IDShowTimes"),
                        rs.getTime("StartTime"),
                        rs.getTime("EndTime"),
                        rs.getDate("Date"),
                        rs.getInt("IDMovie"),
                        rs.getInt("IDTheater")
                );
                showTimesData.add(showTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error searching showtimes.");
        }
    }

    private void searchCategoryProducts(String searchTerm) {
        String query = "SELECT * FROM CategoryProduct WHERE productName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            categoryProductData.clear();
            while (rs.next()) {
                CategoryProduct categoryProduct = new CategoryProduct(
                        rs.getInt("idCategory"),
                        rs.getString("productName")
                );
                categoryProductData.add(categoryProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error searching category products.");
        }
    }

    private String openFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    private Time validateTime(String timeString) {
        try {
            return Time.valueOf(LocalTime.parse(timeString));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format. Use HH:MM:SS.");
        }
    }

    private boolean isDateInPast(Date date) {
        return date.toLocalDate().isBefore(LocalDate.now());
    }

    private boolean isTimeInPast(Time time, Date date) {
        LocalDateTime dateTime = LocalDateTime.of(date.toLocalDate(), time.toLocalTime());
        return dateTime.isBefore(LocalDateTime.now());
    }

    private int fetchAmountOfLimit(int idMovie) throws SQLException {
        String query = "SELECT AmoutOfLimit FROM Movie WHERE IDMovie = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idMovie);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("AmoutOfLimit");
                } else {
                    throw new SQLException("Movie not found");
                }
            }
        }
    }

    private Time calculateEndTime(Time startTime, int amountOfLimit) {
        LocalTime startLocalTime = startTime.toLocalTime();
        LocalTime endLocalTime = startLocalTime.plus(amountOfLimit, ChronoUnit.MINUTES).plus(15, ChronoUnit.MINUTES);
        return Time.valueOf(endLocalTime);
    }

    private void populateComboBoxes(ComboBox<Integer> idMovieComboBox, ComboBox<Integer> idTheaterComboBox) {
        // Fetch available IDs from database and populate the ComboBoxes
        String movieQuery = "SELECT IDMovie FROM Movie";
        String theaterQuery = "SELECT IDTheater FROM Theater";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement movieStmt = conn.prepareStatement(movieQuery);
             PreparedStatement theaterStmt = conn.prepareStatement(theaterQuery);
             ResultSet movieRs = movieStmt.executeQuery();
             ResultSet theaterRs = theaterStmt.executeQuery()) {

            movieIds.clear();
            theaterIds.clear();

            while (movieRs.next()) {
                int idMovie = movieRs.getInt("IDMovie");
                movieIds.add(idMovie);
            }

            while (theaterRs.next()) {
                int idTheater = theaterRs.getInt("IDTheater");
                theaterIds.add(idTheater);
            }

            idMovieComboBox.setItems(movieIds);
            idTheaterComboBox.setItems(theaterIds);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load data from the database.");
        }
    }

}