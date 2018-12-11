/**
 *
 * @author zhaokaiyuan
 * @create 2017-11-22 13:47
 **/
var url = window.location.href;
var strs = url.substr(url.lastIndexOf("/") + 1);
var text = '<div class="weui-tabbar" style="position:fixed;bottom:-1px;"><a href="text_page.html" data-index="1" class="weui-tabbar__item "><div class="weui-tabbar__icon"><img src="resources/images/icon/text.svg" alt=""></div><p class="weui-tabbar__label">文字扫码</p></a>';
var img = '<a href="img_page.html" data-index="2" class="weui-tabbar__item "><div class="weui-tabbar__icon"><img src="resources/images/icon/img.svg" alt=""></div><p class="weui-tabbar__label">图文扫码</p></a>';
var mine = '<a href="mine_page.html" data-index="4" class="weui-tabbar__item"><div class="weui-tabbar__icon"><img src="resources/images/icon/mine.svg" alt=""></div><p class="weui-tabbar__label">我的</p></a></div>';
if (strs == 'text_page.html') text = text.replace('weui-tabbar__item', 'weui-tabbar__item weui-tabbar__item--on').replace(/.svg/ig, '_active.svg');
if (strs == 'img_page.html') img = img.replace('weui-tabbar__item', 'weui-tabbar__item weui-tabbar__item--on').replace(/.svg/ig, '_active.svg');
if (strs == 'mine_page.html') mine = mine.replace('weui-tabbar__item', 'weui-tabbar__item weui-tabbar__item--on').replace(/.svg/ig, '_active.svg');
//var div = document.createElement('div');div.innerHTML = text+img+friends+mine;
document.write(text + img + mine);
//document.body.insertBefore(div, document.getElementsByTagName('script')[1]);
//window.onload = function () {
//};
