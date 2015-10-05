package smartHouse.objectClasses;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
public class User {
    private int id;
    private String userName;
    private java.net.URI email;
    private AdminRole profile;

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
