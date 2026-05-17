package model;

import java.util.UUID;

/**
 * Feedback Class
 * 
 * SRP: This class only holds feedback data.
 * The processing logic is handled by FeedbackService.
 */
public class Feedback {

    private String feedbackId;
    private String description;
    private String status;

    public Feedback(String description) {
        this.feedbackId = UUID.randomUUID().toString();
        this.description = description;
        this.status = "PENDING";
    }

    // submit() just marks the feedback as submitted
    // the actual processing is done by FeedbackService
    public boolean submit() {
        
        this.status = "SUBMITTED";
        System.out.println("Feedback submitted successfully. ID: " + feedbackId);
        return true;
    }

    public String getFeedbackId() { return feedbackId; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId='" + feedbackId + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}