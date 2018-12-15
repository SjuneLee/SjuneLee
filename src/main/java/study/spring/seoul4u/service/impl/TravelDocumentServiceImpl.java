package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.service.TravelDocumentService;

@Service
public class TravelDocumentServiceImpl implements TravelDocumentService{

		/** 처리 결과를 기록할 Log4J 객체 생성 */
		// --> import org.slf4j.Logger;
		// --> import org.slf4j.LoggerFactory;
		private static Logger logger = LoggerFactory.getLogger(TravelDocumentServiceImpl.class);
	
		/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
		// Spring으로 부터 주입받는다.
		@Autowired
		SqlSession sqlSession;



		@Override
		public List<TravelDocument> selectTravelList(TravelDocument travel) throws Exception {
			List<TravelDocument> result = null;

			try {
				result = sqlSession.selectList("TravelDocumentMapper.selectTravelList",travel);
				if (result == null) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				throw new Exception("조회된 글 목록이 없습니다.");
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("글 목록 조회에 실패했습니다.");
			}

			return result;
		}

		@Override
		public List<TravelDocument> selectTravelSearch(TravelDocument travel) throws Exception {
			List<TravelDocument> result = null;

			try {
				result = sqlSession.selectList("TravelDocumentMapper.selectTravelSearch",travel);
				if (result == null) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				throw new Exception("조회된 글 목록이 없습니다.");
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("글 목록 조회에 실패했습니다.");
			}

			return result;
		}
		
		@Override
		public void insertTravel(TravelDocument travel) throws Exception {
			try {
				int result = sqlSession.insert("TravelDocumentMapper.insertTravel", travel);
				if (result == 0) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				// sqlSession.rollback();
				System.out.println("저장된 게시물이 없습니다.");
				throw new Exception("저장된 게시물이 없습니다.");
			} catch (Exception e) {
				// sqlSession.rollback();
				System.out.println("게시물 정보 등록에 실패했습니다.");
				logger.error(e.getLocalizedMessage());
				throw new Exception("게시물 정보 등록에 실패했습니다.");
			} finally {
				// sqlSession.commit();
			}
		}

		@Override
		public TravelDocument selectTravel(TravelDocument travel) throws Exception {
			TravelDocument result = null;

			try {
				result = sqlSession.selectOne("TravelDocumentMapper.selectTravel", travel);
				if (result == null) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				throw new Exception("조회된 게시물이 없습니다.");
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("게시물 조회에 실패했습니다.");
			}

			return result;
		}

		@Override
		public TravelDocument selectPrevTravel(TravelDocument travel) throws Exception {
			TravelDocument result = null;

			try {
				// 이전글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
				result = sqlSession.selectOne("TravelDocumentMapper.selectPrevTravel", travel);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("이전글 조회에 실패했습니다.");
			}

			return result;
		}

		@Override
		public TravelDocument selectNextTravel(TravelDocument travel)  throws Exception {
			TravelDocument result = null;

			try {
				// 다음글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
				result = sqlSession.selectOne("TravelDocumentMapper.selectNextTravel", travel);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("다음글 조회에 실패했습니다.");
			}

			return result;
		}

		@Override
		public void updateTravelHit(TravelDocument travel) throws Exception {
			try {
				int result = sqlSession.update("TravelDocumentMapper.updateTravelHit", travel);
				if (result == 0) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				// sqlSession.rollback();
				throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
			} catch (Exception e) {
				// sqlSession.rollback();
				logger.error(e.getLocalizedMessage());
				throw new Exception("조회수 갱신에 실패했습니다.");
			} finally {
				// sqlSession.commit();
			}
		}



		@Override
		public int selectTravelCount(TravelDocument travel) throws Exception {
			int result = 0;

			try {
				// 게시물 수가 0건인 경우도 있으므로, 
				// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
				result = sqlSession.selectOne("TravelDocumentMapper.selectTravelCount", travel);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("게시물 수 조회에 실패했습니다.");
			}

			return result;
		}


		@Override
		public void deleteTravel(TravelDocument travel) throws Exception {
			try {
				int result = sqlSession.delete("TravelDocumentMapper.deleteTravel", travel);
				if (result == 0) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				// sqlSession.rollback();
				throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
			} catch (Exception e) {
				// sqlSession.rollback();
				logger.error(e.getLocalizedMessage());
				throw new Exception("게시물 삭제에 실패했습니다.");
			} finally {
				// sqlSession.commit();
			}
		}

		@Override
		public void updateTravel(TravelDocument travel) throws Exception {
			try {
				int result = sqlSession.update("TravelDocumentMapper.updateTravel", travel);
				if (result == 0) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				// sqlSession.rollback();
				throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
			} catch (Exception e) {
				// sqlSession.rollback();
				logger.error(e.getLocalizedMessage());
				throw new Exception("게시물 수정에 실패했습니다.");
			} finally {
				// sqlSession.commit();
			}
		}

		/** 여행지 상세 페이지에서 좋아요 등록시 실행 */
		@Override
		public void plusLikeSum(TravelDocument travel) throws Exception {
			try {
				int result = sqlSession.update("TravelDocumentMapper.plusLikeSum", travel);
				if (result == 0) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				throw new Exception("좋아요 총계가 바뀐 것이 없습니다.");
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("좋아요 총계 증가에 실패했습니다.");
			}
			
		}

		/** 여행지 상세 페이지에서 종아요 취소시 실행 */
		@Override
		public void minusLikeSum(TravelDocument travel) throws Exception {
			try {
				int result = sqlSession.update("TravelDocumentMapper.minusLikeSum", travel);
				if (result == 0) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				throw new Exception("좋아요 총계가 바뀐 것이 없습니다.");
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("좋아요 총계 감소에 실패했습니다.");
			}
		}
		
		@Override
		public float selectTravelRatingAvg(TravelDocument travel) throws Exception {
			float result = 0;

			try {
				result = sqlSession.update("TravelDocumentMapper.selectTravelRatingAvg", travel);
				// 평가수가 0일 수도 있으니 Null 예외 처리는 없다.
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("해당 게시물 평점 평균 수정에 실패했습니다.");
			}

			return result;
		}
}

