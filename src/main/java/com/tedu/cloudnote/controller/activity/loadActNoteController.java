package com.tedu.cloudnote.controller.activity;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.ActivityService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class loadActNoteController {
	
	@Resource 
	private ActivityService activityService;
	
	@RequestMapping("/activitynote.do")
	@ResponseBody
	
	public NoteResult execute(){
		NoteResult result = activityService.loadActNotes();
		return result;
	}
}
