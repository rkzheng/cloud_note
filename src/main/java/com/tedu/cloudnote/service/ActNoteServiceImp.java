package com.tedu.cloudnote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tedu.cloudnote.dao.ActNoteDao;


import com.tedu.cloudnote.entity.ActNote;

import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("actnoteSevice")
public class ActNoteServiceImp implements ActNoteService {
@Resource
private ActNoteDao actnoteDao;


public NoteResult findActNotes(String actId) {
	System.out.println(actId);
	List<ActNote> notes = actnoteDao.findActNotes(actId);
	NoteResult result = new NoteResult();
	
	result.setStatus(0);
	result.setMsg("Loading succeeded");
	result.setData(notes);
	return result;
	
}

public NoteResult addActnote(String noteId, String actId) {
	System.out.println(noteId);
	Note note = actnoteDao.findbyId(noteId);
	String title = note.getCn_note_title();
	String body = note.getCn_note_body();
	
	ActNote actnote = new ActNote();
	actnote.setCn_activity_id(actId);
	actnote.setCn_note_activity_body(body);
	actnote.setCn_note_activity_down(0);
	actnote.setCn_note_activity_id(NoteUtil.createId());
	actnote.setCn_note_activity_up(0);
	actnote.setCn_note_activity_title(title);
	actnote.setCn_note_id(noteId);
	actnote.setFav_status(0);
	
	actnoteDao.saveActNote(actnote);
	NoteResult result = new NoteResult();
	result.setStatus(0);
	result.setMsg("succeeded");
	return result;
	
}

public NoteResult previewaNoteById(String actnoteId) {
	ActNote actnote = actnoteDao.findaNoteById(actnoteId);
	NoteResult result = new NoteResult();
	result.setStatus(0);
	result.setMsg("succeed");
	result.setData(actnote);
	return result;
}



}
