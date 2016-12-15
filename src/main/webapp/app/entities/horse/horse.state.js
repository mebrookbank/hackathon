(function() {
    'use strict';

    angular
        .module('mredApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('horse', {
            parent: 'entity',
            url: '/horse',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Horses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/horse/horses.html',
                    controller: 'HorseController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('horse-detail', {
            parent: 'entity',
            url: '/horse/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Horse'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/horse/horse-detail.html',
                    controller: 'HorseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Horse', function($stateParams, Horse) {
                    return Horse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'horse',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('horse-detail.edit', {
            parent: 'horse-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horse/horse-dialog.html',
                    controller: 'HorseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Horse', function(Horse) {
                            return Horse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('horse.new', {
            parent: 'horse',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horse/horse-dialog.html',
                    controller: 'HorseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                horsename: null,
                                phone: null,
                                ownername: null,
                                locationx: null,
                                locationy: null,
                                time: null,
                                point: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('horse', null, { reload: 'horse' });
                }, function() {
                    $state.go('horse');
                });
            }]
        })
        .state('horse.edit', {
            parent: 'horse',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horse/horse-dialog.html',
                    controller: 'HorseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Horse', function(Horse) {
                            return Horse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('horse', null, { reload: 'horse' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('horse.delete', {
            parent: 'horse',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horse/horse-delete-dialog.html',
                    controller: 'HorseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Horse', function(Horse) {
                            return Horse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('horse', null, { reload: 'horse' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
