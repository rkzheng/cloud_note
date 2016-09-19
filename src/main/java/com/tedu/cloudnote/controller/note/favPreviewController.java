package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class favPreviewController {
@Resource
private NoteService noteService;

@RequestMapping("/favpreview.do")
@ResponseBody
public NoteResult execute(String favId){
	NoteResult result = noteService.showFavPreview(favId);
	return result;
}
}
