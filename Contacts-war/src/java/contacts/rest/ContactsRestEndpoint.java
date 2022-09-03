package contacts.rest;

import contacts.dto.Contact;
import contacts.logic.ContactsLogic;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@LocalBean
@Path("contacts")
public class ContactsRestEndpoint {

    @EJB
    private ContactsLogic cl;

    @GET
    @Path("new/{name}/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    public Contact createContact(
            @PathParam("name") String name,
            @PathParam("phone") String phone) {
        return cl.createContact(name, phone);
    }

    @Path("list")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contact> getContactList() {
        return cl.getContactList();
    }

    @Path("contact/{uuid}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Contact getContactByUuid(@PathParam("uuid") String uuid) {
        Contact c = cl.getContact(uuid);
        if (c == null) {
            throw new ContactsRestException("no Contact with uuid " + uuid, Response.Status.NOT_FOUND);
        }
        return c;
    }

    @Path("contact/{uuid}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public void deleteContact(@PathParam("uuid") String uuid) {
        cl.deleteContact(uuid);
    }

    @Path("search/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contact> search(@PathParam("name") String name) {
        return cl.search(name);
    }
}
