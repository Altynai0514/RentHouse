<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.pb.util.Constant;"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0);  
String path = request.getContextPath();
%>
<script type="text/javascript">

	function deleteHouse(id){
		
		var username = '<%=session.getAttribute("username")%>';
		var password = '<%=session.getAttribute("password")%>';
		
		/* 设置权限-只有当前登录用户为管理员时显示“修改”和“删除” */
		if('admin' == username && 'admin' == password){
			var url = 'doDeleteHouse.action';
			$.ajax({
				type:"post",
				url:url,
				data:{"id":id},
				dataType:"html",
				success:function(){
					Boxy.alert("删除成功！");
					history.go(0);
				}
			});
		}else{
			Boxy.alert("您不具有此权限！");
		}
	}
	
	function updateHouse(id){
		
		var username = '<%=session.getAttribute("username")%>';
		var password = '<%=session.getAttribute("password")%>';
		
		/* 设置权限-只有当前登录用户为管理员时显示“修改”和“删除” */
		if('admin' == username && 'admin' == password){
			window.location.href="doUpdateHouse.action?id="+id;
		}else{
			Boxy.alert("您不具有此权限！");
		}
		
	}
	
</script>
<div class="main wrap">
	<s:div id="houseArea">
		<table class="house-list">
		<s:if test="null == page || null == page.list || page.list.size<1">
	   		<tr>
	    		<td colspan="11"><center>无租房信息</center></td>
	    	</tr>
	   	</s:if>
	    <s:else>
		<s:iterator value="page.list" status="status">
			<tr <s:if test="#status.count%2 == 0"> class="odd"</s:if>>
				<td class="house-thumb">
					<span>
						<s:if test='picture.url!=null && picture.url !=""'>
							<img src='<%=path+Constant.UPLOAD_PATH%><s:property value="picture.url"/>' width="90" height="60" 
							onerror="this.src='<%=path %>/img/thumb_house.gif'"/>
						</s:if><s:else>
							<img src="<%=path%>/img/thumb_house.gif" />
						</s:else>
					</span>
				</td>
				<td>
					<dl>
						<dt>
							<s:a href="house!show.action?house.id=%{id}">
								<s:property value="title" />
							</s:a>
						</dt>
						<dd>
							<s:if test="street != null">
								地址：<s:property value="street.district.name"/>区,
								<s:property value="street.name"/>;&nbsp
							</s:if>
							<s:property value="floorage"/>&nbsp平米,
							<s:property value="price"/>&nbsp/月<br />
							联系方式：<s:property value="contact"/>
						</dd>
					</dl>
				</td>
				<td class="house-type">
					<label class="ui-green">
						<input type="button" id="update" value="修    改" onclick="updateHouse(<s:property value="id" />)" />
					</label>
				</td>
				<td class="house-price">
					<label class="ui-green">
						<input type="button" id="delete" value="删    除" onclick="deleteHouse(<s:property value="id" />)" />
					</label>
					<%-- <s:property value="id"/> --%>
					
					<%-- <s:action name="judgeUser" executeResult="false">
						<s:property value="#request.user.username"/>
					</s:action> --%>
				</td>
			</tr>
	    </s:iterator>
	    </s:else>
		</table>
	</s:div>
	<s:include value="page.jsp"></s:include>
</div>
