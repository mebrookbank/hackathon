(function() {
    'use strict';

    angular
        .module('mredApp')
        .controller('HorseDetailController', HorseDetailController);

    HorseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Horse'];

    function HorseDetailController($scope, $rootScope, $stateParams, previousState, entity, Horse) {
        var vm = this;

        vm.horse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mredApp:horseUpdate', function(event, result) {
            vm.horse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
