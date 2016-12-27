diamond.run(
    ['$rootScope', '$state', '$stateParams', '$log', '$loggedHttp',
        function ($rootScope, $state, $stateParams, $log, $loggedHttp) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;

            $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
                $rootScope.$broadcast('refreshLocations', {});
            });

        }
    ]
);