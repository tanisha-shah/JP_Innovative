// File: src/service/DriveService.java
// Package: service
// Description: Handles all Campus Drive related operations in the Smart Campus Placement System.

package service;

import model.Drive;

import java.util.ArrayList;

/**
 * DriveService class.
 * Handles all operations related to campus placement drives:
 * - Adding a new drive
 * - Retrieving all available drives
 *
 * Uses simple ArrayList storage (no database).
 * Performs basic input validation before adding a drive.
 */
public class DriveService {

    // ─── Storage ──────────────────────────────────────────────

    // In-memory list to store all campus placement drives
    private ArrayList<Drive> drives;

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Initializes the drives storage list.
     * Pre-loads sample drives for testing and demonstration purposes.
     */
    public DriveService() {
        this.drives = new ArrayList<>();

        // ── Pre-load sample drives for demonstration ──

        // Sample Drive 1: Google - Software Engineer
        java.util.List<String> branches1 = new ArrayList<>();
        branches1.add("CSE");
        branches1.add("IT");

        java.util.List<String> skills1 = new ArrayList<>();
        skills1.add("Java");
        skills1.add("Data Structures");
        skills1.add("Problem Solving");

        drives.add(new Drive(
                "DRV001",
                "Google",
                "Software Engineer",
                7.5,
                branches1,
                skills1
        ));

        // Sample Drive 2: TCS - System Analyst
        java.util.List<String> branches2 = new ArrayList<>();
        branches2.add("CSE");
        branches2.add("IT");
        branches2.add("ECE");

        java.util.List<String> skills2 = new ArrayList<>();
        skills2.add("SQL");
        skills2.add("Communication");
        skills2.add("Python");

        drives.add(new Drive(
                "DRV002",
                "TCS",
                "System Analyst",
                6.0,
                branches2,
                skills2
        ));

        // Sample Drive 3: Infosys - Java Developer
        java.util.List<String> branches3 = new ArrayList<>();
        branches3.add("CSE");
        branches3.add("MCA");

        java.util.List<String> skills3 = new ArrayList<>();
        skills3.add("Java");
        skills3.add("Spring Boot");
        skills3.add("MySQL");

        drives.add(new Drive(
                "DRV003",
                "Infosys",
                "Java Developer",
                6.5,
                branches3,
                skills3
        ));
    }

    // ─── Core Methods ─────────────────────────────────────────

    /**
     * Adds a new placement drive to the system.
     *
     * Steps:
     * 1. Validate that all required fields are not null or empty.
     * 2. Check that minCGPA is within a valid range (0.0 to 10.0).
     * 3. Ensure eligibleBranches and requiredSkills are not empty.
     * 4. Check for duplicate drive ID.
     * 5. Add the drive to the list if all validations pass.
     *
     * @param drive Fully initialized Drive object to be added
     * @return "SUCCESS" if added successfully, or an error message string if failed
     */
    public String addDrive(Drive drive) {

        // ── Step 1: Null check on drive object ──
        if (drive == null) {
            return "ERROR: Drive data is invalid.";
        }

        // ── Step 2: Validate drive ID ──
        if (drive.getId() == null || drive.getId().trim().isEmpty()) {
            return "ERROR: Drive ID cannot be empty.";
        }

        // ── Step 3: Validate company name ──
        if (drive.getCompanyName() == null || drive.getCompanyName().trim().isEmpty()) {
            return "ERROR: Company name cannot be empty.";
        }

        // ── Step 4: Validate role ──
        if (drive.getRole() == null || drive.getRole().trim().isEmpty()) {
            return "ERROR: Job role cannot be empty.";
        }

        // ── Step 5: Validate minCGPA range ──
        if (drive.getMinCGPA() < 0.0 || drive.getMinCGPA() > 10.0) {
            return "ERROR: Minimum CGPA must be between 0.0 and 10.0.";
        }

        // ── Step 6: Validate eligible branches list ──
        if (drive.getEligibleBranches() == null || drive.getEligibleBranches().isEmpty()) {
            return "ERROR: At least one eligible branch must be specified.";
        }

        // ── Step 7: Validate required skills list ──
        if (drive.getRequiredSkills() == null || drive.getRequiredSkills().isEmpty()) {
            return "ERROR: At least one required skill must be specified.";
        }

        // ── Step 8: Check for duplicate drive ID ──
        for (Drive d : drives) {
            if (d.getId().equalsIgnoreCase(drive.getId().trim())) {
                return "ERROR: A drive with ID '" + drive.getId() + "' already exists.";
            }
        }

        // ── Step 9: All validations passed — add the drive ──
        drives.add(drive);
        return "SUCCESS"; // Drive added successfully
    }

    /**
     * Retrieves all campus placement drives stored in the system.
     *
     * @return ArrayList of all Drive objects (may be empty if none added yet)
     */
    public ArrayList<Drive> getAllDrives() {
        return drives;
    }

    // ─── Helper Methods ───────────────────────────────────────

    /**
     * Finds and returns a Drive by its unique ID.
     * Useful for application and eligibility checks.
     *
     * @param driveId The unique ID of the drive to find
     * @return The matching Drive object, or null if not found
     */
    public Drive getDriveById(String driveId) {

        // Basic null/empty check
        if (driveId == null || driveId.trim().isEmpty()) {
            return null;
        }

        // Search for the drive by ID
        for (Drive d : drives) {
            if (d.getId().equalsIgnoreCase(driveId.trim())) {
                return d; // Drive found — return it
            }
        }

        return null; // No matching drive found
    }

    /**
     * Returns the total number of drives currently in the system.
     * Useful for generating new unique drive IDs.
     *
     * @return Total count of drives
     */
    public int getDriveCount() {
        return drives.size();
    }

    /**
     * Generates a new unique Drive ID based on current drive count.
     * Format: DRV001, DRV002, DRV003, ...
     *
     * @return A new unique drive ID string
     */
    public String generateDriveId() {
        int next = drives.size() + 1;           // Next number in sequence
        return String.format("DRV%03d", next);  // Format as DRV001, DRV002, etc.
    }
}