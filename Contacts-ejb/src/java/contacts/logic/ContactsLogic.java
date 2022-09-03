package contacts.logic;

import contacts.dto.Contact;
import contacts.dto.User;
import java.util.List;
import javax.ejb.Remote;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Remote
public interface ContactsLogic {

    public static final String USER_ROLE = "USER";

    /**
     * Creates a new Contact with the specified name and phone.
     *
     * @param name
     * @param phone
     * @return the new Contact
     */
    public Contact createContact(String name, String phone);

    /**
     * @return a list of all Contacts, ordered by name
     */
    public List<Contact> getContactList();

    /**
     * Retrieves a list of Contacts matching the specified name.
     *
     * @param name (part of) the name to search for
     * @return the matching contacts
     */
    public List<Contact> search(String name);

    /**
     * Tries to load the Contact with the specified UUID.
     *
     * @param uuid the UUID of the Contact
     * @return the contact, or null if no matching UUID was found
     */
    public Contact getContact(String uuid);

    /**
     * Stores an edited, or creates a new Contact. New Contacts initially have
     * uuid==null, the UUID or the new persistent Contact is returned.
     *
     * @param contact a DTO containing possibly edited data
     * @return the UUID of the stored Contact, or null otherwise (e.g. the
     * Contact was deleted or modified meanwhile)
     */
    public String storeContact(Contact contact);

    /**
     * Deletes the Contact with the specified UUID.
     *
     * @param uuid the UUID of the Contact
     * @return true if the Contact could be deleted, false otherwise (e.g. it
     * was already deleted before, or the UUID couldn't be found)
     */
    public boolean deleteContact(String uuid);

    /**
     * Returns the current user determined from caller principal and creates a
     * database entry if required.
     *
     * @return the user, or null if nobody is logged in
     */
    public User getCurrentUser();

    /**
     * Sends contact information of the current user by e-mail.
     */
    public void sendContactsByMail();
}
