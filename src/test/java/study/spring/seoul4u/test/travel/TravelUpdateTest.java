package study.spring.seoul4u.test.travel;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.service.TravelDocumentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class TravelUpdateTest {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private TravelDocumentService travelDocumentService;
	@Test
	public void testFactory() {
		TravelDocument travel = new TravelDocument();
		travel.setId(9);
		int result;
		try {
			//travelDocumentService.plusLikeSum(travel);
			travelDocumentService.minusLikeSum(travel);
			// result= sqlSession.update("TravelDocumentMapper.plusLikeSum", travel);
			// result= sqlSession.update("TravelDocumentMapper.minusLikeSum", travel);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		System.out.println("수정완료");
	}
}