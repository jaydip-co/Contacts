package contacts.rest;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author riediger
 */
@ApplicationException
public class ContactsRestException extends WebApplicationException {

    private static final long serialVersionUID = 6390905608085584603L;

    public ContactsRestException(String message, Response.Status status) {
        super(message, status);
    }
}
