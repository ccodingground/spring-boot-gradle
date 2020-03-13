package board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.service.JpaBoardService;

@Controller
public class JpaBoardController {
	
	@Autowired
	private JpaBoardService jpaBoardService;
	
	@RequestMapping(value = "/jpa/board", method = RequestMethod.GET)
	public ModelAndView boardList() {
		System.out.println("JapBoardController : boardList() 실행");
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/board/jpaBoardList");
		// .html 확장자를 쓰지않아도 
		//	Thymeleaf에 의해  templates 폴더아래 있는 
		//  boardList.html 파일을 의미한다.
		
		List<BoardEntity> list=jpaBoardService.selectBoardList();
		//System.out.println(list);
		//System.out.println("list 사이즈 : "+ list.size());
		//if(list.size()!=0)
		mv.addObject("list", list);
		//게시글 정보
		
		return mv;
	}
	
	//글쓰기 페이지 이동
	@GetMapping("/jpa/board/write")
	public String openBoardWrite() {
		return "/board/jpaBoardWrite";
	}
	//
	@RequestMapping(value =  "/jpa/board/write", method = RequestMethod.POST)
	public String boardWrite(BoardEntity boardEntity, MultipartHttpServletRequest mr) throws Exception {
		jpaBoardService.saveBoard(boardEntity, mr);
		return "redirect:/jpa/board";
	}
	
	@GetMapping("/jpa/board/{boardNo}")
	public ModelAndView openBoardDetail(@PathVariable("boardNo") int boardNo) {
		ModelAndView mv=new ModelAndView("/board/jpaBoardDetail");
		
		BoardEntity board=jpaBoardService.selectBoardDetail(boardNo);
		mv.addObject("detail", board);
		
		return mv;
	}
	//다운로드
	@GetMapping("/jpa/board/file")//fileNo=${list.fileNo}
	public void downloadBoardFile(@RequestParam int fileNo, HttpServletResponse response) throws Exception {
		BoardFileEntity file=jpaBoardService.selectBoardFileInformation(fileNo);
		
		byte[] files=FileUtils.readFileToByteArray(new File(file.getFile_path()));
		response.setContentType("application/octet-stream");
		response.setContentLength(files.length);
		response.setHeader("Content-Disposition",
				"attachment; filename=\""+URLEncoder.encode(file.getFile_name(),"UTF-8")+"\";");
		
		response.getOutputStream().write(files);//데이터 전송
		response.getOutputStream().flush();//버퍼 비우기
		response.getOutputStream().close(); 
	}
	//수정하기
	//@RequestMapping(value = "/jpa/board/{boardNo}", method = RequestMethod.PATCH)
	@PutMapping("/jpa/board/{boardNo}")
	public String updateBoard(BoardEntity _board) throws Exception {
		System.out.println("수정처리");
		BoardEntity board=jpaBoardService.selectBoardDetail(_board.getBoardNo());
		board.setTitle(_board.getTitle());
		board.setContent(_board.getContent());
		System.out.println("수정정보:"+board);
		jpaBoardService.saveBoard(board, null);;
		return "redirect:/jpa/board";
	}
	
	//삭제하기
	@DeleteMapping("/jpa/board/{boardNo}")
	public String deleteBoard(@PathVariable("boardNo") int boardNo) throws Exception {
		jpaBoardService.deleteBoard(boardNo);
		return "redirect:/jpa/board";
	}
}










