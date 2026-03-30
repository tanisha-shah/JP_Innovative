// Package name - this file belongs to the cli package
// cli means Command Line Interface - it handles all menus and user interaction
package cli;

// We need Scanner to take input from user
import java.util.Scanner;

// We need all service classes
import service.ApplicationService;
import service.AuthService;
import service.DriveService;
import service.smart.EligibilityService;
import service.smart.NotificationService;
import service.smart.RecommendationService;
import service.smart.ResumeAnalyzer;
import service.smart.SkillGapService;

// We need model classes for login
import model.Admin;
import model.Company;
import model.Student;

// MainMenu class - this is the first screen user sees when program starts
// It shows 3 options: Login, Register, Exit
// All navigation starts from this class
public class MainMenu {

    // --- All service objects stored as class variables ---
    // We store them here so all methods in this class can use them
    private Scanner              scanner;
    private AuthService          authService;
    private DriveService         driveService;
    private ApplicationService   applicationService;
    private EligibilityService   eligibilityService;
    private SkillGapService      skillGapService;
    private ResumeAnalyzer       resumeAnalyzer;
    private NotificationService  notificationService;
    private RecommendationService recommendationService;

    // --- Constructor ---
    // We pass all service objects when creating MainMenu
    // This way all services are shared across the whole program
    public MainMenu(Scanner scanner,
                    AuthService authService,
                    DriveService driveService,
                    ApplicationService applicationService,
                    EligibilityService eligibilityService,
                    SkillGapService skillGapService,
                    ResumeAnalyzer resumeAnalyzer,
                    NotificationService notificationService,
                    RecommendationService recommendationService) {

        this.scanner               = scanner;
        this.authService           = authService;
        this.driveService          = driveService;
        this.applicationService    = applicationService;
        this.eligibilityService    = eligibilityService;
        this.skillGapService       = skillGapService;
        this.resumeAnalyzer        = resumeAnalyzer;
        this.notificationService   = notificationService;
        this.recommendationService = recommendationService;
    }

    // =====================================================
    //                  START METHOD
    // =====================================================
    // This method starts the main menu loop
    // It keeps showing the menu until user chooses Exit
    public void start() {

        // Print welcome banner when program starts
        printWelcomeBanner();

        // This variable controls the while loop
        // When user chooses Exit we set it to false
        boolean running = true;

        // Keep showing menu until user exits
        while (running) {

            // Print the main menu
            printMainMenu();

            // Read user input
            String input = scanner.nextLine().trim();

            // Convert input to integer for switch-case
            // If input is not a number we handle the error
            int choice = 0;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // User typed something that is not a number
                System.out.println("❌ Invalid input! Please enter 1, 2 or 3.");
                continue; // go back to top of while loop
            }

            // Use switch-case to handle each menu option
            switch (choice) {

                case 1:
                    // User chose LOGIN
                    showLoginMenu();
                    break;

                case 2:
                    // User chose REGISTER
                    showRegisterMenu();
                    break;

                case 3:
                    // User chose EXIT
                    printExitMessage();
                    running = false; // stop the while loop
                    break;

                default:
                    // User entered a number but not 1, 2 or 3
                    System.out.println("❌ Invalid choice! Please enter 1, 2 or 3.");
                    break;
            }
        }
    }

    // =====================================================
    //               PRINT MAIN MENU
    // =====================================================
    // This method just prints the main menu options on screen
    private void printMainMenu() {

        System.out.println("\n==============================================");
        System.out.println("                  MAIN MENU                  ");
        System.out.println("==============================================");
        System.out.println("  1.  Login");
        System.out.println("  2.  Register");
        System.out.println("  3.  Exit");
        System.out.println("==============================================");
        System.out.print("Enter your choice (1-3): ");
    }

    // =====================================================
    //                  LOGIN MENU
    // =====================================================
    // This method shows login options
    // User can login as Student, Company or Admin
    private void showLoginMenu() {

        System.out.println("\n----------------------------------------------");
        System.out.println("                 LOGIN AS                     ");
        System.out.println("----------------------------------------------");
        System.out.println("  1.  Student");
        System.out.println("  2.  Company");
        System.out.println("  3.  Admin");
        System.out.println("  4.  Back to Main Menu");
        System.out.println("----------------------------------------------");
        System.out.print("Enter your choice (1-4): ");

        String input = scanner.nextLine().trim();

        // Convert to int for switch-case
        int choice = 0;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a number.");
            return; // go back to main menu
        }

        switch (choice) {

            case 1:
                // Student Login
                loginStudent();
                break;

            case 2:
                // Company Login
                loginCompany();
                break;

            case 3:
                // Admin Login
                loginAdmin();
                break;

            case 4:
                // Go back to main menu
                System.out.println("↩️  Going back to main menu...");
                break;

            default:
                System.out.println("❌ Invalid choice! Please enter 1 to 4.");
                break;
        }
    }

    // =====================================================
    //               REGISTER MENU
    // =====================================================
    // This method shows register options
    // Student or Company can register from here
    // Admin registration is not allowed from here
    private void showRegisterMenu() {

        System.out.println("\n----------------------------------------------");
        System.out.println("                REGISTER AS                   ");
        System.out.println("----------------------------------------------");
        System.out.println("  1.  Student");
        System.out.println("  2.  Company");
        System.out.println("  3.  Back to Main Menu");
        System.out.println("----------------------------------------------");
        System.out.print("Enter your choice (1-3): ");

        String input = scanner.nextLine().trim();

        // Convert to int for switch-case
        int choice = 0;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a number.");
            return;
        }

        switch (choice) {

            case 1:
                // Student Registration
                registerStudent();
                break;

            case 2:
                // Company Registration
                registerCompany();
                break;

            case 3:
                // Go back to main menu
                System.out.println("↩️  Going back to main menu...");
                break;

            default:
                System.out.println("❌ Invalid choice! Please enter 1 to 3.");
                break;
        }
    }

    // =====================================================
    //              LOGIN METHODS
    // =====================================================

    // --- Student Login ---
    // Takes email and password from student
    // Calls AuthService to verify credentials
    // If successful, opens StudentMenu
    private void loginStudent() {

        System.out.println("\n--- Student Login ---");
        System.out.print("Enter Email    : ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Password : ");
        String password = scanner.nextLine().trim();

        // Try to login - returns Student object if successful, null if failed
        Student loggedStudent = authService.loginStudent(email, password);

        if (loggedStudent != null) {
            // Login successful - open student dashboard
            StudentMenu studentMenu = new StudentMenu(
                    scanner,
                    loggedStudent,
                    driveService,
                    applicationService,
                    eligibilityService,
                    skillGapService,
                    resumeAnalyzer,
                    notificationService,
                    recommendationService
            );
            studentMenu.start(); // start the student dashboard
        }
        // If null, error message already printed inside AuthService
    }

    // --- Company Login ---
    // Takes email and password from company user
    // Calls AuthService to verify credentials
    // If successful, opens CompanyMenu
    private void loginCompany() {

        System.out.println("\n--- Company Login ---");
        System.out.print("Enter Email    : ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Password : ");
        String password = scanner.nextLine().trim();

        // Try to login - returns Company object if successful, null if failed
        Company loggedCompany = authService.loginCompany(email, password);

        if (loggedCompany != null) {
            // Login successful - open company dashboard
            CompanyMenu companyMenu = new CompanyMenu(
                    scanner,
                    loggedCompany,
                    driveService,
                    applicationService,
                    notificationService,
                    authService
            );
            companyMenu.start(); // start the company dashboard
        }
        // If null, error message already printed inside AuthService
    }

    // --- Admin Login ---
    // Takes email and password from admin
    // Calls AuthService to verify credentials
    // If successful, opens AdminMenu
    private void loginAdmin() {

        System.out.println("\n--- Admin Login ---");
        System.out.print("Enter Email    : ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Password : ");
        String password = scanner.nextLine().trim();

        // Try to login - returns Admin object if successful, null if failed
        Admin loggedAdmin = authService.loginAdmin(email, password);

        if (loggedAdmin != null) {
            // Login successful - open admin dashboard
            AdminMenu adminMenu = new AdminMenu(
                    scanner,
                    loggedAdmin,
                    authService,
                    driveService,
                    applicationService,
                    notificationService
            );
            adminMenu.start(); // start the admin dashboard
        }
        // If null, error message already printed inside AuthService
    }

    // =====================================================
    //             REGISTER METHODS
    // =====================================================

    // --- Student Registration ---
    // Takes all student details from user
    // Creates a new Student object
    // Calls AuthService to register
    private void registerStudent() {

        System.out.println("\n--- Student Registration ---");

        System.out.print("Enter Full Name : ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email     : ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Password  : ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter Branch    : ");
        String branch = scanner.nextLine().trim();

        System.out.print("Enter CGPA      : ");
        double cgpa = 0.0;

        // Read CGPA carefully - handle if user types text instead of number
        try {
            cgpa = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid CGPA! Please enter a number like 7.5");
            return; // go back to menu
        }

        // Validate that CGPA is within correct range
        if (cgpa < 0.0 || cgpa > 10.0) {
            System.out.println("❌ CGPA must be between 0.0 and 10.0");
            return;
        }

        // Get a new unique ID for this student
        int newId = authService.getNextStudentId();

        // Create new Student object with all the details
        Student newStudent = new Student(newId, name, email, password, branch, cgpa);

        // Register the student using AuthService
        authService.registerStudent(newStudent);
    }

    // --- Company Registration ---
    // Takes all company details from user
    // Creates a new Company object
    // Calls AuthService to register
    private void registerCompany() {

        System.out.println("\n--- Company Registration ---");

        System.out.print("Enter Company Name : ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email        : ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Password     : ");
        String password = scanner.nextLine().trim();

        // Get a new unique ID for this company
        int newId = authService.getNextCompanyId();

        // Create new Company object with all the details
        Company newCompany = new Company(newId, name, email, password);

        // Register the company using AuthService
        authService.registerCompany(newCompany);
    }

    // =====================================================
    //             HELPER PRINT METHODS
    // =====================================================

    // --- Print welcome banner ---
    // This is shown once when program starts
    private void printWelcomeBanner() {

        System.out.println("==============================================");
        System.out.println("  SMART CAMPUS PLACEMENT MANAGEMENT SYSTEM   ");
        System.out.println("==============================================");
        System.out.println("     Helping students find their dream job!  ");
        System.out.println("==============================================");
        System.out.println("  Default Login Credentials for Testing:     ");
        System.out.println("  Admin   : admin@campus.com / admin123      ");
        System.out.println("  Student : rahul@gmail.com  / rahul123      ");
        System.out.println("  Company : google@gmail.com / google123     ");
        System.out.println("==============================================");
    }

    // --- Print exit message ---
    // This is shown when user chooses to exit
    private void printExitMessage() {

        System.out.println("\n==============================================");
        System.out.println("  Thank you for using Campus Placement System!");
        System.out.println("       Goodbye! Best of luck! 👋             ");
        System.out.println("==============================================");
    }
}