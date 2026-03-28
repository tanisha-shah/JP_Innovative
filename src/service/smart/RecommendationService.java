// File: src/service/smart/RecommendationService.java
// Package: service.smart
// Description: Handles smart drive recommendation logic for students.

package service.smart;

import model.Student;
import model.Drive;

import java.util.ArrayList;
import java.util.List;

/**
 * RecommendationService class.
 * Smart service that recommends eligible placement drives to a student.
 *
 * Uses EligibilityService internally to filter drives based on:
 * - Student's CGPA vs Drive's minimum CGPA requirement
 * - Student's branch vs Drive's eligible branches list
 *
 * Used by GUI pages like EligibleDrivesPage and StudentDashboard.
 */
public class RecommendationService {

    // ─── Dependency ───────────────────────────────────────────

    // EligibilityService is used internally to check each drive
    private EligibilityService eligibilityService;

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Initializes the EligibilityService dependency.
     */
    public RecommendationService() {
        this.eligibilityService = new EligibilityService();
    }

    // ─── Core Method ──────────────────────────────────────────

    /**
     * Returns a list of drives that the given student is eligible for.
     *
     * Steps:
     * 1. Validate that student and drives list are not null.
     * 2. Loop through all available drives.
     * 3. Use EligibilityService to check each drive.
     * 4. Collect and return only the eligible drives.
     *
     * @param student The Student object to check eligibility for
     * @param drives  Full list of available placement drives
     * @return ArrayList of Drive objects the student is eligible for,
     *         or an empty list if none found
     */
    public ArrayList<Drive> getEligibleDrives(Student student, List<Drive> drives) {

        // Result list to hold all eligible drives
        ArrayList<Drive> eligibleDrives = new ArrayList<>();

        // ── Step 1: Null safety check on student ──
        if (student == null) {
            System.out.println("[RecommendationService] WARNING: " +
                               "Student object is null. Returning empty list.");
            return eligibleDrives;
        }

        // ── Step 2: Null or empty check on drives list ──
        if (drives == null || drives.isEmpty()) {
            System.out.println("[RecommendationService] WARNING: " +
                               "Drives list is null or empty. Returning empty list.");
            return eligibleDrives;
        }

        // ── Step 3: Loop through all drives and check eligibility ──
        for (Drive drive : drives) {

            // Skip null drive entries to avoid NullPointerException
            if (drive == null) {
                continue;
            }

            // Use EligibilityService to check if student qualifies for this drive
            if (eligibilityService.isEligible(student, drive)) {
                eligibleDrives.add(drive); // Student is eligible — add to result
            }
        }

        // ── Step 4: Log summary ──
        System.out.println("[RecommendationService] Student: " + student.getName()
                + " | Total Drives: "    + drives.size()
                + " | Eligible Drives: " + eligibleDrives.size());

        return eligibleDrives; // Return all eligible drives
    }

    // ─── Helper Methods ───────────────────────────────────────

    /**
     * Returns the count of drives a student is eligible for.
     * Useful for displaying a count badge on the StudentDashboard.
     *
     * @param student The Student object
     * @param drives  Full list of available placement drives
     * @return Number of drives the student is eligible for
     */
    public int getEligibleDriveCount(Student student, List<Drive> drives) {
        return getEligibleDrives(student, drives).size();
    }

    /**
     * Checks if a student is eligible for at least one drive.
     * Useful for showing alerts or tips on the StudentDashboard.
     *
     * @param student The Student object
     * @param drives  Full list of available placement drives
     * @return true if eligible for at least one drive, false otherwise
     */
    public boolean hasAnyEligibleDrive(Student student, List<Drive> drives) {

        // Null safety
        if (student == null || drives == null || drives.isEmpty()) {
            return false;
        }

        // Check each drive — return true as soon as one eligible drive is found
        for (Drive drive : drives) {
            if (drive != null && eligibilityService.isEligible(student, drive)) {
                return true; // At least one eligible drive found
            }
        }

        return false; // No eligible drives found
    }

    /**
     * Returns a summary string of all eligible drives for a student.
     * Useful for displaying a quick overview in the GUI or notifications.
     *
     * Example output:
     * "Eligible Drives for Alice:
     *  1. Google - Software Engineer (Min CGPA: 7.5)
     *  2. TCS    - System Analyst   (Min CGPA: 6.0)"
     *
     * @param student The Student object
     * @param drives  Full list of available placement drives
     * @return Formatted string listing all eligible drives
     */
    public String getEligibleDrivesSummary(Student student, List<Drive> drives) {

        // Null check
        if (student == null) {
            return "ERROR: Invalid student data.";
        }

        // Get all eligible drives
        ArrayList<Drive> eligibleDrives = getEligibleDrives(student, drives);

        // No eligible drives found
        if (eligibleDrives.isEmpty()) {
            return "No eligible drives found for " + student.getName() + ".\n"
                 + "Tip: Improve your CGPA or update your branch details.";
        }

        // Build summary string
        StringBuilder summary = new StringBuilder();
        summary.append("Eligible Drives for ")
               .append(student.getName())
               .append(":\n");

        // List each eligible drive with index
        int index = 1;
        for (Drive drive : eligibleDrives) {
            summary.append(index)
                   .append(". ")
                   .append(drive.getCompanyName())
                   .append(" - ")
                   .append(drive.getRole())
                   .append(" (Min CGPA: ")
                   .append(drive.getMinCGPA())
                   .append(")\n");
            index++;
        }

        return summary.toString().trim(); // Return clean summary string
    }
}