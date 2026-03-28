// File: src/model/Application.java
// Package: model
// Description: Represents a Student's Job Application in the Smart Campus Placement System.

package model;

/**
 * Application model class.
 * Stores all information related to a student's application for a placement drive.
 * Tracks the current status of the application throughout the recruitment process.
 * Used across service, GUI, and file handling layers.
 */
public class Application {

    // ─── Status Constants ─────────────────────────────────────

    /** Possible status values for an application */
    public static final String STATUS_APPLIED      = "Applied";
    public static final String STATUS_SHORTLISTED  = "Shortlisted";
    public static final String STATUS_REJECTED     = "Rejected";

    // ─── Fields ───────────────────────────────────────────────

    private String applicationId; // Unique application ID (e.g., APP001)
    private String studentId;     // ID of the student who applied (e.g., STU001)
    private String driveId;       // ID of the drive applied to (e.g., DRV001)
    private String status;        // Current status: Applied, Shortlisted, or Rejected

    // ─── Constructors ─────────────────────────────────────────

    /**
     * Default constructor.
     * Sets default status to "Applied" when a new application is created.
     */
    public Application() {
        this.status = STATUS_APPLIED; // Default status on creation
    }

    /**
     * Parameterized constructor.
     * Use this to create a fully initialized Application object.
     *
     * @param applicationId Unique application ID
     * @param studentId     ID of the applying student
     * @param driveId       ID of the targeted drive
     * @param status        Current application status
     */
    public Application(String applicationId, String studentId,
                       String driveId, String status) {
        this.applicationId = applicationId;
        this.studentId     = studentId;
        this.driveId       = driveId;
        this.status        = (status != null && !status.isEmpty())
                              ? status
                              : STATUS_APPLIED; // Fallback to "Applied" if null or empty
    }

    /**
     * Convenience constructor.
     * Creates a new application with default status "Applied".
     * Use this when a student freshly applies for a drive.
     *
     * @param applicationId Unique application ID
     * @param studentId     ID of the applying student
     * @param driveId       ID of the targeted drive
     */
    public Application(String applicationId, String studentId, String driveId) {
        this.applicationId = applicationId;
        this.studentId     = studentId;
        this.driveId       = driveId;
        this.status        = STATUS_APPLIED; // Auto-set to "Applied"
    }

    // ─── Getters ──────────────────────────────────────────────

    /** @return Application's unique ID */
    public String getApplicationId() {
        return applicationId;
    }

    /** @return ID of the student who applied */
    public String getStudentId() {
        return studentId;
    }

    /** @return ID of the drive this application is for */
    public String getDriveId() {
        return driveId;
    }

    /** @return Current status of the application */
    public String getStatus() {
        return status;
    }

    // ─── Setters ──────────────────────────────────────────────

    /** @param applicationId Unique application ID to set */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /** @param studentId Student ID to set */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /** @param driveId Drive ID to set */
    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    /**
     * Sets the application status.
     * Only accepts valid status values: Applied, Shortlisted, Rejected.
     * If an invalid status is provided, the status remains unchanged.
     *
     * @param status New status to set
     */
    public void setStatus(String status) {
        // Only update if the provided status is one of the valid options
        if (STATUS_APPLIED.equals(status)     ||
            STATUS_SHORTLISTED.equals(status) ||
            STATUS_REJECTED.equals(status)) {
            this.status = status;
        } else {
            // Invalid status provided — keep existing status unchanged
            System.out.println("[Warning] Invalid status: '" + status +
                               "'. Status not updated. Valid values: " +
                               STATUS_APPLIED + ", " +
                               STATUS_SHORTLISTED + ", " +
                               STATUS_REJECTED);
        }
    }

    // ─── Utility ──────────────────────────────────────────────

    /**
     * Returns a readable string representation of the Application.
     * Example: Application{applicationId='APP001', studentId='STU001',
     *                      driveId='DRV001', status='Applied'}
     *
     * @return Formatted string of application data
     */
    @Override
    public String toString() {
        return "Application{"                           +
                "applicationId='" + applicationId + '\'' +
                ", studentId='"   + studentId     + '\'' +
                ", driveId='"     + driveId       + '\'' +
                ", status='"      + status        + '\'' +
                '}';
    }
}