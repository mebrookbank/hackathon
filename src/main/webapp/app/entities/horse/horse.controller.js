(function() {
    'use strict';

    angular
        .module('mredApp')
        .controller('HorseController', HorseController);

    HorseController.$inject = ['$scope', '$state', 'Horse'];

    function HorseController ($scope, $state, Horse) {
        var vm = this;

        vm.horses = [];

        loadAll();

        function loadAll() {
            Horse.query(function(result) {
                vm.horses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
