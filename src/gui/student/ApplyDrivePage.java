package gui.student;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Drive;
import model.Student;
import service.ApplicationService;
import service.DriveService;

import java.util.ArrayList;
import java.util.List;

public class ApplyDrivePage {

    private Stage stage;
    private Student student;
    private DriveService driveService     = new DriveService();
    private ApplicationService appService = new ApplicationService();

    public ApplyDrivePage(Stage stage, Student student) {
        this.stage   = stage;
        this.student = student;
    }

    public void show() {

        Label title = new Label("Apply to a Drive");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        // Only show drives where student is eligible (CGPA + branch)
        List<Drive> eligible = getEligibleDrives();

        if (eligible.isEmpty()) {
            Label none = new Label(
                "No eligible drives found.\n" +
                "Your CGPA: " + student.getCgpa() + "  |  Branch: " + student.getBranch());
            none.setWrapText(true);
            none.setStyle("-fx-text-fill: red;");
            Button back = new Button("← Back");
            back.setOnAction(e -> new StudentDashboard(stage, student).show());
            VBox root = new VBox(15, title, none, back);
            root.setPadding(new Insets(20));
            stage.setScene(new Scene(root, 500, 200));
            stage.setTitle("Apply to Drive");
            stage.show();
            return;
        }

        // List of drives to pick from
        ListView<String> driveListView = new ListView<>();
        for (Drive d : eligible) {
            driveListView.getItems().add(
                d.getCompanyName() + "  —  " + d.getJobRole()
                + "  |  CTC: " + d.getCtcLpa() + " LPA"
                + "  |  Min CGPA: " + d.getMinCgpa()
            );
        }
        driveListView.getSelectionModel().selectFirst();
        driveListView.setPrefHeight(180);

        Label msgLabel = new Label();
        msgLabel.setWrapText(true);

        Button applyBtn = new Button("Apply Now");
        applyBtn.setMaxWidth(Double.MAX_VALUE);
        applyBtn.setOnAction(e -> {
            int idx = driveListView.getSelectionModel().getSelectedIndex();
            if (idx < 0) {
                msgLabel.setStyle("-fx-text-fill: red;");
                msgLabel.setText("Please select a drive first.");
                return;
            }

            Drive selected = eligible.get(idx);

            boolean success = appService.apply(
                student.getId(), student.getName(),
                selected.getId(), selected.getCompanyName(), selected.getJobRole()
            );

            if (success) {
                msgLabel.setStyle("-fx-text-fill: green;");
                msgLabel.setText("Applied successfully to " + selected.getCompanyName() + "!");
            } else {
                msgLabel.setStyle("-fx-text-fill: orange;");
                msgLabel.setText("You have already applied to this drive.");
            }
        });

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new StudentDashboard(stage, student).show());

        VBox root = new VBox(12,
                title,
                new Label("Select a drive to apply:"),
                driveListView,
                applyBtn,
                msgLabel,
                new Separator(),
                backBtn
        );
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Apply to Drive");
        stage.show();
    }

    // CGPA >= minCgpa AND branch matches (or no branch restriction)
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
