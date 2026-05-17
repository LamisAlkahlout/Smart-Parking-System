package interfaces;

import model.Feedback;

public interface IFeedbackService {
    boolean submitFeedback(Feedback feedback);
}