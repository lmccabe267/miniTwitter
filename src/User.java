import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class User {
    private String id;
    private String groupId;
    private HashSet<String> following;
    private List<String> tweets;

    public User(String id) {
        this.id = id;
        this.following = new HashSet<String>();
        this.tweets = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }
    
    public void followUser(String userId) {
    	following.add(userId);
    }
    
    public List<String> getFollowing() {
    	return new ArrayList<String>(following);
    }

    @Override
    public String toString() {
    	return this.id + "";
    }
    
    public void setGroup(String groupId) {
    	this.groupId = groupId;
    }
    
    public List<String> getTweets(){
    	return tweets;
    }
    
    public void tweetMessage(String message) {
    	tweets.add(message);
    }
    
    public String getGroupId() {
    	return groupId;
    }
    
    public String[] getNewsFeed() {
    	return new String[2];
    }
}