package menu;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import model.IRoom;
import api.AdminResource;
import model.Room;
import model.RoomType;

public class AdminMenu {

    static Scanner scanner = new Scanner(System.in);

    static AdminResource adminResource = AdminResource.getsrefAdminResource();
    private static AdminMenu getsrefAdminMenu = new AdminMenu();
    static MainMenu getMainMenu = MainMenu.getMainMenu();

    public static AdminMenu getadminmenu() {
        return getsrefAdminMenu;
    }

    public void displayMenu() {
        System.out.println("-Hotel Reservation (Admin Menu)-");
        System.out.println("Here are a list of commands: ");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("Please choose a command from the menu by inputting the corresponding number");
        System.out.println("..............................");

    }

    public void pressEnter() {
        System.out.println("-Press Enter to Continue-");
        scanner.nextLine();
        displayMenu();
    }

    public void adminMenu() throws ParseException {
        String choice = "";

        displayMenu();

        while (choice != "5") {

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayMenu();
                    System.out.println("Here is a list of all customers: ");
                    adminResource.getAllCustomers().forEach(result -> System.out.println(result));
                    pressEnter();

                    break;
                case "2":
                    displayMenu();
                    System.out.println("Here is a list of all rooms available: ");
                    adminResource.getAllRooms().forEach(result -> System.out.println(result));
                    pressEnter();
                    break;
                case "3":
                    System.out.println("Here is a list of all current reservations: ");
                    adminResource.displayAllReservations();
                    pressEnter();
                    break;
                case "4":
                    System.out.println("Please follow directions bellow to add a room");
                    addARoom();
                    pressEnter();
                    break;
                case "5":
                    getMainMenu.mainMenu();
                default:
                    displayMenu();
                    System.out.println("Not a valid input - Please choose a number from 1 to 5");
                    break;
            }
        }

    }

    private static void addARoom() {
        List<IRoom> addThisRoom = new LinkedList<>();
        Double addRoomPrice = 0.00;
        String addRoomNumber = "";
        boolean keepRunning = true;
        boolean runnnn = true;

        System.out.println("Enter Room Number: ");
        while (runnnn) {

            try {
                addRoomNumber = scanner.nextLine();
                if (Integer.parseInt(addRoomNumber) > 0) {
                    runnnn = false;
                    break;
                }
                ;
            } catch (NumberFormatException ex) {
                System.out.println("Please input a valid room number *integer*: ");
            }

        }

        System.out.println("Enter Room Price: ");
        while (keepRunning) {

            try {

                addRoomPrice = Double.parseDouble(scanner.next());
                while (new BigDecimal(String.valueOf(addRoomPrice)).scale() > 2 == true) {
                    System.out.println("Not a valid room price - Please enter a valid price: ");
                    addRoomPrice = Double.parseDouble(scanner.next());

                }
                keepRunning = false;
                break;
            } catch (NumberFormatException ignore) {
                System.out.println("Not a valid room price - Please enter a valid price");
            }

        }
        boolean run = true;
        while (run) {
            try {
                scanner.nextLine();
                System.out.println("Enter Room Type: Type SINGLE or DOUBLE ");
                RoomType addRoomType = RoomType.valueOf(scanner.nextLine().toUpperCase());
                Room room = new Room(addRoomNumber, addRoomPrice, addRoomType);
                addThisRoom.add(room);
                adminResource.addRoom(addThisRoom);
                System.out.println("------Room added------");
                run = false;
            } catch (IllegalArgumentException exception) {
                System.out.println("*Invalid input* Please input SINGLE OR DOUBLE");
                System.out.println("-Press ENTER to try again-");
            }
        }
    }

}