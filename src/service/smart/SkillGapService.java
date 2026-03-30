// Package name - this file belongs to the service.smart package
package service.smart;

// We need ArrayList to store missing skills
import java.util.ArrayList;

// We need Student and Drive classes from model package
import model.Drive;
import model.Student;

// SkillGapService class - this is a SMART FEATURE
// It compares student's skills with drive's required skills
// and tells the student which skills they are missing
// This helps students know what to learn before applying
public class SkillGapService {

    // --- Main skill gap method ---
    // This method compares student skills vs drive required skills
    // It finds which required skills the student does NOT have
    // Returns an ArrayList of missing skills
    // If ArrayList is empty, student has all required skills
    public ArrayList<String> getSkillGap(Student s, Drive d) {

        // This list will store all the skills student is missing
        ArrayList<String> missingSkills = new ArrayList<String>();

        // Get the list of skills required for this drive
        ArrayList<String> requiredSkills = d.getRequiredSkills();

        // Get the list of skills the student currently has
        ArrayList<String> studentSkills = s.getSkills();

        System.out.println("\n--- Skill Gap Analysis ---");
        System.out.println("Student          : " + s.getName());
        System.out.println("Drive            : " + d.getCompanyName() + " - " + d.getRole());

        // Print student's current skills
        System.out.print("Your Skills      : ");
        if (studentSkills.size() == 0) {
            System.out.println("No skills added yet");
        } else {
            for (int i = 0; i < studentSkills.size(); i++) {
                System.out.print(studentSkills.get(i));
                // Print comma after each skill except the last one
                if (i != studentSkills.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(); // move to next line
        }

        // Print required skills for this drive
        System.out.print("Required Skills  : ");
        if (requiredSkills.size() == 0) {
            System.out.println("No specific skills required");
        } else {
            for (int i = 0; i < requiredSkills.size(); i++) {
                System.out.print(requiredSkills.get(i));
                // Print comma after each skill except the last one
                if (i != requiredSkills.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(); // move to next line
        }

        System.out.println("--------------------------");

        // --- Core Logic ---
        // Loop through every required skill of the drive
        // Check if student has that skill or not
        for (int i = 0; i < requiredSkills.size(); i++) {
            String requiredSkill = requiredSkills.get(i);

            // Use hasSkill() from Student class to check
            if (!s.hasSkill(requiredSkill)) {
                // Student does NOT have this skill - add to missing list
                missingSkills.add(requiredSkill);
            }
        }

        // --- Display Results ---
        if (missingSkills.size() == 0) {
            // Student has all required skills - great news!
            System.out.println("🎉 Great News! You have ALL the required skills for this drive!");
            System.out.println("   You are fully prepared to apply!");

        } else {
            // Student is missing some skills - show them what to learn
            System.out.println("⚠️  You are missing " + missingSkills.size() + " skill(s) for this drive:");
            System.out.println();

            // Print each missing skill with a number
            for (int i = 0; i < missingSkills.size(); i++) {
                System.out.println("   " + (i + 1) + ". ❌ " + missingSkills.get(i));
            }

            System.out.println();
            System.out.println("💡 Tip: Learn these skills to improve your chances!");
        }

        System.out.println("--------------------------");

        // Return the missing skills list so other classes can use it
        return missingSkills;
    }

    // --- Check if student has all required skills ---
    // Simple method that returns true if no skills are missing
    // Returns false if even one skill is missing
    public boolean hasAllSkills(Student s, Drive d) {

        ArrayList<String> requiredSkills = d.getRequiredSkills();

        // Loop through all required skills
        for (int i = 0; i < requiredSkills.size(); i++) {

            // If even one skill is missing return false immediately
            if (!s.hasSkill(requiredSkills.get(i))) {
                return false;
            }
        }

        // All skills found - return true
        return true;
    }

    // --- Count how many skills are missing ---
    // Returns a number showing how many skills student still needs to learn
    public int getMissingSkillCount(Student s, Drive d) {

        // We can reuse getSkillGap() and just return its size
        ArrayList<String> missingSkills = new ArrayList<String>();
        ArrayList<String> requiredSkills = d.getRequiredSkills();

        // Loop and count missing skills quietly (no printing)
        for (int i = 0; i < requiredSkills.size(); i++) {
            if (!s.hasSkill(requiredSkills.get(i))) {
                missingSkills.add(requiredSkills.get(i));
            }
        }

        return missingSkills.size();
    }

    // --- Get skill match percentage ---
    // This tells the student what percentage of skills they already have
    // For example: 2 out of 4 skills = 50% match
    public int getSkillMatchPercentage(Student s, Drive d) {

        ArrayList<String> requiredSkills = d.getRequiredSkills();

        // If drive has no required skills then 100% match
        if (requiredSkills.size() == 0) {
            return 100;
        }

        // Count how many required skills the student already has
        int matchedSkills = 0;

        for (int i = 0; i < requiredSkills.size(); i++) {
            if (s.hasSkill(requiredSkills.get(i))) {
                matchedSkills++; // student has this skill
            }
        }

        // Calculate percentage using simple formula
        // multiply by 100 to get percentage
        int percentage = (matchedSkills * 100) / requiredSkills.size();
        return percentage;
    }

    // --- Display full skill gap report ---
    // This method shows a complete and detailed report
    // of student's skills vs drive requirements
    public void displaySkillReport(Student s, Drive d) {

        System.out.println("\n=============================");
        System.out.println("      SKILL GAP REPORT       ");
        System.out.println("=============================");
        System.out.println("Student : " + s.getName());
        System.out.println("Drive   : " + d.getCompanyName() + " - " + d.getRole());
        System.out.println("-----------------------------");

        ArrayList<String> requiredSkills = d.getRequiredSkills();

        // Loop through all required skills
        // Show tick if student has it, cross if student doesn't
        for (int i = 0; i < requiredSkills.size(); i++) {
            String skill = requiredSkills.get(i);

            if (s.hasSkill(skill)) {
                // Student has this skill
                System.out.println("  ✅ " + skill + " - You have this skill!");
            } else {
                // Student does not have this skill
                System.out.println("  ❌ " + skill + " - MISSING (learn this)");
            }
        }

        System.out.println("-----------------------------");

        // Show skill match percentage at the bottom
        int percentage = getSkillMatchPercentage(s, d);
        System.out.println("Skill Match      : " + percentage + "%");
        System.out.println("Missing Skills   : " + getMissingSkillCount(s, d));
        System.out.println("Matched Skills   : " + (requiredSkills.size() - getMissingSkillCount(s, d)));
        System.out.println("=============================");
    }
}