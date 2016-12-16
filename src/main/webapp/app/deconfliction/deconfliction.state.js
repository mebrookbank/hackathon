(function() {
  'use strict';

  angular
    .module('mredApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider.state('deconfliction', {
      parent: 'app',
      url: '/deconfliction',
      data: {
        authorities: [],
        pageTitle: 'Deconfliction'
      },
      views: {
        'content@': {
          templateUrl: 'app/deconfliction/deconfliction.html',
          controller: 'DeconflictionController',
          controllerAs: 'vm'
        }
      },
      resolve: {}
    });
  }
})();
