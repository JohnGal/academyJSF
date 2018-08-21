package dummyData;

import models.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerData {
    private long maxID = 0L;
    private List<Customer> customers = new ArrayList<>();

    public CustomerData() {
        init();
    }

    private void init(){
        addCustomer(createCustomer(
                "Cust1",
                "John",
                "Doe",
                true,
                "Testers",
                "1234567890",
                "Athens",
                "Greece",
                "test@afse.eu",
                new Date(),
                "1234"
        ));

        Customer customer = new Customer("Cust1");

        addCustomer(createCustomer(
                "Spiderman",
                "Jack",
                "Daniels",
                true,
                "Alchoholics",
                "9234515890",
                "London",
                "England",
                "jackd@afse.eu",
                new Date(),
                "3216"
        ));


        addCustomer(createCustomer(
                "AnnaKendrick",
                "Anna",
                "Kendrick",
                true,
                "LA Street",
                "7654515890",
                "Los Angeles",
                "USA",
                "annakendrick@afse.eu",
                new Date(),
                "7654"
        ));

        addCustomer(createCustomer(
                "LillianSpringer",
                "Lillian",
                "Springer",
                true,
                "Springer's",
                "7984515890",
                "London",
                "England",
                "lillian.springer@gmail.com",
                new Date(),
                "7384"
        ));

        addCustomer(createCustomer(
                "JacobJohnston",
                "Jacob",
                "Johnston",
                true,
                "Springer's",
                "7984515890",
                "Berlin",
                "Germany",
                "jacob.johnston@gmail.com",
                new Date(),
                "7384"
        ));

        addCustomer(createCustomer(
                "AnthonyParr",
                "Anthony",
                "Parr",
                true,
                "Springer's",
                "7984515890",
                "Salvador",
                "Brazil",
                "anthony.parr@gmail.com",
                new Date(),
                "7384"
        ));
    }

    private void addCustomer(Customer customer) {
        maxID++;

        customer.setId(maxID);
        customers.add(customer);

    }

    private Customer createCustomer(String userName,
                                    String firstName,
                                    String lastName,
                                    boolean hasAcceptedTerms,
                                    String street,
                                    String phoneNumber,
                                    String city,
                                    String country,
                                    String email,
                                    Date birthDate,
                                    String zipCode) {
        Customer customer = new Customer();
        customer.setUserName(userName);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setHasAcceptedTerms(hasAcceptedTerms);
        customer.setStreet(street);
        customer.setPhoneNumber(phoneNumber);
        customer.setCity(city);
        customer.setCountry(country);
        customer.setEmail(email);
        customer.setBirthDate(birthDate);
        customer.setZipCode(zipCode);

        return customer;
    }

    public List<Customer> getCustomerData() {
        return customers;
    }

    public long getMaxID() {
        return maxID;
    }
}
