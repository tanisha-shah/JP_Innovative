// File: src/gui/LoginPage.java
// Package: gui
// Description: Login Page UI for the Smart Campus Placement System.

package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginPage class.
 * Displays the main login screen of the Smart Campus Placement System.
 *
 * UI Components:
 * - Email input field
 * - Password input field
 * - Role selection dropdown (Student, Company, Admin)
 * - Login button
 * - Register button
 *
 * On button click: prints input values to console (no backend yet).
 * Extends JFrame to act as a standalone window.
 */
public class LoginPage extends JFrame {

    // ─── UI Components ────────────────────────────────────────

    private JTextField     emailField;     // Input field for email
    private JPasswordField passwordField;  // Input field for password (masked)
    private JComboBox<String> roleDropdown; // Dropdown for role selection
    private JButton        loginButton;    // Button to trigger login
    private JButton        registerButton; // Button to navigate to register page
    private JLabel         statusLabel;    // Label to show success/error messages

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Sets up and displays the Login Page window.
     */
    public LoginPage() {
        initWindow();    // Configure the JFrame window
        initComponents(); // Build and add all UI components
        setVisible(true); // Make the window visible
    }

    // ─── Window Setup ─────────────────────────────────────────

    /**
     * Configures the main JFrame window properties.
     * Sets title, size, close operation, and centering.
     */
    private void initWindow() {
        setTitle("Smart Campus Placement System — Login");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);   // Center the window on screen
        setResizable(false);           // Fixed window size
    }

    // ─── UI Components Setup ──────────────────────────────────

    /**
     * Builds and arranges all UI components on the Login Page.
     * Uses a main BorderLayout with a centered form panel.
     */
    private void initComponents() {

        // ── Main Panel (BorderLayout) ──
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ── Title Label (NORTH) ──
        JLabel titleLabel = new JLabel(
                "Smart Campus Placement System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(33, 97, 140));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        JLabel subtitleLabel = new JLabel("Please login to continue",
                SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);

        // Wrap title and subtitle in a north panel
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(new Color(245, 245, 250));
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        // ── Form Panel (CENTER) — GridLayout with labels and fields ──
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBackground(new Color(245, 245, 250));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Email row
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 13));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        // Password row
        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        // Role row
        JLabel roleLabel = new JLabel("Login as :");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        String[] roles = {"Student", "Company", "Admin"};
        roleDropdown = new JComboBox<>(roles);
        roleDropdown.setFont(new Font("Arial", Font.PLAIN, 13));
        roleDropdown.setBackground(Color.WHITE);

        // Status label row (spans across form)
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.RED);

        // Add rows to form panel
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(roleLabel);
        formPanel.add(roleDropdown);
        formPanel.add(new JLabel("")); // Empty placeholder
        formPanel.add(statusLabel);

        // ── Button Panel (SOUTH) ──
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(new Color(245, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 13));
        loginButton.setBackground(new Color(33, 97, 140));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Register button
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 13));
        registerButton.setBackground(new Color(39, 174, 96));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // ── Add all panels to main panel ──
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel,  BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ── Add main panel to frame ──
        add(mainPanel);

        // ── Attach button listeners ──
        attachListeners();
    }

    // ─── Event Listeners ──────────────────────────────────────

    /**
     * Attaches ActionListeners to the Login and Register buttons.
     * Currently prints input values to the console for testing.
     */
    private void attachListeners() {

        // ── Login Button Listener ──
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // ── Register Button Listener ──
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
    }

    // ─── Button Handlers ──────────────────────────────────────

    /**
     * Handles the Login button click.
     * Reads input values, validates they are not empty,
     * and prints them to the console.
     */
    private void handleLogin() {

        // Read input values from fields
        String email    = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role     = (String) roleDropdown.getSelectedItem();

        // ── Basic validation: check for empty fields ──
        if (email.isEmpty()) {
            showError("Please enter your email address.");
            return;
        }

        if (password.isEmpty()) {
            showError("Please enter your password.");
            return;
        }

        // ── Print values to console (no backend yet) ──
        System.out.println("─────────────────────────────────");
        System.out.println("[LoginPage] Login Attempt:");
        System.out.println("  Email    : " + email);
        System.out.println("  Password : " + password);
        System.out.println("  Role     : " + role);
        System.out.println("─────────────────────────────────");

        // ── Show temporary success message on UI ──
        showSuccess("Login clicked! Role: " + role + " | Email: " + email);
    }

    /**
     * Handles the Register button click.
     * Prints a message to the console and shows a UI message.
     * Will open RegisterPage when backend is connected.
     */
    private void handleRegister() {

        String role = (String) roleDropdown.getSelectedItem();

        // Print to console
        System.out.println("─────────────────────────────────");
        System.out.println("[LoginPage] Register button clicked.");
        System.out.println("  Selected Role: " + role);
        System.out.println("  → Will open RegisterPage (to be connected)");
        System.out.println("─────────────────────────────────");

        // Show message on UI
        showSuccess("Register clicked! Opening registration for: " + role);

        // TODO: Open RegisterPage when ready
        // new RegisterPage();
        // dispose();
    }

    // ─── UI Utility Methods ───────────────────────────────────

    /**
     * Displays an error message on the status label in red.
     *
     * @param message The error message to display
     */
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }

    /**
     * Displays a success message on the status label in green.
     *
     * @param message The success message to display
     */
    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(new Color(39, 174, 96));
    }

    // ─── Main Method (for standalone testing) ─────────────────

    /**
     * Main method to test LoginPage independently.
     * Launches the Login Page in the Swing Event Dispatch Thread.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPage();
            }
        });
    }
}