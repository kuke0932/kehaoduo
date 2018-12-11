/**
 *
 * @author Dragon
 * @create 2017-11-15 16:18
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
            publicity: {}
        },
        mounted: function () {
            var that = this;
            var id = getUrlQueryName("id");
            $.ajax({
                method: 'GET',
                url: '/bs/web/publicity/' + id + '/queryContent'
            }).done(function (data) {
                that.publicity = data.data;
            })
        }
    });
});