// Package name - this file belongs to the service package
package service;

// We need ArrayList to store all drives
import java.util.ArrayList;

// We need Drive class from model package
import model.Drive;

// DriveService class - handles all logic related to placement drives
// like adding new drives and viewing existing drives
public class DriveService {

    // This ArrayList works as our database to store all drives
    private ArrayList<Drive> driveList;

    // This counter helps us give unique ID to each new drive
    private int idCounter;

    // --- Constructor ---
    public DriveService() {
        driveList = new ArrayList<Drive>();
        idCounter = 1; // first drive will get ID = 1

        // Adding some sample drives so we can test without adding every time

        // Sample Drive 1 - Google
        Drive d1 = new Drive(idCounter++, "Google", "Software Engineer", 8.0);
        d1.addEligibleBranch("CSE");
        d1.addEligibleBranch("IT");
        d1.addRequiredSkill("Java");
        d1.addRequiredSkill("Python");
        driveList.add(d1);

        // Sample Drive 2 - TCS
        Drive d2 = new Drive(idCounter++, "TCS", "System Analyst", 6.5);
        d2.addEligibleBranch("CSE");
        d2.addEligibleBranch("IT");
        d2.addEligibleBranch("ECE");
        d2.addRequiredSkill("SQL");
        d2.addRequiredSkill("Java");
        driveList.add(d2);

        // Sample Drive 3 - Infosys
        Drive d3 = new Drive(idCounter++, "Infosys", "Junior Developer", 7.0);
        d3.addEligibleBranch("CSE");
        d3.addEligibleBranch("IT");
        d3.addEligibleBranch("MECH");
        d3.addRequiredSkill("Python");
        d3.addRequiredSkill("HTML");
        driveList.add(d3);
    }

    // --- Add a new drive ---
    // This method takes drive details and adds them to the list
    // Company calls this method to post a new placement drive
    public void addDrive(String companyName, String role, double minCGPA,
                         ArrayList<String> branches, ArrayList<String> skills) {

        // Create a new Drive object with given details
        Drive newDrive = new Drive(idCounter, companyName, role, minCGPA);
        idCounter++; // increase counter so next drive gets a different ID

        // Add all eligible branches to the drive
        for (int i = 0; i < branches.size(); i++) {
            newDrive.addEligibleBranch(branches.get(i));
        }

        // Add all required skills to the drive
        for (int i = 0; i < skills.size(); i++) {
            newDrive.addRequiredSkill(skills.get(i));
        }

        // Finally add the drive to our list
        driveList.add(newDrive);

        System.out.println("✅ Drive added successfully! Drive ID is: " + newDrive.getId());
    }

    // --- View all drives ---
    // This method prints all available placement drives on screen
    public void viewDrives() {

        // Check if there are any drives at all
        if (driveList.size() == 0) {
            System.out.println("No drives available at the moment.");
            return;
        }

        System.out.println("\n===== ALL PLACEMENT DRIVES =====");

        // Loop through list and print each drive's info
        for (int i = 0; i < driveList.size(); i++) {
            driveList.get(i).displayInfo();
        }
    }

    // --- Get all drives ---
    // Returns the full ArrayList of drives
    // Used by other service classes
    public ArrayList<Drive> getAllDrives() {
        return driveList;
    }

    // --- Find a drive by its ID ---
    // Returns the Drive object if found
    // Returns null if no drive has that ID
    public Drive getDriveById(int id) {

        for (int i = 0; i < driveList.size(); i++) {
            if (driveList.get(i).getId() == id) {
                return driveList.get(i);
            }
        }

        // Drive with given ID not found
        System.out.println("❌ Drive not found with ID: " + id);
        return null;
    }

    // --- Get drives by company name ---
    // Returns list of all drives posted by a specific company
    public ArrayList<Drive> getDrivesByCompany(String companyName) {

        // This list will hold all drives of that company
        ArrayList<Drive> result = new ArrayList<Drive>();

        for (int i = 0; i < driveList.size(); i++) {
            if (driveList.get(i).getCompanyName().equalsIgnoreCase(companyName)) {
                result.add(driveList.get(i));
            }
        }

        return result;
    }

    // --- Get eligible drives for a student ---
    // This is a SMART FEATURE
    // It checks which drives a student is eligible for
    // based on their branch and CGPA
    public ArrayList<Drive> getEligibleDrives(String branch, double cgpa) {

        // This list will hold all drives the student can apply for
        ArrayList<Drive> eligibleDrives = new ArrayList<Drive>();

        for (int i = 0; i < driveList.size(); i++) {
            Drive d = driveList.get(i);

            // Check if student's branch is eligible AND cgpa meets minimum
            if (d.isBranchEligible(branch) && cgpa >= d.getMinCGPA()) {
                eligibleDrives.add(d);
            }
        }

        return eligibleDrives;
    }

    // --- Display eligible drives for a student ---
    // Prints all drives a student can apply for
    public void displayEligibleDrives(String branch, double cgpa) {

        ArrayList<Drive> eligibleDrives = getEligibleDrives(branch, cgpa);

        if (eligibleDrives.size() == 0) {
            System.out.println("❌ No eligible drives found for your branch and CGPA.");
            return;
        }

        System.out.println("\n===== ELIGIBLE DRIVES FOR YOU =====");
        System.out.println("Branch: " + branch + " | CGPA: " + cgpa);
        System.out.println("------------------------------------");

        // Loop through eligible drives and print each one
        for (int i = 0; i < eligibleDrives.size(); i++) {
            eligibleDrives.get(i).displayInfo();
        }
    }

    // --- Get total number of drives ---
    // Simple method to count how many drives are posted
    public int getTotalDrives() {
        return driveList.size();
    }
}