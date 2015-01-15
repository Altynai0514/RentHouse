package com.pb.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pb.dao.IUserDao;
import com.pb.dao.impl.UserDaoImpl;
import com.pb.entity.House;
import com.pb.entity.HouseUser;
import com.pb.service.IUserService;

public class UserServiceImpl implements IUserService {
	private IUserDao dao = new UserDaoImpl();
	@Override
	public Map<String, Object> login(HouseUser user) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			List<HouseUser> list = dao.findByProperty("HouseUser","username", user.getUsername());
			if(list !=null && list.size()>0){//用户名存在（默认用户名唯一）
				HouseUser loginUser = (HouseUser)list.get(0);
				if(null != user.getPassword() && !user.getPassword().trim().equals("") && user.getPassword().equals(loginUser.getPassword())){//密码正确
					result.put("user", loginUser);
					return result;
				}else{//密码不匹配
					result.put("msg", "用户名或密码错误！");
					return result;
				}
			}else{//用户名不存在
				result.put("msg", "该账户不存在！不能登录！");
				return result;
			}
		} catch (RuntimeException e) {			
			e.printStackTrace();
			return result;
		}
	}

	@Override
	public boolean doRegister(HouseUser user) {
		try{
			if(validate(user.getUsername())){
				dao.save(user);
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean validate(String name) {
		try{
			//根据条件获取集合
			List<HouseUser> list = dao.findByProperty("HouseUser","username",name);
			//判断集合长度
			if(list !=null&& list.size()==0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	

	@Override
	public HouseUser getUserById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public int findAll()
    {
        List list = dao.findByHql("FROM HouseUser");
        return list.size();
    }

    /**
     * @param username
     * @return
     */
    @Override
    public int getUserIdbyName(String username)
    {
        HouseUser user = new HouseUser();
        List list = dao.findByProperty("HouseUser", "username", username);
        Iterator iterator = list.iterator();
        if(iterator.hasNext()){
            user = (HouseUser) iterator.next();
        }
        return user.getId();
    }

	
}
