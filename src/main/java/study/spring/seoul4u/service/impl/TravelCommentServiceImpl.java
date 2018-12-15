package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.Comment;
import study.spring.seoul4u.service.TravelCommentService;

@Service
public class TravelCommentServiceImpl implements TravelCommentService {

	private static Logger logger = LoggerFactory.getLogger(TravelCommentServiceImpl.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<Comment> selectTravelCommentList(Comment comment) throws Exception {
		List<Comment> result = null;
		try {
			result = sqlSession.selectList("TravelCommentMapper.selectTravelCommentList", comment);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("TravelComment 리스트 조회결과가 없습니다.");
		}catch(Exception e) {
			throw new Exception("TravelComment 리스트 조회 실패");
		}
		return result;
	}
	@Override
	public void insertTravelComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.insert("TravelCommentMapper.insertTravelComment", comment);
			if (result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("저장된 여행지 덧글이 없습니다.");
		}catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("여행지 덧글 등록에 실패했습니다");
		}		
	}
	@Override
	public Comment selectTravelComment(Comment comment) throws Exception {
		Comment result = null;

		try {
			result = sqlSession.selectOne("TravelCommentMapper.selectTravelComment", comment);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 덧글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 조회에 실패했습니다.");
		}
		return result;
	}
	@Override
	public void updateTravelComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.update("TravelCommentMapper.updateTravelComment", comment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {			
			throw new Exception("존재하지 않는 덧글에 대한 요청입니다.");
		} catch (Exception e) {			
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 수정에 실패했습니다.");
		} 
	}
	@Override
	public void deleteTravelComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.delete("TravelCommentMapper.deleteTravelComment", comment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {			
			throw new Exception("존재하지 않는 덧글에 대한 요청입니다.");
		} catch (Exception e) {			
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 삭제에 실패했습니다.");
		} 
	}
	@Override
	public int selectTravelCommentCountByMemberId(Comment comment) throws Exception {
		int result = 0;
		
		try {
			// 자신의 덧글이 아닌 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne(
					"TravelCommentMapper.selectTravelCommentCountByMemberId", comment);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 수 조회에 실패했습니다.");
		}
		
		return result;
	}
	@Override
	public int selectTravelCommentCountByPw(Comment comment) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.selectOne(
					"TravelCommentMapper.selectTravelCommentCountByPw", comment);
			// 비밀번호가 일치하는 데이터의 수가 0이라면 비밀번호가 잘못된 것으로 간주한다.
			if (result < 1) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("비밀번호를 확인하세요.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}
		
		return result;
	}
	@Override
	public int selectCommentCountByTravelId(Comment comment) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.selectOne(
					"TravelCommentMapper.selectCommentCountByTravelId", comment);
			// 평가수가 0일 수도 있으니 Null 예외 처리는 없다.
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 게시물 댓글 수 조회에 실패했습니다.");
		}
		
		return result;
	}
	@Override
	public int commentCountByMemberIdTravelId(Comment comment) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne(
					"TravelCommentMapper.commentCountByMemberIdTravelId", comment);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 게시물 해당 회원 댓글 여부 검사에 실패했습니다.");
		}
		
		return result;
	}
	@Override
	public float selectRatingAvgByTravelId(Comment comment) throws Exception {
		float result = 0;
		
		try {
			result = sqlSession.selectOne(
					"TravelCommentMapper.selectRatingAvgByTravelId", comment);
			// 평가수가 0일 수도 있으니 Null 예외 처리는 없다.
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 게시물 평점 평균 조회에 실패했습니다.");
		}
		
		return result;
	}
	@Override
	public void deleteCommentAll(Comment comment) throws Exception {
		try {
			sqlSession.delete("TravelCommentMapper.deleteCommentAll", comment);
			// 평가수가 0일 수도 있으니 Null 예외 처리는 없다.
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 삭제에 실패했습니다.");
		}
		
	}
}