/**
 * 该文件是用于html5页面的下拉城市选择组件
 * 2016/07/29
 */

(function(window){
	var GP_EN = new Array('A 安徽','B 北京','C 重庆','F 福建','G 广西','G 贵州','G 广东','G 甘肃','H 海南','H 湖北','H 黑龙江','H 河南','H 河北','H 湖南','J 江苏','J 吉林','J 江西','L 辽宁','N 内蒙古','N 宁夏','Q 青海','S 四川','S 陕西','S 上海','S 山东','S 山西','T 天津','X 西藏','X 新疆','Y 云南','Z 浙江');
	var GC1 =new Array();
		GC1['A 安徽']=new Array('合肥','阜阳','芜湖','蚌埠','淮南','马鞍山','淮北','铜陵','安庆','黄山','滁州','宿州','巢湖','六安','亳州','池州','宣城');
		GC1['B 北京']=new Array('北京');
		GC1['C 重庆']=new Array('重庆');
		GC1['F 福建']=new Array('福州','厦门','三明','莆田','泉州','漳州','南平','龙岩','宁德');
		GC1['G 广西']=new Array('南宁','百色','柳州','桂林','梧州','北海','防城港','钦州','贵港','玉林','贺州','河池','来宾','崇左');
		GC1['G 贵州']=new Array('贵阳','六盘水','遵义','安顺','铜仁','毕节','黔东南苗族侗族自治州','黔西南布依族苗族自治州','黔南布依族苗族自治州');
		GC1['G 广东']=new Array('广州','深圳','珠海','汕头','韶关','河源','梅州','惠州','汕尾','东莞','中山','江门','佛山','阳江','云浮','湛江','茂名','肇庆','清远','潮州','揭阳');
		GC1['G 甘肃']=new Array('兰州','金昌','白银','天水','嘉峪关','武威','张掖','平凉','酒泉','庆阳','定西','陇南','甘南藏族自治州','临夏回族自治州');
		GC1['H 海南']=new Array('海口','三亚','五指山','琼海','儋州','文昌','万宁','东方','澄迈县','定安县','屯昌县','临高县','白沙黎族自治县','昌江黎族自治县','乐东黎族自治县','陵水黎族自治县','琼中黎族苗族自治县','保亭黎族苗族自治县','南沙群岛','西沙群岛','中沙群岛的岛礁及其海域');
		GC1['H 湖北']=new Array('武汉','黄石','襄阳','十堰','荆州','宜昌','荆门','鄂州','孝感','黄冈','咸宁','随州','仙桃','天门','潜江','神农架林区','恩施土家族苗族自治州');
		GC1['H 黑龙江']=new Array('哈尔滨','齐齐哈尔','鹤岗','双鸭山','鸡西','大庆','伊春','牡丹江','佳木斯','七台河','黑河','绥化','大兴安岭');
		GC1['H 河南']=new Array('郑州','鹤壁','开封','洛阳','平顶山','焦作','新乡','安阳','濮阳','许昌','漯河','三门峡','南阳','商丘','信阳','周口','驻马店');
		GC1['H 河北']=new Array('石家庄','唐山','秦皇岛','邯郸','邢台','保定','张家口','承德','沧州','廊坊','衡水');
		GC1['H 湖南']=new Array('长沙','株洲','湘潭','衡阳','邵阳','岳阳','常德','张家界','益阳','郴州','永州','怀化','娄底','湘西土家族苗族自治州');
		GC1['J 江苏']=new Array('南京','徐州','连云港','淮安','宿迁','盐城','扬州','泰州','南通','镇江','常州','无锡','苏州','昆山','江阴','常熟');
		GC1['J 吉林']=new Array('长春','吉林','四平','辽源','通化','白山','松原','白城','延边朝鲜族自治州');
		GC1['J 江西']=new Array('南昌','景德镇','萍乡','新余','九江','鹰潭','赣州','吉安','宜春','抚州','上饶');
		GC1['L 辽宁']=new Array('沈阳','大连','本溪','鞍山','抚顺','丹东','锦州','葫芦岛','营口','盘锦','阜新','辽阳','铁岭','朝阳');
		GC1['N 内蒙古']=new Array('呼和浩特','乌兰察布','锡林郭勒盟','巴彦淖尔','阿拉善盟','兴安盟','包头','乌海','赤峰','通辽','鄂尔多斯','呼伦贝尔');
		GC1['N 宁夏']=new Array('银川','石嘴山','吴忠','中卫','固原');
		GC1['Q 青海']=new Array('西宁','海东','海北藏族自治州','黄南藏族自治州','海南藏族自治州','果洛藏族自治州','玉树藏族自治州','海西蒙古族藏族自治州');
		GC1['S 四川']=new Array('成都','自贡','攀枝花','泸州','德阳','绵阳','广元','遂宁','内江','乐山','南充','宜宾','广安','达州','巴中','眉山','资阳','雅安','阿坝藏族羌族自治州','甘孜藏族自治州','凉山彝族自治州');
		GC1['S 陕西']=new Array('西安','铜川','宝鸡','咸阳','渭南','延安','汉中','榆林','安康','商洛');
		GC1['S 上海']=new Array('上海');
		GC1['S 山东']=new Array('济南','青岛','聊城','滨州','菏泽','潍坊','日照','淄博','枣庄','东营','烟台','威海','济宁','泰安','莱芜','德州','临沂');
		GC1['S 山西']=new Array('太原','大同','阳泉','长治','晋城','朔州','晋中','忻州','临汾','运城','吕梁');
		GC1['T 天津']=new Array('天津');
		GC1['X 西藏']=new Array('拉萨','那曲','昌都','山南','日喀则','阿里','林芝');
		GC1['X 新疆']=new Array('乌鲁木齐','克拉玛依','石河子','阿拉尔','图木舒克','五家渠','吐鲁番','哈密','和田','喀什','阿克苏','克孜勒苏柯尔克孜自治州','巴音郭楞蒙古自治州','昌吉回族自治州','博尔塔拉蒙古自治州','伊犁哈萨克自治州','阿勒泰地区','塔城地区');
		GC1['Y 云南']=new Array('昆明','曲靖','玉溪','保山','昭通','普洱','临沧','丽江','文山壮族苗族自治州','红河哈尼族彝族自治州','西双版纳傣族自治州','楚雄彝族自治州','大理白族自治州','德宏傣族景颇族自治州','怒江傈傈族自治州','迪庆藏族自治州');
		GC1['Z 浙江']=new Array('杭州','宁波','温州','嘉兴','湖州','绍兴','金华','舟山','台州','丽水','衢州','义乌');
	window.ProvincesSelect = {
		initialize: function(option){
			new select().initialize(option);
		}
	}
	var select = function(){
		/* 初始化组件,并将配置参数option传入 */
		this.initialize= function(option){
			var self = this;
			//配置默认参数
			this.config = $.extend({
				targetDom: '#area',//绑定的目标表单
				data: [{"province": GP_EN},{"city": GC1}],//下拉里面的所需要的参数,以key-value形式传入,key可作为对应value的class
				forDom: '#froms',//当前表单form
				markClass: 'on',//控制选择到的下拉选项
				dataHandle: null,//自定义组装数据
				hideBox: null, //自定义隐藏动画或逻辑
				callback: function(){}
			}, option);

			var self = this,
				moveMark = false,//判断是点击还是触摸移动
				inputParent = $(this.config.targetDom).parent();
			self.areaMove = false;
			inputParent.on('touchstart', function(e){
				self.areaMove = false;
			});
			inputParent.on('touchmove', function(e){
				self.areaMove = true;
			});
			inputParent.on('touchend', function(e){
				var id = $(e.target).attr('id');
				if(!self.areaMove){
					self.createBox();//创建节点
					self.event();//给节点绑定事件
					self.areaMove = false;
					// $(self.config.targetDom).focus();
					self.animate = true;
					var box = self.config.$dom;
					$(self.config.forDom).find('input,select').blur();
					$('#mfyy_phone').blur();
					self.selectFill();
					box.show();
					setTimeout(function(){self.config.$dom.find('.area-select-obj').removeClass('area-mark');},0);//节点显示完毕后才移除class确保css动画执行
					setTimeout(function(){
						self.animate = false;
					}, 400);
				}
			});

		}
		/* 给select绑定事件 */
		this.event= function(){
			var self = this,
				moveMark = false;//判断是点击还是触摸移动
				self.animate = false;//用于判断动画
							
			$(this.config.targetDom).prop('readonly', true);
			// $(this.config.targetDom).off('focus.dw click.dw');
			// //目标表单获得焦点显示select
			// $(this.config.targetDom).on('focus', function(e){
			// 	e.preventDefault();
			// });

			this.config.$dom.on('touchstart', function(e){
				e.preventDefault();
			});

			//用于判断动画结束
			// var transitionEvent = self.whichTransitionEvent();
			// transitionEvent && boxEle.addEventListener(transitionEvent, function(e){
			// 	animate = false;
			// });

			//点击灰色背景关闭select
			this.config.$dom.find(".area-select-background").on('touchstart', function(e){
		        e.preventDefault();
		        if(!self.animate){
		        	self.animate = true;
		        	self.hideBox();
		        }
		    });

			//点击获取触摸点
			var startY = -1,
				originY = -1,
				time = 0,
				stopTime = 0;
			this.config.$dom.find('.area-select').on('touchstart', function(e){
				var targetDom = $(e.target);
				e.preventDefault();
				startY = e.originalEvent.touches[0].pageY;
				originY = e.originalEvent.touches[0].pageY;
				//如果点到的是li下的div
		        if(targetDom.hasClass('area-select-option')){
		            li = targetDom.parent();
		        }else if(targetDom.parent().hasClass('area-select-option')){
		        	li = targetDom.parent().parent();
		        }else{
		            li = targetDom;
		        }
				stopTime = setInterval(function(){
					time++;
					if(time > 20 && !li.hasClass('area-background-tab')){
			        	li.addClass('area-background-tab');
					}
				}, 10);
			});

			//下拉选项里面如果是上下滑动不触发点击
			this.config.$dom.find('.area-select').on('touchmove', function(e){
				var select = $(e.currentTarget);//当前触发事件的元素
				var ul = $(e.currentTarget).find('ul');//触发元素下的ul
				var moveY = e.originalEvent.touches[0].pageY;//移动后的Y坐标
				var move = startY - moveY;//移动的位置 ++↑  --↓
				var heightSelect = select.height();//当前作为窗口的高度
				var heightUl = ul.height();//窗口里面内容的高度
				var height =  heightSelect - heightUl;//两个容器高度差就是ul最大移动距离
				var top = parseInt(ul.data('top') || 0);//获取当前元素的top 之前没有移动则为undefined
				if(moveY !== startY){
					moveMark = true;
					startY = moveY;//将当前移动的坐标覆盖开始值
				}
				if(Math.abs(startY - originY) >= 4){
					clearInterval(stopTime);
					ul.find('.area-background-tab').removeClass('area-background-tab');
		    	}
				if(height < 0){//高度差大于0则说明窗口里面内容不用移动
					if(e.originalEvent.touches[0].clientY <= -1 && top <= height){
						ul.addClass('transition-select');
						ul.css('-webkit-transform', 'translateY(' + height + 'px)');
		        		ul.data('top', height);
		        		setTimeout(function(){
				        	ul.removeClass('transition-select');
				        }, 500);
				        return;
					}
					//如果移动后top大于0则为0   移动后top小于高度差说明移动到最大距离
					var m = (top - move) >= 86 ? 86 : (top - move) <= (height - 86) ? (height - 86) : (top - move);
					ul.data('top', m);
					ul.css('-webkit-transform', 'translateY(' + m + 'px)');
				}
			});

			//触摸离开
		    this.config.$dom.find('.area-select').on('touchend', function(e){
		    	//如果是上下滑动不执行后续动作
		    	time = 0;
		    	clearInterval(stopTime);
		        var ul = $(e.currentTarget).find('ul');
		        var height = $(this).height() - ul.height();
		        ul.addClass('transition-select');
		        if(ul.data('top') >= 0){
		        	ul.css('-webkit-transform', 'translateY(0px)');
		        	ul.data('top', '0');
		        }else if(ul.data('top') <= height){
		        	ul.css('-webkit-transform', 'translateY(' + height + 'px)');
		        	ul.data('top', height);
		        }
		        setTimeout(function(){
		        	ul.removeClass('transition-select');
		        }, 500);
		    	$(e.currentTarget).find('.area-background-tab').removeClass('area-background-tab');
		    	if(moveMark && Math.abs(startY - originY) >= 4){
		    		moveMark = false;
		    		return;
		    	}
		        var targetDom = $(e.target),//当前点击的元素
		        	dataFont = '',//area-select-province里面text
		            city = '',//area-select-city里面的text
		            li = '';//当前点击的li

		        //如果点到的是li下的div
		        if(targetDom.hasClass('area-select-option')){
		            li = targetDom.parent();
		        }else if(targetDom.parent().hasClass('area-select-option')){
		        	li = targetDom.parent().parent();
		        }else{
		            li = targetDom;
		        }
		        //如果点击的是第一行选项 给第二行填充对应数据
		        if(li.parent().hasClass('area-select-province')){
		        	dataFont = li.data('handle');
				    self.fillCity(dataFont, city);
		        }
		        if(li.hasClass('area-select')){
		        	return;
		        }
		        li.parent().find('.' + self.config.markClass).removeClass(self.config.markClass);
		        li.addClass(self.config.markClass);
		        //如果点击的是第二行 则关闭select
		        if(li.parent().hasClass('area-select-city')){
			        //将之前的选择标记干掉 给点击的li加上标记
			        //拿到两个选择的text 填充到目标表单 然后执行关闭动画
					var province = self.config.$dom.find('.area-select-province').find('.' + self.config.markClass + ' .area-select-option').text();
					var city = self.config.$dom.find('.area-select-city').find('.' + self.config.markClass + ' .area-select-option').text();
					$(self.config.targetDom).val(province.substring(2) + ' ' + city);
					animate = true;
					self.config.callback();
		        	self.hideBox();
		        }
		    });
		}
		//如果输入框里面有值,则展示出来
		this.selectFill= function(){
			var cityArr = $(this.config.targetDom).val().split(' ');
			var ul = this.config.$dom.find('.area-select-province');
			var onHandle = '';
			if($(this.config.targetDom).val()){
				var liDom = ul.find('li');
				for(var i=0; i< liDom.length; i++){
					var handle = $(liDom[i]).data('handle');
					if(cityArr[0] === handle.substring(2)){
						ul.find('.on').removeClass('on');
						$(liDom[i]).addClass('on');
						onHandle = handle;
					}
				}
				this.fillCity(onHandle, '', cityArr[1]);
			}
		}
		//填充第二行数据
		this.fillCity= function(dataFont, city, cityText){
			var self = this;
			str = '<ul class="area-select-city">';

    		for (var key in self.config.data[1]['city'][dataFont]) {
	    		city += '<li class=\"' + (self.config.data[1]['city'][dataFont][key] === cityText ? self.config.markClass : "") + '\">' +
		                    '<div class="area-select-option">' + self.config.data[1]['city'][dataFont][key] + '</div>' +
		                '</li>';
    		};
    		str += city + '</ul>';
		    this.config.$dom.find('.area-city').html(str);
		}
		//用于返回判断动画的事件名
		this.whichTransitionEvent= function(){
			var el = document.createElement('fakeelement');
			var transitions = {
				'transition': 'transitionend',
				'OTransition': 'oTransitionEnd',
				'MozTransition': 'transitionend',
				'WebkitTransition': 'webkitTransitionEnd'
			}
			for(var t in transitions){
				if(el.style[t] !== undefined){
					return transitions[t];
				}
			}
		}
		/* 关闭select */
		this.hideBox= function(){
			//如果存在自定义关闭函数则执行自定义函数
			if( typeof this.config.hideBox === 'function' ){
				this.config.hideBox();
				return;
			}

			//接触事件绑定
			this.config.$dom.off('touchstart');

			//点击灰色背景关闭select
			this.config.$dom.find(".area-select-background").off('touchstart');

			//点击获取触摸点
			this.config.$dom.find('.area-select').off('touchstart');

			//下拉选项里面如果是上下滑动不触发点击
			this.config.$dom.find('.area-select').off('touchmove');

			//触摸离开
		    this.config.$dom.find('.area-select').off('touchend');

			$(document.body).removeAttr('style');
            this.config.$dom.find('.area-select-obj').addClass('area-mark');
            var self = this;
            var clearT = setTimeout(function(){
                clearTimeout(clearT);
                //然后干掉节点
                self.config.$dom.remove();
            }, 400);

		}
		/* 将数据组装成节点 */
		this.dataHandle= function(){
			var areaSelect = '';//组件的select
			for(var data in this.config.data){
				for (var key in this.config.data[data]) {
					var li = '',
						arr = [],
						select = '<div class="area-select area-' + key + '">' +
						            '<ul class="area-select-' + key + '">';//将key加上area-select前缀class

					if('city' === key){
						arr = this.config.data[data][key]['A 安徽'];
					}else{
						arr = this.config.data[data][key];
					}

					for(var k in arr){
						var str = '';
						if('city' === key){
							str = arr[k];
						}else{
							str = '<span>'+ arr[k].substring(2,-1) + '</span>' + arr[k].substring(2);
						}
						li += '<li data-handle="' + arr[k] + '" class=\"' + ("city" === key ? "" : (k == 0 ? this.config.markClass : "")) + '\">' +
			                    '<div class="area-select-option">' + str + '</div>' +
			                 '</li>';
					}

					li += '</ul></div>';
					select += li;
					areaSelect += select;
				};
			}
			return areaSelect;
		}
		/* 创建select */
		this.createBox= function(){
			//如果有自定义组装函数就执行自定义组装函数
			var areaSelect = typeof this.config.dataHandle === 'function' ? this.config.dataHandle() : this.dataHandle();

			var str = '<div class="area-select-box">' +
					    '<div class="area-select-background"></div>' +
					    '<div class="area-select-obj area-mark">' +
					    	areaSelect +
					    '</div>' +
					'</div>';
			this.config.$dom = $(str);
			$(document.body).append(this.config.$dom);
		}
	}
})(window)