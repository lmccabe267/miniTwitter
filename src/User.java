import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class User {
    private String id;
    private String groupId;
    private HashSet<String> following;
    private List<String> tweets;
    private TreeView treeView;
    private long createdTime;
    private long timestamp;

    public User(String id, TreeView treeView) {
    	this.treeView = treeView;
        this.id = id;
        this.following = new HashSet<String>();
        this.tweets = new ArrayList<String>();
        this.createdTime = System.currentTimeMillis();
        this.timestamp = -1;
    }

    public String getCreatedTime() {
    	return this.createdTime + "";
    }
    
    public String getId() {
        return id;
    }
    
    public String getTimestamp() {
    	return this.timestamp + "";
    }
    
    public void updateTimestamp() {
    	timestamp = System.currentTimeMillis();
    	treeView.setLastUpdated(this);
    	for(String id: following) {
    		treeView.searchUserFromRoot(id).setTimestamp(this.timestamp);
    	}
    }
    
    public void setTimestamp(long timestamp) {
    	this.timestamp = timestamp;
    }
    
    public Boolean followUser(String userId) {
    	if(treeView.searchUserFromRoot(userId) != null) {
    		following.add(userId);
    		return true;
    	}else {
    		return false;
    	}
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