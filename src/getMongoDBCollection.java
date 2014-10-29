import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

public class getMongoDBCollection
{
	public static DBCollection getMongoDBColl(String host, String DBName,
			String username, String password, String collectionName)
			throws UnknownHostException
	{
		Mongo mongo = new Mongo(host, 27017);
		MongoOptions opt = mongo.getMongoOptions();
		opt.connectTimeout = 120000;
		opt.autoConnectRetry = true;
		opt.socketKeepAlive = true;
		opt.socketTimeout = 20000;
		DB db = mongo.getDB(DBName);
		db.authenticate(username, password.toCharArray());
//		Set<String> collNames = db.getCollectionNames();
//		for (String name : collNames)
//		{
//			System.out.println("collectionName: " + name);
//		}
		// DBCollection statusData = db.getCollection("StatusData");
		DBCollection collection = db.getCollection(collectionName);
		System.out.println("The size of the collection you choose is "
				+ collection.count());
		List<DBObject> indexes = collection.getIndexInfo();
		for (DBObject i : indexes)
		{
			System.out.println("Indexes info of the collection:");
			System.out.println(i);
		}
		return collection;
	}
}
