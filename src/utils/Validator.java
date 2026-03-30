// File: src/utils/Validator.java
// Package: utils
// Description: Provides input validation utility methods for the Smart Campus Placement System.

package utils;

/**
 * Validator class.
 * Provides reusable input validation methods used across
 * all layers of the Smart Campus Placement System.
 *
 * Responsibilities:
 * - Validate email format
 * - Validate CGPA range
 * - Validate common string inputs
 * - Validate password strength
 * - Validate numeric inputs
 *
 * All methods are static — no need to create an instance of this class.
 * Usage: Validator.validateEmail("alice@email.com");
 *        Validator.validateCGPA(8.5);
 */
public class Validator {

    // ─── Private Constructor ──────────────────────────────────

    /**
     * Private constructor.
     * Prevents instantiation of this utility class.
     * All methods are static and should be called directly.
     */
    private Validator() {
        // Utility class — do not instantiate
    }

    // ─── Email Validation ─────────────────────────────────────

    /**
     * Validates whether the given email address is in a correct format.
     *
     * Rules:
     * 1. Email must not be null or empty.
     * 2. Email must contain exactly one '@' symbol.
     * 3. Email must contain at least one '.' after the '@'.
     * 4. Email must not start or end with '@' or '.'.
     * 5. Email must have characters before '@' and after the last '.'.
     *
     * Valid examples   : "alice@email.com", "bob123@college.ac.in"
     * Invalid examples : "alice@", "@email.com", "aliceemail.com", ""
     *
     * @param email The email string to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean validateEmail(String email) {

        // ── Rule 1: Null or empty check ──
        if (email == null || email.trim().isEmpty()) {
            System.out.println("[Validator] Email validation failed: "
                             + "Email is null or empty.");
            return false;
        }

        String trimmedEmail = email.trim();

        // ── Rule 2: Must contain '@' symbol ──
        if (!trimmedEmail.contains("@")) {
            System.out.println("[Validator] Email validation failed: "
                             + "Missing '@' symbol. → " + trimmedEmail);
            return false;
        }

        // ── Rule 3: Must not start with '@' ──
        if (trimmedEmail.startsWith("@")) {
            System.out.println("[Validator] Email validation failed: "
                             + "Email cannot start with '@'. → " + trimmedEmail);
            return false;
        }

        // ── Rule 4: Split email into local and domain parts ──
        String[] parts = trimmedEmail.split("@");

        // Must have exactly two parts: local@domain
        if (parts.length != 2) {
            System.out.println("[Validator] Email validation failed: "
                             + "Email must have exactly one '@'. → " + trimmedEmail);
            return false;
        }

        String localPart  = parts[0]; // Part before '@'
        String domainPart = parts[1]; // Part after  '@'

        // ── Rule 5: Local part must not be empty ──
        if (localPart.isEmpty()) {
            System.out.println("[Validator] Email validation failed: "
                             + "Nothing before '@'. → " + trimmedEmail);
            return false;
        }

        // ── Rule 6: Domain part must contain '.' ──
        if (!domainPart.contains(".")) {
            System.out.println("[Validator] Email validation failed: "
                             + "Domain missing '.'. → " + trimmedEmail);
            return false;
        }

        // ── Rule 7: Domain must not start or end with '.' ──
        if (domainPart.startsWith(".") || domainPart.endsWith(".")) {
            System.out.println("[Validator] Email validation failed: "
                             + "Domain cannot start or end with '.'. → " + trimmedEmail);
            return false;
        }

        // ── Rule 8: Must have characters after the last '.' ──
        int lastDotIndex = domainPart.lastIndexOf(".");
        String extension = domainPart.substring(lastDotIndex + 1);

        if (extension.isEmpty()) {
            System.out.println("[Validator] Email validation failed: "
                             + "No extension after last '.'. → " + trimmedEmail);
            return false;
        }

        // ── All rules passed ──
        System.out.println("[Validator] Email validation passed: " + trimmedEmail);
        return true;
    }

    // ─── CGPA Validation ──────────────────────────────────────

    /**
     * Validates whether the given CGPA value is within the acceptable range.
     *
     * Rules:
     * 1. CGPA must be greater than or equal to 0.0
     * 2. CGPA must be less than or equal to 10.0
     *
     * Valid examples   : 0.0, 6.5, 8.75, 10.0
     * Invalid examples : -1.0, 10.1, 15.0
     *
     * @param cgpa The CGPA value to validate
     * @return true if CGPA is between 0.0 and 10.0 (inclusive),
     *         false otherwise
     */
    public static boolean validateCGPA(double cgpa) {

        // ── Check if CGPA is within valid range ──
        if (cgpa < 0.0 || cgpa > 10.0) {
            System.out.println("[Validator] CGPA validation failed: "
                             + "CGPA must be between 0.0 and 10.0. "
                             + "Given: " + cgpa);
            return false;
        }

        // ── All rules passed ──
        System.out.println("[Validator] CGPA validation passed: " + cgpa);
        return true;
    }

    // ─── String Validation ────────────────────────────────────

    /**
     * Validates whether a given string is not null and not empty.
     * Used for validating names, branches, roles, and other text fields.
     *
     * Usage: Validator.validateNotEmpty("Alice") → true
     *        Validator.validateNotEmpty("")       → false
     *
     * @param value     The string value to validate
     * @param fieldName Name of the field being validated (for logging)
     * @return true if the string is not null and not blank,
     *         false otherwise
     */
    public static boolean validateNotEmpty(String value, String fieldName) {

        // Null or empty check
        if (value == null || value.trim().isEmpty()) {
            System.out.println("[Validator] Validation failed: "
                             + fieldName + " cannot be empty.");
            return false;
        }

        System.out.println("[Validator] Validation passed: "
                         + fieldName + " = " + value.trim());
        return true;
    }

    // ─── Password Validation ──────────────────────────────────

    /**
     * Validates whether the given password meets minimum requirements.
     *
     * Rules:
     * 1. Password must not be null or empty.
     * 2. Password must be at least 4 characters long.
     *
     * Valid examples   : "pass", "abc1", "securePass123"
     * Invalid examples : "", "ab", null
     *
     * @param password The password string to validate
     * @return true if password meets minimum requirements,
     *         false otherwise
     */
    public static boolean validatePassword(String password) {

        // ── Rule 1: Null or empty check ──
        if (password == null || password.trim().isEmpty()) {
            System.out.println("[Validator] Password validation failed: "
                             + "Password is null or empty.");
            return false;
        }

        // ── Rule 2: Minimum length of 4 characters ──
        if (password.length() < 4) {
            System.out.println("[Validator] Password validation failed: "
                             + "Password must be at least 4 characters. "
                             + "Length: " + password.length());
            return false;
        }

        // ── All rules passed ──
        System.out.println("[Validator] Password validation passed.");
        return true;
    }

    // ─── Name Validation ──────────────────────────────────────

    /**
     * Validates whether the given name contains only alphabetic characters
     * and spaces. Prevents names with numbers or special characters.
     *
     * Valid examples   : "Alice", "John Doe", "Mary Jane"
     * Invalid examples : "Alice123", "Bob@email", ""
     *
     * @param name The name string to validate
     * @return true if name contains only letters and spaces,
     *         false otherwise
     */
    public static boolean validateName(String name) {

        // ── Null or empty check ──
        if (name == null || name.trim().isEmpty()) {
            System.out.println("[Validator] Name validation failed: "
                             + "Name is null or empty.");
            return false;
        }

        // ── Check that name contains only letters and spaces ──
        String trimmedName = name.trim();
        for (char c : trimmedName.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                System.out.println("[Validator] Name validation failed: "
                                 + "Name contains invalid character: '" + c + "'");
                return false;
            }
        }

        // ── All rules passed ──
        System.out.println("[Validator] Name validation passed: " + trimmedName);
        return true;
    }

    // ─── Numeric Validation ───────────────────────────────────

    /**
     * Validates whether a given string can be parsed as a valid double.
     * Useful for validating CGPA input from text fields in the GUI.
     *
     * Valid examples   : "8.5", "7.0", "10"
     * Invalid examples : "abc", "8.5.5", "", null
     *
     * @param value The string to check for numeric validity
     * @return true if the string is a valid double number,
     *         false otherwise
     */
    public static boolean isValidDouble(String value) {

        // Null or empty check
        if (value == null || value.trim().isEmpty()) {
            System.out.println("[Validator] Numeric validation failed: "
                             + "Value is null or empty.");
            return false;
        }

        // Attempt to parse the value as a double
        try {
            Double.parseDouble(value.trim());
            System.out.println("[Validator] Numeric validation passed: " + value.trim());
            return true; // Successfully parsed — valid number
        } catch (NumberFormatException e) {
            System.out.println("[Validator] Numeric validation failed: "
                             + "'" + value.trim() + "' is not a valid number.");
            return false; // Could not parse — not a valid number
        }
    }

    /**
     * Validates whether a given string can be parsed as a valid integer.
     * Useful for validating ID numbers or count inputs from the GUI.
     *
     * Valid examples   : "1", "100", "42"
     * Invalid examples : "1.5", "abc", "", null
     *
     * @param value The string to check for integer validity
     * @return true if the string is a valid integer,
     *         false otherwise
     */
    public static boolean isValidInteger(String value) {

        // Null or empty check
        if (value == null || value.trim().isEmpty()) {
            System.out.println("[Validator] Integer validation failed: "
                             + "Value is null or empty.");
            return false;
        }

        // Attempt to parse the value as an integer
        try {
            Integer.parseInt(value.trim());
            System.out.println("[Validator] Integer validation passed: " + value.trim());
            return true; // Successfully parsed — valid integer
        } catch (NumberFormatException e) {
            System.out.println("[Validator] Integer validation failed: "
                             + "'" + value.trim() + "' is not a valid integer.");
            return false; // Could not parse — not a valid integer
        }
    }

    // ─── ID Validation ────────────────────────────────────────

    /**
     * Validates whether a given ID string follows the expected format.
     * IDs must not be null or empty and must have a minimum length of 3.
     *
     * Valid examples   : "STU001", "COM002", "DRV003", "ADM001"
     * Invalid examples : "", "S", null
     *
     * @param id      The ID string to validate
     * @param idLabel Label for the ID type used in logging (e.g., "Student ID")
     * @return true if the ID is valid, false otherwise
     */
    public static boolean validateId(String id, String idLabel) {

        // ── Null or empty check ──
        if (id == null || id.trim().isEmpty()) {
            System.out.println("[Validator] " + idLabel
                             + " validation failed: ID is null or empty.");
            return false;
        }

        // ── Minimum length of 3 characters ──
        if (id.trim().length() < 3) {
            System.out.println("[Validator] " + idLabel
                             + " validation failed: ID too short → " + id.trim());
            return false;
        }

        // ── All rules passed ──
        System.out.println("[Validator] " + idLabel
                         + " validation passed: " + id.trim());
        return true;
    }
}