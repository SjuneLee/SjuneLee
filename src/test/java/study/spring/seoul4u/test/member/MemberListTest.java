package study.spring.seoul4u.test.member;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class MemberListTest {

	@Autowired
	// 객체주입-> 선언만 해 놓으면 할당은 자동으로 수행된다.
	MemberService memberService;

	@Test
	public void testFactory() {
		Member member = new Member();
		// 조회 범위를 설정 -> 페이지 구현변수
		member.setLimitStart(0);
		member.setListCount(5);
		// 조회 결과를 저장하기 위한 객체
		List<Member> list = null;
		try {
			list = memberService.selectMemberList(member);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
