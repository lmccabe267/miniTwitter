

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String groupId;

    public User(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    @Override
    public String toString() {
    	return this.id + "";
    }
    
    public void setGroup(String groupId) {
    	this.groupId = groupId;
    }
    
    public String getGroupId() {
    	return groupId;
    }
}