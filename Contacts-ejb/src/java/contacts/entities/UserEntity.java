package contacts.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@NamedQueries({
    @NamedQuery(
            name = "getUserByUserName",
            query = "SELECT u FROM UserEntity u WHERE u.username = :username"
    )
})
@Entity
public class UserEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 303108301210761000L;

    @Column(unique = true, length=150)
    private String username;

    private String firstname;

    private String lastname;

    @OneToMany(mappedBy = "owner")
    private Set<ContactEntity> contacts;

    public UserEntity() {
        this(false);
    }

    public UserEntity(boolean newEntity) {
        super(newEntity);
        if (newEntity) {
            contacts = new HashSet<>();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<ContactEntity> getContacts() {
        return contacts;
    }
}
