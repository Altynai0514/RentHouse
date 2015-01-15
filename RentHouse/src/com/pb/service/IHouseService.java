package com.pb.service;

import java.util.List;
import java.util.Map;

import com.pb.entity.District;
import com.pb.entity.House;
import com.pb.entity.HousePicture;
import com.pb.entity.HouseType;
import com.pb.entity.Street;
import com.pb.util.Page;
import com.pb.util.UpLoadFile;

public interface IHouseService {

	/**
	 * 保存房屋
	 * @param house
	 */
	public boolean save(House house,UpLoadFile file);

	/**
	 * 删除房屋
	 * @param house
	 */
	public void delete(House house);

	/**
	 * 修改房屋
	 * @param house
	 */
	public boolean update(House house,UpLoadFile file);

	/**
	 * 根据Id获取房屋
	 * @param id
	 */
	public House findById(java.lang.Integer id);

	/**
	 * 根据houseId获取房屋
	 * @param id
	 */
	public HousePicture getPictureById(java.lang.Integer id);
	
	/**
	 * 根据用户id获取房屋记录（多对一关系）
	 */
	public List getHousebyUserId(int userId);
	
	/**
	 * 查询所有房屋列表
	 * @param params
	 */
	public Object[] findAll(Map<String,Object> params);
	/**
	 * 查询所有房屋类型列表
	 */
	public List<HouseType> findTypeList();
	
	/**
	 * 查询所有区列表
	 */
	public List<District> findDistrictList();

	/**
	 * 根据区id查询所有街道列表
	 */
	public List<Street> findStreetListByDisId(int disId);

	/**
     * 获取所有房屋
     * @return
     */
    public int findAllHouse();
}
