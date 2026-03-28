// File: src/service/smart/ResumeAnalyzer.java
// Package: service.smart
// Description: Analyzes a student's resume and calculates a score out of 100.

package service.smart;

import model.Student;

/**
 * ResumeAnalyzer class.
 * Smart service that calculates a resume score for a student
 * based on their academic performance and skill set.
 *
 * Scoring breakdown (Total: 100 points):
 * ─────────────────────────────────────────
 * 1. CGPA Score     : Up to 50 points
 *    Formula        : (CGPA / 10.0) * 50
 *    Example        : CGPA 8.0 → (8.0 / 10.0) * 50 = 40 points
 *
 * 2. Skills Score   : Up to 50 points
 *    Formula        : min(numberOfSkills * 5, 50)
 *    Example        : 7 skills → 7 * 5 = 35 points
 *                   : 10+ skills → capped at 50 points
 * ─────────────────────────────────────────
 * Total Score = CGPA Score + Skills Score (max 100)
 *
 * Used by GUI page: ResumeScorePage.java
 */
public class ResumeAnalyzer {

    // ─── Scoring Constants ────────────────────────────────────

    /** Maximum score contribution from CGPA (out of 100) */
    private static final double MAX_CGPA_SCORE   = 50.0;

    /** Maximum score contribution from Skills (out of 100) */
    private static final double MAX_SKILLS_SCORE = 50.0;

    /** Points awarded per skill */
    private static final double POINTS_PER_SKILL = 5.0;

    /** Maximum CGPA value used for normalization */
    private static final double MAX_CGPA         = 10.0;

    // ─── Core Method ──────────────────────────────────────────

    /**
     * Calculates a resume score for the given student out of 100.
     *
     * Scoring formula:
     * - CGPA Score  = (student.getCgpa() / 10.0) * 50
     * - Skills Score = min(numberOfSkills * 5, 50)
     * - Total Score  = CGPA Score + Skills Score
     *
     * @param student The Student object to calculate score for
     * @return Resume score as a double between 0.0 and 100.0,
     *         or -1.0 if student object is null (error case)
     */
    public double calculateScore(Student student) {

        // ── Step 1: Null safety check ──
        if (student == null) {
            System.out.println("[ResumeAnalyzer] WARNING: " +
                               "Student object is null. Cannot calculate score.");
            return -1.0; // Return -1 to indicate an error
        }

        // ── Step 2: Calculate CGPA score ──
        double cgpaScore = calculateCGPAScore(student.getCgpa());

        // ── Step 3: Calculate Skills score ──
        int    skillCount  = (student.getSkills() != null)
                              ? student.getSkills().size()
                              : 0;
        double skillsScore = calculateSkillsScore(skillCount);

        // ── Step 4: Calculate total score ──
        double totalScore = cgpaScore + skillsScore;

        // ── Step 5: Clamp score between 0 and 100 (safety net) ──
        totalScore = Math.max(0.0, Math.min(100.0, totalScore));

        // ── Step 6: Round to 2 decimal places for clean display ──
        totalScore = Math.round(totalScore * 100.0) / 100.0;

        // ── Step 7: Log score breakdown ──
        System.out.println("[ResumeAnalyzer] Student : " + student.getName());
        System.out.println("[ResumeAnalyzer] CGPA    : " + student.getCgpa()
                         + " → CGPA Score   : " + cgpaScore);
        System.out.println("[ResumeAnalyzer] Skills  : " + skillCount
                         + " → Skills Score : " + skillsScore);
        System.out.println("[ResumeAnalyzer] Total Resume Score : "
                         + totalScore + " / 100");

        return totalScore; // Final resume score out of 100
    }

    // ─── Helper Methods ───────────────────────────────────────

    /**
     * Calculates the CGPA component of the resume score.
     * Formula: (cgpa / 10.0) * 50
     * Maximum contribution: 50 points
     *
     * @param cgpa Student's CGPA (expected between 0.0 and 10.0)
     * @return CGPA score contribution (0.0 to 50.0)
     */
    public double calculateCGPAScore(double cgpa) {

        // Clamp CGPA between 0.0 and 10.0 to handle invalid inputs
        cgpa = Math.max(0.0, Math.min(MAX_CGPA, cgpa));

        // Normalize CGPA to a score out of 50
        return (cgpa / MAX_CGPA) * MAX_CGPA_SCORE;
    }

    /**
     * Calculates the Skills component of the resume score.
     * Formula: min(numberOfSkills * 5, 50)
     * Maximum contribution: 50 points (capped at 10 skills)
     *
     * @param skillCount Number of skills the student has listed
     * @return Skills score contribution (0.0 to 50.0)
     */
    public double calculateSkillsScore(int skillCount) {

        // Ensure skill count is not negative
        skillCount = Math.max(0, skillCount);

        // Each skill contributes 5 points, capped at MAX_SKILLS_SCORE (50)
        double rawScore = skillCount * POINTS_PER_SKILL;
        return Math.min(rawScore, MAX_SKILLS_SCORE);
    }

    /**
     * Returns a grade label based on the resume score.
     * Useful for displaying a letter grade alongside the score in the GUI.
     *
     * Grade scale:
     * 85 - 100 → Excellent
     * 70 -  84 → Good
     * 50 -  69 → Average
     * 30 -  49 → Below Average
     *  0 -  29 → Poor
     *
     * @param score The calculated resume score (0.0 to 100.0)
     * @return Grade label as a String
     */
    public String getGradeLabel(double score) {

        if (score >= 85.0) {
            return "Excellent";   // Top tier resume
        } else if (score >= 70.0) {
            return "Good";        // Strong resume
        } else if (score >= 50.0) {
            return "Average";     // Decent resume
        } else if (score >= 30.0) {
            return "Below Average"; // Needs improvement
        } else {
            return "Poor";        // Significant improvement needed
        }
    }

    /**
     * Returns improvement tips based on the student's resume score.
     * Useful for displaying actionable advice on the ResumeScorePage.
     *
     * @param student The Student object
     * @return A formatted string with personalized improvement tips
     */
    public String getImprovementTips(Student student) {

        // Null check
        if (student == null) {
            return "ERROR: Invalid student data.";
        }

        StringBuilder tips = new StringBuilder();
        tips.append("Improvement Tips for ").append(student.getName()).append(":\n\n");

        // ── CGPA Tips ──
        if (student.getCgpa() < 6.0) {
            tips.append("• Your CGPA is below 6.0. Focus on improving your academics.\n");
        } else if (student.getCgpa() < 7.5) {
            tips.append("• Your CGPA is decent. Aim for 7.5+ to qualify for more drives.\n");
        } else if (student.getCgpa() < 9.0) {
            tips.append("• Good CGPA! Push towards 9.0+ to maximize your CGPA score.\n");
        } else {
            tips.append("• Excellent CGPA! Keep maintaining your academic performance.\n");
        }

        // ── Skills Tips ──
        int skillCount = (student.getSkills() != null) ? student.getSkills().size() : 0;

        if (skillCount == 0) {
            tips.append("• You have no skills listed. Add technical skills to boost your score.\n");
        } else if (skillCount < 3) {
            tips.append("• You have only " + skillCount + " skill(s). "
                      + "Add more skills to increase your resume score.\n");
        } else if (skillCount < 7) {
            tips.append("• You have " + skillCount + " skills. "
                      + "Adding " + (10 - skillCount) + " more will maximize your skills score.\n");
        } else if (skillCount < 10) {
            tips.append("• Good skill set! Add " + (10 - skillCount)
                      + " more skill(s) to reach the maximum skills score.\n");
        } else {
            tips.append("• Great skill set! You have reached the maximum skills score.\n");
        }

        // ── Overall score tip ──
        double score = calculateScore(student);
        tips.append("\nYour current resume score: ")
            .append(score)
            .append(" / 100 (")
            .append(getGradeLabel(score))
            .append(")");

        return tips.toString().trim();
    }

    /**
     * Returns a full formatted resume score report for display on ResumeScorePage.
     *
     * Example output:
     * ─────────────────────────────
     * Resume Score Report
     * Student  : Alice
     * ─────────────────────────────
     * CGPA     : 8.5  → 42.5 / 50
     * Skills   : 6    → 30.0 / 50
     * ─────────────────────────────
     * Total    : 72.5 / 100  [Good]
     * ─────────────────────────────
     *
     * @param student The Student object
     * @return Formatted resume score report as a String
     */
    public String getScoreReport(Student student) {

        // Null check
        if (student == null) {
            return "ERROR: Invalid student data.";
        }

        int    skillCount  = (student.getSkills() != null)
                              ? student.getSkills().size() : 0;
        double cgpaScore   = calculateCGPAScore(student.getCgpa());
        double skillsScore = calculateSkillsScore(skillCount);
        double totalScore  = Math.round((cgpaScore + skillsScore) * 100.0) / 100.0;
        String grade       = getGradeLabel(totalScore);

        // Build report string
        String line = "─────────────────────────────────\n";

        return  line
              + "       Resume Score Report        \n"
              + line
              + "Student  : " + student.getName()  + "\n"
              + line
              + "CGPA     : " + student.getCgpa()  + "  →  "
                              + cgpaScore           + " / 50\n"
              + "Skills   : " + skillCount          + "  →  "
                              + skillsScore         + " / 50\n"
              + line
              + "Total    : " + totalScore          + " / 100"
              + "  [" + grade + "]\n"
              + line;
    }
}