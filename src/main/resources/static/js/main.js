
layui.define(['element', 'layer', 'util', 'pagesize', 'form'], function (exports) {
    var $ = layui.jquery;
    var element = layui.element();
    var layer = layui.layer;
    var util = layui.util;
    var form = layui.form();
    //form.render();
    //��ݲ˵�����
    $('span.sys-title').click(function (e) {
        e.stopPropagation();    //��ֹ�¼�ð��
        $('div.short-menu').slideToggle('fast');
    });
    $('div.short-menu').click(function (e) {
        e.stopPropagation();    //��ֹ�¼�ð��
    });
    $(document).click(function () {
        $('div.short-menu').slideUp('fast');
        $('.individuation').removeClass('bounceInRight').addClass('flipOutY');
    });
    //���Ի����ÿ���
    $('#individuation').click(function (e) {
        e.stopPropagation();    //��ֹ�¼�ð��
        $('.individuation').removeClass('layui-hide').toggleClass('bounceInRight').toggleClass('flipOutY');
    });
    $('.individuation').click(function (e) {
        e.stopPropagation();    //��ֹ�¼�ð��
    })
    $('.layui-body').click(function () {
        $('.individuation').removeClass('bounceInRight').addClass('flipOutY');
    });

    //������ർ�����
    element.on('nav(leftnav)', function (elem) {

        var url = $(elem).children('a').attr('data-url');   //ҳ��url
        var id = $(elem).children('a').attr('data-id');     //tabΨһId
        var title = $(elem).children('a').text();           //�˵�����
        if (title == "��ҳ") {
            element.tabChange('tab', 0);
            return;
        }
        if (url == undefined) return;

        var tabTitleDiv = $('.layui-tab[lay-filter=\'tab\']').children('.layui-tab-title');
        var exist = tabTitleDiv.find('li[lay-id=' + id + ']');
        if (exist.length > 0) {
            //�л���ָ�������Ŀ�Ƭ
            element.tabChange('tab', id);
        } else {
            var index = layer.load(1);
            //����Ajax���ñ��ؾ�̬ҳ����ڿ������⣬������iframe

            setTimeout(function () {
                //ģ��˵�����
                layer.close(index);
                element.tabAdd('tab', { title: title, content: '<iframe src="' + url + '" style="width:100%;height:100%;border:none;outline:none;"></iframe>', id: id });
                //�л���ָ�������Ŀ�Ƭ
                element.tabChange('tab', id);
            }, 500);
        }
    });

    //������ݲ˵����
    $('.short-menu .layui-field-box>div>div').click(function () {
        var elem = this;
        var url = $(elem).children('span').attr('data-url');
        var id = $(elem).children('span').attr('data-id');
        var title = $(elem).children('span').text();

        if (url == undefined) return;

        var tabTitleDiv = $('.layui-tab[lay-filter=\'tab\']').children('.layui-tab-title');
        var exist = tabTitleDiv.find('li[lay-id=' + id + ']');
        if (exist.length > 0) {
            //�л���ָ�������Ŀ�Ƭ
            element.tabChange('tab', id);
        } else {
            var index = layer.load(1);
            //����Ajax���ñ��ؾ�̬ҳ����ڿ������⣬������iframe
            setTimeout(function () {
                //ģ��˵�����
                layer.close(index);
                element.tabAdd('tab', { title: title, content: '<iframe src="' + url + '" style="width:100%;height:100%;border:none;outline:none;"></iframe>', id: id });
                //�л���ָ�������Ŀ�Ƭ
                element.tabChange('tab', id);
            }, 500);
        }
        $('div.short-menu').slideUp('fast');
    });

    //������ߵ�������
    form.on('switch(sidenav)', function (data) {
        if (data.elem.checked) {
            showSideNav();
        } else {
            hideSideNav();
        }
    });

    //�����ߵ�������¼�
    $('.layui-side-hide').click(function () {
        hideSideNav();
        $('input[lay-filter=sidenav]').siblings('.layui-form-switch').removeClass('layui-form-onswitch');
        $('input[lay-filter=sidenav]').prop("checked", false);
    });

    //��꿿��չ����ߵ���
    $(document).mousemove(function (e) {
        if (e.pageX == 0) {
            showSideNav();
            $('input[lay-filter=sidenav]').siblings('.layui-form-switch').addClass('layui-form-onswitch');
            $('input[lay-filter=sidenav]').prop("checked", true);
        }
    });

    //Ƥ���л�
    $('.skin').click(function () {
        var skin = $(this).attr("data-skin");
        $('body').removeClass();
        $('body').addClass(skin);
    });

    var ishide = false;
    //���ز�ߵ���
    function hideSideNav() {
        if (!ishide) {
            $('.layui-side').animate({ left: '-200px' });
            $('.layui-side-hide').animate({ left: '-200px' });
            $('.layui-body').animate({ left: '0px' });
            $('.layui-footer').animate({ left: '0px' });
            // var tishi = layer.msg('��꿿���Զ���ʾ�˵�', { time: 1500 });
            // layer.style(tishi, {
            //     top: 'auto',
            //     bottom: '50px'
            // });
            ishide = true;
        }
    }
    //��ʾ��ߵ���
    function showSideNav() {
        if (ishide) {
            $('.layui-side').animate({ left: '0px' });
            $('.layui-side-hide').animate({ left: '0px' });
            $('.layui-body').animate({ left: '200px' });
            $('.layui-footer').animate({ left: '200px' });
            ishide = false;
        }
    }


    runSteward();
    //�ܼҹ���
    function runSteward() {
        var layerSteward;   //�ܼҴ���
        var isStop = false; //�Ƿ�ֹͣ����

        getNotReplyLeaveMessage();

        var interval = setInterval(function () {
            getNotReplyLeaveMessage();
        }, 60000);  //1��������һ��

        function getNotReplyLeaveMessage() {
            clearInterval(interval); //ֹͣ��ʱ��
            var content = '<div><p>this is a message</p></div>';
            layerSteward = layer.open({
                type: 1,
                title: 'tig',
                shade: 0,
                resize: false,
                area: ['340px', '215px'],
                time: 10000, //10����Զ��ر�
                skin: 'steward',
                closeBtn: 1,
                anim: 2,
                content: content,
                end: function () {
                    if (!isStop) {
                        interval = setInterval(function () {
                            if (!isStop) {
                                clearInterval(interval);
                                getNotReplyLeaveMessage();
                            }
                        }, 60000);
                    }
                }
            });
            $('.steward').click(function (e) {
                event.stopPropagation();    //��ֹ�¼�ð��
            });
            $('.notnotice').click(function () {
                isStop = true;
                layer.close(layerSteward);
                $('input[lay-filter=steward]').siblings('.layui-form-switch').removeClass('layui-form-onswitch');
                $('input[lay-filter=steward]').prop("checked", false);
            });
            form.on('switch(steward)', function (data) {
                if (data.elem.checked) {
                    isStop = false;
                    clearInterval(interval);
                    runSteward();
                } else {
                    isStop = true;
                    layer.close(layerSteward);
                }
            })
        }
    }

    exports('main', {});
});


