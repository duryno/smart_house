package smartHouse.objectClasses;

import javax.xml.bind.annotation.*;
import java.net.URI;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    @XmlAttribute
    private int id;
    @XmlElement
    private String userName;
    @XmlSchemaType(name = "email")
    private java.net.URI email;
    @XmlEnumValue("profile")
    private AdminRole profile;

    public User(String userName, URI email, AdminRole profile) {
        this.userName = userName;
        this.email = email;
        this.profile = profile;
    }
    public User() {
    }


    protected int getId() {
        return id;
    }
    protected void setId(int id) {
        this.id = id;
    }
    protected String getUserName() {
        return userName;
    }
    protected java.net.URI getEmail() {
        return email;
    }
    protected AdminRole getProfile() {
        return profile;
    }
}
