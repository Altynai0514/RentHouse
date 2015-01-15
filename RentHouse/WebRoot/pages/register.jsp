<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>青鸟租房 - 用户注册</title>
	<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
	<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.js"></script>
</head>
<script type="text/javascript">

	//注册用户信息及时验证
	function validatePassword(){
		$("#validatePassword").html("");
		var password = $("#password").val().trim();
		if(password == "" || password.length == 0){
			$("#validatePassword").html("密码不能为空！");
		}else{
			if(password.length < 4){
				$("#validatePassword").html("密码长度至少为4位！");
			}
		}	
	}
	function validateRepassword(){
		$("#validateRepassword").html("");
		var password = $("#password").val().trim();
		var repassword = $("#repassword").val().trim();
		if(repassword == "" || repassword.length == 0){
			$("#validateRepassword").html("确认密码不能为空！");
		}else{
			if(password != repassword){
				$("#validateRepassword").html("密码和确认密码必须相同！");
			}
		}	
	}
	function validateTelephone(){
		$("#validateTelephone").html("");
		var telephone = $("#telephone").val().trim();
		var reg = "^(\d{3,4}-{0,1}(\d{7,8})$)";
		if(telephone == "" || telephone.length == 0){
			$("#validateTelephone").html("电话号码不能为空！");
		}else{
			if(!reg.test(telephone)){
				$("#validateTelephone").html("电话号码格式不正确！");
			}
		}
	}
	
	function validateName(tag){
		var name = $("#name").attr('value');// 获取输入框信息
		if(name.length != 0){
			var url = 'validateName.action?user.username='+name;
			//alert(tag);
			$.ajax({
				url: url,
				dataType: "json",
				success: function(data) {
					//alert(data);
					if(data=='true'){
						if(tag=='submit'){
							$("#formasdf").submit();
						}else{ 
							$("#validateName").html("用户名可以使用");
							$("#validateName").css("color","green");
						}
					}else{
						$("#validateName").html("用户名已存在");
						$("#validateName").css("color","red");
					} 
				}
			});
		}else{
			$("#validateName").html("用户名不能为空");
		}
	}
	
</script>

<body>
	<s:include value="header.jsp"></s:include>
	<div id="regLogin" class="wrap">
		<div class="dialog">
			<dl class="clearfix">
				<dt>新用户注册</dt>
				<dd class="past">填写个人信息</dd>
			</dl>
			<div class="box">
				<form action="register.action" id="formasdf">
					<div class="infos">
						<table>
							<tr>
								<td class="field">用 户 名：</td>
								<td><s:textfield  type="text" cssClass="text" name="user.username" id="name" onblur="validateName()" />&nbsp;<font color="#c00fff">*</font></td>
								<td><font color="red" ><s:fielderror fieldName="user.username" id ="vName"><div id="validateName"></div></s:fielderror></font></td>
							</tr>	
							<tr>
								<td class="field">密　　码：</td>
								<td><s:password type="password" cssClass="text" name="user.password" id="password" onblur="validatePassword()" />&nbsp;<font color="#c00fff">*</font></td>
								<td><font color="red" ><s:fielderror fieldName="user.password" ><div id="validatePassword"></div></s:fielderror></font></td>
							</tr>				
							<tr>
							<td class="field">确认密码：</td>
							<td><s:password type="password" cssClass="text" name="repassword" id="repassword" onblur="validateRepassword()" />&nbsp;<font color="#c00fff">*</font> </td>
							<td><font color="red" ><s:fielderror fieldName="repassword" ><div id="validateRepassword"></div></s:fielderror></font></td>
						</tr>	
						<tr>
							<td class="field">电　　话：</td>
							<td><s:textfield type="text" cssClass="text" name="user.telephone" id="telephone" onblur="validateTelephone()" />&nbsp;<font color="#c00fff">*</font> </td>
							<td><font color="red" ><s:fielderror fieldName="user.telephone" ><div id="validateTelephone"></div></s:fielderror></font></td>
						</tr>
						<tr>
							<td class="field">用户姓名：</td>
							<td><s:textfield type="text" cssClass="text" name="user.realname" id="realname" />&nbsp; </td>
							<td><font color="red" ><div id="validateRealname"></div></font></td>
						</tr>
						</table>
						<div class="buttons">
							<input type="button"  value="立即注册" onclick="validateName('submit')" />
							<!-- <input type="button" value="返回" onclick ="javascript:history.go(-1);" /> -->
							<input type="button" value="返回" onclick ="javascript:window.location.href='doSearch.action'" />
						</div>
					</div>
					<s:token/>
				</form>
			</div>
		</div>
	</div>
	<s:include value="footer.jsp"></s:include>
</body>
</html>