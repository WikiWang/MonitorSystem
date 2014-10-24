<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>阈值监控展示</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="css/spline.css">
	
<script src="js/jquery.1.9.1.min.js"></script>
<script src="js/highcharts.js"></script>
<script src="js/exporting.js"></script>
<script language="javascript" src="js/drawSpline.js" charset="GBK"></script>

</head>

<body>
	<div class="column1">
		<a>监控数据</a> <select id="dataSelect" name="dataSelect">
			<option value="1" selected="selected">北京联通平均失败率25日图</option>
			<option value="2">北京联通平均延时25日图</option>
			<option value="3">黑龙江移动平均失败率25日图</option>
			<option value="4">黑龙江移动平均延时25日图</option>
		</select>
		<input id="submit1" type="button" value="确定" onclick="change()">
		<a>阈值上限:</a> 
		<input type="text" name="upperLimit" id="upperLimit">
		<input id="submit2" type="button" value="修改" onclick="changeUpperLimit()">		
	</div>	
	<div>
		<a>当前时间：</a>
		<label id="time"></label>
		<input id="submit3" type="button" value="下一天" onclick="nextDay()">
	</div>
	<div id="container" class="spline"></div>
	<div class="alarm">
		<label>异常信息</label>
		<table id="table_alarm" border="1">
			<tr>
				<td>异常出现时间</td>
				<td>异常值</td>
				<td>操作</td>
			</tr>					
		</table>		
	</div>
</body>
</html>
