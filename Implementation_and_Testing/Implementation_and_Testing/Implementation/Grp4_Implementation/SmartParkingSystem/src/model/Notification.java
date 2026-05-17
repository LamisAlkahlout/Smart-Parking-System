package model;

import interfaces.INotification;
import interfaces.UIupdateObserver;
import java.util.UUID;

public class Notification implements UIupdateObserver, INotification {
	
	private String notificationId;
    private NotificationType type;
    private String message;
    private boolean isRead;
    
    public Notification() {
        this.notificationId = UUID.randomUUID().toString();
        this.isRead = false;
    }
    
    public Notification(NotificationType type, String message) {
        this.notificationId = UUID.randomUUID().toString();
        this.type = type;
        this.message = message;
        this.isRead = false;
    }

   
    @Override
    public void send(String message, User user) {
        this.message = message;
        this.isRead = false;
        System.out.println("------------------------------------------");
        System.out.println("NOTIFICATION -> " + user.getName());
        System.out.println("Type    : " + type);
        System.out.println("Message : " + message);
        System.out.println("------------------------------------------");
    }

    @Override
    public void update(Reservation reservation) {
        if (reservation == null) return;

        ReservationStatus status = reservation.getStatus();
        User user = reservation.getUser();

        switch (status) {

            case CONFIRMED:
                this.type = NotificationType.CONFIRMATION;
                this.message = "Your reservation " + reservation.getReservationId()
                        + " has been confirmed. Space: "
                        + reservation.getParkingSpace().getSpaceNumber();
                break;

            case ACTIVE:
                this.type = NotificationType.CONFIRMATION;
                this.message = "You have checked in successfully for reservation "
                        + reservation.getReservationId();
                break;

            case CANCELLED:
                this.type = NotificationType.CANCELLATION;
                this.message = "Your reservation " + reservation.getReservationId()
                        + " has been cancelled.";
                break;

            case COMPLETED:
                this.type = NotificationType.CONFIRMATION;
                this.message = "You have checked out. Reservation "
                        + reservation.getReservationId() + " is now completed.";
                break;

            case EXPIRED:
                this.type = NotificationType.ALERT;
                this.message = "Your reservation " + reservation.getReservationId()
                        + " has expired. Please make a new reservation.";
                break;

            case PENDING:
                this.type = NotificationType.REMINDER;
                this.message = "Your reservation " + reservation.getReservationId()
                        + " is pending confirmation.";
                break;

            default:
                this.type = NotificationType.ALERT;
                this.message = "Your reservation status has been updated to: " + status;
                break;
        }

        this.isRead = false;
        send(this.message, user);
    }

    public String getNotificationId() { return notificationId; }
    public NotificationType getType() { return type; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public void setType(NotificationType type) { this.type = type; }
    public void setMessage(String message) { this.message = message; }
    public void setRead(boolean isRead) { this.isRead = isRead; }

    public void markAsRead() {
        this.isRead = true;
        System.out.println("Notification " + notificationId + " marked as read.");
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + notificationId + '\'' +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}