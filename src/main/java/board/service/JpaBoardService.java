package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface JpaBoardService {

	List<BoardEntity> selectBoardList();

	void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest mr) throws Exception ;

	BoardEntity selectBoardDetail(int boardNo);

	BoardFileEntity selectBoardFileInformation(int fileNo);

	void deleteBoard(int boardNo);

}
