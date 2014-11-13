import java.io.IOException;
import java.text.ParseException;

import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class weiboLauncher {

	/**
	 * @param args
	 * @throws IOException
	 * @throws WeiboException
	 * @throws ParseException
	 * @throws JSONException
	 */
	public static void main(String[] args) throws IOException, WeiboException,
			ParseException, InterruptedException {
		// TODO Auto-generated method stub
		// IterCollection collection = new IterCollection();
		// collection.setToken();
		// collection.getId();
		// collection.getUserFromMongo();

		// get spamers
		// getSpamUser gSpamUser = new getSpamUser();
		// gSpamUser.getSpam("3764222901335238");

		getLiarTweetWithComment liar = new getLiarTweetWithComment();
		liar.getLine();
	}

}
