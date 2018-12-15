package study.spring.seoul4u.model;

public class TravelPlan extends File{
	
	private int id;
	private String startDate;
	private int term;
	private String subject;
	private int season; // Null 허용 : null이면 계획중인 플랜.
	private int theme; // Null 허용: null이면 계획중인 플랜.
	private String regDate;
	private String editDate;
	private int likeSum;
	private float ratingAvg;
	private int hit;
	private String ipAddress;
	private int memberId; // Null 허용,참조키 : 회원이 삭제되도 그가 만든 travelPlan은 남아있다.
	
	private int travelId;
	private int travelPlanId;
	private String imagePath;
		
	private  String travelDays;
	private  int travelMoment;
	private  int travelTheme;
	
	private String keyword;
	
	// rating_avg DB 추가
	private int rating;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
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

	public int getLikeSum() {
		return likeSum;
	}

	public void setLikeSum(int likeSum) {
		this.likeSum = likeSum;
	}

	public float getRatingAvg() {
		return ratingAvg;
	}

	public void setRatingAvg(float ratingAvg) {
		this.ratingAvg = ratingAvg;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getTravelDays() {
		return travelDays;
	}

	public void setTravelDays(String travelDays) {
		this.travelDays = travelDays;
	}

	public int getTravelMoment() {
		return travelMoment;
	}

	public void setTravelMoment(int travelMoment) {
		this.travelMoment = travelMoment;
	}

	public int getTravelTheme() {
		return travelTheme;
	}

	public void setTravelTheme(int travelTheme) {
		this.travelTheme = travelTheme;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "TravelPlan [id=" + id + ", startDate=" + startDate + ", term=" + term + ", subject=" + subject
				+ ", season=" + season + ", theme=" + theme + ", regDate=" + regDate + ", editDate=" + editDate
				+ ", likeSum=" + likeSum + ", ratingAvg=" + ratingAvg + ", hit=" + hit + ", ipAddress=" + ipAddress
				+ ", memberId=" + memberId + ", travelId=" + travelId + ", travelPlanId=" + travelPlanId
				+ ", imagePath=" + imagePath + ", travelDays=" + travelDays + ", travelMoment=" + travelMoment
				+ ", travelTheme=" + travelTheme + ", keyword=" + keyword + ", rating=" + rating + "]";
	}
	
	
}
