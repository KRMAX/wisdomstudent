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
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>沈阳城建学院在线答题考试系统</title>
    <script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.easy-pie-chart.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.countdown.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/resources/js/openhomework.js"></script>

    <link href='<c:url value="/resources/themes/openhomework/main.css"></c:url>'
          rel="stylesheet" type="text/css" />
    <link href='<c:url value="/resources/themes/openhomework/iconfont.css"></c:url>'
          rel="stylesheet" type="text/css" />
    <link href='<c:url value="/resources/themes/openhomework/test.css"></c:url>'
          rel="stylesheet" type="text/css" />


    <style>
        .hasBeenAnswer {
            background: #5d9cec;
            color:#fff;
        }
    </style>

</head>
<body>
<div class="main">
    <!--nr start-->
    <div class="test_main">
        <div class="nr_left">
            <div class="test">
                <form action="" method="post" id="context">
           <!--          <div class="test_title">
                        <p class="test_time">
                            <i class="icon iconfont">&#xe6fb;</i><b class="alt-1">请做完所有题目后交卷</b>
                        </p>
                        <font><input type="button" name="test_jiaojuan"  onClick="onSumbit();" value="交卷"></font>
                    </div> -->



                </form>
            </div>

        </div>
        <div class="nr_right">
            <div class="nr_rt_main">
                <div class="rt_nr1" id="questionCardContent">
      <!--               <div class="rt_nr1_title">
                        <h1>
                            <i class="icon iconfont">&#xe692;</i>答题卡
                        </h1>
                        <p class="test_time">
                            <i class="icon iconfont">&#xe6fb;</i><b class="alt-1">无时限</b>
                        </p>
                    </div> -->



                </div>

            </div>
        </div>
    </div>
    <!--nr end-->
   <div class="foot" style="margin-top:10px">

       <%--  <div style="text-align:center;margin:0 0 50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
            <p>适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。</p>
            <p>作者：周末老师</p>
        </div>--%>

    </div>
</div>


<script>
    var flag=0;
    window.jQuery(function($) {
        "use strict";

        $('time').countDown({
            with_separators : false
        });
        $('.alt-1').countDown({
            css_class : 'countdown-alt-1'
        });
        $('.alt-2').countDown({
            css_class : 'countdown-alt-2'
        });

    });


    $(function() {
        $('.test_content_nr_main label').click(function() {
            debugger;
            var examId = $(this).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
            var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
            console.log(examId);
            console.log(cardLi);
            // 设置已答题
            if(!cardLi.hasClass('hasBeenAnswer')){
                cardLi.addClass('hasBeenAnswer');
            }

        });
    });


    function onSumbit(){
    	if(window.confirm('您确定要提交作业吗？提交后不可重复作答！')){
    		 var ids=getQueryString("ids");
		        var radio_value = [];
		        var homeworkid=getQueryString("homeworkid");
		        var value=[];
		        value.push(homeworkid);
		        var val=$('input:radio:checked').each(function(){

		            //alert($(this).attr('name'));//获取radio的name
		            radio_value.push($(this).attr('name')+":"+$(this).val());
		            value.push($(this).attr('name')+":"+$(this).val());
		        });

		        //alert("单选题答案：" + radio_value);
		       

		        var chk_value =[];
		        $('input:checkbox:checked').each(function(){
		            //chk_value.push($(this).val());
		            chk_value.push($(this).attr('name')+":"+ $(this).val());
		            //alert($(this).attr('name'));//获取radio的name
		            //chk_value.push($(this).val());
		            value.push($(this).attr('name')+":"+$(this).val());
		        });
		        //alert("多选题答案：" + chk_value);
		        var dataArry = new Object();
		        dataArry.sinQuestion=radio_value;
		        dataArry.mutiQuestion=chk_value;
		        var dataJson = JSON.stringify(dataArry);
		        var list=JSON.stringify(value);
		        $.ajax({
		            type: 'POST',
		            //url: role + '/addHomework',
		            url: 'countScore',
		            data: list,
		            contentType: "application/json; charset=utf-8",
		            dataType: 'json',
		            success: function (data) { 
		            	console.log(data);
		                if (data.success) {
		                	Closepage();
		                	refreshParent();
		                }

		            },
		        });
         }else{
        	 alert('请继续答题！');
        }   	    	
        

    };
    
    function customalert(custom){
        console.log($('#qu_1_1'));
        var data=custom.split(";");
        var type=data[0];
        var index=data[1];
        var examId;
        if(type=='sin'){
            examId="qu_1_"+index;
        }else if(type="muti"){
            examId="qu_2_"+index;
        }


        var cardLi = $('#kard_'+examId);
        console.log(cardLi);
        if(!cardLi.hasClass('hasBeenAnswer')){
            cardLi.addClass('hasBeenAnswer');
        }


    }
    
    function Closepage() {
    	window.close(); 
        if (window.opener && !window.opener.closed) {
            window.parent.opener.location.reload();
        }
        
     
    }
    
    function refreshParent() {
    	  window.opener.location.href = window.opener.location.href;
    	  window.close();  
    	 }  
</script>

<%--<div style="text-align:center;margin:0 0 50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
    <p>适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。</p>
    <p>作者：周末老师</p>
</div>--%>
</body>
</html>
