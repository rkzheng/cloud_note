package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface ActNoteService {

	public NoteResult findActNotes(String actId);
	
	public NoteResult addActnote(String noteId,String actId); 
	
	public NoteResult previewaNoteById(String actnoteId);
	
}
