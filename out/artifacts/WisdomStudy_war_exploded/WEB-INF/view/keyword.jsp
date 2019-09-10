<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>		
	<div style="width:700px;max-width:800px;height:100px;margin:0 auto; border:1px dotted red;">
		<div style="padding:10px 20px;">
			<div style="margin-bottom:10px;float:left;width:250px;">
				<input id="keywordName" class="easyui-textbox" label="关键字:" labelPosition="left" data-options="prompt:'请输入关键字'" style="width:100%;">
			</div>
			<div style="margin-bottom:10px;float:right;width:250px;">
				<a href="javascript:FindData();" class="easyui-linkbutton" iconCls="icon-ok" style="width:50%;;outline:0;">查 询</a>		
			</div>	
		</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" id="keyworddg" title="关键字表格" style="width:100%;height:450px"
			data-options="	rownumbers:true,
							singleSelect:true,
							url:'getKeywordDatagrid',
							method:'get',
							toolbar:keywordToolbar,
							pagination:true">
		<thead>
			<tr>
				<th data-options="field:'id',width:240,align:'center'">关键字ID</th>
				<th data-options="field:'keywordName',width:240,align:'center'">关键字名称</th>
			</tr>
		</thead>
	</table>
	
	<!-- 以下是隐藏控件[begin] -->
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons"  draggable="false" modal="true">
        <div class="ftitle">关键字信息</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>关键字：</label>
                <input id="keywordAdd" name="keywordName" class="easyui-textbox" 
                	data-options="prompt:'请输入关键字...'" required="true">
            </div>         
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveKeyword();" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
    <!-- 以上是隐藏控件[end] -->
<script type="text/javascript">
	var keywordToolbar = [{
		text:'添加',
		iconCls:'icon-add',
		handler:function(){addKeyword();}
	},'-',{
		text:'删除',
		iconCls:'icon-cut',
		handler:function(){alert('删除关键字！')}
	},'-',{
		text:'Save',
		iconCls:'icon-save',
		handler:function(){alert('save')}
	}];
	var url;//定义全局变量
		
	function FindData(){ 
		var keywordName = $('#keywordName').textbox('getText');
		alert(keywordName);
		if(keywordName == null){
			keywordName = "";
		}
		$('#keyworddg').datagrid({
		    url:'getKeywordDatagrid',
		    queryParams: {
		    	keywordName: keywordName
		    }
		    });  
	}
		
	function addKeyword(){
		$('#dlg').dialog('open').dialog('center').dialog('setTitle','添加关键字');
		$('#fm').form('clear');
		url="disposeKeyword";
	}
	function saveKeyword(){
		
	    $('#fm').form('submit',{
	        url: url,
	        onSubmit: function(param){
	        	//param.instituteNew =  $('#instituteAdd').combobox('getText');//默认combobox向后台传递的是value，不是text，目前手工添加text
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
	                 $('#keyworddg').datagrid('reload');    // reload the user data
	                
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
	        }
	    });
	}
		
	</script>
<style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
</style>