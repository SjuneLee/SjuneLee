package study.spring.seoul4u.test.member;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MemberCountTest {
	
	@Autowired
	//private SqlSession sqlSession;
	private MemberService memberService;
	@Test
	public void testFactory() {
		
		// 조회할 데이터의 조건값 설정
		// -> import study.spring.seoul4u.model.Member;
		Member member = new Member();
		member.setUserId("test");
		member.setEmail("test@test.com");
		
		// 조회결과가 저장될 객체
		int check = 0;
		
		try {
			check = memberService.selectUserIdEmailCount(member);
			//result = sqlSession.selectOne("MemberMapper.selectUserIdEmailCount", member);
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println("데이터 조회에 실패했습니다.");
		}
		
		System.out.println(check);
	}
}
