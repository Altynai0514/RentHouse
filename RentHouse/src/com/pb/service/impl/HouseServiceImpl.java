package com.pb.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.catalina.User;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.pb.dao.IHouseDao;
import com.pb.dao.impl.HouseDaoImpl;
import com.pb.entity.District;
import com.pb.entity.House;
import com.pb.entity.HousePicture;
import com.pb.entity.HouseType;
import com.pb.entity.HouseUser;
import com.pb.entity.Street;
import com.pb.service.IHouseService;
import com.pb.util.Page;
import com.pb.util.UpLoadFile;

public class HouseServiceImpl implements IHouseService {
	private IHouseDao dao = new HouseDaoImpl();
	
	@Override
	public boolean save(House house, UpLoadFile file) {
		try {
		    //1.根据当前用户登录名创建其子文件夹
		    //2.当前上传文件替换为一个不可重复的字符串
		    String fileName = file.getFileName(String.valueOf(house.getHouseUser().getUsername()));
			//上传图片
			if(file!=null && upload(file,fileName)){
				//创建一个房屋图片对象
				HousePicture p = new HousePicture();
				p.setTitle(file.getTitle());
				p.setUrl(fileName);
				house.setPicture(p);
			}
			
			//调用dao方法保存房屋信息
			house.setAdddate(new Date());
			dao.save(house);
			
			return true;
		} catch (Exception e) {
		    e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 上传图片
	 * @return
	 */
	public boolean upload(UpLoadFile file,String fileName){
		if(file!=null && file.getImgfile()!=null){
		    try {
		        String path = file.getPath();
		        System.out.println("上传图片的path为："+path);
		        File tagFile = new File(path+fileName);
		        if(!tagFile.getParentFile().exists()){
		            tagFile.getParentFile().mkdirs();
		        }
		            FileUtils.copyFile(file.getImgfile(), tagFile);
		            return true;
		        } catch (IOException e) {
		            e.printStackTrace();
		            return false;
		        }
		}
		return false;
	}

	@Override
	public void delete(House house) {
	    dao.delete(house);
	}

	@Override
	public boolean update(House house, UpLoadFile file) {
	    try {
            String fileName = file.getFileName(String.valueOf(house.getHouseUser().getUsername()));
            //上传图片
            if(file!=null && upload(file,fileName)){
                //创建一个房屋图片对象
                HousePicture p = new HousePicture();
                p.setTitle(file.getTitle());
                p.setUrl(fileName);
                p.setId(house.getId());
                p.setHouseId(house.getId());
                house.setPicture(p);
            }
            house.setAdddate(new Date());
            
            //调用dao方法更新房屋信息
            dao.update(house);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public House findById(Integer id) {
		//return (House) dao.findById(getClass(), id);
	    
	    House house = null;
	    List list = dao.findByHql("FROM House WHERE id="+id.intValue());
	    Iterator iterator = list.iterator();
	    if(iterator.hasNext()){
	        house = (House) iterator.next();
	    }
	    return house;
	}
	
	@Override
	public HousePicture getPictureById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HouseType> findTypeList() {
		List list = dao.findAll(HouseType.class);
		return (List<HouseType>) list;
	}

	@Override
	public List<District> findDistrictList() {
		List list = dao.findAll(District.class);
		return (List<District>) list;
	}

	@Override
	public List<Street> findStreetListByDisId(int disId) {

		return (List<Street>)dao.findByHql("FROM Street WHERE district.id="+disId);
	}

	@Override
	public Object[] findAll(Map<String, Object> params) {
		Page page = (Page)params.get("page");
		if(null == page){
			page = new Page();
		}
		
		String hql = "FROM House house WHERE 1=1"+buildQueryCondition(params);
		String hqlCount = "SELECT COUNT(*)"+hql;
		
		return dao.findPageByHql(hql+" ORDER BY house.adddate DESC,house.id ASC", hqlCount, page.getIndex(),page.getSize());
	}

	//组合查询条件
	private String buildQueryCondition(Map params) {
		StringBuilder hql = new StringBuilder();
		House house = (House)params.get("house");
		String price = String.valueOf(params.get("price"));
		String floorage = String.valueOf(params.get("floorage"));
		Integer districtId = (Integer)params.get("districtId");
		
		//根据价格查询
		if (null != price && !"".equals(price.trim()) && !price.equals("null")) {
			if(price.indexOf(",")>1){
				String[] array = price.split(",");
				for(int i=0;i<array.length;i++){
					hql.append(" AND house.price " + array[i]);
				}
			}else{
				hql.append(" AND house.price " + price);
			}
		}
		
		//根据面积查询
		if (floorage!=null && !"".equals(floorage.trim()) && !floorage.equals("null")) {
			if(floorage.indexOf(",")>1){
				String[] array = floorage.split(",");
				for(int i=0;i<array.length;i++){
					hql.append(" AND house.floorage " + array[i]);
				}
			}else{
				hql.append(" AND house.floorage " + floorage);
			}
		}
		
		//根据区域查询
		if (districtId!=null && districtId!=0) {
			List<Street> list = findStreetListByDisId(districtId);
			if(list!=null && list.size()>0){
				hql.append("  AND house.street.id IN (");
				for(int i =0;i<list.size();i++){
					Street s = list.get(i);
					if(i<list.size()-1){
						hql.append(s.getId()+",");
					}else{
						hql.append(s.getId());
					}
				}
				hql.append(" )");
			}
		}
		
		
		if(house!=null){
			//根据标题查询
			if (null != house.getTitle() && !house.getTitle().trim().equals("")) {
				hql.append(" AND house.title  LIKE '%" + house.getTitle() + "%'");
			}
			//根据联系电话查询
			if (house.getContact()!=null) {
				hql.append(" AND house.contact  LIKE '% " + house.getContact()+ "%'");
			}
			//根据根据所在街道查询
			if (house.getStreet()!=null && house.getStreet().getId()!=null) {
				hql.append(" AND house.street.id  = " + house.getStreet().getId());
			}
			//根据房屋类型查询
			if (house.getHouseType()!=null && house.getHouseType().getId()!=null) {
				hql.append(" AND house.houseType.id  = " + house.getHouseType().getId());
			}

			//根据当前登录用户id查询
			/*if (house.getUserId()!=null) {
				hql.append(" AND house.houseUser.id  = " + house.getHouseUser().getId());
			}*/
		}

		return hql.toString();
	}

    /**
     * @param userId
     * @return
     */
    @Override
    public List getHousebyUserId(int userId)
    {
        return dao.findByProperty("House", "houseUser.id", userId);
    }

    /**
     * 获取房屋数
     * @return
     */
    @Override
    public int findAllHouse()
    {
        List list = dao.findByHql("FROM House");
        return list.size();
    }
}
