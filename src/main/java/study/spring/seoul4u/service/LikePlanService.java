package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.Likes;

public interface LikePlanService {
	/**
	 * 여행일정 좋아요 했을 때
	 * @param likes
	 * @throws Exception
	 */
	public void insertTravelPlanLikes(Likes likes) throws Exception;
	/**
	 * 여행일정 좋아요 표시 했는지 검사
	 * @param likes
	 * @return int
	 * @throws Exception
	 */
	public int selectCountByTravelPlanId(Likes likes) throws Exception;
	
	
	/**
	 * 여행일정 좋아요 취소
	 * @param likes
	 * @throws Exception
	 */	
	public void deleteTravelPlanLikes(Likes likes) throws Exception;
	
	/**
	 * 좋아한 일정 목록 조회 
	 * @param likes - 여행일정 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<Likes> selectLikePlanList(Likes likes) throws Exception;
	
	/**
	 * 전체 좋아한 일정 수 조회 
	 * @param likes
	 * @return int
	 * @throws Exception
	 */
	public int selectLikePlanCount(Likes likes) throws Exception;
	
	/**
	 * 좋아한 일정을 삭제한다. 
	 * @param likes
	 * @throws Exception
	 */
	public void deleteLikePlan(Likes likes) throws Exception;
	
	/**
	 * 좋아한 일정을 삭제한다. 
	 * @param likes
	 * @throws Exception
	 */
	public void deleteLikePlanAll(Likes likes) throws Exception;
	
	/**
	 * 회원 탈퇴시 좋아한 일정을 삭제한다. 
	 * @param likes
	 * @throws Exception
	 */
	public void deletePlanLikeByMemberId(Likes likes) throws Exception;
	
}
