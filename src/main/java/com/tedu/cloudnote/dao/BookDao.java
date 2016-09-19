package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Book;

public interface BookDao {
	public void save(Book book);
	
	public List<Book> findByUserId(
			String userId);
	public Book findByName(String bookName);
	
	public String getFavId(Map<String,String> param);
}
