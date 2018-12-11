/**
 *
 * @author Dragon
 * @create 2017-11-15 16:16
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
            valid: 0,
            allClick: 0,
            validAreaCode: [],
            listPhone: []
        },
        mounted: function () {
            var that = this;
            var publicityId = getUrlQueryName("id");
            $.ajax({
                method: 'GET',
                url: '/bs/web/publicityResult/selectPublicityResult',
                data: {
                    publicityId: publicityId,
                    contentType: 2
                }
            }).done(function (data) {
                var publicityResult = data.data.publicityResult;
                if (publicityResult != null) {
                    that.scanCount = publicityResult.scanCount;
                    that.valid = publicityResult.valid;
                    that.allClick = publicityResult.allClick;
                    var validAreaCode = publicityResult.validAreaCode.split(";");
                    for (var i = 0; i < validAreaCode.length; i++) {
                        validAreaCode[i] = validAreaCode[i].replace(/,/g, "").replace(/不限/g, '');
                    }
                    that.validAreaCode = validAreaCode;
                }
                that.listPhone = data.data.phoneList;
            })
        }

    });
    // 格式化日期
    Vue.filter('time', function (value) {
        return utils.timeStampToDate(value);
    });
});