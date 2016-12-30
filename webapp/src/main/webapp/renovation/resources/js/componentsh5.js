/*
 *
 * h5公共方法
 * 依赖jquery 或者 zepto
 */
var _script = document.createElement('script');
var _scriptMd5 = document.createElement('script');
_script.src = "http://static.to8to.com/gb_js/to8torsaszb.js";
_script.type = 'text/javascript';
_scriptMd5.type = 'text/javascript';
_scriptMd5.src = "http://static.to8to.com/gb_js/jQuery.md5.js";
document.getElementsByTagName('head')[0].appendChild(_script);
document.getElementsByTagName('head')[0].appendChild(_scriptMd5);
(function(window, $) {
    window.Components = window.Components || {};
    /**
     * url search解析成object
     * @param url 要解析的url
     * @returns {{}} 返回的query对象，kv格式
     */
    Components.parseQuery = function(url) {
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
    };

    /**
     * 设置cookie的值
     * @param name 设置的cookie名  value cookie名的值 expire cookie有效期限
     */
    Components.setCookie = function(name, value, expire) {
        if (!expire) {
            expire = 5000;
        };

        var expiry = new Date();
        expiry.setTime(expiry.getTime() + expire);
        document.cookie = name + '=' + value + ';expires=' + expiry.toGMTString() + ';path=/;domain=.to8to.com';
    };

    /**
     * 获取cookie的值
     * @param name 要获取的cookie值
     * return 返回查询cookie名的值
     */
    Components.getCookie = function(c_name) {
        var c_value = document.cookie;
        var c_start = c_value.indexOf(" " + c_name + "=");
        if (c_start == -1) {
            c_start = c_value.indexOf(c_name + "=");
        }
        if (c_start == -1) {
            c_value = null;
        } else {
            c_start = c_value.indexOf("=", c_start) + 1;
            var c_end = c_value.indexOf(";", c_start);
            if (c_end == -1) {
                c_end = c_value.length;
            }
            c_value = decodeURIComponent(c_value.substring(c_start, c_end));
        }
        return c_value;
    };

    /**
     * 判断手机类型
     * return 手机机型监测情况
     */
    Components.getPhoneType = function() {
        //正则,忽略大小写
        var pattern_phone = new RegExp("iphone", "i");
        var pattern_android = new RegExp("android", "i");
        var userAgent = navigator.userAgent.toLowerCase();
        var isAndroid = pattern_android.test(userAgent);
        var isIphone = pattern_phone.test(userAgent);
        var res = {
            'iphone': false,
            'iphone3g': false,
            'iphone4': false,
            'iphone5': false,
            'iphone6': false,
            'iphone6p': false,
            'android': false,
            'unknown': false
        };

        if (isAndroid) {
            var zh_cnIndex = userAgent.indexOf("-");
            var spaceIndex = userAgent.indexOf("build", zh_cnIndex + 4);
            var fullResult = userAgent.substring(zh_cnIndex, spaceIndex);
            res.android = true;
        } else if (isIphone) {
            //6   w=375    6plus w=414   5s w=320     5 w=320
            var _window = parent.window;
            var wigth = _window.innerWidth || _window.document.documentElement.clientWidth || _window.document.body.clientWidth;
            var height = _window.innerHeight || _window.document.documentElement.clientHeight || _window.document.body.clientHeight;
            res.iphone = true;
            if (wigth > 400) {
                res.iphone6p = true;
            } else if (wigth > 370) {
                res.iphone6 = true;
            } else if (wigth > 315) {
                if (height > 500) {
                    res.iphone5 = true;
                } else {
                    res.iphone4 = true;
                }
            } else {
                res.iphone3g = true;
            }
        } else {
            res.unknown = true;
        }

        return res;
    };

    /**
     * 判断城市站是否落地城市
     * @param city 要验证的城市
     * return true or false  true表示落地城市
     */
    Components.isGroundCity = function(city) {
        if (!city) {
            return false
        };

        var cityType = isNaN(Number(city));
        //to8to_townid
        var CityIdArr = [1130, 1121, 1672, 1181, 2827, 1103, 591, 2920, 1695, 2929, 1547, 1196, 1681, 762, 118, 1, 2298, 317, 2013, 612, 1891, 2912, 2933, 2951, 2962, 2619, 974];
        //to8to_tname
        var CityNameArr = ['北京', '上海', '广州', '深圳', '东莞', '武汉', '长沙', '南京', '杭州', '厦门', '福州', '苏州', '无锡', '合肥', '成都', '昆明', '天津', '郑州', '西安', '南宁', '贵阳', '沈阳', '大连', '昆山', '常州', '佛山', '重庆']

        var GroundCityArr = [];
        if (!cityType) {
            GroundCityArr = CityIdArr;
        } else {
            GroundCityArr = CityNameArr;
        };

        var Len = GroundCityArr.length;
        for (var i = 0; i < Len; i++) {
            if (city == GroundCityArr[i]) {
                return true;
            };
        };
        return false;
    };

    /**
     * 微信分享配置
     * @param  settings
     */
    Components.shareWechat = function(options) {
        var settings = $.extend({
            titleConfig: '',
            titleQuan: '',
            descConfig: '',
            url: '',
            imgUrl: ''
        }, options);
        Components.loadScript('https://res.wx.qq.com/open/js/jweixin-1.0.0.js', function() {
            Components.loadScript('http://m.to8to.com/js/wx.js?20150210', function() {
                /*********** 微信分享 ************/
                try {
                    var shareConfig = {
                        title: settings.titleConfig, //分享标题
                        desc: settings.descConfig, //分享文案
                        link: settings.url, // 分享链接
                        imgUrl: settings.imgUrl // 分享图标
                    };
                    var shareQuan = {
                        title: settings.titleQuan, //分享朋友圈文案
                        link: settings.url, // 分享链接
                        imgUrl: settings.imgUrl // 分享图标
                    };
                    to8toWx && to8toWx.init({
                        onReady: function() {
                            // 分享到朋友圈
                            to8toWx.exec('onMenuShareTimeline', shareQuan);

                            // 发送给朋友
                            to8toWx.exec('onMenuShareAppMessage', shareConfig);

                            // 分享到QQ
                            to8toWx.exec('onMenuShareQQ', shareConfig);
                        }
                    });
                } catch (e) {}
                /*********** 微信分享 ************/
            })
        })
    };

    /**
     * 移动端简易弹窗
     * @param
     */
    Components.simplepop = function() {
        var $aol = $('<div id="alertOveryLayer" style="position: fixed; left: 0px; top: 0px; z-index: 99;zoom:1;text-align: center;  bottom: 0px; right: 0px; background-color: rgba(0, 0, 0, 0.298039);"><div style="  position: absolute;z-index: 999; display: -moz-box;  display: -webkit-box;  display: -webkit-flex;  display: -moz-flex;  display: -ms-flexbox;  display: -ms-flex;  display: flex;top: 30%;width: 100%;"><div class="alert-lay" style="min-width: 320px; max-width: 640px; margin: 0 auto; vertical-align: middle;padding: 0 10%;-webkit-box-sizing: border-box;  -moz-box-sizing: border-box;  box-sizing: border-box;  display: block;  -webkit-box-flex: 1;  -moz-box-flex: 1;  -webkit-flex: 1 1 0;  -moz-flex: 1 1 0;  -ms-flex: 1 1 0;  flex: 1 1 0;  text-align: center;"><div class="overlay-bd" style="background-color: #fff;font-size: 14px;color: #333333;line-height: 24px;padding: 20px 15px;text-align: center;border-radius: 7px 7px 0 0;">asdf</div><div class="overlay-foot" style="border-top: 1px solid #e3e3e3;"><div class="overlay-btn overlay-btn-ok" style="background-color: #fff;cursor: pointer;font-size: 16px;color: #00a1ff;height: 42px;line-height: 42px;border-radius: 0 0 7px 7px;">好的</div></div></div></div></div>');
        var cb = null;
        var sid;
        $aol.appendTo(top.document.body);
        $aol.hide();
        $aol.find(".overlay-btn-ok").on('touchend', function(event) {
            $aol.hide();
            typeof cb == 'function' && cb();
            cb = null;
            clearInterval(sid);
        });
        $aol.on('touchstart', function(event) {
            event.preventDefault();
        });
        return function(msg, cbk) {
            $aol.find(".overlay-bd").html(msg);
            $aol.show();
            cb = cbk;
            sid = setInterval(function() {
                //document.body.scrollIntoView();
            }, 50);
        };
    };

    /**
     * 设置h5城市站cookie值
     *
     */
    Components.InitCityCookie = function() {
        var url = "http://m.to8to.com/index/Citycookie/city/" + "?" + Math.random();
        $.get(url);
    }

    /**
     * APP根据token解析城市站信息
     *
     */
    Components.getAPPCity = function(options) {
        /*
         * options.addressEle 赋值选项
         * options.callback  回调
         */
        var urlData = this.parseQuery(),
            token;
        if (urlData['token'] !== undefined || urlData['to8to_token'] !== undefined) {
            token = urlData['token'] || urlData['to8to_token'];
            $.ajax({
                type: 'get',
                url: 'http://m.to8to.com/index.php/open/getcity',
                data: token,
                success: function(res) {
                    if (res.statu == 1) {
                        if (options.addressEle && $(options.addressEle).length >= 1) {
                            $(options.addressEle).val(res.shen + ' ' + res.city);
                        };
                    }
                    options.callback && options.callback(res);
                }
            })
        };
    }

    /**
     * 点击流与百度统计
     *
     */
    Components.ClickCount = function() {
        var tapFlag = false;
        $(document).on('touchstart', '.public-tap-stream', function() {
            tapFlag = false;
        })
        $(document).on('touchmove', '.public-tap-stream', function() {
            tapFlag = true;
        })
        $(document).on("touchend", '.public-tap-stream', function(event) {
            var ptag = $(this).data('ptag');

            if (!tapFlag) {
                var clickPhoneTimes = localStorage[ptag];
                if (!clickPhoneTimes) {
                    localStorage[ptag] = ptag;
                    try { _hmt && _hmt.push(['_trackEvent', 'zb', 'submit', ptag]); } catch (e) {}
                    (typeof clickStream !== 'undefined') && clickStream.getCvParams(ptag);
                }
            };
            tapFlag = false;
        });
    }

    /**
     * 获取表单数据转成对象kv值
     *
     */
    Components.FormToJSON = function(formName) {
        var data = $(formName).serializeArray();
        var dataObj = {};
        for (var i in data) {
            dataObj[data[i].name] = data[i].value;
        }
        if (dataObj.t8taddress) {
            var addr = dataObj.t8taddress.split(' ');
            dataObj.shen = addr[0];
            dataObj.city = addr[1];
            delete dataObj.t8taddress;
        };
        if (dataObj.phone) {
            dataObj.uuid = this.CreateGuid();
            dataObj.enc = $.md5(dataObj.uuid + dataObj.phone);
        }
        return dataObj;
    }

    /*
     */
    Components.CreateGuid = function() {
        for (var a = "", c = 1; 32 >= c; c++) {
            var b = Math.floor(16 * Math.random()).toString(16),
                a = a + b;
            if (8 == c || 12 == c || 16 == c || 20 == c) a += ""
        }
        return this.guid = a += Math.ceil(1E6 * Math.random())
    }


    var simplePop = Components.simplepop();
    /*
     * 验证H5普通招标
     */
    Components.CheckNormalTender = function(FormName) {
        if (this.repeatFlag) {
            return false
        };
        var phone = $(FormName).find('.t8t-phone').val(),
            chenghu = $(FormName).find('.t8t-name').val(),
            address = $(FormName).find('.t8t-address').val();

        if (chenghu == '' || chenghu == undefined) {
            simplePop('请输入您的称呼！');
            return;
        }
        if (phone == '' || phone == undefined) {
            simplePop('请输入您的手机号码！');
            return;
        };
        var telRegexp = /^(1[3|4|5|7|8])[\d]{9}$/;
        if (!telRegexp.test(phone)) {
            simplePop('请输入正确的手机号码!');
            return;
        }
        if (address == '' || address == undefined) {
            simplePop('请选择您的城市！');
            return;
        }
        return true;
    }

    /*
     * M站普通招标结果态
     */
    Components.repeatFlag = false;
    Components.ResultNormalTender = function(FormName, callback) {
        var sendData = this.FormToJSON(FormName),
            self = this;
        sendData.modeltype = 6;

        //合并表单字段和来源统计字段并
        Components.setAPPCode(sendData);
        //rsa加密电话删除phone字段
        Components.encryptPhone(sendData);

        if (self.repeatFlag) {
            return false
        };
        //防重复提交
        self.repeatFlag = true;
        $.ajax({
            type: "get",
            url: 'http://to8tozb.to8to.com/zb/zb-index-get.php',
            dataType: "jsonp",
            jsonpCallback: "jsonpCallback",
            data: sendData,
            success: function(res) {
                self.repeatFlag = false;
                if (res.status == 3) {
                    simplePop('您的手机号码已重复申请超过5次')
                } else {
                    callback.call(self, res);
                }
            }
        })
    }

    /*
     * 设置隐藏的form字段
     */
    Components.setAPPCode = function(data, url) {
        /*
         * url 修改的地址
         * data  传输的数据
         */
        var url = url || window.location.href;
        var urlObj = this.parseQuery(url),
            appObj = {};

        var itemArr = [
            'ptag', // 发标标识
            'uid', // 用户ID
            'idfa', //  IOS设备标识ID
            'imei', // android设备标识ID
            'channel', //APP下载渠道 优先级高于to8to_from
            'to8to_from', //推广来源
            'device_src', //设备来源
            'pro_sourceid', //产品来源
            'pro_s_sourceid', //产品来源小分类
            'fromapp', // 发标页面来自APP
            'fromnewapp', // 发标页面来自APP
            'to8to_tgid', //添加人工号
            'gonghao', //添加人工号
            'landpage', // 用户进入土巴兔的第一个页面
            'subpage', //发标页url
            'sourcepage', //来源标识
            'openid', //微信用户ID
            'sourceid', //项目登记来源
            'pagetype', //项目登记详细来源
            'appversion',
            'appid'
        ];

        if ($.isEmptyObject(urlObj)) return;
        //支持隐藏域以及返回k v格式字段
        for (var i in urlObj) {
            if (this.inArray(itemArr, i)) {
                if (i == 'imei') {
                    appObj.idfa = urlObj[i];
                    $('input:hidden[name="idfa"]').length && $('input:hidden[name="idfa"]').val(urlObj[i]);
                } else if (i == 'uid') {
                    appObj.fromid = urlObj[i];
                    $('input:hidden[name="fromid"]').length && $('input:hidden[name="fromid"]').val(urlObj[i]);
                } else if (i == 'channel') {
                    appObj.to8to_from = urlObj[i];
                    $('input:hidden[name="to8to_from"]').length && $('input:hidden[name="to8to_from"]').val(urlObj[i]);
                } else if (i == 'to8to_from') {
                    if (!appObj.to8to_from) {
                        appObj.to8to_from = urlObj[i];
                        $('input:hidden[name="to8to_from"]').length && $('input:hidden[name="to8to_from"]').val(urlObj[i]);
                    };
                } else if (i == 'fromapp' || i == 'appid' || i == 'appversion' || i == 'fromnewapp') {
                    appObj.fromapp = 1;
                    $('input:hidden[name="fromapp"]').length && $('input:hidden[name="fromapp"]').val(1);
                } else if (i == 'to8to_tgid' || i == 'gonghao') {
                    appObj.gonghao = urlObj[i];
                    $('input:hidden[name="gonghao"]').length && $('input:hidden[name="gonghao"]').val(urlObj[i]);
                } else {
                    appObj[i] = urlObj[i]
                    $('input:hidden[name="' + i + '"]').length && $('input:hidden[name="' + i + '"]').val(urlObj[i]);
                };
            };
        }
        if (Components.isObject(data)) {
            appObj = $.extend(data, appObj);
        };
        return appObj;
    };


    //电话号码加密
    Components.encryptPhone = function(sendData) {
        var reg = /^1{1}[34578]{1}\d{9}$/,
            isPhone = sendData.phone ? reg.test(sendData.phone) : false;

        if (isPhone) {
            //新版加密添加当前标识
            sendData.rsadata = RSAUtilszb.encryptfun(sendData.phone + ',' + (Math.ceil(Math.random() * 10)) + ',' + Math.random())
            sendData.rsadata = encodeURIComponent(sendData.rsadata);
            sendData.rsastatus = 1;
            sendData.phoneurlencode = 1;
            sendData.phone = '';
        }
        return sendData;
    }

    //判断是否在数组
    Components.inArray = function(arr, item) {
            var arrLen = arr.length || 0;
            var _flag = false;
            if (arrLen <= 0) {
                return false
            };

            for (var i = 0; i < arrLen; i++) {
                if (item == arr[i]) {
                    _flag = true;
                    break;
                };
            }

            return _flag;
        }
        //判断是否是函数
    Components.isFunction = function(value) {
            return typeof value === 'function';
        }
        //JS动态加载JS文件
    Components.loadScript = function(src, callback) {
            var container = container || document.body;
            var _script = document.createElement('script');
            _script.src = src;
            _script.onload = function(e) {
                Components.isFunction(callback) && callback.call(_script, e);
            };
            _script.onerror = function(e) {
                Components.isFunction(callback) && callback.call(_script, e);
            }
            container.appendChild(_script);
        }
        /**
         * 参数是否是对象
         */
    Components.isObject = function(value) {
        return Object.prototype.toString.call(value) == '[object Object]';
    }
    // 下载app方法 参数为安卓渠道号，可选，没有则下载默认渠道号:to8toapp
    Components.loadAPP = function(apkname){
        if(typeof(apkname) == 'undefined'){
           var apkname = 'to8toapp';
        }
        var browser = {
            versions: function() {
                var u = navigator.userAgent, app = navigator.appVersion;
                return {         //移动终端浏览器版本信息
                    trident: u.indexOf('Trident') > -1, //IE内核
                    presto: u.indexOf('Presto') > -1, //opera内核
                    webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                    mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                    android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
                    iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
                    iPad: u.indexOf('iPad') > -1, //是否iPad
                    webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
                };
            }()
        }
        var ua = window.navigator.userAgent.toLowerCase();
        // 获取schema信息
        // iphone 机制
        if (browser.versions.ios || browser.versions.iPhone) {
            if(ua.match(/MicroMessenger/i) == 'micromessenger'){
            } else {
                testApp('to8to.t8tHouseKeeper://');
            }
        }
        function testApp(url){
            var timeout, t = 1000, hasApp = true;
            var iosapp = setTimeout(function () {
                if (hasApp) {
                } else {
                    window.location.href = 'http://um0.cn/2ZID96/';    // 在AppStore中打开链接
                }
                document.body.removeChild(ifr);
            }, 2000);
            if(document.visibilityState !== 'visible'){
                clearTimeout(iosapp);
            }
            if(document.hidden == true){
                clearTimeout(iosapp);
            }
            window.onblur = function() {
                clearTimeout(iosapp);
            }
            var t1 = Date.now();
            var ifr = document.createElement("iframe");
            ifr.setAttribute('src', url);
            ifr.setAttribute('style', 'display:none');
            document.body.appendChild(ifr);
            timeout = setTimeout(function () {
                var t2 = Date.now();
                if (!t1 || t2 - t1 < t + 100) {
                    hasApp = false;
                }
            }, t);
        }
        // 安卓下载机制
        if ( browser.versions.android) {
            if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                // popWinBox($('.weixinopen').clone(true));
            } else {
                var config = {
                    /*scheme:必须*/
                    scheme_IOS: 'to8to.t8tHouseKeeper://',
                    scheme_Adr: 'to8to://splash',
                    timeout: 700
                };
                var startTime = Date.now();
                window.location.href = 'to8to://splash';
                var t = setTimeout(function() {
                    var endTime = Date.now();
                    if (!startTime || endTime - startTime < config.timeout + 200) {
                        // 安卓下载包链接，或需要动态获取
                        // window.location.href = name;
                        var sendlastversion = {
                            model: 'Update',
                            action: 'Lastversion',
                            channelName: apkname,
                            apkPackageName: 'com.to8to.housekeeper'
                        };
                        $.ajax({
                            type: 'GET',
                            url: 'http://mobileapi.to8to.com/smallapp.php',
                            dataType: 'jsonp',
                            data: sendlastversion,
                            jsonpCallback: "jsonpCallback",
                            success: function (res) {
                                androidurl = res.apkDownloadUrl;
                                if(androidurl){
                                    window.location.href = androidurl;
                                }else{
                                    window.location.href = 'http://pic.to8to.com/app/android/to8to/20161018_e61188c21265c5f737c6GGcakiJ0WYT3.apk';
                                }
                            },
                            error: function(res) {
                                window.location.href = 'http://pic.to8to.com/app/android/to8to/20161018_e61188c21265c5f737c6GGcakiJ0WYT3.apk';
                            }
                        })
                    } else {
                    }
                }, config.timeout);
                if(document.visibilityState !== 'visible'){
                    clearTimeout(t);
                }
                if(document.hidden == true){
                    clearTimeout(t);
                }
                window.onblur = function() {
                    clearTimeout(t);
                }
            }
        }
        if(!(browser.versions.ios || browser.versions.iPhone || browser.versions.android)){
			 // 无效下载信息
            alert('仅支持iPhone和Android设备下载哦，如以上设备不能下载可重装浏览器试试~');
        }
        // 跳转和判断超时
        return false;
    }

})(window, window.$ || window.zepto || window.jQuery);