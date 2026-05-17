package model;

public enum NotificationType {
	
	REMINDER,       // Sent before reservation start time
    CONFIRMATION,   // Sent when reservation is confirmed or checked in
    CANCELLATION,   // Sent when reservation is cancelled
    ALERT           // Sent when reservation is expired or has an issue


}
