/*
 * Backing Bean for the edit_contact page.
 */
package contacts.web;

import contacts.dto.Contact;
import contacts.logic.ContactsLogic;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@ViewScoped
@Named
public class ContactEditBean implements Serializable {

    private static final long serialVersionUID = -8516655326049733719L;

    @EJB
    private ContactsLogic cl;

    private boolean success;
    private boolean failure;
    private String failureMessage;

    private String uuid;

    private Contact contact;

    /**
     * @return true if a contact with the specified uuid could be loaded
     */
    public boolean isValid() {
        return uuid != null && getContact() != null;
    }

    /**
     * @return true if the contact could be stored successfully
     */
    public boolean isSuccess() {
        return success && !FacesContext.getCurrentInstance().isValidationFailed();
    }

    /**
     * @return true if the contact could not be stored
     */
    public boolean isFailure() {
        return failure && !FacesContext.getCurrentInstance().isValidationFailed();
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * lazily loads the Contact with the specified UUID
     *
     * @return the Contact DTO
     */
    public Contact getContact() {
        if (contact == null) {
            if ("new".equals(uuid)) {
                contact = new Contact();
            } else {
                contact = cl.getContact(uuid);
            }
        }
        return contact;
    }

    /**
     * Stores the (edited) Contact
     */
    public void storeContact() {
        try {
            uuid = cl.storeContact(contact);
            if (uuid != null) {
                success = true;
                failure = false;
                // force reload since the JPA version might have changed
                contact = null;
            }
        } catch (EJBException e) {
            success = false;
            failure = true;
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            failureMessage = t.getMessage();
        }
    }

    /**
     * Deletes the Contact
     */
    public void deleteContact() {
        cl.deleteContact(uuid);
    }
}
