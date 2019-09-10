<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
</head>
<body class="easyui-layout">
<div id="result"></div>
<div><%=basePath%></div>
        <h2>管理员为班级和课程分配任课教师</h2>

<div style="width:700px;max-width:800px;margin:0 auto; border:1px dotted red;">
	<div style="padding:10px 40px;">
		<div style="margin-bottom:10px;float:left;width:250px;">
			<input class="easyui-combobox" id="teacherDepartment" style="width:100%;" data-options="
						url:'getTeacherDepartmentCombobox',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						label: '教师所属系:',
						labelPosition: 'left',
						onSelect: function(rec){
            				var url = 'getMajorByDepId/'+rec.id;
            				$('#teacherMajor').combobox('loadData',{});
            				$('#teacherMajor').combobox('enable');
            				$('#teacherMajor').combobox('clear');          			              					
            				$('#teacherMajor').combobox('reload', url);
       					}
			">
		</div>
		<div style="margin-bottom:10px;float:right;width:250px;">
			<input class="easyui-combobox" id="teacherMajor" style="width:100%;" data-options="
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						editable:true,
						disabled:true,
						label: '教师专业:',
						labelPosition: 'left'
			">
		</div>	
	</div>
	<div style="padding:10px 40px;">
		<div style="margin-bottom:10px;float:left;width:250px;">
			<input class="easyui-combobox" id="admissionYear" style="width:100%;" data-options="
						url:'getAdmissionYearCombobox',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						editable:false,
						label: '入学年度:',
						labelPosition: 'left'
			">
		</div>
		<div style="margin-bottom:10px;float:right;width:250px;">
			<input class="easyui-combobox" id="department" style="width:100%;" data-options="
						url:'getDepartmentCombobox',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						editable:true,
						label: '班级所属系：',
						labelPosition: 'left'
			">
		</div>
	</div>
	<div  style="padding:10px 40px;">
		<a href="javascript:OpenWindow();" class="easyui-linkbutton c1"  
				data-options="iconCls:'icon-search'" style="width:100%;height:24px;outline:0;">分配课程</a>
	</div>
</div>


<div id="w" class="easyui-window" title="Load Panel Content" style="width:1000px;height:400px;padding:10px;"
			data-options="modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				tools:[{
					iconCls:'icon-reload',
					handler:function(){

						$('#w').window('refresh', 'getCourseSelecting');
					}
				}]
			">
</div>
<script type="text/javascript">
function OpenWindow(){
	var selectedTeacherMajor = $('#teacherMajor').combobox('getText');
	var selectedAdmissionYear = $('#admissionYear').combobox('getText');
	var selectedDepartment = $('#department').combobox('getText');
	if(selectedTeacherMajor == ""){
		alert("请选择教师专业");
		return;
	}
	if(selectedAdmissionYear == ""){
		alert("请选择学生年份");
		return;
	}
	if(selectedDepartment == ""){
		alert("请选择学生所属系");
		return;
	}
	var url = 'getCourseSelecting/'+selectedTeacherMajor + '/'+ selectedAdmissionYear + '/'+ selectedDepartment;
	$('#w').window('refresh', url).window('open');

}
</script>
</body>
</html>


