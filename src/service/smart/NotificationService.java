// File: src/service/smart/NotificationService.java
// Package: service.smart
// Description: Handles all Notification related operations in the Smart Campus Placement System.

package service.smart;

import model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * NotificationService class.
 * Smart service that handles all notification operations:
 * - Adding a new notification for a user
 * - Retrieving all notifications for a specific user
 *
 * Notifications can be sent to any user type (Student, Company, Admin)
 * by using their respective user ID.
 *
 * Uses simple ArrayList storage (no database).
 * Used by GUI page: NotificationsPage.java
 */
public class NotificationService {

    // ─── Storage ──────────────────────────────────────────────

    // In-memory list to store all notifications across all users
    private ArrayList<Notification> notifications;

    // Counter to help generate unique notification IDs
    private int notificationCounter;

    // ─── Constructor ──────────────────────────────────────────

    /**
     * Constructor.
     * Initializes the notifications storage list and ID counter.
     * Pre-loads sample notifications for testing and demonstration.
     */
    public NotificationService() {
        this.notifications      = new ArrayList<>();
        this.notificationCounter = 0;

        // ── Pre-load sample notifications for demonstration ──
        addNotification("STU001",
                "Welcome to Smart Campus Placement System! " +
                "Start exploring available drives.");

        addNotification("STU001",
                "New drive posted by Google for Software Engineer role. " +
                "Check your eligibility now!");

        addNotification("STU001",
                "Your application APP001 status has been updated to: Shortlisted!");
    }

    // ─── Core Methods ─────────────────────────────────────────

    /**
     * Adds a new notification for a specific user.
     *
     * Steps:
     * 1. Validate that userId and message are not null or empty.
     * 2. Generate a unique notification ID.
     * 3. Create a new Notification object.
     * 4. Add it to the notifications list.
     *
     * @param userId  ID of the user to send the notification to
     *                (e.g., STU001, COM001, ADM001)
     * @param message The notification message content
     * @return "SUCCESS" if added successfully,
     *         or an error message string if validation fails
     */
    public String addNotification(String userId, String message) {

        // ── Step 1: Validate userId ──
        if (userId == null || userId.trim().isEmpty()) {
            return "ERROR: User ID cannot be empty.";
        }

        // ── Step 2: Validate message ──
        if (message == null || message.trim().isEmpty()) {
            return "ERROR: Notification message cannot be empty.";
        }

        // ── Step 3: Generate a unique notification ID ──
        String notificationId = generateNotificationId();

        // ── Step 4: Create a new Notification object ──
        Notification notification = new Notification(
                notificationId,
                userId.trim(),
                message.trim()
        );

        // ── Step 5: Add to the notifications list ──
        notifications.add(notification);

        // Log confirmation
        System.out.println("[NotificationService] Notification added"
                + " | ID: "      + notificationId
                + " | User: "    + userId
                + " | Message: " + message);

        return "SUCCESS"; // Notification added successfully
    }

    /**
     * Retrieves all notifications for a specific user.
     *
     * Steps:
     * 1. Validate that userId is not null or empty.
     * 2. Loop through all notifications.
     * 3. Collect and return only those matching the given userId.
     *
     * @param userId ID of the user whose notifications are to be fetched
     * @return List of Notification objects belonging to the user,
     *         or an empty list if none found or input is invalid
     */
    public List<Notification> getNotifications(String userId) {

        // Result list to hold matching notifications
        List<Notification> userNotifications = new ArrayList<>();

        // ── Step 1: Validate userId ──
        if (userId == null || userId.trim().isEmpty()) {
            System.out.println("[NotificationService] WARNING: " +
                               "User ID is null or empty. Returning empty list.");
            return userNotifications;
        }

        // ── Step 2: Loop through all notifications and match by userId ──
        for (Notification notification : notifications) {

            // Skip null entries for safety
            if (notification == null) {
                continue;
            }

            // Case-insensitive match on userId
            if (notification.getUserId().equalsIgnoreCase(userId.trim())) {
                userNotifications.add(notification); // Match found — add to result
            }
        }

        // ── Step 3: Log result summary ──
        System.out.println("[NotificationService] Fetched notifications"
                + " | User: "         + userId
                + " | Count: "        + userNotifications.size());

        return userNotifications; // Return all matching notifications
    }

    // ─── Helper Methods ───────────────────────────────────────

    /**
     * Returns the total count of notifications for a specific user.
     * Useful for displaying a notification badge count in the GUI.
     *
     * @param userId ID of the user
     * @return Number of notifications for that user
     */
    public int getNotificationCount(String userId) {
        return getNotifications(userId).size();
    }

    /**
     * Checks if a specific user has any notifications.
     * Useful for conditionally showing the notification bell icon in the GUI.
     *
     * @param userId ID of the user
     * @return true if the user has at least one notification, false otherwise
     */
    public boolean hasNotifications(String userId) {
        return !getNotifications(userId).isEmpty();
    }

    /**
     * Deletes all notifications for a specific user.
     * Useful for a "Clear All" button on the NotificationsPage.
     *
     * @param userId ID of the user whose notifications are to be cleared
     * @return "SUCCESS" if cleared, or an error message if input is invalid
     */
    public String clearNotifications(String userId) {

        // Validate userId
        if (userId == null || userId.trim().isEmpty()) {
            return "ERROR: User ID cannot be empty.";
        }

        // Remove all notifications matching the given userId
        notifications.removeIf(n ->
                n != null &&
                n.getUserId().equalsIgnoreCase(userId.trim())
        );

        System.out.println("[NotificationService] Cleared all notifications" +
                           " for User: " + userId);

        return "SUCCESS"; // All notifications cleared
    }

    /**
     * Sends a notification to multiple users at once.
     * Useful for broadcasting drive announcements to all students.
     *
     * @param userIds List of user IDs to send the notification to
     * @param message The notification message content
     * @return Number of notifications successfully sent
     */
    public int broadcastNotification(List<String> userIds, String message) {

        // Track how many notifications were sent successfully
        int successCount = 0;

        // Null check on userIds list
        if (userIds == null || userIds.isEmpty()) {
            System.out.println("[NotificationService] WARNING: " +
                               "No user IDs provided for broadcast.");
            return successCount;
        }

        // Send notification to each user in the list
        for (String userId : userIds) {
            String result = addNotification(userId, message);
            if ("SUCCESS".equals(result)) {
                successCount++; // Count successful sends
            }
        }

        System.out.println("[NotificationService] Broadcast complete"
                + " | Sent to: " + successCount + " users"
                + " | Message: " + message);

        return successCount; // Return total successfully sent
    }

    /**
     * Returns all notifications in the system.
     * Useful for admin overview of all system notifications.
     *
     * @return ArrayList of all Notification objects
     */
    public ArrayList<Notification> getAllNotifications() {
        return notifications;
    }

    /**
     * Generates a unique Notification ID.
     * Format: NOT001, NOT002, NOT003, ...
     *
     * @return A new unique notification ID string
     */
    private String generateNotificationId() {
        notificationCounter++;                              // Increment counter
        return String.format("NOT%03d", notificationCounter); // Format as NOT001, etc.
    }
}