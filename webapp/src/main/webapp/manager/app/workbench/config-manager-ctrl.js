diamond.controller('configManagerController', function ($rootScope, $scope, $log, $loggedHttp) {

    $scope.selectedMenu = "1";

    $scope.selectMenu = function (menu) {
        $scope.selectedMenu = menu;
    };

    $scope.refreshMenu = function () {
        $loggedHttp.post('menu/refreshMenu').success(function (response) {
            if (response.data != 0 || !response.success) {
                alert("更新菜单失败");
            }
            else {
                alert("更新菜单成功");
            }

        });
    };

    $scope.refreshConfig = function () {
        $loggedHttp.post('cache/clearCache').success(function (response) {
            if (response.success) {
                alert("刷新配置成功");
            }
            else {
                alert("刷新配置失败");
            }

        });
    };
});