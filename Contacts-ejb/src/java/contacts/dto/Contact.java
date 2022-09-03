package contacts.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@XmlRootElement
public class Contact extends AbstractDTO {

    private static final long serialVersionUID = -4848962003160569842L;

    private String name;
    private String phone;

    public Contact() {
    }

    public Contact(String uuid, int jpaVersion, String name, String phone) {
        super(uuid, jpaVersion);
        this.name = name;
        this.phone = phone;
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

}
