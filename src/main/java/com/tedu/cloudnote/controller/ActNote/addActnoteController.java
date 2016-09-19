package com.tedu.cloudnote.controller.ActNote;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.ActNoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class addActnoteController {
	@Resource
	private ActNoteService actnoteService;
	
	@RequestMapping("/activity/take_part_in.do")
	@ResponseBody
	public NoteResult addActNote(String noteId,String actId){
		NoteResult result = actnoteService.addActnote(noteId, actId);
		return result;
	}

}
