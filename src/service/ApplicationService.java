// Package name - this file belongs to the service package
package service;

// We need ArrayList to store all applications
import java.util.ArrayList;

// We need these classes from model package
import model.Application;
import model.Drive;
import model.Student;

// ApplicationService class - handles all logic related to applications
// like applying for a drive and viewing application status
public class ApplicationService {

    // This ArrayList works as our database to store all applications
    private ArrayList<Application> applicationList;

    // This counter helps us give unique ID to each new application
    private int idCounter;

    // --- Constructor ---
    public ApplicationService() {
        applicationList = new ArrayList<Application>();
        idCounter = 1; // first application will get ID = 1
    }

    // --- Apply for a drive ---
    // This method lets a student apply for a placement drive
    // It checks if student is eligible before applying
    // Returns true if application is successful
    // Returns false if not eligible or already applied
    public boolean applyForDrive(Student student, Drive drive) {

        // Check 1 - Has student already applied for this drive?
        for (int i = 0; i < applicationList.size(); i++) {
            Application a = applicationList.get(i);

            // If same student and same drive found, they already applied
            if (a.getStudentId() == student.getId() && a.getDriveId() == drive.getId()) {
                System.out.println("⚠️  You have already applied for this drive!");
                return false;
            }
        }

        // Check 2 - Is student's branch eligible for this drive?
        if (!drive.isBranchEligible(student.getBranch())) {
            System.out.println("❌ Sorry! Your branch (" + student.getBranch() + ") is not eligible for this drive.");
            return false;
        }

        // Check 3 - Does student meet the minimum CGPA requirement?
        if (student.getCgpa() < drive.getMinCGPA()) {
            System.out.println("❌ Sorry! Your CGPA (" + student.getCgpa() + ") is below the minimum required CGPA (" + drive.getMinCGPA() + ").");
            return false;
        }

        // All checks passed - create new application and add to list
        Application newApplication = new Application(idCounter, student.getId(), drive.getId());
        idCounter++; // increase counter so next application gets different ID
        applicationList.add(newApplication);

        System.out.println("✅ Application submitted successfully!");
        System.out.println("   Application ID : " + newApplication.getApplicationId());
        System.out.println("   Company        : " + drive.getCompanyName());
        System.out.println("   Role           : " + drive.getRole());
        System.out.println("   Status         : " + newApplication.getStatus());

        return true;
    }

    // --- View applications by student ---
    // This method shows all applications made by a specific student
    // Student can see which drives they applied for and their status
    public void viewApplicationsByStudent(int studentId) {

        // This list will hold all applications of that student
        ArrayList<Application> studentApplications = new ArrayList<Application>();

        // Loop through all applications and find ones that belong to this student
        for (int i = 0; i < applicationList.size(); i++) {
            if (applicationList.get(i).getStudentId() == studentId) {
                studentApplications.add(applicationList.get(i));
            }
        }

        // Check if student has applied anywhere at all
        if (studentApplications.size() == 0) {
            System.out.println("You have not applied for any drives yet.");
            return;
        }

        System.out.println("\n===== YOUR APPLICATIONS =====");

        // Print each application one by one
        for (int i = 0; i < studentApplications.size(); i++) {
            studentApplications.get(i).displayInfo();
        }
    }

    // --- Get applications by student ID ---
    // Returns ArrayList of all applications made by a student
    // Used by other classes when they need the list
    public ArrayList<Application> getApplicationsByStudent(int studentId) {

        ArrayList<Application> studentApplications = new ArrayList<Application>();

        for (int i = 0; i < applicationList.size(); i++) {
            if (applicationList.get(i).getStudentId() == studentId) {
                studentApplications.add(applicationList.get(i));
            }
        }

        return studentApplications;
    }

    // --- Get applications by drive ID ---
    // Returns ArrayList of all applications for a specific drive
    // Used by Company and Admin to see who applied
    public ArrayList<Application> getApplicationsByDrive(int driveId) {

        ArrayList<Application> driveApplications = new ArrayList<Application>();

        for (int i = 0; i < applicationList.size(); i++) {
            if (applicationList.get(i).getDriveId() == driveId) {
                driveApplications.add(applicationList.get(i));
            }
        }

        return driveApplications;
    }

    // --- Update application status ---
    // This method is used by Admin to update status of an application
    // Status can be changed to "Selected" or "Rejected"
    public void updateStatus(int applicationId, String newStatus) {

        // Loop through all applications to find the one with matching ID
        for (int i = 0; i < applicationList.size(); i++) {
            if (applicationList.get(i).getApplicationId() == applicationId) {

                // Found the application - update its status
                applicationList.get(i).setStatus(newStatus);
                System.out.println("✅ Application ID " + applicationId + " status updated to: " + newStatus);
                return;
            }
        }

        // If we reach here, application was not found
        System.out.println("❌ Application not found with ID: " + applicationId);
    }

    // --- Get all applications ---
    // Returns full list of all applications
    // Used by Admin to see everything
    public ArrayList<Application> getAllApplications() {
        return applicationList;
    }

    // --- Display all applications ---
    // Prints every application in the system
    // Useful for Admin panel
    public void displayAllApplications() {

        // Check if there are any applications at all
        if (applicationList.size() == 0) {
            System.out.println("No applications found.");
            return;
        }

        System.out.println("\n===== ALL APPLICATIONS =====");

        // Loop and print each application
        for (int i = 0; i < applicationList.size(); i++) {
            applicationList.get(i).displayInfo();
        }
    }

    // --- Get total number of applications ---
    // Simple method to count total applications in system
    public int getTotalApplications() {
        return applicationList.size();
    }
}