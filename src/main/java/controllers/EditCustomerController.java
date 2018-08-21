package controllers;

import dummyData.CountryData;
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

    @ManagedProperty(value = "#{customersService}")
    private CustomersService customersService;

    public void setCustomersService(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostConstruct
    private void init() {

        data = (new CountryData()).getCountryData();
        countries = new TreeSet<>(data.keySet());

        getIdFromFlash();

    }

    private void getIdFromFlash() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        setCustomerID((Long) flash.get("customerID"));
    }

    private void initCustomer(Long customerID) {
        if (customerID == null) {
            customer = new Customer();
            return;
        }

        try {
            customer = customersService.getCustomerById(customerID);

        } catch (CustomerNotFoundException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                externalContext.dispatch("customers.xhtml");
            } catch (IOException e1) {
            }
        }

        onCountryChange();
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
            customer.setCountry(country);
        }
    }

    public void setCity(String city) {
        if (cities.contains(city)) {
            customer.setCity(city);
        }
    }

    public String getCountry() {
        return customer.getCountry();
    }

    public String getCity() {
        return customer.getCity();
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

        if (validateCustomer(customer)) {
            if (customersService.containsCustomer(customer)) {
                customersService.updateCustomer(customer);
                context.addMessage(null, new FacesMessage(getLocalizedMessage("successful"), getLocalizedMessage("customerSuccessEdit")));
            } else {
                customerID = customersService.addCustomer(customer);
                context.addMessage(null, new FacesMessage(getLocalizedMessage("successful"), getLocalizedMessage("customerSuccessCreate")));
                customer.setId(customerID);
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
        return customer.getFirstName();
    }

    public void setFirstName(String name) {
        if (validateName(name)) {
            customer.setFirstName(name);
        }
    }

    public String getLastName() {
        return customer.getLastName();
    }

    public void setLastName(String name) {
        if (validateName(name)) {
            customer.setLastName(name);
        }
    }

    public String getUserName() {
        return customer.getUserName();
    }

    public void setUserName(String name) {
        if (validateUsername(name)) {
            customer.setUserName(name);
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
        return customer.getBirthDate();
    }

    public void setBirthDate(Date birthDate) {
        if ((new Date()).after(birthDate)) {
            customer.setBirthDate(birthDate);
        }
    }

    public String getEmail() {
        return customer.getEmail();
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
            customer.setEmail(email);
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
        return customer.getZipCode();
    }

    public void setZipCode(String zipCode) {
        if (validateZipCode(zipCode)) {
            customer.setZipCode(zipCode);
        }
    }

    public boolean getHasAcceptedTerms() {
        return customer.getHasAcceptedTerms();
    }

    public void setHasAcceptedTerms(boolean hasAcceptedTerms) {
        customer.setHasAcceptedTerms(hasAcceptedTerms);
    }


    public String getPhoneNumber() {
        return customer.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() == 10 && phoneNumber.matches("^[0-9]*$")) {
            customer.setPhoneNumber(phoneNumber);
        }
    }

    public String getStreet() {
        return customer.getStreet();
    }

    public void setStreet(String street) {
        if (street != null && street.length() <= 30) {
            customer.setStreet(street);
        }
    }

    private String getLocalizedMessage(String messageName) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle myResources = ResourceBundle.getBundle("com.jsfProject.customers", locale);
        String message = myResources.getString(messageName);

        return message;
    }
}
