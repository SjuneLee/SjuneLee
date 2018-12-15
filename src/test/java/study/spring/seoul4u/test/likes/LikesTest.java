package study.spring.seoul4u.test.likes;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.service.LikesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class LikesTest {

	@Autowired
	private SqlSession sqlSession; // 객체주입
	@Autowired
	LikesService likesService;
	
//	@Test
//	public void travel() {
//		Likes likes = new Likes();
//		likes.setMemberId(2);
//		likes.setTravelId(1);
//		int result = 0;
//		try {
//			result = likesService.selectCountByTravelId(likes);
//			//int result = sqlSession.selectOne("LikesTravelMapper.likesCountByTravelId",likes);
//			//if(result == 0) {
//			//	throw new NullPointerException();
//			//}
//		}catch(NullPointerException e) {
//			System.out.println("travel 좋아요가 없습니다.");
//		}catch(Exception e) {
//			System.out.println("travel 좋아요 조회 실패");
//		}		
//	}
	@Test
	public void deleteTravelLikes() {
		Likes likes = new Likes();
		likes.setMemberId(2);
		likes.setTravelId(2);
		try {
			likesService.deleteTravelLikes(likes);
			//sqlSession.delete("LikesTravelMapper.deleteLikes", likes);
		} catch (NullPointerException e) {
			System.out.println("travel 좋아요 삭제 없습니다.");
		} catch (Exception e) {
			System.out.println("travel 좋아요 삭제 실패");
		}
	}
//	@Test
//	public void insertTravelLikes() {
//		Likes likes = new Likes();
//		likes.setMemberId(2);
//		likes.setTravelId(1);
//		try {
//			likesService.insertTravelLikes(likes);
//			//sqlSession.insert("LikesTravelMapper.insertLikes", likes);
//		} catch (NullPointerException e) {
//			System.out.println("travel 좋아요 등록 없습니다.");
//		} catch (Exception e) {
//			System.out.println("travel 좋아요 등록 실패");
//		}
//	}
//	@Test
//	public void travelUpdate() {
//		Likes likes = new Likes();
//		likes.setId(1);
//		try {
//			int result = sqlSession.update("LikesTravelMapper.updateLikes");
//			if(result == 0) {
//				throw new NullPointerException();
//			}
//		}catch(NullPointerException e) {
//			System.out.println("travel 좋아요 수정이 없습니다.");
//		}catch(Exception e) {
//			System.out.println("travel 좋아요 수정 실패");
//		}
//	}
	
//	@Test
//	public void travelPlan() {
//		int result = -1;
//		try {
//			result = sqlSession.selectOne("LikesTravelPlanMapper.travelCount");
//			if(result == 0) {
//				throw new NullPointerException();
//			}
//		}catch(NullPointerException e) {
//			System.out.println("travelPlan 좋아요가 없습니다.");
//		}catch(Exception e) {
//			System.out.println("travelPlan 좋아요 조회 실패");
//		}
//		
//		System.out.println(result);
//	}

}
