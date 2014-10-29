package weibo4j.examples.comment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import weibo4j.Comments;
import weibo4j.Users;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.Paging;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

public class GetCommentById {

	private final static String host = "10.108.192.165";
	private final static String dbName = "AlexYoung";
	private final static String username = "ittcdb";
	private final static String password = "ittc706706";
	private final static String collectionName = "spamer";
	private static DBCollection statusCollection;
	private static String pathname = "./data/spamerTime.txt";

	public GetCommentById() throws UnknownHostException {
		this.statusCollection = MongoConnection.getConnection(host, dbName,
				username, password, collectionName);
	}

	public void getCommentTime(String weiboId, Date author_date)
			throws IOException, WeiboException {
		String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
		Comments cm = new Comments();
		cm.client.setToken(access_token);
		CommentWapper temp = cm.getCommentById(weiboId);
		long number = temp.getTotalNumber();
		long pagenumber = number / 50;
		for (int pagenum = 1; pagenum <= pagenumber + 1; pagenum++) {
			Paging page = new Paging(pagenum);
			// page.setCount(50);
			CommentWapper comment = cm.getCommentById(weiboId, page, 0);

			for (Comment c : comment.getComments()) {
				// Log.logInfo(c.toString());
				Date reviewer_date = c.getCreatedAt();
				long diff = (reviewer_date.getTime() - author_date.getTime()) / 3600000;
				long now = diff - 8;
				print(now);
			}
		}
		// System.out.println(comment.getTotalNumber());
		// System.out.println(comment.getNextCursor());
		// System.out.println(comment.getNextCursor());
		// System.out.println(comment.getHasvisible());

	}

	public void getRepostRatio(){
		
	}
	
	public void getStatusMid() throws IOException, Exception {
		DBObject dbObject = new BasicDBObject();
		DBObject query = new BasicDBObject();
		dbObject.put("WeiboID", true);
		dbObject.put("Comment_Count", true);
		dbObject.put("CreateTime", true);
		DBCursor cursor = statusCollection.find(query, dbObject);
		while (cursor.hasNext()) {
			String info = cursor.next().toString();
			JSONObject object = JSONObject.fromObject(info);

			int count = object.optInt("Comment_Count");
			if (count > 0) {
				JSONObject time = JSONObject.fromObject(object
						.getString("CreateTime"));
				String date = time.getString("$date");

				SimpleDateFormat sFormat = new SimpleDateFormat(
						"yyyy-MM-ddHH:mm:ss");
				System.out.println(date);
				Date now = sFormat.parse(time.optString("$date").substring(0,
						10)
						+ time.optString("$date").substring(11, 19));

				String weiboId = object.optString("WeiboID");
				System.out.println(weiboId);
				getCommentTime(weiboId, now);
			}
		}
	}
/**
 * 获取微博Spam的注册时间
 * @throws IOException
 * @throws Exception
 */
	public void getSpamTime() throws IOException, Exception {
		DBObject dbObject = new BasicDBObject();
		DBObject query = new BasicDBObject();
		dbObject.put("CreatedAt", true);
		DBCursor cursor = statusCollection.find(query, dbObject);
		while (cursor.hasNext()) {
			String info = cursor.next().toString();
			JSONObject object = JSONObject.fromObject(info);

			JSONObject time = JSONObject.fromObject(object
					.getString("CreatedAt"));
			String date = time.getString("$date");

			SimpleDateFormat sFormat = new SimpleDateFormat(
					"yyyy-MM-ddHH:mm:ss");
			System.out.println(date);
			Date register = sFormat.parse(time.optString("$date").substring(0, 10)
					+ time.optString("$date").substring(11, 19));
			Date now = new Date();
			long diff = (now.getTime() - register.getTime()) /3600000 / 24; 
			print(diff);
		}
	}

	public void print(Long data) throws IOException {
		FileWriter writer = new FileWriter(new File(pathname), true);
		BufferedWriter bWriter = new BufferedWriter(writer);
		bWriter.append(data + "\r\n");
		bWriter.close();
	}
}
