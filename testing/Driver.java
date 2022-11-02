package testing;

import model.Customer;

//Will check to see if the email is in a valid email format.
public class Driver {

    public static void main(String[] args) {

        Customer customer = new Customer("first", "second", "tiuytiuytiyut");
        System.out.println(customer);

    }
}
