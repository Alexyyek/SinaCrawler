package weibo4j.examples.comment;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

public class MongoConnection {
	public static DBCollection getConnection(String host, String DbName,
			String username, String password, String collectionName)
			throws UnknownHostException {
		Mongo mongo = new Mongo(host, 27017);
		MongoOptions options = new MongoOptions();
		options.autoConnectRetry = true;
		options.socketKeepAlive = true;
		options.socketTimeout = 20000;
		options.connectTimeout = 120000;
		DB db = mongo.getDB(DbName);
		db.authenticate(username, password.toCharArray());
		DBCollection collection = db.getCollection(collectionName);
		return collection;
	}
}
