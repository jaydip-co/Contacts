package contacts.dao;

import contacts.entities.ContactEntity;
import contacts.entities.UserEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@LocalBean
@Stateless
public class ContactAccess {

    @PersistenceContext(unitName = "Contacts-ejbPU")
    private EntityManager em;

    public ContactEntity createContact(UserEntity owner, String name, String phone) {
        ContactEntity c = new ContactEntity(true);
        c.setName(name);
        c.setPhone(phone);
        c.setOwner(owner);
        owner.getContacts().add(c);
        em.persist(c);
        return c;
    }

    public List<ContactEntity> getContactList(UserEntity owner) {
        return em.createNamedQuery("getContactList")
                .setParameter("owner", owner)
                .getResultList();
    }

    public ContactEntity getContactByUuid(UserEntity owner, String uuid) {
        try {
            ContactEntity ce = em.createNamedQuery("getContactByUuid", ContactEntity.class)
                    .setParameter("owner", owner)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
            return ce;
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean deleteContact(UserEntity owner, String uuid) {
        ContactEntity ce = getContactByUuid(owner, uuid);
        if (ce == null) {
            return false;
        }
        ce.getOwner().getContacts().remove(ce);
        ce.setOwner(null);
        em.remove(ce);
        return true;
    }
}
