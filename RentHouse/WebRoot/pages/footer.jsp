<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<<script type="text/javascript">

function showMsg(){
	var username = '<%=session.getAttribute("username")%>';
	var password = '<%=session.getAttribute("password")%>';
	if('admin' == username && 'admin' == password){
		//获取所有用户数
		var userNum = '<%=session.getAttribute("userNum")%>';
		var houseNum = '<%=session.getAttribute("houseNum")%>';
		Boxy.alert("网站注册用户数："+userNum+"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp网站发布房屋数："+houseNum);
	}else{
		Boxy.alert("您不具有此权限！");
	}
}
</script>
<div id="footer" class="wrap">
	<dl>
    	<dt>青鸟租房 · 专业的房屋出租网站 &copy; <%=session.getAttribute("username")%>  徐平正   </dt>
        <dd>关于我们 · 联系方式 · 意见反馈 · 帮助中心 · <a onclick="showMsg()">信息统计</a></dd>
    </dl>
</div>
