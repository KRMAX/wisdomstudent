var role = 'student';
var questionCheckedArr = new Array();
var sinOptArr = new Array(); //单选题数组
var mutiOptArr = new Array(); //多选题数组
var courseId = 0;
$(document).ready(function () {
    $('#dgHomework').datagrid({
        //url:role + '/getHomework',
        url: 'getHomeworkStudent',
        title: "作业列表",
        fitColumns: true,
        striped: true,
        rownumbers: true,
        singleSelect: true,
        pagination: true,
        method: 'get',
        columns: [[
            {field: 'name', title: '作业名称', width: 50, align: 'center'},
            {field: 'term', title: '学期', width: 50, align: 'center'},
            {field: 'hmDesc', title: '作业描述', width: $(this).width() * 0.1},
            {field: 'teacherName', title: '创建者', width: 30},
            {
                field: 'createTime', title: '创建时间', width: 70,
                formatter: function (value, row, index) {
                    var time = new Date(row.createTime);
                    var str = time.getFullYear() + "年" + (time.getMonth() + 1) + "月" + (time.getDate()) + "日" + (time.getHours()) + "点";
                    return str;
                }
            },
            {
                field: 'operate', title: '操作', align: 'center', width: 30,
                formatter: function (value, row, index) {
                	var id=row.id;
                    var questionid = row.questionId;
                    var str = '<a  class="easyui-linkbutton" plain="true" data-options="iconCls:\'icon-add\'" onclick="javascript:readHomeWork(\'' + questionid+':'+id + '\')">开始答题</a>';
                    return str;
                }
            },
            {field: 'homeworkStatus', align: 'center',title: '是否提交', width: 20},
            {field: 'score', align: 'center',title: '得分', width: 20}
        ]],
        onLoadSuccess: function (data) {
            /* $("a[name='opera']").linkbutton({text:'预览答题卡',plain:true,iconCls:'icon-search',onclick:"readalert()"});
             $("a[name='addClass']").linkbutton({text:'添加班级',plain:true,iconCls:'icon-add'});*/

        }
    });
});

function readHomeWork(e) {
			 var questionids=e.split(":")[0];
				var id=e.split(":")[1];
				var realData='ids='+questionids+'&homeworkid='+id;
			    $.ajax({
			        type: 'POST',
			        //url: role + '/addHomework',
			        url: 'getHomeworkStatus?homeworkid='+id,
			        contentType: "application/json; charset=utf-8",
			        dataType: 'json',
			        success: function (data) {  
			        	console.log(data);
			            if (data.issubmit==1) {
			               alert('此道题目您已作答');
			            }else{
			            	//对参数进行64位加密
			            	var encodeUrl = 'openHomeWork?'+window.btoa(realData);
			                window.open(encodeUrl, 'newwindow', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no')
			            }

			        },
			    });

}

function refresh()  //任意你想刷新时调用的方法
{
this.location = this.location;
}

function FindData(){
	
}
