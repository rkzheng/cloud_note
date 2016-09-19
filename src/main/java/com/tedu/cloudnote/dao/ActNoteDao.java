package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.ActNote;
import com.tedu.cloudnote.entity.Note;

public interface ActNoteDao {
public List<ActNote> findActNotes(String actId);

public void saveActNote(ActNote actnote);

public Note findbyId(String noteId);

public ActNote findaNoteById(String actnoteId);
}
