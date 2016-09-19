package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Share;

public interface ShareDao {
	public List<Share> findLikeTitle(
			Map<String,Object> params);
	public void save(Share share);
	
	public Share findByShareId(String shareId);
	
    public String checkFav(String shareId);
	
	public int updateFavStatus(String shareId);
	
	public int favStatus0(String noteId);
}
