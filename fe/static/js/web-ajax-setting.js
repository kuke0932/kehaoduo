window.utils = window.utils || {};
window.site = localStorage.getItem('_site') || 'localhost';
if (typeof Vue != 'undefined') {
    Vue.prototype.time = function (value) {
        return utils.timeStampToDate(value);
    };
    Vue.prototype.isEmptyObject = function (e) {
        var t;
        for (t in e)
            return !1;
        return !0
    };
    Vue.filter('time', function (value) {
        return utils.timeStampToDate(value);
    });
}
var oldAjax = $.ajax;
var loading;
$.ajax = function () {
    $.ajaxSetup({
        timeout: 10000,
        beforeSend: function (jqXhr, settings) {
            var auth = window.localStorage.getItem('x-auth-token');
            jqXhr.setRequestHeader('x-auth-token', auth);
            jqXhr.setRequestHeader('appId', localStorage.getItem('belongAppId'));
            if (settings.global) {
                loading = weui.loading('正在加载...');
            }
            return true;
        }
    });
    return oldAjax.apply($, arguments).fail(function (jqXhr, errThrown, status) {
        if (status == 'timeout') {
            jqXhr.abort();    // 超时后中断请求
            weui.alert("网络超时，请刷新页面");
        } else {
            var resp = jqXhr.responseJSON;
            weui.alert(resp.message);
        }
    }).always(function (data, textStatus, status) {
        loading && loading.hide();
    });
};


function getUrlQueryName(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"),
        r = location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}


/**
 * 日期类
 */
(function () {
    /**
     * 将long根式time转换为yyyy-mm-dd hh:dd:ss
     */
    function timeStampToDate(longTime) {
        if (longTime == null || longTime == '') {
            return null;
        }
        var d = new Date(longTime);    //根据时间戳生成的时间对象
        var strMonth = (d.getMonth() + 1) > 9 ? (d.getMonth() + 1) : ('0' + (d.getMonth() + 1));
        var strDay = d.getDate() > 9 ? (d.getDate()) : ('0' + (d.getDate()));
        var strHours = d.getHours() > 9 ? (d.getHours()) : ('0' + (d.getHours()));
        var strMinutes = d.getMinutes() > 9 ? (d.getMinutes()) : ('0' + (d.getMinutes()));
        var strSeconds = d.getSeconds() > 9 ? (d.getSeconds()) : ('0' + (d.getSeconds()));
        var date = (d.getFullYear()) + "-" +
            strMonth + "-" +
            strDay + " " +
            strHours + ":" +
            strMinutes + ":" +
            strSeconds;
        return date;
    }

    /**
     * 将long根式time转换为yyyy-mm-dd
     */
    function timeStampToDate1(longTime) {
        if (longTime == null || longTime == '') {
            return null;
        }
        var d = new Date(longTime);    //根据时间戳生成的时间对象
        var strMonth = (d.getMonth() + 1) > 9 ? (d.getMonth() + 1) : ('0' + (d.getMonth() + 1));
        var strDay = d.getDate() > 9 ? (d.getDate()) : ('0' + (d.getDate()));
        var date = (d.getFullYear()) + "-" +
            strMonth + "-" +
            strDay;
        return date;
    }

    /**
     * 将long根式time转换为mm-dd
     */
    function timeStampToDate2(longTime) {
        if (longTime == null || longTime == '') {
            return null;
        }
        var d = new Date(longTime);    //根据时间戳生成的时间对象
        var strMonth = (d.getMonth() + 1) > 9 ? (d.getMonth() + 1) : ('0' + (d.getMonth() + 1));
        var strDay = d.getDate() > 9 ? (d.getDate()) : ('0' + (d.getDate()));
        var date = strMonth + "-" +
            strDay;
        return date;
    }

    /**
     * 将long根式time转换为 hh:dd
     */
    function timeStampToDate3(longTime) {
        if (longTime == null || longTime == '') {
            return null;
        }
        var d = new Date(longTime);    //根据时间戳生成的时间对象
        var strHours = d.getHours() > 9 ? (d.getHours()) : ('0' + (d.getHours()));
        var strMinutes = d.getMinutes() > 9 ? (d.getMinutes()) : ('0' + (d.getMinutes()));
        var date = strHours + ":" +
            strMinutes;
        return date;
    }

    /**
     * 将long根式time转换为yyyy年
     */
    function timeStampToDate4(longTime) {
        if (longTime == null || longTime == '') {
            return null;
        }
        var d = new Date(longTime);    //根据时间戳生成的时间对象
        var strMonth = (d.getMonth() + 1) > 9 ? (d.getMonth() + 1) : ('0' + (d.getMonth() + 1));
        var strDay = d.getDate() > 9 ? (d.getDate()) : ('0' + (d.getDate()));
        var date = (d.getFullYear()) + "年";
        return date;
    }


    /**
     * 将long根式time转换为MM月dd日 hh:dd
     */
    function timeStampToDate5(longTime) {
        if (longTime == null || longTime == '') {
            return null;
        }
        var d = new Date(longTime);    //根据时间戳生成的时间对象
        var strMonth = (d.getMonth() + 1) > 9 ? (d.getMonth() + 1) : ('0' + (d.getMonth() + 1));
        var strDay = d.getDate() > 9 ? (d.getDate()) : ('0' + (d.getDate()));
        var strHours = d.getHours() > 9 ? (d.getHours()) : ('0' + (d.getHours()));
        var strMinutes = d.getMinutes() > 9 ? (d.getMinutes()) : ('0' + (d.getMinutes()));
        var strSeconds = d.getSeconds() > 9 ? (d.getSeconds()) : ('0' + (d.getSeconds()));
        var date = strMonth + "月" +
            strDay + "日 " +
            strHours + ":" +
            strMinutes;
        return date;
    }

    /**
     * 获取当前时间
     * @returns {String}
     */
    function getDateFull() {
        var date = new Date();
        var currentdate = date.getFullYear() + "-" + appendZero(date.getMonth() + 1) + "-" + appendZero(date.getDate()) + " " + appendZero(date.getHours()) + ":" + appendZero(date.getMinutes()) + ":" + appendZero(date.getSeconds());
        return currentdate;
    }

    utils.timeStampToDate = timeStampToDate;
    utils.timeStampToDate1 = timeStampToDate1;
    utils.timeStampToDate2 = timeStampToDate2;
    utils.timeStampToDate3 = timeStampToDate3;
    utils.timeStampToDate4 = timeStampToDate4;
    utils.timeStampToDate5 = timeStampToDate5;
    utils.getDateFull = getDateFull;
})();
