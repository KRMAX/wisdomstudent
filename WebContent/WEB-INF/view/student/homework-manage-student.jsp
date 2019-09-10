<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%	String contextPath = request.getContextPath();
	String url = request.getRequestURI();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ contextPath + "/";
	%>
<html>
<head>
	<meta charset="UTF-8">
	<title>Full Layout - jQuery EasyUI Demo</title>
	<link href='<c:url value="/resources/themes/examtest/main.css"></c:url>'
			rel="stylesheet" type="text/css" />
	<link href='<c:url value="/resources/themes/examtest/test.css"></c:url>'
			rel="stylesheet" type="text/css" />
	<link href='<c:url value="/resources/themes/bootstrap/easyui.css"></c:url>'
			rel="stylesheet" type="text/css" />
	<link href='<c:url value="/resources/themes/icon.css"></c:url>' 
			rel="stylesheet" type="text/css">
	<link href='<c:url value="/resources/themes/color.css"></c:url>'
			rel="stylesheet" type="text/css">
	<link href='<c:url value="/resources/themes/cexo.css"></c:url>'
			rel="stylesheet" type="text/css">
	<link href='<c:url value="/resources/themes/demo.css"></c:url>' 
			rel="stylesheet" type="text/css">
			

	
	<style type="text/css">
	.normal_main{width:100%;padding:15px auto 0;}
	.right_datagrid_area{width:100%;margin-left:100px;height:900px; margin-bottom:10px;padding-top:20px;
	}
	</style>
</head>
<body>

<div style="width:700px;max-width:800px;margin:0 auto; border:1px dotted red;">
		<div style="padding:10px 40px;">
		<div style="margin-bottom:10px;float:left;width:250px;">
			<input class="easyui-combobox" id="course" name="course"  style="width:100%;" 
				data-options="	valueField:'id',
								textField:'text',
								panelHeight:'auto',
								editable:false,						
								url:'getCourseComboboxForSt',
								label: '选择课程：',
								labelPosition: 'left'				
								">
		</div>
		<div style="margin-bottom:10px;float:right;width:250px;">
				<a href="javascript:FindData();" class="easyui-linkbutton c1"  data-options="iconCls:'icon-search'" style="width:70%;height:24px;outline:0;">查 询</a>
		
		</div>
				
	</div>
	
	<div  style="padding:10px 40px;">
		<div style="width:100%;height:24px;outline:0;"></div>
	</div>
</div>


<div class="normal_main">
	<div  id='dgHomework'></div>

</div>
	

<script type="text/javascript">

</script>
</body>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/homework-manage-student.js"></script>
</html>