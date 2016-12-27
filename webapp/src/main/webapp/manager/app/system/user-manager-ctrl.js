diamond.controller('userManagerController', function ($rootScope, $scope, $log, $loggedHttp) {


    init();

    function init() {
        initTableHeight();
    }

    function initTableHeight() {
        $scope.tableContentHieght = {
            height : $('.content ').height()  -$('.place ').height()- $('.tools ').height() - 24
        }
    }

    $scope.initPageNum = 1;
    $scope.initpageSize = 1;

    $scope.tableconfigs = {
        page: $scope.initPageNum,
        count: $scope.initpageSize,
        sort: "create_time",
        order : "desc",
        getData: function ($defer, params) {
            $scope.tableGridContext = params;
            var condition = {};
            var param = {
                page: params.page(),
                count: params.count(),
                sort: params.sort(),
                order: params.order(),
                condition : condition
            };

            $loggedHttp.post('system/user/list', param).success(function (response) {
                var data = response.data;

                params.total(data.totalcount);
                $defer.resolve(data.items);
            });
        }
    };

    $scope.batchRemove = function () {
        var datas = $scope.tableGridContext.data();
    }
});