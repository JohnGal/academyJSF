package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@FacesValidator("phoneValidator")
public class PhoneValidator extends AbstractValidator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String name = o.toString();

        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        if(!pattern.matcher(name).matches() && name.length() <= 20){
            FacesMessage msg =
                    new FacesMessage(getLocalizedMessage("customer.errorInvalidPhoneNumber"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
