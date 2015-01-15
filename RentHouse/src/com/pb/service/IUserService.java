package com.pb.service;

import java.util.Map;

import com.pb.entity.HouseUser;

public interface IUserService {

	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public Map<String,Object> login(HouseUser user);
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	public boolean doRegister(HouseUser user);
	
	/**
	 * 验证
	 * @param name
	 * @return
	 */
	public boolean validate(String name);
	
	/**
	 * 根据Id获取user对象
	 * @param id
	 * @return
	 */
	public HouseUser getUserById(Integer id);
	
	/**
	 * 根据用户名获取用户id
	 * @param username
	 * @return
	 */
	public int getUserIdbyName(String username);
	
	/**
     * 获取所有用户
     * @return
     */
	public int findAll();
}
