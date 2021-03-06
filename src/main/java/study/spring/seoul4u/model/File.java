package study.spring.seoul4u.model;

public class File {
	// 여행지, 공지사항, 질문답변의 첨부파일에 대한 정보 
	private int id;
	private String originName;
	private String fileDir;
	private String fileName;
	private String contentType;
	private long fileSize;
	private String regDate;
	private String editDate;
	private int travelId;// 참조키 : travel 테이블의 id (null 허용)
	private int customerCenterId;// 참조키 : customer_center 테이블의 id (null 허용)
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public String getOriginName() {return originName;}
	public void setOriginName(String originName) {this.originName = originName;}
	
	public String getFileDir() {return fileDir;}
	public void setFileDir(String fileDir) {this.fileDir = fileDir;}
	
	public String getFileName() {return fileName;}
	public void setFileName(String fileName) {this.fileName = fileName;}
	
	public String getContentType() {return contentType;}
	public void setContentType(String contentType) {this.contentType = contentType;}
	
	public long getFileSize() {return fileSize;}
	public void setFileSize(long fileSize) {this.fileSize = fileSize;}
	
	public String getRegDate() {return regDate;}
	public void setRegDate(String regDate) {this.regDate = regDate;}
	
	public String getEditDate() {return editDate;}
	public void setEditDate(String editDate) {this.editDate = editDate;}
	
	public int getTravelId() {return travelId;}
	public void setTravelId(int travelId) {this.travelId = travelId;}
	
	public int getCustomerCenterId() {return customerCenterId;}
	public void setCustomerCenterId(int customerCenterId) {this.customerCenterId = customerCenterId;}
	
	@Override
	public String toString() {
		return "File [id=" + id + ", originName=" + originName + ", fileDir=" + fileDir + ", fileName=" + fileName
				+ ", contentType=" + contentType + ", fileSize=" + fileSize + ", regDate=" + regDate + ", editDate="
				+ editDate + ", travelId=" + travelId + ", customerCenterId=" + customerCenterId + "]";
	}
}
