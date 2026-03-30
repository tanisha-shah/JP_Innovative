// Package name - this file belongs to the service package
package service;

// We need ArrayList to store all users
import java.util.ArrayList;

// We need these classes from model package
import model.Admin;
import model.Company;
import model.Student;

// AuthService class - handles login and registration for all users
// Students, Companies and Admins all use this class to login or register
public class AuthService {

    // These ArrayLists work as our database to store all users
    private ArrayList<Student> studentList;
    private ArrayList<Company> companyList;
    private ArrayList<Admin>   adminList;

    // --- Constructor ---
    // This runs when we create AuthService object
    // We also add some default sample data here for testing
    public AuthService() {
        studentList = new ArrayList<Student>();
        companyList = new ArrayList<Company>();
        adminList   = new ArrayList<Admin>();

        // Adding a default Admin so we can login right away
        Admin defaultAdmin = new Admin(1, "Admin", "admin@campus.com", "admin123");
        adminList.add(defaultAdmin);

        // Adding sample students for testing
        Student s1 = new Student(1, "Rahul Sharma", "rahul@gmail.com", "rahul123", "CSE", 8.5);
        s1.addSkill("Java");
        s1.addSkill("Python");
        studentList.add(s1);

        Student s2 = new Student(2, "Priya Patel", "priya@gmail.com", "priya123", "IT", 7.8);
        s2.addSkill("SQL");
        s2.addSkill("HTML");
        studentList.add(s2);

        // Adding sample companies for testing
        Company c1 = new Company(1, "Google",  "google@gmail.com",       "google123");
        Company c2 = new Company(2, "TCS",     "tcs@tcs.com",            "tcs123");
        Company c3 = new Company(3, "Infosys", "infosys@infosys.com",    "infosys123");
        companyList.add(c1);
        companyList.add(c2);
        companyList.add(c3);
    }

    // =========================================================
    //                  REGISTER METHODS
    // =========================================================

    // --- Register a new Student ---
    // Adds a student object directly into the student list
    public void registerStudent(Student s) {

        // Check if email is already taken by another student
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getEmail().equalsIgnoreCase(s.getEmail())) {
                System.out.println("❌ Registration failed! Email already exists: " + s.getEmail());
                return; // stop here, don't add duplicate
            }
        }

        // Email is unique so we can safely add the student
        studentList.add(s);
        System.out.println("✅ Student registered successfully! Welcome, " + s.getName());
    }

    // --- Register a new Company ---
    // Adds a company object directly into the company list
    public void registerCompany(Company c) {

        // Check if email is already taken by another company
        for (int i = 0; i < companyList.size(); i++) {
            if (companyList.get(i).getEmail().equalsIgnoreCase(c.getEmail())) {
                System.out.println("❌ Registration failed! Email already exists: " + c.getEmail());
                return; // stop here, don't add duplicate
            }
        }

        // Email is unique so we can safely add the company
        companyList.add(c);
        System.out.println("✅ Company registered successfully! Welcome, " + c.getName());
    }

    // --- Register a new Admin ---
    // Adds an admin object directly into the admin list
    public void registerAdmin(Admin a) {

        // Check if email is already taken by another admin
        for (int i = 0; i < adminList.size(); i++) {
            if (adminList.get(i).getEmail().equalsIgnoreCase(a.getEmail())) {
                System.out.println("❌ Registration failed! Email already exists: " + a.getEmail());
                return; // stop here, don't add duplicate
            }
        }

        // Email is unique so we can safely add the admin
        adminList.add(a);
        System.out.println("✅ Admin registered successfully! Welcome, " + a.getName());
    }

    // =========================================================
    //                    LOGIN METHODS
    // =========================================================

    // --- Login a Student ---
    // Checks email and password against the student list
    // Returns the Student object if credentials match
    // Returns null if not found or wrong password
    public Student loginStudent(String email, String password) {

        // Loop through all students one by one
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);

            // Step 1 - Check if email matches
            if (s.getEmail().equalsIgnoreCase(email)) {

                // Step 2 - Email matched, now check password
                if (s.getPassword().equals(password)) {
                    System.out.println("✅ Login successful! Welcome, " + s.getName());
                    return s; // return the matched student object
                } else {
                    // Email was correct but password is wrong
                    System.out.println("❌ Invalid credentials! Wrong password.");
                    return null;
                }
            }
        }

        // If we reach here, no student had that email
        System.out.println("❌ Invalid credentials! Email not found.");
        return null;
    }

    // --- Login a Company ---
    // Checks email and password against the company list
    // Returns the Company object if credentials match
    // Returns null if not found or wrong password
    public Company loginCompany(String email, String password) {

        // Loop through all companies one by one
        for (int i = 0; i < companyList.size(); i++) {
            Company c = companyList.get(i);

            // Step 1 - Check if email matches
            if (c.getEmail().equalsIgnoreCase(email)) {

                // Step 2 - Email matched, now check password
                if (c.getPassword().equals(password)) {
                    System.out.println("✅ Login successful! Welcome, " + c.getName());
                    return c; // return the matched company object
                } else {
                    // Email was correct but password is wrong
                    System.out.println("❌ Invalid credentials! Wrong password.");
                    return null;
                }
            }
        }

        // If we reach here, no company had that email
        System.out.println("❌ Invalid credentials! Email not found.");
        return null;
    }

    // --- Login an Admin ---
    // Checks email and password against the admin list
    // Returns the Admin object if credentials match
    // Returns null if not found or wrong password
    public Admin loginAdmin(String email, String password) {

        // Loop through all admins one by one
        for (int i = 0; i < adminList.size(); i++) {
            Admin a = adminList.get(i);

            // Step 1 - Check if email matches
            if (a.getEmail().equalsIgnoreCase(email)) {

                // Step 2 - Email matched, now check password
                if (a.getPassword().equals(password)) {
                    System.out.println("✅ Login successful! Welcome, " + a.getName());
                    return a; // return the matched admin object
                } else {
                    // Email was correct but password is wrong
                    System.out.println("❌ Invalid credentials! Wrong password.");
                    return null;
                }
            }
        }

        // If we reach here, no admin had that email
        System.out.println("❌ Invalid credentials! Email not found.");
        return null;
    }

    // =========================================================
    //                   GETTER METHODS
    // =========================================================

    // --- Get all students ---
    // Returns the full student list
    // Useful for Admin to see all registered students
    public ArrayList<Student> getAllStudents() {
        return studentList;
    }

    // --- Get all companies ---
    // Returns the full company list
    // Useful for Admin to see all registered companies
    public ArrayList<Company> getAllCompanies() {
        return companyList;
    }

    // --- Get all admins ---
    // Returns the full admin list
    public ArrayList<Admin> getAllAdmins() {
        return adminList;
    }

    // --- Get next student ID ---
    // Returns a new unique ID for the next student
    // We just use the current list size + 1
    public int getNextStudentId() {
        return studentList.size() + 1;
    }

    // --- Get next company ID ---
    // Returns a new unique ID for the next company
    public int getNextCompanyId() {
        return companyList.size() + 1;
    }
}