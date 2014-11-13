package weibo4j.examples.user;

import weibo4j.Users;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class showUserByDomain {

	public static void main(String[] args) {
		String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
		//String domain  = args[1];
		Users um = new Users();
		um.client.setToken(access_token);
		try {
			//User user = um.showUserByDomain(domain);
			User user = um.showUserById("1288915263");
			Log.logInfo(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
