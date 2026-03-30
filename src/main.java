// This is the main file - starting point of our entire project
// When we run the program, Java looks for main() method in this file
// and starts executing from there

import java.util.Scanner;

// We need all service classes to pass them to menu classes
import service.ApplicationService;
import service.AuthService;
import service.CompanyService;
import service.DriveService;
import service.StudentService;
import service.smart.EligibilityService;
import service.smart.NotificationService;
import service.smart.RecommendationService;
import service.smart.ResumeAnalyzer;
import service.smart.SkillGapService;

// We need model classes for login
import model.Admin;
import model.Company;
import model.Student;

// Main class - this is where our program starts
public class Main {

    public static void main(String[] args) {

        // Create a Scanner object to take input from user
        Scanner scanner = new Scanner(System.in);

        // =====================================================
        //         CREATE ALL SERVICE OBJECTS
        // =====================================================
        // We create all service objects here in main
        // and pass them to menu classes when needed
        // This way all menus share the same data

        // Core services
        AuthService          authService          = new AuthService();
        StudentService       studentService       = new StudentService();
        CompanyService       companyService       = new CompanyService();
        DriveService         driveService         = new DriveService();
        ApplicationService   applicationService   = new ApplicationService();

        // Smart services
        EligibilityService   eligibilityService   = new EligibilityService();
        SkillGapService      skillGapService      = new SkillGapService();
        ResumeAnalyzer       resumeAnalyzer       = new ResumeAnalyzer();
        NotificationService  notificationService  = new NotificationService();
        RecommendationService recommendationService = new RecommendationService();

        // =====================================================
        //              WELCOME MESSAGE
        // =====================================================

        System.out.println("==============================================");
        System.out.println("   SMART CAMPUS PLACEMENT MANAGEMENT SYSTEM  ");
        System.out.println("==============================================");
        System.out.println("   Helping students find their dream jobs!   ");
        System.out.println("==============================================");

        // This variable controls the while loop
        // When user chooses Exit we set it to false and loop stops
        boolean running = true;

        // =====================================================
        //                  MAIN LOOP
        // =====================================================
        // This loop keeps the program running
        // until the user chooses to exit

        while (running) {

            // Print the main menu options
            System.out.println("\n==============================================");
            System.out.println("                  MAIN MENU                  ");
            System.out.println("==============================================");
            System.out.println("  1. Login");
            System.out.println("  2. Register");
            System.out.println("  3. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice (1-3): ");

            // Read user's choice as String first to avoid input errors
            String input = scanner.nextLine().trim();

            // Use if-else to handle each choice
            if (input.equals("1")) {

                // User wants to LOGIN
                // Ask what type of user they are
                showLoginMenu(scanner, authService, studentService,
                        companyService, driveService, applicationService,
                        eligibilityService, skillGapService, resumeAnalyzer,
                        notificationService, recommendationService);

            } else if (input.equals("2")) {

                // User wants to REGISTER
                // Ask what type of user they are
                showRegisterMenu(scanner, authService);

            } else if (input.equals("3")) {

                // User wants to EXIT
                System.out.println("\n==============================================");
                System.out.println("   Thank you for using Campus Placement System!");
                System.out.println("          Goodbye! Best of luck! 👋            ");
                System.out.println("==============================================");
                running = false; // this will stop the while loop

            } else {
                // User entered something invalid
                System.out.println("❌ Invalid choice! Please enter 1, 2 or 3.");
            }
        }

        // Close scanner when program ends
        scanner.close();
    }

    // =====================================================
    //                  LOGIN MENU
    // =====================================================
    // This method shows login options for different user types
    // Student, Company or Admin can login from here

    private static void showLoginMenu(Scanner scanner,
                                       AuthService authService,
                                       StudentService studentService,
                                       CompanyService companyService,
                                       DriveService driveService,
                                       ApplicationService applicationService,
                                       EligibilityService eligibilityService,
                                       SkillGapService skillGapService,
                                       ResumeAnalyzer resumeAnalyzer,
                                       NotificationService notificationService,
                                       RecommendationService recommendationService) {

        System.out.println("\n----------------------------------------------");
        System.out.println("                LOGIN AS                      ");
        System.out.println("----------------------------------------------");
        System.out.println("  1. Student");
        System.out.println("  2. Company");
        System.out.println("  3. Admin");
        System.out.println("  4. Back to Main Menu");
        System.out.println("----------------------------------------------");
        System.out.print("Enter your choice (1-4): ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {

            // --- Student Login ---
            System.out.println("\n--- Student Login ---");
            System.out.print("Enter Email    : ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter Password : ");
            String password = scanner.nextLine().trim();

            // Try to login using AuthService
            Student loggedStudent = authService.loginStudent(email, password);

            if (loggedStudent != null) {
                // Login successful - go to student dashboard
                // We will create StudentMenu later
                showStudentDashboard(scanner, loggedStudent, driveService,
                        applicationService, eligibilityService, skillGapService,
                        resumeAnalyzer, notificationService, recommendationService);
            }
            // If null, error message already printed in AuthService

        } else if (choice.equals("2")) {

            // --- Company Login ---
            System.out.println("\n--- Company Login ---");
            System.out.print("Enter Email    : ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter Password : ");
            String password = scanner.nextLine().trim();

            // Try to login using AuthService
            Company loggedCompany = authService.loginCompany(email, password);

            if (loggedCompany != null) {
                // Login successful - go to company dashboard
                showCompanyDashboard(scanner, loggedCompany, driveService,
                        applicationService, notificationService,
                        authService);
            }

        } else if (choice.equals("3")) {

            // --- Admin Login ---
            System.out.println("\n--- Admin Login ---");
            System.out.print("Enter Email    : ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter Password : ");
            String password = scanner.nextLine().trim();

            // Try to login using AuthService
            Admin loggedAdmin = authService.loginAdmin(email, password);

            if (loggedAdmin != null) {
                // Login successful - go to admin dashboard
                showAdminDashboard(scanner, loggedAdmin, authService,
                        driveService, applicationService,
                        notificationService);
            }

        } else if (choice.equals("4")) {
            // Go back to main menu
            System.out.println("Going back to main menu...");

        } else {
            System.out.println("❌ Invalid choice! Please try again.");
        }
    }

    // =====================================================
    //                 REGISTER MENU
    // =====================================================
    // This method shows register options for different user types
    // Student or Company can register from here

    private static void showRegisterMenu(Scanner scanner, AuthService authService) {

        System.out.println("\n----------------------------------------------");
        System.out.println("               REGISTER AS                    ");
        System.out.println("----------------------------------------------");
        System.out.println("  1. Student");
        System.out.println("  2. Company");
        System.out.println("  3. Back to Main Menu");
        System.out.println("----------------------------------------------");
        System.out.print("Enter your choice (1-3): ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {

            // --- Student Registration ---
            System.out.println("\n--- Student Registration ---");

            System.out.print("Enter Name     : ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email    : ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Password : ");
            String password = scanner.nextLine().trim();

            System.out.print("Enter Branch   : ");
            String branch = scanner.nextLine().trim();

            System.out.print("Enter CGPA     : ");

            // Read CGPA carefully - it should be a number
            double cgpa = 0.0;
            try {
                cgpa = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                // If user types something that is not a number
                System.out.println("❌ Invalid CGPA! Please enter a number like 7.5");
                return;
            }

            // Validate CGPA range
            if (cgpa < 0.0 || cgpa > 10.0) {
                System.out.println("❌ CGPA must be between 0.0 and 10.0");
                return;
            }

            // Get new unique ID for this student
            int newId = authService.getNextStudentId();

            // Create new Student object
            model.Student newStudent = new model.Student(newId, name, email, password, branch, cgpa);

            // Register using AuthService
            authService.registerStudent(newStudent);

        } else if (choice.equals("2")) {

            // --- Company Registration ---
            System.out.println("\n--- Company Registration ---");

            System.out.print("Enter Company Name : ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email        : ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Password     : ");
            String password = scanner.nextLine().trim();

            // Get new unique ID for this company
            int newId = authService.getNextCompanyId();

            // Create new Company object
            model.Company newCompany = new model.Company(newId, name, email, password);

            // Register using AuthService
            authService.registerCompany(newCompany);

        } else if (choice.equals("3")) {
            // Go back to main menu
            System.out.println("Going back to main menu...");

        } else {
            System.out.println("❌ Invalid choice! Please try again.");
        }
    }

    // =====================================================
    //              STUDENT DASHBOARD
    // =====================================================
    // Simple inline student dashboard
    // Shows all options a student can do after login

    private static void showStudentDashboard(Scanner scanner,
                                              Student student,
                                              DriveService driveService,
                                              ApplicationService applicationService,
                                              EligibilityService eligibilityService,
                                              SkillGapService skillGapService,
                                              ResumeAnalyzer resumeAnalyzer,
                                              NotificationService notificationService,
                                              RecommendationService recommendationService) {

        boolean studentRunning = true;

        while (studentRunning) {

            System.out.println("\n==============================================");
            System.out.println("   STUDENT DASHBOARD - " + student.getName());
            System.out.println("==============================================");
            System.out.println("  1.  View All Drives");
            System.out.println("  2.  Apply for a Drive");
            System.out.println("  3.  View My Applications");
            System.out.println("  4.  View Eligible Drives");
            System.out.println("  5.  Check Skill Gap");
            System.out.println("  6.  View Resume Score");
            System.out.println("  7.  View Notifications");
            System.out.println("  8.  View My Profile");
            System.out.println("  9.  Add Skill");
            System.out.println("  10. Logout");
            System.out.println("==============================================");
            System.out.print("Enter your choice (1-10): ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {

                // View all available drives
                driveService.viewDrives();

            } else if (choice.equals("2")) {

                // Apply for a drive
                System.out.print("Enter Drive ID to apply: ");
                try {
                    int driveId = Integer.parseInt(scanner.nextLine().trim());
                    service.DriveService ds = driveService;
                    model.Drive drive = ds.getDriveById(driveId);

                    if (drive != null) {
                        applicationService.applyForDrive(student, drive);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Please enter a valid Drive ID number.");
                }

            } else if (choice.equals("3")) {

                // View student's own applications
                applicationService.viewApplicationsByStudent(student.getId());

            } else if (choice.equals("4")) {

                // View eligible drives using smart recommendation
                recommendationService.getEligibleDrives(student, driveService.getAllDrives());

            } else if (choice.equals("5")) {

                // Check skill gap for a specific drive
                System.out.print("Enter Drive ID to check skill gap: ");
                try {
                    int driveId = Integer.parseInt(scanner.nextLine().trim());
                    model.Drive drive = driveService.getDriveById(driveId);

                    if (drive != null) {
                        skillGapService.displaySkillReport(student, drive);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Please enter a valid Drive ID number.");
                }

            } else if (choice.equals("6")) {

                // View resume score
                resumeAnalyzer.showImprovementTips(student);

            } else if (choice.equals("7")) {

                // View notifications
                notificationService.displayNotifications(student.getId());

            } else if (choice.equals("8")) {

                // View student profile
                student.displayInfo();

            } else if (choice.equals("9")) {

                // Add a skill to profile
                System.out.print("Enter skill to add (e.g. Java, Python, SQL): ");
                String skill = scanner.nextLine().trim();

                if (skill.isEmpty()) {
                    System.out.println("❌ Skill cannot be empty!");
                } else {
                    student.addSkill(skill);
                    System.out.println("✅ Skill added: " + skill);
                }

            } else if (choice.equals("10")) {

                // Logout
                System.out.println("👋 Logged out successfully. Goodbye, " + student.getName() + "!");
                studentRunning = false;

            } else {
                System.out.println("❌ Invalid choice! Please enter a number from 1 to 10.");
            }
        }
    }

    // =====================================================
    //              COMPANY DASHBOARD
    // =====================================================
    // Simple inline company dashboard
    // Shows all options a company can do after login

    private static void showCompanyDashboard(Scanner scanner,
                                              Company company,
                                              DriveService driveService,
                                              ApplicationService applicationService,
                                              NotificationService notificationService,
                                              AuthService authService) {

        boolean companyRunning = true;

        while (companyRunning) {

            System.out.println("\n==============================================");
            System.out.println("   COMPANY DASHBOARD - " + company.getName());
            System.out.println("==============================================");
            System.out.println("  1. Post a New Drive");
            System.out.println("  2. View My Drives");
            System.out.println("  3. View Applications for a Drive");
            System.out.println("  4. View All Students");
            System.out.println("  5. Logout");
            System.out.println("==============================================");
            System.out.print("Enter your choice (1-5): ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {

                // Post a new drive
                System.out.println("\n--- Post New Drive ---");

                System.out.print("Enter Job Role     : ");
                String role = scanner.nextLine().trim();

                System.out.print("Enter Minimum CGPA : ");
                double minCgpa = 0.0;
                try {
                    minCgpa = Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid CGPA! Please enter a number.");
                    continue; // go back to menu
                }

                // Take eligible branches from company
                java.util.ArrayList<String> branches = new java.util.ArrayList<String>();
                System.out.print("How many branches are eligible? ");
                try {
                    int branchCount = Integer.parseInt(scanner.nextLine().trim());
                    for (int i = 0; i < branchCount; i++) {
                        System.out.print("Enter branch " + (i + 1) + ": ");
                        branches.add(scanner.nextLine().trim());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid number! Please try again.");
                    continue;
                }

                // Take required skills from company
                java.util.ArrayList<String> skills = new java.util.ArrayList<String>();
                System.out.print("How many skills are required? ");
                try {
                    int skillCount = Integer.parseInt(scanner.nextLine().trim());
                    for (int i = 0; i < skillCount; i++) {
                        System.out.print("Enter skill " + (i + 1) + ": ");
                        skills.add(scanner.nextLine().trim());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid number! Please try again.");
                    continue;
                }

                // Add the drive using DriveService
                driveService.addDrive(company.getName(), role, minCgpa, branches, skills);

                // Notify all registered students about the new drive
                java.util.ArrayList<model.Student> allStudents = authService.getAllStudents();
                for (int i = 0; i < allStudents.size(); i++) {
                    notificationService.addNotification(
                        allStudents.get(i).getId(),
                        "New drive posted by " + company.getName() + " for " + role + " role!"
                    );
                }

            } else if (choice.equals("2")) {

                // View drives posted by this company
                java.util.ArrayList<model.Drive> myDrives =
                        driveService.getDrivesByCompany(company.getName());

                if (myDrives.size() == 0) {
                    System.out.println("You have not posted any drives yet.");
                } else {
                    System.out.println("\n===== YOUR POSTED DRIVES =====");
                    for (int i = 0; i < myDrives.size(); i++) {
                        myDrives.get(i).displayInfo();
                    }
                }

            } else if (choice.equals("3")) {

                // View applications for a specific drive
                System.out.print("Enter Drive ID: ");
                try {
                    int driveId = Integer.parseInt(scanner.nextLine().trim());
                    java.util.ArrayList<model.Application> apps =
                            applicationService.getApplicationsByDrive(driveId);

                    if (apps.size() == 0) {
                        System.out.println("No applications found for Drive ID: " + driveId);
                    } else {
                        System.out.println("\n===== APPLICATIONS FOR DRIVE " + driveId + " =====");
                        for (int i = 0; i < apps.size(); i++) {
                            apps.get(i).displayInfo();
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Please enter a valid Drive ID number.");
                }

            } else if (choice.equals("4")) {

                // View all registered students
                java.util.ArrayList<model.Student> allStudents = authService.getAllStudents();

                if (allStudents.size() == 0) {
                    System.out.println("No students registered yet.");
                } else {
                    System.out.println("\n===== ALL REGISTERED STUDENTS =====");
                    for (int i = 0; i < allStudents.size(); i++) {
                        allStudents.get(i).displayInfo();
                    }
                }

            } else if (choice.equals("5")) {

                // Logout
                System.out.println("👋 Logged out successfully. Goodbye, " + company.getName() + "!");
                companyRunning = false;

            } else {
                System.out.println("❌ Invalid choice! Please enter a number from 1 to 5.");
            }
        }
    }

    // =====================================================
    //               ADMIN DASHBOARD
    // =====================================================
    // Simple inline admin dashboard
    // Admin can manage everything from here

    private static void showAdminDashboard(Scanner scanner,
                                            Admin admin,
                                            AuthService authService,
                                            DriveService driveService,
                                            ApplicationService applicationService,
                                            NotificationService notificationService) {

        boolean adminRunning = true;

        while (adminRunning) {

            System.out.println("\n==============================================");
            System.out.println("    ADMIN DASHBOARD - " + admin.getName());
            System.out.println("==============================================");
            System.out.println("  1. View All Students");
            System.out.println("  2. View All Companies");
            System.out.println("  3. View All Drives");
            System.out.println("  4. View All Applications");
            System.out.println("  5. Update Application Status");
            System.out.println("  6. View All Notifications");
            System.out.println("  7. Logout");
            System.out.println("==============================================");
            System.out.print("Enter your choice (1-7): ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {

                // View all students
                java.util.ArrayList<model.Student> allStudents = authService.getAllStudents();
                if (allStudents.size() == 0) {
                    System.out.println("No students registered yet.");
                } else {
                    System.out.println("\n===== ALL REGISTERED STUDENTS =====");
                    for (int i = 0; i < allStudents.size(); i++) {
                        allStudents.get(i).displayInfo();
                    }
                }

            } else if (choice.equals("2")) {

                // View all companies
                java.util.ArrayList<model.Company> allCompanies = authService.getAllCompanies();
                if (allCompanies.size() == 0) {
                    System.out.println("No companies registered yet.");
                } else {
                    System.out.println("\n===== ALL REGISTERED COMPANIES =====");
                    for (int i = 0; i < allCompanies.size(); i++) {
                        allCompanies.get(i).displayInfo();
                    }
                }

            } else if (choice.equals("3")) {

                // View all drives
                driveService.viewDrives();

            } else if (choice.equals("4")) {

                // View all applications
                applicationService.displayAllApplications();

            } else if (choice.equals("5")) {

                // Update application status
                System.out.print("Enter Application ID to update: ");
                try {
                    int appId = Integer.parseInt(scanner.nextLine().trim());

                    System.out.println("Select new status:");
                    System.out.println("  1. Selected");
                    System.out.println("  2. Rejected");
                    System.out.println("  3. Pending");
                    System.out.print("Enter choice (1-3): ");

                    String statusChoice = scanner.nextLine().trim();
                    String newStatus = "";

                    if (statusChoice.equals("1")) {
                        newStatus = "Selected";
                    } else if (statusChoice.equals("2")) {
                        newStatus = "Rejected";
                    } else if (statusChoice.equals("3")) {
                        newStatus = "Pending";
                    } else {
                        System.out.println("❌ Invalid status choice!");
                        continue;
                    }

                    // Update the status
                    applicationService.updateStatus(appId, newStatus);

                    // Find the application and send notification to student
                    java.util.ArrayList<model.Application> allApps =
                            applicationService.getAllApplications();

                    for (int i = 0; i < allApps.size(); i++) {
                        if (allApps.get(i).getApplicationId() == appId) {
                            int studentId = allApps.get(i).getStudentId();
                            int driveId   = allApps.get(i).getDriveId();
                            model.Drive drive = driveService.getDriveById(driveId);

                            if (drive != null) {
                                // Send notification to student about status change
                                notificationService.notifyApplicationStatus(
                                    studentId,
                                    drive.getCompanyName(),
                                    newStatus
                                );
                            }
                            break;
                        }
                    }

                } catch (NumberFormatException e) {
                    System.out.println("❌ Please enter a valid Application ID number.");
                }

            } else if (choice.equals("6")) {

                // View all notifications
                notificationService.displayAllNotifications();

            } else if (choice.equals("7")) {

                // Logout
                System.out.println("👋 Logged out successfully. Goodbye, " + admin.getName() + "!");
                adminRunning = false;

            } else {
                System.out.println("❌ Invalid choice! Please enter a number from 1 to 7.");
            }
        }
    }
}