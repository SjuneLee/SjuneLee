package study.spring.seoul4u.test.member;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MemberItemTest {

	@Autowired
	private SqlSession sqlSession; // 객체주입
	
	@Test
	public void testFactory() {
		Member member = new Member();
		member.setId(2);
		
		Member result = null;
		
		try {
			result = sqlSession.selectOne("MemberMapper.selectMember", member);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			System.out.println("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			System.out.println("데이터 조회에 실패했습니다.");
		}
		System.out.println(result.toString());
	}
}
