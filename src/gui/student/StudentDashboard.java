// File: src/gui/student/StudentDashboard.java
// Package: gui.student
// Description: Student Dashboard UI for the Smart Campus Placement System.

package gui.student;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * StudentDashboard class.
 * Displays the main dashboard for a logged-in student.
 *
 * UI Components:
 * - Welcome header with student name
 * - Navigation buttons:
 *   → View Drives
 *   → Eligible Drives
 *   → Skill Gap
 *   → Resume Score
 *   → My Applications
 *   → Notifications
 * - Logout button
 *
 * Extends JFrame to act as a standalone window.
 */
public class StudentDashboard extends JFrame {

    // ─── Logged-in Student ────────────────────────────────────

    // The currently logged-in student passed from LoginPage
    private Student loggedInStudent;

    // ─── UI Components ────────────────────────────────────────

    private JButton btnViewDrives;      // Navigate to ViewDrivesPage
    private JButton btnEligibleDrives;  // Navigate to EligibleDrivesPage
    private JButton btnSkillGap;        // Navigate to SkillGapPage
    private JButton btnResumeScore;     // Navigate to ResumeScorePage
    private JButton btnApplications;    // Navigate to ApplicationStatusPage
    private JButton btnNotifications;   // Navigate to NotificationsPage
    private JButton btnLogout;          // Logout and return to LoginPage
    private JLabel  statusLabel;        // Bottom status bar label

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Accepts the logged-in Student object to personalize the dashboard.
     *
     * @param student The currently logged-in Student
     */
    public StudentDashboard(Student student) {
        this.loggedInStudent = student;
        initWindow();
        initComponents();
        setVisible(true);
    }

    // ─── Window Setup ─────────────────────────────────────────

    /**
     * Configures the main JFrame window properties.
     */
    private void initWindow() {
        setTitle("Student Dashboard — Smart Campus Placement System");
        setSize(520, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // ─── UI Components Setup ──────────────────────────────────

    /**
     * Builds and arranges all UI components on the Student Dashboard.
     */
    private void initComponents() {

        // ── Main Panel ──
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ── Header Panel (NORTH) ──
        JPanel headerPanel = new JPanel(new GridLayout(3, 1));
        headerPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel(
                "Smart Campus Placement System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(33, 97, 140));

        // Personalized welcome message using student's name
        String studentName = (loggedInStudent != null)
                ? loggedInStudent.getName() : "Student";
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + studentName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setForeground(new Color(39, 174, 96));

        JLabel subLabel = new JLabel(
                "What would you like to do today?", SwingConstants.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(welcomeLabel);
        headerPanel.add(subLabel);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // ── Info Panel — show student details ──
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        infoPanel.setBackground(new Color(220, 235, 250));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 240)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Show branch, CGPA info if student is available
        String branch = (loggedInStudent != null) ? loggedInStudent.getBranch() : "N/A";
        String cgpa   = (loggedInStudent != null)
                ? String.valueOf(loggedInStudent.getCgpa()) : "N/A";
        String skills = (loggedInStudent != null && loggedInStudent.getSkills() != null)
                ? String.valueOf(loggedInStudent.getSkills().size()) + " skills" : "0 skills";

        JLabel branchInfo = new JLabel("Branch: " + branch, SwingConstants.CENTER);
        JLabel cgpaInfo   = new JLabel("CGPA: "   + cgpa,   SwingConstants.CENTER);
        JLabel skillInfo  = new JLabel(skills,               SwingConstants.CENTER);

        branchInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        cgpaInfo.setFont(new Font("Arial",   Font.PLAIN, 12));
        skillInfo.setFont(new Font("Arial",  Font.PLAIN, 12));

        infoPanel.add(branchInfo);
        infoPanel.add(cgpaInfo);
        infoPanel.add(skillInfo);

        // Combine header and info into north panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(240, 248, 255));
        northPanel.add(headerPanel, BorderLayout.NORTH);
        northPanel.add(infoPanel,   BorderLayout.SOUTH);

        // ── Button Panel (CENTER) — 3 rows × 2 columns ──
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Create all navigation buttons
        btnViewDrives     = createDashboardButton(
                "📋  View Drives",      new Color(52, 152, 219));
        btnEligibleDrives = createDashboardButton(
                "✅  Eligible Drives",   new Color(39, 174, 96));
        btnSkillGap       = createDashboardButton(
                "🔍  Skill Gap",         new Color(142, 68, 173));
        btnResumeScore    = createDashboardButton(
                "📄  Resume Score",      new Color(230, 126, 34));
        btnApplications   = createDashboardButton(
                "📁  My Applications",   new Color(33, 97, 140));
        btnNotifications  = createDashboardButton(
                "🔔  Notifications",     new Color(192, 57, 43));

        // Add buttons to the grid panel
        buttonPanel.add(btnViewDrives);
        buttonPanel.add(btnEligibleDrives);
        buttonPanel.add(btnSkillGap);
        buttonPanel.add(btnResumeScore);
        buttonPanel.add(btnApplications);
        buttonPanel.add(btnNotifications);

        // ── South Panel — Logout + Status ──
        JPanel southPanel = new JPanel(new BorderLayout(0, 5));
        southPanel.setBackground(new Color(240, 248, 255));

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogout.setBackground(new Color(149, 165, 166));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0));

        statusLabel = new JLabel(
                "Logged in as Student", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        statusLabel.setForeground(Color.GRAY);

        southPanel.add(btnLogout,    BorderLayout.CENTER);
        southPanel.add(statusLabel,  BorderLayout.SOUTH);

        // ── Assemble Main Panel ──
        mainPanel.add(northPanel,   BorderLayout.NORTH);
        mainPanel.add(buttonPanel,  BorderLayout.CENTER);
        mainPanel.add(southPanel,   BorderLayout.SOUTH);

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
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        button.setOpaque(true);
        return button;
    }

    // ─── Event Listeners ──────────────────────────────────────

    /**
     * Attaches ActionListeners to all dashboard buttons.
     */
    private void attachListeners() {

        // View Drives button
        btnViewDrives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: View All Drives...");
                System.out.println("[StudentDashboard] View Drives clicked.");
                // TODO: new ViewDrivesPage(loggedInStudent); dispose();
            }
        });

        // Eligible Drives button
        btnEligibleDrives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: Eligible Drives...");
                System.out.println("[StudentDashboard] Eligible Drives clicked.");
                // TODO: new EligibleDrivesPage(loggedInStudent); dispose();
            }
        });

        // Skill Gap button
        btnSkillGap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: Skill Gap Analysis...");
                System.out.println("[StudentDashboard] Skill Gap clicked.");
                // TODO: new SkillGapPage(loggedInStudent); dispose();
            }
        });

        // Resume Score button
        btnResumeScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: Resume Score...");
                System.out.println("[StudentDashboard] Resume Score clicked.");
                // TODO: new ResumeScorePage(loggedInStudent); dispose();
            }
        });

        // My Applications button
        btnApplications.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: My Applications...");
                System.out.println("[StudentDashboard] Applications clicked.");
                // TODO: new ApplicationStatusPage(loggedInStudent); dispose();
            }
        });

        // Notifications button
        btnNotifications.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Opening: Notifications...");
                System.out.println("[StudentDashboard] Notifications clicked.");
                // TODO: new NotificationsPage(loggedInStudent); dispose();
            }
        });

        // Logout button
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("[StudentDashboard] Logout clicked.");
                // TODO: new LoginPage(); dispose();
                statusLabel.setText("Logged out successfully.");
                JOptionPane.showMessageDialog(
                        StudentDashboard.this,
                        "You have been logged out successfully.",
                        "Logout",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    // ─── Main Method (for standalone testing) ─────────────────

    /**
     * Main method to test StudentDashboard independently.
     * Creates a dummy Student object for UI testing.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Dummy student for UI testing
                java.util.List<String> skills = new java.util.ArrayList<>();
                skills.add("Java");
                skills.add("SQL");
                skills.add("Python");
                Student dummyStudent = new Student(
                        "STU001", "Alice", "alice@email.com",
                        "pass123", "CSE", 8.5, skills
                );
                new StudentDashboard(dummyStudent);
            }
        });
    }
}