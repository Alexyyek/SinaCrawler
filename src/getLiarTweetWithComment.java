import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

public class getLiarTweetWithComment {
	private final String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
	private final static String host = "10.108.192.165";
	private final static String dbName = "AlexYoung";
	private final static String username = "ittcdb";
	private final static String password = "ittc706706";
//	private final static String collectionName = "CreditStatusWithComment";
	private final static String collectionName = "test";
	private static DBCollection Collection;
	private String path = "./data/bigLiarMid.txt";

	public getLiarTweetWithComment() throws UnknownHostException {
		this.Collection = getMongoDBCollection.getMongoDBColl(host,
				dbName, username, password, collectionName);
	}

	public void getLine() throws IOException, WeiboException {
		InputStreamReader iReader = new InputStreamReader(new FileInputStream(
				new File(path)));
		BufferedReader bReader = new BufferedReader(iReader);
		String line;
		while ((line = bReader.readLine()) != null) {
			getIdFromMid(line);
		}
		bReader.close();
	}

	public void getIdFromMid(String mid) throws WeiboException {
		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		try {
			JSONObject object = tm.QueryId(mid, 1, 1);
			String id = object.optString("id");
			getTweetById(id, mid);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

	public void getTweetById(String id, String mid) throws WeiboException{
		Comments cm = new Comments();
		cm.client.setToken(access_token);
		CommentWapper temp = cm.getCommentById(id);
		long number = temp.getTotalNumber();
		long pagenumber = number / 50;
		for (int pagenum = 1; pagenum <= pagenumber + 1; pagenum++) {
			Paging page = new Paging(pagenum);
			CommentWapper comment = cm.getCommentById(id, page, 0);
			for (Comment c : comment.getComments()) {
				getComment(c, mid);
//				getRetweetRatio(c);
			}
		}
	}

	public void getComment(Comment comment, String mid) {
		CommentWithStatusData cData = new CommentWithStatusData();
		cData.setCommentDate(comment.getCreatedAt());
		cData.setCommentID(comment.getId());
		cData.setcommentText(comment.getText());
		User user = comment.getUser();
		cData.setReviewerID(user.getId());
		cData.setReviewerName(user.getName());
		cData.setReviewerCreateAt(user.getCreatedAt());
		cData.setReviewrFollowersCount(user.getFollowersCount());
		cData.setReviewrFriendsCount(user.getFriendsCount());
		cData.setReviewrStatusesCount(user.getStatusesCount());
		cData.setReviewrBiCount(user.getBiFollowersCount());
		Status status = comment.getStatus();
		cData.setTweerID(status.getId());
		cData.setTweetMid(mid);
		cData.setTweetDate(status.getCreatedAt());
		cData.setTweerText(status.getText());
		cData.setCommentsCount(status.getCommentsCount());
		User author = status.getUser();
		cData.setAuthorID(author.getId());
		cData.setAuthorName(author.getName());
		cData.setAuthorCreateAt(author.getCreatedAt());
		cData.setAuthorFollowersCount(author.getFollowersCount());
		cData.setAuthorFriendsCount(author.getFriendsCount());
		cData.setAuthorStatusesCount(author.getStatusesCount());
		cData.setAuthorBiCount(author.getBiFollowersCount());
		cData.setLike(0);
		updateToDb(cData, comment.getId());
	}

	public void updateToDb(CommentWithStatusData cData, long commentID) {
		DBObject query = new BasicDBObject("commentID", commentID);
		DBObject object = new BasicDBObject();
		object.put("CommentDate", cData.getCommentDate());
		object.put("CommentID", cData.getCommentID());
		object.put("CommentText", cData.getcommentText());
		object.put("ReviewerID", cData.getReviewrID());
		object.put("ReviewerName", cData.getReviewrName());
		object.put("ReviewerCreateAt", cData.getReviewerCreateAt());
		object.put("ReviewrFollowersCount", cData.getReviewrFollowersCount());
		object.put("ReviewrFriendsCount", cData.getReviewrFriendsCount());
		object.put("ReviewrStatusesCount", cData.getReviewrStatusesCount());
		object.put("ReviewrBiCount", cData.getReviewrBiCount());
		object.put("TweerID", cData.getTweerID());
		object.put("TweetMid", cData.getTweetMid());
		object.put("TweetDate", cData.getTweetDate());
		object.put("TweerText", cData.getTweerText());
		object.put("CommentsCount", cData.getCommentsCount());
		object.put("AuthorID", cData.getAuthorID());
		object.put("AuthorName", cData.getAuthorName());
		object.put("AuthorCreateAt", cData.getAuthorCreateAt());
		object.put("AuthorFollowersCount", cData.getAuthorFollowersCount());
		object.put("AuthorFriendsCount", cData.getAuthorFriendsCount());
		object.put("AuthorStatusesCount", cData.getAuthorStatusesCount());
		object.put("AuthorBiCount", cData.getAuthorBiCount());
		object.put("Like", cData.getLike());
		DBObject updateSetValue = new BasicDBObject("$set", object);
		Collection.update(query, updateSetValue, true, false);
	}
	
	/**
	 * 补原创比例
	 * @param comment
	 */
	public void getRetweetRatio(Comment comment) {
		CommentWithStatusData cData = new CommentWithStatusData();
		User user = comment.getUser();
		int statusCount = user.getStatusesCount();
		long id = comment.getId();
		cData.setReviewerRetweetRatio(getReteet(user.getId(), statusCount));
		updateRetweet(cData, id);
	}
	
	public void updateRetweet(CommentWithStatusData cData, long commentId){
		DBObject updateSetValue = new BasicDBObject("$set", new BasicDBObject("ReviewerRetweetRatio",cData.getReviewerRetweetRatio()));
		Collection.update(new BasicDBObject().append("commentID", commentId), updateSetValue, false, false);
	}
	
	public double getReteet(String id, int count) {
		Timeline timeline = new Timeline();
		timeline.client.setToken(access_token);
		double origin = 0.0;
		double copy = 0.0;
		for (int i = 1; i < 5; i++) {
			Paging paging = new Paging(i);
			paging.setCount(50);
			StatusWapper sWapper;
			try {
				sWapper = timeline.getUserTimelineByUid(id, paging, 0, 0);
				for (Status s : sWapper.getStatuses()) {
					Status re = s.getRetweetedStatus();
					if (re != null) {
						copy += 1.0;
					} else {
						origin += 1.0;
					}
				}
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return origin / (origin + copy);
	}
	
	/**
	 * 补注册时间,主程序已修改，此段保留他用
	 * @param comment
	 */
	public void getCreateAt(Comment comment){
		CommentWithStatusData cData = new CommentWithStatusData();
		User user = comment.getUser();
		cData.setReviewerCreateAt(user.getCreatedAt());
		Status status = comment.getStatus();
		User author = status.getUser();
		cData.setAuthorCreateAt(author.getCreatedAt());
		long id = comment.getId();
		update(cData, id);
	}
	
	public void update(CommentWithStatusData cData, long commentID){
		DBObject updateSetValue = new BasicDBObject("$set", new BasicDBObject("ReviewerCreateAt",cData.getReviewerCreateAt()));
		Collection.update(new BasicDBObject().append("commentID", commentID), updateSetValue, false, false);
	}
}
