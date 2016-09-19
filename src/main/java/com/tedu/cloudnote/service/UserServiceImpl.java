package com.tedu.cloudnote.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tedu.cloudnote.dao.UserDao;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteException;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("userService")//扫描

public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;//注入
	
	public NoteResult checkLogin(
			String name, String password) {
		NoteResult result = new NoteResult();
		//判断用户名
		User user = userDao.findByName(name);
		if(user==null){
			result.setStatus(1);
			result.setMsg("username not exist");
			return result;
		}
		//判断密码
		//将用户输入的明文加密
		try{
			String md5_pwd = NoteUtil.md5(password);
			if(!user.getCn_user_password()
					.equals(md5_pwd)){
				result.setStatus(2);
				result.setMsg("wrong password");
				return result;
			}
		}catch(Exception e){
			throw new NoteException(
				"encode error", e);	
		}
		//登录成功
		result.setStatus(0);
		result.setMsg("login succeed");
		user.setCn_user_password("");//把密码屏蔽不返回
		result.setData(user);//返回user信息
		return result;
	}
	
	public NoteResult addUser(
		String name, String nick, String password) {
		NoteResult result = new NoteResult();
		try{
			//检测是否重名
			User has_user = 
				userDao.findByName(name);
			if(has_user != null){
				result.setStatus(1);
				result.setMsg("username has been taken");
				return result;
			}
			
			//执行用户注册
			User user = new User();
			user.setCn_user_name(name);//设置用户名
			user.setCn_user_nick(nick);//设置昵称
			String md5_pwd = NoteUtil.md5(password);
			user.setCn_user_password(md5_pwd);//设置加密密码
			String userId = NoteUtil.createId();
			user.setCn_user_id(userId);//设置用户ID
			userDao.save(user);
			//创建返回结果
			result.setStatus(0);
			result.setMsg("sign up succeed");
			return result;
		}catch(Exception e){
			throw new NoteException("sign up exception",e);
		}
		
	}
	public NoteResult changePwd(String userId, String opwd, String npwd) {
		User user = userDao.findById(userId);
		try {
			String md5_opwd = NoteUtil.md5(opwd);
			String md5_npwd = NoteUtil.md5(npwd);
		
		NoteResult result = new NoteResult();
		if(user!=null){
			if(md5_opwd.equals(user.getCn_user_password())){
				Map<String,String> param = new HashMap<String,String>();
				param.put("npwd", md5_npwd);
				param.put("userId", userId);
				int rows = userDao.changePassword(param);
				if(rows==1){
					result.setStatus(0);
					result.setMsg("password updated, please login");
					return result;
				}else{
					result.setStatus(1);
					result.setMsg("database exception, try again later");
					return result;
				}
			}else{
				result.setStatus(2);
				result.setMsg("origin password is wrong");
				return result;
				}
		}else{
			result.setStatus(3);
			result.setMsg("session expired, login");
			return result;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
