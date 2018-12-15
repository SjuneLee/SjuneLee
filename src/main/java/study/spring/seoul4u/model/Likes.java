package study.spring.seoul4u.model;

public class Likes{
	private int id;
	private int memberId;
	private int travelId;
	private int travelPlanId;
	private int likeSum;
	private String imagePath;
	private String category;
	private String subject;
	
	private int limitStart;
	private int listCount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getLikeSum() {
		return likeSum;
	}
	public void setLikeSum(int likeSum) {
		this.likeSum = likeSum;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	@Override
	public String toString() {
		return "Likes [id=" + id + ", memberId=" + memberId + ", travelId=" + travelId + ", travelPlanId="
				+ travelPlanId + ", likeSum=" + likeSum + ", imagePath=" + imagePath + ", category=" + category
				+ ", subject=" + subject + ", limitStart=" + limitStart + ", listCount=" + listCount + "]";
	}
	
	

	
}