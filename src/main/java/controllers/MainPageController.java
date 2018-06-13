package controllers;

import models.Customer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class MainPageController implements Serializable {
    private List<Customer> customers = new ArrayList<Customer>();

}
