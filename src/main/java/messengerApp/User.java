package messengerApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class User {

    private List<String> messages = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private String username;
    private String lastname;
    private String firstname;
    private String id;
    private String password;

    public User(String firstname, String lastname, String id, String username, String password) {
        setFirstname(firstname);
        setLastname(lastname);
        setUsername(username);
        setId(id);
        setPassword(password);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String msg) {
        messages.add(msg);
    }

    @Override
    public String toString() {
        return "User{name='" + firstname + "', id='" + id + "'}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        User user = (User) object;
        return username.equals(user.getUsername());
    }

    @Override
    public int hashCode(){
        return Objects.hash(username);
    }
    public void setFirstname(String firstname) {
        if (firstname != null) {
            this.firstname = firstname;
        } else {
            setFirstname(scanner.next());
        }
    }

    public String getName() {
        return firstname;
    }

    public void setId(String id) {
        if (id != null) {
            this.id = firstname;
        } else {
            setId(scanner.next());
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null) {
            this.username = firstname;
        } else {
            setUsername(scanner.next());
        }
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (lastname != null) {
            this.lastname = firstname;
        } else {
            setLastname(scanner.next());
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = firstname;
        } else {
            setPassword(scanner.next());
        }
    }
}
