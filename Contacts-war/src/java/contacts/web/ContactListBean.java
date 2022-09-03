/*
 * Backing Bean for the contact list page (homepage)
 */
package contacts.web;

import contacts.dto.Contact;
import contacts.logic.ContactsLogic;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named
public class ContactListBean {

    @EJB
    private ContactsLogic cl;

    private List<Contact> contactList;

    public List<Contact> getContactList() {
        if (contactList == null) {
            contactList = cl.getContactList();
        }
        return contactList;
    }

    public void mailContacts() {
        cl.sendContactsByMail();
    }

    public void createTestData() {
        cl.createContact("Anne", "2710");
        cl.createContact("Volker", "2706");
        cl.createContact("Silke", "2720");
        cl.createContact("Jan", "2722");
        cl.createContact("Katharina", "2708");
        cl.createContact("Sven", "2796");
        // force reload
        contactList = null;
    }
}
