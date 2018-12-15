package study.spring.seoul4u.model;
// 여행자들의 일정 상세보기 할 때 사용
public class PlanTravelFileJoinByDetail {
	private int memberId;
	private int planId;
	private String planSubject;
	private String startDate;
	private int term;
	private int season;
	private int theme;
	private int planLikeSum;
	private float planRatingAvg;
	private int planHit;
	private int detailPlanId;
	private int day;
	private int contentNo;
	private String travelSubject;
	private String travelSummary;
	private String travelContent;
	private String travelAddress;
	private int travelLikeSum;
	private float travelRatingAvg;
	private int travelHit;
	private Double travelMapX;
	private Double travelMapY;
	private String fileOriginName;
	private String fileDir;
	private String fileName;
	private String imagePath;
	
	// getter AND setter
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public String getPlanSubject() {
		return planSubject;
	}
	public void setPlanSubject(String planSubject) {
		this.planSubject = planSubject;
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
	public int getPlanLikeSum() {
		return planLikeSum;
	}
	public void setPlanLikeSum(int planLikeSum) {
		this.planLikeSum = planLikeSum;
	}
	public float getPlanRatingAvg() {
		return planRatingAvg;
	}
	public void setPlanRatingAvg(float planRatingAvg) {
		this.planRatingAvg = planRatingAvg;
	}
	public int getPlanHit() {
		return planHit;
	}
	public void setPlanHit(int planHit) {
		this.planHit = planHit;
	}
	public int getDetailPlanId() {
		return detailPlanId;
	}
	public void setDetailPlanId(int detailPlanId) {
		this.detailPlanId = detailPlanId;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getContentNo() {
		return contentNo;
	}
	public void setContentNo(int contentNo) {
		this.contentNo = contentNo;
	}
	public String getTravelSubject() {
		return travelSubject;
	}
	public void setTravelSubject(String travelSubject) {
		this.travelSubject = travelSubject;
	}
	public String getTravelSummary() {
		return travelSummary;
	}
	public void setTravelSummary(String travelSummary) {
		this.travelSummary = travelSummary;
	}
	public String getTravelContent() {
		return travelContent;
	}
	public void setTravelContent(String travelContent) {
		this.travelContent = travelContent;
	}
	public String getTravelAddress() {
		return travelAddress;
	}
	public void setTravelAddress(String travelAddress) {
		this.travelAddress = travelAddress;
	}
	public int getTravelLikeSum() {
		return travelLikeSum;
	}
	public void setTravelLikeSum(int travelLikeSum) {
		this.travelLikeSum = travelLikeSum;
	}
	public float getTravelRatingAvg() {
		return travelRatingAvg;
	}
	public void setTravelRatingAvg(float travelRatingAvg) {
		this.travelRatingAvg = travelRatingAvg;
	}
	public int getTravelHit() {
		return travelHit;
	}
	public void setTravelHit(int travelHit) {
		this.travelHit = travelHit;
	}
	public Double getTravelMapX() {
		return travelMapX;
	}
	public void setTravelMapX(Double travelMapX) {
		this.travelMapX = travelMapX;
	}
	public Double getTravelMapY() {
		return travelMapY;
	}
	public void setTravelMapY(Double travelMapY) {
		this.travelMapY = travelMapY;
	}
	public String getFileOriginName() {
		return fileOriginName;
	}
	public void setFileOriginName(String fileOriginName) {
		this.fileOriginName = fileOriginName;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Override
	public String toString() {
		return "PlanTravelFileJoinByDetail [memberId=" + memberId + ", planId=" + planId + ", planSubject="
				+ planSubject + ", startDate=" + startDate + ", term=" + term + ", season=" + season + ", theme="
				+ theme + ", planLikeSum=" + planLikeSum + ", planRatingAvg=" + planRatingAvg + ", planHit=" + planHit
				+ ", detailPlanId=" + detailPlanId + ", day=" + day + ", contentNo=" + contentNo + ", travelSubject="
				+ travelSubject + ", travelSummary=" + travelSummary + ", travelContent=" + travelContent
				+ ", travelAddress=" + travelAddress + ", travelLikeSum=" + travelLikeSum + ", travelRatingAvg="
				+ travelRatingAvg + ", travelHit=" + travelHit + ", travelMapX=" + travelMapX + ", travelMapY="
				+ travelMapY + ", fileOriginName=" + fileOriginName + ", fileDir=" + fileDir + ", fileName=" + fileName
				+ ", imagePath=" + imagePath + "]";
	}
		
	
}
