diamond.controller('roleManagerController', function ($rootScope,$scope,$log,$loggedHttp,$http) {

    $scope.test = "asdasdasd";

    $http({
        url: 'https://app.shanghaiairport.com/?username=x1',
        method: "GET",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(function success(response) {
        var name = response.data;
    });

});