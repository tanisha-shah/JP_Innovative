// File: src/service/smart/SkillGapService.java
// Package: service.smart
// Description: Handles skill gap analysis between a student and a drive requirement.

package service.smart;

import model.Student;
import model.Drive;

import java.util.ArrayList;
import java.util.List;

/**
 * SkillGapService class.
 * Smart service that analyzes the gap between a student's current skills
 * and the skills required for a specific placement drive.
 *
 * The skill gap is the list of skills that the drive requires
 * but the student does not currently possess.
 *
 * Used by GUI pages like SkillGapPage and EligibleDrivesPage.
 */
public class SkillGapService {

    // ─── Core Method ──────────────────────────────────────────

    /**
     * Calculates the skill gap between a student and a drive.
     *
     * The skill gap is defined as:
     * Skills required by the drive - Skills already possessed by the student
     *
     * Steps:
     * 1. Validate that student and drive objects are not null.
     * 2. Retrieve required skills from the drive.
     * 3. Retrieve current skills from the student.
     * 4. Compare both lists (case-insensitive).
     * 5. Return skills the student is missing.
     *
     * @param student The Student object to analyze
     * @param drive   The Drive object containing required skills
     * @return List of skill names the student is missing,
     *         or an empty list if no skill gap exists
     */
    public List<String> getSkillGap(Student student, Drive drive) {

        // Result list to hold all missing skills
        List<String> missingSkills = new ArrayList<>();

        // ── Step 1: Null safety check ──
        if (student == null) {
            System.out.println("[SkillGapService] WARNING: " +
                               "Student object is null. Returning empty list.");
            return missingSkills;
        }

        if (drive == null) {
            System.out.println("[SkillGapService] WARNING: " +
                               "Drive object is null. Returning empty list.");
            return missingSkills;
        }

        // ── Step 2: Get required skills from drive ──
        List<String> requiredSkills = drive.getRequiredSkills();

        // If no skills required by drive, no gap exists
        if (requiredSkills == null || requiredSkills.isEmpty()) {
            System.out.println("[SkillGapService] INFO: " +
                               "Drive has no required skills defined. No gap.");
            return missingSkills;
        }

        // ── Step 3: Get current skills from student ──
        List<String> studentSkills = student.getSkills();

        // If student has no skills at all, all required skills are missing
        if (studentSkills == null || studentSkills.isEmpty()) {
            System.out.println("[SkillGapService] INFO: " +
                               "Student has no skills listed. All required skills are missing.");
            missingSkills.addAll(requiredSkills); // All required skills are gaps
            return missingSkills;
        }

        // ── Step 4: Compare skills — case-insensitive matching ──
        for (String requiredSkill : requiredSkills) {

            // Check if student already has this required skill
            if (!studentHasSkill(studentSkills, requiredSkill)) {
                missingSkills.add(requiredSkill); // Student is missing this skill
            }
        }

        // ── Step 5: Log summary ──
        System.out.println("[SkillGapService] Student: " + student.getName()
                + " | Drive: "          + drive.getRole()
                + " | Required Skills: " + requiredSkills.size()
                + " | Missing Skills: "  + missingSkills.size());

        return missingSkills; // Return all missing skills
    }

    // ─── Helper Methods ───────────────────────────────────────

    /**
     * Checks if a student's skill list contains a specific skill.
     * Comparison is case-insensitive (e.g., "java" matches "Java").
     *
     * @param studentSkills  List of skills the student currently has
     * @param requiredSkill  The skill to check for
     * @return true if the student has the skill, false otherwise
     */
    private boolean studentHasSkill(List<String> studentSkills, String requiredSkill) {

        // Loop through student's skills and compare case-insensitively
        for (String skill : studentSkills) {
            if (skill.equalsIgnoreCase(requiredSkill)) {
                return true; // Skill found in student's list
            }
        }

        return false; // Skill not found
    }

    /**
     * Returns the count of missing skills for a student and drive.
     * Useful for displaying a skill gap score or badge in the GUI.
     *
     * @param student The Student object
     * @param drive   The Drive object
     * @return Number of skills the student is missing
     */
    public int getSkillGapCount(Student student, Drive drive) {
        return getSkillGap(student, drive).size();
    }

    /**
     * Checks if a student has zero skill gap for a drive.
     * Useful for highlighting "fully skilled" drives in the GUI.
     *
     * @param student The Student object
     * @param drive   The Drive object
     * @return true if student has all required skills, false otherwise
     */
    public boolean hasNoSkillGap(Student student, Drive drive) {
        return getSkillGap(student, drive).isEmpty();
    }

    /**
     * Returns a list of skills the student already has that match
     * the drive's required skills.
     * Useful for showing "matched skills" alongside the gap.
     *
     * @param student The Student object
     * @param drive   The Drive object
     * @return List of skills the student already has that the drive requires
     */
    public List<String> getMatchedSkills(Student student, Drive drive) {

        // Result list to hold all matched skills
        List<String> matchedSkills = new ArrayList<>();

        // Null safety check
        if (student == null || drive == null) {
            return matchedSkills;
        }

        List<String> requiredSkills = drive.getRequiredSkills();
        List<String> studentSkills  = student.getSkills();

        // Safety check on lists
        if (requiredSkills == null || requiredSkills.isEmpty() ||
            studentSkills  == null || studentSkills.isEmpty()) {
            return matchedSkills;
        }

        // Find skills that appear in both lists
        for (String requiredSkill : requiredSkills) {
            if (studentHasSkill(studentSkills, requiredSkill)) {
                matchedSkills.add(requiredSkill); // Skill matched
            }
        }

        return matchedSkills;
    }

    /**
     * Returns a human-readable skill gap summary for a student and drive.
     * Useful for displaying detailed feedback on the SkillGapPage.
     *
     * Example output:
     * "Skill Gap Analysis for Alice — Software Engineer at Google:
     *  Matched Skills (2): Java, Problem Solving
     *  Missing Skills (1): Data Structures
     *  Tip: Work on the missing skills to improve your chances!"
     *
     * @param student The Student object
     * @param drive   The Drive object
     * @return Formatted string showing matched and missing skills
     */
    public String getSkillGapSummary(Student student, Drive drive) {

        // Null check
        if (student == null || drive == null) {
            return "ERROR: Invalid student or drive data.";
        }

        // Get matched and missing skill lists
        List<String> missingSkills = getSkillGap(student, drive);
        List<String> matchedSkills = getMatchedSkills(student, drive);

        // Build summary string
        StringBuilder summary = new StringBuilder();

        summary.append("Skill Gap Analysis for ")
               .append(student.getName())
               .append(" — ")
               .append(drive.getRole())
               .append(" at ")
               .append(drive.getCompanyName())
               .append(":\n\n");

        // ── Matched Skills ──
        summary.append("Matched Skills (")
               .append(matchedSkills.size())
               .append("): ");
        if (matchedSkills.isEmpty()) {
            summary.append("None");
        } else {
            summary.append(String.join(", ", matchedSkills));
        }

        summary.append("\n");

        // ── Missing Skills ──
        summary.append("Missing Skills (")
               .append(missingSkills.size())
               .append("): ");
        if (missingSkills.isEmpty()) {
            summary.append("None — You have all required skills!");
        } else {
            summary.append(String.join(", ", missingSkills));
        }

        summary.append("\n\n");

        // ── Tip message ──
        if (missingSkills.isEmpty()) {
            summary.append("Great job! You meet all skill requirements for this drive.");
        } else {
            summary.append("Tip: Work on the missing skills to improve your chances!");
        }

        return summary.toString().trim();
    }
}