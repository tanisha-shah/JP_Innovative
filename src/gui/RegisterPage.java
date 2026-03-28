package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import service.AuthService;
import model.Student;
import utils.Constants;

public class RegisterPage {

    private Stage stage;
    private AuthService authService = new AuthService();

    public RegisterPage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label title = new Label("Student Registration");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField nameField  = new TextField(); nameField.setPromptText("Full Name");
        TextField emailField = new TextField(); emailField.setPromptText("Email");
        PasswordField passField = new PasswordField(); passField.setPromptText("Password (min 6 chars)");

        ComboBox<String> branchBox = new ComboBox<>();
        branchBox.getItems().addAll(Constants.BRANCHES);
        branchBox.setPromptText("Select Branch");
        branchBox.setMaxWidth(Double.MAX_VALUE);

        TextField cgpaField = new TextField(); cgpaField.setPromptText("CGPA (e.g. 8.5)");

        Label msgLabel = new Label();
        msgLabel.setStyle("-fx-text-fill: red;");
        msgLabel.setWrapText(true);

        Button registerBtn = new Button("Register");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setOnAction(e -> {
            String name   = nameField.getText().trim();
            String email  = emailField.getText().trim();
            String pass   = passField.getText().trim();
            String branch = branchBox.getValue();
            String cgpaStr = cgpaField.getText().trim();

            // Simple validation
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || branch == null || cgpaStr.isEmpty()) {
                msgLabel.setText("All fields are required."); return;
            }
            if (pass.length() < 6) {
                msgLabel.setText("Password must be at least 6 characters."); return;
            }
            double cgpa;
            try {
                cgpa = Double.parseDouble(cgpaStr);
                if (cgpa < 0 || cgpa > 10) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                msgLabel.setText("CGPA must be a number between 0 and 10."); return;
            }

            Student s = authService.registerStudent(name, email, pass, branch, cgpa);
            if (s == null) {
                msgLabel.setStyle("-fx-text-fill: red;");
                msgLabel.setText("Email already registered. Please login.");
            } else {
                msgLabel.setStyle("-fx-text-fill: green;");
                msgLabel.setText("Registered! Waiting for admin verification. You can now login.");
            }
        });

        Button backBtn = new Button("← Back to Login");
        backBtn.setOnAction(e -> new LoginPage(stage).show());

        VBox form = new VBox(10,
            title, new Separator(),
            nameField, emailField, passField,
            new Label("Branch:"), branchBox,
            cgpaField, registerBtn, msgLabel,
            new Separator(), backBtn
        );
        form.setPadding(new Insets(30));
        form.setMaxWidth(380);

        StackPane root = new StackPane(form);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root, 500, 500));
        stage.setTitle("Register — Student");
        stage.show();
    }
}
