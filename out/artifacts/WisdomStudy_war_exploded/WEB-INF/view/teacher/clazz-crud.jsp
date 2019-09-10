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
<body>
<div id="result"></div>
<div style="width:700px;max-width:800px;margin:0 auto; border:1px dotted red;">
	<div style="padding:10px 40px;">
		<div style="margin-bottom:10px;float:left;width:250px;">
			<input class="easyui-textbox" id="clazzName" label="班级名称:" labelPosition="left" 
					data-options="prompt:'请输入班级名称...'" style="width:100%;">
		</div>
		<div style="margin-bottom:10px;float:right;width:250px;">
			<input class="easyui-combobox" id="major" style="width:100%;" data-options="
						url:'getMajorCombobox',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						editable:true,
						label: '所学专业:',
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
						label: '所属系：',
						labelPosition: 'left'
			">
		</div>
	</div>
	<div  style="padding:10px 40px;">
		<a href="javascript:FindData();" class="easyui-linkbutton c1"  data-options="iconCls:'icon-search'" style="width:100%;height:24px;outline:0;">查 询</a>
	</div>
</div>
<div class="normal_main">

	<div class="datagrid_area">
    <table id="clazzdg" title="班级列表" class="easyui-datagrid" style="width:100%;height:500px;"
            url="getClazzDatagrid"
            toolbar="#toolbar" pagination="true" striped="true" 	 
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" width="20">班级id</th>
                <th field="clazzName" width="30" style="border:0px;">班级名称</th>
                <th field="major" width="30" style="border:0px;">专业</th>
                <th field="department" width="20">所属系</th>
                <th field="admissionYear" width="20">入学年度</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0);" class="easyui-linkbutton c3" iconCls="icon-add" plain="false" onclick="addClazz();">添加班级</a>
        <a href="javascript:void(0)" class="easyui-linkbutton c3" iconCls="icon-edit" plain="false" onclick="editClazz();">修改班级</a>
        <a href="javascript:void(0)" class="easyui-linkbutton c3" iconCls="icon-remove" plain="false" onclick="destroyClazz();">删除班级</a>
        <span>用excel文件批量导入：</span>
        <a href="javascript:void(0);" class="easyui-linkbutton c7" iconCls="icon-add" plain="false" onclick="downloadClazzTemplete();">下载模板</a>
        <a href="javascript:void(0);" class="easyui-linkbutton c7" iconCls="icon-add" plain="false"  onclick="openUploadClazz();">批量上传班级</a>   
    </div>
	</div>
    <!-- 以下是隐藏控件[begin] -->
    <div id="dlg" class="easyui-dialog" style="width:600px;height:350px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons"  draggable="false" modal="true">
        <div class="ftitle">班级信息</div>
        <form id="fm" method="post">
            <div class="fitem">
                <label>班级名称</label>
                <input id="clazzNameAdd" name="clazzName" class="easyui-textbox" 
                	data-options="prompt:'请输入班级名称...'" required="true">
            </div>
            <div class="fitem">
                <label>所属学院</label>
                <input class="easyui-combobox" id="departmentAdd" name="department" required="true"
					data-options="	valueField:'id',
									textField:'text',
									height:30,
									editable:false,
									panelHeight:'auto',							
									url:'getDepartmentCombobox',
									onSelect: function(rec){
            							var url = 'getMajorCombobox?department='+rec.text;
            							$('#majorAdd').combobox('loadData',{});
            							$('#majorAdd').combobox('clear'); 			              					
            							$('#majorAdd').combobox('reload', url);
       								}"
									>
			 </div>
            <div class="fitem">
                <label>所属系</label>
                <input class="easyui-combobox" id="majorAdd" name="major" 
					data-options="	valueField:'id',
									textField:'text',
									height:30,
									editable:false,
									panelHeight:'auto',							
									url:'getMajorCombobox'" required="true"
									
									>
			 </div>
            <div class="fitem">
				<label>入学年度</label>		
                 <input class="easyui-combobox" id="admissionYearAdd" name="admissionYear" 
					data-options="	valueField:'id',
									textField:'text',
									height:30,
									editable:false,
									panelHeight:'auto',								
									url:'getAdmissionYearCombobox'" required="true">
            </div>         
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-ok" onclick="saveClazz();" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
    <!-- 以上是隐藏控件[end] -->
</div>
<!-- datagrid中点击右键弹出的菜单[begin] -->
<div id="menu" class="easyui-menu" style="width: 30px; display: none;">  
	<!--放置一个隐藏的菜单Div-->  
	<div id="btn_More" data-options="iconCls:'icon-remove'" onclick="MoreInfo()">你想多了！</div>        
</div>  
<!-- datagrid中点击右键弹出的菜单[end] -->

<!-- 隐藏控件--文件上传/下载[begin] -->
<div id="uploaddlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px"
            title="文件上传" closed="true" draggable="false" modal="true">
	<div style="width:100%;padding:10px 0">
		<div class="ftitle">请上传excel类型文件（excel2003-2007）：</div>
		<div class="fitem">
			<div>已上传的文件名:</div>
			<input id="uploadedFilename" class="easyui-textbox" style="width:100%">
		</div>
		<div style="margin-bottom:20px">
			<input id="afile" class="easyui-filebox" name="afile" label="文件名:" labelPosition="top"
				data-options="prompt:'请选择一个文件...'" style="width:100%">
		</div>
		<div style="margin-bottom:40px"></div>
		<div class="fitem">
			<a class="easyui-linkbutton"  onclick="uploadClazz();" style="width:100%">上传文件</a>
		</div>
	</div>
</div>
<!-- 隐藏控件--文件上传/下载[end] -->	
<script type="text/javascript">
var url;//定义全局变量
var rowdata;//存放行数据，用于校验是否form表单是否发生改变
var role = "teacher";

function onclickfunc(){
	var selectedField = $('#field').combobox('getText');
	var selectedQuestType = $('#questType').combobox('getText');
	var selectedChapter = $('#chapter').combobox('getText');

}

function FindData(){ 
	var selectedMajor = $('#major').combobox('getText');
	var selectedClazzName = $('#clazzName').combobox('getText');
	var selectedAdmissionYear = $('#admissionYear').combobox('getText');
	var selectedDepartment = $('#department').combobox('getText');
	alert($('#department').combobox('getText'));
	return;
    $('#clazzdg').datagrid({
    	url:'getClazzDatagrid',
    	queryParams: {
    		major: selectedMajor,
    		clazzName: selectedClazzName,
    		admissionYear: selectedAdmissionYear,
    		department: selectedDepartment
    	}
    	});  
}  
$(document).ready(function(){
	// datagrid中点击右键弹出的菜单[begin]
	 $("#clazzdg").datagrid({
		onRowContextMenu: function (e, rowIndex, rowData) { //右键时触发事件  
        //三个参数：e里面的内容很多，真心不明白，rowIndex就是当前点击时所在行的索引，rowData当前行的数据  
        e.preventDefault(); //阻止浏览器捕获右键事件  
        $(this).datagrid("clearSelections"); //取消所有选中项  
        $(this).datagrid("selectRow", rowIndex); //根据索引选中该行  
        $('#menu').menu('show', {  
            //显示右键菜单  
            left: e.pageX,//在鼠标点击处显示菜单  
            top: e.pageY  
        });  
        e.preventDefault();  //阻止浏览器自带的右键菜单弹出  
    }
	 });
	// datagrid中点击右键弹出的菜单[end]
})

function addClazz(){

    $('#dlg').dialog('open').dialog('center').dialog('setTitle','添加班级');
	
    $('#fm').form('clear');
    url="disposeClazz";
}    
function editClazz(){
    var row = $('#clazzdg').datagrid('getSelected');

    if (row){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','修改班级信息');

        rowdata = row;
        //$('#fmEdit').form('clear');                 
        $('#fm').form('load',row);//此句起作用              
       url = 'disposeClazz?id='+row.id ;//row.id就是后台传给前台数据时班级对象的id属性。
    }else{
    	alert("请选择一行数据，谢谢！");
    }
}

function saveClazz(){
	//判断是否表单内容发生变化[begin]
	$('#fm').data('changed',false);
	var row = $("#fm").serializeArray();
	$.each(row,function(index,value){
    	if(rowdata[value.name] != value.value){
        	$('#fm').data('changed',true);
        }
   	});
	if(!$("#fm").data("changed")){
		$.messager.alert('温馨提示', '没有发生数据参数变化');
		return;
	}
	//判断是否表单内容发生变化[end]
	
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(param){
        	param.majorAdd =  $('#majorAdd').combobox('getText');//默认combobox向后台传递的是value，不是text，目前手工添加text
        	param.departmentAdd =  $('#departmentAdd').combobox('getText');
        	param.admissionYearAdd =  $('#admissionYearAdd').combobox('getText');
        	return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');       
            if (result.success){
            	$.messager.show({
                    title: '提示信息',
                    msg: result.successMsg
                });
            	 $('#dlg').dialog('close');        // close the dialog
                 $('#clazzdg').datagrid('reload');    // reload the user data
                
            } else {
            	$.messager.show({
                    title: '错误信息',
                    timeout: 1000,  //定义自动关闭时限，0 means no close.
                    showType: 'fade',  //定义如何将显示消息窗口。可用的值是：null,slide,fade,show。默认值slide
                    msg: result.errorMsg,
                    style: { // 通过style改变位置
                        top:document.documentElement.offsetTop+20,
                        color:'red' //更改颜色没起作用2016-4-17
                     }
                });
            }
        },
        error: function(xhr,status,msg){
            alert("状态码"+status+"; "+msg)
            //layer.msg('玩命加载中..');
         }
    });
}
 
function destroyClazz(){
    var row = $('#clazzdg').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','确定要删除此班级吗?，如果删除，会级联删除该班级包含的所有学生信息、选课信息等！',function(r){
            if (r){
                $.post('admin/destroyClazz',{id:row.id},function(result){
                    if (result.success){
                    	
                        $('#clazzdg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    }
                },'json');
            }
        });
    }
}

function openUploadClazz(){
	$('#uploaddlg').dialog('open').dialog('center');
	$("#afile").filebox('clear');//手工清空filebox组件的值
}
function uploadClazz(){
	if($("#afile").filebox('getValue')==''){
		alert("请添加文件！");
		return ;
	}
	//因easyui切换页面问题，所以要获取新id
	var currentFilebox_file_id = $('input[name="afile"]').attr('id');
	$.ajaxFileUpload({
		//处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
		url:'uploadClazz',
		secureuri:false,                       //是否启用安全提交,默认为false
		type:'post',
		fileElementId: [currentFilebox_file_id],    //文件选择框的id属性,刷新后默认是filebox_file_id_1。
		dataType:'json',
		success : function(data, status){        //服务器响应成功时的处理函数				
				var message = data['message'];   //对应State.message
				var code = data['code'];		//对应State.code
				if(code == 200){
					var uploadedFile = $('#uploadedFilename').textbox('getValue');
					uploadedFile = $('input[name="afile"]').val() + ";" + uploadedFile;
					$('#uploadedFilename').textbox('setValue', uploadedFile);
				}
				$("#afile").filebox('clear');
				$('#result').html(message);
		},
		error : function(data,status,e) {
			alert(e);
	        $('#result').html(status);
	    }//服务器响应失败时的处理函数

	});
}
</script>

    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        #w{
        	margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:16px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:20px;
            border-bottom:5px solid #a26ea1;
        }
        .fitem{
            margin-bottom:20px;
        }
        .fitem label{
            display:inline-block;
            width:100px;
        }
        .fitem input{
            width:350px;
        }
    </style>
</body>
</html>

