package model;

import java.util.regex.Pattern;


public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String emailRegex = "^(.+)@(.+).(.+)$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
        if (pattern.matcher(email).matches() == false) {
            throw new IllegalArgumentException("Error, Invalid email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "(First Name: " + firstName +")"+
                " (Last Name: " + lastName +")"+
                " (Email: " + email +")";
    }

    public String getEmail() {
        return this.email;
    }

}
