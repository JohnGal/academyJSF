package controllers;

import models.Customer;
import org.apache.log4j.Logger;
import services.CustomersService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@ManagedBean
@ViewScoped
public class CustomersController implements Serializable {
    private static final long serialVersionUID = -5244368569743288298L;


    @Inject
    private Logger logger;

    @PostConstruct
    private void postConstruct() {
        logger.info("post construct initiated");
    }

    @PreDestroy
    private void preDestroy() {
        logger.info("pre destroy initiated");
    }

//    @ManagedProperty(value = "#{customersService}")
    @EJB
    private CustomersService customersService;

    public void setCustomersService(CustomersService customersService) {
        this.customersService = customersService;
    }

    public List<Customer> getCustomers() {
        return customersService.getCustomers();
    }

    public void deleteCustomer(Long id) {
        customersService.deleteCustomer(id);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(getLocalizedMessage("successful"), getLocalizedMessage("deleteSuccess")));
    }

    private String getLocalizedMessage(String messageName) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle myResources = ResourceBundle.getBundle("com.jsfProject.customers", locale);
        String message = myResources.getString(messageName);

        return message;
    }

    public String goToEditCustomerView(Long id) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("customerID", id);

        return "edit.xhtml?faces-redirect=true";
    }
}
