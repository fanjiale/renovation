/**
 * by hank.lan   原本用于替换成app下载引导banner    
 * 该文件是用于 替换底部banner  首页底部  上滑浮框底部banner替换
 * 2016/11/16
 */

(function(window){
	window.appGuide = {
		init: function(option){
			var self = this;
			self.config = $.extend({
				url: 'http://m.to8to.com/apps',//调转的url
				ios_ptag:'',//ios 下载ptag
				android_ptag:''//android 下载ptag
			}, option);
			//用cookie判断是否 已经在首页进入发标页发标  底部上滑发标  以及效果图详情页发标
			//深圳城市优先出现微信小号的banner，逻辑跟app的bannner一样
			if (getCookie('already_fabiao_shenzhen') == 'true') {//特定发标框深圳发标
				self.replaceWxBanner('深圳');
				return false;
			}if(getCookie('already_fabiao_dongguan') == 'true'){//特定发标框东莞发标
				self.replaceWxBanner('东莞');
				return false;
			}else if (getCookie('already_fabiao') == 'true') {//特定发标框发标(最初始调用的app下载banner)
				self.replaceBanner();
				return false;	
			}else{
				//首页
				$('#homePage').show();
				//底部上滑浮框
				$('.whan-ad-ask').show();
				return false;
			}

		},
		//替换底部 m首页 底部上浮浮框 banner  -- 效果图详情页 替换文字
		replaceBanner: function(){

			var self = this;
			var str1 =  '<section class="bar-appguide whan-ad-h70" id="appguide_new">' +
							'<input type="hidden" exposure-ptag="2_3_3_1193" click-ptag="2_3_3_1192">'+
							'<section class="appguide-box">' +
								'<section class="appguide-img">' +
								'<img src="http://img.to8to.com/wap/app_guide1.png?v=20161117">'+
								'</section>'+
								'<section class="appguide-text">' +
								'<p class="appguide-p1">装修入了这些坑 到手媳妇都跑了</p>'+
								'<p class="appguide-p2">2,615万次播放</p>'+
								'<p class="appguide-p3">打开土巴兔App观看 干货满满<img class="appguide-finger" src="http://img.to8to.com/wap/bar_finger.png?v=20161117"></p>'+
								'</section>'+
							'</section>'+
						'</section>';
			var str2 =  '<section class="bar-appguide whan-ad-h70" id="appguide_new">' +
							'<input type="hidden" exposure-ptag="2_3_3_1189" click-ptag="2_3_3_1188">'+
							'<section class="appguide-box">' +
								'<section class="appguide-img">' +
								'<img src="http://img.to8to.com/wap/app_guide2.png?v=20161117">'+
								'</section>'+
								'<section class="appguide-text">' +
								'<p class="appguide-p1">预算陷阱太坑爹 看完再装不吃亏</p>'+
								'<p class="appguide-p2">2,534万次播放</p>'+
								'<p class="appguide-p3">打开土巴兔App观看 干货满满<img class="appguide-finger" src="http://img.to8to.com/wap/bar_finger.png?v=20161117"></p>'+
								'</section>'+
							'</section>'+
						'</section>';
			var str3 =  '<section class="bar-appguide whan-ad-h70" id="appguide_new">' +
							'<input type="hidden" exposure-ptag="2_3_3_1185" click-ptag="2_3_3_1184">'+
							'<section class="appguide-box">' +
								'<section class="appguide-img">' +
								'<img src="http://img.to8to.com/wap/app_guide3.png?v=20161117">'+
								'</section>'+
								'<section class="appguide-text">' +
								'<p class="appguide-p1">装修必须要知道的5个风水煞！</p>'+
								'<p class="appguide-p2">3,423万次播放</p>'+
								'<p class="appguide-p3">打开土巴兔App观看 干货满满<img class="appguide-finger" src="http://img.to8to.com/wap/bar_finger.png?v=20161117"></p>'+
								'</section>'+
							'</section>'+
						'</section>';
			var random = parseInt(Math.random()*3);//随机0-2
			$('.whan-ad-h70').remove();
			$('#banner-wx').remove();
			if (random == 0) {              //样式1
				$('body').append(str1);
				self.config.ios_ptag = '2_3_3_1191';
				self.config.android_ptag = '2_3_3_1190';
			}else if (random == 1){         //样式2
				$('body').append(str2);
				self.config.ios_ptag = '2_3_3_1187';
				self.config.android_ptag = '2_3_3_1186';	
			}else{                          //样式3
				$('body').append(str3);
				self.config.ios_ptag = '2_3_3_1183';
				self.config.android_ptag = '2_3_3_1182';
			}
			//给新的banner  绑定点击统计  曝光统计			
			self.manualHref();
		},
		manualHref:function(){
			var self = this;
			var ptag = $('#appguide_new').find('input').attr('click-ptag');
			//插入机构后获取
			var e_ptag = $('#appguide_new').find('input').attr('exposure-ptag');
			
			//埋点  曝光统计	     
			(typeof clickStream !== 'undefined') && clickStream.getCvParams(e_ptag);	        
	        $("#appguide_new").on('touchstart',function(e){
	            //点击埋点  点击统计	            
	            (typeof clickStream !== 'undefined') && clickStream.getCvParams(ptag);
	            Components.loadAPP('app_0_a_to8toh5dh_all');         
	        });
		},
		replaceWxBanner: function(city){//深圳地区banner
			//深圳就显示微信小号banner
			var self = this;

			var str = '';
			if (city==='深圳') {
				str = '<div class="banner-wx-new banner-wx-new-index" id="banner-wx-new">'+
							    '<div class="banner-wx-new-img">'+
							        '<img src="http://static.to8to.com/wap/static/images/banner_people1.gif" alt="微信小号">'+
							    '</div>'+
							    '<div class="banner-wx-new-text">'+
							        '<p>添加“<span class="banner-wx-new-text-name">深圳装修顾问-馨馨</span>”为好友<br/> 随时咨询户型设计 装修报价等疑问</p>'+
							        '<p><span class="banner-wx-new-text-number">微信号：<span class="banner-wx-new-text-hao" id="banner-wx-new-text-hao">13302974452</span>'+
							        												'<span class="weixin-pop-wx3-btn pulse-btn">'+
							        													'<img src="http://static.to8to.com/wap/static/images/bj_result/wx_icon.png" alt="" class="weixin-pop-wx3-btn-icon"/>'+
							        													'<span class="weixin-pop-wx3-btn-text" id="new-result-wx3-btn-text">点击加微信</span>'+
							        												'</span></span></p>'+
							    '</div>'+
							'</div>';

				setTimeout(function(){
					(typeof clickStream !== 'undefined') && clickStream.getCvParams('2_1_1_1320');
				},10);
			}
			else {
				//东莞
				str = '<div class="banner-wx-new banner-wx-new-index" id="banner-wx-new">'+
						    '<div class="banner-wx-new-img">'+
						        '<img src="http://static.to8to.com/wap/static/images/banner_people1.gif" alt="微信小号">'+
						    '</div>'+
						    '<div class="banner-wx-new-text">'+
						        '<p>添加“<span class="banner-wx-new-text-name">东莞装修顾问-蓉蓉</span>”为好友<br/> 随时咨询户型设计 装修报价等疑问</p>'+
						        '<p><span class="banner-wx-new-text-number">微信号：<span class="banner-wx-new-text-hao" id="banner-wx-new-text-hao">13302974661</span>'+
						        												'<span class="weixin-pop-wx3-btn pulse-btn">'+
						        													'<img src="http://static.to8to.com/wap/static/images/bj_result/wx_icon.png" alt="" class="weixin-pop-wx3-btn-icon"/>'+
						        													'<span class="weixin-pop-wx3-btn-text" id="new-result-wx3-btn-text">点击加微信</span>'+
						    '</div>'+
						'</div>';
				setTimeout(function(){
					(typeof clickStream !== 'undefined') && clickStream.getCvParams('2_1_1_1481');
				},10);
			}
			$('.whan-ad-h70').remove();
			$('body').append(str);
			self.bindEvent(city);

		},
		bindEvent: function(city){
			var phone = '';
			if (city==='深圳') {
				phone = '13302974452';
			}
			else {
				//东莞
				phone = '13302974661';
			}
			$('body').on('touchstart','#banner-wx-new',function(){
				var pop = '<div class="banner-wx-new-pop" id="banner-wx-new-pop">'+
					        '<div class="banner-wx-new-pop-box">'+
					            '<p class="banner-wx-new-pop-box-tip">长按微信号复制 <img src="http://static.to8to.com/wap/static/images/bj_result/hand.gif" alt=""></p>'+
					            '<p class="banner-wx-new-pop-box-num" id="banner-wx-new-pop-box-num">'+phone+'</p>'+
					            '<span class="banner-wx-new-pop-box-close">X</span>'+
					        '</div>'+
					    '</div>';
				$('body').append(pop);
			});
			//点击按钮关闭弹窗
            $('body').on('touchstart','.banner-wx-new-pop-box-close',function(event){
            	event.preventDefault();
                $('#banner-wx-new-pop').remove();
            });	
		}
	}

	//自己调用自己方法
	window.appGuide.init({});
})(window)