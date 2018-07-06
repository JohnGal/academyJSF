package controllers;

import exceptions.CustomerNotFoundException;
import models.Customer;
import services.CustomersService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

@ManagedBean
@ViewScoped
public class EditCustomerController implements Serializable {
    private static final long serialVersionUID = 3369499013929317972L;
    private Set<String> countries;
    private Set<String> cities;
    private Map<String, Set<String>> data = new HashMap<>();

    private Long customerID;

    private Customer customer;
    private Customer tempCustomer = new Customer();

    @ManagedProperty(value = "#{customersService}")
    private CustomersService customersService;

    public void setCustomersService(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostConstruct
    private void init() {

        countries = new TreeSet<>();
        countries.add("USA");
        countries.add("Germany");
        countries.add("Brazil");
        countries.add("Greece");
        countries.add("England");

        Set<String> set = new TreeSet<>();
        set.add("New York");
        set.add("San Fransisco");
        set.add("Denver");
        set.add("Los Angeles");
        data.put("USA", set);

        set = new TreeSet<>();
        set.add("Berlin");
        set.add("Munich");
        set.add("Frankfurt");
        data.put("Germany", set);

        set = new TreeSet<>();
        set.add("Sao Paolo");
        set.add("Rio de Janerio");
        set.add("Salvador");
        data.put("Brazil", set);

        set = new TreeSet<>();
        set.add("Athens");
        set.add("Volos");
        set.add("Kavala");
        data.put("Greece", set);

        set = new TreeSet<>();
        set.add("London");
        set.add("Bristol");
        set.add("Chester");
        data.put("England", set);

        pullIdFromFlash();

    }

    private void pullIdFromFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        setCustomerID((Long) flash.get("customerID"));
    }

    private void initCustomer(Long customerID) {
        if (customerID == null) {
            return;
        }

        try {
            customer = customersService.getCustomer(new Customer(customerID));
            copyCustomerData(customer, tempCustomer);

        } catch (CustomerNotFoundException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                externalContext.dispatch("customers.xhtml");
            } catch (IOException e1) {
            }
            facesContext.responseComplete();
        }

        onCountryChange();
    }

    private void copyCustomerData(Customer src, Customer dest) {
        dest.setFirstName(src.getFirstName());
        dest.setLastName(src.getLastName());
        dest.setUserName(src.getUserName());
        dest.setBirthDate(src.getBirthDate());
        dest.setCountry(src.getCountry());
        dest.setCity(src.getCity());
        dest.setStreet(src.getStreet());
        dest.setZipCode(src.getZipCode());
        dest.setPhoneNumber(src.getPhoneNumber());
        dest.setEmail(src.getEmail());
        dest.setId(src.getId());
        dest.setHasAcceptedTerms(src.getHasAcceptedTerms());
    }

    public Long getCustomerID() {
        return customerID;
    }

    private void setCustomerID(Long customerID) {
        this.customerID = customerID;
        initCustomer(customerID);
    }


    public void setCountry(String country) {
        if (countries.contains(country)) {
            tempCustomer.setCountry(country);
        }
    }

    public void setCity(String city) {
        if (cities.contains(city)) {
            tempCustomer.setCity(city);
        }
    }

    public String getCountry() {
        return tempCustomer.getCountry();
    }

    public String getCity() {
        return tempCustomer.getCity();
    }

    public Set<String> getCountries() {
        return countries;
    }

    public Set<String> getCities() {
        return cities;
    }

    public void onCountryChange() {

        if (getCountry() != null && !" ".equals(getCountry())) {
            cities = data.get(getCountry());
        } else {
            cities = new HashSet<>();
        }
    }

    public String register() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (validateCustomer(tempCustomer)) {
            if (customersService.containsCustomer(customer)) {
                copyCustomerData(tempCustomer, customer);
                context.addMessage(null, new FacesMessage(getLocalizedMessage("successful"), getLocalizedMessage("customerSuccessEdit")));
            } else {
                customer = new Customer();
                copyCustomerData(tempCustomer, customer);
                customersService.addCustomer(customer);
                context.addMessage(null, new FacesMessage(getLocalizedMessage("successful"), getLocalizedMessage("customerSuccessCreate")));
                customerID = customer.getId();
            }

        } else {
            context.addMessage(null, new FacesMessage(getLocalizedMessage("error"), getLocalizedMessage("customerWrongDetails")));
        }

        return "edit.xhtml";
    }

    private boolean validateCustomer(Customer customer) {
        boolean result = customer.getUserName() != null
                && validateUsername(customer.getUserName())
                && customer.getLastName() != null
                && customer.getFirstName() != null
                && customer.getBirthDate() != null
                && customer.getCountry() != null
                && customer.getCity() != null
                && customer.getStreet() != null
                && customer.getZipCode() != null
                && customer.getPhoneNumber() != null
                && customer.getEmail() != null
                && customer.getHasAcceptedTerms();

        return result;
    }

    private boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^[A-Z][a-z]*$");
        return pattern.matcher(name).matches() && name.length() <= 20;
    }

    public String getFirstName() {
        return tempCustomer.getFirstName();
    }

    public void setFirstName(String name) {
        if (validateName(name)) {
            tempCustomer.setFirstName(name);
        }
    }

    public String getLastName() {
        return tempCustomer.getLastName();
    }

    public void setLastName(String name) {
        if (validateName(name)) {
            tempCustomer.setLastName(name);
        }
    }

    public String getUserName() {
        return tempCustomer.getUserName();
    }

    public void setUserName(String name) {
        if (validateUsername(name)) {
            tempCustomer.setUserName(name);
        }
    }

    public void validateUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String name = (String) value;
        if (name == null || "".equals(name)) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "invalid username", getLocalizedMessage("customer.errorEmptyUserName")));
        }

        if (name.length() > 20 || name.length() < 2) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid username", getLocalizedMessage("customer.errorTooLongUserName")));
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9]*$");
        if (!pattern.matcher(name).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid username", getLocalizedMessage("customer.errorInvalidCharactersUserName")));
        }

        try {
            Customer customer = customersService.getCustomerByUsername(name);
            if (!customer.getId().equals(customerID)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid username", getLocalizedMessage("customer.errorUserNameExists")));
            }
        } catch (CustomerNotFoundException e) {
        }


    }

    private boolean validateUsername(String name) {
        if (name == null || "".equals(name)) {
            return false;
        }

        if (name.length() > 20) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9]*$");
        if (!pattern.matcher(name).matches()) {
            return false;
        }

        try {
            Customer customer = customersService.getCustomerByUsername(name);
            if (!customer.getId().equals(customerID)) {
                return false;
            }
        } catch (CustomerNotFoundException e) {
        }

        return true;
    }

    public Date getBirthDate() {
        return tempCustomer.getBirthDate();
    }

    public void setBirthDate(Date birthDate) {
        if ((new Date()).after(birthDate)) {
            tempCustomer.setBirthDate(birthDate);
        }
    }

    public String getEmail() {
        return tempCustomer.getEmail();
    }

    private boolean validateEmail(String email) {
        boolean valid = false;

        if (email == null) {
            return valid;
        }

        Pattern pattern = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        valid = pattern.matcher(email).matches();

        return valid;
    }

    public void setEmail(String email) {
        if (email != null && validateEmail(email)) {
            tempCustomer.setEmail(email);
        }
    }

    private boolean validateZipCode(String zipcode) {
        boolean validation = false;
        if (zipcode != null && zipcode.length() < 10) {
            validation = true;
        }

        return validation;
    }

    public String getZipCode() {
        return tempCustomer.getZipCode();
    }

    public void setZipCode(String zipCode) {
        if (validateZipCode(zipCode)) {
            tempCustomer.setZipCode(zipCode);
        }
    }

    public boolean getHasAcceptedTerms() {
        return tempCustomer.getHasAcceptedTerms();
    }

    public void setHasAcceptedTerms(boolean hasAcceptedTerms) {
        tempCustomer.setHasAcceptedTerms(hasAcceptedTerms);
    }


    public String getPhoneNumber() {
        return tempCustomer.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() == 10 && phoneNumber.matches("^[0-9]*$")) {
            tempCustomer.setPhoneNumber(phoneNumber);
        }
    }

    public String getStreet() {
        return tempCustomer.getStreet();
    }

    public void setStreet(String street) {
        if (street != null && street.length() <= 30) {
            tempCustomer.setStreet(street);
        }
    }

    private String getLocalizedMessage(String messageName) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle myResources = ResourceBundle.getBundle("com.jsfProject.customers", locale);
        String message = myResources.getString(messageName);

        return message;
    }
}
