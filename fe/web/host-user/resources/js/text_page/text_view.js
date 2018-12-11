/**
 *
 * @author Dragon
 * @create 2017-11-15 16:22
 **/
$(function () {
    // 返回
    $('.back_btn').click(function () {
        history.go(-1);
    });

    // 初始化数据
    new Vue({
        el: '.container',
        data: {
            scanCount: 0,
            sendCount: 0,
            publicityResult: []
        },
        mounted: function () {
            var that = this;
            var publicityId = getUrlQueryName("id");
            $.ajax({
                method: 'GET',
                url: '/bs/web/publicityResult/selectPublicityResult',
                data: {
                    publicityId: publicityId,
                    contentType: 1
                }
            }).done(function (data) {
                if (data.data.scanCount) {
                    that.scanCount = data.data.scanCount;
                }
                if (data.data.sendCount) {
                    that.sendCount = data.data.sendCount;
                }
                that.publicityResult = data.data.publicityResult;
            })
        }

    });

    // 格式化日期
    Vue.filter('time', function (value) {
        return utils.timeStampToDate(value);
    });
});