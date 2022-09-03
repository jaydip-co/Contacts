package contacts.logic.impl;

import contacts.dao.ContactAccess;
import contacts.dao.UserAccess;
import contacts.dto.Contact;
import contacts.dto.User;
import contacts.entities.ContactEntity;
import contacts.entities.UserEntity;
import contacts.logic.ContactsLogic;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
public class ContactsLogicImpl implements ContactsLogic {

    @EJB
    private ContactAccess ca;

    @EJB
    private UserAccess ua;

    @Resource(lookup = "mail/contacts-mail")
    private Session mailSession;

    @Resource
    private EJBContext ejbContext;

    private static final Logger LOG = Logger.getLogger(ContactsLogicImpl.class.getName());

    private UserEntity caller;

    @AroundInvoke
    private Object getCaller(InvocationContext ctx) throws Exception {
        Principal p = ejbContext.getCallerPrincipal();
        if (p != null) {
            caller = ua.getUser(p.getName());
        }
        return ctx.proceed();
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public Contact createContact(String name, String phone) {
        return createDTO(ca.createContact(caller, name, phone));
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public List<Contact> getContactList() {
        return ca.getContactList(caller)
                .stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public List<Contact> search(String name) {
        // TODO add implementation
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public Contact getContact(String uuid) {
        ContactEntity ce = ca.getContactByUuid(caller, uuid);
        return ce == null ? null : createDTO(ce);
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public String storeContact(Contact contact) {
        if (contact.isNew()) {
            return ca.createContact(caller, contact.getName(), contact.getPhone()).getUuid();
        }
        ContactEntity ce = ca.getContactByUuid(caller, contact.getUuid());
        if (ce == null) {
            return null;
        }
        ce.setJpaVersion(contact.getJpaVersion());
        ce.setName(contact.getName());
        ce.setPhone(contact.getPhone());
        return ce.getUuid();
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public boolean deleteContact(String uuid) {
        return ca.deleteContact(caller, uuid);
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public User getCurrentUser() {
        return createDTO(caller);
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public void sendContactsByMail() {
        List<Contact> l = getContactList();
        Message msg = new MimeMessage(mailSession);
        try {
            msg.setSubject("Your contacts");
            msg.setSentDate(new Date());
            msg.setFrom(InternetAddress.parse(
                    "nobody@acme.edu",
                    false)[0]);
            msg.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(
                            caller.getUsername(),
                            false)
            );
            StringBuilder sb = new StringBuilder();
            for (Contact c : l) {
                sb.append(c.getName()).append("\t").append(c.getPhone()).append("\n");
            }
            msg.setText(sb.toString());
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(ContactsLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Contact createDTO(ContactEntity ce) {
        return new Contact(ce.getUuid(), ce.getJpaVersion(), ce.getName(), ce.getPhone());
    }

    private User createDTO(UserEntity ue) {
        return new User(ue.getUuid(), ue.getJpaVersion(), ue.getUsername(), ue.getFirstname(), ue.getLastname());
    }
}
