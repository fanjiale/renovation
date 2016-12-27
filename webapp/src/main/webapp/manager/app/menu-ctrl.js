diamond.controller('menuController', function ($rootScope, $scope, $log, $loggedHttp) {
    //导航切换
    $(".header").click(function () {
        var $parent = $(this).parent();
        $(".menuson>li.active").not($parent).removeClass("active open").find('.sub-menus').hide();

        $parent.addClass("active");
        if (!!$(this).next('.sub-menus').size()) {
            if ($parent.hasClass("open")) {
                $parent.removeClass("open").find('.sub-menus').hide();
            } else {
                $parent.addClass("open").find('.sub-menus').show();
            }


        }
    });

    // 三级菜单点击
    $('.sub-menus li').click(function (e) {
        $(".sub-menus li.active").removeClass("active");
        $(this).addClass("active");
    });

    $('.title').click(function () {
        var $ul = $(this).next('ul');
        $('dd').find('.menuson').slideUp();
        if ($ul.is(':visible')) {
            $(this).next('.menuson').slideUp();
        } else {
            $(this).next('.menuson').slideDown();
        }
    });
});
diamond.controller('locationController', function ($rootScope, $scope, $log, $loggedHttp) {
    refreshLocations();

    $scope.$on('refreshLocations', function (event, obj) {
        refreshLocations();
    });

    function refreshLocations() {
        $scope.locations = [];
        var locations = $rootScope.$state.$current.includes;

        for (var key in locations) {
            if (key != '' && locations.hasOwnProperty(key) && locations[key]) {
                var state = $rootScope.$state.get(key);
                $scope.locations.push(state);
            }
        }
    }
});