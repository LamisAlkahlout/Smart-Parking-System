package model;

public class Guest {
    private String guestId;
    private String name;
    private String phone;
    private String email;      
    private String accessCode;

    // Constructor
    public Guest(String guestId, String name, String phone, String email, String accessCode) {
        this.guestId = guestId;
        this.name = name;
        this.phone = phone;
        this.email = email;    
        this.accessCode = accessCode;
    }

    // Getters
    public String getGuestId() { return guestId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }          
    public String getAccessCode() { return accessCode; }

    // Setters
    public void setGuestId(String guestId) { this.guestId = guestId; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }   // ✅ added
    public void setAccessCode(String accessCode) { this.accessCode = accessCode; }

    // Method as per diagram
    public Reservation createReservation() {
        System.out.println("Guest " + name + " is creating a reservation");

        Reservation reservation = new Reservation();

        return reservation;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "guestId='" + guestId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}