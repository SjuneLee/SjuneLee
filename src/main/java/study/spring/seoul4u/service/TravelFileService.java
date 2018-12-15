package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.TravelFile;


public interface TravelFileService {
	/**
	 * 파일 정보를 저장한다.
	 * @param file - 파일 데이터
	 * @throws Exception
	 */
	public void insertFile(TravelFile file) throws Exception;
	
	/**
	 * 하나의 게시물에 종속된 파일 목록을 조회한다.
	 * @param file - 게시물 일련번호를 저장하고 있는 JavaBeans
	 * @return 파일데이터 컬렉션
	 * @throws Exeption
	 */
	public List<TravelFile> selectFileList(TravelFile file) throws Exception;
	
	/**
	 * 하나의 게시물에 종속된 파일 목록을 삭제한다.
	 * @param travel
	 * @throws Exception
	 */
	public void deleteFileAll(TravelFile file) throws Exception;
	
	/**
	 * 하나의 단일 파일에 대한 정보를 조회한다.
	 * @param document
	 * @return TravelFile - 저장된 경로 정보
	 * @throws Exception
	 */
	public TravelFile selectFile(TravelFile file) throws Exception;
	
	/**
	 * 하나의 단일 파일 정보를 삭제한다.
	 * @param travel
	 * @throws Exception
	 */
	public void deleteFile(TravelFile file) throws Exception;
}
