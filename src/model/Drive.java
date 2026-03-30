package model;

import java.util.ArrayList;

public class Drive {

    private int id;
    private String companyName;
    private String role;
    private double minCGPA;
    private ArrayList<String> eligibleBranches;
    private ArrayList<String> requiredSkills;

    // Constructor
    public Drive(int did, String cname, String drole, double dminCGPA) {
        id = did;
        companyName = cname;
        role = drole;
        minCGPA = dminCGPA;
        eligibleBranches = new ArrayList<String>();
        requiredSkills = new ArrayList<String>();
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRole() {
        return role;
    }

    public double getMinCGPA() {
        return minCGPA;
    }

    public ArrayList<String> getEligibleBranches() {
        return eligibleBranches;
    }

    public ArrayList<String> getRequiredSkills() {
        return requiredSkills;
    }

    // Setters

    public void setId(int did) {
        id = did;
    }

    public void setCompanyName(String cname) {
        companyName = cname;
    }

    public void setRole(String drole) {
        role = drole;
    }

    public void setMinCGPA(double dminCGPA) {
        minCGPA = dminCGPA;
    }

    public void setEligibleBranches(ArrayList<String> ebranches) {
        eligibleBranches = ebranches;
    }

    public void setRequiredSkills(ArrayList<String> rskills) {
        requiredSkills = rskills;
    }

    // Helper methods

    public void addEligibleBranch(String branch) {
        eligibleBranches.add(branch);
    }

    public void addRequiredSkill(String skill) {
        requiredSkills.add(skill);
    }

    public boolean isBranchEligible(String branch) {
        for (int i = 0; i < eligibleBranches.size(); i++) {
            if (eligibleBranches.get(i).equalsIgnoreCase(branch)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSkillRequired(String skill) {
        for (int i = 0; i < requiredSkills.size(); i++) {
            if (requiredSkills.get(i).equalsIgnoreCase(skill)) {
                return true;
            }
        }
        return false;
    }

    public void displayInfo() {
        System.out.println("=============================");
        System.out.println("Drive ID       : " + id);
        System.out.println("Company        : " + companyName);
        System.out.println("Role           : " + role);
        System.out.println("Minimum CGPA   : " + minCGPA);

        System.out.print("Branches       : ");
        if (eligibleBranches.size() == 0) {
            System.out.println("No branches added yet");
        } else {
            for (int i = 0; i < eligibleBranches.size(); i++) {
                System.out.print(eligibleBranches.get(i));
                if (i != eligibleBranches.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        System.out.print("Required Skills: ");
        if (requiredSkills.size() == 0) {
            System.out.println("No skills added yet");
        } else {
            for (int i = 0; i < requiredSkills.size(); i++) {
                System.out.print(requiredSkills.get(i));
                if (i != requiredSkills.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        System.out.println("=============================");
    }
}