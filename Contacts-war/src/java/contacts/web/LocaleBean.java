package contacts.web;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@SessionScoped
@Named
public class LocaleBean implements Serializable {

    private static final long serialVersionUID = 516756595421760915L;

    private Locale userLocale;

    public Locale getUserLocale() {
        if (userLocale == null) {
            userLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
        if (userLocale == null) {
            userLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
        }
        return userLocale;
    }

    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }

    public void selectEnglish() {
        userLocale = Locale.ENGLISH;
    }

    public void selectGerman() {
        userLocale = Locale.GERMAN;
    }

    public Date getCurrentDate() {
        return new Date();
    }

}
