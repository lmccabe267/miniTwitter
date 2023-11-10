import java.util.HashMap;
import java.util.Map;

public class GroupHierarchy {
    public static void main(String[] args) {
        // Create a hierarchy of groups and users
        UserGroup rootGroup = createGroupHierarchy();

        // Search for a user by id
        User targetUser = searchUserById(rootGroup, "2");
        if (targetUser != null) {
            System.out.println("Found user: " + targetUser);
        } else {
            System.out.println("User not found.");
        }
    }

    private static UserGroup createGroupHierarchy() {
        // Create a sample hierarchy
        User user1 = new User("1", "Alice", "Smith");
        User user2 = new User("2", "Bob", "Jones");
        User user3 = new User("3", "Charlie", "Brown");

        UserGroup rootGroup = new UserGroup("Root Group");
        UserGroup group1 = new UserGroup("Group 1");
        UserGroup group2 = new UserGroup("Group 2");
        UserGroup subgroup = new UserGroup("Subgroup");

        group1.addUser(user1);
        group1.addUser(user2);

        subgroup.addUser(user3);
        group2.addSubgroup(subgroup);

        rootGroup.addSubgroup(group1);
        rootGroup.addSubgroup(group2);

        return rootGroup;
    }

    private static User searchUserById(UserGroup group, String userId) {
        Map<String, User> userMap = new HashMap<>();

        // Populate the user map with users in the current group
        for (User user : group.getUsers()) {
            userMap.put(user.getId(), user);
        }

        // Search for the user by id in the current group
        User foundUser = userMap.get(userId);
        if (foundUser != null) {
            return foundUser;
        }

        // Search for the user by id in subgroups
        for (UserGroup subgroup : group.getSubgroups()) {
            User userInSubgroup = searchUserById(subgroup, userId);
            if (userInSubgroup != null) {
                return userInSubgroup;
            }
        }

        return null; // User not found in this group or its subgroups
    }
}