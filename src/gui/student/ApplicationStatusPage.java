package gui.student;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Application;
import model.Student;
import service.ApplicationService;

import java.util.List;

public class ApplicationStatusPage {

    private Stage stage;
    private Student student;
    private ApplicationService appService = new ApplicationService();

    public ApplicationStatusPage(Stage stage, Student student) {
        this.stage   = stage;
        this.student = student;
    }

    public void show() {

        Label title = new Label("My Application Status");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        List<Application> apps = appService.getByStudent(student.getId());

        if (apps.isEmpty()) {
            Label none = new Label("You have not applied to any drives yet.");
            none.setStyle("-fx-text-fill: grey;");
            Button back = new Button("← Back");
            back.setOnAction(e -> new StudentDashboard(stage, student).show());
            VBox root = new VBox(15, title, none, back);
            root.setPadding(new Insets(20));
            stage.setScene(new Scene(root, 480, 180));
            stage.setTitle("My Applications");
            stage.show();
            return;
        }

        TableView<Application> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Application, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getCompanyName()));

        TableColumn<Application, String> roleCol = new TableColumn<>("Job Role");
        roleCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getJobRole()));

        // Status column with color coding
        TableColumn<Application, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        statusCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    switch (status) {
                        case Application.SELECTED    -> setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                        case Application.SHORTLISTED -> setStyle("-fx-text-fill: blue;");
                        case Application.REJECTED    -> setStyle("-fx-text-fill: red;");
                        default                      -> setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });

        table.getColumns().addAll(companyCol, roleCol, statusCol);
        table.setItems(FXCollections.observableArrayList(apps));

        // Status legend
        Label legend = new Label(
            "🟢 Selected   🔵 Shortlisted   🔴 Rejected   ⚫ Applied (waiting)"
        );
        legend.setStyle("-fx-font-size: 11px; -fx-text-fill: #555;");

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new StudentDashboard(stage, student).show());

        VBox root = new VBox(12, title, table, legend, backBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 620, 420));
        stage.setTitle("My Application Status");
        stage.show();
    }
}

