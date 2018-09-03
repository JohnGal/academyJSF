package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@FacesValidator("streetValidator")
public class StreetValidator extends AbstractValidator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String name = o.toString();
        Pattern pattern = Pattern.compile("^[A-Za-z0-9 '.]*$");
        if(!pattern.matcher(name).matches()){
            FacesMessage msg =
                    new FacesMessage(getLocalizedMessage("customer.errorInvalidStreetName"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        if(name.length() > 20 || name.length() < 2){
            FacesMessage msg =
                    new FacesMessage(getLocalizedMessage("customer.errorTooLongStreetName"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
