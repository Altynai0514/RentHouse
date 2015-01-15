package com.pb.web.action;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pb.entity.HouseUser;
import com.pb.util.HibernateSessionFactory;

public class test {
	@SuppressWarnings("unchecked")
	public static void main(String[] asdf){
		 Session session = HibernateSessionFactory.getSession();
		 Query q = session.createQuery("from HouseUser");
		 List<HouseUser> list =q.list();
		 for(HouseUser user : list){
			 
			 System.out.println(user.getRealname());
		 }
		 
	}
	
}
