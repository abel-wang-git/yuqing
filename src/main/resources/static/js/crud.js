function addSubmit(formid) {
    var option = {
        success: function (data) {
            layer.msg(data);
        },
        async: false,//使用同步的方式,true为异步方式
    }
    $(formid).ajaxSubmit(option)
}

function removeobj(thiz) {

    layer.confirm('你确定要删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.post($(thiz).attr("abbr") + '/delete', {id: $(thiz).attr("value")},
            function (data) {
                if (data.indexOf("成功") != -1) {
                    layer.msg("操作成功");
                    $(thiz).parent().parent().remove();
                } else {
                    layer.msg("操作失败");
                }
            }
        );
    }, function(){
        });
}

function updateObj(thiz) {
    //Ajax获取
    $.get($(thiz).attr("abbr") + '/add', {id: $(thiz).attr("value")}, function (data) {
        layer.open({
            type: 1,
            area: ['500px', '300px'],
            btn: ['确定', '取消'], //按钮
            btn1: function (index) {
                addSubmit("#add")
                layer.close(index);
            },
            btn2: function () {
            },
            content: data
        });
    });
}

function checkadd(thiz,other) {
    var ids = $("input[name='"+other+"']").val();
    var newId = $(thiz).val();
    if ($(thiz).is(':checked')) {
        if(ids!=null&&ids!=""){newId=","+newId}
        $("input[name='"+other+"']").val(ids + newId);
    } else {
        if(ids.split(",").length>1&&ids.indexOf(newId)!=0){newId=","+newId}
        if(ids.indexOf(newId)==0&&ids.indexOf(",")!=-1){newId = newId+","}
        $("input[name='"+other+"']").val(ids.replace(newId, ""));
    }
}