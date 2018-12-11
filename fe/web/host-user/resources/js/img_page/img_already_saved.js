/**
 *
 * @author Dragon
 * @create 2017-11-15 16:09
 **/
$(function () {
    // 返回
    $('.back_btn').click(function () {
        history.go(-1);
    });

    // 初始化数据数据
    new Vue({
        el: '.container',
        data: {
            listPublicity: []
        },
        mounted: function () {
            var that = this;
            var contentType = null;
            $.ajax({
                method: 'GET',
                url: '/bs/web/publicity/1/1/2/listPublicityByUserId'
            }).done(function (data) {
                that.listPublicity = data.data.rows;
                that.total = data.data.rows.length;
            })
        },
        updated: function () {
            var that = this;

            // 编辑
            $('.edit').click(function () {
                var id = $(this).parent('div').attr('data-id');
                window.location.href = "/web/host-user/img_page/img_edit.html?id=" + id;
            });

            // 删除
            $('.draftRemove').click(function () {
                var id = $(this).parent('div').attr('data-id');
                var index = $(this).parent('div').data('index');
                weui.confirm('确定删除吗？删除后不可恢复！',
                    function () {
                        // 确定
                        $.ajax({
                            method: "DELETE",
                            url: "/bs/web/publicity/" + id + "/delete"
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
        }
    });
    // 格式化日期
    Vue.filter('time', function (value) {
        return utils.timeStampToDate(value);
    });

});