/**
 *
 * @author Dragon
 * @create 2017-11-15 16:21
 **/
$(function () {
    // 返回
    $('.back_btn').click(function () {
        history.go(-1);
    });

    // 提交审核
    $('.text_add_btn').click(function () {
        // 审核状态0 未填写 1 已保存2 未审核 3 审核通过 4 审核不通过
        // 目前是直接审核通过，后期需要审核的话，把参数改成2
        save(2);
    })
});

/**
 * 保存
 * @param checkStatus 审核状态
 */
function save(checkStatus) {
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
        publicityTitle: publicityTitle,
        content: content,
        publicityType: 1,
        checkStatus: checkStatus,
        contentType: 1,
        toUserType: toUserType
    };

    $.ajax({
        method: "POST",
        url: "/bs/web/publicity/addPublicity",
        data: data
    }).done(function (data) {
        weui.toast('提交成功');
        setTimeout(function () {
            window.location = "../text_page.html";
        }, 1000);
    })
}