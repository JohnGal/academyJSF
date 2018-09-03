package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("zipCodeValidator")
public class ZipCodeValidator extends AbstractValidator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String zipCode = o.toString();
        if (zipCode == null || zipCode.length() < 2 || zipCode.length() > 10) {
            FacesMessage msg =
                    new FacesMessage(getLocalizedMessage("customer.errorInvalidZipCode"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

