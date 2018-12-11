/**
 *
 * @author Dragon
 * @create 2017-11-21 16:32
 **/

$(function () {
    /*textarea自动伸缩*/
    autosize(document.querySelectorAll('textarea'));

    var id = getUrlQueryName("id");
    $.ajax({
        method: 'GET',
        url: '/bs/web/publicity/' + id + '/queryPublicity'
    }).done(function (data) {
        var previewImgContent = data.data;
        if (previewImgContent) {
            $('.main_content').empty();
            // 标题赋值
            var publicityTitle = previewImgContent.publicityTitle;
            $('#publicityTitle').val(publicityTitle);
            // 简介赋值
            var publicityIntro = previewImgContent.publicityIntro;
            $('#publicityIntro').html(publicityIntro);

            // 图片裁剪
            checksize();

            // 正文赋值
            var contentList = previewImgContent.contentList;
            var contentImage = '';
            for (var i = 0; i < contentList.length; i++) {
                var flag = false;
                if(contentList[i] !== null){
                    var text = contentList[i].contentText || "";
                    var contentText = '<textarea class="weui-textarea content" placeholder="请输入正文" rows="1">' +
                        text +
                        '</textarea>';
                    if (contentList[i].contentImage) {
                        contentImage = '<div class="weui-uploader__input-box preview_img" style="display: none;">' +
                            '<div class="preview"><div class="pre"></div></div>'+
                            '<input class="weui-uploader__input" type="file" accept="image/!*" multiple="">' +
                            '</div>' +
                            '<img src="' + contentList[i].contentImage + '" class="imageUrl data-' + i + '">' +
                            '<div class="delete_img"><img src="../resources/images/icon/delete_img.svg" alt=""></div>';
                        flag = true;
                    } else {
                        contentImage = '<div class="weui-uploader__input-box preview_img">' +
                            '<input class="weui-uploader__input" type="file" accept="image/!*" multiple="">' +
                            '</div>'
                    }
                    var html = `
                    <div class="inner_content">
                        <div class="weui-cells weui-cells_form">
                            <div class="weui-cell extend_title">
                                <div class="weui-cell__bd">
                                    ` + contentText + `
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells weui-cells_form">
                            <div class="weui-panel weui-panel_access">
                                <div class="weui-panel__bd add_img">
                                    <div class="weui-media-box weui-media-box_appmsg">
                                        <div class="weui-uploader uploader">
                                            <div class="weui-uploader__bd">
                                                ` + contentImage + `
                                            </div>
                                        </div>
                                        <div class="textarea_content">
                                            <div class="weui-cell__bd">
                                                <p class="" style="text-align: justify;padding: 0 0 0 15px;">
                                                    请在左侧上传图文消息图片，图片大小不超过5M，如图片不合要求，请通过QQ或者微信截图处理。如不需要图片，可不上传。
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>`;
                    $('.main_content').append(html);
                    if (flag) {
                        checksize($('.data-' + i).parents('div.weui-uploader.uploader'));
                    }
                    autosize(document.querySelectorAll('textarea'));
                }
            }
            //删除正文图片
            $('.main_content .delete_img').click(function () {
                var that = this;
                weui.confirm('确定删除吗？',
                    function () {
                        // 确定
                        $(that).siblings('img').remove();
                        $(that).siblings('.weui-uploader__input-box').css({'display': 'inline-block'});
                        $(that).remove();
                    },
                    function () {
                        // 取消
                    }, {
                        title: '删除'
                    });
            });
            window.localStorage.setItem('previewImgContent', null);
        }
        // 图片上传
        upload();
    });

    //键盘隐藏-显示
    toggleKeyboard();

    //继续添加
    $(".continue_add").click(function () {
        var html = `
            <div class="inner_content">
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell extend_title">
                        <div class="weui-cell__bd">
                            <textarea class="weui-textarea content" id="" placeholder="请输入正文" rows="1"></textarea>
                        </div>
                    </div>
                </div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-panel weui-panel_access">
                        <div class="weui-panel__bd add_img">
                            <div class="weui-media-box weui-media-box_appmsg">
                                <div class="weui-uploader uploader">
                                    <div class="weui-uploader__bd">
                                        <div class="weui-uploader__input-box preview_img">
                                            <div class="preview">
                                                <div class="pre"></div>
                                            </div>
                                            <input class="weui-uploader__input" type="file" accept="image/*" multiple="">
                                        </div>
                                    </div>
                                </div>
                                <div class="textarea_content">
                                    <div class="weui-cell__bd">
                                        <p class="" style="text-align: justify;padding: 0 0 0 15px;">
                                            请在左侧上传图文消息图片，图片大小不超过5M，如图片不合要求，请通过QQ或者微信截图处理。如不需要图片，可不上传。
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`;
        $('.main_content').append(html);
        autosize(document.querySelectorAll('textarea'));
        upload();
        toggleKeyboard()
    });

    // 返回
    $('.back_btn').click(function () {
        history.go(-1);
    });

    // 保存草稿
    $('#saveDraft').click(function () {
        // 审核状态0 未填写 1 已保存2 未审核 3 审核通过 4 审核不通过
        save(1, id);
    });

    // 预览效果
    $('#preview').click(function () {
        save(0, id);
    });
});

/* 输入框获取焦点 底部按钮消失 失去焦点按钮显示 */
function toggleKeyboard(){
    /* var input = document.getElementsByTagName('input');
     var textarea = document.getElementsByTagName('textarea');
     for(var i=0;i<input.length;i++){
         $(input[i]).focus(function(){
             $('.add_btn_box').hide()
         })
         $(input[i]).blur(function(){
             $('.add_btn_box').show()
         })
     }
     for(var j=0;j<textarea.length;j++){
         $(textarea[j]).focus(function(){
             $('.add_btn_box').hide()
         })
         $(textarea[j]).blur(function(){
             $('.add_btn_box').show()
         })
     }*/
    var h0 = window.innerHeight;
    $(window).resize(function(){
        var h1 = window.innerHeight;
        if(h1<h0){
            $('.add_btn_box').hide();
        }else {
            $('.add_btn_box').show();
        }
    })
}

/**
 * 图片上传
 */
function upload() {
    $('.uploader').each(function () {
        var $this = $(this);
        weui.uploader($this, {
            url: '/bs/web/publicity/uploadImg',
            auto: true,
            type: 'file',
            fileVal: 'file',
            compress: false,
            resize: false,
            onBeforeQueued: function (files) {
                // `this` 是轮询到的文件, `files` 是所有文件
                if (["image/jpg", "image/jpeg", "image/png", "image/gif"].indexOf(this.type) < 0) {
                    weui.alert('请上传图片');
                    return false; // 阻止文件添加
                }
                if (this.size > 5 * 1024 * 1024) {
                    weui.alert('请上传不超过5M的图片');
                    return false;
                }
                if (files.length > 1) { // 防止一下子选择过多文件
                    weui.alert('最多只能上传1张图片，请重新选择');
                    return false;
                }

                // return true; // 阻止默认行为，不插入预览图的框架
            },
            onQueued: function () {
                var url = $(this)[0].url;
                $this.find('.preview').show();
                $this.find('.preview').css({'background': 'url('+ url +') center no-repeat','background-size':'75px 75px'})
            },
            onBeforeSend: function (data, headers) {

            },
            onProgress: function (procent) {
            },
            onSuccess: function (ret) {
                var html = '<img src="' + ret.data + '" class="imageUrl">';
                $this.find('.weui-uploader__input-box').css({'display': 'none'});
                $this.find('.weui-uploader__bd').append(html);
                $this.find('.weui-uploader__bd').append('<div class="delete_img"><img src="../resources/images/icon/delete_img.svg" alt=""></div>');
                // 图片裁剪
                checksize($this);
                //删除图片
                $this.find('.delete_img').click(function () {
                    var that = this;
                    weui.confirm('确定删除吗？',
                        function () {
                            // 确定
                            $this.find('.weui-uploader__bd img').remove();
                            $this.find('.weui-uploader__input-box').css({'display': 'inline-block'});
                            $(that).remove();
                        },
                        function () {
                            // 取消
                        }, {
                            title: '删除'
                        });
                })
                // return true; // 阻止默认行为，不使用默认的成功态

            },
            onError: function (err) {
                console.log(this, err);
                weui.alert('图片上传失败!');
                $this.find('.preview').hide();
                // return true; // 阻止默认行为，不使用默认的失败态
            }
        });
    })
}

/*图片裁剪*/
function checksize(that) {
    if (!that) {
        return;
    }
    var img = that.find(".preview_img").siblings('img');
    img[0].onload = function () {
        that.find('.preview').hide();
        var imgh = img.height();
        var imgw = img.width();
        if (imgh < imgw) {
            img.css("height", "75px");
            var rw = img.width();
            img.css({
                "position": "absolute",
                "left": "50%",
                "top": "0",
                "margin-left": -rw / 2 + "px",
            })
        } else if (imgh > imgw) {
            img.css("width", "75px");
            var rh = img.height();
            img.css({
                "position": "absolute",
                "top": "50%",
                "left": "0",
                "margin-top": -rh / 2 + "px",
            })
        } else {
            img.css({
                "width": "100%",
                "height": "100%",
            })
        }
    }
}
/**
 * 保存
 * @param checkStatus 审核状态
 */
function save(checkStatus, id) {
    // 简介
    var publicityIntro = $('#publicityIntro').val();
    if (!publicityIntro) {
        weui.alert("请设置扫码群发时封面消息");
        return;
    }
    // 标题
    var publicityTitle = $('#publicityTitle').val();
    if (!publicityTitle) {
        weui.alert("请输入图文消息标题");
        return;
    }
    // 正文
    var content = [], hasContent = false;
    $('.inner_content').each(function () {
        var data = {};
        data.contentText = $(this).find('.content').val();
        data.contentImage = $(this).find('.imageUrl').attr('src') || '';
        if (data.contentText.length > 0 || data.contentImage) {
            hasContent = true;
        }
        content.push(data);
    });
    if (!hasContent) {
        weui.alert("请输入正文或上传图片");
        return;
    }
    var data = {
        id: id,
        checkStatus: checkStatus,
        publicityType: 1,
        contentType: 2,
        publicityIntro: publicityIntro,
        publicityTitle: publicityTitle,
        contentList: content
    };
    if (checkStatus == 1) {
        // 保存草稿
        $.ajax({
            method: "POST",
            url: "/bs/web/publicity/updatePublicity",
            contentType: "application/json",
            data: JSON.stringify(data)
        }).done(function (data) {
            weui.toast('保存成功');
            setTimeout(function () {
                window.location = "../img_page.html";
            }, 1000);
        })
    } else {
        // 预览效果
        window.localStorage.setItem("previewImgContent", JSON.stringify(data));
        window.location.href = "img_new_extend_preview.html";
    }
}