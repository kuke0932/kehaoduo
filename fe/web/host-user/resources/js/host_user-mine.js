/**
 * ${kehaoduo}
 * @author zhaokaiyuan
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 **/

$('#back').click(function () {
    history.go(-1);
});

var mine = new Vue({
    el: '.container',
    data: {
        shop: {},
        user: {}
    },
    mounted: function () {
        var that = this;
        $.ajax({
            url: '/bs/shop/queryShopByUserId',
            method: 'get',
        }).done(function (data) {
            that.shop = data.data;
        });

        $.ajax({
            url: '/system/web/getUserInfo'
        }).done(function (data) {
            that.user = data.data;
        });
    },
    // 在 `methods` 对象中定义方法
    methods: {

        logout: function () {
            weui.confirm('确认退出系统吗？', {
                title: '退出系统',
                buttons: [{
                    label: '我再想想',
                    type: 'default',
                    onClick: function () {
                        return;
                    }
                }, {
                    label: '退出系统',
                    type: 'primary',
                    onClick: function () {
                        $.ajax({
                            url: '/system/web/logout',
                            method: 'post'
                        }).done(function (data) {
                            location.href = '../login/login.html';
                        });

                    }
                }]
            });
        },
        removeComma: function (value) {
            if (value) {
                return value.replace(/,/g, '').replace(/不限/g, '');
            } else {
                return "";
            }
        }
    }
});

