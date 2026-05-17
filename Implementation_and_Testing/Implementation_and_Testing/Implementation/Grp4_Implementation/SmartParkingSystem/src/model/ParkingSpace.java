package model;

import java.time.LocalDateTime;

public class ParkingSpace {
    private String parkingId;
    private String spaceNumber;
    private String zoneId;
    private ParkingSpaceStatus status;
    private Long lockExpiry;
    
    // Constructor with 4 parameters
    public ParkingSpace(String parkingId, String spaceNumber, String zoneId, ParkingSpaceStatus status) {
        this.parkingId = parkingId;
        this.spaceNumber = spaceNumber;
        this.zoneId = zoneId;
        this.status = status;
        this.lockExpiry = null;
    }
    
    // Constructor with 3 parameters (default status = AVAILABLE)
    public ParkingSpace(String parkingId, String spaceNumber, String zoneId) {
        this(parkingId, spaceNumber, zoneId, ParkingSpaceStatus.AVAILABLE);
    }
    
    // Getters and Setters
    public String getParkingId() { return parkingId; }
    public String getSpaceNumber() { return spaceNumber; }
    public String getZoneId() { return zoneId; }
    public ParkingSpaceStatus getStatus() { return status; }
    public void setStatus(ParkingSpaceStatus status) { this.status = status; }
    public Long getLockExpiry() { return lockExpiry; }
    public void setLockExpiry(Long lockExpiry) { this.lockExpiry = lockExpiry; }
    
    // Methods
    public boolean isAvailable() {
        return status == ParkingSpaceStatus.AVAILABLE;
    }
    
    public void lockTemporarily() {
        if (isAvailable()) {
            this.status = ParkingSpaceStatus.LOCKED;
            this.lockExpiry = System.currentTimeMillis() + (5 * 60 * 1000); // 5 minutes
            System.out.println("Space " + spaceNumber + " locked for 5 minutes");
        }
    }
    
    public void release() {
        this.status = ParkingSpaceStatus.AVAILABLE;
        this.lockExpiry = null;
        System.out.println("Space " + spaceNumber + " released");
    }
}