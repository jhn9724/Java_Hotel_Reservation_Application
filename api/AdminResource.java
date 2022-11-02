package api;

import java.util.Collection;
import java.util.List;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {

    private static AdminResource srefAdminResource = new AdminResource();

    public static AdminResource getsrefAdminResource() {
        return srefAdminResource;
    }

    private CustomerService customerService = CustomerService.getsrefCustomerService();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);

    }

    private ReservationService reservationService = ReservationService.getsrefReservationService();

    public void addRoom(List<IRoom> rooms) {
        for (IRoom aRoom : rooms) {
            reservationService.addRoom(aRoom);

        }

    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getRooms();

    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();

    }

    public void displayAllReservations() {

        reservationService.printAllReservation();

    }

}
