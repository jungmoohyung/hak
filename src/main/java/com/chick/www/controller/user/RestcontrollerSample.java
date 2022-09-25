package com.chick.www.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chick.dto.Board;
import com.chick.dto.Criteria;
import com.chick.dto.Member;
import com.chick.service.BoardService;

@RestController
@RequestMapping("/ex")
public class RestcontrollerSample {
//	@Contorller와 ResponseEntity를 합친게 REST 리턴타입은 뷰X 자원
//	lombok
	private static final Logger log = LoggerFactory.getLogger(RestcontrollerSample.class);
	
	@Inject
	BoardService bs;
	
	
	
	//produces = 해당 메소드가 생산하는 MIME 타입 => 문자열로 지정, MediaType 클래스로 지정
	@GetMapping(value="getText",produces = "text/plain; charset=utf-8")
	public String getText() {
		log.info("MIME TYPE : "+ MediaType.TEXT_PLAIN_VALUE);
		return "반갑습니다.";
	}
//	객체 반환 : jackson-databind, jackson - dataformat-xml
//	자바객체를 json 타입의 문자열로 변환 : 
	@GetMapping(value="getBoard", 
				produces= {MediaType.APPLICATION_JSON_UTF8_VALUE,
							MediaType.APPLICATION_XML_VALUE})
	public Board getBoard() {
		return bs.searchBoard("129");
		
	}
	
	//컬렉션 타입 객체 반환
	@GetMapping(value="getList")
	public List<Board> getList(){
		Criteria cri = new Criteria(1,5);
		return bs.list(cri);
	}
	
	@GetMapping(value="getMap")
	public Map<String, Board> getMap(){
		Map<String, Board> map = new HashMap<String, Board>();
		map.put("First", bs.searchBoard("129"));
		map.put("Second", bs.searchBoard("128"));
		return map;
	}
	
	//ResponseEntity 타입 => 요점 데이터가 정상인지 비정상인지 구분, 헤더에 상태코드값 전달 가능
	//메소드 매개변수 타입이 기본 데이터타입(int, short, boolean, ...)은 사용불가
	@GetMapping(value="check", params= {"page","record"})
	public ResponseEntity<List<Board>> check(Integer page, Integer record){
		List <Board>BoardList = bs.list(new Criteria(page,record));
		ResponseEntity<List<Board>> result = null;
		if(page < 1 || record > 100) {
			result = (ResponseEntity<List<Board>>) ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(BoardList);
		} else {
			result = (ResponseEntity<List<Board>>) ResponseEntity.status(HttpStatus.OK).body(BoardList);
		}
		return result;
	}
	
	//파라미터가 url경로에 포함된 경우 : @PathVariable
	//Http://localhost:8080/board/43/page/1/record/5/title/검색어
	@GetMapping("board/page/{pno}/record/{rno}/{key}/{value}")
	public List<Board> getSearchBoard(@PathVariable("pno") Integer currpage,
			@PathVariable("rno") Integer rowPerPage,
			@PathVariable("key") String searchItem,
			@PathVariable("value") String searchValue){
		
		Criteria cri = new Criteria(currpage,rowPerPage);
		cri.setKey(searchItem);
		cri.setCategory(searchValue);
		List <Board> BoardList = bs.list(cri);
		return BoardList;
	}
	
	//@RequestBody : 요청 메시지를 Content-type 헤더에 지정된 값에 따라
	//HttpMessageConvert를 사용해서 요청메시지를 반환함
	// - ByteArrayMessageConverter : 바이트 배열로 변환
	// - StringHttpMessageConverter : String 타입으로 변환
	// - FromHttpMessageConverter : application/x-www-form-urlencoded
	//   로 읽어서 MulitvalueMap<String, String> 으로 변환
	//	 MulitValueMap<String,String> 을 application/x-www-form-urlencoded 또는
	//	 multipart/form-data 메시지로 변환
	@PostMapping("member")
	public Member convert(@RequestBody Member member) {
			log.info("convert..member"+member);
		return member;
	}
}
