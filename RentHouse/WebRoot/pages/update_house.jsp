﻿<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.pb.util.Constant;"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ss" uri="/struts-dojo-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>青鸟租房 - 添加租房信息</title>
	<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
	<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.js"></script>
<style>
br{
	display:none;
}
</style>
</head>
<ss:head parseContent="true"/>

<script type="text/javascript">
var reg = /^\d+\.?\d*$/;
//添加事件
$(document).ready(function(){
	$("#error-floorage").hide();
	$("#error-price").hide();
	//面积输入验证
	$("#floorage").keyup(function(){
		if(  reg.test($("#floorage").val()) ){
			$("#error-floorage").hide();
		}else{
			$("#error-floorage").show();
		}
	});
	
	//价格输入验证
	$("#price").keyup(function(){
		if(  reg.test($("#price").val()) ){
			$("#error-price").hide();
		}else{
			$("#error-price").show();
		}
	});
});

//提交验证
function validate() {
	validateFileType();//验证图片格式
	//面积验证
	if($("#floorage").val()=='' || reg.test($("#floorage").val()) ){
		$("#error-floorage").hide();
	}else{
		$("#error-floorage").show();
		return false;
	}
	//价格验证
	if($("#price").val()=='' || reg.test($("#price").val()) ){
		$("#error-price").hide();
	}else{
		$("#error-price").show();
		return false;
	}
	
	return true;
}

//验证图片格式
function validateFileType(obj) {
	var fileValue = obj!=null ? obj.value : document.getElementById("img").value;
	//验证图片格式
	if(fileValue !=null && fileValue.length > 0){
		var suffix = fileValue.substring(fileValue.length - 3, fileValue.length);
		if(!(suffix == "jpg" || suffix == "JPG")) {
			Boxy.alert("请选择JPG格式的图片!");
			
			return false;
		}
	}
}
</script>
<style>
br{
	display:none;
}
</style>

<body>
<s:include value="header.jsp"></s:include>
<div id="regLogin" class="wrap">
	<div class="dialog">
		<dl class="clearfix">
			<dt>新房屋信息发布</dt>
			<dd class="past">填写房屋信息</dd>
		</dl>
		<div class="box">
				<s:form action="updateHouse.action" name="updateHouse" id="updateHouse" onsubmit="return validate();" enctype="multipart/form-data" >
					<div class="infos">
						<table class="field">
							<!-- 获取house的已有信息 -->
							<s:hidden cssClass="text" name="house.id" value="%{#request.house.id}" />
							<s:hidden cssClass="text" name="house.picture.id" value="%{#request.house.picture.id}" />
							<s:hidden cssClass="text" name="house.upLoadFile.houseId" value="%{#request.house.picture.houseId}" />
							
							<tr>
								<td class="field">标　　题：</td>
								<td>
								<s:textfield cssClass="text" key="title" name="house.title" value="%{#request.house.title}" />
								</td>
							</tr>
							<tr>
								<td class="field">户　　型：</td>
								<td>
									<s:select list="typeList" name="house.houseType.id" listKey="id" listValue="name">
									</s:select>
								</td>
							</tr>
							<tr>
								<td class="field">面　　积：</td>
								<td>
									<s:textfield cssClass="text" key="floorage" id="floorage" name="house.floorage" value="%{#request.house.floorage}" />&nbsp;平米
									<span id="error-floorage"><font color="red">只能输入数字</font></span>
								</td>
							</tr>
							<tr>
								<td class="field">价　　格：</td>
								<td>
									<s:textfield cssClass="text" key="price" id="price" name="house.price" value="%{#request.house.price}"/>&nbsp;元/每月
									<span id="error-price"><font color="red">只能输入数字</font></span>
								</td>
							</tr>
							<tr>
								<td class="field">房产证日期：</td>
								<td>
									<ss:datetimepicker id="datePicker" cssClass="text" name="house.pubdate" value="%{#request.house.adddate}"></ss:datetimepicker>
								</td>
							</tr>
							<tr>
								<td class="field">位　　置：</td>
								<td >
									<div class="nobr">
									<s:doubleselect doubleList="streetMap.get(top.id)" list="disList" 
										doubleName="house.street.id" name="districtId"
										listKey="id" listValue="name" 
										doubleListKey="id" doubleListValue="name">
									</s:doubleselect>
									</div>
	                            </td>
							</tr>
							<tr>
			 					<td class="field">上传图片：</td>
			 					<td>
			 						<s:file id="img" name="img" cssClass="FileSty" onchange="validateFileType(this)" cssStyle="width:280">
			 						</s:file><br />
			 						<font color="red"><s:fielderror fieldName="img"/></font>
			 					</td>
			 				</tr>
			 				<tr>
			 					<td class="field">图片标题：</td>
								<td>
									<s:textfield name="upLoadFile.title" cssClass="text" value="%{#request.house.picture.title}"></s:textfield>
								</td>
			 				</tr>
			 				<%-- <tr>
				 				<td class="field">已上传图片</td>
				 				<td  style="text-align: center">
				 					<img onerror="this.src='<%=path %>/img/noimage.jpg'" width="90" height="60" alt="" src="<%=path %>/img/noimage.jpg"/>
				 				</td>
				 			</tr> --%>
				 			 <tr>
								<td class="field">联系方式：</td>
								<td>
									<s:textfield cssClass="text" key="contact" name="house.contact" value="%{#request.house.contact}"/>
								</td>
							</tr>
	                        <tr>
								<td class="field">详细信息：</td>
								<td><s:textarea name="house.description" value="%{#request.house.description}"></s:textarea></td>
							</tr>
						</table>
						<div class="buttons">
							<input type="submit"  value="修改发布" />
							<!-- <input type="button" value="返回" onclick ="javascript:history.go(-1);" /> -->
							<!-- <input type="button" value="返回" onclick ="javascript:window.location.href='doSearch.action'" /> -->
						</div>
					</div>
				</s:form>
			</div>
	</div>
</div>
<s:include value="footer.jsp"></s:include>
</body>
</html>

