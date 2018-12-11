/**
 *
 * @author zhaokaiyuan
 * @create 2017-11-24 10:15
 **/
var checkStatusJson = {
    "0": '未提交审核',
    "1": '已保存',
    "2": '待审核',
    "3": '审核通过',
    "4": '审核不通过',
    "5": '取消审核'

}

var publicityAndContentTypeJson = {
    '0/0': '全部',
    '1/1': '文本扫码',
    '1/2': '图文扫码',
    '2/2': '朋友圈转发'
};
new Vue({
    el: '#mySubmitted',
    data: {
        listPublicity: [],
        total: -1,
        hasMore: true,
        currentPage: 1,
        isLoading: false,
        checkStatus: -1,
        publicityAndContentType: '0/0'
    },
    mounted: function () {
        var that = this;
        that.pullToRefresh();
        that.infinite();

        $.ajax({
            method: 'GET',
            url: '/bs/web/publicity/-1/listPublicityByUserId'
        }).done(function (data) {
            var page = data.data;
            that.listPublicity = page.rows;
            that.total = page.total;
            that.currentPage = page.pageNum;
            if (page.size < page.pageSize) {
                that.hasMore = false;
            }
            for (var i = 0; i < page.size; i++) {
                that.listPublicity[i].checkStatusText = checkStatusJson[that.listPublicity[i].checkStatus];
                that.listPublicity[i].publicityAndContentType = publicityAndContentTypeJson[that.listPublicity[i].publicityType + '/' + that.listPublicity[i].contentType];
            }
        }).always(function () {

        })
    },
    updated: function(){
        //图片裁剪
        $('.imgItem').each(function(){
            $(this)[0].onload = function () {
                var imgh = $(this).height();
                var imgw = $(this).width();
                if (imgh < imgw) {
                    $(this).css("height", "60px");
                    var rw = $(this).width();
                    $(this).css({
                        "position": "absolute",
                        "left": "50%",
                        "top": "0",
                        "margin-left": -rw / 2 + "px",
                    })
                } else if (imgh > imgw) {
                    $(this).css("width", "60px");
                    var rh = $(this).height();
                    $(this).css({
                        "position": "absolute",
                        "top": "50%",
                        "left": "0",
                        "margin-top": -rh / 2 + "px",
                    })
                } else {
                    $(this).css({
                        "width": "100%",
                        "height": "100%",
                    })
                }
            }
        })
    },
    watch: {
        hasMore: function (val, oldVal) {
            if (val == false) {
                $(document.body).destroyInfinite();
            }
        }
    },
    methods: {
        filterCheckStatus: function () {
            var checkStatusPicker = [{
                "value": "-1",
                "label": "全部",
            }, {
                "value": "2",
                "label": "待审核",
            }, {
                "value": "3",
                "label": "审核通过",
            }, {
                "value": "4",
                "label": "审核不通过",
            }, {
                "value": "5",
                "label": "取消审核",
            }];
            var that = this
            weui.picker(checkStatusPicker, {
                container: 'body',
                defaultValue: ['-1'],
                onConfirm: function (result) {
                    var checkStatus = result[0].value;
                    that.checkStatus = checkStatus;
                    $('#checkStatus').html(checkStatusJson[checkStatus])

                    var url = '/bs/web/publicity/' + that.checkStatus + '/listPublicityByUserId';
                    if (that.publicityAndContentType != '0/0') {
                        var t = that.publicityAndContentType.split('/');
                        url = '/bs/web/publicity/' + that.checkStatus + '/' + t[0] + '/' + t[1] + '/listPublicityByUserId';
                    }

                    $.ajax({
                        method: 'GET',
                        url: url
                    }).done(function (data) {
                        var page = data.data;
                        that.listPublicity = page.rows;
                        that.total = page.total;
                        that.currentPage = page.pageNum;
                        if (page.size < page.pageSize) {
                            that.hasMore = false;
                        }
                        for (var i = 0; i < page.size; i++) {
                            that.listPublicity[i].checkStatusText = checkStatusJson[that.listPublicity[i].checkStatus];
                            that.listPublicity[i].publicityAndContentType = publicityAndContentTypeJson[that.listPublicity[i].publicityType + '/' + that.listPublicity[i].contentType];
                        }
                    }).always(function () {

                    })
                },
                id: 'checkStatus'
            });
        },

        filterPublicityAndContentType: function () {
            var publicityAndContentTypePicker = [{
                "value": "0/0",
                "label": publicityAndContentTypeJson['0/0'],
            }, {
                "value": "1/1",
                "label": publicityAndContentTypeJson['1/1'],
            }, {
                "value": "1/2",
                "label": publicityAndContentTypeJson['1/2'],
            }, {
                "value": "2/2",
                "label": publicityAndContentTypeJson['2/2'],
            }];
            var that = this
            weui.picker(publicityAndContentTypePicker, {
                container: 'body',
                defaultValue: ['0/0'],
                onConfirm: function (result) {
                    var publicityAndContentType = result[0].value;
                    that.publicityAndContentType = publicityAndContentType;
                    $('#publicityAndContentType').html(publicityAndContentTypeJson[publicityAndContentType]);

                    var url = '/bs/web/publicity/' + that.checkStatus + '/listPublicityByUserId';
                    if (publicityAndContentType != '0/0') {
                        var t = publicityAndContentType.split('/');
                        url = '/bs/web/publicity/' + that.checkStatus + '/' + t[0] + '/' + t[1] + '/listPublicityByUserId';
                    }
                    $.ajax({
                        method: 'GET',
                        url: url
                    }).done(function (data) {
                        var page = data.data;
                        that.listPublicity = page.rows;
                        that.total = page.total;
                        that.currentPage = page.pageNum;
                        if (page.size < page.pageSize) {
                            that.hasMore = false;
                        }
                        for (var i = 0; i < page.size; i++) {
                            that.listPublicity[i].checkStatusText = checkStatusJson[that.listPublicity[i].checkStatus];
                            that.listPublicity[i].publicityAndContentType = publicityAndContentTypeJson[that.listPublicity[i].publicityType + '/' + that.listPublicity[i].contentType];
                        }
                    }).always(function () {

                    })
                },
                id: 'publicityAndContentType'
            });
        },
        showAll: function (id, publicityType, contentType) {
            var r
            if (publicityType == 1 && contentType == 2) {
                r = 'img'
            } else if (publicityType == 2 && contentType == 2) {
                r = 'friends_circle'
            }
            location.href = '/web/host-user/' + r + '_page/' + r + '_view_full.html?id=' + id;
        },
        cancelCheck(id) {
            const vm = this;
            weui.confirm(
                '您确定要取消审核吗?',
                function () {
                    $.ajax({
                        method: 'post',
                        url: '/bs/web/publicity/cancelCheck',
                        data: {
                            id: id
                        },
                        global: false,
                    }).done(function (data) {
                        weui.toast("取消审核成功！");
                        setTimeout(function () {
                            location.reload();
                        }, 1000)
                    })
                }, function () {
                }, {
                    title: '取消审核'
                });
        },
        edit: function (publicityId, publicityType, contentType) {
            if (publicityType == 1 && contentType == 1) {
                window.location.href = "/web/host-user/text_page/text_edit.html?id=" + publicityId;
            } else if (publicityType == 1 && contentType == 2) {
                window.location.href = "/web/host-user/img_page/img_edit.html?id=" + publicityId;
            } else if (publicityType == 2 && contentType == 2) {
                window.location.href = "/web/host-user/friends_circle_page/friends_circle_edit.html?id=" + publicityId;
            }
        },
        remove: function (publicityId) {
            weui.confirm(
                '您确定要删除该推广吗?',
                function () {
                    $.ajax({
                        method: 'DELETE',
                        url: '/bs/web/publicity/' + publicityId + '/delete'
                    }).done(function (data) {
                        weui.toast("删除成功！");
                        setTimeout(function () {
                            location.reload();
                        }, 1000)
                    })
                }, function () {
                }, {
                    title: '删除'
                });
        },
        showCheckedResult: function (result) {
            weui.alert(result, {title: '审核结果'});
        },
        infinite: function () {
            var that = this;
            // 上拉加载更多
            $(document.body).infinite(50).on("infinite", function () {

                if (that.isLoading) return;
                that.isLoading = true;
                var url = '/bs/web/publicity/' + that.checkStatus + '/listPublicityByUserId';
                if (that.publicityAndContentType != '0/0') {
                    var t = that.publicityAndContentType.split('/');
                    url = '/bs/web/publicity/' + that.checkStatus + '/' + t[0] + '/' + t[1] + '/listPublicityByUserId';
                }
                $.ajax({
                    method: 'GET',
                    url: url,
                    data: {
                        pageNum: that.currentPage + 1,
                    },
                    global: false,
                }).done(function (data) {
                    var page = data.data;
                    var isNextPage = (that.currentPage + 1) == page.pageNum;
                    //如果页数大于总页数，则返回最后一页。 如果当前页个数小于默认页面个数，则认为数据已经加载完毕。
                    if (!isNextPage || page.size < page.pageSize) {
                        that.hasMore = false;
                        return;
                    }
                    that.listPublicity = page.rows;
                    that.total = page.total;
                    that.currentPage = page.pageNum;
                    for (var i = 0; i < page.size; i++) {
                        that.listPublicity[i].checkStatusText = checkStatusJson[that.listPublicity[i].checkStatus];
                        that.listPublicity[i].publicityAndContentType = publicityAndContentTypeJson[that.listPublicity[i].publicityType + '/' + that.listPublicity[i].contentType];
                    }
                }).always(function () {
                    that.isLoading = false;
                });
            });
        },
        pullToRefresh: function () {
            var that = this;
            $('.container').pullToRefresh({
                onRefresh: function () {
                    that.hasMore = true;
                    var url = '/bs/web/publicity/' + that.checkStatus + '/listPublicityByUserId';
                    if (that.publicityAndContentType != '0/0') {
                        var t = that.publicityAndContentType.split('/');
                        url = '/bs/web/publicity/' + that.checkStatus + '/' + t[0] + '/' + t[1] + '/listPublicityByUserId';
                    }
                    $.ajax({
                        method: 'GET',
                        url: url,
                        global: false,
                    }).done(function (data) {
                        var page = data.data;
                        that.listPublicity = page.rows;
                        that.total = page.total;
                        that.isLoading = false;
                        that.currentPage = page.pageNum;
                        if (page.size < page.pageSize) {
                            that.hasMore = false;
                        }
                        for (var i = 0; i < page.size; i++) {
                            that.listPublicity[i].checkStatusText = checkStatusJson[that.listPublicity[i].checkStatus];
                            that.listPublicity[i].publicityAndContentType = publicityAndContentTypeJson[that.listPublicity[i].publicityType + '/' + that.listPublicity[i].contentType];
                        }

                    }).always(function () {
                        $('.container').pullToRefreshDone();
                        $(document.body).destroyInfinite();
                        that.infinite();
                    });
                }
            });
        }
    }
});
