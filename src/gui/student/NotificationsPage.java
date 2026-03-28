package gui.student;

import database.FileHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Student;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

/*
 * NotificationsPage — reads notifications.txt and shows alerts for this student.
 *
 * Notification file format (one line per notification):
 *   studentId|message|read(true/false)
 *
 * Example:
 *   STU-abc123|New drive posted by TCS — Software Engineer|false
 */
public class NotificationsPage {

    private Stage stage;
    private Student student;

    public NotificationsPage(Stage stage, Student student) {
        this.stage   = stage;
        this.student = student;
    }

    public void show() {

        Label title = new Label("My Notifications");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        List<String[]> myNotifs = loadNotifications();

        if (myNotifs.isEmpty()) {
            Label none = new Label("No notifications yet.");
            none.setStyle("-fx-text-fill: grey;");
            Button back = new Button("← Back");
            back.setOnAction(e -> new StudentDashboard(stage, student).show());
            VBox root = new VBox(15, title, none, back);
            root.setPadding(new Insets(20));
            stage.setScene(new Scene(root, 460, 180));
            stage.setTitle("Notifications");
            stage.show();
            return;
        }

        ListView<String> listView = new ListView<>();
        for (String[] n : myNotifs) {
            // n[0]=studentId, n[1]=message, n[2]=read
            String prefix = "false".equals(n[2]) ? "🔔 NEW  " : "       ";
            listView.getItems().add(prefix + n[1]);
        }

        Button markAllBtn = new Button("Mark All as Read");
        markAllBtn.setMaxWidth(Double.MAX_VALUE);
        markAllBtn.setOnAction(e -> {
            markAllRead();
            new NotificationsPage(stage, student).show();   // refresh
        });

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new StudentDashboard(stage, student).show());

        VBox root = new VBox(12, title, listView, markAllBtn, backBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 560, 420));
        stage.setTitle("Notifications");
        stage.show();
    }

    // Read notifications.txt and return only this student's notifications
    private List<String[]> loadNotifications() {
        List<String[]> result = new ArrayList<>();
        for (String line : FileHandler.readAll(Constants.NOTIFICATIONS)) {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 3 && parts[0].equals(student.getId())) {
                result.add(parts);
            }
        }
        return result;
    }

    // Mark all this student's notifications as read in the file
    private void markAllRead() {
        List<String> allLines = FileHandler.readAll(Constants.NOTIFICATIONS);
        List<String> updated  = new ArrayList<>();
        for (String line : allLines) {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 3 && parts[0].equals(student.getId())) {
                updated.add(parts[0] + "|" + parts[1] + "|true");
            } else {
                updated.add(line);
            }
        }
        FileHandler.writeAll(Constants.NOTIFICATIONS, updated);
    }
}

