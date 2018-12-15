package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.service.LikePlanService;
@Service
public class LikePlanServiceImpl implements LikePlanService{
	/** 처리 결과를 기록할 Log4J 객체 생성 */
	// --> import org.slf4j.Logger;
	// --> import org.slf4j.LoggerFactory;
	private static Logger logger = LoggerFactory.getLogger(LikesServiceImpl.class);
	
	/** MyBatis */
	// --> import org.springframework.beans.factory.annotation.Autowired;
	// --> import org.apache.ibatis.session.SqlSession
	@Autowired
	SqlSession sqlSession;
	/** 특정 여행지에 좋아요 등록 */
	@Override
	public void insertTravelPlanLikes(Likes likes) throws Exception {
		try {
			int result = sqlSession.insert("LikesTravelPlanMapper.insertLikes", likes);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("여행지 좋아요 등록 결과 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("여행지 좋아요 등록 실패했습니다.");
		}
	}
	/** 특정 여행지에 좋아요 했는지 검사 */
	@Override
	public int selectCountByTravelPlanId(Likes likes) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("LikesTravelPlanMapper.likesCountBytravelPlanId", likes);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("여행지 좋아요 검사에 실패했습니다.");
		}
		
		return result;
	}
	/** 특정 여행지에 좋아요 취소 */
	@Override
	public void deleteTravelPlanLikes(Likes likes) throws Exception {
		try {
			int result = sqlSession.delete("LikesTravelPlanMapper.deleteLikes", likes);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("여행지 좋아요 삭제 결과 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("여행지 좋아요 삭제 실패 했습니다.");
		}
	}
	/** 좋아한일정 목록 */
	@Override
	public List<Likes> selectLikePlanList(Likes likes) throws Exception {
		List<Likes> result = null;

		try {
			result = sqlSession.selectList("LikesTravelPlanMapper.selectLikePlanList", likes );
			System.out.println("리스트 결과는" + result);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("좋아한 일정 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아한 일정 목록 조회에 실패했습니다.");
		}

		return result;
	}
	/**좋아한 일정 수 조회*/
	@Override
	public int selectLikePlanCount(Likes likes) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로, 
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("LikesTravelPlanMapper.selectLikePlanCount", likes);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아한 일정 수 조회에 실패했습니다.");
		}

		return result;
	}
	/**좋아한 일정 목록에서 삭제*/
	@Override
	public void deleteLikePlan(Likes likes) throws Exception {
		try {
			int result = sqlSession.delete("LikesTravelPlanMapper.deleteLikePlan", likes);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {			
			throw new Exception("존재하지 않는 좋아한 일정에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아한 일정 삭제에 실패했습니다.");
		}
		
	}
	
	@Override
	public void deleteLikePlanAll(Likes likes) throws Exception {
		try {
			// 첨부파일이 없는 경우도 있으므로, 결과가 0인 경우 예외를 발생하지 않는다.
			sqlSession.delete("LikesTravelPlanMapper.deleteLikePlanAll", likes);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 좋아한 일정 삭제에 실패했습니다.");
		}
		
	}
	
	@Override
	public void deletePlanLikeByMemberId(Likes likes) throws Exception {
		try {
			// 첨부파일이 없는 경우도 있으므로, 결과가 0인 경우 예외를 발생하지 않는다.
			sqlSession.delete("LikesTravelPlanMapper.deletePlanLikeByMemberId", likes);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 좋아한 일정 삭제에 실패했습니다.");
		}
		
	}
	
}
