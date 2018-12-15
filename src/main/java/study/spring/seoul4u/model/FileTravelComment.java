package study.spring.seoul4u.model;

public class FileTravelComment extends File{
	// planning2에서 여행지 정보를 보여주기 위한 JAVABEANS
	private int fileId;//file 테이블
	private String originName;//file 테이블
	private String fileDir;//file 테이블
	private String fileName;//file 테이블
	private int travelId;//travel 테이블
	private String travelCategory;//travel 테이블
	private String travelSubject;//travel 테이블
	private String travelSummary;//travel 테이블
	private String travelContent;//travel 테이블
	private String travelAddress;//travel 테이블
	private Double travelMapX;//travel 테이블
	private Double travelMapY;//travel 테이블
	private int travelLikeSum;//travel 테이블
	private float travelRatingAvg;//서브쿼리 commentAvgBytravelId를 담을 값
	private int travelHit;//travel 테이블
	private int commentCount;//서브쿼리 commentCountByTravelId를 담을 값
	
	private String imagePath; //이미지경로
	
	public int getFileId() {return fileId;}
	public void setFileId(int fileId) {this.fileId = fileId;}
	
	public String getOriginName() {return originName;}
	public void setOriginName(String originName) {this.originName = originName;}
	
	public String getFileDir() {return fileDir;}
	public void setFileDir(String fileDir) {this.fileDir = fileDir;}
	
	public String getFileName() {return fileName;}
	public void setFileName(String fileName) {this.fileName = fileName;}
	
	public int getTravelId() {return travelId;}
	public void setTravelId(int travelId) {this.travelId = travelId;}
	
	public String getTravelCategory() {return travelCategory;}
	public void setTravelCategory(String travelCategory) {this.travelCategory = travelCategory;}
	
	public String getTravelSubject() {return travelSubject;}
	public void setTravelSubject(String travelSubject) {this.travelSubject = travelSubject;}
	
	public String getTravelSummary() {return travelSummary;}
	public void setTravelSummary(String travelSummary) {this.travelSummary = travelSummary;}
	
	public String getTravelContent() {return travelContent;}
	public void setTravelContent(String travelContent) {this.travelContent = travelContent;}
	
	public String getTravelAddress() {return travelAddress;}
	public void setTravelAddress(String travelAddress) {this.travelAddress = travelAddress;}
	
	public Double getTravelMapX() {return travelMapX;}
	public void setTravelMapX(Double travelMapX) {this.travelMapX = travelMapX;}
	
	public Double getTravelMapY() {return travelMapY;}
	public void setTravelMapY(Double travelMapY) {this.travelMapY = travelMapY;}
	
	public int getTravelLikeSum() {return travelLikeSum;}
	public void setTravelLikeSum(int travelLikeSum) {this.travelLikeSum = travelLikeSum;}
	
	public float getTravelRatingAvg() {return travelRatingAvg;}
	public void setTravelRatingAvg(float travelRatingAvg) {this.travelRatingAvg = travelRatingAvg;}
	
	public int getTravelHit() {return travelHit;}
	public void setTravelHit(int travelHit) {this.travelHit = travelHit;}
	
	public int getCommentCount() {return commentCount;}
	public void setCommentCount(int commentCount) {this.commentCount = commentCount;}
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public String toString() {
		return "FileTravelComment [fileId=" + fileId + ", originName=" + originName + ", fileDir=" + fileDir
				+ ", fileName=" + fileName + ", travelId=" + travelId + ", travelCategory=" + travelCategory
				+ ", travelSubject=" + travelSubject + ", travelSummary=" + travelSummary + ", travelContent="
				+ travelContent + ", travelAddress=" + travelAddress + ", travelMapX=" + travelMapX + ", travelMapY="
				+ travelMapY + ", travelLikeSum=" + travelLikeSum + ", travelRatingAvg=" + travelRatingAvg
				+ ", travelHit=" + travelHit + ", commentCount=" + commentCount + ", imagePath=" + imagePath + "]";
	}
	
}
