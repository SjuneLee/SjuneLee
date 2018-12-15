package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.Member;

/** 회원 관련 기능을 제공하기 위한 Service 계층 */
public interface MemberService {
	/**
	 * 아이디 중복검사
	 * @param member - 아이디
	 * @return int
	 * @throws Exception - 중복된 데이터인 경우 예외 발생함
	 */
	public int selectUserIdCount(Member member) throws Exception;
	/**
	 * 이메일 중복검사
	 * @param member - 이메일
	 * @throws Exception - 중복된 데이터인 경우 예외 발생함
	 */
	public void selectEmailCount(Member member) throws Exception;
	/**
	 * 작성한 아이디와 이메일이 일치하는지 확인한다.
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public int selectUserIdEmailCount(Member member) throws Exception;
	/**
	 * 회원가입(아이디,이메일 중복검사 후 가입처리)
	 * @param member - 일련번호, 가입일시,변경일시를 제외한 모든 정보
	 * @throws Exception
	 */
	public void insertMember(Member member) throws Exception;
	/**
	 * 로그인시 DB에 정보가 있는지 검사
	 * @param member - 아이디, 비밀번호
	 * @return
	 * @throws Exception
	 */
	public Member selectLoginInfo(Member member) throws Exception;
	/**
	 * 관리자 페이지 로그인시 DB에 정보가 있는지 검사
	 * @param member -- 관리자 아이디, 비밀번호
	 * @return
	 * @throws Exception
	 */
	public Member selectAdminInfo(Member member) throws Exception;
	/**
	 * 아이디 찾기
	 * @param member - 전화번호로
	 * @return 
	 * @throws Exception
	 */
	public Member selectUserIdByTel(Member member) throws Exception;
	/**
	 * 비밀번호 변경
	 * @param member - 이메일 주소. 비밀번호
	 * @throws Exception
	 */
	public void updateMemberPasswordByEmail(Member member) throws Exception;
	/**
	 * 비밀번호 검사
	 * @param member
	 * @throws Exception
	 */
	public void selectMemberPasswordCount(Member member) throws Exception;
	/**
	 * 회원탈퇴
	 * @param member - 회원번호, 비밀번호
	 * @throws Exception
	 */
	public void deleteMember(Member member) throws Exception;
	/**
	 * 회원정보 수정
	 * @param member
	 * @throws Exception
	 */
	public void updateMember(Member member) throws Exception;
	/**
	 * 일련번호에 의한 회원정보 조회
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public Member selectMember(Member member) throws Exception;
	/**
	 * 회원 목록 조회
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	public List<Member> selectMemberList(Member member) throws Exception;

	/**
	 * 전체 목록 수 조회
	 * @return 조회 결과
	 * @throws Exception
	 */
	public int selectMemberCount(Member member) throws Exception;
	/**
	 * 관리자 회원삭제
	 * @param member 
	 * @throws Exception
	 */
	public void deleteMemberByAdmin(Member member) throws Exception;
}
