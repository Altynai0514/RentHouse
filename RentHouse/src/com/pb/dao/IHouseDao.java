package com.pb.dao;

import com.pb.base.dao.IBaseDao;
import com.pb.entity.House;

public interface IHouseDao extends IBaseDao<House> {
	public void save(House instances);
}
