package study.spring.seoul4u.model;

public class Comment {
	private int id;
	private String writerId; // 댓글 작성자 아이디
	private String writerPw; // 댓글 작성자 패스워드
	private String email; // 댓글 작성자 이메일
	private String content;
	private int rating;
	private String regDate;
	private String editDate;
	private String ipAddress;
	private String categoryComment;
	private int memberId; // null 허용 : 맴버 사라저도 코멘트는 남아야 하니까
	private int travelId; // null 허용: comment 테이블의 행은  travel, travelplan, customerCenter 중 하나와 관련되 있다.
	private int travelPlanId; // null 허용
	private int customerCenterId; // null 허용
	private String profileImg; //member 테이블과 innerjoin
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public String getWriterPw() {
		return writerPw;
	}
	public void setWriterPw(String writerPw) {
		this.writerPw = writerPw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCategoryComment() {
		return categoryComment;
	}
	public void setCategoryComment(String categoryComment) {
		this.categoryComment = categoryComment;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getTravelId() {
		return travelId;
	}
	public void setTravelId(int travelId) {
		this.travelId = travelId;
	}
	public int getTravelPlanId() {
		return travelPlanId;
	}
	public void setTravelPlanId(int travelPlanId) {
		this.travelPlanId = travelPlanId;
	}
	public int getCustomerCenterId() {
		return customerCenterId;
	}
	public void setCustomerCenterId(int customerCenterId) {
		this.customerCenterId = customerCenterId;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", writerId=" + writerId + ", writerPw=" + writerPw + ", email=" + email
				+ ", content=" + content + ", rating=" + rating + ", regDate=" + regDate + ", editDate=" + editDate
				+ ", ipAddress=" + ipAddress + ", categoryComment=" + categoryComment + ", memberId=" + memberId
				+ ", travelId=" + travelId + ", travelPlanId=" + travelPlanId + ", customerCenterId=" + customerCenterId
				+ ", profileImg=" + profileImg + "]";
	}
}
