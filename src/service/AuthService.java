// File: src/service/AuthService.java
// Package: service
// Description: Handles Authentication logic for the Smart Campus Placement System.

package service;

import model.Student;
import model.Company;
import model.Admin;

import java.util.ArrayList;

/**
 * AuthService class.
 * Handles all authentication-related operations:
 * - Student registration
 * - Login for Student, Company, and Admin
 *
 * Uses simple ArrayList storage (no database).
 * Performs basic email and password validation.
 */
public class AuthService {

    // ─── Storage ──────────────────────────────────────────────

    // In-memory list to store registered students
    private ArrayList<Student> students;

    // In-memory list to store registered companies
    private ArrayList<Company> companies;

    // In-memory list to store admins (pre-loaded with a default admin)
    private ArrayList<Admin> admins;

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Initializes all storage lists.
     * Pre-loads a default admin account for system access.
     */
    public AuthService() {
        this.students  = new ArrayList<>();
        this.companies = new ArrayList<>();
        this.admins    = new ArrayList<>();

        // Pre-load a default admin account
        // Admin can log in with: admin@campus.com / admin123
        admins.add(new Admin("ADM001", "Admin", "admin@campus.com", "admin123"));
    }

    // ─── Registration ─────────────────────────────────────────

    /**
     * Registers a new Student into the system.
     *
     * Steps:
     * 1. Validate that name, email, and password are not empty.
     * 2. Check if the email is already registered.
     * 3. If valid, create a new Student and add to the list.
     *
     * @param id       Unique student ID
     * @param name     Full name of the student
     * @param email    Email address (must be unique)
     * @param password Login password (must not be empty)
     * @param branch   Academic branch/department
     * @param cgpa     CGPA of the student
     * @param skills   Comma-separated skills string (e.g., "Java,SQL,Python")
     * @return "SUCCESS" if registered, or an error message string if failed
     */
    public String registerStudent(String id, String name, String email,
                                  String password, String branch,
                                  double cgpa, java.util.List<String> skills) {

        // ── Step 1: Validate inputs ──
        if (name == null || name.trim().isEmpty()) {
            return "ERROR: Name cannot be empty.";
        }
        if (email == null || email.trim().isEmpty()) {
            return "ERROR: Email cannot be empty.";
        }
        if (!email.contains("@") || !email.contains(".")) {
            return "ERROR: Invalid email format.";
        }
        if (password == null || password.trim().isEmpty()) {
            return "ERROR: Password cannot be empty.";
        }
        if (password.length() < 4) {
            return "ERROR: Password must be at least 4 characters long.";
        }
        if (branch == null || branch.trim().isEmpty()) {
            return "ERROR: Branch cannot be empty.";
        }
        if (cgpa < 0.0 || cgpa > 10.0) {
            return "ERROR: CGPA must be between 0.0 and 10.0.";
        }

        // ── Step 2: Check for duplicate email ──
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email.trim())) {
                return "ERROR: Email is already registered. Please login.";
            }
        }

        // ── Step 3: Create and store the new student ──
        Student newStudent = new Student(
                id,
                name.trim(),
                email.trim().toLowerCase(),
                password,
                branch.trim(),
                cgpa,
                skills
        );
        students.add(newStudent);

        return "SUCCESS"; // Registration successful
    }

    // ─── Login ────────────────────────────────────────────────

    /**
     * Attempts to log in a Student using email and password.
     *
     * @param email    Email address entered by the user
     * @param password Password entered by the user
     * @return The matched Student object if login is successful, null otherwise
     */
    public Student loginStudent(String email, String password) {

        // Basic null/empty check
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            return null;
        }

        // Search for a matching student by email and password
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email.trim()) &&
                s.getPassword().equals(password)) {
                return s; // Login successful — return the student object
            }
        }

        return null; // No match found — login failed
    }

    /**
     * Attempts to log in a Company using email and password.
     *
     * @param email    Email address entered by the company
     * @param password Password entered by the company
     * @return The matched Company object if login is successful, null otherwise
     */
    public Company loginCompany(String email, String password) {

        // Basic null/empty check
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            return null;
        }

        // Search for a matching company by email and password
        for (Company c : companies) {
            if (c.getEmail().equalsIgnoreCase(email.trim()) &&
                c.getPassword().equals(password)) {
                return c; // Login successful — return the company object
            }
        }

        return null; // No match found — login failed
    }

    /**
     * Attempts to log in an Admin using email and password.
     *
     * @param email    Email address entered by the admin
     * @param password Password entered by the admin
     * @return The matched Admin object if login is successful, null otherwise
     */
    public Admin loginAdmin(String email, String password) {

        // Basic null/empty check
        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            return null;
        }

        // Search for a matching admin by email and password
        for (Admin a : admins) {
            if (a.getEmail().equalsIgnoreCase(email.trim()) &&
                a.getPassword().equals(password)) {
                return a; // Login successful — return the admin object
            }
        }

        return null; // No match found — login failed
    }

    // ─── Helper Methods ───────────────────────────────────────

    /**
     * Registers a new Company into the system.
     * Used internally or by admin to add companies.
     *
     * @param company Fully initialized Company object to register
     * @return "SUCCESS" if registered, or an error message if failed
     */
    public String registerCompany(Company company) {

        // Basic null check
        if (company == null) {
            return "ERROR: Company data is invalid.";
        }

        // Check for duplicate email
        for (Company c : companies) {
            if (c.getEmail().equalsIgnoreCase(company.getEmail())) {
                return "ERROR: Company email is already registered.";
            }
        }

        companies.add(company);
        return "SUCCESS";
    }

    /**
     * Returns the full list of registered students.
     * Useful for admin operations.
     *
     * @return ArrayList of all registered students
     */
    public ArrayList<Student> getAllStudents() {
        return students;
    }

    /**
     * Returns the full list of registered companies.
     * Useful for admin operations.
     *
     * @return ArrayList of all registered companies
     */
    public ArrayList<Company> getAllCompanies() {
        return companies;
    }
}