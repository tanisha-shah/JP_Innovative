package gui.student;

import database.FileHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Student;
import service.ReadinessService;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

/*
 * ResumeScorePage — shows the student's profile/resume score out of 100.
 *
 * Scoring:
 *   CGPA      → max 40 pts   (cgpa / 10) * 40
 *   Skills    → max 40 pts   5 pts each, capped at 8 skills
 *   Profile   → max 20 pts   branch filled = 10, at least 1 skill = 10
 */
public class ResumeScorePage {

    private Stage stage;
    private Student student;
    private ReadinessService readinessSvc = new ReadinessService();

    public ResumeScorePage(Stage stage, Student student) {
        this.stage   = stage;
        this.student = student;
    }

    public void show() {

        Label title = new Label("Resume Score");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        int score    = readinessSvc.getScore(student);
        String label = readinessSvc.getLabel(score);

        Label scoreLabel = new Label("Your Score:  " + score + " / 100");
        scoreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ProgressBar bar = new ProgressBar(score / 100.0);
        bar.setMaxWidth(Double.MAX_VALUE);
        bar.setPrefHeight(22);

        if      (score >= 80) bar.setStyle("-fx-accent: green;");
        else if (score >= 60) bar.setStyle("-fx-accent: dodgerblue;");
        else if (score >= 40) bar.setStyle("-fx-accent: orange;");
        else                  bar.setStyle("-fx-accent: red;");

        Label resultLabel = new Label(label);
        resultLabel.setWrapText(true);
        resultLabel.setStyle("-fx-font-size: 13px;");

        // Score breakdown
        int cgpaScore    = (int) ((student.getCgpa() / 10.0) * 40);
        int skillScore   = Math.min(student.getSkills().size(), 8) * 5;
        int profileScore = (student.getBranch() != null && !student.getBranch().isEmpty() ? 10 : 0)
                         + (!student.getSkills().isEmpty() ? 10 : 0);

        Label breakdownLabel = new Label(
            "Breakdown:\n" +
            "  CGPA score       :  " + cgpaScore   + " / 40\n" +
            "  Skills score     :  " + skillScore   + " / 40  (" + student.getSkills().size() + " skill/s added)\n" +
            "  Profile complete :  " + profileScore + " / 20"
        );
        breakdownLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");

        // ── Update Skills section ──────────────────────────────
        Label updateTitle = new Label("Update Your Skills:");
        updateTitle.setStyle("-fx-font-weight: bold;");

        FlowPane skillPane = new FlowPane(8, 8);
        List<CheckBox> checkBoxes = new ArrayList<>();
        for (String sk : Constants.SKILLS) {
            CheckBox cb = new CheckBox(sk);
            cb.setSelected(student.getSkills().contains(sk));
            checkBoxes.add(cb);
            skillPane.getChildren().add(cb);
        }

        Label saveMsgLabel = new Label();

        Button saveBtn = new Button("Save Skills & Refresh Score");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setOnAction(e -> {
            List<String> selected = new ArrayList<>();
            for (CheckBox cb : checkBoxes) {
                if (cb.isSelected()) selected.add(cb.getText());
            }
            student.setSkills(selected);
            FileHandler.updateLine(Constants.STUDENTS, student.getId(), student.toFileString());
            saveMsgLabel.setStyle("-fx-text-fill: green;");
            saveMsgLabel.setText("Skills saved!");
            // Refresh the page to show updated score
            new ResumeScorePage(stage, student).show();
        });

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new StudentDashboard(stage, student).show());

        VBox root = new VBox(12,
                title,
                new Separator(),
                scoreLabel,
                bar,
                resultLabel,
                new Separator(),
                breakdownLabel,
                new Separator(),
                updateTitle,
                skillPane,
                saveBtn,
                saveMsgLabel,
                new Separator(),
                backBtn
        );
        root.setPadding(new Insets(20));

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);

        stage.setScene(new Scene(scroll, 620, 560));
        stage.setTitle("Resume Score");
        stage.show();
    }
}
