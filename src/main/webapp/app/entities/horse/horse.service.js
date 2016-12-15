(function() {
    'use strict';
    angular
        .module('mredApp')
        .factory('Horse', Horse);

    Horse.$inject = ['$resource', 'DateUtils'];

    function Horse ($resource, DateUtils) {
        var resourceUrl =  'api/horses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
