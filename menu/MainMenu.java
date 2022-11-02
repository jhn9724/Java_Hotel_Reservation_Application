package menu;

import java.util.Collection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

public class MainMenu {
    static Scanner scanner = new Scanner(System.in);

    private static MainMenu getsrefMainMenu = new MainMenu();

    public static MainMenu getMainMenu() {
        return getsrefMainMenu;
    }

    public void displayMenu() {
        System.out.println("-Hotel Reservation (Main Menu)-");
        System.out.println("Here are a list of commands: ");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("Please choose a command from the menu by inputting the corresponding number");
        System.out.println("..............................");
    }

    String choice = "";

    public void mainMenu() throws ParseException {
        displayMenu();

        while (choice != "5") {

            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Follow directions below to find and reserve a room");
                    findReserveRoom();
                    pressEnter();
                    break;
                case "2":
                    showReservation();
                    pressEnter();
                    break;
                case "3":
                    System.out.println("Follow directions below to create a customer account");
                    createCustomerAccount();
                    pressEnter();
                    break;
                case "4":
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.adminMenu();
                    break;

                case "5":
                    System.out.println("Good-Bye");
                    choice = "5";
                    break;
                default:
                    displayMenu();
                    System.out.println("Not a valid input - Please choose a number from 1 to 5:");
                    break;

            }

        }

    }

    /**
     * @throws ParseException
     */
    private void findReserveRoom() throws ParseException {

        try {

            System.out.println("Please enter the check-in date MM/dd/yyyy");
            String dateEntered = scanner.nextLine();
            Date checkIn = new SimpleDateFormat("MM/dd/yyyy").parse(dateEntered);
            System.out.println("Please enter the check-out date mm/dd/yyyy");
            dateEntered = scanner.nextLine();
            Date checkOut = new SimpleDateFormat("MM/dd/yyyy").parse(dateEntered);
            if (checkIn.before(checkOut) == true) {
            } else {
                System.out.println("Check-in date needs to be before the check-out date");
                findReserveRoom();
            }
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available on those dates \n");
                System.out.println("Checking to see if there are any rooms available 7 days out from date inputted");
                Calendar addDates = Calendar.getInstance();
                addDates.setTime(checkIn);
                addDates.add(Calendar.DAY_OF_YEAR, 7);
                checkIn = addDates.getTime();
                addDates.setTime(checkOut);
                addDates.add(Calendar.DAY_OF_YEAR, 7);
                checkOut = addDates.getTime();
                System.out.println(checkIn + " - " + checkOut + "\n");
                availableRooms = hotelResource.findARoom(checkIn, checkOut);
                availableRooms.forEach(System.out::println);
                if (availableRooms.isEmpty()) {
                    System.out.println("Sorry, no rooms available on those dates either");
                } else {
                    System.out.println("The rooms below are available: ");
                    reserveRoom(checkIn, checkOut);

                }
            } else {
                System.out.println("The current rooms available are listed below: ");
                availableRooms.forEach(System.out::println);
                checkCustomer();
                reserveRoom(checkIn, checkOut);
            }

        } catch (InputMismatchException | ParseException exception) {
            System.out.println("*Wrong date format* Format needs to be MM/dd/yyyy");
            findReserveRoom();
        }

    }

    private static HotelResource hotelResource = HotelResource.getsrefHotelResource();

    private static void showReservation() {
        String emailRegex = "^(.+)@(.+).(.+)$";
        final Pattern pattern = Pattern.compile(emailRegex);
        System.out.println("Please enter your email: ");
        String emailResponse = scanner.nextLine();
        if (pattern.matcher(emailResponse).matches() == false) {
            System.out.println("Error! *Please enter a valid email*");
            showReservation();
        } else {
            showReservations(hotelResource.getCustomersReservations(emailResponse));
        }

    }

    private static void showReservations(Collection<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("You have no reservations");

        } else {
            reservations.forEach(reservation -> System.out.println(reservation));
        }
    }

    private void createCustomerAccount() {
        String emailRegex = "^(.+)@(.+).(.+)$";
        final Pattern pattern = Pattern.compile(emailRegex);

        System.out.println("Please enter your email: ");
        String customerEmail = scanner.nextLine();
        if (pattern.matcher(customerEmail).matches() == false) {
            System.out.println("Error! *Please enter a valid email*");
            createCustomerAccount();
        } else {
            System.out.println("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter your last name: ");
            String lastName = scanner.nextLine();
            hotelResource.createACustomer(customerEmail, firstName, lastName);
            displayMenu();
            System.out.println("Account Created");
        }

    }

    public void pressEnter() {
        System.out.println("-Press Enter to Continue-");
        scanner.nextLine();
        displayMenu();
    }

    private void checkCustomer() throws ParseException {

        System.out.println("Do you have an account with us? Yes or No");
        String customerResponse = scanner.nextLine();
        while ((customerResponse.equals("yes") == false) && (customerResponse.equals("no") == false)) {
            System.out.println("Not a valid response - Please input Yes or No");
            checkCustomer();
            break;
        }
        if (customerResponse.equals("no") == true) {
            System.out.println("Please create an account before continuing ");
            pressEnter();
            mainMenu();
        }

    }

    private void reserveRoom(Date checkIn, Date checkOut) throws ParseException {

        System.out.println("Please enter your email: ");
        String email = scanner.nextLine();

        Customer checkCustomer = null;

        while (checkCustomer == null) {

            checkCustomer = hotelResource.getCustomer(email);
            if (checkCustomer == null) {
                System.out.println("Your account could not be located");
                System.out.println("Please create an account to reserve a room");
                pressEnter();
                mainMenu();
                ;
            }
        }

        Collection<IRoom> roomsAvailable = hotelResource.findARoom(checkIn, checkOut);
        if (roomsAvailable.isEmpty()) {
            System.out.print("No rooms currently available - Please get an administrator to add rooms");
        }
        System.out.println("Reserve a room? Yes or No");
        String reserveResponse = scanner.nextLine();
        reserveResponse = reserveResponse.toLowerCase();
        while ((reserveResponse.equals("yes") == false) && (reserveResponse.equals("no") == false)) {
            System.out.println("Not a valid response - Please input Yes or No");
            reserveRoom(checkIn, checkOut);
            break;
        }
        System.out.println("Enter the room number you would like to reserve: ");
        String roomNumber = scanner.nextLine();
        for (IRoom roomCheck : roomsAvailable) {
            if (roomCheck.getRoomNumber().equals(roomNumber)) {

                IRoom room = roomCheck;
                hotelResource.bookARoom(email, room, checkIn, checkOut);
                System.out.println("Here is the customer's reservation confirmation: ");
                Customer customer = hotelResource.getCustomer(email);
                Reservation reservationConfirmation = new Reservation(customer, room, checkIn, checkOut);
                System.out.println(reservationConfirmation);
                pressEnter();
                mainMenu();

            }

        }
        System.out.println("The room number inputted is not available");
        System.out.println("Please try again with a different room number");

    }

}
