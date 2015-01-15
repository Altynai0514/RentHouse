package com.pb.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.pb.base.action.BaseAction;
import com.pb.base.dao.impl.BaseDaoImpl;
import com.pb.dao.IHouseDao;
import com.pb.dao.impl.HouseDaoImpl;
import com.pb.entity.District;
import com.pb.entity.House;
import com.pb.entity.HouseType;
import com.pb.entity.HouseUser;
import com.pb.entity.Street;
import com.pb.service.IHouseService;
import com.pb.service.impl.HouseServiceImpl;
import com.pb.service.impl.UserServiceImpl;
import com.pb.util.Page;
import com.pb.util.UpLoadFile;

public class HouseAction extends BaseAction {
	private IHouseService service = new HouseServiceImpl();
	//////////////////////////查询条件字段/////////////////////////////
	private List<HouseType> typeList;
	private List<District> disList;
	private Map<Integer,List<Street>> streetMap;
	private House house;
	private String price;
	private String floorage;//面积
	private Integer districtId;
	
	//////////////////////////上传图片字段/////////////////////////////
	private File img;
	private String imgFileName;
	private String imgContentType;
	private UpLoadFile upLoadFile;
	
	//////////////////////////// METHOD ////////////////////////////////
	public String doSearch(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("house", house);
		params.put("price", price);
		params.put("floorage", floorage);
		params.put("districtId", districtId);
		
		Object[] o = service.findAll(params);
		    //Object[0]当前页的数据列表List、Object[1]总页数、Object[2]总记录数
		if(page == null){
			page = new Page();
		}
		page.setList((List<Object>)o[0]);
		page.setTotalCount(Integer.parseInt(String.valueOf(o[2])));
		getSelect();
		
		return SUCCESS;
	}
	
	/**
	 * 获取页面下拉列表
	 */
	public void getSelect(){
		typeList = service.findTypeList();//获取房屋类型集合
		disList = service.findDistrictList();//获取房屋所在区域集合
		streetMap = new HashMap<Integer,List<Street>>();
		for(int i =0;i<disList.size();i++){//遍历区域集合
			District dis = disList.get(i);
			if(dis.getStreets()!=null && dis.getStreets().size()>0){
				streetMap.put(dis.getId(), new ArrayList<Street>(dis.getStreets()));
			}
		}
	}
	
	/**
	 * 显示发布房屋信息方法
	 * @return
	 */
	public String gotoAddHouse(){
		getSelect();
		
		return SUCCESS;
	}
	
	/**
	 * 保存房屋信息方法
	 * @return
	 */
	public String doAddHouse(){
		HouseUser user = (HouseUser)session.get("LOGIN_USER");
		if(user!=null){
			house.setHouseUser(user);//保存当前登录用户
			if(img!=null){
				upLoadFile.setFileName(imgFileName);
				upLoadFile.setImgfile(img);
			}
			//调用service方法保存信息
			if(service.save(house, upLoadFile)){//如果保存成功
				return "house.success";
			}else{
				addActionMessage("保存失败！");
			}
		}else{
			addActionMessage("请先登录！");
		}
		getSelect();
		return INPUT;
		
	}
	
	/**
	 * 删除房屋方法
	 * 
	 */
	public void doDeleteHouse(){
	    String id = ServletActionContext.getRequest().getParameter("id");
	    //根据id获取house
	    House house = service.findById(Integer.parseInt(id));
	    System.out.println("您已删除一条房屋记录，id为："+id);
	    service.delete(house);
	}
	
	/**
	 * 更新房屋跳转方法
	 * 
	 */
	public String doUpdateHouse(){
	    String id = ServletActionContext.getRequest().getParameter("id");
	    House house = service.findById(Integer.parseInt(id));
	    
	    //将信息存放到request中
	    if(house != null){
	        HttpServletRequest request = ServletActionContext.getRequest();
	        request.setAttribute("house", house);
	        
	        getSelect();
	    }
	    return SUCCESS;
	}
	
	/**
     * 更新房屋方法
     * 
     */
	public String updateHouse(){
	    
	    HouseUser user = (HouseUser)session.get("LOGIN_USER");
        if(user!=null){
            house.setHouseUser(user);//保存当前登录用户
            if(img!=null){
                upLoadFile.setFileName(imgFileName);
                upLoadFile.setImgfile(img);
            }
            //调用service方法更新信息
            if(service.update(house, upLoadFile)){//如果更新成功
                return SUCCESS;
            }else{
                addActionMessage("保存失败！");
            }
        }else{
            addActionMessage("请先登录！");
        }
        getSelect();
        return INPUT;
	}
	
	/**
	 * 展示房屋方法
	 * 
	 */
	public String show(){
	    int id = house.getId();
	    House house = service.findById(id);
	    if(house != null){
	        //将house对象存放到request中
	        HttpServletRequest request = ServletActionContext.getRequest();
	        request.setAttribute("house", house);
	        
	        getSelect();
	    }
	    return SUCCESS;
	}
	
	/**
     * 查询所有房屋数
     * 
     */
    public String findAllHouse(){
        int num = service.findAllHouse();
        HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession().setAttribute("houseNum", num);
        return SUCCESS;
    }
	
	/**
	 * 根据用户id获取房屋信息
	 * 
	 */
	public String getUserHouse(){
	    String userId = ServletActionContext.getRequest().getParameter("userId");
	    //根据用户id获取房屋记录（多对一关系）
        List list = service.getHousebyUserId(Integer.parseInt(userId));
        Iterator iterator = list.iterator();
        if(iterator.hasNext()){
            house = (House) iterator.next();
        }
        if(house != null){
            //将house对象存放到request中
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("house", house);
            
            getSelect();
        }
        System.out.println(house.getHouseUser().getUsername()+"  "+house.getTitle());
        return SUCCESS;
        
        /*String userId = ServletActionContext.getRequest().getParameter("userId");
        //根据用户id获取房屋记录（多对一关系）
        List list = service.getHousebyUserId(Integer.parseInt(userId));
        Iterator iterator = list.iterator();
        if(iterator.hasNext()){
            house = (House) iterator.next();
        }
        
        System.out.println(userId+"  "+house.getHouseUser().getUsername()+"  "+house.getTitle());
        if(house != null){
            //将house对象存放到request中
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("house", house);
            
            getSelect();
        }
        return SUCCESS;*/
	}
	//////////////////////////// GETER / SETER ///////////////////////////////
	
	public List<HouseType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<HouseType> typeList) {
		this.typeList = typeList;
	}

	public List<District> getDisList() {
		return disList;
	}

	public void setDisList(List<District> disList) {
		this.disList = disList;
	}

	public Map<Integer, List<Street>> getStreetMap() {
		return streetMap;
	}

	public void setStreetMap(Map<Integer, List<Street>> streetMap) {
		this.streetMap = streetMap;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFloorage() {
		return floorage;
	}

	public void setFloorage(String floorage) {
		this.floorage = floorage;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public String getImgContentType() {
		return imgContentType;
	}

	public void setImgContentType(String imgContentType) {
		this.imgContentType = imgContentType;
	}

	public UpLoadFile getUpLoadFile() {
		return upLoadFile;
	}

	public void setUpLoadFile(UpLoadFile upLoadFile) {
		this.upLoadFile = upLoadFile;
	}

}
