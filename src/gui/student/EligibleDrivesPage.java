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

import java.util.ArrayList;
import java.util.List;

public class EligibleDrivesPage {

    private Stage stage;
    private Student student;
    private DriveService driveService = new DriveService();

    public EligibleDrivesPage(Stage stage, Student student) {
        this.stage   = stage;
        this.student = student;
    }

    public void show() {

        Label title = new Label("Eligible Drives For You");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Label info = new Label("Showing drives where your CGPA (" + student.getCgpa()
            + ") and branch (" + student.getBranch() + ") match the requirements.");
        info.setWrapText(true);
        info.setStyle("-fx-text-fill: #555;");

        List<Drive> eligible = getEligibleDrives();

        if (eligible.isEmpty()) {
            Label none = new Label("No eligible drives found at the moment.");
            none.setStyle("-fx-text-fill: red;");
            Button back = new Button("← Back");
            back.setOnAction(e -> new StudentDashboard(stage, student).show());
            VBox root = new VBox(15, title, info, none, back);
            root.setPadding(new Insets(20));
            stage.setScene(new Scene(root, 520, 220));
            stage.setTitle("Eligible Drives");
            stage.show();
            return;
        }

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
        table.setItems(FXCollections.observableArrayList(eligible));

        Label detailLabel = new Label("Click a drive to see skill requirements.");
        detailLabel.setWrapText(true);
        detailLabel.setStyle("-fx-text-fill: #444;");

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                String skills = selected.getRequiredSkills().isEmpty()
                    ? "No specific skills required"
                    : String.join(", ", selected.getRequiredSkills());
                detailLabel.setText("Skills Required: " + skills);
            }
        });

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new StudentDashboard(stage, student).show());

        VBox root = new VBox(12, title, info, table, detailLabel, backBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 680, 460));
        stage.setTitle("Eligible Drives");
        stage.show();
    }

    // A drive is eligible if: student CGPA >= minCgpa AND branch matches (or no branch filter)
    private List<Drive> getEligibleDrives() {
        List<Drive> result = new ArrayList<>();
        for (Drive d : driveService.getOpenDrives()) {
            boolean cgpaOk   = student.getCgpa() >= d.getMinCgpa();
            boolean branchOk = d.getRequiredBranches().isEmpty()
                             || d.getRequiredBranches().contains(student.getBranch());
            if (cgpaOk && branchOk) result.add(d);
        }
        return result;
    }
}
