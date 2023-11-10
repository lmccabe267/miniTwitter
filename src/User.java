

public class User {
    private String id;
    private String groupId;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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