package com.tedu.cloudnote.entity;

import java.io.Serializable;

public class Favorite implements Serializable{
	private String fav_id;
	private String fav_title;
	private String fav_body;
	private String cn_note_id;
	private String cn_user_id;
	public String getFav_id() {
		return fav_id;
	}
	public void setFav_id(String fav_id) {
		this.fav_id = fav_id;
	}
	public String getFav_title() {
		return fav_title;
	}
	public void setFav_title(String fav_title) {
		this.fav_title = fav_title;
	}
	public String getFav_body() {
		return fav_body;
	}
	public void setFav_body(String fav_body) {
		this.fav_body = fav_body;
	}
	public String getCn_note_id() {
		return cn_note_id;
	}
	public void setCn_note_id(String cn_note_id) {
		this.cn_note_id = cn_note_id;
	}
	public String getCn_user_id() {
		return cn_user_id;
	}
	public void setCn_user_id(String cn_user_id) {
		this.cn_user_id = cn_user_id;
	}
	
}
