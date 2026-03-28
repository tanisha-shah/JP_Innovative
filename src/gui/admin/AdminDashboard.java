// File: src/gui/admin/AdminDashboard.java
// Package: gui.admin
// Description: Admin Dashboard UI for the Smart Campus Placement System.

package gui.admin;

import model.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AdminDashboard class.
 * Displays the main dashboard for a logged-in administrator.
 *
 * UI Components:
 * - Welcome header with admin name
 * - Navigation buttons:
 *   → Manage Students
 *   → Manage Companies
 * - Logout button
 *
 * Extends JFrame to act as a standalone window.
 */
public class AdminDashboard extends JFrame {

    // ─── Logged-in Admin ──────────────────────────────────────

    // The currently logged-in admin passed from LoginPage
    private Admin loggedInAdmin;

    // ─── UI Components ────────────────────────────────────────

    private JButton btnManageStudents;   // Navigate to ManageStudentsPage
    private JButton btnManageCompanies;  // Navigate to ManageCompaniesPage
    private JButton btnLogout;           // Logout and return to LoginPage
    private JLabel  statusLabel;         // Bottom status bar label

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Accepts the logged-in Admin object to personalize the dashboard.
     *
     * @param admin The currently logged-in Admin
     */
    public AdminDashboard(Admin admin) {
        this.loggedInAdmin = admin;
        initWindow();
        initComponents();
        setVisible(true);
    }

    // ─── Window Setup ─────────────────────────────────────────

    /**
     * Configures the main JFrame window properties.
     */
    private void initWindow() {
        setTitle("Admin Dashboard — Smart Campus Placement System");
        setSize(500, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // ─── UI Components Setup ──────────────────────────────────

    /**
     * Builds and arranges all UI components on the Admin Dashboard.
     */
    private void initComponents() {

        // ── Main Panel ──
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 245, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ── Header Panel (NORTH) ──
        JPanel headerPanel = new JPanel(new GridLayout(3, 1));
        headerPanel.setBackground(new Color(255, 245, 240));

        JLabel titleLabel = new JLabel(
                "Smart Campus Placement System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(33, 97, 140));

        // Personalized welcome using admin name
        String adminName = (loggedInAdmin != null)
                ? loggedInAdmin.getName() : "Admin";
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + adminName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setForeground(new Color(192, 57, 43));

        JLabel subLabel = new JLabel(
                "Manage students and companies from here.",
                SwingConstants.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(welcomeLabel);
        headerPanel.add(subLabel);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // ── Info Panel — admin details ──
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        infoPanel.setBackground(new Color(250, 220, 210));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 180, 170)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        String email = (loggedInAdmin != null)
                ? loggedInAdmin.getEmail() : "N/A";
        String id    = (loggedInAdmin != null)
                ? loggedInAdmin.getId()    : "N/A";

        JLabel emailInfo = new JLabel(
                "Email: " + email, SwingConstants.CENTER);
        JLabel idInfo    = new JLabel(
                "Admin ID: " + id, SwingConstants.CENTER);

        emailInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        idInfo.setFont(new Font("Arial",    Font.PLAIN, 12));

        infoPanel.add(emailInfo);
        infoPanel.add(idInfo);

        // Combine header and info into north panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(255, 245, 240));
        northPanel.add(headerPanel, BorderLayout.NORTH);
        northPanel.add(infoPanel,   BorderLayout.SOUTH);

        // ── Button Panel (CENTER) — 2 large buttons ──
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        buttonPanel.setBackground(new Color(255, 245, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        btnManageStudents = createDashboardButton(
                "🎓  Manage Students",    new Color(33, 97, 140));
        btnManageCompanies = createDashboardButton(
                "🏢  Manage Companies",   new Color(192, 57, 43));

        buttonPanel.add(btnManageStudents);
        buttonPanel.add(btnManageCompanies);

        // ── South Panel — Logout + Status ──
        JPanel southPanel = new JPanel(new BorderLayout(0, 5));
        southPanel.setBackground(new Color(255, 245, 240));

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogout.setBackground(new Color(149, 165, 166));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0));

        statusLabel = new JLabel(
                "Logged in as Admin", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        statusLabel.setForeground(Color.GRAY);

        southPanel.add(btnLogout,   BorderLayout.CENTER);
        southPanel.add(statusLabel, BorderLayout.SOUTH);

        // ── Assemble Main Panel ──
        mainPanel.add(northPanel,  BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel,  BorderLayout.SOUTH);

        add(mainPanel);

        // ── Attach Listeners ──
        attachListeners();
    }

    // ─── Button Factory ───────────────────────────────────────

    /**
     * Creates a styled dashboard button with consistent formatting.
     *
     * @param text  Button label text
     * @param color Background color for the button
     * @return Styled JButton instance
     */
    private JButton createDashboardButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        button.setOpaque(true);
        return button;
    }

    // ─── Event Listeners ──────────────────────────────────────

    /**
     * Attaches ActionListeners to all dashboard buttons.
     */
    private void attachListeners() {

        // Manage Students button
        btnManageStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: Manage Students...");
                System.out.println("[AdminDashboard] Manage Students clicked.");
                // TODO: new ManageStudentsPage(loggedInAdmin); dispose();
            }
        });

        // Manage Companies button
        btnManageCompanies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: Manage Companies...");
                System.out.println("[AdminDashboard] Manage Companies clicked.");
                // TODO: new ManageCompaniesPage(loggedInAdmin); dispose();
            }
        });

        // Logout button
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("[AdminDashboard] Logout clicked.");
                statusLabel.setText("Logged out successfully.");
                // TODO: new LoginPage(); dispose();
                JOptionPane.showMessageDialog(
                        AdminDashboard.this,
                        "You have been logged out successfully.",
                        "Logout",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    // ─── Main Method (for standalone testing) ─────────────────

    /**
     * Main method to test AdminDashboard independently.
     * Creates a dummy Admin object for UI testing.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Admin dummyAdmin = new Admin(
                        "ADM001", "Admin",
                        "admin@campus.com", "admin123"
                );
                new AdminDashboard(dummyAdmin);
            }
        });
    }
}