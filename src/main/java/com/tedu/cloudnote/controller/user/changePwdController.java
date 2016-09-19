package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class changePwdController {
		@Resource
		private UserService userService;
		
		@RequestMapping("/pwd/changepwd.do")
		@ResponseBody
		public NoteResult execute(String userId, String opwd, String npwd){
			NoteResult result = 
					userService.changePwd(userId, opwd, npwd);
			return result;
		}
}
