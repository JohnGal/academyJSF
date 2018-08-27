package validators;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import java.util.Locale;
import java.util.ResourceBundle;


public abstract class AbstractValidator implements Validator {

    protected String getLocalizedMessage(String messageName) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle myResources = ResourceBundle.getBundle("com.jsfProject.customers", locale);
        String message = myResources.getString(messageName);

        return message;
    }
}
