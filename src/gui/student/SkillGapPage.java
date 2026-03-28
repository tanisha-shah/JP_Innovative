package gui.student;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Drive;
import model.Student;
import service.DriveService;
import service.SkillGapService;

import java.util.List;

public class SkillGapPage {

    private Stage stage;
    private Student student;
    private DriveService driveService     = new DriveService();
    private SkillGapService skillGapSvc   = new SkillGapService();

    public SkillGapPage(Stage stage, Student student) {
        this.stage   = stage;
        this.student = student;
    }

    public void show() {

        Label title = new Label("Skill Gap Analysis");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        // Show student's current skills
        String mySkills = student.getSkills().isEmpty()
            ? "None added yet  (go to Placement Readiness to add skills)"
            : String.join(", ", student.getSkills());

        Label mySkillsLabel = new Label("Your Skills:  " + mySkills);
        mySkillsLabel.setWrapText(true);
        mySkillsLabel.setStyle("-fx-text-fill: #333;");

        Label instruction = new Label("Select a drive below to see which skills you have and which you are missing:");
        instruction.setWrapText(true);

        // Drive dropdown
        List<Drive> drives = driveService.getOpenDrives();
        ComboBox<String> driveBox = new ComboBox<>();
        for (Drive d : drives) {
            driveBox.getItems().add(d.getCompanyName() + " — " + d.getJobRole());
        }
        driveBox.setPromptText("Select a drive");
        driveBox.setMaxWidth(Double.MAX_VALUE);

        // Result labels
        ProgressBar bar = new ProgressBar(0);
        bar.setMaxWidth(Double.MAX_VALUE);
        bar.setPrefHeight(18);

        Label percentLabel  = new Label();
        Label matchedLabel  = new Label();
        Label missingLabel  = new Label();
        percentLabel.setStyle("-fx-font-weight: bold;");
        matchedLabel.setStyle("-fx-text-fill: green;");
        missingLabel.setStyle("-fx-text-fill: red;");
        matchedLabel.setWrapText(true);
        missingLabel.setWrapText(true);

        Button analyzeBtn = new Button("Analyze");
        analyzeBtn.setMaxWidth(Double.MAX_VALUE);
        analyzeBtn.setOnAction(e -> {
            int idx = driveBox.getSelectionModel().getSelectedIndex();
            if (idx < 0) {
                percentLabel.setText("Please select a drive first.");
                return;
            }

            Drive selected = drives.get(idx);

            if (selected.getRequiredSkills().isEmpty()) {
                bar.setProgress(1.0);
                percentLabel.setText("Match: 100%");
                matchedLabel.setText("✅ This drive has no specific skill requirements.");
                missingLabel.setText("");
                return;
            }

            List<String> matched = skillGapSvc.getMatchedSkills(student, selected);
            List<String> missing = skillGapSvc.getMissingSkills(student, selected);
            int percent          = skillGapSvc.getMatchPercent(student, selected);

            bar.setProgress(percent / 100.0);
            percentLabel.setText("Skill Match: " + percent + "%");

            matchedLabel.setText("✅ Skills you have:  "
                + (matched.isEmpty() ? "None" : String.join(", ", matched)));

            missingLabel.setText("❌ Skills you are missing:  "
                + (missing.isEmpty() ? "None — you match all required skills!" : String.join(", ", missing)));
        });

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new StudentDashboard(stage, student).show());

        VBox root = new VBox(12,
                title,
                new Separator(),
                mySkillsLabel,
                new Separator(),
                instruction,
                driveBox,
                analyzeBtn,
                new Separator(),
                bar,
                percentLabel,
                matchedLabel,
                missingLabel,
                new Separator(),
                backBtn
        );
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 600, 480));
        stage.setTitle("Skill Gap Analysis");
        stage.show();
    }
}
