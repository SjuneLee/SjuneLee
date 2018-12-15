package study.spring.seoul4u.model;

public class DetailPlan extends TravelPlan{

	private int id;
	private int day;
	private int contentNo;
	private int memberId;// null 허용. 참조키
	private int travelId;// 참조키
	private int travelPlanId;// 참조키
	private String imagePath;
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public int getDay() {return day;}
	public void setDay(int day) {this.day = day;}
	
	public int getContentNo() {return contentNo;}
	public void setContentNo(int contentNo) {this.contentNo = contentNo;}
	
	public int getMemberId() {return memberId;}
	public void setMemberId(int memberId) {this.memberId = memberId;}
	
	public int getTravelId() {return travelId;}
	public void setTravelId(int travelId) {this.travelId = travelId;}
	
	public int getTravelPlanId() {return travelPlanId;}
	public void setTravelPlanId(int travelPlanId) {this.travelPlanId = travelPlanId;}
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public String toString() {
		return "DetailPlan [id=" + id + ", day=" + day + ", contentNo=" + contentNo + ", memberId=" + memberId
				+ ", travelId=" + travelId + ", travelPlanId=" + travelPlanId + ", imagePath=" + imagePath
				+ ", getStartDate()=" + getStartDate() + ", getTerm()=" + getTerm() + ", getSubject()=" + getSubject()
				+ ", getSeason()=" + getSeason() + ", getTheme()=" + getTheme() + ", getRegDate()=" + getRegDate()
				+ ", getEditDate()=" + getEditDate() + ", getLikeSum()=" + getLikeSum() + ", getRatingAvg()="
				+ getRatingAvg() + ", getHit()=" + getHit() + ", getIpAddress()=" + getIpAddress()
				+ ", getTravelDays()=" + getTravelDays() + ", getTravelMoment()=" + getTravelMoment()
				+ ", getTravelTheme()=" + getTravelTheme() + ", getOriginName()=" + getOriginName() + ", getFileDir()="
				+ getFileDir() + ", getFileName()=" + getFileName() + "]";
	}	
	
}
