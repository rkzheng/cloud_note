package com.tedu.cloudnote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tedu.cloudnote.dao.ActivityDao;
import com.tedu.cloudnote.entity.Activity;
import com.tedu.cloudnote.util.NoteResult;

@Service("activityService")
public class ActivityServiceImp implements ActivityService{
@Resource 
private ActivityDao activityDao;
	public NoteResult loadActNotes() {
		List<Activity> acts = activityDao.loadActNotes();
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(acts);
		result.setMsg("Loading act notes succeeded");
		return result;
	}

}
