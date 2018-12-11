/**
 *
 * @author zhaokaiyuan
 * @create 2017-11-24 10:15
 **/
function CommonLoadData(checkStatus, publicityType, contentType) {
    this.checkStatus = checkStatus;
    this.publicityType = publicityType;
    this.contentType = contentType;

    this.init = function () {
        var result = {
            '1/1': 'text',
            '1/2': 'img',
            '2/2': 'friends_circle'
        };
        var r = result[this.publicityType + '/' + this.contentType];
        this.dataUrl = '/bs/web/publicity/' + this.checkStatus + '/' + this.publicityType + '/' + this.contentType + '/listPublicityByUserId';
        this.logicalDelUrl = '/bs/web/publicity/{id}/logicalDel';
        this.draftDelUrl = '/bs/web/publicity/{id}/delete';

        this.showResultUrl = '/web/host-user/' + r + '_page/' + r + '_view.html?id={id}';
        this.beginExtendUrl = '/web/host-user/' + r + '_page/' + r + '_begin_extend.html?id={id}';
        this.showAllUrl = '/web/host-user/' + r + '_page/' + r + '_view_full.html?id={id}';
        this.editurl = '/web/host-user/' + r + '_page/' + r + '_edit.html?id={id}';
    };

    this.init();

    this.newVueInstance = function () {
        var self = this;
        return new Vue({
            el: '.container',
            data: {
                listPublicity: [],
                total: -1,
                hasMore: true,
                currentPage: 1,
                isLoading: false
            },
            mounted: function () {
                var that = this;
                that.pullToRefresh();
                that.infinite();

                $.ajax({
                    method: 'GET',
                    url: self.dataUrl
                }).done(function (data) {
                    var page = data.data;
                    that.listPublicity = page.rows;
                    that.total = page.total;
                    that.currentPage = page.pageNum;
                    if (page.size < page.pageSize) {
                        that.hasMore = false;
                    }
                    var type = '',currentType;
                    for (var i = 0; i < page.size; i++) {
                        var toUserType = that.listPublicity[i].toUserType;
                        if (toUserType.indexOf(",") > -1) {
                            toUserType.split(",").forEach(function (v) {
                                switch (parseInt(v)) {
                                    case 1:
                                        currentType = "全部好友";
                                        break;
                                    case 2:
                                        currentType = "男性朋友";
                                        break;
                                    case 3:
                                        currentType = "女性朋友";
                                        break;
                                    case 4:
                                        currentType = "群";
                                        break;
                                    case 5:
                                        currentType = "最近联系人";
                                        break;
                                }
                                type += currentType + "，"
                            });
                            type = type.substr(0, type.length - 1)
                        } else {
                            switch (parseInt(toUserType)) {
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
                        }
                        that.listPublicity[i].type = type;
                    }
                }).always(function () {

                })
            },
            watch: {
                hasMore: function (val, oldVal) {
                    if (val == false) {
                        $(document.body).destroyInfinite();
                    }
                }
            },
            updated: function () {
                var that = this;

                // 查看全文
                $('.showAll').click(function () {
                    var id = $(this).attr('data-id');
                    window.location.href = self.showAllUrl.replace('{id}', id);
                });

                // 开始推广
                $('.beginExtend').click(function () {
                    var id = $(this).parent('div').attr('data-id');
                    window.location.href = self.beginExtendUrl.replace('{id}', id);
                });

                // 查看效果
                $('.showResult').click(function () {
                    var id = $(this).parent('div').attr('data-id');
                    window.location.href = self.showResultUrl.replace('{id}', id);
                });

                // 删除
                $('.remove').click(function () {
                    var id = $(this).parent('div').attr('data-id');
                    var index = $(this).parent('div').data('index');
                    weui.confirm('确定删除吗？删除后不可恢复！',
                        function () {
                            // 确定
                            $.ajax({
                                method: "DELETE",
                                url: self.logicalDelUrl.replace('{id}', id)
                            }).done(function (data) {
                                weui.toast("删除成功");
                                that.listPublicity.splice(index, 1);
                                that.total = that.listPublicity.length;
                            }).fail(function (jqXhr) {
                                weui.alert("删除失败");
                            })
                        },
                        function () {
                            // 取消
                        }, {
                            title: '删除'
                        });
                });

                $('.draftRemove').click(function () {
                    var id = $(this).parent('div').attr('data-id');
                    var index = $(this).parent('div').data('index');
                    weui.confirm('确定删除吗？删除后不可恢复！',
                        function () {
                            // 确定
                            $.ajax({
                                method: "DELETE",
                                url: self.draftDelUrl.replace('{id}', id)
                            }).done(function (data) {
                                weui.toast("删除成功");
                                that.listPublicity.splice(index, 1);
                            }).fail(function (jqXhr) {
                                var resp = jqXhr.responseJSON;
                                weui.alert(resp.message);
                            })
                        },
                        function () {
                            // 取消
                        }, {
                            title: '删除'
                        });
                });

                //图片裁剪
                $('.imgItem').each(function(){
                    $(this)[0].onload = function () {
                        var imgh = $(this).height();
                        var imgw = $(this).width();
                        if (imgh < imgw) {
                            $(this).css("height", "80px");
                            var rw = $(this).width();
                            $(this).css({
                                "position": "absolute",
                                "left": "50%",
                                "top": "0",
                                "margin-left": -rw / 2 + "px",
                            })
                        } else if (imgh > imgw) {
                            $(this).css("width", "80px");
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
            methods: {
                infinite: function () {
                    var that = this;
                    // 上拉加载更多
                    $(document.body).infinite(50).on("infinite", function () {

                        if (that.isLoading) return;
                        that.isLoading = true;
                        $.ajax({
                            method: 'GET',
                            url: self.dataUrl,
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
                            var type = '',currentType;
                            for (var i = 0; i < page.size; i++) {
                                var toUserType = that.listPublicity[i].toUserType;
                                if (toUserType.indexOf(",") > -1) {
                                    toUserType.split(",").forEach(function (v) {
                                        switch (parseInt(v)) {
                                            case 1:
                                                currentType = "全部好友";
                                                break;
                                            case 2:
                                                currentType = "男性朋友";
                                                break;
                                            case 3:
                                                currentType = "女性朋友";
                                                break;
                                            case 4:
                                                currentType = "群";
                                                break;
                                            case 5:
                                                currentType = "最近联系人";
                                                break;
                                        }
                                        type += currentType + "，"
                                    });
                                    type = type.substr(0, type.length - 1)
                                } else {
                                    switch (parseInt(toUserType)) {
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
                                }
                                that.listPublicity[i].type = type;
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
                            $.ajax({
                                method: 'GET',
                                url: self.dataUrl,
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
                                var type = '',currentType;
                                for (var i = 0; i < page.size; i++) {
                                    var toUserType = that.listPublicity[i].toUserType;
                                    if (toUserType.indexOf(",") > -1) {
                                        toUserType.split(",").forEach(function (v) {
                                            switch (parseInt(v)) {
                                                case 1:
                                                    currentType = "全部好友";
                                                    break;
                                                case 2:
                                                    currentType = "男性朋友";
                                                    break;
                                                case 3:
                                                    currentType = "女性朋友";
                                                    break;
                                                case 4:
                                                    currentType = "群";
                                                    break;
                                                case 5:
                                                    currentType = "最近联系人";
                                                    break;
                                            }
                                            type += currentType + "，"
                                        });
                                        type = type.substr(0, type.length - 1)
                                    } else {
                                        switch (parseInt(toUserType)) {
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
                                    }
                                    that.listPublicity[i].type = type;
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
    }
}
