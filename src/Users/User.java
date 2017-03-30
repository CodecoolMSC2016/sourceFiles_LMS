package Users;

/**
 * Created by Mudzso on 2017.03.29..
 */
public abstract class User {

     protected String name;
     protected String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
