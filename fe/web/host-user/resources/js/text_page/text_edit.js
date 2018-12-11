/**
 *
 * @author Dragon
 * @create 2017-12-21 16:50
 **/
$(function () {
    // 返回
    $('.back_btn').click(function () {
        history.go(-1);
    });

    var id = getUrlQueryName("id");
    $.ajax({
        method: 'GET',
        url: '/bs/web/publicity/' + id + '/queryPublicity'
    }).done(function (data) {
        var publicity = data.data;
        $('#publicityTitle').val(publicity.publicityTitle);

        $('#content').html(publicity.content);

        var toUserType = publicity.toUserType - 1;
        $($('.toUserType')[toUserType]).prop('checked', 'checked');
    })

    // 提交审核
    $('.text_add_btn').click(function () {
        // 审核状态0 未填写 1 已保存2 未审核 3 审核通过 4 审核不通过
        // 目前是直接审核通过，后期需要审核的话，把参数改成2
        save(2, id);
    })
});

/**
 * 保存
 * @param checkStatus 审核状态
 * @param id
 */
function save(checkStatus, id) {
    // 标题
    var publicityTitle = $('#publicityTitle').val();
    if (!publicityTitle) {
        weui.alert("请输入标题");
        return;
    }
    // 正文
    var content = $('#content').val();
    if (!content) {
        weui.alert("请输入发送内容");
        return;
    }
    // 发送给用户类型
    var UserTypes = [],toUserType;
    $.each($('input:checkbox:checked'), function () {
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

    var data = {
        id: id,
        publicityTitle: publicityTitle,
        content: content,
        publicityType: 1,
        checkStatus: checkStatus,
        contentType: 1,
        toUserType: toUserType
    };

    $.ajax({
        method: "POST",
        url: "/bs/web/publicity/updatePublicity",
        data: data
    }).done(function (data) {
        weui.toast('提交成功');
        setTimeout(function () {
            window.location = "../text_page.html";
        }, 1000);
    })
}