// Package name - this file belongs to the service.smart package
package service.smart;

// We need Student class from model package
import model.Student;

// ResumeAnalyzer class - this is a SMART FEATURE
// It calculates a simple resume score for a student
// Score is based on two things:
//   1. Student's CGPA
//   2. Number of skills student has
// Final score is out of 100
public class ResumeAnalyzer {

    // --- We define maximum points for each section ---

    // CGPA can give maximum 60 points out of 100
    private int maxCgpaScore = 60;

    // Skills can give maximum 40 points out of 100
    private int maxSkillScore = 40;

    // Each skill gives 8 points (so 5 skills = 40 points max)
    private int pointsPerSkill = 8;

    // Maximum CGPA is 10.0 in most colleges
    private double maxCgpa = 10.0;

    // --- Main score calculation method ---
    // This method takes a Student object
    // Calculates their resume score out of 100
    // Returns the final score as an integer
    public int calculateScore(Student s) {

        System.out.println("\n=============================");
        System.out.println("     RESUME SCORE REPORT     ");
        System.out.println("=============================");
        System.out.println("Student  : " + s.getName());
        System.out.println("Branch   : " + s.getBranch());
        System.out.println("CGPA     : " + s.getCgpa());
        System.out.println("Skills   : " + s.getSkills().size());
        System.out.println("-----------------------------");

        // --- Step 1: Calculate CGPA Score ---
        // Formula: (studentCGPA / maxCGPA) * maxCgpaScore
        // Example: CGPA = 8.0 → (8.0 / 10.0) * 60 = 48 points
        int cgpaScore = (int) ((s.getCgpa() / maxCgpa) * maxCgpaScore);

        System.out.println("📊 CGPA Score Calculation:");
        System.out.println("   Formula  : (CGPA / 10.0) x 60");
        System.out.println("   Result   : (" + s.getCgpa() + " / 10.0) x 60 = " + cgpaScore + " points");

        // --- Step 2: Calculate Skills Score ---
        // Each skill gives 8 points
        // Maximum skill score is 40 points (so max 5 skills counted)
        // If student has more than 5 skills, we still give only 40 points max
        int numberOfSkills = s.getSkills().size();
        int skillScore     = numberOfSkills * pointsPerSkill;

        // Make sure skill score does not go above 40
        if (skillScore > maxSkillScore) {
            skillScore = maxSkillScore; // cap it at 40
        }

        System.out.println("\n📊 Skills Score Calculation:");
        System.out.println("   Formula  : Number of Skills x 8 (max 40)");
        System.out.println("   Skills   : " + numberOfSkills + " skill(s) found");
        System.out.println("   Result   : " + numberOfSkills + " x 8 = " + (numberOfSkills * pointsPerSkill) + " points");

        // If score was capped, show a message
        if (numberOfSkills * pointsPerSkill > maxSkillScore) {
            System.out.println("   Capped at: 40 points (maximum skill score)");
        }

        // --- Step 3: Add both scores to get final score ---
        int finalScore = cgpaScore + skillScore;

        // Just in case, make sure final score does not exceed 100
        if (finalScore > 100) {
            finalScore = 100;
        }

        // --- Step 4: Display final result ---
        System.out.println("\n-----------------------------");
        System.out.println("CGPA Score   : " + cgpaScore  + " / 60");
        System.out.println("Skills Score : " + skillScore + " / 40");
        System.out.println("-----------------------------");
        System.out.println("TOTAL SCORE  : " + finalScore + " / 100");
        System.out.println("-----------------------------");

        // Show a rating based on the score
        System.out.println("Rating       : " + getRating(finalScore));
        System.out.println("=============================");

        return finalScore;
    }

    // --- Get rating based on score ---
    // This method gives a simple text rating based on the score
    // Like grades: Excellent, Good, Average, Poor
    public String getRating(int score) {

        // Check score range and return matching rating
        if (score >= 85) {
            return "⭐⭐⭐⭐⭐ Excellent! Your resume is very strong!";
        } else if (score >= 70) {
            return "⭐⭐⭐⭐  Good! Your resume is above average.";
        } else if (score >= 50) {
            return "⭐⭐⭐    Average. Try adding more skills.";
        } else if (score >= 30) {
            return "⭐⭐      Below Average. Improve CGPA and skills.";
        } else {
            return "⭐       Poor. Focus on studies and learning skills.";
        }
    }

    // --- Get improvement tips ---
    // This method gives simple tips to the student
    // based on their current score to help them improve
    public void showImprovementTips(Student s) {

        int score        = calculateScore(s);
        int numberOfSkills = s.getSkills().size();

        System.out.println("\n💡 IMPROVEMENT TIPS FOR YOU:");
        System.out.println("-----------------------------");

        // Tip based on CGPA
        if (s.getCgpa() < 6.0) {
            System.out.println("📚 Your CGPA is low. Focus on studies to improve it.");
        } else if (s.getCgpa() < 8.0) {
            System.out.println("📚 Your CGPA is decent. Try to push it above 8.0.");
        } else {
            System.out.println("📚 Great CGPA! Keep maintaining it.");
        }

        // Tip based on number of skills
        if (numberOfSkills == 0) {
            System.out.println("🛠️  You have no skills added. Start learning Java or Python!");
        } else if (numberOfSkills < 3) {
            System.out.println("🛠️  You have only " + numberOfSkills + " skill(s). Try to learn at least 3-5 skills.");
        } else if (numberOfSkills < 5) {
            System.out.println("🛠️  Good! You have " + numberOfSkills + " skills. Adding 1-2 more will boost your score.");
        } else {
            System.out.println("🛠️  Excellent! You have " + numberOfSkills + " skills. Keep learning new ones!");
        }

        // Overall tip based on final score
        if (score < 50) {
            System.out.println("🎯 Overall: You need significant improvement to get placed.");
        } else if (score < 70) {
            System.out.println("🎯 Overall: You are on the right track. Keep improving!");
        } else {
            System.out.println("🎯 Overall: You have a strong profile. Apply confidently!");
        }

        System.out.println("-----------------------------");
    }

    // --- Get score breakdown as text ---
    // Returns a simple one line summary of the score
    public String getScoreSummary(Student s) {

        int score      = calculateScore(s);
        String rating  = getRating(score);

        return "Resume Score: " + score + "/100 | " + rating;
    }
}