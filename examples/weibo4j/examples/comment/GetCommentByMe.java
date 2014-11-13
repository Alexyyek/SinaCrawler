package weibo4j.examples.comment;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.WeiboException;

public class GetCommentByMe {

	public static void main(String[] args) {
		String access_token = "2.00jrobWBe3dgkC4b30b8e7d358ktQB";
		Comments cm = new Comments();
		cm.client.setToken(access_token);
		try {
			CommentWapper comment = cm.getCommentById("3768594901295237");
			for(Comment c : comment.getComments()){
				//Log.logInfo(c.toString());
				System.out.println(c.getText());
			}
			System.out.println(comment.getNextCursor());
			System.out.println(comment.getPreviousCursor());
			System.out.println(comment.getTotalNumber());
			System.out.println(comment.getHasvisible());
			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
