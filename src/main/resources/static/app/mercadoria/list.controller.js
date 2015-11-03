/**
 * Controller da view <code>list.html</code>.
 * 
 * @author <a href="mailto:eder@yaw.com.br">Eder Magalhães</a>
 */
(function (angular) {
    'use strict'
    angular.module('mercadoria')
        .controller('MercadoriaListCtrl', [
            '$scope',
            '$state',
            '$q',
            'MercadoriaService',
            function($scope, $state, $q, MercadoriaService) {
            	var loadMercadorias = MercadoriaService
            		.findAll()
            		.success(function (data) {
            			$scope.mercadorias = data;
            		});
            	
            	$q.all([loadMercadorias]);
            	
            	$scope.filters = { nome: '', descricao: '', categoria: '', precoDe: '', precoAte: '', ordemPor: '' };
            	
            	$scope.search = function search() {
            		MercadoriaService
	            		.search($scope.filters)
	            		.success(function (data) {
	            			$scope.mercadorias = data;
	            		});
            	}
            }
        ]);
})(window.angular);
