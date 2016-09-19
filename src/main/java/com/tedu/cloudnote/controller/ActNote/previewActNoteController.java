package com.tedu.cloudnote.controller.ActNote;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.ActNoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class previewActNoteController {
@Resource
private ActNoteService actnoteService;

@RequestMapping("/activity/load_contents.do")
@ResponseBody
public NoteResult execute(String actnoteId){
	NoteResult result = actnoteService.previewaNoteById(actnoteId);
	return result;
}
}
