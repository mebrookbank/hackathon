(function() {
    'use strict';

    angular
        .module('mredApp')
        .config(stateConfig, ['openlayers-directive']);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('deconfliction', {
            parent: 'app',
            url: '/deconfliction',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/deconfliction/deconfliction.html',
                    controller: 'DeconflictionController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
