package weibo4j.examples.comment;

import java.io.IOException;

public class Lancer {
	public static void main(String[] args) throws IOException, Exception {
		GetCommentById gById = new GetCommentById();
//		String UserID = "1314637182";
//		gById.getStatusMid();
		gById.getSpamTime();
	}
}
