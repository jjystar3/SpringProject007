package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

//자식 클래스들 중 서비스로 지정
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository repository; //리파지토리를 빈으로 주입
	
	@Override
	public int register(BoardDTO dto) {
		
		// 전달받은 DTO를 엔티티로 전환
		Board entity = dtoToEntity(dto);

		// 엔티티를 테이블에 저장
		repository.save(entity);
		
		// 저장 후 게시물의 번호를 반환
		return entity.getNo();
	}

	@Override
	public List<BoardDTO> getList() {
		
		// 데이터베이스에서 게시물 목록 가져오기
		List<Board> result = repository.findAll();
		
		// 엔티티 리스트를 DTO 리스트로 변환하기
		List<BoardDTO> list = new ArrayList<>();
		
		// 리스트에서 스트림 생성하기
		// 스트림을 사용하여 모든 엔티티를 DTO로 변환
		// 함수형 인터페이스는 람다식 함수로 구현한다
		// collect: 스트림을 다른 자료구조로 변환하는 함수
		list = result.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());

		// 변환한 DTO 리스트 반환
        return list;
	}

	@Override
	public BoardDTO read(int no) {

		// 게시물 번호로 글 조회
		Optional<Board> optional = repository.findById(no);
		
		// 값이 있는지 확인
		if(optional.isPresent()) {
			Board board = optional.get();
			BoardDTO dto = entityToDTO(board);
			return dto;
		}
		
		return null;
	}

	@Override
	public void modify(BoardDTO dto) {

		// 전달받은 DOT에서 게시물 번호를 꺼내고, DB에 존제하는지 확인
		int no = dto.getNo();
		Optional<Board> optional = repository.findById(no);
		
		if(optional.isPresent()) {
			
			Board board = optional.get();
			
			// 기존 엔티티에서 제목, 내용, 작성자 변경
			board.setTitle(dto.getTitle());
			board.setContent(dto.getContent());
			board.setWriter(dto.getWriter());
			
			// 데이터베이스에 업데이트
			repository.save(board);
		}
		
	}
	
}

