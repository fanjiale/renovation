var diamond = angular.module('diamond', ['ui.router']);
diamond
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/system/user');

        for (var key in appStates) {
            if (appStates.hasOwnProperty(key)) {
                $stateProvider.state(key, appStates[key]);
            }
        }
    }])
    .factory('$loggedHttp', function ($http, $log) {
        return {
            get: function (url) {
                showWaitWindow();
                return $http.get(url).success(function (response) {
                    hideWaitWindow();
                    $log.info(response.data);
                }).error(function (r) {
                    hideWaitWindow();
                })
            },
            post: function (url, param) {
                showWaitWindow();
                $log.info(param);
                return $http.post(url, param).success(function (response) {
                    hideWaitWindow();
                    $log.info(response.data);
                }).error(function (r) {
                    hideWaitWindow();
                })
            },
            put: function (url, param) {
                showWaitWindow();
                $log.info(param);
                return $http.put(url, param).success(function (response) {
                    hideWaitWindow();
                    $log.info(response.data);
                }).error(function (r) {
                    hideWaitWindow();
                })
            },
            delete: function (url, param) {
                showWaitWindow();
                return $http.delete(url, param).success(function (response) {
                    hideWaitWindow();
                    $log.info(response.data);
                }).error(function (r) {
                    hideWaitWindow();
                })
            },
            syncGet: function (url, callback) {
                $.ajax({
                    contentType: "application/json",
                    url: url,
                    dataType: "json",
                    async: false,
                    type: "get",
                    success: function (data) {
                        $log.info("URL:" + url + " GET 请求返回");
                        // $log.debug(data);
                        callback(data);
                    },
                    error: function (r) {
                    }
                })
            },
            syncPost: function (url, param, callback) {
                $.ajax({
                    contentType: "application/json",
                    url: url,
                    dataType: "json",
                    data: JSON.stringify(param),
                    async: false,
                    type: "post",
                    success: function (data) {
                        $log.info("URL:" + url + " POST 请求返回");
                        // $log.debug(data);
                        callback(data);
                    },
                    error: function (r) {
                    }
                })
            }
        }
    });