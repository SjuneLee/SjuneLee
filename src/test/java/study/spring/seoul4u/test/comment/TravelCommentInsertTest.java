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
public class TravelCommentInsertTest {

	@Autowired
	SqlSession sqlSession;
	@Autowired
	TravelCommentService travelCommentService;
	@Test
	public void testFactory() {
		Comment comment = new Comment();
		comment.setContent("내용");
		comment.setRating(4);
		comment.setIpAddress("test");
		comment.setMemberId(1);
		comment.setTravelId(1);
		
		try {
			travelCommentService.insertTravelComment(comment);
			//sqlSession.insert("TravelCommentMapper.insertTravelComment", comment);
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		System.out.println("입력완료");
	}
}
