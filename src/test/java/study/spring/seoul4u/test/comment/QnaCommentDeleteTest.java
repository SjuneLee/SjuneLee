package study.spring.seoul4u.test.comment;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class QnaCommentDeleteTest {

	@Autowired
	private SqlSession sqlSession;
	
	@Test
	public void testFactory() {
		Comment comment = new Comment();
		comment.setId(3);
		
		try {
			/*qnaCommentService.insertQnaComment(comment);*/
			sqlSession.delete("QnaCommentMapper.deleteQnaComment", comment);
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		System.out.println("삭제완료");
	}
}
