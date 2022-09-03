package contacts.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */

public class User extends AbstractDTO {

    private static final long serialVersionUID = 282980014285470000L;

    private final String username; // read only

    private String firstname;

    private String lastname;

    public User(String uuid, int jpaVersion, String username, String firstname, String lastname) {
        super(uuid, jpaVersion);
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
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
}
