// File: src/service/ApplicationService.java
// Package: service
// Description: Handles all Application related operations in the Smart Campus Placement System.

package service;

import model.Application;

import java.util.ArrayList;

/**
 * ApplicationService class.
 * Handles all operations related to student job applications:
 * - Applying for a placement drive
 * - Retrieving applications by student
 * - Retrieving applications by drive (for company view)
 * - Updating application status
 *
 * Uses simple ArrayList storage (no database).
 * Performs basic input validation before processing applications.
 */
public class ApplicationService {

    // ─── Storage ──────────────────────────────────────────────

    // In-memory list to store all student applications
    private ArrayList<Application> applications;

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Initializes the applications storage list.
     */
    public ApplicationService() {
        this.applications = new ArrayList<>();
    }

    // ─── Core Methods ─────────────────────────────────────────

    /**
     * Applies a student for a specific placement drive.
     *
     * Steps:
     * 1. Validate that studentId and driveId are not null or empty.
     * 2. Check if the student has already applied for the same drive.
     * 3. Generate a unique application ID.
     * 4. Create a new Application object with default status "Applied".
     * 5. Add the application to the list.
     *
     * @param studentId ID of the student applying (e.g., STU001)
     * @param driveId   ID of the drive being applied to (e.g., DRV001)
     * @return "SUCCESS" if application is successful, or an error message if failed
     */
    public String applyForDrive(String studentId, String driveId) {

        // ── Step 1: Validate studentId ──
        if (studentId == null || studentId.trim().isEmpty()) {
            return "ERROR: Student ID cannot be empty.";
        }

        // ── Step 2: Validate driveId ──
        if (driveId == null || driveId.trim().isEmpty()) {
            return "ERROR: Drive ID cannot be empty.";
        }

        // ── Step 3: Check for duplicate application ──
        // A student cannot apply for the same drive more than once
        for (Application app : applications) {
            if (app.getStudentId().equalsIgnoreCase(studentId.trim()) &&
                app.getDriveId().equalsIgnoreCase(driveId.trim())) {
                return "ERROR: You have already applied for this drive.";
            }
        }

        // ── Step 4: Generate a unique application ID ──
        String applicationId = generateApplicationId();

        // ── Step 5: Create a new Application with default status "Applied" ──
        Application newApplication = new Application(
                applicationId,
                studentId.trim(),
                driveId.trim()
                // Status is auto-set to "Applied" by the convenience constructor
        );

        // ── Step 6: Add application to the list ──
        applications.add(newApplication);

        return "SUCCESS"; // Application submitted successfully
    }

    /**
     * Retrieves all applications submitted by a specific student.
     *
     * @param studentId ID of the student whose applications are to be fetched
     * @return ArrayList of Application objects belonging to the student,
     *         or an empty list if no applications found
     */
    public ArrayList<Application> getApplicationsByStudent(String studentId) {

        // Result list to hold matching applications
        ArrayList<Application> studentApplications = new ArrayList<>();

        // Basic null/empty check — return empty list if invalid input
        if (studentId == null || studentId.trim().isEmpty()) {
            return studentApplications;
        }

        // Search for all applications matching the given studentId
        for (Application app : applications) {
            if (app.getStudentId().equalsIgnoreCase(studentId.trim())) {
                studentApplications.add(app); // Match found — add to result list
            }
        }

        return studentApplications; // Return all matching applications
    }

    // ─── Helper Methods ───────────────────────────────────────

    /**
     * Retrieves all applications submitted for a specific drive.
     * Useful for company dashboard to view applicants.
     *
     * @param driveId ID of the drive whose applicants are to be fetched
     * @return ArrayList of Application objects for the given drive,
     *         or an empty list if no applications found
     */
    public ArrayList<Application> getApplicationsByDrive(String driveId) {

        // Result list to hold matching applications
        ArrayList<Application> driveApplications = new ArrayList<>();

        // Basic null/empty check — return empty list if invalid input
        if (driveId == null || driveId.trim().isEmpty()) {
            return driveApplications;
        }

        // Search for all applications matching the given driveId
        for (Application app : applications) {
            if (app.getDriveId().equalsIgnoreCase(driveId.trim())) {
                driveApplications.add(app); // Match found — add to result list
            }
        }

        return driveApplications; // Return all matching applications
    }

    /**
     * Updates the status of a specific application.
     * Used by company or admin to shortlist or reject applicants.
     *
     * Valid status values: "Applied", "Shortlisted", "Rejected"
     *
     * @param applicationId ID of the application to update
     * @param newStatus     New status to set
     * @return "SUCCESS" if updated, or an error message if failed
     */
    public String updateApplicationStatus(String applicationId, String newStatus) {

        // Validate applicationId
        if (applicationId == null || applicationId.trim().isEmpty()) {
            return "ERROR: Application ID cannot be empty.";
        }

        // Validate newStatus
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return "ERROR: Status cannot be empty.";
        }

        // Search for the application and update its status
        for (Application app : applications) {
            if (app.getApplicationId().equalsIgnoreCase(applicationId.trim())) {

                // Use the validated setStatus() from Application model
                app.setStatus(newStatus.trim());
                return "SUCCESS"; // Status updated successfully
            }
        }

        return "ERROR: Application with ID '" + applicationId + "' not found.";
    }

    /**
     * Retrieves all applications stored in the system.
     * Useful for admin management and overview.
     *
     * @return ArrayList of all Application objects
     */
    public ArrayList<Application> getAllApplications() {
        return applications;
    }

    /**
     * Checks if a specific student has already applied for a specific drive.
     * Useful for disabling the Apply button in the GUI.
     *
     * @param studentId ID of the student
     * @param driveId   ID of the drive
     * @return true if already applied, false otherwise
     */
    public boolean hasAlreadyApplied(String studentId, String driveId) {

        // Search for an existing application matching both IDs
        for (Application app : applications) {
            if (app.getStudentId().equalsIgnoreCase(studentId) &&
                app.getDriveId().equalsIgnoreCase(driveId)) {
                return true; // Duplicate application found
            }
        }

        return false; // No existing application found
    }

    /**
     * Generates a new unique Application ID based on current application count.
     * Format: APP001, APP002, APP003, ...
     *
     * @return A new unique application ID string
     */
    private String generateApplicationId() {
        int next = applications.size() + 1;      // Next number in sequence
        return String.format("APP%03d", next);   // Format as APP001, APP002, etc.
    }
}