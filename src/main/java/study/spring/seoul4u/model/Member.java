package study.spring.seoul4u.model;
	//회원 자바빈스
public class Member {
	
	private int id;
	private String name;
	private String userId;
	private String userPw;
	private String email;
	private String birthdate;
	private String tel;
	private String profileImg;
	private String regDate;
	private String editDate;
	private String gender;
	private String isAdmin;
	// 회원목록 페이지네이션 변수 : 관리자페이지 회원관리에 사용
	private int limitStart;
	private int listCount;
	
	// 추가된 멤버변수 : 회원정보 수정에서 사용 (새 비밀번호, 새 비밀번호 확인)
	private String newUserPw;
	private String newUserPwRe;
	
	private String search;
	private String date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
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
	public String getNewUserPw() {
		return newUserPw;
	}
	public void setNewUserPw(String newUserPw) {
		this.newUserPw = newUserPw;
	}
	public String getNewUserPwRe() {
		return newUserPwRe;
	}
	public void setNewUserPwRe(String newUserPwRe) {
		this.newUserPwRe = newUserPwRe;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", userId=" + userId + ", userPw=" + userPw + ", email=" + email
				+ ", birthdate=" + birthdate + ", tel=" + tel + ", profileImg=" + profileImg + ", regDate=" + regDate
				+ ", editDate=" + editDate + ", gender=" + gender + ", isAdmin=" + isAdmin + ", limitStart="
				+ limitStart + ", listCount=" + listCount + ", newUserPw=" + newUserPw + ", newUserPwRe=" + newUserPwRe
				+ ", search=" + search + ", date=" + date + "]";
	}
	

	
}
