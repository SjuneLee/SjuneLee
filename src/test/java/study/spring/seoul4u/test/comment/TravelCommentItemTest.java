package study.spring.seoul4u.test.comment;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Comment;
import study.spring.seoul4u.service.TravelCommentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class TravelCommentItemTest {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private TravelCommentService travelCommentService;
	@Test
	public void testFactory() {
		Comment comment = new Comment();
		comment.setTravelId(1);
		//Comment result = null;
		float result = 0;
		try {
			result = travelCommentService.selectRatingAvgByTravelId(comment);
			//result = sqlSession.selectOne("TravelCommentMapper.selectRatingAvgByTravelId", comment);
			//if (result == null) {
			//	throw new NullPointerException();
			//}
		} catch (NullPointerException e) {
			System.out.println("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			System.out.println("데이터 조회에 실패했습니다.");
		}
		//System.out.println(result.toString());
		System.out.println(result);
	}

}