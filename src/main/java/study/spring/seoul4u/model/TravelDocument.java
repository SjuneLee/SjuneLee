package study.spring.seoul4u.model;

public class TravelDocument {
	
	private int id;
	private String category;
	private String subject;
	private String summary;
	private String content;
	private String address;
	private Double mapX;
	private Double mapY;
	private int likeSum;
	private float ratingAvg;
	private int hit;
	private String regDate;
	private String editDate;
	private int memberId;
	
	private int limitStart;
	private int listCount;
	
	//사용자 갤러리 구현을 위해서 추가된 값
	//private boolean gallery;
	private String imagePath;
	private String search;
	//전체 검색
	private String keyword;
	
	// rating_avg DB 추가
	private int rating;
	private int travelId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getMapX() {
		return mapX;
	}
	public void setMapX(Double mapX) {
		this.mapX = mapX;
	}
	public Double getMapY() {
		return mapY;
	}
	public void setMapY(Double mapY) {
		this.mapY = mapY;
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
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
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
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
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
	public int getTravelId() {
		return travelId;
	}
	public void setTravelId(int travelId) {
		this.travelId = travelId;
	}
	@Override
	public String toString() {
		return "TravelDocument [id=" + id + ", category=" + category + ", subject=" + subject + ", summary=" + summary
				+ ", content=" + content + ", address=" + address + ", mapX=" + mapX + ", mapY=" + mapY + ", likeSum="
				+ likeSum + ", ratingAvg=" + ratingAvg + ", hit=" + hit + ", regDate=" + regDate + ", editDate="
				+ editDate + ", memberId=" + memberId + ", limitStart=" + limitStart + ", listCount=" + listCount
				+ ", imagePath=" + imagePath + ", search=" + search + ", keyword=" + keyword + ", rating=" + rating
				+ ", travelId=" + travelId + "]";
	}

}
