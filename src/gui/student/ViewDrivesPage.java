package gui.student;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Drive;
import model.Student;
import service.DriveService;

import java.util.List;

public class ViewDrivesPage {

    private Stage stage;
    private Student student;
    private DriveService driveService = new DriveService();

    public ViewDrivesPage(Stage stage, Student student) {
        this.stage   = stage;
        this.student = student;
    }

    public void show() {

        Label title = new Label("All Open Drives");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        // Table setup
        TableView<Drive> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Drive, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getCompanyName()));

        TableColumn<Drive, String> roleCol = new TableColumn<>("Job Role");
        roleCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getJobRole()));

        TableColumn<Drive, String> cgpaCol = new TableColumn<>("Min CGPA");
        cgpaCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                String.valueOf(data.getValue().getMinCgpa())));

        TableColumn<Drive, String> ctcCol = new TableColumn<>("CTC (LPA)");
        ctcCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                String.valueOf(data.getValue().getCtcLpa())));

        table.getColumns().addAll(companyCol, roleCol, cgpaCol, ctcCol);

        List<Drive> drives = driveService.getOpenDrives();
        table.setItems(FXCollections.observableArrayList(drives));

        // Detail label — shown when a drive is selected
        Label detailLabel = new Label("Click a drive to see required branches and skills.");
        detailLabel.setWrapText(true);
        detailLabel.setStyle("-fx-text-fill: #444;");

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                String branches = selected.getRequiredBranches().isEmpty()
                    ? "All branches"
                    : String.join(", ", selected.getRequiredBranches());
                String skills = selected.getRequiredSkills().isEmpty()
                    ? "No specific skills required"
                    : String.join(", ", selected.getRequiredSkills());
                detailLabel.setText("Branches: " + branches + "\nSkills Required: " + skills);
            }
        });

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new StudentDashboard(stage, student).show());

        VBox root = new VBox(12, title, table, detailLabel, backBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 680, 460));
        stage.setTitle("View All Drives");
        stage.show();
    }
}
