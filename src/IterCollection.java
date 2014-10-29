import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import weibo4j.Account;
import weibo4j.Comments;
import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class IterCollection {
	private final String host = "10.108.192.165";
	private final String dbName = "AlexYoung";
	private final String username = "ittcdb";
	private final String password = "ittc706706";
	private final String UserCollection = "User";
	private final String StatusCollection = "UserStatus";
	private final String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
	private final String filepath = "data/User.txt";
	private static DBCollection userColl;
	private static DBCollection statusColl;
	private Users userInfo;
	private Friendships fsInfo;
	private Timeline timeline;
	private Account acountInfo;
	private Comments commentInfo;
	private Logger logger = Logger.getLogger(IterCollection.class.getName());

	public IterCollection() throws UnknownHostException {
		this.userColl = getMongoDBCollection.getMongoDBColl(host, dbName,
				username, password, UserCollection);
		this.statusColl = getMongoDBCollection.getMongoDBColl(host, dbName,
				username, password, StatusCollection);
	}

	public void getUser(String UserId) throws WeiboException, ParseException,
			InterruptedException {
		logger.info(UserId);
		getIpLimits();
		try {
			getUserStatusId(UserId, getDataFromUser(UserId));
		} catch (WeiboException e) {
			setException(e);
		}
	}
	
	public void getUserFromMongo() throws WeiboException{
		DBObject dbObject = new BasicDBObject();
		DBObject query = new BasicDBObject();
		dbObject.put("ID", true);
		DBCursor cursor = userColl.find(query, dbObject);
		while(cursor.hasNext()){
			String info = cursor.next().toString();
			net.sf.json.JSONObject jObject = net.sf.json.JSONObject.fromObject(info);
			String userId = jObject.getString("ID");
			logger.info(userId);
			//getIpLimits();
			try {
				getUserStatusId(userId);
			} catch (WeiboException e) {
				// TODO: handle exception
			}
		}
	}
	
	public void getUserStatusId(String UserId, User user) throws WeiboException {
		int statusCount = user.getStatusesCount();
		for (int pagenum = 1; pagenum <= 60; pagenum++) {
			Paging page = new Paging(pagenum);
			getTimeline(UserId, page);
		}
	}
	
	public void getUserStatusId(String UserId) throws WeiboException {
		for (int pagenum = 1; pagenum <= 5	; pagenum++) {
			Paging page = new Paging(pagenum);
			getTimeline(UserId, page);
		}
	}

	public void getTimeline(String UserId,Paging page) throws WeiboException {
		StatusWapper wapper = timeline.getUserTimelineByUid(UserId, page, 0, 0);
		List<Status> list = wapper.getStatuses();
		System.out.println(wapper.getTotalNumber());
		for (Status status : list) {
			JSONObject weiboMid = timeline.QueryMid(1, status.getId());
			String mid = weiboMid.optString("mid");
			System.out.println(mid);
			getStatusComments(status, mid);
		}
	}

	public void getIpLimits() throws WeiboException {
		int ipLimit = acountInfo.getAccountRateLimitStatus()
				.getRemainingIpHits();
		while (ipLimit < 4) {
			ipLimit = acountInfo.getAccountRateLimitStatus()
					.getRemainingIpHits();
			continue;
		}
	}

	public void getId() throws IOException, WeiboException, ParseException,
			InterruptedException {
		InputStreamReader iReader = new InputStreamReader(new FileInputStream(
				new File(filepath)));
		BufferedReader bReader = new BufferedReader(iReader);
		String userId;
		while ((userId = bReader.readLine()) != null) {
			getUser(userId);
		}
		bReader.close();
	}

	private String getNowDate() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss HH");
		String date = sdf.format(dt);
		return date;
	}
	
	private int getDataDiff(Date now, Date ago){
		int diff = (int) (now.getTime() / 86400000 - ago.getTime() / 86400000);
		return diff;
	}

	public void setToken() {
		userInfo = new Users();
		fsInfo = new Friendships();
		acountInfo = new Account();
		commentInfo = new Comments();
		timeline = new Timeline();
		userInfo.client.setToken(access_token);
		fsInfo.client.setToken(access_token);
		acountInfo.client.setToken(access_token);
		commentInfo.client.setToken(access_token);
		timeline.client.setToken(access_token);
	}

	public void getStatusComments(Status status, String mid) {
		StatusData sData = new StatusData();

		sData.setID(Long.parseLong(status.getId()));
		sData.setcomments_count(status.getCommentsCount());
		sData.setText(status.getText());
		sData.setCreatedAtTime(status.getCreatedAt());
		sData.setreposts_count(status.getRepostsCount());
		sData.setUserID(Long.parseLong(status.getUser().getId()));
		sData.setMid(mid);
		Date now = new Date();
		if(status.getRetweetedStatus() == null && getDataDiff(now, sData.getCreatedAtTime()) < 100){
//			System.out.println(status.getRetweetedStatus().getText());
			updateToStatusDb(sData.getID(), sData);
		}
	}
	
	public User getDataFromUser(String UserId) throws WeiboException {
		UserData uData = new UserData();
		User user = userInfo.showUserById(UserId);
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
		updateUserToDb(UserId, uData);
		return user;
	}

	public void updateUserToDb(String userId, UserData uData) {
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
		userColl.update(query, updateSetValue, true, false);
	}

	public void updateToStatusDb(Long statusId, StatusData sData) {
		BasicDBObject query = new BasicDBObject("ID", statusId);
		DBObject object = new BasicDBObject();
		object.put("WeiboID", sData.getID());
		object.put("WeiboMid", sData.getMid());
		object.put("UserID", sData.getUserID());
		object.put("Text", sData.getText());
		object.put("Comment_Count", sData.getcomments_count());
		object.put("Repost_Count", sData.getreposts_count());
		object.put("CreateTime", sData.getCreatedAtTime());
		DBObject updateSetValue = new BasicDBObject("$set", object);
		statusColl.update(query, updateSetValue, true, false);
	}

	public void setException(WeiboException e) throws ParseException,
			InterruptedException {
		e.printStackTrace();
		if ((e.getErrorCode() == 10004) || (e.getErrorCode() == 10022)
				|| (e.getErrorCode() == 10023) || (e.getErrorCode() == 10024)) {
			long currenttime = System.currentTimeMillis();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:00:00");
			String datesString = format.format(new Date(currenttime));
			Date datetime = format.parse(datesString);
			long waittime = 1000 * 60 * 60 - (currenttime - datetime.getTime())
					+ 5 * 60000;
			System.out.println();
			System.out.println("访问限制，等待" + waittime / 60000 + "分钟...");
			Thread.sleep(waittime);

		} else if ((e.getErrorCode() == 10002) || (e.getErrorCode() == 10003)
				|| (e.getErrorCode() == 10009) || (e.getErrorCode() == 10010)) {
			Thread.sleep(1000 * 60 * 10);
		}
		// TODO: handle exception
	}
}
