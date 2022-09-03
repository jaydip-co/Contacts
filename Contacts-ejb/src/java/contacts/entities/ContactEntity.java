package contacts.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@NamedQueries({
    @NamedQuery(
            name = "getContactList",
            query = "SELECT c FROM ContactEntity c"
            + " WHERE c.owner = :owner"
            + " ORDER BY c.name"
    ),
    @NamedQuery(
            name = "getContactByUuid",
            query = "SELECT c FROM ContactEntity c"
            + " WHERE c.owner = :owner AND c.uuid = :uuid"
    ),
    @NamedQuery(
            name = "deleteContactByUuid",
            query = "DELETE FROM ContactEntity c WHERE c.uuid = :uuid"
    )
})
@Entity
public class ContactEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 4528824764886721927L;

    private String name;

    private String phone;

    @ManyToOne
    private UserEntity owner;

    public ContactEntity() {
        this(false);
    }

    public ContactEntity(boolean newEntity) {
        super(newEntity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

}
