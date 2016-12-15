(function() {
    'use strict';

    angular
        .module('mredApp')
        .config(stateConfig);


    stateConfig.$inject = ['$stateProvider', '$qProvider'];

    function stateConfig($stateProvider, $qProvider) {

        $qProvider.errorOnUnhandledRejections(false);

        $stateProvider.state('app', {
            abstract: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ]
            }
        });
    }
})();
