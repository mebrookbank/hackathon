(function() {
    'use strict';

    angular
        .module('mredApp')
        .controller('FlightController', FlightController);

    FlightController.$inject = ['$scope', '$state', 'Flight'];

    function FlightController ($scope, $state, Flight) {
        var vm = this;

        vm.flights = [];

        loadAll();

        function loadAll() {
            Flight.query(function(result) {
                vm.flights = result;
                vm.searchQuery = null;
            });
        }
    }
})();
