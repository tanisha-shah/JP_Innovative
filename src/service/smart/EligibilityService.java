// Package name - this file belongs to the service.smart package
// We put it in a sub-package called "smart" to keep smart features separate
package service.smart;

// We need Student and Drive classes from model package
import model.Drive;
import model.Student;

// EligibilityService class - checks if a student is eligible for a drive
// This is a SMART FEATURE of our placement system
// It checks CGPA and Branch before allowing a student to apply
public class EligibilityService {

    // --- Main eligibility check method ---
    // This method checks if a student is eligible for a specific drive
    // It checks two things:
    //   1. Is student's branch in the eligible branches list?
    //   2. Is student's CGPA equal to or more than minimum required CGPA?
    // Returns true if student is eligible
    // Returns false if student is NOT eligible
    public boolean isEligible(Student s, Drive d) {

        System.out.println("\n--- Checking Eligibility ---");
        System.out.println("Student  : " + s.getName());
        System.out.println("Branch   : " + s.getBranch());
        System.out.println("CGPA     : " + s.getCgpa());
        System.out.println("Drive    : " + d.getCompanyName() + " - " + d.getRole());
        System.out.println("Min CGPA : " + d.getMinCGPA());
        System.out.println("----------------------------");

        // --- Check 1: Branch Eligibility ---
        // We call isBranchEligible() from Drive class
        // It checks if student's branch is in the allowed branches list
        if (!d.isBranchEligible(s.getBranch())) {
            // Branch is NOT in the eligible list
            System.out.println("❌ Branch Check   : FAILED");
            System.out.println("   Your branch (" + s.getBranch() + ") is not eligible for this drive.");
            System.out.println("   Eligible branches are: " + d.getEligibleBranches());
            return false; // student is not eligible, stop checking further
        }

        // Branch check passed
        System.out.println("✅ Branch Check   : PASSED (" + s.getBranch() + " is eligible)");

        // --- Check 2: CGPA Eligibility ---
        // We compare student's CGPA with the minimum CGPA required for the drive
        if (s.getCgpa() < d.getMinCGPA()) {
            // Student's CGPA is less than required minimum
            System.out.println("❌ CGPA Check     : FAILED");
            System.out.println("   Your CGPA (" + s.getCgpa() + ") is below the minimum required CGPA (" + d.getMinCGPA() + ").");
            return false; // student is not eligible
        }

        // CGPA check passed
        System.out.println("✅ CGPA Check     : PASSED (" + s.getCgpa() + " >= " + d.getMinCGPA() + ")");

        // Both checks passed - student is eligible!
        System.out.println("🎉 Result         : You are ELIGIBLE for this drive!");
        System.out.println("----------------------------");
        return true;
    }

    // --- Check only branch ---
    // Sometimes we just want to check branch separately
    // Returns true if branch is eligible, false if not
    public boolean isBranchEligible(Student s, Drive d) {

        if (d.isBranchEligible(s.getBranch())) {
            System.out.println("✅ Branch (" + s.getBranch() + ") is eligible for this drive.");
            return true;
        } else {
            System.out.println("❌ Branch (" + s.getBranch() + ") is NOT eligible for this drive.");
            return false;
        }
    }

    // --- Check only CGPA ---
    // Sometimes we just want to check CGPA separately
    // Returns true if CGPA is enough, false if not
    public boolean isCgpaEligible(Student s, Drive d) {

        if (s.getCgpa() >= d.getMinCGPA()) {
            System.out.println("✅ CGPA (" + s.getCgpa() + ") meets the minimum requirement (" + d.getMinCGPA() + ").");
            return true;
        } else {
            System.out.println("❌ CGPA (" + s.getCgpa() + ") does NOT meet the minimum requirement (" + d.getMinCGPA() + ").");
            return false;
        }
    }

    // --- Get eligibility reason ---
    // This method tells the student exactly WHY they are or are not eligible
    // Returns a simple String message explaining the result
    public String getEligibilityReason(Student s, Drive d) {

        // Check branch first
        if (!d.isBranchEligible(s.getBranch())) {
            return "Not eligible: Your branch (" + s.getBranch() + ") is not allowed for this drive.";
        }

        // Check CGPA next
        if (s.getCgpa() < d.getMinCGPA()) {
            return "Not eligible: Your CGPA (" + s.getCgpa() + ") is less than required CGPA (" + d.getMinCGPA() + ").";
        }

        // Both passed
        return "Eligible: Your branch and CGPA both meet the requirements!";
    }
}