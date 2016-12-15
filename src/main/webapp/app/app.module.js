(function() {
    'use strict';

    angular
        .module('mredApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'openlayers-directive'
        ])
        .run(run);


    run.$inject = ['stateHandler'];


    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
