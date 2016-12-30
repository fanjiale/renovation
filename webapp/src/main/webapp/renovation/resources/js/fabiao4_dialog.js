/**
 * Created by hank.lan on 2016/9/2.
 */
var _scriptMd5 = document.createElement('script');
_scriptMd5.type = 'text/javascript';
_scriptMd5.src = "http://static.to8to.com/gb_js/jQuery.md5.js";
document.getElementsByTagName('head')[0].appendChild(_scriptMd5);

if(typeof RSAUtilszb == "undefined"){
    var elemetRSA = document.createElement('script');
    elemetRSA.type = 'text/javascript';
    elemetRSA.src = "http://static.to8to.com/gb_js/to8torsaszb.js";
    document.getElementsByTagName('head')[0].appendChild(elemetRSA);
}
window.msgalert1 = (function () {
    var $aol = $('<div id="alertOveryLayer" style="position: fixed; left: 0px; top: 0px; z-index: 102;zoom:1;text-align: center;  bottom: 0px; right: 0px; background-color: rgba(0, 0, 0, 0.298039);"><div style="  position: absolute;z-index: 999; display: -moz-box;  display: -webkit-box;  display: -webkit-flex;  display: -moz-flex;  display: -ms-flexbox;  display: -ms-flex;  display: flex;top: 30%;width: 100%;"><div class="alert-lay" style="/* display: table-cell; */vertical-align: middle;padding: 0 10%;-webkit-box-sizing: border-box;  -moz-box-sizing: border-box;  box-sizing: border-box;  display: block;  -webkit-box-flex: 1;  -moz-box-flex: 1;  -webkit-flex: 1 1 0;  -moz-flex: 1 1 0;  -ms-flex: 1 1 0;  flex: 1 1 0;  text-align: center;"><div class="overlay-bd" style="background-color: #fff;font-size: 14px;color: #333333;line-height: 24px;padding: 20px 15px;text-align: center;border-radius: 7px 7px 0 0;">asdf</div><div class="overlay-foot" style="border-top: 1px solid #e3e3e3;"><div class="overlay-btn overlay-btn-ok" style="background-color: #fff;cursor: pointer;font-size: 16px;color: #00a1ff;height: 42px;line-height: 42px;border-radius: 0 0 7px 7px;">好的</div></div></div></div></div>');
    var cb = null;
    var sid;
    $aol.appendTo(top.document.body);
    $aol.hide();
    $aol.find(".overlay-btn-ok").click(function () {
        $aol.hide();
        typeof cb == 'function' && cb();
        cb = null;
        clearInterval(sid);
    });
    return function (msg, cbk) {
        $aol.find(".overlay-bd").html(msg);
        $aol.show();
        cb = cbk;
        sid = setInterval(function () {
            //document.body.scrollIntoView();
        }, 50);
    };
})();
var hasck=false;
var doubleSubmit = false;
var page_href = window.location.href.split('/')[3] || '';//抓取链接判断不同模块
(function($, that) {    
    var zxbjObj = {
        init: function(tp) {
            initEvent();
            initSelec('secshi','室');
            initSelec('secting','厅');
            initSelec('secchu','厨');
            initSelec('secwei','卫');
            initSelec('secyangtai','阳台');
            if(tp=='cs')
            {
                initCIty();
            }
        }
    };
    function initCIty()
    {
        $.ajax({
            type: "POST",
            url: 'http://m.to8to.com/index/citys',
            dataType: 'json',
            data:{from_ip:1} ,
            success: function (res) {
                var city=res['cityname'] ||'深圳';
                var province=res['provincename'] || '广东';
                $('#slide_area').val(province+' '+city);
            }
        });
    }
    function initEvent() {
        var values= $("#slide_area");
        var getvalues=$("#slide_area").val();
        values.css("color","#a9a9a9");
        if(!(getvalues =="请选择您的所在城市")){
            $("#slide_area").css("color","#000")
        }
        $('input[name="ptag"]').buriedPoint();

        $('.fill-msg input[type=button], #mfyy-submit').on('touchstart',function(){
            $(this).css("opacity",.8);
        });

        $('.fill-msg input[type=button], #mfyy-submit').on('touchend',function(){
            $(this).css("opacity",1);
        });
        $("#ask-form-submit").on('touchstart', function(e){
            if(!hasck){
                $(this).css({
                    'opacity': 1,
                    'background': '#FF9011'
                });
            }
        });
    

        var mark = false;
        $("#ask-form-submit").on('touchmove', function(e){
            mark = true;
        });
        $("#ask-form-submit").on('touchend',function(e)
        {   
            $(this).removeAttr('style');
            //排除滑动触发
            if(mark){
                mark = false;
                return;
            }
            e.preventDefault();
            var phone=$('#newPhone').val();
            ruku(phone);
        });
        $('#close-one').on('touchstart',function(){
            $('#msgOne').hide();
            $("#close2").show();
        });

        $("#close-two").on('touchstart',function(e){
            e.preventDefault();
            $('#dt-hd-clcik')[0].click();
            $('#close2').hide();
            $('.layerout').hide();
        });
        var bStyle=$('.jiting,.jishi').find('b');
        var tel=$('.jiting,.jishi').find('input[type="tel"]');

        tel.on('keypress',function(){
            bStyle.css("color","#000");
        });

        $('#areaInput').on('keyup', function(){
            selectDoorModle($(this).val(), this);
        });
        $(document).delegate('#ask-form-submit,#mfyy-submit','touchstart', function() {
            
            //百度统计函数
            var ptag = $('.ptag-set').attr('dataptag') || '2_3_1_1';
            try{
                _hmt && _hmt.push(['_trackEvent', 'zb', 'submit', ptag]);
            }catch(e){

            }
        });


        var noScroll=$(".layerout, .fill-msg h5, .fill-msg h6, .fill-msg h3, .fill-msg h1");
        noScroll.on("touchstart",function(e){
            e.preventDefault();
            document.body.addEventListener('click',function(){

            },false)
        });
        $('.ptag-set').on('touchstart', function () {
            var ptag = $(this).attr('dataptag');			 
            (typeof clickStream !== 'undefined') && clickStream.getCvParams(ptag);    //埋点
        });

        var areaTime = null;
        $('#areaInput').on('change',function(){
            if (!areaTime) {
                clearTimeout(areaTime);
            };
            if (Number($(this).val()) < 30) {
                areaTime = setTimeout(function(){
                    $('.oarea-remind').show()
                },300)
            } else {
                 $('.oarea-remind').hide();
            };
        })
    }

    function ruku(phone)
    {
        var _formdata=checkForm();        
        if(hasck || _formdata)
        {
            if(doubleSubmit){
                return;
            }
            doubleSubmit = true;
            ask_dialog_send(phone);            
        }
        
    }
    function ask_dialog_send(phone){
            var ptag = $('.ptag-set').attr('dataptag') || '2_3_1_1';           
            var data = formToJSON($('#froms'));
            data.type = 'detail';
			data.phone ? data.phone = '' : '';
            data.nowstep = 1;
            data.modeltype = 2;
            data.shen = $('#slide_area').val().split(' ')[0];
            data.city = $('#slide_area').val().split(' ')[1];
            data.square = $('#areaInput').val();
            data.shi = $('#secshi').val();
            data.ting = $('#secting').val();//通过cookie判读这个电话在这个页面只入库一次
            data.chu = $('#secchu').val();
            data.yangtai = $('#secyangtai').val();
            data.ptag = ptag;
            rsaNewEncryptPhoneNameGuid(phone, data);
            data.pro_sourceid = 2;
            data = Components.setAPPCode(data);
            data = Components.encryptPhone(data);
            $.ajax({
                type: "GET",
                url: 'http://to8tozb.to8to.com/zb/zb.php',
                dataType : "jsonp",//数据类型为jsonp
                jsonpCallback: "jsonpCallback",//服务端用于接收callback调用的function名的参数
                data: data,
                success: function (data) {
                //若选择深圳则设微信小号cookie换banner，否则按照原来的规则
                   var city = $('#slide_area').val().split(' ')[1];
                   if (city =='深圳') {
                        setCookie('already_fabiao_dongguan',null);
                        setCookie('already_fabiao_shenzhen','true',15);
                        //若为深圳则隐藏结果态更换为微信小号结果态
                        $('.zxd-form').css('display','none');
                        /*$('#fabiaoqu-ask').hide();*/
                        $('#banner-wx-new-init').hide();
                        $('.whan-ad').height(53);
                        $('.whan-ad-ask .fabiaoqu-ask').height(334);
                        $('.new-result-content-service-name').text('深圳装修顾问-馨馨');
                        $('#new-result-wx-num').text(13302974452);
                        $('#new-result').show();                        
                   }else if (city =='东莞') {
                        setCookie('already_fabiao_shenzhen',null);
                        setCookie('already_fabiao_dongguan','true',15);
                        //若为东莞则隐藏结果态更换为微信小号结果态
                        $('.zxd-form').css('display','none');
                        /*$('#fabiaoqu-ask').hide();*/
                        $('#banner-wx-new-init').hide();
                        $('.whan-ad').height(53);
                        $('.whan-ad-ask .fabiaoqu-ask').height(334);
                        $('.new-result-content-service-name').text('东莞装修顾问-蓉蓉');
                        $('#new-result-wx-num').text(13302974661);
                        $('#new-result').show();
                   }else {
                        //移除深圳城市发标的cookie
                        setCookie('already_fabiao_shenzhen',null);
                        setCookie('already_fabiao_dongguan',null);
                        setCookie('already_fabiao','true',15);
                        ask_fzprice(data.priceInfo,data.res,data.banbaoPrice,data.other);
                   }

                   $('#newPhone').val('');                   
                   //大通栏登记后 5天内在全站不自动上滑
                   setCookie('h5_register_c','true',5);
                   //底部通栏登记后 存一个cookie换banner
                   
                   doubleSubmit = false;
                }
            });
    }
	function rsaNewEncryptPhoneNameGuid (phone, obj) {
    	var rsadata = RSAUtilszb.encryptfun(phone + ',' + (Math.ceil(Math.random() * 10)) + ',' + Math.random())
    	obj.rsadata = encodeURIComponent(rsadata);
    	obj.phoneurlencode = 1;
    	obj.rsastatus = 1;
    	obj.uuid = createGuid();
    	obj.enc = $.md5(obj.uuid + phone);
	}    
	function createGuid () {
        for (var a = "", c = 1; 32 >= c; c++) {
            var b = Math.floor(16 * Math.random()).toString(16),
                a = a + b;
            if (8 == c || 12 == c || 16 == c || 20 == c) a += ""
        }
        return this.guid = a += Math.ceil(1E6 * Math.random())
    }
    function ask_fzprice(data,homeMsg,banbao_price,other){
            var shi_all_price = 0;
            var ting_all_price = 0;
            var chu_all_price = 0;
            var wei_all_price = 0;
            var yangtai_all_price = 0;
            var rest_all_price = 0;
            for(var i = 0; i< homeMsg.length; i++) {
                if(homeMsg[i].key=='shi_arr[]')
                {
                    shi_all_price += data[i].price;
                }

                if(homeMsg[i].key=='ting_arr[]')
                {
                    ting_all_price += data[i].price;
                }
                if(homeMsg[i].key=='chu_arr[]')
                {
                    chu_all_price += data[i].price;
                }
                if(homeMsg[i].key=='wei_arr[]')
                {
                    wei_all_price += data[i].price;
                }
                if(homeMsg[i].key=='yangtai_arr[]')
                {
                    yangtai_all_price += data[i].price;
                }

            }
            var str = '<div class="dialog-ask-box">'
                        +'<div class="dialog-ask-fruit">'
                        +'<div class="ask-fruit-title">'
                        +'<div class="dashed-box-left"></div>'
                        +'<p class="fruit-title-p">'
                        +'<b class="dashed-b-left"></b>'
                        +'<span>毛坯房半包装修预估价</span>'
                        +'<b class="dashed-b-right"></b>'
                        +'</p>'
                        +'<div class="dashed-box-right"></div>'
                        +'</div>'
                        +'<div class="ask-fruit-data">'
                        +'<p><em>'+((banbao_price)/10000).toFixed(1)+'</em>万元</p>'
                        +'<ul class="clearfix">'
                        +'<li><span>卧室</span><em>'+shi_all_price+'元</em></li>'
                        +'<li><span>客厅</span><em>'+ting_all_price+'元</em></li>'
                        +'<li><span>厨房</span><em>'+chu_all_price+'元</em></li>'
                        +'<li><span>阳台</span><em>'+wei_all_price+'元</em></li>'
                        +'<li><span>卫生间</span><em>'+yangtai_all_price+'元</em></li>'
                        +'<li><span>其他</span><em>'+(banbao_price-shi_all_price-ting_all_price-chu_all_price-wei_all_price-yangtai_all_price)+'元</em></li>'
                        +'</ul>'
                        +'</div>'
                        +'<div class="ask-fruit-cue">'
                        +'<p><span>*</span>因材料品牌及工程量不同,具体报价以量房实测为准。</p>'
                        +'<p><span>*</span>稍后装修管家将致电您，为您提供免费装修资询服务。</p>'
                        +'</div>'
                        +'<input id="ask-submit-again" class="submit-again" type="button" value="重新计算">'
                        +'</div>'
                        +'</div>';
            $('.zxd-form').css('display','none');
            $('.dialog-ask-box').remove();//防止意外重复提交出现多个报价页            
            $('.fabiaoqu-ask').append(str);
            var mark = false;
            $("#ask-submit-again").on('touchmove', function(e){
                mark = true;
            });
            $("#ask-submit-again").on('touchend',function(e)
            {   
                $(this).removeAttr('style');
                //排除滑动触发
                if(mark){
                    mark = false;
                    return;
                }
                e.preventDefault();
                $('.dialog-ask-box').remove();
                $('.zxd-form').css('display','block');
            });
        
           
    }
           
    function checkForm() {
        var cobj=$("#slide_area").val().split(" ");
        if($('#phone2').val())
            phoneId='phone2';
        var oarea = $('#areaInput').val(),
            phone = $('#newPhone').val() || '',
            shen = cobj[0] ||'',
            fang = $('#secshi').val(),
            ting = $('#secting').val(),
            chu = $('#secchu').val(),
            wei = $('#secwei').val(),
            yangtai = $('#secyangtai').val(),
            city = cobj[1] ||'',
            newPhone = $('#newPhone').val();
        $('#city').val(city);
        $('#shen').val(shen);

        if ($('#slide_area').val()=='请选择您的所在城市' || $('#slide_area').length <= 0 || city == '' || shen == '') {
            msgalert1('请选择所在地区');
            return;
        }
        if ($('[name=oarea]').length && (!oarea || oarea < 0 || (parseFloat(oarea) + "") != (oarea + ''))) {
            msgalert1('请您填写真实的房屋面积');
            return;
        }
        if ((oarea < 5 && oarea >= 0) || oarea > 1000) {
            msgalert1("房屋面积必须在5-1000范围");
            return;
        }
        var telRegexp = /^(1[3|4|5|7|8])[\d]{9}$/;
        if (!phone) {
            msgalert1('请输入手机号码');
            return;
        }
        if (!telRegexp.test(phone)) {
            msgalert1('请输入正确手机号码');
            return;
        }       
        return true;
    }
    function initSelec(id,name)
    {
        var option='';
        for(var i=1;i<=6;i++)
        {
            option+='<option value="'+i+'">'+i+'&nbsp;'+name+'</option>';
        }
        $('#'+id).html(option);
    }

    function checkPhone(phone)
    {
        
        return true;
    }

    //根据面积显示户型
    function selectDoorModle(square, squareEle){
        var square = Number(square);
        if (square + '' == 'NaN' || $(squareEle).val() == '') {
            return
        }
        if (90>square && square >= 60) {
            $('#secshi').val(2);
            $('#secting').val(1);
            $('#secchu').val(1);
            $('#secwei').val(1);
            $('#secyangtai').val(1);
        } else if (150>square&& square >= 90) {
            $('#secshi').val(3);
            $('#secting').val(2);
            $('#secchu').val(1);
            $('#secwei').val(2);
            $('#secyangtai').val(1);
        }
        else if (150<=square ) {
            $('#secshi').val(4);
            $('#secting').val(2);
            $('#secchu').val(1);
            $('#secwei').val(2);
            $('#secyangtai').val(2);
        }
        else
        {
            $('#secshi').val(1);
            $('#secting').val(1);
            $('#secchu').val(1);
            $('#secwei').val(1);
            $('#secyangtai').val(1);
        }
    }
    //格式转换
    function formToJSON(formEle) {
        var data = formEle.serializeArray();
        var dataObj = {};
        for (var i in data) {
            dataObj[data[i].name] = data[i].value;
        }
        return dataObj;
    }
    that.zxbjObj = zxbjObj;
})(jQuery,this);

var valo = $("#slide_area").attr("areaid");

// $('#area').scroller('destroy').scroller(
//     {
//         preset: 'area',
//         theme: 'android-ics light',
//         display: 'bottom',
//         valueo: valo
//     });
jQuery.fn.buriedPoint = function () {
    var urlArr = location.href.split('#'),
        point = '';
    for (var i in urlArr) {
        var temp = urlArr[i].split('=');
        if (temp && 'point' == temp[0] && temp[1]) {
            point = temp[1];
            break;
        }
    }
    this.each(function (i, dom) {
        if ('' != point) {
            dom.value = point;
        }
    });
    return this;
};

function addClientData(data) {
    var liDOM = '';
    for (var i = 0; i < data.length; i++) {
        liDOM += '<li class="swiper-slide"><div><span>'+ data[i].cityname +'</span><span>'+ data[i].area +'㎡</span><span>'+ data[i].tel +'</span><span>'+ data[i].time +'前</span></div></li>'
    }
    return liDOM;
}
