package weibo4j.examples.timeline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import weibo4j.Timeline;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

public class GetUserTimelineByUid {
	 public static void main(String[] args) throws IOException {
         String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
         Timeline tm = new Timeline();
 		tm.client.setToken(access_token);

         try {
        	 	//for (int i = 1; i < 2; i++) {
        	 		Paging pag = new Paging();
        	 		pag.setCount(100);
//        	 		StatusWapper status = tm.getUserTimelineByUid("2009182385", pag, 0, 0);
        	 		StatusWapper status = tm.getUserTimelineByUid("1794052095", pag, 0, 0);
                    for (Status s : status.getStatuses()) {
                            //System.out.println(s.getId() + "," + s.getCreatedAt() + "," + s.getText());
                    	Status re = s.getRetweetedStatus();
                    	if (re != null) {
                    		print(re.getId());
						}else {
							print("原创");
						}
                    	
                    }
				//}
                // pag.setSinceId(3481085788427823L);
                 
         } catch (WeiboException e) {
                 e.printStackTrace();
         }
 }
	 
	 public static void print(String data) throws IOException {
			FileWriter writer = new FileWriter(new File("./data/131463718211111.txt"), true);
			BufferedWriter bWriter = new BufferedWriter(writer);
			bWriter.append(data + "\r\n");
			bWriter.close();
		}
}
