(function() {
    'use strict';

    angular
        .module('mredApp')
        .controller('HorseDeleteController',HorseDeleteController);

    HorseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Horse'];

    function HorseDeleteController($uibModalInstance, entity, Horse) {
        var vm = this;

        vm.horse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Horse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
