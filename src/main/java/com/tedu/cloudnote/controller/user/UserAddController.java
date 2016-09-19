package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

@Controller//扫描
public class UserAddController {
	@Resource//注入
	private UserService userService;
	
	@RequestMapping("/user/add.do")
	@ResponseBody
	public NoteResult execute(
		String name,String password,String nick){
		NoteResult result = 
			userService.addUser(
			name, nick, password);
		return result;
	}
	
}
