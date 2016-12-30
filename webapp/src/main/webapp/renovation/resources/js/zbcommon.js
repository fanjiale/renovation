/**
 * Created by steven.zhang on 2015/6/27.
 */
//RSA加密处理
function GetUrlParam(name)
{
    var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null) return unescape(r[2]); return null;
}
var WX = window.navigator.userAgent.toLowerCase();
//var wxsign = GetUrlParam('wxsign');
//var wxopenid = GetUrlParam('openid');
if(WX.match(/MicroMessenger/i) == 'micromessenger')
{
    var wx_openid=getCookie1('wx_openid');
    if(!wx_openid){
        if(!getCookie1('rederect_get_openid')){
            setCookie1('rederect_get_openid',1,5000);
            var fburl = encodeURIComponent(location.href);
            var _t = new Date().getTime();
            window.location.href='http://m.to8to.com/open/GetWxOpenId?_t='+_t+'&fburl='+fburl;
        }
    }else{
        var openid_input = $('input[name=openid]');
        if(openid_input.length){
            openid_input.val(wx_openid);
        }
    }
}

function setCookie1(name, value, expire, pre)
{
    if (!expire){
        expire = 5000;
    };

    if (pre){
        name = 'to8to_' + name;
    };

    var expiry = new Date();
    expiry.setTime(expiry.getTime() + expire);
    document.cookie = name + '=' + value + ';expires=' + expiry.toGMTString() + ';path=/;domain=.to8to.com';
}
function getCookie1(name, pre) {
    if (pre)
        name = 'to8to_' + name;
    var r = new RegExp("(\\b)" + name + "=([^;]*)(;|$)");
    var m = document.cookie.match(r);
    var res = !m ? "": decodeURIComponent(m[2]);
    var result = stripscript1(res);
    return result;

}
function stripscript1(s)
{
    var pattern = new RegExp("[%--`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
    //格式 RegExp("[在中间定义特殊过滤字符]")
    var rs = "";
    for (var i = 0; i < s.length; i++) {
        rs = rs + s.substr(i, 1).replace(pattern, '');

    }
    return rs;

}
function rsaEncryptNameAndPhone(dataObj, type, unReEmpty) {
    var type = type || false,
        date = new Date(),
        chenghu = '',
        phoneObj = dataObj.phoneObj,
        chenhuObj = dataObj.chenhuObj,
        phoneTmp = phoneObj.val(),
        rsadata, data;

    if (chenhuObj) {//有称呼
        chenghu = chenhuObj.val();
    }
    rsadata = RSAUtils.encryptfun(phoneObj.val() + ',' + chenghu + ',' + date.getTime() + ',' + Math.random());
    if (!unReEmpty) {//清空
        phoneObj.val('').trigger('blur');
        chenhuObj && chenhuObj.val('').trigger('blur');
    }

    if (type) {
        return 'phone=' + phoneTmp + '&' + rsadata;
    } else {
        return '&rsadata=' + rsadata + '&rsastatus=1';
    }
}

//新版RSA加密
function rsaNewEncryptPhoneName(phone, chenghu) {
    var rsadata = RSAUtilszb.encryptfun(phone + ',' + (Math.ceil(Math.random() * 10)) + ',' + Math.random())
    rsadata = encodeURIComponent(rsadata);
    return '&rsadata='+rsadata+'&rsastatus=1&phoneurlencode=1&chenghu='+chenghu;
}

/*获取openId
* */
function getopenId(code,openid){
    $.ajax({
        type:'POST',
        dataType: 'json',
        url:'http://m.to8to.com/Open/GetOpenId',
        data:'code='+code+'openid='+openid,
        success:function(msg){
            $('input:hidden[name="uid"]').length && $('input:hidden[name="uid"]').val(msg.uid);
            $('input:hidden[name="fromid"]').length && $('input:hidden[name="fromid"]').val(msg.uid);
            $('input:hidden[name="authOpenid"]').length && $('input:hidden[name="authOpenid"]').val(msg.authOpenid);
            $('input:hidden[name="openid"]').length && $('input:hidden[name="openid"]').val(msg.openid);
            $('input:hidden[name="reurl"]').length && $('.reurl').attr('href',msg.redirectUrl);
        }
    })
}

$(function(){
    getUrlQuery();
    publickClickStream();
})
//过滤URL字段
function getUrlQuery(){
    var hiddenArr = ['fromnewapp','channel','fromapp','to8to_from','code','ptag','device_src','xinbaojia','pro_s_sourceid','sourceid','uid','idfa','openid','pagetype','authOpenid','openid','gonghao','isprice','yid','logId','appid','appversion'];
    var queryList = parseQuery();
    var appshow = 0;
    for (var i in queryList) {
        if(i == 'channel')
        {
            $('input:hidden[name="to8to_from"]').length && $('input:hidden[name="to8to_from"]').val(queryList[i]);
        }
        if(i == 'to8to_tgid') {
            $('input:hidden[name="gonghao"]').length && $('input:hidden[name="gonghao"]').val(queryList[i]);
        }
        if (i == 'appid' || i == 'appversion') {
            appshow = 1;
        }
        if (i== 'imei') {
            $('input:hidden[name="idfa"]').length && $('input:hidden[name="idfa"]').val(queryList[i]);
        };
        if (i== 'uid') {
            $('input:hidden[name="fromid"]').length && $('input:hidden[name="fromid"]').val(queryList[i]);
        };
        if(inArray(hiddenArr, i)) {
            $('input:hidden[name="'+ i +'"]').length && $('input:hidden[name="'+ i +'"]').val(queryList[i]);
        }
    };
    if(appshow){
        $('input:hidden[name="fromapp"]').length && $('input:hidden[name="fromapp"]').val(1);
    }
    if(queryList && queryList.code) {
        getopenId(queryList.code,queryList.openid);
    }
}

function publickClickStream(){
    $(document).on("touchstart", '.publick-tap-stream',function(){
        var ptag = $(this).data('ptag');
        var clickPhoneTimes = localStorage[ptag];
        if(!clickPhoneTimes){
            localStorage[ptag] = ptag;
            clickStream.getCvParams(ptag);
        }
    });
    $(document).on("click", '.publick-click-stream',function(){
        var ptag = $(this).data('ptag');
        var clickPhoneTimes = localStorage[ptag];
        if(!clickPhoneTimes){
            localStorage[ptag] = ptag;
            clickStream.getCvParams(ptag);
        }
    });
}

function inArray(arr,find){
    var arrLen = arr.length || 0;
    var _flag = false;
    if (arrLen <= 0) { return false};

    for (var i = 0; i < arrLen; i++) {
        if (find == arr[i]) {
            _flag = true;
            break;
        };
    }

    return _flag;
}
// 获取当前url以及to8to_Form
function parseQuery(url) {
    var url = url || location.href;
    var query = url ? (url.split('?')[1] || '') : location.search;
    var queryList = query.substr(0).split('&');
    var parseRes = {};

    if (queryList.length > 0) {
        for (var i = 0; i < queryList.length; i++) {
            var kv = queryList[i].split('=');
            parseRes[kv[0]] = decodeURIComponent(kv[1]).split('#')[0];
        }
    }
    return parseRes;
}
//获取当前页面申请次数
function getApplySum(){
   $.ajax({
        type:'POST',
        DataType:'text',
        url:'http://m.to8to.com/Open/ApplySum',
        success:function(msg){
            $('.applysum').html(msg);
        }
    })
}
getApplySum();


function get_device_src(){
    var sUserAgent = navigator.userAgent.toLowerCase();
    var ipad = sUserAgent.match(/ipad/i) == "ipad";
    var iPod = sUserAgent.match(/iPod/i) == "iPod";
    var iPhone = sUserAgent.match(/iPhone/i) == "iPhone";
    var android = sUserAgent.match(/android/i) == "android";
    var windowsphone = sUserAgent.match(/windows mobile/i) == "windows mobile";
    if(android)
    {
        $('input[name="device_src"]').val(3);
    }else if(ipad) {
        $('input[name="device_src"]').val(2);
    }else if(iPhone){
        $('input[name="device_src"]').val(2);
    }
    else if(windowsphone){
        $('input[name="device_src"]').val(99);
    }else {
        $('input[name="device_src"]').val(1);
    }
}

