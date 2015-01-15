package com.pb.util;

import java.io.File;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

//import org.apache.struts2.ServletActionContext;

public class UpLoadFile {
	private File imgfile;//文件
	private String fileName; //上传文件名
	private String contentType;//上传文件类型
	private String path;//文件上传路径
	private String title;//文件标题
	
	public File getImgfile() {
		return imgfile;
	}
	public void setImgfile(File imgfile) {
		this.imgfile = imgfile;
	}
	public String getFileName() {
		return fileName;
	}
	
	//实现重命名方法
	/**
	 * 自定义替换文件名方法
	 * @param prefix
	 * @param fileName
	 * @return fileName
	 */
    public String getFileName(String prefix) {
        if(fileName !=null && fileName.indexOf(".")>0){
            String name = fileName.substring(fileName.indexOf("."),fileName.length());
            fileName = (prefix !=null ? "/"+prefix+"/" : "")+UUID.randomUUID().toString()+name;
        }
        
        return fileName;
    }
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		
		return ServletActionContext.getServletContext().getRealPath(Constant.UPLOAD_PATH);
	}

	public void setPath(String path) {
		this.path = path;
	}

}
