<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.pb.util.Constant;"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ss" uri="/struts-dojo-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>青鸟租房 - 查看租房信息</title>
	<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
	<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.js"></script>
<style>
br{
	display:none;
}
</style>
</head>
<ss:head parseContent="true"/>

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
			<dd class="past">查看房屋信息</dd>
		</dl>
		<div class="box">
			<s:action name="house" executeResult="false">
					<div class="infos">
						<table class="field">
							<tr>
								<td class="field">标　　题：</td>
								<td>
								<s:property value="#request.house.title" />
								</td>
							</tr>
							<tr>
								<td class="field">户　　型：</td>
								<td>
									<s:property value="#request.house.houseType.name" />
								</td>
							</tr>
							<tr>
								<td class="field">面　　积：</td>
								<td>
									<s:property value="#request.house.floorage" />&nbsp;平米
								</td>
							</tr>
							<tr>
								<td class="field">价　　格：</td>
								<td>
									<s:property value="#request.house.price" />&nbsp;元/每月
								</td>
							</tr>
							<tr>
								<td class="field">发布日期：</td>
								<td>
									<s:property value="#request.house.adddate" />
								</td>
							</tr>
							<tr>
								<td class="field">房产证日期：</td>
								<td>
									<s:property value="#request.house.pubdate" />
								</td>
							</tr>
							<tr>
								<td class="field">位　　置：</td>
								<td >
									<div class="nobr">
									<s:property value="#request.house.street.district.name" />区，
									<s:property value="#request.house.street.name" />街道
									</div>
	                            </td>
							</tr>
							<tr>
				 				<td class="field">图片标题：</td>
				 				<td>
			 						<s:property value="#request.house.picture.title" />
			 					</td>
				 			</tr>
			 				<tr>
				 				<td class="field">已上传图片：</td>
				 				<td class="house-thumb">
									<span>
						 				<s:if test='#request.house.picture.url!=null && #request.house.picture.url !=""'>
											<img src='<%=path+Constant.UPLOAD_PATH%><s:property value="#request.house.picture.url"/>' width="90" height="60" 
											onerror="this.src='<%=path %>/img/thumb_house.gif'"/>
										</s:if><s:else>
											<img src="<%=path%>/img/thumb_house.gif" />
										</s:else>
						 			</span>
								</td>
				 			</tr>
				 			<tr>
								<td class="field">联系方式：</td>
								<td>
									<s:property value="#request.house.contact" />
								</td>
							</tr>
	                        <tr>
								<td class="field">详细信息：</td>
								<td>
									<s:property value="#request.house.description" />
								</td>
							</tr>
						</table>
						<!-- <div class="buttons">
							<input type="submit"  value="立即发布" />
							<input type="button" value="返回" onclick ="javascript:history.go(-1);" />
							<input type="button" value="返回" onclick ="javascript:window.location.href='doSearch.action'" />
						</div> -->
					</div>
				</s:action>
			</div>
	</div>
</div>
<s:include value="footer.jsp"></s:include>
</body>
</html>

