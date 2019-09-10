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
	<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/resources/js/ajaxfileupload.js"></script>
</head>
<body>
<div id="result"></div>
<p>请先选择题型，试题导入前要查询相应的知识点是否已录入系统。</p>
<div style="width:700px;max-width:800px;margin:0 auto; border:1px dotted red;">
	<div style="padding:10px 40px;">
		<div style="margin-bottom:10px;float:left;width:250px;">
			<input class="easyui-combobox" id="questType" name="questType" style="width:100%;" data-options="
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						label:'请选择题型：',
						labelPosition:'left',
						data:[{
								id:1,text:'单选题'
							},{
								id:2,text:'多选题'
							},{
								id:3,text:'填空题'
							},{
								id:4,text:'判断题'
							}]">
		</div>
		<div style="margin-bottom:10px;float:right;width:250px;">
			<input class="easyui-combobox" id="field" name="field" style="width:100%;" data-options="
						url:'getFieldCombobox',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						label:'请选择题库：',
						labelPosition:'left',
						onSelect: function(rec){
								var questType = $('#questType').combobox('getText');
								if(questType == '' || questType == null){
									alert('请先选择题型，谢谢');
									$('#field').combobox('clear');
									return;
								}
            					var url = 'getChapterWithAll?field='+rec.id+'&withAllTag='+0;
            					$('#chapter').combobox('loadData',{});
            					$('#chapter').combobox('clear');          			              					
            					$('#chapter').combobox('reload', url);
       						}
						">
		</div>
	</div>
	<div style="padding:10px 40px;">
		<div style="margin-bottom:10px;float:left;width:250px;">
			<input class="easyui-combobox" id="chapter" name="chapter" style="width:100%;" data-options="
							valueField:'id',
							textField:'chapterName',
							label: '请选择章节：',
							labelPosition: 'left',						
							onSelect: function(rec){
								var field = $('#field').combobox('getText');
								var questType = $('#questType').combobox('getText');
								if(questType == '' || questType == null){
									alert('请先选择题型，谢谢');
									$('#chapter').combobox('clear');
									return;
								}
            					var url = 'getKnowledgePointCombobox?chapId='+rec.id;
            					$('#knowledgePoint').combobox('loadData',{});
            					$('#knowledgePoint').combobox('clear'); 
            					$('#knowledgePoint').combobox('reload', url);
						 }">
		</div>
		<div style="margin-bottom:10px;float:right;width:250px;">
			<input class="easyui-combobox" id="knowledgePoint" name="knowledgePoint" style="width:100%;" data-options="
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						editable:true,
						label: '请选择知识点：',
						labelPosition: 'left'
			">
		</div>
	</div>
	<div  style="padding:10px 40px;">
		<a href="javascript:FindData();" class="easyui-linkbutton c1"  
				data-options="iconCls:'icon-search'" style="width:100%;height:24px;outline:0;">查询</a>
	</div>
</div>

<div style="width:500px;margin:0 auto;">
	<div class="easyui-panel" title="Upload File" style="width:400px;padding:30px 20px 30px 20px">
		<div style="margin-bottom:20px">
			<div>Name:</div>
			<input class="easyui-textbox" style="width:100%">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-filebox" name="afile" label="File1:" labelPosition="top" data-options="prompt:'Choose a file...'" style="width:100%">
			
		</div>
		<div>
			<a class="easyui-linkbutton"  onclick="onclickfunc();" style="width:100%">上传文件</a>
		</div>
	</div>
</div>
<div style="width:100%;" id='dg'></div>
<script type="text/javascript">
var role='admin';

$('#dg').datagrid({
    url:'',
    title:"题目列表",
    fitColumns:true,
    striped:true,
    singleSelect:true,
    columns:[[
        {field:'questionId',title:'id',width:10},
        {field:'content.title',title:'题目',width:150,
        	formatter: function(value,row,index){

        		if(row.content.titleImg != ""){//alert(row.qc.titleImg);
				return '<img src=getQuestImg/'+ row.questionId+'?id=' + Math.random()+'/>';}
			}
        },
        {field:'answer',title:'答案',width:15},
       /*  {field:'knowledgePoint.kpDesc',title:'知识点描述',width:50,
        	formatter: function(value,row,index){
				if (row.kp){
					return row.kp.knowledgePointDesc;
				} else {
					return value;
				}
			}	
        }, */
        {field:'kpDescribe',title:'知识点描述',width:40},
        {field:'questionTypeName',title:'题型',width:30},
        {field:'creator',title:'作者',width:30},
        {field:'createTime',title:'createTime',width:30,
        	formatter: function(value,row,index){
       		 var time = new Date(row.createTime);
       		 var str = time.getFullYear()+"年"+(time.getMonth()+1)+"月"+(time.getDate())+"日"+(time.getHours())+"点";
				return str;
			}
        }
    ]]
});

function onclickfunc(){
	var fieldId = $('#field').combobox('getValue');
	var field = $('#field').combobox('getText');
	var questTypeId = $('#questType').combobox('getValue');
	var questType = $('#questType').combobox('getText');
	var chapterId = $('#chapter').combobox('getValue');
	var chapter = $('#chapter').combobox('getText');
	var kpId = $('#knowledgePoint').combobox('getValue');
	var knowledgePoint = $('#knowledgePoint').combobox('getText');
	if(field=="" || questType=="" || chapter=="" || knowledgePoint==""){
		alert("请选择导入的excel文件对应的知识点类型");
		return;
	}
	
	//因easyui切换页面问题，所以要获取新id
	var currentFilebox_file_id = $('input[name="afile"]').attr('id');
	$.ajaxFileUpload({
		//处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
		url:'fileUpload?field='+field+'&fieldId='+fieldId
				+'&questTypeId='+questTypeId+'&questType='+questType
				+'&chapterId='+chapterId
				+'&chapter='+chapter+'&knowledgePoint='+knowledgePoint + '&kpId='+kpId,
		secureuri:false,                       //是否启用安全提交,默认为false
		type:'post',
		fileElementId: [currentFilebox_file_id],    //文件选择框的id属性,刷新后默认是filebox_file_id_1。
		dataType:'json',
		success : function(data, status){        //服务器响应成功时的处理函数
				$('#result').html("文件上传成功<br/>");
				var message = data['message'];   //对应State.message
				var code = data['code'];		//对应State.code
				alert("mseesage = " +message);
					$('#result').html(message);
		},
		error : function(data,status,e) {
			var message = data['message'];   //对应State.message
	        alert(message);
	        $('#result').html(message);
	    }//服务器响应失败时的处理函数

	});
}

function FindData(){ 
	var selectedField = $('#field').combobox('getText');
	var selectedQuestType = $('#questType').combobox('getText');
	var selectedChapter = $('#chapter').combobox('getText');
	var selectedKnowledgePoint = $('#knowledgePoint').combobox('getText');
	if(selectedField =="" || selectedQuestType =="" || selectedChapter =="" || selectedKnowledgePoint ==""){
		alert("请选择知识点！");
		return;
	}
	
	var selectedKnowledgePointId = $('#knowledgePoint').combobox('getValue');
    $('#dg').datagrid({
    	url:'getQuestionDatagrid?knowledgePointId='+selectedKnowledgePointId,
    	queryParams: {
    		id: selectedKnowledgePointId
    	}
    	});
}  
</script>
</body>
</html>