package study.spring.seoul4u.test.member;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MemberSelectLoginInfoTest {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Test
	public void testFactory() {
		
		// 조회할 데이터의 조건값 설정
		// -> import study.spring.seoul4u.model.Member;
		Member member = new Member();
		member.setId(1);
		
		// 조회결과가 저장될 객체
		Member result = null;
		
		try {
			result = sqlSession.selectOne("MemberMapper.selectMember", member);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			System.out.println("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println("데이터 조회에 실패했습니다.");
		}
		
		System.out.println(result.toString());
	}
}
