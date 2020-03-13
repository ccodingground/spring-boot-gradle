package board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardFileEntity;

@Component//스프링빈으로 등록
public class JpaFileUtils {
	
	public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest mr) throws Exception {
		//첨부파일 미존재시..
		//ObjectUtils.isEmpty(mr) 비어있으면 true, 존재하면 false
		//true if the object is null or empty
		List<MultipartFile> _files=mr.getFiles("files");
		System.out.println(_files.get(0).getOriginalFilename());
		System.out.println("length:"+_files.get(0).getOriginalFilename().length());
		if(_files.size()==1 && (_files.get(0).getOriginalFilename().length())==0) {
			System.out.println("파일없음");
			return null;
		}
		//첨부파일 존재시..처리
		
		List<BoardFileEntity> fileList=new ArrayList<>();
		//파일이 저장될 폴더 날짜를 이용하여 생성
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime now=ZonedDateTime.now();
		String path="files/"+now.format(formatter);
		System.out.println("path : "+path);
		File dir=new File(path);
		if(dir.exists()==false) {
			dir.mkdir();//파일경로가 생성 되지 않았으면 디렉터리 생성
		}
		System.out.println(dir.getAbsolutePath());
		///////////////////////////////
		
		
		
		System.out.println("_files.isEmpty() : "+_files.isEmpty());//true if this list contains no elements
		System.out.println("_files.size()" +_files.size());
		for(MultipartFile file:_files) {
			
			String orgName=file.getOriginalFilename();
			System.out.println("파일이름 :"+orgName);
			String[] names=orgName.split("[.]");
			System.out.println("길이:"+names.length);
			String extension=names[names.length-1];//확장자만 저장
			System.out.println("확장자 : "+extension);
			String newName=Long.toString(System.nanoTime())+"."+extension;
			System.out.println("새로운 파일이름 : "+newName);
		
			
			BoardFileEntity dto=new BoardFileEntity();
			//dto.setB_no(b_no);//게시글 번호
			dto.setFile_name(file.getOriginalFilename());//원래 파일이름
			dto.setFile_path(path+"/"+newName);//실제저장위치
			dto.setFile_size(file.getSize());
			
			fileList.add(dto);
			
			//실제저장위치로 저장
			//업로드된 파일을 새로운 이름으로 바꾸어서 지정된 경로에 저장
			File newfile=new File(path+"/"+newName);
			file.transferTo(newfile);
		}
		
		
		/*
		Iterator<String> it=mr.getFileNames();
		while(it.hasNext()) {
			String files=it.next();
			List<MultipartFile> list=mr.getFiles(files);//파일이름으로 파일 얻어오기 
			for(MultipartFile mf:list) {
				
				// mf.getContentType()//파일의 타입을 if문을 이용해서 파일 제한 할수도 있다.
				
				String orgName=mf.getOriginalFilename();
				System.out.println("파일이름 :"+orgName);
				String[] names=orgName.split("[.]");
				System.out.println("길이:"+names.length);
				String extension=names[names.length-1];//확장자만 저장
				System.out.println("확장자 : "+extension);
				String newName=Long.toString(System.nanoTime())+"."+extension;
				System.out.println("새로운 파일이름 : "+newName);
			
				
				BoardFileEntity dto=new BoardFileEntity();
				//dto.setB_no(b_no);//게시글 번호
				dto.setFile_name(mf.getOriginalFilename());//원래 파일이름
				dto.setFile_path(path+"/"+newName);//실제저장위치
				dto.setFile_size(mf.getSize());
				
				fileList.add(dto);
				
				//실제저장위치로 저장
				//업로드된 파일을 새로운 이름으로 바꾸어서 지정된 경로에 저장
				File file=new File(path+"/"+newName);
				mf.transferTo(file);
			}
			
		}
		*/
		return fileList;
	}
}
