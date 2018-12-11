/**
 *
 * @author Dragon
 * @create 2017-11-15 16:11
 **/
var webSocket = null;
$(function () {
    // 返回
    $('.back_btn').click(function () {
        history.go(-1);
    });

    // 判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        var site = window.site.split('/')[0]
        webSocket = new WebSocket("ws://"+ site +":8082/bs/webSocket");
    } else {
        weui.alert('当前浏览器不支持webSocket')
    }

    /**
     * 连接发生错误的回调方法
     */
    webSocket.onerror = function (event) {
        console.log("握手失败")
    };

    /**
     * 连接成功建立的回调方法
     */
    webSocket.onopen = function (event) {
        console.log("握手成功");

    };

    /**
     * 接收到消息的回调方法
     */
    webSocket.onmessage = function (event) {
        var retData = JSON.parse(event.data);
        weui.toast(retData.message, function () {
            $('.mask p').html(retData.message);
            $('.mask').show();
        });
    };

    /**
     * 连接关闭的回调方法
     */
    webSocket.onclose = function (event) {
        console.log("连接关闭");
    };

    /**
     * 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
     */
    window.onbeforeunload = function (event) {
        webSocket.close();
    };

    var wssend = function (message, callback) {
        waitForConnection(function () {
            webSocket.send(message);
            if (typeof callback !== 'undefined') {
                callback();
            }
        }, 1000);
    };

    var waitForConnection = function (callback, interval) {
        if (webSocket.readyState === 1) {
            callback();
        } else {
            // optional: implement backoff for interval here
            setTimeout(function () {
                waitForConnection(callback, interval);
            }, interval);
        }
    };

    var id = getUrlQueryName("id");
    // 初始化数据
    new Vue({
        el: '.container',
        data: {
            map: {
                publicity: {}
            }
        },
        mounted: function () {
            var that = this;
            $.ajax({
                method: 'GET',
                url: '/bs/web/publicity/' + id + '/beginExtend'
            }).done(function (data) {
                that.map = data.data;
                var type;
                switch (that.map.publicity.toUserType) {
                    case 1:
                        type = "全部好友";
                        break;
                    case 2:
                        type = "男性朋友";
                        break;
                    case 3:
                        type = "女性朋友";
                        break;
                    case 4:
                        type = "群";
                        break;
                    case 5:
                        type = "最近联系人";
                        break;
                }
                that.map.publicity.type = type;
                setTimeout(function () {
                    var sendMsg = {
                        id: id,
                        uuid: data.data.uuid,
                        belongAppId: localStorage.getItem("belongAppId")
                    };
                    wssend(JSON.stringify(sendMsg));
                }, 10)

            })
        },
        methods: {
            refreshQrCodeUrl: function () {
                var that = this;
                $.ajax({
                    method: 'GET',
                    url: '/bs/web/publicity/' + id + '/generateQrCodeUrl',
                    data: {
                        oldUuid: that.map.uuid
                    }
                }).done(function (data) {
                    that.map.qrCodeUrl = data.data.qrCodeUrl;
                    var sendMsg = {
                        id: id,
                        uuid: data.data.uuid,
                        belongAppId: localStorage.getItem("belongAppId")
                    };
                    wssend(JSON.stringify(sendMsg));
                    $('.mask').hide();
                });
            }
        }

    })
});
