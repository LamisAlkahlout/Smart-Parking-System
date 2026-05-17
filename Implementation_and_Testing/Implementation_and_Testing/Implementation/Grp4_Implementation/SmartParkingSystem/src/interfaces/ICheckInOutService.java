package interfaces;

public interface ICheckInOutService {
    boolean checkIn(String reservationId);
    boolean checkOut(String reservationId);
}