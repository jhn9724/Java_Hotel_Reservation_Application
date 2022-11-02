package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Customer;

public class CustomerService {

    private Map<String, Customer> mapOfCustomers = new HashMap<>();

    private static CustomerService srefCustomerService = new CustomerService();

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        mapOfCustomers.put(email, customer);

    }

    public Customer getCustomer(String customerEmail) {
        return mapOfCustomers.get(customerEmail);

    }

    public Collection<Customer> getAllCustomers() {
        return mapOfCustomers.values();

    }

    public static CustomerService getsrefCustomerService() {
        return srefCustomerService;
    }

}
