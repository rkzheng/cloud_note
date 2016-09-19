package com.tedu.cloudnote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tedu.cloudnote.dao.BookDao;
import com.tedu.cloudnote.dao.FavDao;
import com.tedu.cloudnote.dao.NoteDao;
import com.tedu.cloudnote.dao.ShareDao;
import com.tedu.cloudnote.entity.Favorite;
import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.entity.Share;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("noteService")
public class NoteServiceImpl implements NoteService{
	@Resource
	private NoteDao noteDao;
	@Resource
	private ShareDao shareDao;
	@Resource
	private BookDao bookDao;
	@Resource
	private FavDao favDao;
	
	public NoteResult loadBookNotes(String bookId) {
		//按笔记本ID查询笔记信息
		List<Map> list = 
			noteDao.findByBookId(bookId);
		//创建返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("Searching completed");
		result.setData(list);
		return result;
	}
	public NoteResult loadNote(String noteId) {
		//按笔记ID查询笔记信息
		Note note = noteDao.findById(noteId);
		//创建返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("Searching completed");
		result.setData(note);
		return result;
	}
	public NoteResult updateNote(
		String noteId, String title, String body) {
		Note note = new Note();
		note.setCn_note_id(noteId);//设置笔记ID
		note.setCn_note_title(title);//设置标题
		note.setCn_note_body(body);//设置内容
		Long time = System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);//设置修改时间
		int rows = noteDao.updateNote(note);//更新
		//创建返回结果
		NoteResult result = new NoteResult();
		if(rows==1){//成功
			result.setStatus(0);
			result.setMsg("Note saved");
		}else{//失败
			result.setStatus(1);
			result.setMsg("Note saving failed");
		}
		return result;
	}
	public NoteResult addNote(
	String userId, String noteTitle, String bookId) {
		NoteResult result = new NoteResult();
		//创建note参数保存
		Map<String,String> m = new HashMap<String,String>();
		m.put("noteName", noteTitle);
		m.put("bookId", bookId);
		Note n = noteDao.findByName(m);
		Note note = new Note();
		if(n==null){
		note.setCn_user_id(userId);//设置用户ID
		note.setCn_note_title(noteTitle);//设置笔记名称
		note.setCn_note_body("");
		note.setCn_notebook_id(bookId);//设置笔记本ID
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);//笔记ID
		Long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);//创建时间
		note.setCn_note_last_modify_time(time);//最后修改时间
		note.setFav_status(0);
		noteDao.save(note);//保存笔记
		//创建返回结果
		
		result.setStatus(0);
		result.setMsg("Note created");
		result.setData(noteId);//返回笔记ID
		return result;
		}else{
			result.setStatus(1);
			result.setMsg("Note name has been taken");
			return result;
		}
	}
	public NoteResult deleteNote(String noteId) {
		int rows = 
			noteDao.updateStatus(noteId);
		//创建返回结果
		NoteResult result = new NoteResult();
		if(rows >= 1){//成功
			result.setStatus(0);
			result.setMsg("Note deleted");
		}else{
			result.setStatus(1);
			result.setMsg("Note delete failed");
		}
		return result;
	}
	public NoteResult moveNote(
		String noteId, String bookId) {
		Note note = new Note();
		note.setCn_note_id(noteId);//设置笔记ID
		note.setCn_notebook_id(bookId);//设置笔记本ID
		int rows = 
			noteDao.updateBookId(note);//更新
		//创建返回结果
		NoteResult result = new NoteResult();
		if(rows>=1){
			result.setStatus(0);
			result.setMsg("Note transfered");
		}else{
			result.setStatus(1);
			result.setMsg("Note transfer failed");
		}
		return result;
	}
	public NoteResult shareNote(String noteId) {
		NoteResult result = new NoteResult();
		//获取笔记信息
		Note note = noteDao.findById(noteId);
		//检查cn_note_type_id是否为分享状态,
		//如果已分享不执行下面逻辑
		if("2".equals(note.getCn_note_type_id())){
			result.setStatus(1);
			result.setMsg("This note has been shared");
			return result;
		}
		//更新笔记的cn_note_type_id为分享状态'2'
		noteDao.updateTypeId(noteId);
		//添加到分享表
		Share share = new Share();
		share.setCn_note_id(noteId);//笔记ID
		share.setCn_share_id(NoteUtil.createId());//分享ID
		share.setCn_share_title(
			note.getCn_note_title());//分享标题
		share.setCn_share_body(
			note.getCn_note_body());//分享内容
		share.setFav_status(0);
		shareDao.save(share);
		//创建返回结果
		result.setStatus(0);
		result.setMsg("Note shared");
		return result;
	}
	public NoteResult searchShareNote(
			String keyword,int page) {
		//处理查询条件值
		String title = "%";
		if(keyword!=null && !"".equals(keyword)){
			title = "%"+keyword+"%";
		}
		if(page<1){
			page=1;
		}
		int begin = (page-1)*5;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("keyword", title);
		List<Share> list = 
			shareDao.findLikeTitle(params);
		//封装NoteResult结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("Searching completed");
		result.setData(list);
		return result;
	}
	public NoteResult previewSearch(String shareId) {
		Share share = shareDao.findByShareId(shareId);
		NoteResult result = new NoteResult();
		if(share!=null){
			result.setStatus(0);
			result.setMsg("Loading succeed");
			result.setData(share);
			
			return result;
		}else{
		result.setStatus(1);
		result.setMsg("Loading prview failed");
		return result;
		}
	}
	public NoteResult showTrash(String userId) {
		NoteResult result = new NoteResult();
		if(userId==null){
			result.setStatus(1);
		}
		List<Note> list = noteDao.selectTrash(userId);
		result.setStatus(0);
		result.setMsg("Loading succeed");
		result.setData(list);
		return result;
	}
	public NoteResult noteRecover(String noteId) {
		NoteResult result = new NoteResult();
		int rows = noteDao.noteRecover(noteId);
		if(rows==1){
			result.setStatus(0);
			result.setMsg("Recovery succeed");
			return result;
		}else{
			result.setStatus(1);
			result.setMsg("Recovery failed");
		}
		return null;
	}
	public NoteResult sureDelete(String noteId) {
		int rows = noteDao.sureDelete(noteId);
		NoteResult result = new NoteResult();
		if(rows==1){
			result.setStatus(0);
			result.setMsg("Note has been clearly deleted");
			return result;
		}
		result.setStatus(1);
		result.setMsg("Failed to delete note");
		return result;
	}
	
	public NoteResult addToFavorite(String shareId,String userId) {
		NoteResult result = new NoteResult();
		Share share = shareDao.findByShareId(shareId);
		String noteTitle = share.getCn_share_title();
		String noteBody = share.getCn_share_body();
		int status = Integer.parseInt(shareDao.checkFav(shareId));
		//if duplicate
		System.out.println(status);
		if(status==1){
			System.out.println("here");
			result.setStatus(1);
			result.setMsg("This note has been added to your favorite before");
			return result;
		}
		//else
		shareDao.updateFavStatus(shareId);
		String favId = NoteUtil.createId();
		Favorite fav = new Favorite();
		fav.setFav_id(favId);
		fav.setFav_title(noteTitle);
		fav.setFav_body(noteBody);
		fav.setCn_note_id(share.getCn_note_id());
		fav.setCn_user_id(userId);
		favDao.saveFav(fav);
		result.setStatus(0);
		result.setMsg("Add note to your favorite succeeded");
		
		return result;
		
		
	}
	public NoteResult showFavorite(String userId) {
		
		List<Favorite> favs = favDao.getFavorite(userId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("Loading succeed");
		result.setData(favs);
		return result;
	}
	public NoteResult showFavPreview(String favId) {
		System.out.println("1"+favId);
		Favorite fav = favDao.favPreview(favId);
//		System.out.println("1"+fav.getFav_title());
		
		NoteResult result = new NoteResult();
		
			result.setStatus(0);
			result.setMsg("Loading content succeeded");
			result.setData(fav);
			return result;
		
	}
	public NoteResult cancelFavorite(String favId) {
		Favorite fav = favDao.favPreview(favId);
		String noteId = fav.getCn_note_id();
		int uprows = shareDao.favStatus0(noteId);
		int cancelrows = favDao.cancelFav(favId);
		NoteResult result = new NoteResult();
		if(uprows==1 && cancelrows==1){
			result.setStatus(0);
			result.setMsg("Canceled");
			return result;
		}
		result.setStatus(1);
		result.setMsg("Cancel failed");
		return result;
	}

}
