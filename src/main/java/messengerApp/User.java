package messengerApp;

import java.util.Scanner;

public class User {

    Scanner scanner = new Scanner(System.in);
    private String username;
    private String lastname;
    private String firstname;
    private String id;
    private String password;

    public User(String firstname, String id) {
        setFirstname(firstname);
//        setLastname(firstname);
//        setUsername(username);
        setId(id);
//        setPassword(password);
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

    @Override
    public String toString() {
        return "User{name='" + firstname + "', id='" + id + "'}";
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
        if (password != null){
            this.password = firstname;
        }else {
            setPassword(scanner.next());
        }
    }
}
