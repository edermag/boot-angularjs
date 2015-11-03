/**
 * App angular.
 * 
 * @author <a href="mailto:eder@yaw.com.br">Eder Magalhães</a>
 */
(function(angular) {
    'use strict'
    angular.module('bootAngular', [ 'ui.router','ui.bootstrap', 'mercadoria' ])
        .config([
            '$locationProvider',
            '$stateProvider',
            '$urlRouterProvider',
            '$modalProvider',
            '$httpProvider',
            function($locationProvider,
                $stateProvider,
                $urlRouterProvider,
                $modalProvider,
                $httpProvider) {

            	$locationProvider.html5Mode(false);
                $urlRouterProvider.when('', '/');
                $urlRouterProvider.otherwise("/");

                $stateProvider
                .state('base', {
                    abstract: true,
                    views: {
                        'header': {
                            templateUrl: 'views/header.html'
                        }
                    }
                })

                .state('base.list', {
                    url: '/',
                    views: {
                        '@': {
                            templateUrl: 'views/list.html'
                        }
                    }
                })
                
                .state('base.create', {
                    url: '/create',
                    views: {
                        '@': {
                            templateUrl: 'views/form.html'
                        }
                    }
                })
                .state('base.update', {
                    url: '/update/:mercadoriaId',
                    views: {
                        '@': {
                            templateUrl: 'views/form.html'
                        }
                    }
                });
            }
        ])
        .run(['$rootScope', '$state', '$location', function($rootScope, $state, $location){
            $rootScope.$state = $state;
        }]);
})(window.angular);