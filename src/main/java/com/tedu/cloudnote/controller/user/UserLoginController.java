package com.tedu.cloudnote.controller.user;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;
@Controller//扫描
public class UserLoginController {
	@Resource//注入
	private UserService userService;
	@RequestMapping("/user/login.do")
	@ResponseBody
	public NoteResult execute(
		String name,String password){
		NoteResult result = 
			userService.checkLogin(
				name, password);
		return result;
	}
	
}
