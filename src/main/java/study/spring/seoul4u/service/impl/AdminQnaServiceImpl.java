package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.Qna;
import study.spring.seoul4u.service.AdminQnaService;

// --> import org.springframeworklstereotype.Service;
@Service
public class AdminQnaServiceImpl implements AdminQnaService {
	
	/** 처리 결과를 기록할 Log4J 객체 생성 */
	// --> import org.slf4j.Logger;
	// --> import org.slf4j.LoggerFactory;
	private static Logger logger = LoggerFactory.getLogger(AdminQnaServiceImpl.class);
	
	/** MyBatis */
	// --> import org.springframework.beans.factory.annotation.Autowired;
	// --> import org.apache.ibatis.session.SqlSession
	@Autowired
	SqlSession sqlSession;
	
	/** Qna 목록 조회 */
	@Override
	public List<Qna> selectQnaList(Qna qna) throws Exception {
		
/*		Qna item = new Qna();
		item.setCategory("qna");*/
		
		List<Qna> result = null;		
		
		try {
			result  = sqlSession.selectList("AdminQnaMapper.selectQnaList", qna);
			System.out.println("여기까지 왔지롱 그리고 리스트는=" + result);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("QNA 리스트 조회결과가 없습니다.");
		}catch(Exception e) {
			throw new Exception("QNA 리스트 조회 실패!!");
		}
		System.out.println("result 결과는" + result);
		return result;
	}
	
	@Override
	public int selectQnaCount(Qna qna) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로, 
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("AdminQnaMapper.selectQnaCount", qna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			System.out.println("페이지네이션");
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}
	
	/** Qna 제목 클릭시 상세정보 조회 */
	@Override
	public Qna selectQnaItem(Qna qna) throws Exception {
		Qna result = null;
		try {
			result = sqlSession.selectOne("AdminQnaMapper.selectQnaItem", qna);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("QNA 상세보기 조회결과가 없습니다.");
		}catch(Exception e) {
			throw new Exception("QNA 상세보기 조회 실패ㅜㅜ");
		}
		
		return result;
	}
	
	/** qna 게시글 삭제  */
	@Override
	public void deleteAdminQna(Qna qna) throws Exception {
		try {
			int result = sqlSession.delete("AdminQnaMapper.deleteQna", qna);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			System.out.println("QNA 삭제할 데이터가 없습니다.");
			throw new Exception("QNA 삭제할 데이터가 없습니다.");
		}catch(Exception e) {
			System.out.println("QNA 데이터 삭제 실패..");
			throw new Exception("QNA 데이터 삭제 실패..");
		}
		
	}
	
	/** qna 게시글 수정 */
	@Override
	public void updateAminQna(Qna qna) throws Exception {
		try {
			int result = sqlSession.update("AdminQnaMapper.editQna", qna);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			System.out.println("QNA 수정할 데이터가 없습니다.");
			throw new Exception("QNA 수정할 데이터가 없습니다.");
		}catch(Exception e) {
			System.out.println("QNA 데이터 수정 실패..");
			throw new Exception("QNA 데이터 수정 실패..");
		}
		
	}

	@Override
	public Qna selectPrevQna(Qna qna) throws Exception {
		Qna result = null;

		try {
			// 이전글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("AdminQnaMapper.selectPrevQna", qna);
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
			result = sqlSession.selectOne("AdminQnaMapper.selectNextQna", qna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("다음글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectAdminQnaCount(Qna qna) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("AdminQnaMapper.selectAdminQnaCount", qna);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}
		return result;
	}
	
	
}