package main;

import model.*;
import service.AuthService;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    private static AuthService authService = new AuthService();
    private static Scanner scanner = new Scanner(System.in);

    private static ParkingSpace demoSpace = new ParkingSpace("P001", "A01", "ZONE_A");
    private static Notification notification = new Notification();

    // Store active reservations per user
    private static Map<String, Reservation> activeReservations = new HashMap<>();

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("   🚗 PARKING MANAGEMENT SYSTEM 🚗");
        System.out.println("=========================================");

        addDefaultUsers();

        while (true) {
            if (!authService.isLoggedIn()) {
                showMainMenu();
            } else {
                showParkingMenu();
            }
        }
    }

    // ================= DEFAULT USERS =================
    private static void addDefaultUsers() {

        Student student = new Student("1", "Ahmed Student",
                "student@test.com", "0551234567", "pass123", "S001");

        Staff staff = new Staff("2", "Sara Staff",
                "staff@test.com", "0551234568", "pass123", "ST001", "IT");

        Administrator admin = new Administrator("3", "Admin User",
                "admin@test.com", "0551234569", "admin123", "A001");

        authService.register(student);
        authService.register(staff);
        authService.register(admin);

        System.out.println("\n📝 Default Users:");
        System.out.println("   Student: student@test.com / pass123");
        System.out.println("   Staff: staff@test.com / pass123");
        System.out.println("   Admin: admin@test.com / admin123");
    }

    // ================= MAIN MENU =================
    private static void showMainMenu() {

        System.out.println("\n📌 MAIN MENU");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: register(); break;
            case 2: login(); break;
            case 3:
                System.out.println("👋 Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("❌ Invalid option!");
        }
    }

    // ================= REGISTER =================
    private static void register() {

        System.out.println("\n📝 REGISTRATION");
        System.out.println("1. Student");
        System.out.println("2. Staff");
        System.out.println("3. Administrator");
        System.out.print("Choose: ");

        int type = getIntInput();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = null;

        switch (type) {
            case 1:
                System.out.print("Student ID: ");
                String studentId = scanner.nextLine();
                user = new Student(UUID.randomUUID().toString(),
                        name, email, phone, password, studentId);
                break;

            case 2:
                System.out.print("Staff ID: ");
                String staffId = scanner.nextLine();
                System.out.print("Department: ");
                String dept = scanner.nextLine();
                user = new Staff(UUID.randomUUID().toString(),
                        name, email, phone, password, staffId, dept);
                break;

            case 3:
                System.out.print("Admin ID: ");
                String adminId = scanner.nextLine();
                user = new Administrator(UUID.randomUUID().toString(),
                        name, email, phone, password, adminId);
                break;

            default:
                System.out.println("❌ Invalid type!");
                return;
        }

        if (user != null) {
            authService.register(user);
        }
    }

    // ================= LOGIN =================
    private static void login() {

        System.out.print("\nEmail: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        authService.login(email, password);
    }

    // ================= DASHBOARD =================
    private static void showParkingMenu() {

        User user = authService.getCurrentLoggedInUser();
        if (user == null) return;

        System.out.println("\n=========================================");
        System.out.println("   🅿️ PARKING DASHBOARD");
        System.out.println("=========================================");
        System.out.println("Welcome " + user.getName() + " (" + user.getRole() + ")");

        System.out.println("\n📌 PARKING MENU");
        System.out.println("1. Make Reservation");
        System.out.println("2. Cancel Reservation");
        System.out.println("3. View Active Reservation");

        if (user instanceof Administrator) {
            System.out.println("4. Admin Menu");
            System.out.println("5. View All Users");
            System.out.println("6. Logout");
        } else {
            System.out.println("4. Logout");
        }

        System.out.print("Choose: ");
        int choice = getIntInput();

        if (user instanceof Administrator) {
            handleAdmin((Administrator) user, choice);
        } else {
            handleUser(user, choice);
        }
    }

    // ================= USER MENU =================
    private static void handleUser(User user, int choice) {

        switch (choice) {
            case 1: makeReservation(user); break;
            case 2: cancelReservation(user); break;
            case 3: viewReservation(user); break;
            case 4: authService.logout(); break;
            default: System.out.println("❌ Invalid option!");
        }
    }

    // ================= ADMIN MENU =================
    private static void handleAdmin(Administrator admin, int choice) {

        switch (choice) {
            case 1: makeReservation(admin); break;
            case 2: cancelReservation(admin); break;
            case 3: viewReservation(admin); break;
            case 4: adminMenu(admin); break;
            case 5: authService.displayAllUsers(); break;
            case 6: authService.logout(); break;
            default: System.out.println("❌ Invalid option!");
        }
    }

    // ================= RESERVATION =================
    private static void makeReservation(User user) {

        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = start.plusHours(2);

        Reservation res = user.makeReservation(demoSpace, start, end);

        if (res != null) {
            res.setReservationId(UUID.randomUUID().toString());
            res.setStatus(ReservationStatus.CONFIRMED);

            activeReservations.put(user.getEmail(), res);

            notification.update(res);

            System.out.println("\n✅ Reservation Created!");
            System.out.println("ID: " + res.getReservationId());
            System.out.println("Status: " + res.getStatus());
        }
    }

    private static void cancelReservation(User user) {

        Reservation res = activeReservations.get(user.getEmail());

        if (res != null) {
            user.cancelReservation(res);

            activeReservations.remove(user.getEmail());

            notification.update(res);

            System.out.println("\n✅ Reservation Cancelled!");
        } else {
            System.out.println("\n❌ No active reservation found.");
        }
    }

    private static void viewReservation(User user) {

        Reservation res = activeReservations.get(user.getEmail());

        if (res != null) {
            System.out.println("\n📄 Active Reservation:");
            System.out.println("ID: " + res.getReservationId());
            System.out.println("Start: " + res.getStartTime());
            System.out.println("End: " + res.getEndTime());
            System.out.println("Status: " + res.getStatus());
        } else {
            System.out.println("\n📭 No active reservation.");
        }
    }

    // ================= ADMIN =================
    private static void adminMenu(Administrator admin) {

        System.out.println("\n🔧 ADMIN MENU");
        System.out.println("1. Generate Report");
        System.out.println("2. Back");
        System.out.print("Choose: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                admin.generateReport();
                break;
            case 2:
                return;
            default:
                System.out.println("❌ Invalid option!");
        }
    }

    // ================= INPUT =================
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}