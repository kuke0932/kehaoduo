/**
 *
 * @author Dragon
 * @create 2017-11-23 15:39
 **/
$(function () {
    // 返回
    $('.back_btn').click(function () {
        history.go(-1);
    });
});

/**
 * 初始化数据
 */
new Vue({
    el: '.container',
    data: {
        publicityIntro: '',
        publicityTitle: '',
        content: ''
    },
    mounted: function () {
        var previewImgContent = JSON.parse(window.localStorage.getItem("previewImgContent"));
        var contentList = previewImgContent.contentList;
        var content = '';
        this.publicityIntro = previewImgContent.publicityIntro;
        this.publicityTitle = previewImgContent.publicityTitle;
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
        this.content = content;

        // 下一步
        $('#next').click(function () {
            window.location.href = "img_new_extend_second.html";
        })
    }
});