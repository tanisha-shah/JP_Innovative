// Package name - this file belongs to the service.smart package
package service.smart;

// We need ArrayList to store and return list of drives
import java.util.ArrayList;

// We need Student and Drive classes from model package
import model.Drive;
import model.Student;

// RecommendationService class - this is a SMART FEATURE
// It recommends eligible drives to a student
// based on their branch, CGPA and skills
// This helps students quickly find drives they can apply for
public class RecommendationService {

    // --- Main recommendation method ---
    // This method takes a student and a full list of all drives
    // It checks each drive and returns only the ones student is eligible for
    // Eligibility is based on:
    //   1. Student's branch is in drive's eligible branches list
    //   2. Student's CGPA meets the drive's minimum CGPA requirement
    public ArrayList<Drive> getEligibleDrives(Student s, ArrayList<Drive> drives) {

        // This list will store all drives the student is eligible for
        ArrayList<Drive> eligibleDrives = new ArrayList<Drive>();

        System.out.println("\n=============================");
        System.out.println("   DRIVE RECOMMENDATIONS     ");
        System.out.println("=============================");
        System.out.println("Student  : " + s.getName());
        System.out.println("Branch   : " + s.getBranch());
        System.out.println("CGPA     : " + s.getCgpa());
        System.out.println("-----------------------------");
        System.out.println("Checking " + drives.size() + " drive(s) for you...");
        System.out.println("-----------------------------");

        // Check if drive list is empty
        if (drives.size() == 0) {
            System.out.println("❌ No drives available at the moment.");
            return eligibleDrives; // return empty list
        }

        // --- Core Logic ---
        // Loop through every drive one by one
        // Check if student is eligible for each drive
        for (int i = 0; i < drives.size(); i++) {
            Drive d = drives.get(i);

            // Flag variables to track each check
            boolean branchOk = false;
            boolean cgpaOk   = false;

            // Check 1 - Is student's branch in eligible branches?
            if (d.isBranchEligible(s.getBranch())) {
                branchOk = true;
            }

            // Check 2 - Does student's CGPA meet minimum requirement?
            if (s.getCgpa() >= d.getMinCGPA()) {
                cgpaOk = true;
            }

            // If both checks pass - student is eligible for this drive
            if (branchOk && cgpaOk) {
                eligibleDrives.add(d); // add to eligible list
            }
        }

        // --- Display Results ---
        if (eligibleDrives.size() == 0) {

            // No eligible drives found for this student
            System.out.println("❌ Sorry! No eligible drives found for you.");
            System.out.println("   Tip: Improve your CGPA to unlock more drives.");

        } else {

            // Found some eligible drives - show them
            System.out.println("🎉 " + eligibleDrives.size() + " eligible drive(s) found for you!\n");

            // Print each eligible drive with a number
            for (int i = 0; i < eligibleDrives.size(); i++) {
                System.out.println("Drive " + (i + 1) + ":");
                eligibleDrives.get(i).displayInfo();
            }
        }

        System.out.println("=============================");

        // Return the list of eligible drives
        return eligibleDrives;
    }

    // --- Get recommended drives with skill match ---
    // This is an ADVANCED recommendation
    // It not only checks branch and CGPA
    // but also shows how many skills student already has for each drive
    public void getRecommendationsWithSkillMatch(Student s, ArrayList<Drive> drives) {

        System.out.println("\n=============================");
        System.out.println(" SMART DRIVE RECOMMENDATIONS ");
        System.out.println("=============================");
        System.out.println("Student : " + s.getName());
        System.out.println("Branch  : " + s.getBranch());
        System.out.println("CGPA    : " + s.getCgpa());
        System.out.println("Skills  : " + s.getSkills());
        System.out.println("-----------------------------");

        // Check if drive list is empty
        if (drives.size() == 0) {
            System.out.println("❌ No drives available at the moment.");
            return;
        }

        // Counter to track how many eligible drives we find
        int count = 0;

        // Loop through all drives and check eligibility
        for (int i = 0; i < drives.size(); i++) {
            Drive d = drives.get(i);

            // Check branch and CGPA eligibility
            boolean branchOk = d.isBranchEligible(s.getBranch());
            boolean cgpaOk   = s.getCgpa() >= d.getMinCGPA();

            // Only show drives where student is eligible
            if (branchOk && cgpaOk) {
                count++;

                // Count how many required skills student already has
                int matchedSkills  = 0;
                int totalRequired  = d.getRequiredSkills().size();

                for (int j = 0; j < d.getRequiredSkills().size(); j++) {
                    if (s.hasSkill(d.getRequiredSkills().get(j))) {
                        matchedSkills++; // student has this skill
                    }
                }

                // Calculate skill match percentage
                int skillPercent = 0;
                if (totalRequired > 0) {
                    skillPercent = (matchedSkills * 100) / totalRequired;
                } else {
                    skillPercent = 100; // no skills required means 100% match
                }

                // Print drive info with skill match
                System.out.println("\n✅ Drive " + count + " : " + d.getCompanyName() + " - " + d.getRole());
                System.out.println("   Drive ID      : " + d.getId());
                System.out.println("   Min CGPA      : " + d.getMinCGPA());
                System.out.println("   Skill Match   : " + matchedSkills + "/" + totalRequired + " (" + skillPercent + "%)");

                // Show skill match bar using simple characters
                System.out.print("   Match Level   : ");
                if (skillPercent == 100) {
                    System.out.println("[##########] Perfect Match! 🎯");
                } else if (skillPercent >= 75) {
                    System.out.println("[########  ] Strong Match 💪");
                } else if (skillPercent >= 50) {
                    System.out.println("[#####     ] Moderate Match 👍");
                } else if (skillPercent >= 25) {
                    System.out.println("[###       ] Weak Match ⚠️");
                } else {
                    System.out.println("[#         ] Very Weak Match ❌");
                }
            }
        }

        // If no eligible drives found
        if (count == 0) {
            System.out.println("❌ No eligible drives found for you right now.");
            System.out.println("   Tip: Improve your CGPA to unlock more drives.");
        } else {
            System.out.println("\n-----------------------------");
            System.out.println("Total Eligible Drives : " + count);
        }

        System.out.println("=============================");
    }

    // --- Get top recommended drive ---
    // This method returns the single BEST drive for the student
    // Best drive = eligible drive where student has most skills matched
    public Drive getTopRecommendation(Student s, ArrayList<Drive> drives) {

        // This will hold the best drive we find
        Drive bestDrive       = null;
        int   bestSkillMatch  = -1; // start with -1 so any match is better

        // Loop through all drives
        for (int i = 0; i < drives.size(); i++) {
            Drive d = drives.get(i);

            // First check basic eligibility
            boolean branchOk = d.isBranchEligible(s.getBranch());
            boolean cgpaOk   = s.getCgpa() >= d.getMinCGPA();

            if (branchOk && cgpaOk) {

                // Count how many skills student has for this drive
                int matchedSkills = 0;

                for (int j = 0; j < d.getRequiredSkills().size(); j++) {
                    if (s.hasSkill(d.getRequiredSkills().get(j))) {
                        matchedSkills++;
                    }
                }

                // If this drive has better skill match than previous best
                // update bestDrive
                if (matchedSkills > bestSkillMatch) {
                    bestSkillMatch = matchedSkills;
                    bestDrive      = d;
                }
            }
        }

        // Show result
        if (bestDrive == null) {
            System.out.println("❌ No eligible drive found for top recommendation.");
        } else {
            System.out.println("\n🏆 TOP RECOMMENDED DRIVE FOR YOU:");
            bestDrive.displayInfo();
        }

        return bestDrive;
    }

    // --- Count total eligible drives ---
    // Simple method to count how many drives a student can apply for
    public int countEligibleDrives(Student s, ArrayList<Drive> drives) {

        int count = 0;

        for (int i = 0; i < drives.size(); i++) {
            Drive d = drives.get(i);

            // Check branch and CGPA
            if (d.isBranchEligible(s.getBranch()) && s.getCgpa() >= d.getMinCGPA()) {
                count++;
            }
        }

        return count;
    }
}