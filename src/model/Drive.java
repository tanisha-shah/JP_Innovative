// File: src/model/Drive.java
// Package: model
// Description: Represents a Campus Placement Drive in the Smart Campus Placement System.

package model;

import java.util.List;
import java.util.ArrayList;

/**
 * Drive model class.
 * Stores all information related to a campus placement drive posted by a company.
 * Contains eligibility criteria such as minimum CGPA, eligible branches, and required skills.
 * Used across service, GUI, and file handling layers.
 */
public class Drive {

    // ─── Fields ───────────────────────────────────────────────

    private String id;                      // Unique drive ID (e.g., DRV001)
    private String companyName;             // Name of the company hosting the drive
    private String role;                    // Job role offered (e.g., Software Engineer)
    private double minCGPA;                 // Minimum CGPA required to apply
    private List<String> eligibleBranches;  // List of eligible branches (e.g., ["CSE", "IT"])
    private List<String> requiredSkills;    // List of required skills (e.g., ["Java", "SQL"])

    // ─── Constructors ─────────────────────────────────────────

    /**
     * Default constructor.
     * Initializes eligibleBranches and requiredSkills as empty lists.
     */
    public Drive() {
        this.eligibleBranches = new ArrayList<>();
        this.requiredSkills   = new ArrayList<>();
    }

    /**
     * Parameterized constructor.
     * Use this to create a fully initialized Drive object.
     *
     * @param id                Unique drive ID
     * @param companyName       Name of the hosting company
     * @param role              Job role offered
     * @param minCGPA           Minimum CGPA required
     * @param eligibleBranches  List of branches eligible for this drive
     * @param requiredSkills    List of skills required for this role
     */
    public Drive(String id, String companyName, String role, double minCGPA,
                 List<String> eligibleBranches, List<String> requiredSkills) {
        this.id                = id;
        this.companyName       = companyName;
        this.role              = role;
        this.minCGPA           = minCGPA;
        this.eligibleBranches  = (eligibleBranches != null) ? eligibleBranches : new ArrayList<>();
        this.requiredSkills    = (requiredSkills   != null) ? requiredSkills   : new ArrayList<>();
    }

    // ─── Getters ──────────────────────────────────────────────

    /** @return Drive's unique ID */
    public String getId() {
        return id;
    }

    /** @return Name of the company hosting the drive */
    public String getCompanyName() {
        return companyName;
    }

    /** @return Job role offered in this drive */
    public String getRole() {
        return role;
    }

    /** @return Minimum CGPA required to apply */
    public double getMinCGPA() {
        return minCGPA;
    }

    /** @return List of branches eligible for this drive */
    public List<String> getEligibleBranches() {
        return eligibleBranches;
    }

    /** @return List of skills required for this drive */
    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    // ─── Setters ──────────────────────────────────────────────

    /** @param id Unique drive ID to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @param companyName Company name to set */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** @param role Job role to set */
    public void setRole(String role) {
        this.role = role;
    }

    /** @param minCGPA Minimum CGPA to set */
    public void setMinCGPA(double minCGPA) {
        this.minCGPA = minCGPA;
    }

    /** @param eligibleBranches List of eligible branches to set */
    public void setEligibleBranches(List<String> eligibleBranches) {
        this.eligibleBranches = (eligibleBranches != null) ? eligibleBranches : new ArrayList<>();
    }

    /** @param requiredSkills List of required skills to set */
    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = (requiredSkills != null) ? requiredSkills : new ArrayList<>();
    }

    // ─── Utility ──────────────────────────────────────────────

    /**
     * Returns a readable string representation of the Drive.
     * Example: Drive{id='DRV001', companyName='Google', role='SDE',
     *                minCGPA=7.5, eligibleBranches=[CSE, IT], requiredSkills=[Java, SQL]}
     *
     * @return Formatted string of drive data
     */
    @Override
    public String toString() {
        return "Drive{"                             +
                "id='"               + id               + '\'' +
                ", companyName='"    + companyName       + '\'' +
                ", role='"           + role              + '\'' +
                ", minCGPA="         + minCGPA           +
                ", eligibleBranches=" + eligibleBranches +
                ", requiredSkills="   + requiredSkills   +
                '}';
    }
}