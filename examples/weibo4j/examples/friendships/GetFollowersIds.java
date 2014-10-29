package weibo4j.examples.friendships;

import weibo4j.Friendships;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.WeiboException;

public class GetFollowersIds
{

	public static void main(String[] args)
	{
		String access_token = "2.00fGrYJCe3dgkC60196939db0xHe1q";
		String uid = "1642909335";
		Friendships fm = new Friendships();
		fm.client.setToken(access_token);
		try
		{
			String[] ids = fm.getFollowersIdsById(uid, 5000, 0);
			for (String u : ids)
			{
				Log.logInfo(u.toString());
			}
		} catch (WeiboException e)
		{
			e.printStackTrace();
		}

	}

}
