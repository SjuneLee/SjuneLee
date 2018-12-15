package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.Likes;

public interface LikesService {
	
	/**
	 * 여행지 좋아요 했을 때
	 * @param likes
	 * @throws Exception
	 */
	public void insertTravelLikes(Likes likes) throws Exception;
	
	/**
	 * 여행지 좋아요 표시 했는지 검사
	 * @param likes
	 * @return int
	 * @throws Exception
	 */
	public int selectCountByTravelId(Likes likes) throws Exception;
	
	
	/**
	 * 여행지 좋아요 취소
	 * @param likes
	 * @throws Exception
	 */	
	public void deleteTravelLikes(Likes likes) throws Exception;
	
	/**
	 * 클립보드 목록 조회
	 * 
	 * @param likes - 여행지 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<Likes> selectClipList(Likes likes) throws Exception;
	
	/**
	 * 전체 클립보드 수 조회
	 * 
	 * @param likes
	 * @return int
	 * @throws Exception
	 */
	public int selectClipCount(Likes likes) throws Exception;
	
	/**
	 * 클립보드를 삭제한다.
	 * 
	 * @param likes
	 * @throws Exception
	 */
	public void deleteClip(Likes likes) throws Exception;
	
	/**
	 * 클립보드를 삭제한다.
	 * 
	 * @param likes
	 * @throws Exception
	 */
	public void deleteClipAll(Likes likes) throws Exception;
	
	/**
	 * 회원탈퇴시 클립보드를 삭제한다. 
	 * @param likes
	 * @throws Exception
	 */
	public void deleteClipByMemberId(Likes likes) throws Exception;
	
}
