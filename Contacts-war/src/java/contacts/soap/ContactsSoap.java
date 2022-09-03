package contacts.soap;

import contacts.dto.Contact;
import contacts.logic.ContactsLogic;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@WebService(name = "ws", serviceName = "Contacts")
public class ContactsSoap {

    @EJB
    private ContactsLogic cl;

    @WebMethod
    public Contact createContact(
            @WebParam(name = "name") String name,
            @WebParam(name = "phone") String phone) {
        return cl.createContact(name, phone);
    }

    @WebMethod
    public List<Contact> getContactList() {
        return cl.getContactList();
    }

    @WebMethod
    public Contact getContact(@WebParam(name = "uuid") String uuid) {
        return cl.getContact(uuid);
    }

    @WebMethod
    public List<Contact> search(@WebParam(name = "name") String name) {
        return cl.search(name);
    }

    @WebMethod
    public boolean deleteContact(@WebParam(name = "uuid") String uuid) {
        return cl.deleteContact(uuid);
    }
}
