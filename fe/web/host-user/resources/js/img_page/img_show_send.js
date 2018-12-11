/**
 *
 * @author Dragon
 * @create 2017-11-20 15:02
 **/
/**
 *
 * @author Dragon
 * @create 2017-11-15 16:18
 **/

$.ajax({
    url: "/bs/common/createSign",
    data: {
        url: encodeURIComponent(location.href)
    },
    method: "post",
    global: false
}).done(function (data) {
    var sign = data.data;
    sign.jsApiList = ['getLocation'];
    wx.config(sign);
});

var fromOpenid = getUrlQueryName('fromOpenid');
var code = getUrlQueryName('code');


new Vue({
    el: '.container',
    data: {
        publicity: {}
    },
    created: function () {
        var that = this;
        var publicityId = getUrlQueryName('id');

        wx.ready(function () {
            wx.getLocation({
                type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                success: function (res) {
                    var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                    var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。

                    $.ajax({
                        url: '/bs/web/publicity/queryContent4Click',
                        data: {
                            id: publicityId,
                            latitude: latitude,
                            longitude: longitude,
                            byIP: false,
                            code: code
                        }
                    }).done(function (data) {
                        that.publicity = data.data;

                        var inValidAreaCode = that.publicity.inValidAreaCode;
                        var read = that.publicity.logicalDel == 0 || that.publicity.userHasRead;
                        if (inValidAreaCode && read) {
                            var $body = $('body');
                            document.title = data.data.publicityTitle;
                            // hack在微信等webview中无法修改document.title的情况
                            var $iframe = $('<iframe style="display: none" frameborder="0" src="/favicon.ico"></iframe>').on('load', function () {
                                setTimeout(function () {
                                    $iframe.off('load').remove()
                                }, 0)
                            }).appendTo($body);

                            var readerOpenid = that.publicity.readerOpenid;
                            if(!(that.publicity.logicalDel == 1 || that.publicity.isEnded == 1)) { //未结束 未删除
                                insertPublicityReadLog(publicityId, fromOpenid, readerOpenid)
                            }
                        }
                    });

                }
            });
        });
    }
});


$(function () {
    $(document).on("click", '#addPhone', function () {
        var publicityId = getUrlQueryName("id");
        var phone = $('#tel').val();
        if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(phone))) {
            weui.alert("请输入正确手机号再提交");
            return;
        }

        wx.getLocation({
            type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
            success: function (res) {
                var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                $.ajax({
                    method: 'POST',
                    url: '/bs/web/publicity/insertPhone',
                    data: {
                        phone: phone,
                        validAreaCode: '',
                        publicityId: publicityId,
                        latitude: latitude,
                        longitude: longitude,
                        byIP: false
                    }
                }).done(function (data) {
                    weui.alert("添加成功");
                })
            }
        });
    });
});

/**
 * 生成点击日志
 *
 * @param publicityId
 * @param fromOpenId
 * @param readerOpenId
 */
function insertPublicityReadLog(publicityId, fromOpenId, readerOpenId) {
    $.ajax({
        url: '/bs/web/publicity/insertPublicityReadLog',
        method: 'post',
        data: {
            publicityId: publicityId,
            fromOpenId: fromOpenId,
            readerOpenId: readerOpenId
        },
        global: false
    }).done(function (data) {

    })
}