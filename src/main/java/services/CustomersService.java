package services;

import exceptions.CustomerNotFoundException;
import models.Customer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class CustomersService implements Serializable {

    private static final long serialVersionUID = -1028825087637725483L;

    private List<Customer> customers;
    private Long maxID = 0L;

    @PostConstruct
    public void init() {
        customers = new ArrayList<>();

        Customer customer = new Customer("Cust1");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setHasAcceptedTerms(true);
        customer.setStreet("Testers");
        customer.setPhoneNumber("1234567890");
        customer.setCity("Athens");
        customer.setCountry("Greece");
        customer.setEmail("test@afse.eu");
        customer.setBirthDate(new Date());
        customer.setZipCode("1234");

        addCustomer(customer);

        customer = new Customer("Spiderman");
        customer.setFirstName("Jack");
        customer.setLastName("Daniels");
        customer.setHasAcceptedTerms(true);
        customer.setStreet("Alchoholics");
        customer.setPhoneNumber("9234515890");
        customer.setCity("London");
        customer.setCountry("England");
        customer.setEmail("jackd@afse.eu");
        customer.setBirthDate(new Date());
        customer.setZipCode("3216");
        addCustomer(customer);

        customer = new Customer("AnnaKendrick");
        customer.setFirstName("Anna");
        customer.setLastName("Kendrick");
        customer.setHasAcceptedTerms(true);
        customer.setStreet("LA Street");
        customer.setPhoneNumber("7654515890");
        customer.setCity("Los Angeles");
        customer.setCountry("USA");
        customer.setEmail("annakendrick@afse.eu");
        customer.setBirthDate(new Date());
        customer.setZipCode("7654");
        addCustomer(customer);

        customer = new Customer("LillianSpringer");
        customer.setFirstName("Lillian");
        customer.setLastName("Springer");
        customer.setHasAcceptedTerms(true);
        customer.setStreet("Springer's");
        customer.setPhoneNumber("7984515890");
        customer.setCity("London");
        customer.setCountry("England");
        customer.setEmail("lillian.springer@gmail.com");
        customer.setBirthDate(new Date());
        customer.setZipCode("7384");
        addCustomer(customer);

        customer = new Customer("JacobJohnston");
        customer.setFirstName("Jacob");
        customer.setLastName("Johnston");
        customer.setHasAcceptedTerms(true);
        customer.setStreet("Springer's");
        customer.setPhoneNumber("7984515890");
        customer.setCity("Berlin");
        customer.setCountry("Germany");
        customer.setEmail("jacob.johnston@gmail.com");
        customer.setBirthDate(new Date());
        customer.setZipCode("7384");
        addCustomer(customer);

        customer = new Customer("AnthonyParr");
        customer.setFirstName("Anthony");
        customer.setLastName("Parr");
        customer.setHasAcceptedTerms(true);
        customer.setStreet("Springer's");
        customer.setPhoneNumber("7984515890");
        customer.setCity("Salvador");
        customer.setCountry("Brazil");
        customer.setEmail("anthony.parr@gmail.com");
        customer.setBirthDate(new Date());
        customer.setZipCode("7384");
        addCustomer(customer);

    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void deleteCustomer(Long id) {
        customers.remove(new Customer(id));
    }

    public boolean containsCustomer(Customer customer) {
        return customers.contains(customer);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        maxID++;
        customer.setId(maxID);
    }

    public Customer getCustomer(Customer customer) throws CustomerNotFoundException {
        int result = customers.indexOf(customer);


        if (result == -1) {
            throw new CustomerNotFoundException();
        }

        return customers.get(result);
    }

    public Customer getCustomerByUsername(String username) throws CustomerNotFoundException {

        for (Customer customer : customers) {
            if (customer.getUserName().equals(username)) {
                return customer;
            }
        }

        throw new CustomerNotFoundException();
    }

}
