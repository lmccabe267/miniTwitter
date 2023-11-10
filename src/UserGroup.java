import java.util.ArrayList;
import java.util.List;


public class UserGroup {
    private String id;
    private List<User> users = new ArrayList<>();
    private List<UserGroup> subgroups = new ArrayList<>();

    public UserGroup(String id) {
        this.id = id;
    }

    public void addUser(User user) {
    	user.setGroup(id);
        users.add(user);
    }

    public void addSubgroup(UserGroup subgroup) {
        subgroups.add(subgroup);
    }

    public String getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<UserGroup> getSubgroups() {
        return subgroups;
    }

    @Override
    public String toString() {
        return id;
    }
}