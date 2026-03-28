// File: src/service/smart/EligibilityService.java
// Package: service.smart
// Description: Handles eligibility checking logic for students and drives.

package service.smart;

import model.Student;
import model.Drive;

/**
 * EligibilityService class.
 * Smart service that checks whether a student is eligible
 * for a specific campus placement drive.
 *
 * Eligibility is determined by two criteria:
 * 1. Student's CGPA must meet the drive's minimum CGPA requirement.
 * 2. Student's branch must be in the drive's list of eligible branches.
 *
 * Used by GUI pages like EligibleDrivesPage and ApplyDrivePage.
 */
public class EligibilityService {

    // ─── Core Method ──────────────────────────────────────────

    /**
     * Checks whether a given student is eligible for a given drive.
     *
     * Eligibility criteria:
     * - Student's CGPA >= Drive's minimum CGPA requirement
     * - Student's branch is present in the drive's eligible branches list
     *
     * @param student The Student object to check eligibility for
     * @param drive   The Drive object to check eligibility against
     * @return true if the student meets all eligibility criteria, false otherwise
     */
    public boolean isEligible(Student student, Drive drive) {

        // ── Step 1: Null safety check ──
        // If either object is null, eligibility cannot be determined
        if (student == null || drive == null) {
            System.out.println("[EligibilityService] WARNING: " +
                               "Student or Drive object is null.");
            return false;
        }

        // ── Step 2: Check CGPA eligibility ──
        // Student's CGPA must be greater than or equal to drive's minimum CGPA
        if (!isCGPAEligible(student, drive)) {
            return false; // CGPA does not meet the requirement
        }

        // ── Step 3: Check branch eligibility ──
        // Student's branch must be in the drive's list of eligible branches
        if (!isBranchEligible(student, drive)) {
            return false; // Branch is not in the eligible list
        }

        // ── All checks passed ──
        return true; // Student is fully eligible for this drive
    }

    // ─── Helper Methods ───────────────────────────────────────

    /**
     * Checks if the student's CGPA meets the drive's minimum CGPA requirement.
     *
     * @param student The Student object
     * @param drive   The Drive object
     * @return true if student's CGPA >= drive's minCGPA, false otherwise
     */
    public boolean isCGPAEligible(Student student, Drive drive) {

        // Compare student CGPA against drive's minimum required CGPA
        if (student.getCgpa() >= drive.getMinCGPA()) {
            return true; // CGPA requirement satisfied
        }

        // Log reason for ineligibility (helpful for debugging)
        System.out.println("[EligibilityService] CGPA check failed for student: "
                + student.getName()
                + " | Student CGPA: " + student.getCgpa()
                + " | Required: "     + drive.getMinCGPA());

        return false; // CGPA too low
    }

    /**
     * Checks if the student's branch is in the drive's eligible branches list.
     * Comparison is case-insensitive (e.g., "cse" matches "CSE").
     *
     * @param student The Student object
     * @param drive   The Drive object
     * @return true if student's branch is in the eligible branches list,
     *         false otherwise
     */
    public boolean isBranchEligible(Student student, Drive drive) {

        // Safety check — if branch list is null or empty, no one is eligible
        if (drive.getEligibleBranches() == null ||
            drive.getEligibleBranches().isEmpty()) {
            System.out.println("[EligibilityService] WARNING: " +
                               "Drive has no eligible branches defined.");
            return false;
        }

        // Loop through all eligible branches in the drive
        for (String branch : drive.getEligibleBranches()) {

            // Case-insensitive comparison to avoid mismatches like "CSE" vs "cse"
            if (branch.equalsIgnoreCase(student.getBranch())) {
                return true; // Branch match found
            }
        }

        // Log reason for ineligibility (helpful for debugging)
        System.out.println("[EligibilityService] Branch check failed for student: "
                + student.getName()
                + " | Student Branch: " + student.getBranch()
                + " | Eligible Branches: " + drive.getEligibleBranches());

        return false; // Branch not in eligible list
    }

    /**
     * Returns a human-readable eligibility summary for a student and drive.
     * Useful for displaying detailed feedback in the GUI.
     *
     * Example output:
     * "CGPA: PASS | Branch: PASS | Overall: ELIGIBLE"
     * "CGPA: FAIL (Required 7.5, You have 6.2) | Branch: PASS | Overall: NOT ELIGIBLE"
     *
     * @param student The Student object
     * @param drive   The Drive object
     * @return A formatted eligibility summary string
     */
    public String getEligibilitySummary(Student student, Drive drive) {

        // Null check
        if (student == null || drive == null) {
            return "ERROR: Invalid student or drive data.";
        }

        // ── CGPA status ──
        boolean cgpaPass   = isCGPAEligible(student, drive);
        String  cgpaStatus = cgpaPass
                ? "CGPA : PASS (" + student.getCgpa() + " >= " + drive.getMinCGPA() + ")"
                : "CGPA : FAIL  (Required " + drive.getMinCGPA()
                  + ", You have " + student.getCgpa() + ")";

        // ── Branch status ──
        boolean branchPass   = isBranchEligible(student, drive);
        String  branchStatus = branchPass
                ? "Branch : PASS (" + student.getBranch() + " is eligible)"
                : "Branch : FAIL  (" + student.getBranch()
                  + " not in " + drive.getEligibleBranches() + ")";

        // ── Overall eligibility ──
        String overall = (cgpaPass && branchPass)
                ? "Overall : ELIGIBLE"
                : "Overall : NOT ELIGIBLE";

        // Combine all parts into a readable summary
        return cgpaStatus + "\n" + branchStatus + "\n" + overall;
    }
}