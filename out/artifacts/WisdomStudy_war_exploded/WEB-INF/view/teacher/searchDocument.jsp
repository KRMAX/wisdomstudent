<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>		
			<div style="width:700px;max-width:800px;margin:0 auto; border:1px dotted red;">
			<div style="padding:10px 40px;">
				<div style="margin-bottom:10px;float:left;width:250px;">
					<input class="easyui-textbox" label="文件名:" labelPosition="left" data-options="prompt:'Enter a email address...'" style="width:100%;">
				</div>
				<div style="margin-bottom:10px;float:right;width:250px;">
					<input class="easyui-combobox" name="keyword" style="width:100%;" data-options="
						url:'demo/combobox/combobox_data1.json',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						label: '关键字:',
						labelPosition: 'left'
					">
				</div>
				
			</div>
			<div style="padding:10px 40px;">
				<div style="margin-bottom:10px;float:left;width:250px;">
					<input id="referenceNumber" class="easyui-textbox" label="文号:" labelPosition="left" style="width:100%;">
				</div>
				<div style="margin-bottom:10px;float:right;width:250px;">
					<input id="serialNum" class="easyui-textbox" label="文件顺序号:" labelPosition="left" style="width:100%;">
				</div>
			</div>
			<div style="padding:10px 40px;">
				<div style="margin-bottom:10px;float:left;width:250px;">
					<input id="issuingAgency" class="easyui-textbox" label="发文机关:" labelPosition="left" style="width:100%;">
				</div>
				<div style="margin-bottom:10px;float:right;width:250px;">
					<input class="easyui-combobox" name="dispatchLevel" style="width:100%;" data-options="
						data: [
							{value:1,text:'国家级'},<!-- national -->
							{value:2,text:'省级'},<!-- provincial -->
							{value:3,text:'市级'}<!-- prefectural -->
						],
						panelHeight:'auto',
						label: '发文级别:',
						labelPosition: 'left'
					">
				</div>
			</div>
			<div style="padding:10px 40px;">
				<div style="margin-bottom:10px;float:left;width:250px;">
					<input class="easyui-combobox" name="effectiveness" style="width:100%;" data-options="
						url:'demo/combobox/combobox_data1.json',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						label: '文件效力:',
						labelPosition: 'left'
					">
				</div>
				<div style="margin-bottom:10px;float:right;width:250px;">
					<input class="easyui-combobox" name="organizationType" style="width:100%;" data-options="
						url:'demo/combobox/combobox_data1.json',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto',
						label: '组织类别:',
						labelPosition: 'left'
					">
				</div>
			</div>
			<div style="padding:10px 40px;">
				<div style="margin-bottom:10px;float:left;width:250px;">
					<input id="issuingStartDate" class="easyui-datebox" label="发文起始日期:" labelPosition="left" data-options="formatter:myformatter,parser:myparser" style="width:100%;">
				</div>
				<div style="margin-bottom:10px;float:right;width:250px;">
					<input id="issuingEndDate" class="easyui-datebox" label="发文截止日期:" labelPosition="left" data-options="formatter:myformatter,parser:myparser" style="width:100%;">
				</div>
			</div>
			<div  style="padding:10px 40px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:50%;height:24px;outline:0;">查 询</a>
			</div>
		</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="DataGrid with Toolbar" style="width:100%;height:500px"
			data-options="	rownumbers:true,
							singleSelect:true,
							url:'datagrid_data1.json',
							method:'get',
							toolbar:documentToolbar,
							pagination:true">
		<thead>
			<tr>
				<th data-options="field:'documentName',width:240,align:'center'">文件名</th>
				<th data-options="field:'keyword',width:240,align:'center'">关键字</th>
				<th data-options="field:'referenceNumber',width:240,align:'center'">文号</th>
				<th data-options="field:'serialNum',width:80,align:'center'">文件顺序号</th>
				<th data-options="field:'issuingAgency',width:120,align:'center'">发文机关</th>
				<th data-options="field:'dispatchLevel',width:80,align:'center'">发文级别</th>
				<th data-options="field:'effectiveness',width:80,align:'center'">文件效力</th>
				<th data-options="field:'organizationType',width:80,align:'center'">组织类别</th>
				<th data-options="field:'issuingDate',width:80,align:'center'">发文日期</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		var documentToolbar = [{
			text:'查看',
			iconCls:'icon-add',
			handler:function(){alert('查看文件内容')}
		},{
			text:'下载',
			iconCls:'icon-cut',
			handler:function(){alert('下载文件')}
		},'-',{
			text:'Save',
			iconCls:'icon-save',
			handler:function(){alert('save')}
		}];
	</script>
	