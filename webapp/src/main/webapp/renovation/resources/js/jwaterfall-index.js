/* 
 * jQuery throttle / debounce - v1.1 - 3/7/2010
 * http://benalman.com/projects/jquery-throttle-debounce-plugin/
 * 
 * Copyright (c) 2010 "Cowboy" Ben Alman
 * Dual licensed under the MIT and GPL licenses.
 * http://benalman.com/about/license/
 */
(function(b,c){var $=b.jQuery||b.Cowboy||(b.Cowboy={}),a;$.throttle=a=function(e,f,j,i){var h,d=0;if(typeof f!=="boolean"){i=j;j=f;f=c}function g(){var o=this,m=+new Date()-d,n=arguments;function l(){d=+new Date();j.apply(o,n)}function k(){h=c}if(i&&!h){l()}h&&clearTimeout(h);if(i===c&&m>e){l()}else{if(f!==true){h=setTimeout(i?k:l,i===c?e-m:e)}}}if($.guid){g.guid=j.guid=j.guid||$.guid++}return g};$.debounce=function(d,e,f){return f===c?a(d,e,false):a(d,f,e!==false)}})(this);


$(function() {
    var jwf = {
        holder: $('.jwaterfall'),
        page: typeof(seopage) != "undefined" ? seopage : 0,
        threshold : 1400,  //滚动到底部加载更多加载下一页的额外偏移  因为接口要500ms左右 将threshold增大一倍 
        colors : ["#f5f1e6", "#9cb5d4", "#aed2e3", "#edd196", "#c4bab4"],
        init: function() {
            var self = this;
            self.dataUrl = self.holder.attr('data-url');
            self.holder.removeAttr('data-url');
            
            if(self.holder.children().hasClass('nodata')){
                $('.xgt-preload').hide();
                $('.jwaterfall').css('opacity', 1);
                return;
            }

            self.leftCol = self.holder.children().eq(0);
            self.rightCol = self.holder.children().eq(1);
            self.width = self.leftCol.width();
            self.leftH = self.rightH = 0;
            self.leftCol.find('img.lazy0').lazyload({container: '.cc-box'});
            self.rightCol.find('img.lazy0').lazyload({container: '.cc-box'});
            $('.xgt-preload').hide();
            // $(window).scroll($.throttle(100,function() {
            //     if (self.isScrolledIntoView('.jwf-more')) {
            //         if(!self._isLoadingData){
            //             self.page++;
            //             self.getData();
            //         }
            //     }

            // }));
            $('.cc-box').scroll($.throttle(100,function() {
                if (self.isScrolledIntoView('.jwf-more')) {
                    if(!self._isLoadingData){
                        self.page++;
                        self.getData();
                    }
                }

            }));
        },
        //防止重复加载
        _isLoadingData : false,
        getData: function() {
            var self = this;

            this._isLoadingData = true;
            $.ajax({
                url: self.dataUrl,
                dataType: 'json',
                data: {
                    'page': self.page
                },
                success: function(data) {
                    if (data.list.length > 0) {
                        self._isLoadingData = false;    
                        self.data = data.list;
                        self.urlType = data.url_type;
                        self.detailurl = data.list.detailurl;
                        self.dataurl = data.dataurl;
                        self.len = self.data.length;
                        self.splitData();
                    }else if(self.page == 1){
                        
                    }
                    //no more
                }
            });
        },
        _$xgtPreLoad : $('.xgt-preload'),
        splitData: function() {
            var self = this;

            self._leftH = self.leftCol.height();
            self._rightH = self.rightCol.height();

            $(self.data).each(function(i, o) {
                var _holder = $('<a></a>');

                if (self._leftH > self._rightH ) {
                    self.getHtml('right', _holder, o, i);
                    _holder.appendTo(self.rightCol);
                } else {
                    self.getHtml('left', _holder, o, i);
                    _holder.appendTo(self.leftCol);
                }
            });
            this._$xgtPreLoad.hide();
            self.holder.css('opacity', 1);
            self.imgLoad();
        },
        imgLoad: function() {
            var self = this;

            var lazyloadObj = {
                effect: 'fadeIn',
                effectspeed: 300,
                threshold : 200,
                event: "scroll",
                container: '.cc-box',
                load: function() {
                    $(this).parent().next().show();
                },
                placeholder: ''
            }
            self.leftCol.find('img.lazy' + self.page).lazyload(lazyloadObj);
            self.rightCol.find('img.lazy' + self.page).lazyload(lazyloadObj);
        },
        getHtml: function(dir, holder, obj, i) {
            var self = this,
                imgW = obj.width,
                imgH = obj.height,
                $img,
                _h = Math.round((imgH * self.width) / imgW),
                padding = (imgH / imgW / 2 * 100).toFixed(8) + '% 0';

            if (isNaN(_h)) {
                holder.remove();
                return;
            }

            if(dir == 'right'){
                self._rightH += _h;
            }else{
                self._leftH += _h;
            }

            var _url = '/xiaoguotu/c'+obj.oldcid+'.html';
            holder.attr('href', _url); 
            $img = $('<img class="lazy' + self.page + '" data-original="' + obj.thumb +'" alt="'+obj.title+'" title="'+obj.title+'">');
            $('<div class="wf-img-wrap"/>').append($img).css({
                'padding' : padding,
                'background-color': self.colors[Math.floor(this.colors.length * Math.random())]
            }).appendTo(holder); 


            var title = obj.title;
            var info = '';
            if (obj.num) {
                info += '<span class="jwf-num">' + obj.num + '张</span>';
            }
            $('<p>' + info + '</p>').appendTo(holder);

            $('<span>' + title + '</span>').appendTo(holder);
        },
        isScrolledIntoView: function(elem) {
            var docViewTop = $('.cc-box').scrollTop() || $(window).scrollTop(),
                docViewBottom = docViewTop + $(window).height(),
                elemTop = $(elem).offset().top;

            return elemTop <= (docViewBottom + (this.threshold ? this.threshold : 0 ) );
        }
    }
    jwf.init();
    
});