package services;

import dummyData.CustomerData;
import exceptions.CustomerNotFoundException;
import models.Customer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;

@Stateless
//@ManagedBean
//@ApplicationScoped
public class CustomersService implements Serializable {

    private static final long serialVersionUID = -1028825087637725483L;

    private static List<Customer> customers;
    private static Long maxID = 0L;

    @PostConstruct
    public void init() {
        if (customers == null) {
            CustomerData customerData = new CustomerData();
            customers = customerData.getCustomerData();
            maxID = customerData.getMaxID();
        }
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

    public Long addCustomer(Customer customer) {
        maxID++;
        customer.setId(maxID);
        customers.add(customer);
        return maxID;
    }

    public Customer getCustomerById(Long id) throws CustomerNotFoundException {

        int result = customers.indexOf(new Customer(id));

        if (result == -1) {
            throw new CustomerNotFoundException();
        }

        return new Customer(customers.get(result));
    }

    public Customer getCustomerByUsername(String username) throws CustomerNotFoundException {

        for (Customer customer : customers) {
            if (customer.getUserName().equals(username)) {
                return new Customer(customer);
            }
        }

        throw new CustomerNotFoundException();
    }

    public void updateCustomer(Customer customer) {
        customers.remove(customer);
        customers.add(customer);
    }
}
