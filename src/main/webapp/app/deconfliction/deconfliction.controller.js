(function() {
    'use strict';

    angular
        .module('mredApp')
        .controller('DeconflictionController', DeconflictionController);

    DeconflictionController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function DeconflictionController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        getAccount();

        function getAccount() {

        }
        function register () {
            $state.go('register');
        }
    }
})();
