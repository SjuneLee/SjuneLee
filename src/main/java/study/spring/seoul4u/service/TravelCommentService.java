package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.Comment;

public interface TravelCommentService {

	/**
	 * QNA comment 리스트 조회
	 * @throws Exception
	 */
	public List<Comment> selectTravelCommentList(Comment comment) throws Exception;
	
	/**
	 * Comment Insert
	 * @param comment 
	 * @throws Exception
	 */
	public void insertTravelComment(Comment comment) throws Exception;
	/**
	 * Comment 단일행조회
	 * @param comment 
	 * @throws Exception
	 */
	public Comment selectTravelComment(Comment comment) throws Exception;
	/**
	 * 자신의 덧글인지 검사한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public int selectTravelCommentCountByMemberId(Comment comment) throws Exception;
	/**
	 * 비밀번호를 검사한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public int selectTravelCommentCountByPw(Comment comment) throws Exception;
	/**
	 * 특정 여행지에 대한 댓글(평가) 수를 조회한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public int selectCommentCountByTravelId(Comment comment) throws Exception;
	/**
	 * 특정 여행지에 특정 사용자가 댓글을 달았는지 검사한다.
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public int commentCountByMemberIdTravelId(Comment comment) throws Exception;
	/**
	 * 특정 여행지에 대한 평점 평균을 조회한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public float selectRatingAvgByTravelId(Comment comment) throws Exception;
	/**
	 * Comment 수정
	 * @param comment 
	 * @throws Exception
	 */
	public void updateTravelComment(Comment comment) throws Exception;
	
	/**
	 * Comment 덧글 삭제
	 * @param comment 
	 * @throws Exception
	 */
	public void deleteTravelComment(Comment comment) throws Exception;
	/**
	 * 특정 게시물에 속한 모든 덧글을 삭제한다.
	 * @param comment 
	 * @throws Exception
	 */
	public void deleteCommentAll(Comment comment) throws Exception;
	
}
