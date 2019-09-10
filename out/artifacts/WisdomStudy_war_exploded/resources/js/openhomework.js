$(document).ready(function () {
    flag=1;
    var ids=getQueryString("ids");
    if(ids!=null){
        $.ajax({
            type: 'POST',
            url:  'getQuestionById?ids=' +ids,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                var jsonArrayData=data;

                var sinOptArr = new Array(); //单选题数组
                var mutiOptArr = new Array(); //多选题数组



                //如果是单选题，将此题信息存入sinOptAnsArr中
                //var sinOptObject;
                for(var j=0;j<jsonArrayData.length;j++)
                    switch (jsonArrayData[j].questiontype) {
                        case 1:
                            var sinOptObject = {
                                id: jsonArrayData[j].id,
                                type: jsonArrayData[j].questiontypestr,
                                ansOption: ["A", "B", "C", "D"]
                            };
                            sinOptArr.push(sinOptObject);
                            break;
                        case 2:
                            var mutiOptObject = {
                                id: jsonArrayData[j].id,
                                type: jsonArrayData[j].questiontypestr,
                                ansOption: ["A", "B", "C", "D"]
                            };
                            mutiOptArr.push(mutiOptObject);
                            break;
                    }


                var cexo = '';
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
                            cexo += '<label class="demo--label" >';
                            cexo += '<input class="demo--radio" type="radio"';
                            cexo += 'name="1_answer_' + i + '" id="1_answer_' + i + '_option_' + j + '"  style="display:none" value="' + ansOptionObj[j - 1] + '">';
                            cexo += '<span class="demo--radioInput" onclick="customalert(\''+'sin;'+i+'\')"></span>' + ansOptionObj[j - 1];
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

                            cexo += '<input type="checkbox" class="radioOrCheck" onclick="customalert(\''+'muti;'+i+'\')" ' ;
                            cexo += 'name="2_answer_' + i + '" id="2_answer_' + i + '_option_' + j + '" value="' + ansOptionObj[j - 1] + '"/>';
                            cexo += '<label for="2_answer_' + i + '_option_' + j + '">';
                            cexo += ansOptionObj[j - 1];
                            cexo += '</label></li>';
                        }
                        cexo += '</ul></div>'; // 对应：<div class="test_content_nr_main"><ul>
                        cexo += '</li>';
                    }
                    cexo += '</ul></div>';
                }

                var questionCardContent='';
                if (sinOptArr.length > 0) {
                    questionCardContent+='<div class="rt_content">';
                    questionCardContent+='<div class="rt_content_tt">';
                    questionCardContent+='<h2>单选题</h2>';
                    questionCardContent+='<p><span>共</span><i class="content_lit">'+sinOptArr.length+'</i><span>题</span></p>';
                    questionCardContent+='</div>';
                    questionCardContent+='<div class="rt_content_nr answerSheet">';
                    questionCardContent+='<ul>';
                    for (var i = 1; i <= sinOptArr.length; i++) {
                        questionCardContent+='<li><a href="#qu_1_'+i+'" id="kard_qu_1_'+i+'">'+i+'</a></li>';

                    }
                    questionCardContent+='</ul>';
                    questionCardContent+='</div></div>';
                }

                if (mutiOptArr.length > 0) { //如果有多选题
                    questionCardContent+='<div class="rt_content">';
                    questionCardContent+='<div class="rt_content_tt">';
                    questionCardContent+='<h2>多选题</h2>';
                    questionCardContent+='<p><span>共</span><i class="content_lit">'+mutiOptArr.length+'</i><span>题</span></p>';
                    questionCardContent+='</div>';
                    questionCardContent+='<div class="rt_content_nr answerSheet">';
                    questionCardContent+='<ul>';
                    for (var i = 1; i <= mutiOptArr.length; i++) {
                        questionCardContent+='<li><a href="#qu_2_'+i+'" id="kard_qu_2_'+i+'">'+i+'</a></li>';
                    }
                    questionCardContent+='</ul>';
                    questionCardContent+='</div></div>';
                    }
                /*cexo += '</div></div>'*/
                var height=0;
                if (sinOptArr.length > 0) {
                    height+=sinOptArr.length*250+100;
                }
                if (mutiOptArr.length > 0) {
                    height+=mutiOptArr.length*250+100;
                }
              /*  $('.test').attr("height",height);*/
                $('.test').height(height);
                console.log(questionCardContent);
                console.log(cexo);
                $('#questionCardContent').html(questionCardContent);
                $('#context').html(cexo);



            },
        });
    }else{
        alert("没有作业内容");
    }
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

})

function getQueryString(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
