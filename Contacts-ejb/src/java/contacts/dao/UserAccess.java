package contacts.dao;

import contacts.entities.UserEntity;
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
public class UserAccess {

    @PersistenceContext(unitName = "Contacts-ejbPU")
    private EntityManager em;

    public UserEntity getUser(String username) {
        UserEntity ue;
        try {
            ue = em.createNamedQuery("getUserByUserName", UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            ue = new UserEntity(true);
            ue.setUsername(username);
            em.persist(ue);
        }
        return ue;
    }
}
