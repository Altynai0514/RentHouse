package com.pb.web.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.pb.base.action.BaseAction;
import com.pb.entity.HouseUser;
import com.pb.service.IUserService;
import com.pb.service.impl.UserServiceImpl;
import com.pb.util.Constant;

public class UserAction extends BaseAction {
	private IUserService service = new UserServiceImpl();
	private HouseUser user;
	private String msg;
	private String repassword;
	
	/**
	 * 登录
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		if(user!=null){
			Map<String,Object> map = service.login(user);
			user = (HouseUser)map.get("user");
			if(user!=null){
				session.put(Constant.LOGIN_USER,user); //将登录用户保存到session
				msg = "true";
				return SUCCESS;
			}else{
				msg = (String)map.get("msg");
				return LOGIN;
			}
		}else{
			msg = "false";
			return LOGIN;
		}
	}

	/**
	 * 退出
	 * @return
	 */
	public String logout(){
		session.remove(Constant.LOGIN_USER);
		session.clear();
		return "house.manage";
	}	
	
	/**
	 * 注册
	 * @return
	 */
	public String register(){
		if(service.doRegister(user)){
			return SUCCESS;
		}
		return INPUT;
	}
	
	/**
	 * 验证用户名是否存在
	 * @return
	 */
	
	public String validateName(){
		if(null!=user && service.validate(user.getUsername())){
			msg = "true";
			return SUCCESS;
		}
		msg = "false";
		return INPUT;
	}
	
	/**
     * 获取当前登录用户信息
     * 
     */
    public String judgeUser(){
        String username = ServletActionContext.getRequest().getParameter("username");
        String password = ServletActionContext.getRequest().getParameter("password");
        
        //根据用户名获取用户id
        int userId = service.getUserIdbyName(username);
        
        System.out.println("用户id："+userId+"   用户名："+username+"   密码："+password);
        HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("password", password);
        return SUCCESS;
    }
    
    /**
     * 查询所有用户数
     * 
     */
    public String findAll(){
        int num = service.findAll();
        HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession().setAttribute("userNum", num);
        return SUCCESS;
    }
    
    
	public HouseUser getUser() {
		return user;
	}


	public void setUser(HouseUser user) {
		this.user = user;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
		
	
}
