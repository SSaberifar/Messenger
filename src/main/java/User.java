public class User {

    private String name;
    private String id;

    public User(String name , String id){
        setId(id);
        setName(name);
    }

    public void setName(String name){
        this.name = name;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
    public String getId(){
        return this.id;
    }
}
