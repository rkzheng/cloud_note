package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class PreviewSearchController {
@Resource
private NoteService noteService;

@RequestMapping("/share/preview.do")
@ResponseBody
public NoteResult execute(String shareId){
	NoteResult result = noteService.previewSearch(shareId);
	System.out.println(result.getMsg());
	return result;
}
}
