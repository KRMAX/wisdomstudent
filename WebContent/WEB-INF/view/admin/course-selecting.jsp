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

    	 <form id="fm" method="post">
        <div style="width:700px;max-width:1000px;margin:0 auto;">
            <div class="left">
                <table>
                	<c:forEach items="${teacherList}" var="teacher">
                		<tr>
                			<td> <div data-id="${teacher.id}" class="item">${teacher.name}</div></td>
                		</tr>
                	</c:forEach>
                </table>
            </div>
            <div class="right">
                <table>
                    <tr>
                        <td class="blank"></td>
                        <c:forEach items="${courseList}" var="course">
                        	<td class="title">${course.name }</td>
                        </c:forEach>
                    </tr>
                    
                    	<c:forEach items="${clazzList}" var="clazz">
                    	<tr>
                    		<td class="time">${clazz.clazzName }</td>
                    		<%-- <c:forEach begin="1" end="${courseList.size() }"> --%>
                    		<c:forEach items="${courseList}" var="course">
                    			<td data-clazzId="${clazz.id }" data-courseId="${course.id}" class="drop">
                    				<%--对每个单元格，判断是否已有选课记录 --%>
                    				<c:forEach items="${scHasChozenList}" var="sc">                  				
                    				<c:if test="${sc.clazzId == clazz.id && sc.courseId == course.id}">                  				
                    					${sc.teacherName}<p style="display:none;">${sc.teacherId }</p>
                    				</c:if>
                    				</c:forEach>
                    			</td>
                    		</c:forEach>
                    	</tr>
                    	</c:forEach>
 
                    <tr>
                        <td class="time">13:00</td>
                        <td class="lunch" colspan="5">Lunch</td>
                    </tr>
                </table>
            </div>
		</div>
		</form>
        <div style="text-align:center;margin:0 auto;clear:both;padding:5px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton c7" onclick="submitForm()" style="width:150px">提交</a>      	
        </div>
               
        <style type="text/css">
            .left{
                width:120px;
                float:left;
            }
            .left table{
                background:#E0ECFF;
            }
            .left td{
                background:#eee;
				padding-bottom:5px;
            }
            .right{
                float:right;
                width:570px;
            }
            .right table{
                background:#E0ECFF;
                width:100%;
            }

            .right td{
                text-align:center;
                background:#637c3d;
                color:#fff;
				width:100px;
				padding-top:3px;
				padding-bottom:3px;
				font-size:16px;
            }
			.right td.title{
				
			}
            .right td.drop{
                background:#fafafa;
                color:#000;
                width:100px;
				
            }
            .right td.over{
                background:#FBEC88;
            }
            .item{
                text-align:center;
                background:#563d7c;
                color:#fff;
                width:100px;
				padding-top:3px;
				padding-bottom:3px;
				font-size:16px;
            }
            .assigned{
                border:1px solid #637c3d;
				margin:0 auto;
            }
            .trash{
                background-color:red;
            }
        </style>
<script type="text/javascript">

function submitForm(){

	var courseSelectArr = new Array();
	//从left.item复制到right.drop单元格中的对象都会带.assigned样式，这些对象是最后要统计的，会带3个自定义属性。
	if($('.assigned').length == 0){
		alert("没有选课记录，请选课！");
		return;
	}
	
    for(var i=0;i<$('.assigned').length;i++){
    	courseSelectArr[i] = new Array();
		var attrs = $('.assigned').eq(i);
		// For some browsers, `attr` is undefined; for others, `attr` is false. Check for both.
		if (typeof attrs !== typeof undefined && attrs !== false) {
        	// Element has this attribute
			//alert("教师:"+attrs.attr('data-id')+"班级:"+attrs.prop("data-clazzId")+"课程:"+attrs.prop("data-courseId"));
			courseSelectArr[i]= {teacherId:attrs.attr('data-id'),clazzId:attrs.prop("data-clazzId"),courseId:attrs.prop("data-courseId")};
		}
     }
    //添加不含有.assigned样式的历史已分配选课记录
    var assignedArr = new Array();//已分配的选课记录，初始化时先存入此数组
    for(var i=0;i<$('.drop').length;i++){
    	//先去掉含有div的新分配的记录
    	if( !$('.drop').eq(i).find('div').hasClass('assigned')){
    		var text = $('.drop').eq(i).find('p').text().trim();//p元素中保存的是已分配选课记录中的teacherId
    		//再去掉内容为空的td
    		if(text != null && text !=""){
    			//alert(text);
    			assignedArr[i] = new Array();
    			var attrs = $('.drop').eq(i);
    			if (typeof attrs !== typeof undefined && attrs !== false) {
    	        	// Element has this attribute					
    				assignedArr[i]= {teacherId:text,clazzId:attrs.attr("data-clazzId"),courseId:attrs.attr("data-courseId")};
    			}
    		}
    	} 
		
	}
	//合并两个数组
    for(var i=0;i<assignedArr.length;i++){
    	if(assignedArr[i] != null)
    		courseSelectArr.push(assignedArr[i]);
    }
    
	var courseSelectArrJson = JSON.stringify(courseSelectArr);
	alert("courseSelectArrJson = " + courseSelectArrJson);	
	
	//将三属性存入数组，JSON序列化后传递给后台，后台用selectCourseResult对象接收。
	$.ajax({
		type:'post',
        url:'<%=basePath%>submitCourseSelecting',
        contentType: "application/json; charset=utf-8", 
        data: courseSelectArrJson,
        dataType:'json',//服务器返回的数据类型 可选XML ,Json jsonp script htmltext等
        success:function(msg){
            if (msg.success){
            	$.messager.show({
                    title: '提示信息',
                    msg: msg.successMsg
                });
            	 $('#w').window('close');        // close the dialog
                // $('#clazzdg').datagrid('reload');    // reload the user data
                
            } else {
            	$.messager.show({
                    title: '错误信息',
                    timeout: 1000,  //定义自动关闭时限，0 means no close.
                    showType: 'fade',  //定义如何将显示消息窗口。可用的值是：null,slide,fade,show。默认值slide
                    msg: msg.errorMsg,
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
	
     }) 
	
}
            $(function(){
            	

                $('.left .item').draggable({
                    revert:true,
                    proxy:'clone'
                });
                $('.right td.drop').droppable({
                    accept: '.item',
                    onDragEnter:function(){
                        $(this).addClass('over');
                    },
                    onDragLeave:function(){
                        $(this).removeClass('over');
                    },
                    onDrop:function(e,source){
                        $(this).removeClass('over');
                        
                        if ($(source).hasClass('assigned')){
                        	//如果是从其他单元格转移过来的克隆对象，则调整其clazzId、courseId为新单元格的clazzId、courseId
                        	$(source).prop("data-clazzId",$(this).attr("data-clazzId"));
                        	$(source).prop("data-courseId",$(this).attr("data-courseId"));
                            $(this).empty().append(source);//与官网demo不同，一个单元格内只能有一个老师
                            $(source).draggable({
                                revert:true
                            });
                        } else {
                            var c = $(source).clone().addClass('assigned');
                         	//把教师对象克隆后赋予克隆对象目标单元格的clazzId、courseId
                        	c.prop("data-clazzId",$(this).attr("data-clazzId"));    
                        	c.prop("data-courseId",$(this).attr("data-courseId"));
                            $(this).empty().append(c);

                            c.draggable({
                                revert:true
                            });
                        }
                    }
                });
                $('.left').droppable({
                    accept:'.assigned',
                    onDragEnter:function(e,source){
                        $(source).addClass('trash');
                    },
                    onDragLeave:function(e,source){
                        $(source).removeClass('trash');
                    },
                    onDrop:function(e,source){
                        $(source).remove();
                    }
                });
            });
        </script>

</body>
</html>

