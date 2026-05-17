package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dashboard Class - Facade Pattern
 *
 * Facade Pattern:
 * Instead of calling ParkingZone, Reservation, and other classes
 * separately, Dashboard provides ONE simple method getMetrics()
 * that gathers everything and returns it at once.
 *
 * SRP: Dashboard's only job is to collect and return system metrics.
 */


public class Dashboard {
	
	private float occupancyRate;
	 // Facade Pattern - hides all complexity behind one method
    public Map<String, Object> getMetrics(ParkingZone zone, List<Reservation> reservations) {

        Map<String, Object> metrics = new HashMap<>();

        // --- Parking Space Metrics ---
        int totalSpaces = zone.getCapacity();
        int availableSpaces = zone.getAvailableSpacesCount();
        int occupiedSpaces = totalSpaces - availableSpaces;

        // calculate occupancy rate
        if (totalSpaces > 0) {
            this.occupancyRate = ((float) occupiedSpaces / totalSpaces) * 100;
        } else {
            this.occupancyRate = 0;
        }

        // --- Reservation Metrics ---
        int totalReservations = reservations.size();
        int activeReservations = 0;
        int cancelledReservations = 0;
        int completedReservations = 0;

        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.ACTIVE) {
                activeReservations++;
            } else if (r.getStatus() == ReservationStatus.CANCELLED) {
                cancelledReservations++;
            } else if (r.getStatus() == ReservationStatus.COMPLETED) {
                completedReservations++;
            }
        }

        // --- Put everything in the Map ---
        metrics.put("totalSpaces", totalSpaces);
        metrics.put("availableSpaces", availableSpaces);
        metrics.put("occupiedSpaces", occupiedSpaces);
        metrics.put("occupancyRate", occupancyRate);
        metrics.put("totalReservations", totalReservations);
        metrics.put("activeReservations", activeReservations);
        metrics.put("cancelledReservations", cancelledReservations);
        metrics.put("completedReservations", completedReservations);

        return metrics;
    }

    // print the metrics in a readable format
    public void displayMetrics(ParkingZone zone, List<Reservation> reservations) {
        Map<String, Object> metrics = getMetrics(zone, reservations);

        System.out.println("==========================================");
        System.out.println("           SYSTEM DASHBOARD");
        System.out.println("==========================================");
        System.out.println("PARKING SPACES");
        System.out.println("  Total Spaces       : " + metrics.get("totalSpaces"));
        System.out.println("  Available Spaces   : " + metrics.get("availableSpaces"));
        System.out.println("  Occupied Spaces    : " + metrics.get("occupiedSpaces"));
        System.out.println("  Occupancy Rate     : " + metrics.get("occupancyRate") + "%");
        System.out.println("------------------------------------------");
        System.out.println("RESERVATIONS");
        System.out.println("  Total Reservations : " + metrics.get("totalReservations"));
        System.out.println("  Active             : " + metrics.get("activeReservations"));
        System.out.println("  Completed          : " + metrics.get("completedReservations"));
        System.out.println("  Cancelled          : " + metrics.get("cancelledReservations"));
        System.out.println("==========================================");
    }

    public float getOccupancyRate() { return occupancyRate; }
}

