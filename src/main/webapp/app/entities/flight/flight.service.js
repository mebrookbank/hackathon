(function() {
    'use strict';
    angular
        .module('mredApp')
        .factory('Flight', Flight);

    Flight.$inject = ['$resource', 'DateUtils'];

    function Flight ($resource, DateUtils) {
        var resourceUrl =  'api/flights/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.starttime = DateUtils.convertDateTimeFromServer(data.starttime);
                        data.endtime = DateUtils.convertDateTimeFromServer(data.endtime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
