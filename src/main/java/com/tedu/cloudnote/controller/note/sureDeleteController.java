package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class sureDeleteController {
	
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/trash/sure_delete.do")
	@ResponseBody
	public NoteResult sureDelete(String noteId){
		NoteResult result =
				noteService.sureDelete(noteId);
		return result;
	}

}
