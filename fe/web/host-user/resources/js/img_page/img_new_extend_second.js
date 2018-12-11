/**
 *
 * @author Dragon
 * @create 2017-11-23 15:40
 **/
$(function () {
    // 返回
    $('.back_btn').click(function () {
        history.go(-1);
    });

    //选择区域
    $('.distPicker').click(function () {
        var that = this;
        weui.picker(allDistrict, {
            container: 'body',
            defaultValue: ['河北省', '石家庄市', '长安区'],
            onConfirm: function (result) {
                $(that).val(result[0].label + "," + result[1].label + "," + result[2].label);
            },
            className: 'distPicker'
        });
    });

    // 拿到缓存的数据
    var previewImgContent = JSON.parse(window.localStorage.getItem("previewImgContent"));
    var contentList = previewImgContent.contentList;
    var id = previewImgContent.id;
    var content = '';
    for (var i = 0; i < contentList.length; i++) {
        if (contentList[i].contentText) {
            content += '<p>';
            content += contentList[i].contentText;
            content += '</p>';
        }
        if (contentList[i].contentImage) {
            content += '<img src="' + contentList[i].contentImage + '">';
        }
    }

    // 从我的审核点过来以后需要从数据库拿值赋值到页面
    if (id) {
        $.ajax({
            method: 'GET',
            url: '/bs/web/publicity/' + id + '/queryPublicity'
        }).done(function (data) {
            var publicity = data.data;

            var valid = publicity.validAreaCode.split(';');
            valid.forEach(function (el,i) {
                $('.weui-input.distPicker')[i].value = el;
            })

            var toUserType = publicity.toUserType - 1;
            $($('.toUserType')[toUserType]).prop('checked', 'checked');

            if (publicity.isContainPhone == 1) {
                $('#b11').prop('checked', 'checked');
            }
        })
    }

    // 提交
    $('#submit').click(function () {
        // 有效区域
        var validAreaCode = [];
        $('.weui-input.distPicker').each(function () {
            var value = $(this).val();
            if (value) {
                validAreaCode.push(value);
            }
        });
        if (validAreaCode.length == 0) {
            weui.alert("请选择推广区域");
            return;
        }

        // 发送给用户类型
        var UserTypes = [],toUserType;
        $.each($('.toUserType:checkbox:checked'), function () {
            UserTypes.push($(this).val())
        })
        if (UserTypes.indexOf("1") !== -1) { // 包含所有用户选项
            if (UserTypes.indexOf("4") !== -1) { //包含所有用户和群
                toUserType = "1,4";
            } else {
                toUserType = "1";
            }
        } else if (UserTypes.indexOf("2") !== -1 && UserTypes.indexOf("3") !== -1) { //包含男女选项
            if (UserTypes.indexOf("4") !== -1) { //包含男女选项和群
                toUserType = "1,4";
            } else {
                toUserType = "1";
            }
        } else { //不包含所有用户选项
            toUserType = UserTypes.join(",")
        }

        // 使用让客户留电话功能
        var isContainPhone = $('#b11[type="checkbox"]:checked').length;

        var data = {
            publicityTitle: previewImgContent.publicityTitle,
            publicityIntro: previewImgContent.publicityIntro,
            content: content,
            contentList: contentList,
            publicityType: 1,
            checkStatus: 2,
            contentType: 2,
            validAreaCode: validAreaCode.join(";"),
            isContainPhone: isContainPhone,
            toUserType: toUserType
        };
        var url;
        if (id) {
            url = "/bs/web/publicity/updatePublicity";
            data.id = id;
        } else {
            url = "/bs/web/publicity/addPublicity";
        }

        $.ajax({
            method: "POST",
            url: url,
            contentType: "application/json",
            data: JSON.stringify(data)
        }).done(function (data) {
            weui.toast("提交成功");
            window.localStorage.setItem('previewImgContent', null);
            setTimeout(function () {
                window.location = "../img_page.html";
            }, 1000);
        })
    })
});