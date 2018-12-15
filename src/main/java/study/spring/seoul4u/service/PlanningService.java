package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.FileTravelComment;
import study.spring.seoul4u.model.PlanTravelFileJoinByDetail;

public interface PlanningService {
	/**
	 * planning2.jsp의 여행지 정보에서 사용한다.
	 * @param fileTravelComment
	 * @return List-FileTravelComment
	 * @throws Exception
	 */
	public List<FileTravelComment> listForPlanning(FileTravelComment fileTravelComment) throws Exception;
	/**
	 * plan_info.jsp 페이지에서 사용한다.
	 * @param joinResult
	 * @return
	 * @throws Exception
	 */
	public List<PlanTravelFileJoinByDetail> listForPlanInfo(PlanTravelFileJoinByDetail joinResult) throws Exception;
}
