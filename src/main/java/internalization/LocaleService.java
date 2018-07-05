package internalization;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@ManagedBean(eager = true)
@SessionScoped
public class LocaleService implements Serializable {

    private static final long serialVersionUID = -7282752179545899988L;

    private String locale;

    private static Map<String, Object> countries;

    static {
        countries = new LinkedHashMap<>();
        countries.put("English", Locale.ENGLISH);
        countries.put("Spanish", Locale.forLanguageTag("es"));
        FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.forLanguageTag("es"));
    }

    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale().toLanguageTag();
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {

        return locale;
    }


    public Map<String, Object> getCountries() {
        return countries;
    }

    public void localChanged(ValueChangeEvent event) {
        String newLocaleValue = event.getNewValue().toString();

        for (Map.Entry<String, Object> entry : countries.entrySet()) {
            if (entry.getValue().toString().equals(newLocaleValue)) {
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
            }
        }
    }
}
