package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface NoteService {
	public NoteResult searchShareNote(
			String keyword, int page);
	public NoteResult shareNote(String noteId);
	public NoteResult moveNote(
		String noteId,String bookId);
	public NoteResult deleteNote(String noteId);
	public NoteResult addNote(
		String userId,String noteTitle,String bookId);
	public NoteResult updateNote(
		String noteId,String title,String body);
	public NoteResult loadNote(
			String noteId);
	public NoteResult loadBookNotes(
			String bookId);
	public NoteResult previewSearch(String shareId);
	
	public NoteResult showTrash(String userId);
	
	public NoteResult noteRecover(String noteId);
	
	public NoteResult sureDelete(String noteId);
	
	public NoteResult addToFavorite(String shareId,String userId);
	
	public NoteResult showFavorite(String userId);
	
	public NoteResult showFavPreview(String noteId);
	
	public NoteResult cancelFavorite(String favId);
}
