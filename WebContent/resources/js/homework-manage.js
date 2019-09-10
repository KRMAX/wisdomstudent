var role = 'teacher';
var questionCheckedArr = new Array();
var sinOptArr = new Array(); //单选题数组
var mutiOptArr = new Array(); //多选题数组
var courseId = 0;
var homeworid=0;
var edit=false;
var selectedHomeworid=0;


var params;


$(document).ready(function () {
    $('#dgHomework').datagrid({
        //url:role + '/getHomework',
        url: 'getHomeworkList',
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
                field: 'operate', title: '点击预览', align: 'center', width: 30,
                formatter: function (value, row, index) {
                    var questionid = row.questionId;
                    var str = '<a  class="buttons" plain="true" data-options="iconCls:\'icon-add\'" onclick="javascript:readHomeWork(\'' + questionid + '\')">作业预览</a>';
                    return str;
                }
            },
            {
                field: 'clazzinfo', title: '查看完成情况', align: 'center', width: 30,
                formatter: function (value, row, index) {
                    var homeworkid = row.id;
                    var str = '<a  class="buttons" plain="true" data-options="iconCls:\'icon-add\'" onclick="javascript:openHomeworkClazz(\'' + homeworkid + '\')">班级情况</a>';
                    return str;
                }
            }/*,
            {field: 'isReleased', title: '是否发布', width: 20},
            {field: 'isEnabled', title: '是否有效', width: 20}*/
        ]],
        onLoadSuccess: function (data) {
            /* $("a[name='opera']").linkbutton({text:'预览答题卡',plain:true,iconCls:'icon-search',onclick:"readalert()"});
             $("a[name='addClass']").linkbutton({text:'添加班级',plain:true,iconCls:'icon-add'});*/

        },
        toolbar: '#toolbar'
    });

    $('#kptree').tree({
        url: 'getkptreeCourseNode/' + courseId,
        onBeforeExpand: function (node) {
            $("#kptree").tree('options').url = 'getkptreeNodeByIdAndLevel?id=' + node.id + "&level=" + node.attributes.level; //这是点击根节点的时候发送请求去加载子节点
            //$("#kptree").tree('reload',node.target);
        },
        onClick: function (node) {
            $("#kptree").tree('options').url = 'getkptreeNodeByIdAndLevel?id=' + node.id + "&level=" + node.attributes.level;
            //对每个"知识点"级别的叶子节点，按照id取出此知识点下所有习题
            if ($('#kptree').tree('isLeaf', node.target) && node.attributes.level == '知识点') {//

                $('#dgQuestion').datagrid({
                    url: 'getQuestionDatagrid?knowledgePointId=' + node.id

                });
            }
           // courseId=node.id;
        },
        //method:'post',
        animate: true
    });

    $('#dgQuestion').datagrid({
        url: '',
        title: "题目列表",
        fitColumns: true,
        striped: true,
        pagination: true,
        rownumbers: false,
        singleSelect: false,
        checkOnSelect: false,
        selectOnCheck: false,
        resizable: true,
        columns: [[
            {field: 'ck', checkbox: true},
            {
                field: 'content.title', title: '题目', width: 150,
                formatter: function (value, row, index) {

                    if (row.content.titleImg != "") {//alert(row.qc.titleImg);
                        return '<img src=getQuestImg/' + row.questionId + '?id=' + Math.random() + '/>';
                    }
                }
            },
            {field: 'answer', title: '答案', width: 15, align: 'center'},
            {field: 'kpDescribe', title: '知识点描述', width: 40, align: 'center'},
            {field: 'questionTypeName', title: '题型', width: 30, align: 'center'},
            {field: 'questionDesc', title: '习题描述', width: 50},
            {field: 'teacherName', title: '作者', width: 30},
            {
                field: 'createTime', title: 'createTime', width: 30,
                formatter: function (value, row, index) {
                    var time = new Date(row.createTime);
                    var str = time.getFullYear() + "年" + (time.getMonth() + 1) + "月" + (time.getDate()) + "日" + (time.getHours()) + "点";
                    return str;
                }
            }
        ]],
        toolbar: [{
            iconCls: 'icon-add',
            handler: function () {
                alert('add')
            }
        }, '-', {
            iconCls: 'icon-remove',
            handler: function () {
                alert('remove')
            }
        }],
        onCheck: function (index, row) {

            //习题数组已有就不添加此row的questionId了
            for (var i = 0; i < questionCheckedArr.length; i++) {
                if (questionCheckedArr[i] == row.questionId) {
                    return;
                }
            }
            var tempNum = $('#questTotal').text();
            $('#questTotal').text(++tempNum);

            //如果是单选题，将此题信息存入sinOptAnsArr中
            //var sinOptObject;

            switch (row.questionTypeName) {
                case "单选题":
                    tempNum = $('#sinoptQuestTotal').text();
                    $('#sinoptQuestTotal').text(++tempNum);
                    var sinOptObject = {
                        id: row.questionId,
                        type: row.questionTypeName,
                        ansOption: ["A", "B", "C", "D"]
                    };
                    sinOptArr.push(sinOptObject);
                    break;
                case "多选题":
                    tempNum = $('#mutioptQuestTotal').text();
                    $('#mutioptQuestTotal').text(++tempNum);
                    var mutiOptObject = {
                        id: row.questionId,
                        type: row.questionTypeName,
                        ansOption: ["A", "B", "C", "D"]
                    };
                    mutiOptArr.push(mutiOptObject);
                    break;
            }

            var questObject = row.questionId;
            questionCheckedArr.push(questObject);

        },
        onUncheck: function (index, row) {
            var tempNum = $('#questTotal').text();
            $('#questTotal').text(--tempNum);

            switch (row.questionTypeName) {
                case "单选题":
                    tempNum = $('#sinoptQuestTotal').text();
                    $('#sinoptQuestTotal').text(--tempNum);
                    for (var i = 0; i < sinOptArr.length; i++) {
                        if (sinOptArr[i].id == row.questionId) {
                            sinOptArr.splice(i, 1);
                        }
                    }
                    sncooperateinfodetail
                    break;
                case "多选题":
                    tempNum = $('#mutioptQuestTotal').text();
                    $('#mutioptQuestTotal').text(--tempNum);
                    for (var i = 0; i < mutiOptArr.length; i++) {
                        if (mutiOptArr[i].id == row.questionId) {
                            mutiOptArr.splice(i, 1);
                        }
                    }
                    break;

            }

            //删除选中的习题OID
            for (var i = 0; i < questionCheckedArr.length; i++) {
                if (questionCheckedArr[i] == row.questionId) {
                    questionCheckedArr.splice(i, 1);
                }
            }

        },
        onLoadSuccess: function (data) {
            var loadRows = data.rows;
            for (var i = 0; i < questionCheckedArr.length; i++) {
                for (var j = 0; j < loadRows.length; j++) {
                    if (questionCheckedArr[i] == loadRows[j].questionId) {

                        $('#dgQuestion').datagrid('checkRow', j);
                    }
                }
            }
        }
    });

    $('#dgQuestion').datagrid('getPanel').addClass("lines-bottom");

});

function submitForm() {

    questionCheckedArrJson = JSON.stringify(questionCheckedArr);

    $('#w').window('clear');
    var cexo = '<div class="test_main">';
    cexo += '<div class="test">';
    if (sinOptArr.length > 0) {//如果有单选题
        cexo += '<div class="test_content">';
        cexo += '<div class="test_content_title">';
        cexo += '<h2>单选题</h2>';
        cexo += '<p><span>共</span><i class="content_lit">' + sinOptArr.length + '</i><span>题，</span>';
        cexo += '<span>合计</span><i class="content_fs">' + (sinOptArr.length) * 2 + '</i><span>分</span></p>';
        cexo += '</div></div>';
        cexo += '<div class="test_content_nr">';
        cexo += '<ul>';
        for (var i = 1; i <= sinOptArr.length; i++) {
            cexo += '<li id="qu_1_' + i + '">';
            cexo += '<div class="test_content_nr_tt">';
            cexo += '<i>' + i + '</i><img src="getQuestImg/' + sinOptArr[i - 1].id + '" /><b class="icon iconfont">&#xe881;</b>';
            cexo += '</div>';
            cexo += '<div class="test_content_nr_main"><ul>';
            var ansOptionObj = sinOptArr[i - 1].ansOption;
            for (var j = 1; j <= ansOptionObj.length; j++) {
                cexo += '<li class="option">';
                cexo += '<label class="demo--label">';
                cexo += '<input class="demo--radio" type="radio"';
                cexo += 'name="1_answer_' + i + '" id="1_answer_' + i + '_option_' + j + '" style="display:none" value="' + ansOptionObj[j - 1] + '">';
                cexo += '<span class="demo--radioInput"></span>' + ansOptionObj[j - 1];
                cexo += '</label></li>';
            }
            cexo += '</ul></div>'; // 对应：<div class="test_content_nr_main"><ul>
            cexo += '</li>';
        }
        cexo += '</ul></div>';
    }
    if (mutiOptArr.length > 0) { //如果有多选题
        alert(mutiOptArr.length);
        cexo += '<div class="test_content">';
        cexo += '<div class="test_content_title">';
        cexo += '<h2>多选题</h2>';
        cexo += '<p><span>共</span><i class="content_lit">' + mutiOptArr.length + '</i><span>题，</span>';
        cexo += '<span>合计</span><i class="content_fs">' + (mutiOptArr.length) * 2 + '</i><span>分</span></p>';
        cexo += '</div></div>';
        cexo += '<div class="test_content_nr">';
        cexo += '<ul>';
        for (var i = 1; i <= mutiOptArr.length; i++) {
            cexo += '<li id="qu_2_' + i + '">';
            cexo += '<div class="test_content_nr_tt">';
            //cexo += '<i>' + i + '</i><img src="teacher/getQuestImg/' + mutiOptArr[i - 1].id + '" /><b class="icon iconfont">&#xe881;</b>';
            cexo += '<i>' + i + '</i><img src="getQuestImg/' + mutiOptArr[i - 1].id + '" /><b class="icon iconfont">&#xe881;</b>';
            cexo += '</div>';
            cexo += '<div class="test_content_nr_main"><ul>';
            var ansOptionObj = mutiOptArr[i - 1].ansOption;
            for (var j = 1; j <= ansOptionObj.length; j++) {
                cexo += '<li class="option">';
                cexo += '<label for="2_answer_' + i + '_option_' + j + '">';
                cexo += '<input type="checkbox" class="radioOrCheck"';
                cexo += 'name="2_answer_' + i + '" id="2_answer_' + i + '_option_' + j + '" value="' + ansOptionObj[j - 1] + '"/>';
                cexo += ansOptionObj[j - 1];
                cexo += '</label></li>';
            }
            cexo += '</ul></div>'; // 对应：<div class="test_content_nr_main"><ul>
            cexo += '</li>';
        }
        cexo += '</ul></div>';
    }
    cexo += '</div></div>';
    $('#w').append(cexo);

    //如果打开的是修改页面
    if(edit){
        var editbtn = $("<input type='button' id='submitBtn1' value='修改作业' style='width:70px;'/>");
        $('#w').append(editbtn);
        addBtnEvent1("submitBtn1");	
    }else{
        var btn = $("<input type='button' id='submitBtn' value='作业生成' style='width:70px;'/>");
        $('#w').append(btn);
        addBtnEvent("submitBtn");	
    }


    //预览所有习题组成的界面
    $('#w').window('open');
}


function addBtnEvent1(id) {
    $("#" + id).bind("click", function () {
    	 $.messager.confirm("操作提示", "您确定要修改作业吗？", function (data) {
    		 if(data){
    			 var hmArr = new Object();
                 hmArr.hmName = $('#hwName').textbox('getText');
                 hmArr.hwDiscrib = $('#hwDiscrib').textbox('getText');
                 hmArr.courseId = courseId;
                 hmArr.clazzsId=$('#class').combobox('getValues').toString();
                 hmArr.idList = questionCheckedArr;
                 hmArr.homeworkId=homeworid;
                 hmArr.term = 2019;
                 var hmArrJson = JSON.stringify(hmArr);
                 
                 $.ajax({
                     type: 'POST',
                     //url: role + '/addHomework',
                     url: 'editHomework',
                     data: hmArrJson,
                     contentType: "application/json; charset=utf-8",
                     dataType: 'json',
                     success: function (data) {
                     	console.log(data);
                         if (data.success) {
                         	 alert('作业修改成功');
                             questionCheckedArr.length = 0;
                             sinOptArr.length = 0;
                             mutiOptArr.length = 0;
                             $('#w').window('clear');
                             $('#w').window('close');
                             $('#wHomework').window('close');
                             $('#dgHomework').datagrid('reload');
                             // 刷新页面
                             $("#wHomework").dialog({closed:true});

                         }

                     },
                 });
                 
    		 }else {

                 alert("您已取消修改！");

             }

    		 
    		 
    	 })
     
    });
}

function addBtnEvent(id) {
    var clazzName=$('#class').combobox('getText').toString();
    $("#" + id).bind("click", function () {

        $.messager.confirm("操作提示", "请再次确定您选择得班级:"+clazzName, function (data) {

            if(data) {

                //这里做提交
                var hmArr = new Object();
                hmArr.hmName = $('#hwName').textbox('getText');
                hmArr.hwDiscrib = $('#hwDiscrib').textbox('getText');
                hmArr.courseId = courseId;
                hmArr.clazzsId=$('#class').combobox('getValues').toString();

                hmArr.term = 2019;//"2019年第二学期"必须要填数据字典得int类型id

                hmArr.idList = questionCheckedArr;

                var hmArrJson = JSON.stringify(hmArr);
                $.ajax({
                    type: 'POST',
                    //url: role + '/addHomework',
                    url: 'addHomework',
                    data: hmArrJson,
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    success: function (data) {                   	
                        if (data.success) {
                        	alert('作业添加成功');
                            questionCheckedArr.length = 0;
                            sinOptArr.length = 0;
                            mutiOptArr.length = 0;
                            $('#w').window('clear');
                            $('#w').window('close');
                            $('#wHomework').window('close');
                            $('#dgHomework').datagrid('reload');
                            // 刷新页面
                            $("#wHomework").dialog({closed:true});

                        }

                    },
                });

            }else {

                alert("请重新选择！");

            }

        });

    });
}

function addHomework() {
	edit=false;

	//去除修改作业时带出的数据
    $('#hwName').textbox('setValue','');
    $('#hwDiscrib').textbox('setValue','');
    $('#class').combobox('setValue',[]);
    $('#dgQuestion').datagrid('loadData', {total: 0, rows: []});
    var selectedCourse = $('#course').combobox('getValue');
    if (selectedCourse == null || selectedCourse == "") {
        alert("请先选择课程，才能添加作业！");
        return;
    }//
    courseId = selectedCourse;
    $("#kptree").tree('options').url = 'getkptreeCourseNode/' + courseId;
    $('#kptree').tree('reload');
    $('#kptree').tree('collapseAll');
    $('#wHomework').dialog('open');
    questionCheckedArr.length = 0;
    sinOptArr.length = 0;
    mutiOptArr.length = 0;

    $('#dgQuestion').datagrid('loadData', {total: 0, rows: []});
    $('#questTotal').text(0);
    $('#sinoptQuestTotal').text(0);
    $('#mutioptQuestTotal').text(0);
    $('#fillQuestTotal').text(0);
    $('#judgeQuestTotal').text(0);

}


function readHomeWork(e) {
        if(e!=null){
            $.ajax({
                type: 'POST',
                url:  'getQuestionById?ids=' +e,
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success: function (data) {
                    var jsonArrayData=data;
                    questionCheckedArr=new Array(jsonArrayData.length);
                   var sinOptArr = new Array(); //单选题数组
                    var mutiOptArr = new Array(); //多选题数组
                    //习题数组已有就不添加此row的questionId了
                    for (var i = 0; i < questionCheckedArr.length; i++) {
                        if (questionCheckedArr[i] == jsonArrayData[i].id) {
                            return;
                        }
                    }


                    //如果是单选题，将此题信息存入sinOptAnsArr中
                    //var sinOptObject;
                   for(var j=0;j<jsonArrayData.length;j++)
                    switch (jsonArrayData[j].questiontypestr) {
                        case "单选题":
                            var sinOptObject = {
                                id: jsonArrayData[j].id,
                                type: jsonArrayData[j].questiontypestr,
                                ansOption: ["A", "B", "C", "D"]
                            };
                            sinOptArr.push(sinOptObject);
                            break;
                        case "多选题":
                            var mutiOptObject = {
                                id: jsonArrayData[j].id,
                                type: jsonArrayData[j].questiontypestr,
                                ansOption: ["A", "B", "C", "D"]
                            };
                            mutiOptArr.push(mutiOptObject);
                            break;
                    }

                    $('#w').window('clear');
                    var cexo = '<div class="test_main">';
                    cexo += '<div class="test">';
                    if (sinOptArr.length > 0) {//如果有单选题
                        cexo += '<div class="test_content">';
                        cexo += '<div class="test_content_title">';
                        cexo += '<h2>单选题</h2>';
                        cexo += '<p><span>共</span><i class="content_lit">' + sinOptArr.length + '</i><span>题，</span>';
                        cexo += '<span>合计</span><i class="content_fs">' + (sinOptArr.length) * 2 + '</i><span>分</span></p>';
                        cexo += '</div></div>';
                        cexo += '<div class="test_content_nr">';
                        cexo += '<ul>';
                        for (var i = 1; i <= sinOptArr.length; i++) {
                            cexo += '<li id="qu_1_' + i + '">';
                            cexo += '<div class="test_content_nr_tt">';
                            cexo += '<i>' + i + '</i><img src="getQuestImg/' + sinOptArr[i - 1].id + '" /><b class="icon iconfont">&#xe881;</b>';
                            cexo += '</div>';
                            cexo += '<div class="test_content_nr_main"><ul>';
                            var ansOptionObj = sinOptArr[i - 1].ansOption;
                            for (var j = 1; j <= ansOptionObj.length; j++) {
                                cexo += '<li class="option">';
                                cexo += '<label class="demo--label">';
                                cexo += '<input class="demo--radio" type="radio"';
                                cexo += 'name="1_answer_' + i + '" id="1_answer_' + i + '_option_' + j + '" style="display:none" value="' + ansOptionObj[j - 1] + '">';
                                cexo += '<span class="demo--radioInput"></span>' + ansOptionObj[j - 1];
                                cexo += '</label></li>';
                            }
                            cexo += '</ul></div>'; // 对应：<div class="test_content_nr_main"><ul>
                            cexo += '</li>';
                        }
                        cexo += '</ul></div>';
                    }
                    if (mutiOptArr.length > 0) { //如果有多选题
                        cexo += '<div class="test_content">';
                        cexo += '<div class="test_content_title">';
                        cexo += '<h2>多选题</h2>';
                        cexo += '<p><span>共</span><i class="content_lit">' + mutiOptArr.length + '</i><span>题，</span>';
                        cexo += '<span>合计</span><i class="content_fs">' + (mutiOptArr.length) * 2 + '</i><span>分</span></p>';
                        cexo += '</div></div>';
                        cexo += '<div class="test_content_nr">';
                        cexo += '<ul>';
                        for (var i = 1; i <= mutiOptArr.length; i++) {
                            cexo += '<li id="qu_2_' + i + '">';
                            cexo += '<div class="test_content_nr_tt">';
                            cexo += '<i>' + i + '</i><img src="getQuestImg/' + mutiOptArr[i - 1].id + '" /><b class="icon iconfont">&#xe881;</b>';
                            cexo += '</div>';
                            cexo += '<div class="test_content_nr_main"><ul>';
                            var ansOptionObj = mutiOptArr[i - 1].ansOption;
                            for (var j = 1; j <= ansOptionObj.length; j++) {
                                cexo += '<li class="option">';
                                cexo += '<label for="2_answer_' + i + '_option_' + j + '">';
                                cexo += '<input type="checkbox" class="radioOrCheck"';
                                cexo += 'name="2_answer_' + i + '" id="2_answer_' + i + '_option_' + j + '" value="' + ansOptionObj[j - 1] + '"/>';
                                cexo += ansOptionObj[j - 1];
                                cexo += '</label></li>';
                            }
                            cexo += '</ul></div>'; // 对应：<div class="test_content_nr_main"><ul>
                            cexo += '</li>';
                        }
                        cexo += '</ul></div>';
                    }
                    cexo += '</div></div>';
                    $('#w').append(cexo);

                    $('#w').window('open');

                },
            });
        }else{
            alert("没有作业内容");
        }


}
function destroyHomework(){
	var row = $('#dgHomework').datagrid('getSelected');
	 if (row){
		 homeworid=row.id;
		 $.messager.confirm("操作提示", "您确定要删除作业："+row.name+"吗？", function (data) {
			 if(data) {
			 $.ajax({
				 type: 'POST',
				 url: 'deleteHomework?id='+row.id,
				 contentType: "application/json; charset=utf-8",
				 success: function (data) {
					 if(data.success){
						 alert(data.successMsg);
					       // 刷新表格
						 $('#dgHomework').datagrid('reload');
					 }
				 }
			 })
		 }
		 })

		 }else{
			 alert("请选择作业内容");
		 }
}
function editHomework(){
	edit=true;

	var k=0;
    $('#hwName').textbox('setValue','');
    $('#hwDiscrib').textbox('setValue','');
    $('#class').combobox('setValue',[]);
    $('#submitBtn').hide();
    $('#submitBtn1').show();
    $('#dgQuestion').datagrid('loadData', {total: 0, rows: []});
	var row = $('#dgHomework').datagrid('getSelected');
	
	 if (row){
		 homeworid=row.id;
		 $.ajax({
			 type: 'POST',
			 url: 'getHomework?id='+row.id,
			 contentType: "application/json; charset=utf-8",
			 dataType: 'json',
			 success: function (data) {
	
				   $('#hwName').textbox('setValue',data.name);
				    $('#hwDiscrib').textbox('setValue',data.discrib);
                    var clazz=data.clazzid.split(',');
                    console.log(clazz);
				    $('#class').combobox('setValue',clazz);
				    courseId = data.courseid;
				    $("#kptree").tree('options').url = 'getkptreeCourseNode/' + courseId;
				    $('#kptree').tree('reload');
				    $('#kptree').tree('collapseAll');
				    $('#wHomework').dialog('open');
				    questionCheckedArr.length = 0;
				    sinOptArr.length = 0;
				    mutiOptArr.length = 0;

				    $('#dgQuestion').datagrid({
			            url: 'getQuestionDatagridEdit?ids=' + data.questionid
				    })
				    
			        $('#dgQuestion').datagrid({
			            url: '',
			            fit:false,
			            title: "题目列表",
			            fitColumns: true,
			            striped: true,
			            pagination: true,
			            rownumbers: false,
			            singleSelect: false,
			            checkOnSelect: false,
			            selectOnCheck: false,
			            resizable: true,
			            height:800,
			            columns: [[
			                {field: 'ck', checkbox: true},
			                {
			                    field: 'content.title', title: '题目', width: 150,
			                    formatter: function (value, row, index) {
                                    console.log(row);
			                        if (row.content.titleImg != "") {//alert(row.qc.titleImg);
			                            return '<img src=getQuestImg/' + row.questionId + '?id=' + Math.random() + '/>';
			                        }
			                    }
			                },
			                {field: 'answer', title: '答案', width: 15, align: 'center'},
			                {field: 'kpDescribe', title: '知识点描述', width: 40, align: 'center'},
			                {field: 'questionTypeName', title: '题型', width: 30, align: 'center'},
			                {field: 'questionDesc', title: '习题描述', width: 50},
			                {field: 'teacherName', title: '作者', width: 30},
			                {
			                    field: 'createTime', title: 'createTime', width: 30,
			                    formatter: function (value, row, index) {
			                        var time = new Date(row.createTime);
			                        var str = time.getFullYear() + "年" + (time.getMonth() + 1) + "月" + (time.getDate()) + "日" + (time.getHours()) + "点";
			                        return str;
			                    }
			                }
			            ]],
			            toolbar: [{
			                iconCls: 'icon-add',
			                handler: function () {
			                    alert('add')
			                }
			            }, '-', {
			                iconCls: 'icon-remove',
			                handler: function () {
			                    alert('remove')
			                }
			            }],
			            onCheck: function (index, row) {
			                //习题数组已有就不添加此row的questionId了
			                for (var i = 0; i < questionCheckedArr.length; i++) {
			                    if (questionCheckedArr[i] == row.questionId) {
			                        return;
			                    }
			                }
			                var tempNum = $('#questTotal').text();
			                $('#questTotal').text(++tempNum);

			                //如果是单选题，将此题信息存入sinOptAnsArr中
			                //var sinOptObject;

			                switch (row.questionTypeName) {
			                    case "单选题":
			                        tempNum = $('#sinoptQuestTotal').text();
			                        $('#sinoptQuestTotal').text(++tempNum);
			                        var sinOptObject = {
			                            id: row.questionId,
			                            type: row.questionTypeName,
			                            ansOption: ["A", "B", "C", "D"]
			                        };
			                        sinOptArr.push(sinOptObject);
			                        break;
			                    case "多选题":
			                        tempNum = $('#mutioptQuestTotal').text();
			                        $('#mutioptQuestTotal').text(++tempNum);
			                        var mutiOptObject = {
			                            id: row.questionId,
			                            type: row.questionTypeName,
			                            ansOption: ["A", "B", "C", "D"]
			                        };
			                        mutiOptArr.push(mutiOptObject);
			                        break;
			                }
			                var questObject = row.questionId;
			                questionCheckedArr.push(questObject);
			                console.log(questionCheckedArr);
			            },
			            onUncheck: function (index, row) {
			                var tempNum = $('#questTotal').text();
			                $('#questTotal').text(--tempNum);

			                switch (row.questionTypeName) {
			                    case "单选题":
			                        tempNum = $('#sinoptQuestTotal').text();
			                        $('#sinoptQuestTotal').text(--tempNum);
			                        for (var i = 0; i < sinOptArr.length; i++) {
			                            if (sinOptArr[i].id == row.questionId) {
			                                sinOptArr.splice(i, 1);
			                            }
			                        }
			                        
			                        break;
			                    case "多选题":
			                        tempNum = $('#mutioptQuestTotal').text();
			                        $('#mutioptQuestTotal').text(--tempNum);
			                        for (var i = 0; i < mutiOptArr.length; i++) {
			                            if (mutiOptArr[i].id == row.questionId) {
			                                mutiOptArr.splice(i, 1);
			                            }
			                        }
			                        break;

			                }
			         
			                //删除选中的习题OID
			                for (var i = 0; i < questionCheckedArr.length; i++) {
			                    if (questionCheckedArr[i] == row.questionId) {
			                        questionCheckedArr.splice(i, 1);
			                    }
			                }

			            },
			            onLoadSuccess: function (data) {
			            	
			            	if(k==0){
			            	      var loadRows = data.rows;
				                    for (var j = 0; j < loadRows.length; j++) {
				                       $('#dgQuestion').datagrid('checkRow', j); 
				                    }
			            	}else{
			                    var loadRows = data.rows;
			                    for (var i = 0; i < questionCheckedArr.length; i++) {
			                        for (var j = 0; j < loadRows.length; j++) {
			                            if (questionCheckedArr[i] == loadRows[j].questionId) {

			                                $('#dgQuestion').datagrid('checkRow', j);
			                            }
			                        }
			                    }
			            	}
			          
			            	k++;
			            }
			        });

			        
			        $('#dgQuestion').datagrid('getPanel').addClass("lines-bottom");
				    $('#questTotal').text(0);
				    $('#sinoptQuestTotal').text(0);
				    $('#mutioptQuestTotal').text(0);
				    $('#fillQuestTotal').text(0);
				    $('#judgeQuestTotal').text(0);
	
			 }
		 })
		 $('#wHomework').dialog('open').dialog('setTitle','修改作业');
		 
		 
	 }else{
		 alert("请选择作业内容");
	 }
	 
	 
}


function openHomeworkClazz(e){
	$('#searchhomeworkclass').combobox('setValue',[]);
	selectedHomeworid=e;
	$('#homeworkclazz').dialog('open');
    if(e!=null){
    	  $('#dgHomeworkClazz').datagrid({
    	        //url:role + '/getHomework',
    		    url:  'getHomeWorkClazz',
    	        title: "作业列表",
    	    	queryParams: {
    	    		'homeworkid': e
    	    	},
    	        fitColumns: true,
    	        striped: true,
    	        rownumbers: true,
    	        singleSelect: true,
    	        pagination: true,
    	        method: 'POST',
    	        columns: [[
    	            {field: 'homeworkName', title: '作业名称', width: 50, align: 'center'},
    	            {field: 'clazzName', title: '班级名称', width: 50, align: 'center'},
    	            {field: 'averagescore', title: '平均分', width: 50, align: 'center'},
    	            {field: 'highestscore', title: '最高分', width: 50, align: 'center'},
    	            {field: 'lowestscore', title: '最低分', width: 50, align: 'center'},
    	            {field: 'studentnum', title: '学生总数', width: 50, align: 'center'},
    	            {field: 'completionnum', title: '应完成总数', width: 50, align: 'center'},
    	            
    	            {
    	                field: 'operate', title: '学生作业完成情况', align: 'center', width: 50,
    	                formatter: function (value, row, index) {
    	                    var homeworkid = row.homeworkid;
    	                    var clazzid=row.clazzid;
    	                    var averagescore=row.averagescore;
    	                    var highestscore=row.highestscore;
    	                    var lowestscore=row.lowestscore;
    	                    var homeworkName=row.homeworkName;
    	                    var clazzName=row.clazzName;
    		                params=homeworkid+':'+clazzid+':'+averagescore+':'+highestscore+':'+lowestscore+':'+homeworkName+':'+clazzName;
    	                    var str = '<a  href="javascript:void(0)" class="easyui-linkbutton" plain="true" data-options="iconCls:\'icon-add\'" onclick="javascript:openHomeWorkStudent(\'' + homeworkid+':'+clazzid+':'+averagescore+':'+highestscore+':'+lowestscore +':'+homeworkName+':'+clazzName + '\')">班级学生作业情况</a>';
    	                    return str;
    	                }
    	            },
    	       
    	
    	        ]],
    	        onLoadSuccess: function (data) {

    	            /* $("a[name='opera']").linkbutton({text:'预览答题卡',plain:true,iconCls:'icon-search',onclick:"readalert()"});
    	             $("a[name='addClass']").linkbutton({text:'添加班级',plain:true,iconCls:'icon-add'});*/

    	        },
    	        onLoadError:function(){
    	        	$('#dgHomeworkClazz').datagrid('loadData', { total: 0, rows: [] });
    	        	alert('加载错误!请查看是否有作业-学生的数据！');
    	        	 
    	        }
    
    	    });
    	  
    }else{
        alert("没有获取作业内容");
    }
	
}


function FindHomeworkClassData(){
	
	var selectedClazz = $('#searchhomeworkclass').combobox('getValue');
	$('#dgHomeworkClazz').datagrid('loadData', { total: 0, rows: [] });
	if(selectedHomeworid!=""&&selectedHomeworid!=null){
	    $('#dgHomeworkClazz').datagrid({
	    	url:'getHomeWorkClazz',
	    	queryParams: {
	    		'homeworkid': selectedHomeworid,
	    		'clazzid': selectedClazz
	    	},
	    fitColumns: true,
	    striped: true,
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    method: 'POST',
	    columns: [[
	        {field: 'homeworkName', title: '作业名称', width: 50, align: 'center'},
	        {field: 'clazzName', title: '班级名称', width: 50, align: 'center'},
	        {field: 'averagescore', title: '平均分', width: 50, align: 'center'},
	        {field: 'highestscore', title: '最高分', width: 50, align: 'center'},
	        {field: 'lowestscore', title: '最低分', width: 50, align: 'center'},
	        {field: 'studentnum', title: '学生总数', width: 50, align: 'center'},
	        {field: 'completionnum', title: '应完成总数', width: 50, align: 'center'},
	        
	        {
	            field: 'operate', title: '班级学生情况', align: 'center', width: 30,
	            formatter: function (value, row, index) {
	                 var homeworkid = row.homeworkid;
	                 var clazzid=row.clazzid;
	                 var averagescore=row.averagescore;
	                 var highestscore=row.highestscore;
	                 var lowestscore=row.lowestscore;
	                 var homeworkName=row.homeworkName;
	                 var clazzName=row.clazzName;
	                 params=homeworkid+':'+clazzid+':'+averagescore+':'+highestscore+':'+lowestscore+':'+homeworkName+':'+clazzName;
	                var str = '<a  class="easyui-linkbutton" plain="true" data-options="iconCls:\'icon-add\'" onclick="javascript:openHomeWorkStudent(\'' + homeworkid+':'+clazzid+':'+averagescore+':'+highestscore+':'+lowestscore+':'+homeworkName+':'+clazzName + '\')">班级学生作业情况</a>';
	                return str;
	            }
	        },
	   

	    ]],
	    onLoadSuccess: function (data) {
	        /* $("a[name='opera']").linkbutton({text:'预览答题卡',plain:true,iconCls:'icon-search',onclick:"readalert()"});
	         $("a[name='addClass']").linkbutton({text:'添加班级',plain:true,iconCls:'icon-add'});*/

	    },
	    onLoadError:function(){
	    	$('#dgHomeworkClazz').datagrid('loadData', { total: 0, rows: [] });
	    	alert('加载错误!请查看是否有作业-学生的数据！');
	    	 
	    }
	    
	    	}); 
	}

    
    
}

function openHomeWorkStudent(e){
	
    var homeworkid=e.split(':')[0];
    var clazzid=e.split(':')[1];
    var averagescore=e.split(':')[2];
    var highestscore=e.split(':')[3];
    var lowestscore=e.split(':')[4];
    var homeworkName=e.split(':')[5];
    var clazzName=e.split(':')[6];
	$('#homeworkstudent').dialog('open');
    if(homeworkid!=null&&clazzid!=null){
    	  $('#dgHomeworkStudent').datagrid({
    	        //url:role + '/getHomework',
    		    url:  'getHomeWorkStudent',
    	        title: "作业列表",
    	    	queryParams: {
    	    		'homeworkid': homeworkid,
    	    		'clazzid':clazzid
    	    	},
    	        fitColumns: true,
    	        striped: true,
    	        rownumbers: true,
    	        singleSelect: true,
    	        pagination: true,
    	        method: 'POST',
    	        columns: [[
    	            {field: 'homeworkName', title: '作业名称', width: 50, align: 'center',formatter: function(value,row,index){
    				return homeworkName;
    				}},
    	            {field: 'clazzName', title: '班级名称', width: 50, align: 'center',formatter: function(value,row,index){
    				return clazzName;
    				}},
    				{field: 'studentNo', title: '学号', width: 50, align: 'center'},
    				{field: 'studentName', title: '学生姓名', width: 50, align: 'center'},
    	            {field: 'score', title: '学生得分', width: 50, align: 'center'},
    	            {field: 'issubmit', title: '是否提交作业', width: 50, align: 'center',formatter: function(value,row,index){
    	            	if(row.issubmit==1){
    	            		return '已提交';
    	            	}else{
    	            		return '未提交';
    	            	}
    				
    				}},
    	           {field: 'averagescore', title: '平均分', width: 50, align: 'center',formatter: function(value,row,index){
    				return averagescore;
    				}},
    	            {field: 'highestscore', title: '最高分', width: 50, align: 'center',formatter: function(value,row,index){
    				return highestscore;
    				}},
    	            {field: 'lowestscore', title: '最低分', width: 50, align: 'center',formatter: function(value,row,index){
    				return lowestscore;
    				}},  
    	            {
    	                field: 'operate', title: '错误题目', align: 'center', width: 50,
    	                formatter: function (value, row, index) {
    	                    var homeworkid = row.homeworkid;
    	                    var studentid=row.studentid;
    	                    var str = '<a  class="easyui-linkbutton" plain="true" data-options="iconCls:\'icon-add\'" onclick="javascript:openErrorHomework(\'' + homeworkid+':'+studentid + '\')">错题查看</a>';
    	                    return str;
    	                }
    	            }
    	       
    	
    	        ]],
    	        onLoadSuccess: function (data) {
    	            /* $("a[name='opera']").linkbutton({text:'预览答题卡',plain:true,iconCls:'icon-search',onclick:"readalert()"});
    	             $("a[name='addClass']").linkbutton({text:'添加班级',plain:true,iconCls:'icon-add'});*/

    	        },
    	        onLoadError:function(){
    	        	$('#dgHomeworkClazz').datagrid('loadData', { total: 0, rows: [] });
    	        	alert('加载错误!请查看是否有作业-学生的数据！');
    	        	 
    	        }
    
    	    });
    	  
    }else{
        alert("没有获取作业内容");
    }
	
}

function FindHomeworkStudentData(){
    var homeworkid=params.split(':')[0];
    var clazzid=params.split(':')[1];
    var averagescore=params.split(':')[2];
    var highestscore=params.split(':')[3];
    var lowestscore=params.split(':')[4];
    var homeworkName=params.split(':')[5];
    var clazzName=params.split(':')[6];
	console.log(params);
    var stName=$('#stName').textbox('getValue');
    var stNo=$('#stNo').textbox('getValue');
    console.log(stName);
    console.log(stNo);
    if(homeworkid!=null&&clazzid!=null){
    	  $('#dgHomeworkStudent').datagrid({
    	        //url:role + '/getHomework',
    		    url:  'getHomeWorkStudent',
    	        title: "作业列表",
    	    	queryParams: {
    	    		'homeworkid': homeworkid,
    	    		'clazzid':clazzid,
    	    		'studentName':stName,
    	    		'studentNo':stNo
    	    	},
    	        fitColumns: true,
    	        striped: true,
    	        rownumbers: true,
    	        singleSelect: true,
    	        pagination: true,
    	        method: 'POST',
    	        columns: [[
    	            {field: 'homeworkName', title: '作业名称', width: 50, align: 'center',formatter: function(value,row,index){
    				return homeworkName;
    				}},
    	            {field: 'clazzName', title: '班级名称', width: 50, align: 'center',formatter: function(value,row,index){
    				return clazzName;
    				}},
    				{field: 'studentNo', title: '学号', width: 50, align: 'center'},
    				{field: 'studentName', title: '学生姓名', width: 50, align: 'center'},
    	            {field: 'score', title: '学生得分', width: 50, align: 'center'},
    	            {field: 'issubmit', title: '是否提交作业', width: 50, align: 'center',formatter: function(value,row,index){
    	            	if(row.issubmit==1){
    	            		return '已提交';
    	            	}else{
    	            		return '未提交';
    	            	}
    				
    				}},
    	           {field: 'averagescore', title: '平均分', width: 50, align: 'center',formatter: function(value,row,index){
    				return averagescore;
    				}},
    	            {field: 'highestscore', title: '最高分', width: 50, align: 'center',formatter: function(value,row,index){
    				return highestscore;
    				}},
    	            {field: 'lowestscore', title: '最低分', width: 50, align: 'center',formatter: function(value,row,index){
    				return lowestscore;
    				}},  
    	            {
    	                field: 'operate', title: '错误题目', align: 'center', width: 50,
    	                formatter: function (value, row, index) {
    	                    var homeworkid = row.homeworkid;
    	                    var studentid=row.studentid;
    	                    var str = '<a  class="easyui-linkbutton" plain="true" data-options="iconCls:\'icon-add\'" onclick="javascript:openErrorHomework(\'' + homeworkid+':'+studentid + '\')">错题查看</a>';
    	                    return str;
    	                }
    	            }
    	       
    	
    	        ]],
    	        onLoadSuccess: function (data) {
    	            /* $("a[name='opera']").linkbutton({text:'预览答题卡',plain:true,iconCls:'icon-search',onclick:"readalert()"});
    	             $("a[name='addClass']").linkbutton({text:'添加班级',plain:true,iconCls:'icon-add'});*/

    	        },
    	        onLoadError:function(){
    	        	$('#dgHomeworkClazz').datagrid('loadData', { total: 0, rows: [] });
    	        	alert('加载错误!请查看是否有作业-学生的数据！');
    	        	 
    	        }
    
    	    });
    	  
    }else{
        alert("没有获取作业内容");
    }
	

}
