package study.spring.seoul4u.service.impl;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.service.LikesService;
//--> import org.springframeworklstereotype.Service;
@Service
public class LikesServiceImpl implements LikesService {
	
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
	public void insertTravelLikes(Likes likes) throws Exception {
		try {
			int result = sqlSession.insert("LikesTravelMapper.insertLikes", likes);
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
	public int selectCountByTravelId(Likes likes) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("LikesTravelMapper.likesCountByTravelId", likes);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("여행지 좋아요 검사에 실패했습니다.");
		}
		
		return result;
	}
	/** 특정 여행지에 좋아요 취소 */
	@Override
	public void deleteTravelLikes(Likes likes) throws Exception {
		try {
			int result = sqlSession.delete("LikesTravelMapper.deleteLikes", likes);
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
	
	@Override
	public List<Likes> selectClipList(Likes likes) throws Exception {
		List<Likes> result = null;

		try {
			result = sqlSession.selectList("LikesTravelMapper.selectClipList", likes );
			System.out.println("리스트 결과는" + result);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("클립보드 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("클립보드 목록 조회에 실패했습니다.");
		}

		return result;
	}
	@Override
	public int selectClipCount(Likes likes) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로, 
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("LikesTravelMapper.selectClipCount", likes);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("클립보드 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void deleteClip(Likes likes) throws Exception {
		try {
			int result = sqlSession.delete("LikesTravelMapper.deleteClip", likes);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("존재하지 않는 클립보드에 대한 요청입니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("클립보드 삭제에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}
	}
	
	@Override
	public void deleteClipAll(Likes likes) throws Exception {
		try {
			// 첨부파일이 없는 경우도 있으므로, 결과가 0인 경우 예외를 발생하지 않는다.
			sqlSession.delete("LikesTravelMapper.deleteClipAll", likes);
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 클립보드 삭제에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}
	}
	
	@Override
	public void deleteClipByMemberId(Likes likes) throws Exception {
		try {
			sqlSession.delete("LikesTravelMapper.deleteClipByMemberId", likes);
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 클립보드 삭제에 실패했습니다.");
		}
		
	}
	
}
