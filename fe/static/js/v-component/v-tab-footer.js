/**
 * ${kehaoduo}
 * @author zhaokaiyuan
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 **/
// 不同页面标签显示颜色不同
new Vue({
    el: 'tab-footer',
    components: {
        'tab-footer': {
            template: '<div class="weui-tabbar" style="position:fixed;bottom:-1px;">\n' +
            '        <a href="text_page.html" data-index="1" class="weui-tabbar__item">\n' +
            '            <div class="weui-tabbar__icon">\n' +
            '                <img src="resources/images/icon/text.svg" alt="">\n' +
            '            </div>\n' +
            '            <p class="weui-tabbar__label">文字扫码</p>\n' +
            '        </a>\n' +
            '        <a href="img_page.html" data-index="2" class="weui-tabbar__item">\n' +
            '            <div class="weui-tabbar__icon">\n' +
            '                <img src="resources/images/icon/img.svg" alt="">\n' +
            '            </div>\n' +
            '            <p class="weui-tabbar__label">图文扫码</p>\n' +
            '        </a>\n' +
            '        <a href="mine_page.html" data-index="4" class="weui-tabbar__item">\n' +
            '            <div class="weui-tabbar__icon">\n' +
            '                <img src="resources/images/icon/mine.svg" alt="">\n' +
            '            </div>\n' +
            '            <p class="weui-tabbar__label">我的</p>\n' +
            '        </a>\n' +
            '    </div>'
        }
    },
    mounted: function () {
        var url = window.location.href;
        var strs = url.substr(url.lastIndexOf("/") + 1);
        $(".weui-tabbar__item").each(function () {
            var link = $(this).attr('href');
            if (strs == link) {
                // $(this).css({'background': '#eee'});
                var src = $(this).find('.weui-tabbar__icon img')[0].src;
                src = src.replace('.svg', '_active.svg');
                $(this).find('.weui-tabbar__icon img')[0].src = src;
                $(this).find('.weui-tabbar__label').css({'color': '#e64340'})
            }
        })
    }
});
