package smartHouse.objectClasses;

import javax.xml.bind.annotation.*;
import java.net.URI;

/**
 * Created by Patrik Glendell on 02/10/15.
 *
 *Implemented by David Munro & Juraj Orszag
 */
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    @XmlAttribute
    private int id;
    @XmlElement
    private String userName;
    @XmlElement(name = "email")
    private String email;
    @XmlEnumValue("profile")
    private AdminRole profile;
    @XmlElement
    private String password;

    public User() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }
    public AdminRole getProfile() {
        return profile;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setProfile(AdminRole profile) {
        this.profile = profile;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
