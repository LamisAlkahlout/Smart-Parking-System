package interfaces;

import model.Reservation;
import model.ParkingSpace;
import model.User;
import java.time.LocalDateTime;

public interface IReservationService {
    Reservation makeReservation(User user, ParkingSpace space, LocalDateTime start, LocalDateTime end);
    boolean cancelReservation(String reservationId);
    boolean modifyReservation(String reservationId, LocalDateTime newStart, LocalDateTime newEnd);
}