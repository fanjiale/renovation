diamond.directive('ngTable', function () {
    return {
        restrict: "EA",
        scope: {
            tableconfigs: '='
        },
        templateUrl: 'manager/app/directive/ng-table/ng-table.html',
        link: {
            pre: function (scope, element, attrs) {
                element.find('thead').append(scope.$parent.ngTableData.headContent);
                element.find('tbody').append(scope.$parent.ngTableData.bodyContent);
            },
            post: function (scope, element, attr) {
                scope.$columns = [];
                var thead_tds = element.find("thead").find("tr").find("th");
                thead_tds.each(function (e) {
                    var thead_td = element.find(this);
                    var sort = thead_td.attr("sort");

                    var $column = {
                        code : 1,
                        order : '',
                        sort : sort
                    };

                    $column.sortBy = function () {
                        if(this.code == 1){
                            this.code = 2;
                            this.order = 'asc';
                            scope.tableParameters.sort = this.sort;
                            scope.tableParameters.order = this.order;
                        }
                        else if(this.code == 2){
                            this.code = 3;
                            this.order = 'desc';
                            scope.tableParameters.sort = this.sort;
                            scope.tableParameters.order = this.order;
                        }
                        else {
                            this.code = 1;
                            this.order = '';
                            scope.tableParameters.sort = '';
                            scope.tableParameters.order = '';
                        }
                        scope.params.reload();
                    };

                    scope.$columns.push($column);
                });
            }
        },
        controller: function ($scope, $q, $log) {
            var initPageNum = 1;
            var initPageSize = 10;
            $scope.tableParameters = {};

            var me = $scope.tableconfigs;

            $scope.params = {};
            var params = $scope.params;

            $scope.tableParameters.total = 0;
            $scope.tableParameters.data = [];
            $scope.tableParameters.sort = angular.isDefined(me.sort) ? me.sort : "";
            $scope.tableParameters.order = me.order == "desc" ? "desc" : me.order == "asc" ? "asc" : "";
            $scope.tableParameters.page = angular.isDefined(me.page) ? me.page : initPageNum;
            $scope.tableParameters.count = angular.isDefined(me.count) ? me.count : initPageSize;
            $scope.tableParameters.parameters = function () {
                return {
                    'count': $scope.tableParameters.count,
                    'page': $scope.tableParameters.page
                }
            };

            params.parameters = function (newParameters) {
                if (angular.isDefined(newParameters)) {
                    var page = newParameters.page;
                    var count = newParameters.count;

                    $scope.tableParameters.page = angular.isDefined(page) ? page : initPageNum;
                    $scope.tableParameters.count = angular.isDefined(count) ? count : initPageSize;
                }
                return $scope.tableParameters.parameters();
            };

            params.count = function (count) {
                return angular.isDefined(count) ? this.parameters({
                    'count': count
                }) : $scope.tableParameters.count;
            };

            params.page = function (page) {
                return angular.isDefined(page) ? this.parameters({
                    'page': page
                }) : $scope.tableParameters.page;
            };

            params.total = function (total) {
                $scope.tableParameters.total = angular.isDefined(total) ? total : 0;
                return $scope.tableParameters.total;
            };

            params.sort = function (sort) {
                if(angular.isDefined(sort)) $scope.tableParameters.sort =sort;

                return $scope.tableParameters.sort;
            };

            params.order = function (order) {
                if (angular.isDefined(order)) {
                    if (order.toLowerCase() == "desc") {
                        $scope.tableParameters.order = order;
                        return "desc";
                    }
                    else if (order.toLowerCase() == "asc") {
                        $scope.tableParameters.order = order;
                        return "asc";
                    }
                    else {
                        return $scope.tableParameters.order;
                    }
                }
                else {
                    return $scope.tableParameters.order;
                }
            };

            params.data = function () {
                return $scope.tableParameters.data;
            };

            params.reload = function () {
                var deferred = $q.defer();
                me.getData(deferred, params);
                var promise = deferred.promise;

                promise.then(function (result) {
                    $scope.tableParameters.data = result;

                    $scope.list = $scope.tableParameters.data;

                    $scope.tableParameters.pageTotal = $scope.list.length == 0 ? 0 : Math.ceil($scope.tableParameters.total/$scope.list.length);
                    $scope.tableParameters.currentPage = $scope.tableParameters.page;

                });

            };

            params.reload();

            params.pageTo = function () {
                if(isPositiveNum($scope.tableParameters.currentPage) && $scope.tableParameters.currentPage <= $scope.tableParameters.pageTotal){
                    $scope.tableParameters.page = $scope.tableParameters.currentPage;

                    params.reload();
                }
            };

            params.preAll = function () {
                if($scope.tableParameters.page == 1) return;
                $scope.tableParameters.page = 1;
                params.reload();
            };
            params.pre = function () {
                if($scope.tableParameters.page == 1) return;
                $scope.tableParameters.page--;
                params.reload();
            };
            params.next = function () {
                if($scope.tableParameters.page == $scope.tableParameters.pageTotal) return;
                $scope.tableParameters.page++;
                params.reload();
            };
            params.nextAll = function () {
                if($scope.tableParameters.page == $scope.tableParameters.pageTotal) return;
                $scope.tableParameters.page = $scope.tableParameters.pageTotal;
                params.reload();
            };

            function isPositiveNum(s){//是否为正整数
                var re = /^[0-9]*[1-9][0-9]*$/ ;
                return re.test(s)
            }

        }
    }
}).directive('tableContent', function () {
    return {
        restrict: "EA",
        compile: function (element, qw, asd) {

            var thead_tds = element.find("thead").find("tr").find("th");
            var tbody_tds = element.find("tbody").find("tr").find("td");

            tbody_tds.each(function (e) {
                var tbody_td = element.find(this);

                var sort = tbody_td.attr("sort");
                if(angular.isDefined(sort) && sort !=""){
                    var thead_td = thead_tds.eq(e);

                    var str = ' <div class="sortCls">' +
                                ' <span class="sortUp glyphicon glyphicon-chevron-up" ng-class="{\'up_show\': $columns[' +  e + '].order == \'asc\',\'up_hide\' : $columns[' +  e + '].order == \'desc\'}"></span>' +
                                '<span class="sortdown glyphicon glyphicon-chevron-down" ng-class="{\'down_show\' : $columns[' +  e + '].order == \'desc\',\'down_hide\': $columns[' +  e + '].order == \'asc\'}"></span>' +
                                ' </div>';

                    thead_td.attr("sort", sort);
                    thead_td.attr("ng-click", "$columns[" + e +"].sortBy()");
                    thead_td.append(str);
                }
            });


            var headContent = element.find("thead").html();

            var bodyContent = element.find("tbody").html();

            return {
                post: function (scope, iElem, iAttrs) {
                    scope.ngTableData = {
                        headContent: headContent,
                        bodyContent: bodyContent
                    }

                }
            }

        }
    }
})
    .directive('tableCompile', function ($compile) {
        return function (scope, element, attrs) {
            scope.$watch(element,function (value) {
                    $compile(element.find('.compileContent').contents())(scope);
                }
            );
        };
    })
// configure new 'compile' directive by passing a directive
// factory function. The factory function injects the '$compile'
    .directive('compile', function ($compile) {
        // directive factory creates a link function
        return function (scope, element, attrs) {
            scope.$watch(
                function (scope) {
                    // watch the 'compile' expression for changes
                    return scope.$eval(attrs.compile);
                },
                function (value) {
                    // when the 'compile' expression changes
                    // assign it into the current DOM
                    element.html(value);
                    // compile the new DOM and link it to the current
                    // scope.
                    // NOTE: we only compile .childNodes so that
                    // we don't get into infinite loop compiling ourselves
                    $compile(element.contents())(scope);
                }
            );
        };
    });