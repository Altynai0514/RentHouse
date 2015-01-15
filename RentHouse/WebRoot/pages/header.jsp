<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('#releaseButton').click(function(){
		//判断当前用户是否登录
		if($('#loginUser').attr('value') == null || $('#loginUser').attr('value')==""){//弹出登录提示框
			//Boxy.alert("您还没有登录，请先登录！");
			//显示登录提示框
			$('#loginDiv').css('position','fixed').show();
		}else{//直接跳转到显示发布信息的Action
			
			window.location.href="gotoAddHouse.action";
		}
	});
	
	//隐藏登录提示框
	$("#hiddenButton").click(function(){
		$('#loginDiv').hide();
	});
	
	//登录
	$("#divLogin").click(function(){
		var username = $("#uName").val();
		var password = $("#uPass").val();
		if(username.length == 0 || password.length == 0 ){
			$("#msg").html("请输入用户名或密码");
			$("#msg").css("color","red");
			
			return false;
		}
		
		var params = {
			"user.username" : username,
			"user.password" : password,
		};
		$.ajax({ 
			url: "login",  
			dataType : "json",  
			type: "post",  
			data: params,
		    success:function(data){
			    if(data.msg == "true"){
			     	window.location.href = "gotoAddHouse";//根据实际业务修改
			    }else{
			   		$("#msg").html(data.msg);
			   		$("#msg").css("color","red");
			    } 
			},
			error: function() { 
				Boxy.alert("ajax执行失败"); 
			}
	    });
	});
});

//登录
function doLogin(type) {
 	var name =  $('#loginName').attr('value');
	var pw = $('#loginPassword').attr('value');
	var url = 'login.action?user.username='+name+'&user.password='+pw; 
	if (name != null && name!='' && pw != null && pw!='') {
		$.ajax({
			url: url,
			type:"post",
			dataType: "json",
			async: false,
			beforeSend: function(data) {
				$("#loginButton").attr("disabled", true);
			},
			success: function(data) {
				 if(data.user!=null){
					$("#loginUser").attr("value",data.user);
					$("#msg").html("&nbsp;<br>");		 
					var html = "<s:text name='welcomeTip'><s:param>"+data.user.username+"</s:param></s:text>&nbsp;&nbsp;&nbsp;<label class='ui-green'><input type='button' name='search' value='<s:text name="exit" />' onclick='document.location=\"logout.action\"'/></label>";
					$("#topLoginDiv").html(html);
				}else{
					$("#msg").html(data.msg);
					$("#msg").css("color","red");
				}
				
				$("#loginButton").attr("disabled", false);
			}
		});
	}else{
		Boxy.alert("请输入用户名或密码！");
	} 
	
	/* 获取当前登录用户信息  */
	var userurl = "judgeUser.action";
	$.ajax({
		url: userurl,
		data:{"username":name,
			  "password":pw},
		type:"post",
		dataType: "json",
		async: false,
	});
	
	//获取所有用户数
	var url = 'findUsers.action';
	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
	});
	
	//获取所有房屋数
	var url2 = 'findAllHouse.action';
	$.ajax({
		type:"post",
		url:url2,
		dataType:"html",
	});
	
	//刷新页面以获取登录用户
	history.go(0);
}

//查看我的发布
function searchInfo(){
	var userId = '<%=session.getAttribute("userId")%>';
	if('null' != userId){
		//将参数用户id传入action
		window.location.href="getUserHouse.action?userId="+userId;
	}else {
		Boxy.alert("您还没有登录，请先登录！");
	}
}

</script>
<div id="header" class="wrap">
	<s:hidden name="loginUser" id="loginUser" ></s:hidden>
	<div id="logo"><img src="<%=path%>/img/logo.jpg" /></div>
	
	<div >
		<div id="msg" align="center">&nbsp;<s:actionmessage/><br></div>
	</div>

	<div align="right">
		<form method="post" id="loginFrom" action="user!login.action">
			<s:if test="#session.LOGIN_USER==null">
			<div id="topLoginDiv" style="display: inline">
				<s:text name="username" />&nbsp;<s:textfield name="user.name" id="loginName" required="true" size="15"/>
				<s:text name="password" />&nbsp;<s:password name="user.password"  id="loginPassword" required="true" size="15"/>
				<label class="ui-green">
					<input type="button" name="loginButton" id="loginButton"  value="<s:text name='login' />" onclick="doLogin();" />
				</label>
				<label class="ui-green">
					<input type='button' value='<s:text name="register" />' onclick='document.location="<%=path%>/pages/register.jsp"'/>
				</label>
			</div>
			</s:if><s:else>
				<s:text name="welcomeTip"><s:param><s:property value="#session.LOGIN_USER.username"/></s:param></s:text>
				&nbsp;&nbsp;&nbsp;
				<label class='ui-green'>
					<input type='button' name='search' value='<s:text name="exit"/>' onclick='document.location="logout.action"'/>
				</label>
			</s:else>
			<div style="display: inline">
				<label class="ui-green">
					<input type="button" id="releaseButton" name="search" value="<s:text name='release' />" />
				</label>	
			</div>
			
			<div style="display: inline">
			<label class="ui-green">
				<input type="button" value='<s:text name="check"/>' onclick="searchInfo()" />
			</label>	
			</div>
			
		</form>
	</div>
</div>
<!-- 未登录弹窗 -->
<div id="loginDiv" style="display:none; border:4px solid #000;margin:0 auto;" >
	<div >
		<h3>&nbsp;您还没有登录！</h3>
		<table>
			<tr>
				<td nowrap="nowrap">
					<span>&nbsp;&nbsp;&nbsp;用户名：</span>
					<s:textfield name="user.username" id="uName"  required="true" size="15"/>
				</td>
			</tr>
			<tr>
				<td >
					<span>&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码：</span>
					<s:password name="user.password" id="uPass" required="true" size="15"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					&nbsp;&nbsp;&nbsp;
					<label class="ui-green">
						<input type="button" name="loginButton" id="divLogin" value="登录" />
					</label>&nbsp;
					<label class="ui-green">
						<input type='button' id="hiddenButton" value='取消' />
					</label>
				</td>
			</tr>
		</table>
	</div>
</div>
<!-- //未登录弹窗 -->
