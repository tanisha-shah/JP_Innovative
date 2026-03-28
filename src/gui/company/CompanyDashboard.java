// File: src/gui/company/CompanyDashboard.java
// Package: gui.company
// Description: Company Dashboard UI for the Smart Campus Placement System.

package gui.company;

import model.Company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CompanyDashboard class.
 * Displays the main dashboard for a logged-in company.
 *
 * UI Components:
 * - Welcome header with company name
 * - Navigation buttons:
 *   → Post a Drive
 *   → View Applicants
 * - Logout button
 *
 * Extends JFrame to act as a standalone window.
 */
public class CompanyDashboard extends JFrame {

    // ─── Logged-in Company ────────────────────────────────────

    // The currently logged-in company passed from LoginPage
    private Company loggedInCompany;

    // ─── UI Components ────────────────────────────────────────

    private JButton btnPostDrive;       // Navigate to PostDrivePage
    private JButton btnViewApplicants;  // Navigate to ViewApplicantsPage
    private JButton btnLogout;          // Logout and return to LoginPage
    private JLabel  statusLabel;        // Bottom status bar label

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Accepts the logged-in Company object to personalize the dashboard.
     *
     * @param company The currently logged-in Company
     */
    public CompanyDashboard(Company company) {
        this.loggedInCompany = company;
        initWindow();
        initComponents();
        setVisible(true);
    }

    // ─── Window Setup ─────────────────────────────────────────

    /**
     * Configures the main JFrame window properties.
     */
    private void initWindow() {
        setTitle("Company Dashboard — Smart Campus Placement System");
        setSize(500, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // ─── UI Components Setup ──────────────────────────────────

    /**
     * Builds and arranges all UI components on the Company Dashboard.
     */
    private void initComponents() {

        // ── Main Panel ──
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 255, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ── Header Panel (NORTH) ──
        JPanel headerPanel = new JPanel(new GridLayout(3, 1));
        headerPanel.setBackground(new Color(240, 255, 245));

        JLabel titleLabel = new JLabel(
                "Smart Campus Placement System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(33, 97, 140));

        // Personalized welcome using company name
        String companyName = (loggedInCompany != null)
                ? loggedInCompany.getName() : "Company";
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + companyName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setForeground(new Color(39, 174, 96));

        JLabel subLabel = new JLabel(
                "Manage your placement drives and applicants.",
                SwingConstants.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(welcomeLabel);
        headerPanel.add(subLabel);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // ── Info Panel — company details ──
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        infoPanel.setBackground(new Color(210, 245, 225));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 220, 190)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        String email = (loggedInCompany != null)
                ? loggedInCompany.getEmail() : "N/A";
        String id    = (loggedInCompany != null)
                ? loggedInCompany.getId()    : "N/A";

        JLabel emailInfo = new JLabel(
                "Email: " + email, SwingConstants.CENTER);
        JLabel idInfo    = new JLabel(
                "Company ID: " + id, SwingConstants.CENTER);

        emailInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        idInfo.setFont(new Font("Arial",    Font.PLAIN, 12));

        infoPanel.add(emailInfo);
        infoPanel.add(idInfo);

        // Combine header and info into north panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(240, 255, 245));
        northPanel.add(headerPanel, BorderLayout.NORTH);
        northPanel.add(infoPanel,   BorderLayout.SOUTH);

        // ── Button Panel (CENTER) — 2 large buttons ──
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        buttonPanel.setBackground(new Color(240, 255, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        btnPostDrive = createDashboardButton(
                "📢  Post a New Drive",      new Color(39, 174, 96));
        btnViewApplicants = createDashboardButton(
                "👥  View Applicants",        new Color(52, 152, 219));

        buttonPanel.add(btnPostDrive);
        buttonPanel.add(btnViewApplicants);

        // ── South Panel — Logout + Status ──
        JPanel southPanel = new JPanel(new BorderLayout(0, 5));
        southPanel.setBackground(new Color(240, 255, 245));

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogout.setBackground(new Color(149, 165, 166));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0));

        statusLabel = new JLabel(
                "Logged in as Company", SwingConstants.CENTER);
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

        // Post Drive button
        btnPostDrive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: Post a New Drive...");
                System.out.println("[CompanyDashboard] Post Drive clicked.");
                // TODO: new PostDrivePage(loggedInCompany); dispose();
            }
        });

        // View Applicants button
        btnViewApplicants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: View Applicants...");
                System.out.println("[CompanyDashboard] View Applicants clicked.");
                // TODO: new ViewApplicantsPage(loggedInCompany); dispose();
            }
        });

        // Logout button
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("[CompanyDashboard] Logout clicked.");
                statusLabel.setText("Logged out successfully.");
                // TODO: new LoginPage(); dispose();
                JOptionPane.showMessageDialog(
                        CompanyDashboard.this,
                        "You have been logged out successfully.",
                        "Logout",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    // ─── Main Method (for standalone testing) ─────────────────

    /**
     * Main method to test CompanyDashboard independently.
     * Creates a dummy Company object for UI testing.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Company dummyCompany = new Company(
                        "COM001", "Google",
                        "google@hire.com", "pass123"
                );
                new CompanyDashboard(dummyCompany);
            }
        });
    }
}