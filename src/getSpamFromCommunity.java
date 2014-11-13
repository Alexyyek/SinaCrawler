import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import weibo4j.Friendships;
import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class getSpamFromCommunity {
	private final static String host = "10.108.192.165";
	private final static String dbName = "AlexYoung";
	private final static String username = "ittcdb";
	private final static String password = "ittc706706";
	private final static String collectionName = "SpamerCommunity";
	private static DBCollection statusCollection;
	private String path = "./data/spamCommunity.txt";

	public getSpamFromCommunity() throws UnknownHostException {
		this.statusCollection = getMongoDBCollection.getMongoDBColl(host,
				dbName, username, password, collectionName);
	}

	public void getLine() throws IOException {
		InputStreamReader iReader = new InputStreamReader(new FileInputStream(
				new File(path)),"GBK");
		BufferedReader bReader = new BufferedReader(iReader);
		String line;
		while ((line = bReader.readLine()) != null) {
			String[] info = line.split("\t");
			if (info.length == 5) {
				getSpamerName(info[2]);
			}
		}
		bReader.close();
	}
	
	public void getSpamerName(String name) {
		String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
//		Friendships fm = new Friendships();
//		fm.client.setToken(access_token);
//		try {
//			UserWapper userWapper = fm.getFollowersByName(name);
//			long count = userWapper.getTotalNumber();
//			System.out.println(userWapper.getTotalNumber());
//		} catch (WeiboException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		Users um = new Users();
		um.client.setToken(access_token);
		try {
			User user = um.showUserByScreenName(name);
			getDataFromUser(user);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getUidFromLocal()throws IOException{
		InputStreamReader iReader = new InputStreamReader(new FileInputStream(new File(path)));
		BufferedReader bReader = new BufferedReader(iReader);
		String line;
		while((line=bReader.readLine())!=null){
			getSpamId(line);
		}
		bReader.close();
	}

	public void getSpamId(String id){
		String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
		Users um = new Users();
		um.client.setToken(access_token);
		try {
			User user = um.showUserById(id);
			getDataFromUser(user);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public User getDataFromUser(User user) throws WeiboException {
		UserData uData = new UserData();
		uData.setID(Long.parseLong(user.getId()));
		uData.setGender(user.getGender());
		uData.setScreenName(user.getScreenName());
		uData.setLocation(user.getLocation());
		uData.setDescription(user.getDescription());
		uData.setFollowersCount(user.getFollowersCount());
		uData.setFriendsCount(user.getFriendsCount());
		uData.SetBiFollowCount(user.getbiFollowersCount());
		uData.setStatusesCount(user.getStatusesCount());
		uData.setCreatedAt(user.getCreatedAt());
		uData.setVerified(user.isVerified());
		uData.setVerifiedReason(user.getVerified_reason());
		updateUserToDb(user.getId(), uData);
		return user;
	}

	public void updateUserToDb(String userId, UserData uData) {
		BasicDBObject query = new BasicDBObject("ID", Long.parseLong(userId));
		DBObject object = new BasicDBObject();
		object.put("ID", uData.getID());
		object.put("Name", uData.getScreenName());
		object.put("Gender", uData.getGender());
		object.put("Location", uData.getLocation());
		object.put("Description", uData.getDescription());
		object.put("FollowersCount", uData.getFollowersCount());
		object.put("FriendsCount", uData.getFriendsCount());
		object.put("BiFollowCount", uData.getBiFollowCount());
		object.put("StatusesCount", uData.getStatusesCount());
		object.put("CreatedAt", uData.getCreatedAt());
		object.put("Verified", uData.getVerified());
		object.put("verifiedReason", uData.getVerifiedReason());
		DBObject updateSetValue = new BasicDBObject("$set", object);
		statusCollection.update(query, updateSetValue, true, false);
	}
}
