// File: src/model/Notification.java
// Package: model
// Description: Represents a Notification in the Smart Campus Placement System.

package model;

/**
 * Notification model class.
 * Stores all information related to a notification sent to a user.
 * Notifications can be sent to students, companies, or admins using their userId.
 * Used across service, GUI, and file handling layers.
 */
public class Notification {

    // ─── Fields ───────────────────────────────────────────────

    private String id;      // Unique notification ID (e.g., NOT001)
    private String userId;  // ID of the user this notification belongs to (e.g., STU001)
    private String message; // Notification message content (e.g., "You have been shortlisted!")

    // ─── Constructors ─────────────────────────────────────────

    /**
     * Default constructor.
     * Creates an empty Notification object.
     */
    public Notification() {
    }

    /**
     * Parameterized constructor.
     * Use this to create a fully initialized Notification object.
     *
     * @param id      Unique notification ID
     * @param userId  ID of the user receiving the notification
     * @param message Notification message content
     */
    public Notification(String id, String userId, String message) {
        this.id      = id;
        this.userId  = userId;
        this.message = message;
    }

    // ─── Getters ──────────────────────────────────────────────

    /** @return Notification's unique ID */
    public String getId() {
        return id;
    }

    /** @return ID of the user this notification belongs to */
    public String getUserId() {
        return userId;
    }

    /** @return Notification message content */
    public String getMessage() {
        return message;
    }

    // ─── Setters ──────────────────────────────────────────────

    /** @param id Unique notification ID to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @param userId User ID to set */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** @param message Notification message content to set */
    public void setMessage(String message) {
        this.message = message;
    }

    // ─── Utility ──────────────────────────────────────────────

    /**
     * Returns a readable string representation of the Notification.
     * Example: Notification{id='NOT001', userId='STU001',
     *                       message='You have been shortlisted for Drive DRV001!'}
     *
     * @return Formatted string of notification data
     */
    @Override
    public String toString() {
        return "Notification{"              +
                "id='"       + id       + '\'' +
                ", userId='" + userId   + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}