package com.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.BookService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class LoadBooksController {
	@Resource
	private BookService bookService;
	
	@RequestMapping("/book/loadbooks.do")
	@ResponseBody
	public NoteResult execute(String userId){
		NoteResult result = 
		bookService.loadUserBooks(userId);
		return result;
	}
	
}
