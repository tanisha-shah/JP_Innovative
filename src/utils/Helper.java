// File: src/utils/Helper.java
// Package: utils
// Description: General-purpose utility/helper methods for the Smart Campus Placement System.

package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Helper class.
 * Provides general-purpose utility methods that can be reused
 * across all layers of the Smart Campus Placement System.
 *
 * Responsibilities:
 * - Printing formatted messages to the console
 * - Formatting strings for display
 * - Date and time utilities
 * - List formatting helpers
 *
 * All methods are static — no need to create an instance of this class.
 * Usage: Helper.printMessage("Hello"); Helper.formatName("alice");
 */
public class Helper {

    // ─── Constants ────────────────────────────────────────────

    /** Separator line used for console output formatting */
    public static final String SEPARATOR =
            "───────────────────────────────────────────────";

    /** Bold separator line used for section headers */
    public static final String BOLD_SEPARATOR =
            "═══════════════════════════════════════════════";

    /** Application name constant for reuse across the system */
    public static final String APP_NAME =
            "Smart Campus Placement Management System";

    // ─── Private Constructor ──────────────────────────────────

    /**
     * Private constructor.
     * Prevents instantiation of this utility class.
     * All methods are static and should be called directly.
     */
    private Helper() {
        // Utility class — do not instantiate
    }

    // ─── Print Methods ────────────────────────────────────────

    /**
     * Prints a simple message to the console.
     * Adds a "[INFO]" prefix for easy identification in logs.
     *
     * Usage: Helper.printMessage("Application started.");
     *
     * @param message The message string to print
     */
    public static void printMessage(String message) {
        System.out.println("[INFO] " + message);
    }

    /**
     * Prints an error message to the console.
     * Adds an "[ERROR]" prefix to distinguish from info messages.
     *
     * Usage: Helper.printError("Invalid email format.");
     *
     * @param message The error message string to print
     */
    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    /**
     * Prints a success message to the console.
     * Adds a "[SUCCESS]" prefix for positive confirmations.
     *
     * Usage: Helper.printSuccess("Student registered successfully.");
     *
     * @param message The success message string to print
     */
    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    /**
     * Prints a warning message to the console.
     * Adds a "[WARNING]" prefix for non-critical issues.
     *
     * Usage: Helper.printWarning("No drives available.");
     *
     * @param message The warning message string to print
     */
    public static void printWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    /**
     * Prints a section header to the console with
     * a separator line above and below the title.
     * Useful for clearly marking sections in console output.
     *
     * Usage: Helper.printSectionHeader("Student Dashboard");
     *
     * Output:
     * ───────────────────────────────────────────────
     *   Student Dashboard
     * ───────────────────────────────────────────────
     *
     * @param title The section title to display
     */
    public static void printSectionHeader(String title) {
        System.out.println(SEPARATOR);
        System.out.println("  " + title);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the application startup banner to the console.
     * Called once from Main.java when the application launches.
     *
     * Output:
     * ═══════════════════════════════════════════════
     *    Smart Campus Placement Management System
     * ═══════════════════════════════════════════════
     */
    public static void printAppBanner() {
        System.out.println(BOLD_SEPARATOR);
        System.out.println("   " + APP_NAME);
        System.out.println(BOLD_SEPARATOR);
    }

    /**
     * Prints a labeled key-value pair to the console.
     * Useful for displaying student or drive details in a readable format.
     *
     * Usage: Helper.printField("Name", "Alice");
     * Output:   Name       : Alice
     *
     * @param label The field label (e.g., "Name", "Email")
     * @param value The field value (e.g., "Alice", "alice@email.com")
     */
    public static void printField(String label, String value) {
        System.out.printf("  %-12s : %s%n", label, value);
    }

    // ─── String Formatting Methods ────────────────────────────

    /**
     * Capitalizes the first letter of a given string
     * and converts the rest to lowercase.
     * Useful for formatting names and branch names consistently.
     *
     * Usage: Helper.formatName("aLICE") → "Alice"
     *
     * @param text The string to format
     * @return Formatted string with first letter capitalized,
     *         or empty string if input is null or empty
     */
    public static String formatName(String text) {

        // Null or empty check
        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        // Trim, lowercase all, then capitalize first letter
        String trimmed = text.trim().toLowerCase();
        return Character.toUpperCase(trimmed.charAt(0))
               + trimmed.substring(1);
    }

    /**
     * Converts a string to uppercase and trims whitespace.
     * Useful for formatting branch names (e.g., "cse" → "CSE").
     *
     * Usage: Helper.formatBranch("cse") → "CSE"
     *
     * @param text The string to convert to uppercase
     * @return Uppercase trimmed string,
     *         or empty string if input is null or empty
     */
    public static String formatBranch(String text) {

        // Null or empty check
        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        return text.trim().toUpperCase();
    }

    /**
     * Formats a double value to exactly two decimal places.
     * Useful for displaying CGPA and resume scores consistently.
     *
     * Usage: Helper.formatDouble(8.5) → "8.50"
     *        Helper.formatDouble(7.0) → "7.00"
     *
     * @param value The double value to format
     * @return Formatted string with exactly 2 decimal places
     */
    public static String formatDouble(double value) {
        return String.format("%.2f", value);
    }

    /**
     * Converts a List of strings into a single comma-separated string.
     * Useful for displaying skills or branches in a readable format.
     *
     * Usage: Helper.listToString(["Java", "SQL", "Python"])
     *        → "Java, SQL, Python"
     *
     * @param list The List of strings to join
     * @return A comma-separated string of all list items,
     *         or "None" if the list is null or empty
     */
    public static String listToString(List<String> list) {

        // Null or empty list — return a readable placeholder
        if (list == null || list.isEmpty()) {
            return "None";
        }

        return String.join(", ", list);
    }

    /**
     * Converts a comma-separated string into a List of strings.
     * Useful for parsing skills stored as "Java,SQL,Python" from a text file.
     *
     * Usage: Helper.stringToList("Java,SQL,Python")
     *        → ["Java", "SQL", "Python"]
     *
     * @param text Comma-separated string to split into a list
     * @return List of trimmed strings,
     *         or an empty list if input is null or empty
     */
    public static List<String> stringToList(String text) {

        // Result list
        List<String> result = new java.util.ArrayList<>();

        // Null or empty check
        if (text == null || text.trim().isEmpty()) {
            return result;
        }

        // Split by comma and trim each item
        String[] parts = text.split(",");
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                result.add(part.trim()); // Add trimmed item to list
            }
        }

        return result;
    }

    /**
     * Checks if a string is null or empty after trimming whitespace.
     * A single reusable null/empty check used across all layers.
     *
     * Usage: Helper.isNullOrEmpty("") → true
     *        Helper.isNullOrEmpty("Alice") → false
     *
     * @param text The string to check
     * @return true if the string is null or blank, false otherwise
     */
    public static boolean isNullOrEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * Truncates a string to a maximum length and appends "..."
     * if it exceeds that length.
     * Useful for displaying long messages in table cells or labels.
     *
     * Usage: Helper.truncate("Hello World", 7) → "Hello W..."
     *
     * @param text      The string to truncate
     * @param maxLength Maximum allowed character length
     * @return Truncated string with "..." appended if needed,
     *         or original string if within the limit
     */
    public static String truncate(String text, int maxLength) {

        // Null or empty check
        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        // Return original if within limit
        if (text.length() <= maxLength) {
            return text;
        }

        // Truncate and append "..."
        return text.substring(0, maxLength) + "...";
    }

    // ─── Date and Time Methods ────────────────────────────────

    /**
     * Returns the current date as a formatted string.
     * Format: dd-MM-yyyy (e.g., 25-12-2024)
     * Useful for timestamping notifications and applications.
     *
     * Usage: Helper.getCurrentDate() → "25-12-2024"
     *
     * @return Current date string in dd-MM-yyyy format
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }

    /**
     * Returns the current date and time as a formatted string.
     * Format: dd-MM-yyyy HH:mm:ss (e.g., 25-12-2024 10:30:45)
     * Useful for logging and notification timestamps.
     *
     * Usage: Helper.getCurrentDateTime() → "25-12-2024 10:30:45"
     *
     * @return Current date and time string in dd-MM-yyyy HH:mm:ss format
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(new Date());
    }
}