import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import weibo4j.Comments;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.Paging;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
/**
 * 从名人评论ID内获取评论用户UID，利用规则筛选spam用户
 * @author AlexYoung
 *
 */
public class getSpamUser {

	private final static String host = "10.108.192.165";
	private final static String dbName = "AlexYoung";
	private final static String username = "ittcdb";
	private final static String password = "ittc706706";
	private final static String collectionName = "spamer";
	private static DBCollection statusCollection;
	private static String pathname = "./data/spam.txt";

	public getSpamUser() throws UnknownHostException {
		this.statusCollection = getMongoDBCollection.getMongoDBColl(host,
				dbName, username, password, collectionName);
	}
	
	public void getSpam(String weiboId) throws WeiboException {
		String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
		Comments cm = new Comments();
		cm.client.setToken(access_token);
		CommentWapper temp = cm.getCommentById(weiboId);
		long number = temp.getTotalNumber();
		long pagenumber = number / 50;
		for (int pagenum = 1; pagenum <= pagenumber + 1; pagenum++) {
			Paging page = new Paging(pagenum);
			CommentWapper comment = cm.getCommentById(weiboId, page, 0);

			for (Comment c : comment.getComments()) {
//				Users um = new Users();
//				um.client.setToken(access_token);
//				User user = um.showUserById(userId);
				User user = c.getUser();
				if (user.getFollowersCount() < 20
						&& user.getFriendsCount() >= 100) {
					getSpamInfo(user);
				}
			}
		}
	}

	public void getSpamInfo(User user) {
		UserData uData = new UserData();
		String UserId = user.getId();
		uData.setID(Long.parseLong(user.getId()));
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
		updateToDb(UserId, uData);
	}

	public void updateToDb(String userId, UserData uData) {
		BasicDBObject query = new BasicDBObject("ID", Long.parseLong(userId));
		DBObject object = new BasicDBObject();
		object.put("ID", uData.getID());
		object.put("Name", uData.getScreenName());
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

	public void print(Long data) throws IOException {
		FileWriter writer = new FileWriter(new File(pathname), true);
		BufferedWriter bWriter = new BufferedWriter(writer);
		bWriter.append(data + "\r\n");
		bWriter.close();
	}

}
