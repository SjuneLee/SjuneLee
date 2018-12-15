package study.spring.seoul4u.test.customercenter;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Qna;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class QnaAdminTest {

	@Autowired
	private SqlSession sqlSession; // 객체주입
	
//	@Test
//	public void item() {
//		Qna qna = new Qna();
//		qna.setId(1);
//		
//		Qna result = null;
//		try {
//			result = sqlSession.selectOne("AdminQnaMapper.selectQnaItem", qna);
//			if (result == null) {
//				throw new NullPointerException();
//			}
//		} catch (NullPointerException e) {
//			System.out.println("상세조회 데이터가 없습니다.");
//		} catch (Exception e) {
//			System.out.println("데이터 상세조회에 실패했습니다.");
//		}
//		
//		System.out.println("결과 -> " + result.toString());
//	}
	
//	@Test
//	public void list() {
//		List<Qna> result = null;
//		
//		try {
//			result = sqlSession.selectList("AdminQnaMapper.selectQnaList");
//			if (result == null) {
//				throw new NullPointerException();
//			}
//		} catch (NullPointerException e) {
//			System.out.println("리스트  데이터가 없습니다.");
//		} catch (Exception e) {
//			System.out.println("리스트 조회에 실패했습니다.");
//		}
//		
//		System.out.println("리스트 결과 -> " + result.toString());
//	}
	
//	@Test
//	public void delete() {
//		Qna qna = new Qna();
//		qna.setId(3);
//		
//		try {
//			int result = sqlSession.delete("AdminQnaMapper.deleteQna", qna);
//			System.out.println("qna 삭제 성공");
//			if (result == 0) {
//				throw new NullPointerException();
//			}
//		} catch (NullPointerException e) {
//			System.out.println("삭제된 데이터가 없습니다.");
//		} catch (Exception e) {
//			System.out.println("데이터 삭제 실패했습니다.");
//		}
//	}
	
}
