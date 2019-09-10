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
			
	<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/homework-manage.js"></script>
	
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
								url:'getCourseComboboxForHw',
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
    <div id="toolbar">
        <a href="javascript:void(0);" class="easyui-linkbutton c3" iconCls="icon-add" plain="false" onclick="addHomework();">添加作业</a>
        <a href="javascript:void(0)" class="easyui-linkbutton c3" iconCls="icon-edit" plain="false" onclick="editHomework();">修改作业</a>
        <a href="javascript:void(0)" class="easyui-linkbutton c3" iconCls="icon-remove" plain="false" onclick="destroyHomework();">删除作业</a>
    </div>
</div>
<!-- 打开隐藏作业窗口 -->
<div id="wHomework" class="easyui-dialog" title="新建作业题" 
	data-options="iconCls:'icon-save',closed:true,draggable:true,
        modal:true,minimizable:true,maximizable:true" style="width:80%;height:400px;top:10px">
	
 	<div>
		<h2>教师在这里选择所出的题目，并生成作业题。</h2>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" id="hwName" style="width:40%" data-options="label:'作业名称:',required:true">
			<input class="easyui-textbox" id="hwDiscrib" style="width:40%;margin-left:10px" data-options="label:'作业描述:',required:true">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" id="class" name="class"
				   name="language"
				   style="width:50%"
				   data-options="
					url:'getClassCombobox',
					method:'get',
					valueField:'id',
					label: '作业发放班级:',
					textField:'text',
					multiple:true,
					panelHeight:'auto'
			">

		</div>
		<div style="margin:20px 0;float:left;width:200px;">
		<div class="easyui-panel" style="padding:10px">
			<ul id="kptree"></ul>
		</div>
			<div id="hwtotal">
			<p>已选择的题目总数为：<span id="questTotal">0</span>个，
				其中单选题：<span id="sinoptQuestTotal">0</span>个，
				多选题：<span id="mutioptQuestTotal">0</span>个，
				填空题：<span id="fillQuestTotal">0</span>个，
				判断题：<span id="judgeQuestTotal">0</span>个。</p>
			<div style="text-align:center;width:100px;padding:5px 0">
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">预览</a>      	
	        </div>	
		</div>
		</div>
		<div class="right_datagrid_area">
	    <div  id='dgQuestion'></div>
	    
		</div>
	</div>
	<div id="w" class="easyui-window" title="作业题预览" 
		data-options="iconCls:'icon-save',closed:true,modal:true" style="width:100%;height:100%;padding:10px;top:10px;">
		The window content.
	</div>
	
	
	
		<div id="homeworkclazz" class="easyui-window" title="班级作业情况预览" 
		data-options="iconCls:'icon-save',closed:true,modal:true" style="width:100%;height:100%;padding:10px;top:10px;">
		
		<div style="width:700px;max-width:800px;margin:0 auto; border:1px dotted red;">
	         <div style="padding:10px 40px;">
		         <div style="margin-bottom:10px;float:left;width:250px;">
					<input class="easyui-combobox" id="searchhomeworkclass" name="searchhomeworkclass"
				   style="width:100%"
				   data-options="
					url:'getClassCombobox',
					method:'get',
					valueField:'id',
					label: '作业发放班级:',
					textField:'text',
					multiple:false,
					panelHeight:'auto'
					">
		</div>
		<div style="margin-bottom:10px;float:right;width:250px;">
				<a href="javascript:FindHomeworkClassData();" class="easyui-linkbutton c1"  data-options="iconCls:'icon-search'" style="width:70%;height:24px;outline:0;">查 询</a>
		
		</div>
				
	</div>
	
	<div  style="padding:10px 40px;">
		<div style="width:100%;height:24px;outline:0;"></div>
	</div>
</div>
    <div  id='dgHomeworkClazz'></div>

	   </div>
	
		<div id="homeworkstudent" class="easyui-window" title="学生作业情况预览" 
		data-options="iconCls:'icon-save',closed:true,modal:true" style="width:100%;height:100%;padding:10px;top:10px;">
		
		<div style="width:700px;max-width:800px;margin:0 auto; border:1px dotted red;">
	         <div style="padding:10px 40px;">
		         <div style="margin-bottom:10px;float:left;width:250px;">
					<input class="easyui-textbox" id="stNo" style="width:100%" data-options="label:'学生编号:'">
		</div>
		<div style="margin-bottom:10px;float:right;width:250px;">
        <input class="easyui-textbox" id="stName" style="width:100%" data-options="label:'学生姓名:'">		
		</div>
				
	</div>
	
		<div  style="padding:10px 35%;" id="hwstFind">
		<div  onclick="FindHomeworkStudentData()" class="easyui-linkbutton"  data-options="iconCls:'icon-search'" style="width:80%;height:24px;outline:0;">查 询</div>
	</div>
</div>
<div  id='dgHomeworkStudent'></div>

	</div>
	
	
</div>		

<script type="text/javascript">

</script>
</body>
</html>