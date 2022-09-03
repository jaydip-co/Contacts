package contacts.web;

import contacts.dto.User;
import contacts.logic.ContactsLogic;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@SessionScoped
@Named
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 7755587737462676166L;

    private static final Logger LOG = Logger.getLogger(LoginBean.class.getName());

    private User currentUser;
    
    private String role;

    @EJB
    private ContactsLogic cl;

    @PostConstruct
    public void newSession() {
        LOG.info("Contacts: NEW SESSION");
    }

    public boolean isLoggedIn() {
        return getUser() != null;
    }

    public boolean isStaff() {
        if (!isLoggedIn()) {
            return false;
        }
        return FacesContext.getCurrentInstance()
                .getExternalContext().isUserInRole("STAFF");
    }
    
    public String getRole(){
        if(!isLoggedIn()){
        return "not Logged In";
        }
        return String.valueOf(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("STUDENT"));
    }

    private Principal oldPrincipal = null; // used to detect changed login

    public User getUser() {
        Principal p = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        if (p == null) {
            currentUser = null;
        } else {
            if (!p.equals(oldPrincipal)) {
                LOG.log(Level.INFO, "Contacts: LOGIN user {0}", p.getName());
                currentUser = cl.getCurrentUser();
            }
        }
        oldPrincipal = p;
        return currentUser;
    }

    public void invalidateSession() {
        LOG.log(Level.INFO, "invalidateSession()");
        Principal p = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        if (p != null) {
            LOG.log(Level.INFO, "Contacts: LOGOUT user {0}", p.getName());
        }
        currentUser = null;
        oldPrincipal = null;
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
    }

    public void logout() {
        invalidateSession();
        FacesContext.getCurrentInstance()
                .responseComplete();
    }
}
