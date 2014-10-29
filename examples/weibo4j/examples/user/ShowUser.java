package weibo4j.examples.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import weibo4j.Users;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class ShowUser
{
	private static String path = "./data/test.txt";
	private static String inPath = "./data/VipId";
	private static int limit = 1000000;
//	private int step = 0;
	
	public static void main(String[] args) throws IOException
	{
		ShowUser showUser = new ShowUser();
		showUser.getUid(path);
		//showUser.seperateId();
	}
		
	
	public void getVipId(String Uid) throws IOException{
		String access_token = "2.00KdnqDD0HMGJXf22c615d31Q51dWD";
		//String uid = "1314637182";
		Users um = new Users();
		um.client.setToken(access_token);
		try
		{
			User user = um.showUserById(Uid);
			//Log.logInfo(user.toString());
			if (user.getFollowersCount() > limit) {
				System.out.println(user.getFollowersCount());
				setUid(user.getId());
			}
		} catch (WeiboException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setUid(String Uid) throws IOException{
		FileWriter writer = new FileWriter(inPath, true);
		writer.write(Uid);
		writer.write("\r\n");
		writer.close();
	}
	
	public void getUid(String path) throws IOException{
		InputStreamReader iReader = new InputStreamReader(new FileInputStream(new File(path)));
		BufferedReader bReader = new BufferedReader(iReader);
		String line;
		while((line = bReader.readLine()) != null){
			getVipId(line);
		}	
		bReader.close();
	}
	
	public void seperateId() throws IOException{
		InputStreamReader iReader = new InputStreamReader(new FileInputStream(new File("./data/VipId")));
		BufferedReader bReader = new BufferedReader(iReader);
		String line;
		line = bReader.readLine();
		for (int i = 0; i < line.length(); i+=10) {
			setUid(line.substring(i, i+9));
		}
		bReader.close();
	}

}
