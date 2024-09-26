package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	// 등록화면을 반환하는 메소드
	@GetMapping("/register")
	public void register() {
		
	}

	// 새로운 게시물을 등록하는 메소드
	@PostMapping("/register") // POST + /board/register
	// 폼데이터를 수집할 때는 어노테이션 없음
	
	// RedirectAttributes: 리다이렉트할 때 데이터를 전달하는 객체 (모델)
	public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
		
		//화면에서 전달한 폼데이터를 받아서 데이터베이스에 저장
		//그리고 새로운 게시물번호를 반환받음
		int no = service.register(dto);		
		System.out.println("no:" + no);
		
		// 이동할 화면에 새로운 게시물 번호를 전달
		redirectAttributes.addFlashAttribute("no", no);
		
		// 목록화면으로 리다이렉트
		// 리다이렉트: 새로운 URL을 다시 호출하는 것
		return "redirect:/board/list";
	}
	
}
