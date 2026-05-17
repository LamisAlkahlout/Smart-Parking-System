package interfaces;
import model.User;


public interface INotification {
	void send(String message, User user);


}
