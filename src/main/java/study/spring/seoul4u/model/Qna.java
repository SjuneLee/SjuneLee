package study.spring.seoul4u.model;

public class Qna extends Member {
	private int id;
	private String category;
	private char qnaAnswerCondition;
	private char qnaSecretCondition;
	private String subject;
	private String regDate;
	private String content;
	private String qnaAnswerDate;
	private int hit;
	private String userId;
	private String qnaAnswer;
	private int memberId;
	private String ipAddress;
	//검색어
	private String search;
	private String condition;
	
	private int limitStart;
	private int listCount;
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
	public char getQnaAnswerCondition() {
		return qnaAnswerCondition;
	}
	public void setQnaAnswerCondition(char qnaAnswerCondition) {
		this.qnaAnswerCondition = qnaAnswerCondition;
	}
	public char getQnaSecretCondition() {
		return qnaSecretCondition;
	}
	public void setQnaSecretCondition(char qnaSecretCondition) {
		this.qnaSecretCondition = qnaSecretCondition;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getQnaAnswerDate() {
		return qnaAnswerDate;
	}
	public void setQnaAnswerDate(String qnaAnswerDate) {
		this.qnaAnswerDate = qnaAnswerDate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getQnaAnswer() {
		return qnaAnswer;
	}
	public void setQnaAnswer(String qnaAnswer) {
		this.qnaAnswer = qnaAnswer;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Override
	public String toString() {
		return "Qna [id=" + id + ", category=" + category + ", qnaAnswerCondition=" + qnaAnswerCondition
				+ ", qnaSecretCondition=" + qnaSecretCondition + ", subject=" + subject + ", regDate=" + regDate
				+ ", content=" + content + ", qnaAnswerDate=" + qnaAnswerDate + ", hit=" + hit + ", userId=" + userId
				+ ", qnaAnswer=" + qnaAnswer + ", memberId=" + memberId + ", ipAddress=" + ipAddress + ", search="
				+ search + ", condition=" + condition + ", limitStart=" + limitStart + ", listCount=" + listCount + "]";
	}
	
}