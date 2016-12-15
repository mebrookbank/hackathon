(function() {
    'use strict';

    angular
        .module('mredApp')
        .controller('FlightDetailController', FlightDetailController);

    FlightDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Flight'];

    function FlightDetailController($scope, $rootScope, $stateParams, previousState, entity, Flight) {
        var vm = this;

        vm.flight = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mredApp:flightUpdate', function(event, result) {
            vm.flight = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
