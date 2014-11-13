import java.util.Date;

public class CommentWithStatusData {
	public CommentWithStatusData() {
		this.setSegName(new java.util.ArrayList<String>());
	}

	// comment date
	private Date commentDate;

	public final Date getCommentDate() {
		return commentDate;
	}

	public final void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	// comment id
	private long commentId;

	public final long getCommentID() {
		return commentId;
	}

	public final void setCommentID(long commentID) {
		this.commentId = commentID;
	}

	// comment text
	private String commentText;

	public final String getcommentText() {
		return commentText;
	}

	public final void setcommentText(String commentText) {
		this.commentText = commentText;
	}

	// comment user name
	private String reviewerName;

	public final String getReviewrName() {
		return reviewerName;
	}

	public final void setReviewerName(String ReviewerName) {
		this.reviewerName = ReviewerName;
	}

	// comment user id

	private String ReviewerID;

	public final String getReviewrID() {
		return ReviewerID;
	}

	public final void setReviewerID(String ReviewerID) {
		this.ReviewerID = ReviewerID;
	}
	
	//comment user CreateAt
	
	private Date ReviewerCreateAt;
	
	public final Date getReviewerCreateAt() {
		return ReviewerCreateAt;
	}

	public final void setReviewerCreateAt(Date ReviewerCreateAt) {
		this.ReviewerCreateAt = ReviewerCreateAt;
	}

	// comment user followers_count

	private int followers_count;

	public final int getReviewrFollowersCount() {
		return followers_count;
	}

	public final void setReviewrFollowersCount(int followers_count) {
		this.followers_count = followers_count;
	}

	// comment user friends_count

	private int friends_count;

	public final int getReviewrFriendsCount() {
		return friends_count;
	}

	public final void setReviewrFriendsCount(int friends_count) {
		this.friends_count = friends_count;
	}

	// comment user statuses_count

	private int statuses_count;

	public final int getReviewrStatusesCount() {
		return statuses_count;
	}

	public final void setReviewrStatusesCount(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	//comment user retweet ratio
	
	private double retweet_ratio;
	
	public final double getReviewerRetweetRatio(){
		return retweet_ratio;
	}
	
	public final void setReviewerRetweetRatio(double retweet_ratio){
		this.retweet_ratio = retweet_ratio;
	}
	
	// comment user bi_followers_count
	private int bi_followers_count;

	public final int getReviewrBiCount() {
		return bi_followers_count;
	}

	public final void setReviewrBiCount(int bi_followers_count) {
		this.bi_followers_count = bi_followers_count;
	}

	//tweet create date
	private Date tweetDate;
	
	public final Date getTweetDate(){
		return tweetDate;
	}
	
	public final void setTweetDate(Date tweetDate){
		this.tweetDate = tweetDate;
	}
	
	//tweet id
	private String tweeterID;

	public final String getTweerID() {
		return tweeterID;
	}

	public final void setTweerID(String tweeterID) {
		this.tweeterID = tweeterID;
	}
	
	// tweet Mid
	private String tweetMid;

	public final String getTweetMid() {
		return tweetMid;
	}

	public final void setTweetMid(String tweetMid) {
		this.tweetMid = tweetMid;
	}
	
	//tweet text
	private String TweetText;

	public final String getTweerText() {
		return TweetText;
	}

	public final void setTweerText(String TweetText) {
		this.TweetText = TweetText;
	}
	
	//tweet comments_count
	private int comments_count;

	public final int getCommentsCount() {
		return comments_count;
	}

	public final void setCommentsCount(int comments_count) {
		this.comments_count = comments_count;
	}
	
	// author name
	private String authorName;

	public final String getAuthorName() {
		return authorName;
	}

	public final void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	// author id

	private String AuthorID;

	public final String getAuthorID() {
		return AuthorID;
	}

	public final void setAuthorID(String AuthorID) {
		this.AuthorID = AuthorID;
	}
	
	//author CreateAt
	
	private Date AuthorCreateAt;
	
	public final Date getAuthorCreateAt() {
		return AuthorCreateAt;
	}

	public final void setAuthorCreateAt(Date AuthorCreateAt) {
		this.AuthorCreateAt = AuthorCreateAt;
	}

	// author followers_count

	private int authorfollowers_count;

	public final int getAuthorFollowersCount() {
		return authorfollowers_count;
	}

	public final void setAuthorFollowersCount(int authorfollowers_count) {
		this.authorfollowers_count = authorfollowers_count;
	}

	// author friends_count

	private int authorfriends_count;

	public final int getAuthorFriendsCount() {
		return authorfriends_count;
	}

	public final void setAuthorFriendsCount(int authorfriends_count) {
		this.authorfriends_count = authorfriends_count;
	}

	// author statuses_count

	private int authorstatuses_count;

	public final int getAuthorStatusesCount() {
		return authorstatuses_count;
	}

	public final void setAuthorStatusesCount(int authorstatuses_count) {
		this.authorstatuses_count = authorstatuses_count;
	}

	// author bi_followers_count
	private int authorbi_followers_count;

	public final int getAuthorBiCount() {
		return authorbi_followers_count;
	}

	public final void setAuthorBiCount(int authorbi_followers_count) {
		this.authorbi_followers_count = authorbi_followers_count;
	}
	
	//like
	private int like;
	public final int getLike(){
		return like;
	}
	
	public final void setLike(int like){
		this.like = like;
	}
	
	//others
	private String privateContent;

	public final String getContent() {
		return privateContent;
	}

	public final void setContent(String value) {
		privateContent = value;
	}

	/**
	 * Gets or sets the creation time of the status.
	 */

	private Date privateCreatedAt;

	public final Date getCreatedAt() {
		return privateCreatedAt;
	}

	public final void setCreatedAt(Date value) {
		privateCreatedAt = value;
	}

	/**
	 * Gets or sets the stutus id.
	 */
	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [XmlElement("id")]
	private long privateID;

	public final long getID() {
		return privateID;
	}

	public final void setID(long value) {
		privateID = value;
	}

	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [MongoIgnore]
	public final String getKey() {
		return (new Long(this.getID())).toString();
	}

	public final void setKey(String value) {
		this.setID(Long.parseLong(value));
	}

	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [MongoIgnore]
	public final Object getRealBindingData() {
		return this;
	}

	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [MongoIgnore]
	private java.util.ArrayList<String> privateSegName;

	public final java.util.ArrayList<String> getSegName() {
		return privateSegName;
	}

	public final void setSegName(java.util.ArrayList<String> value) {
		privateSegName = value;
	}
}
