/**
 * Controller da view <code>form.html</code>.
 * 
 * @author <a href="mailto:eder@yaw.com.br">Eder Magalhães</a>
 */
(function (angular) {
    'use strict'
    angular.module('mercadoria')
        .controller('MercadoriaFormCtrl', [
            '$scope',
            '$state',
            '$stateParams',
            '$q',
            'MercadoriaService',
            function($scope, $state, $stateParams, $q, MercadoriaService) {
            	if ($stateParams.mercadoriaId) {
                	var loadForUpdate = MercadoriaService
                		.findById($stateParams.mercadoriaId)
                        .success(function(data) {
                            $scope.mercadoria = angular.copy(data);
                        });
                	
            		$q.all([loadForUpdate]);
            	}
            	
            	$scope.save = function save() {
                    var mercadoria = angular.copy($scope.mercadoria);
                    if (mercadoria.id) {
                    	MercadoriaService
                        	.update(mercadoria)
                            .success($scope.callbackSuccess)
                            .error($scope.callbackError);
                    } else {
                    	MercadoriaService
                            .create(mercadoria)
                            .success($scope.callbackSuccess)
                            .error($scope.callbackError);
                    }
                }
            	
            	$scope.remove = function remove() {
                    var mercadoria = angular.copy($scope.mercadoria);
                    if (mercadoria.id) {
                    	MercadoriaService
                        	.remove(mercadoria)
                            .success($scope.callbackSuccess)
                            .error($scope.callbackError);
                    }
                }
            	
            	$scope.alerts = [];
            	$scope.closeAlert = function(index) {
            	    $scope.alerts.splice(index, 1);
            	};
            	
            	$scope.callbackSuccess = function callbackSuccess(data) {
            		var _msg = $state.current.name === 'base.update' ? 'Mensagem atualizada com sucesso' : 'Mensagem incluída com sucesso';
            		$scope.alerts.push({type: 'success', msg: _msg});
            		$state.go('base.list');
                }

                $scope.callbackError = function callbackError(data, status) {
                	console.log('erro: '+data);
                    if (status === 500) {
                    	$scope.alerts.push({type: 'danger', msg: 'Error Internal (500): '+data});
                    } else {
                    	$scope.alerts.push({type: 'danger', msg: 'Error: '+data.message});
                    }
                }
            }
        ]);
})(window.angular);
