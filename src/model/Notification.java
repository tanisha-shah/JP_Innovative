package model;

public class Notification {

    private int id;
    private int userId;
    private String message;

    // Constructor
    public Notification(int nid, int uid, String msg) {
        id = nid;
        userId = uid;
        message = msg;
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    // Setters

    public void setId(int nid) {
        id = nid;
    }

    public void setUserId(int uid) {
        userId = uid;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    // Helper method

    public void displayInfo() {
        System.out.println("-----------------------------");
        System.out.println("Notification ID : " + id);
        System.out.println("User ID         : " + userId);
        System.out.println("Message         : " + message);
        System.out.println("-----------------------------");
    }
}