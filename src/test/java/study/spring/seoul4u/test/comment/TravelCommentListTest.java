package study.spring.seoul4u.test.comment;

import java.util.List;

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
public class TravelCommentListTest {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private TravelCommentService travelCommentService;
	@Test
	public void testFactory() {
		List<Comment> list = null;
		Comment travelComment = new Comment();
		travelComment.setTravelId(2);
		
		try {
			list = travelCommentService.selectTravelCommentList(travelComment);
			/*list = sqlSession.selectList("TravelCommentMapper.selectTravelCommentList", travelComment);*/
			if(list == null) {
				throw new NullPointerException();
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
