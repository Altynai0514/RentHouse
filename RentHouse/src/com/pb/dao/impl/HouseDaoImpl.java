package com.pb.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pb.base.dao.impl.BaseDaoImpl;
import com.pb.dao.IHouseDao;
import com.pb.entity.House;
import com.pb.entity.HousePicture;

public class HouseDaoImpl extends BaseDaoImpl<House> implements IHouseDao {
	private Log log = LogFactory.getLog(this.getClass());
	
	public void save(House house){
		log.debug("saving "+house+" instance");
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			int id = (Integer)session.save(house);
			HousePicture picture = house.getPicture();
			if(picture!=null){
				picture.setHouseId(id);
				session.save(picture);
			}
			tx.commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			tx.rollback();
			log.error("save failed", re);
			throw re;
		} finally{
			closeSession();
		}
	}
}
