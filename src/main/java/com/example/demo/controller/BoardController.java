package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired // 컨테이너에 등록된 빈 주입받기
	BoardService service;
	
	// 메인화면을 반환하는 메소드
	@GetMapping("/main")
	public void main() {
		
	}
	
	// 목록화면을 반환하는 메소드
	@GetMapping("/list") // /board/list
	public void list(Model model) {
		
		// 서비스를 통해 게시물 목록을 가져와서 화면에 전달
		List<BoardDTO> list = service.getList();
		
		// 모델 객체에 데이터 담기
		model.addAttribute("list", list);
		
	}
	
}
