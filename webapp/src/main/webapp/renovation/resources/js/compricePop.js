(function(window){
	window.alertForm = {
		init: function(option){
			var self = this;
			self.config = $.extend({
				title: '我家装修要花多少钱？',//表单title
				formParameter: [{name:'dangci',value:'jianzhuang'},{name:'ptag',value:''}],//表单隐藏域
				alertFormClass: 't8s-alert-form',
				provinceSelect: 't8s_provinceSelect',//城市选择
				housesArea: 't8s_areaInput',//房屋面积
				bedroom: 't8s_secshi',//卧室
				parlor: 't8s_secting',//客厅
				kitchen: 't8s_secchu',//厨房
				bathroom: 't8s_secwei',//卫生间
				balcony: 't8s_secyangtai',//阳台
				phone: 't8s_phone',//手机号码
				submitClass: 't8s-submit',//提交按钮样式
				submitPtag: '2_1_1_743',//提交按钮ptag
				clickStream: 'publick-tap-stream',//按钮上是否触发埋点
				event: null,//绑定函数
				t8s_send: null,//发送ajax
				t8s_validate: null,//表单校验
				closeBox: null//关闭弹框
			}, option);
			if(typeof $.md5 != 'function'){
				var _scriptMd5 = document.createElement('script');
				_scriptMd5.type = 'text/javascript';
				_scriptMd5.src = "http://static.to8to.com/gb_js/jQuery.md5.js";
				document.getElementsByTagName('head')[0].appendChild(_scriptMd5);
			}
            if(typeof RSAUtilszb == "undefined"){
                var elemetRSA = document.createElement('script');
                elemetRSA.type = 'text/javascript';
                elemetRSA.src = "http://static.to8to.com/gb_js/to8torsaszb.js";
                document.getElementsByTagName('head')[0].appendChild(elemetRSA);
            }
			self.createDom();
			self.event();
		},
		createDom: function(){
			var self = this;
			var input = '';
			for (var i = self.config.formParameter.length - 1; i >= 0; i--) {
				input += '<input type="hidden" name="'+self.config.formParameter[i].name+'" value="'+self.config.formParameter[i].value+'">';
			};
			var str = '<div class="t8s-alert-title" style="width: 100%;">'
                        +'<span style="color: #ff7d26;font-size: 22px;text-align: center;">'+self.config.title+'</span>'
                    +'</div>'
                    +'<div class="t8s-form-box">'
                    +'<from class="'+self.config.alertFormClass+' mfsj-from ">'
                        +'<div class="t8s-list-box">'
                            +'<div class="row mfsj-location-row clearfix">'
                                + input
                                +'<div class="t8s-provinces"><input class="t8s-provinces-input" id="'+self.config.provinceSelect+'" type="text" readonly="readonly" placeholder="请选择房屋所在省市"></div>'
                            +'</div>'
                        +'</div>'
                        +'<div class="t8s-list-box">'    
                            +'<div class="t8s-area clearfix">'
                                +'<input class="t8s-areas-input" id="'+self.config.housesArea+'" name="oarea" type="tel" placeholder="请输入您的房屋面积">'
                                +'<span>㎡</span>'
                            +'</div>'
                        +'</div>'
                        +'<div class="t8s-list-box">'     
                            +'<div class="t8s-jiting row mfsj-location-row clearfix" id="t8s-jiting">'
                                +'<div class="t8s-jiting-box">'
                                    +'<div class="t8s-shi">'
                                        +'<div class="t8s-shi-wrap">'
                                            +'<select name="" id="'+self.config.bedroom+'" class="needsclick"><option value="1">1&nbsp;室</option><option value="2">2&nbsp;室</option><option value="3">3&nbsp;室</option><option value="4">4&nbsp;室</option><option value="5">5&nbsp;室</option><option value="6">6&nbsp;室</option></select>'
                                        +'</div>'
                                    +'</div>'
                                    +'<div class="t8s-ting">'
                                        +'<div class="t8s-ting-wrap">'
                                            +'<select name="" id="'+self.config.parlor+'" class="needsclick"><option value="1">1&nbsp;厅</option><option value="2">2&nbsp;厅</option><option value="3">3&nbsp;厅</option><option value="4">4&nbsp;厅</option><option value="5">5&nbsp;厅</option><option value="6">6&nbsp;厅</option></select>'
                                        +'</div>'
                                    +'</div>'
                                +'</div>'
                                +'<div class="t8s-chuwei-box">'    
                                    +'<div class="t8s-chu">'
                                        +'<div class="t8s-chu-wrap">'
                                            +'<select name="" id="'+self.config.kitchen+'" class="needsclick"><option value="1">1&nbsp;厨</option><option value="2">2&nbsp;厨</option><option value="3">3&nbsp;厨</option><option value="4">4&nbsp;厨</option><option value="5">5&nbsp;厨</option><option value="6">6&nbsp;厨</option></select>'
                                        +'</div>'
                                    +'</div>'
                                    +'<div class="t8s-wei">'
                                        +'<div class="t8s-wei-wrap">'
                                            +'<select name="" id="'+self.config.bathroom+'" class="needsclick"><option value="1">1&nbsp;卫</option><option value="2">2&nbsp;卫</option><option value="3">3&nbsp;卫</option><option value="4">4&nbsp;卫</option><option value="5">5&nbsp;卫</option><option value="6">6&nbsp;卫</option></select>'
                                        +'</div>'
                                    +'</div>'                                                            
                                    +'<div class="t8s-yangtai">'
                                        +'<div class="t8s-yangtai-wrap">'
                                            +'<select name="" id="'+self.config.balcony+'" class="needsclick"><option value="1">1&nbsp;阳台</option><option value="2">2&nbsp;阳台</option><option value="3">3&nbsp;阳台</option><option value="4">4&nbsp;阳台</option><option value="5">5&nbsp;阳台</option><option value="6">6&nbsp;阳台</option></select>'
                                        +'</div>'
                                    +'</div>'
                                +'</div>'
                            +'</div>'
                        +'</div>'
                        +'<div class="t8s-list-box">'     
                            +'<div class="t8s-phone clearfix" style="margin-top: 10px;">'
                                +'<input class="t8s-phone-input" id="'+self.config.phone+'" name="phone" type="tel" placeholder="输入手机号码获取预算明细">'
                            +'</div>'
                        +'</div>'   
                        +'<div class="'+self.config.submitClass+' '+self.config.clickStream+'" data-ptag="'+self.config.submitPtag+'">开始计算</div>'
                    +'</from>'
                    +'</div>';
			var html_code = '<div class="t8s-alert-box" style="position: fixed; left: 0px; top: 0px;   z-index: 200; text-align: center;  bottom: 0px; right: 0px; background-color: rgba(0, 0, 0, 0.298039);">'
                                +'<div class="t8s-box" style="position: relative;display: -moz-box;  display: -webkit-box;  display: -webkit-flex;  display: -moz-flex;  display: -ms-flexbox;  display: -ms-flex;  display: flex;top: 50%;width: 100%;">'
                                    +'<div class="alert-lay" style="/* display: table-cell; */vertical-align: middle;padding: 0 8%;-webkit-box-sizing: border-box;  -moz-box-sizing: border-box;  box-sizing: border-box;  display: block;  -webkit-box-flex: 1;  -moz-box-flex: 1;  -webkit-flex: 1 1 0;  -moz-flex: 1 1 0;  -ms-flex: 1 1 0;  flex: 1 1 0;  text-align: center;">'
                                        +'<div class="overlay-bd" style="background-color: #fff;font-size: 14px;color: #333333;line-height: 24px;padding: 15px 15px 10px 15px;text-align: center;width: 100%;">'
                                            +'<div class="t8s-alert-close" style="width: 100%;height: 18px;position: relative;">'
                                                +'<i class="t8s-alert-i" ></i>'
                                            +'</div>'
                                            +'<div class="t8s-content-box">'
                                            	+str
                                            +'</div>'
                                        +'</div>'
                                    +'</div>'
                                +'</div>'
                            +'</div>'; 
            var dom = $(html_code);
            $(document.body).append(dom);
            var top = ($(window).height() - dom.find('.t8s-box').height()) / 2;
            dom.find('.t8s-box').css('top', top+'px');
		},
		event: function(){
			var self = this;
			if( typeof self.config.event === 'function' ){
				self.config.event();
				return;
			}
			ProvincesSelect.initialize({targetDom: '#' + self.config.provinceSelect});
			//close box
		    $('body').on('touchstart', '.t8s-alert-close i', function(e){
		    	if( typeof self.config.closeBox === 'function' ){
		    		self.config.closeBox(e);
					return;
		    	}
	            e.preventDefault();
		        $('.t8s-alert-box').remove();
		    })
            
		    $('body').on('touchmove', '.t8s-alert-box', function(e){
		        e.preventDefault();
		    });

			self.t8s_flag = false;//防重复提交
		    $('body').on('touchstart', '.' + self.config.submitClass, function(e){
		    	e.preventDefault();
		    	$('input').blur();
		        self.t8s_send();
		    });

		    $('body').on('keyup', '#' + self.config.housesArea, function(){
		        self.selectDoorModle($(this).val(), this);
		    });
		    $('body').on('keyup', '#' + self.config.phone, function(){
		        var phone = $(this).val();
		        if(/^(1[3|4|5|7|8])[\d]{9}$/.test(phone)){
		            $(this).parents('.t8s-list-box').removeClass('t8s-error-div').find('.t8s-error-box').remove();
		        }
		    });
		    $('body').on('change','#'+self.config.formProvince+',#'+self.config.formCity, function(){
		        $(this).css('border','1px solid #e3e3e3').parents('.t8s-list-box').removeClass('t8s-error-div').find('.t8s-error-box').remove();
		    });
		},
		//弹框ajax
		t8s_send: function(){
			var self = this;
			if( typeof self.config.t8s_send === 'function' ){
				self.config.t8s_send();
				return;
			}
	        var _formdata = self.t8s_validate();
	        var data = self.formToJSON($('#quoted_form_bottom'));
	        data.type = 'detail';
	        data.nowstep = 1;
	        data.modeltype = 8;
	        data.shen = _formdata.shen;
	        data.city = _formdata.city;
	        data.square = _formdata.oarea;
	        data.shi = _formdata.fang;
	        data.ting = _formdata.ting;
	        data.chu = _formdata.chu;
	        data.wei = _formdata.wei;
	        data.yangtai = _formdata.yangtai;
	        data.ptag = self.config.submitPtag;
	        data.method = "baojiaZb";
            this.rsaNewEncryptPhoneNameGuid(_formdata.phone, data);
	        if (_formdata && !self.t8s_flag) {
                self.t8s_flag = true;
	            $.ajax({
		            type: "GET",
		            url: 'http://to8tozb.to8to.com/zb/zb.php',
		            dataType : "jsonp",//数据类型为jsonp
		            jsonpCallback: "jsonpCallback",//服务端用于接收callback调用的function名的参数
		            data: data,
		            success: function (data) {
		               self.t8s_newprice(data);
                        self.t8s_flag = false;
		            }
	        	});
	        }
		},
		//数据验证
		t8s_validate: function(){
			var self = this;
	        $('.'+self.config.alertFormClass).find('.t8s-area,.t8s-phone').css('border','1px solid #e3e3e3');
	        $('.'+self.config.alertFormClass).find('select').css('border','1px solid #e3e3e3');
	        $('.'+self.config.alertFormClass).find('.t8s-error-box').remove();
	        $('.'+self.config.alertFormClass).find('.t8s-list-box').removeClass('t8s-error-div');
	        var area = $.trim($('#'+self.config.housesArea).val()),
	            phone = $.trim($('#'+self.config.phone).val()),
	            provinces = $.trim($('#'+self.config.provinceSelect).val()),
	            fang = $('#'+self.config.bedroom).val(),
	            ting = $('#'+self.config.parlor).val(),
	            chu = $('#'+self.config.kitchen).val(),
	            wei = $('#'+self.config.bathroom).val(),
	            yangtai = $('#'+self.config.balcony).val(),
	            sheng,city;

	        if ($('#'+self.config.provinceSelect).length && (!provinces || provinces == '')) {
	            $('#'+self.config.provinceSelect).parents('.t8s-list-box').addClass('t8s-error-div').append('<div class="t8s-error-box"><p class="t8s-error">*请您选择您所在的城市</p>');
	            return false;
	        }else {
	        	var cobj=provinces.split(" ");
	        	sheng = cobj[0];
	        	city = cobj[1];
	        }
	        if ($('#'+self.config.housesArea).length && (!area || area < 0 || (parseFloat(area) + "") != (area + ''))) {
	            $('#'+self.config.housesArea).parents('.t8s-list-box').addClass('t8s-error-div').append('<div class="t8s-error-box"><p class="t8s-error">*请您填写真实的房屋面积</p>');
	            return false;
	        }
	        if ((area < 5 && area >= 0) || area > 1000) {
	            $('#'+self.config.housesArea).parents('.t8s-list-box').addClass('t8s-error-div').append('<div class="t8s-error-box"><p class="t8s-error">*房屋面积必须在5-1000范围</p>');
	            return false;
	        }
	        if(!/^(1[3|4|5|7|8])[\d]{9}$/.test(phone)){
	            $('#'+self.config.phone).parents('.t8s-list-box').addClass('t8s-error-div').append('<div class="t8s-error-box"><p class="t8s-error">*请您填写正确的手机号码!</p>');
	            return false;
	        }
	        return {oarea:area,phone:phone,shen:sheng,fang:fang,ting:ting,chu:chu,wei:wei,yangtai:yangtai,city:city,newPhone:phone};
		},
		//格式转换
	    formToJSON: function(formEle) {
	        var data = formEle.serializeArray();
	        var dataObj = {};
	        for (var i in data) {
	            dataObj[data[i].name] = data[i].value;
	        }
	        return dataObj;
	    },
        //加密参数添加
        rsaNewEncryptPhoneNameGuid: function (phone, obj) {
                var rsadata = RSAUtilszb.encryptfun(phone + ',' + (Math.ceil(Math.random() * 10)) + ',' + Math.random())
                obj.rsadata = encodeURIComponent(rsadata);
                obj.phoneurlencode = 1;
                obj.rsastatus = 1;
                obj.uuid = this.createGuid();
                obj.enc = $.md5(obj.uuid + phone);
       },
    //电话加密
	    createGuid: function() {
	        for (var a = "", c = 1; 32 >= c; c++) {
	            var b = Math.floor(16 * Math.random()).toString(16),
	                a = a + b;
	            if (8 == c || 12 == c || 16 == c || 20 == c) a += ""
	        }
	        return this.guid = a += Math.ceil(1E6 * Math.random())
    	},
	    //报价结果赋值
		t8s_fzprice: function(data,homeMsg,banbao_price){
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
		     var  fruit_html = '<div class="t8s-fruit-price">'+
		     						'<p style="font-size:22px;color: #333;text-align: center;">您的装修预算约'+
			     						'<span style="font-size: 25px; color: #ff0000;padding: 0 10px;">'+(banbao_price/10000).toFixed(1)+'</span>'+
			     						'<i style="font-size: 14px;font-style: normal;">万元</i>'+
			     					'</p>'+
			     				'</div>'+
				                '<div class="t8s-price-list">'+
				                 	'<ul>'+
				                 		'<li><span class="t8s-budget-span">卧 室 预 算：</span><span>'+shi_all_price+'</span>元</li>'+
				                 		'<li><span class="t8s-budget-span">客 厅 预 算：</span><span>'+ting_all_price+'</span>元</li>'+
				                 		'<li><span class="t8s-budget-span">厨 房 预 算：</span><span>'+chu_all_price+'</span>元</li>'+
				                 		'<li><span class="t8s-budget-span">卫生间预算：</span><span>'+wei_all_price+'</span>元</li>'+
				                 		'<li><span class="t8s-budget-span">阳 台 预 算：</span><span>'+yangtai_all_price+'</span>元</li>'+
				                 		'<li><span class="t8s-budget-span">其 它 预 算：</span><span>'+data[homeMsg.length].price+'</span>元</li>'+
				                 	'</ul>'+
				                '</div>'+
				                '<p style="font-size: 12px;color: #33bf82;text-align: left;margin-top: 10px;">*本价格为毛坯房半包估算价格(不含水电报价)，旧房价格由实际工程量决定。</p>'+
				                '<p style="font-size: 12px;color: #33bf82;text-align: left;">*稍后装修管家将致电您，为您提供免费装修咨询服务。</p>';
		    $('.t8s-content-box').html(fruit_html);
		},
		//新版半包报价结果
		t8s_newprice: function(data){

			var fruit_html = '<div class="t8s-fruit-price">'+
	     						'<p style="font-size:20px;color: #333;text-align: center;">毛坯房半包装修预估价：</p>'+
	     						'<p>'+
		     						'<span style="font-size: 25px; color: #ff0000;padding: 0 10px;">'+(data.to8to_totle_price/10000).toFixed(1)+'</span>'+
		     						'<i style="font-size: 14px;font-style: normal;">万元</i>'+
		     					'</p>'+
		     				'</div>'+
			                '<div class="t8s-price-list">'+
			                 	'<ul>'+
			                 		'<li class="clearfix"><span class="t8s-budget-span">材料费：</span><span><em>'+data.to8to_cl_price+'</em>元</span></li>'+
			                 		'<li class="clearfix"><span class="t8s-budget-span">人工费：</span><span><em>'+data.to8to_rg_price+'</em>元</span></li>'+
			                 		'<li class="clearfix"><span class="t8s-budget-span">设计费：</span><span><em>0</em>元<del class="to8to_zj">'+ data.normal_sj_price +'元</del></li>'+
			                 		'<li class="clearfix"><span class="t8s-budget-span">质检费：</span><span><em>0</em>元<del class="to8to_zj">'+ data.normal_zj_price +'元</del></li>'+
			                 	'</ul>'+
			                '</div>'+
			                '<p style="font-size: 12px;color: #fe5f00;text-align: left;margin-top: 10px;">* 稍后装修管家将致电您，为您提供免费装修咨询服务。</p>'+
			                '<p style="font-size: 12px;color: #999;text-align: left;">*因材料品牌及工程量不同，具体报价以量房实测为准。</p>';
		    $('.t8s-content-box').html(fruit_html);
		}, 
		//根据面积显示户型
	    selectDoorModle: function(square, squareEle){
	        var square = Number(square);
	        var self = this;
	        if (square + '' == 'NaN' || $(squareEle).val() == '') {
	            return
	        }
	        if (square >= 5 && square <= 1000) {
	            $(squareEle).parents('.t8s-list-box').removeClass('t8s-error-div').find('.t8s-error-box').remove();   
	        };
	        var shi = $('#'+self.config.bedroom),
		        ting = $('#'+self.config.parlor),
		        chu = $('#'+self.config.kitchen),
		        wei = $('#'+self.config.bathroom),
		        yangtai = $('#'+self.config.balcony);
	        if (90>square && square >= 60) {
	            shi.val(2);
	            ting.val(1);
	            chu.val(1);
	            wei.val(1);
	            yangtai.val(1);
	        } else if (150>square&& square >= 90) {
	            shi.val(3);
	            ting.val(2);
	            chu.val(1);
	            wei.val(2);
	            yangtai.val(1);
	        }
	        else if (150<=square ) {
	            shi.val(4);
	            ting.val(2);
	            chu.val(1);
	            wei.val(2);
	            yangtai.val(2);
	        }
	        else
	        {
	            shi.val(1);
	            ting.val(1);
	            chu.val(1);
	            wei.val(1);
	            yangtai.val(1);
	        }
	    }
	}
})(window)

