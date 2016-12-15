(function() {
    'use strict';

    angular
        .module('mredApp')
        .controller('FlightDialogController', FlightDialogController);

    FlightDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Flight'];

    function FlightDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Flight) {
        var vm = this;

        vm.flight = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.flight.id !== null) {
                Flight.update(vm.flight, onSaveSuccess, onSaveError);
            } else {
                Flight.save(vm.flight, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mredApp:flightUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.starttime = false;
        vm.datePickerOpenStatus.endtime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
