package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.Comment;

public interface TravelPlanCommentService {

	/**
	 * travel_plan comment 리스트 조회
	 * @throws Exception
	 */
	public List<Comment> selectTravelPlanCommentList(Comment comment) throws Exception;
	/**
	 * Comment Insert
	 * @param comment 
	 * @throws Exception
	 */
	public void insertTravelPlanComment(Comment comment) throws Exception;
	/**
	 * Comment 단일행조회
	 * @param comment 
	 * @throws Exception
	 */
	public Comment selectTravelPlanCommentItem(Comment comment) throws Exception;
	/**
	 * 자신의 덧글인지 검사한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public int selectPlanCommentCountByMemberId(Comment comment) throws Exception;
	/**
	 * 비밀번호를 검사한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public int selectPlanCommentCountByPw(Comment comment) throws Exception;
	/**
	 * 특정 여행지에 대한 댓글(평가) 수를 조회한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public int selectPlanCountByTravelPlanId(Comment comment) throws Exception;
	/**
	 * 특정 회원이 특정 여행 플랜을 평가했는지 검사한다.
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public int commentCountByMemberIdTravelPlanId(Comment comment) throws Exception;
	/**
	 * 특정 여행지에 대한 평점 평균을 조회한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public float selectRatingAvgByTravelPlanId(Comment comment) throws Exception;
	/**
	 * Comment 수정
	 * @param comment 
	 * @throws Exception
	 */
	public void updateTravelPlanComment(Comment comment) throws Exception;
	
	/**
	 * Comment 삭제
	 * @param comment 
	 * @throws Exception
	 */
	public void deleteTravelPlanComment(Comment comment) throws Exception;
	
	/**
	 * 특정 게시물에 속한 모든 댓글 삭제
	 * @param comment 
	 * @throws Exception
	 */
	public void deleteTravelPlanCommentAll(Comment comment) throws Exception;
	
	/**
	* 회원과 여행일정댓글에 참조관계를 해제한다.
	* @return TravelPlan -댓글 데이터
	* @throws Exception
	*/
	public void updatePlanCommentMamberOut(Comment comment) throws Exception;
	
}
