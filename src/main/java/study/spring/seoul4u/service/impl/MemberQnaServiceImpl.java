package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.Qna;
import study.spring.seoul4u.service.MemberQnaService;

// --> import org.springframeworklstereotype.Service;
@Service
public class MemberQnaServiceImpl implements MemberQnaService {

	/** 처리 결과를 기록할 Log4J 객체 생성 */
	// --> import org.slf4j.Logger;
	// --> import org.slf4j.LoggerFactory;
	private static Logger logger = LoggerFactory.getLogger(MemberQnaServiceImpl.class);

	/** MyBatis */
	// --> import org.springframework.beans.factory.annotation.Autowired;
	// --> import org.apache.ibatis.session.SqlSession
	@Autowired
	SqlSession sqlSession;

	/** Qna 목록 조회 */
	@Override
	public List<Qna> selectQnaList(Qna qna) throws Exception {
		List<Qna> result = null;

		try {
			result = sqlSession.selectList("MemberQnaMapper.selectMemberQnaList", qna);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("고객 QNA 리스트 조회결과가 없습니다.");
		} catch (Exception e) {
			throw new Exception("고객 QNA 리스트 조회 실패!!");
		}
		System.out.println("result 결과는" + result);
		return result;
	}

	/** Qna 제목 클릭시 상세정보 조회 */
	@Override
	public Qna selectQnaItem(Qna qna) throws Exception {
		Qna result = null;
		try {
			result = sqlSession.selectOne("MemberQnaMapper.selectMemberQnaItem", qna);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("QNA 상세보기 조회결과가 없습니다.");
		} catch (Exception e) {
			throw new Exception("QNA 상세보기 조회 실패ㅜㅜ");
		}

		return result;
	}

	/** 고객센터에서 고객의 질문 등록 */
	@Override
	public void insertMemberQna(Qna qna) throws Exception {
		int result;
		try {
			result = sqlSession.insert("MemberQnaMapper.insertMemberQna", qna);
			System.out.println("글추가=" + result);
			if (result == 0) {
				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			System.out.println("QNA 고객센터 글 등록 결과가 없습니다.");
			throw new Exception("QNA 고객센터 글 등록 결과가 없습니다.");
		} catch (Exception e) {
			System.out.println("QNA 고객센터 글 등록 실패ㅜㅜ");
			throw new Exception("QNA 고객센터 글 등록 실패ㅜㅜ");
		}
	}

	/** 고객센터 qna글 수정 */
	@Override
	public void editMemberQna(Qna qna) throws Exception {
		int result;
		try {
			result = sqlSession.update("MemberQnaMapper.updateMemberQna", qna);
			System.out.println("글추가=" + result);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			System.out.println("QNA 고객센터 글 수정 결과가 없습니다.");
			throw new Exception("QNA 고객센터 글 수정 결과가 없습니다.");
		} catch (Exception e) {
			System.out.println("QNA 고객센터 글 수정 실패ㅜㅜ");
			throw new Exception("QNA 고객센터 글 수정 실패ㅜㅜ");
		}
	}

	/** 고객센테 qna글 삭제 */
	@Override
	public void deleteMemberQna(Qna qna) throws Exception {
		try {
			int result = sqlSession.delete("MemberQnaMapper.deleteMemberQna", qna);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			System.out.println("삭제할 데이터가 없습니다.");
			throw new Exception("삭제할 데이터가 없습니다.");
		} catch (Exception e) {
			System.out.println("QNA 고객센터 글 삭제 실패");
			throw new Exception("QNA 고객센터 글 삭제 실패ㅜㅜ");
		}
	}

	/** 조회수 증가 **/
	@Override
	public void updateMemberQnaHit(Qna qna) throws Exception {
		try {
			int result = sqlSession.update("MemberQnaMapper.updateMemberQnaHit", qna);
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
	public int selectMemberQnaCount(Qna qna) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("MemberQnaMapper.selectMemberQnaCount", qna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public Qna selectPrevQna(Qna qna) throws Exception {
		Qna result = null;

		try {
			// 이전글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("MemberQnaMapper.selectPrevQna", qna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("이전글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public Qna selectNextQna(Qna qna) throws Exception {
		Qna result = null;

		try {
			// 다음글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("MemberQnaMapper.selectNextQna", qna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("다음글 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public void updateQnaByMamberOut(Qna qna) throws Exception {
		try {
			//게시글을 작성한 적이 없는 회원도 있을 수 있기 때문에,
			//NullPointException을 발생시키지 않는다.
			sqlSession.update("MemberQnaMapper.updateQnaByMamberOut", qna);
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
		
	}
	@Override
	public int selectCountByMemberId(Qna qna) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("MemberQnaMapper.selectCountByMemberId", qna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원 수 조회에 실패했습니다.");
		}
		return result;
	}

}