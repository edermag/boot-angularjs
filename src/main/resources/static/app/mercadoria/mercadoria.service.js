/**
 * Serviço com as operações REST do módulo mercadoria.
 * 
 * @author <a href="mailto:eder@yaw.com.br">Eder Magalhães</a>
 */
(function (angular) {
    angular.module('mercadoria')
        .factory('MercadoriaService', ['$http', function ($http) {
            return {
                findAll: function findAll() {
                    return $http.get('/api/mercadorias');
                },
                findById: function findById(mercadoriaId) {
                	return $http.get('/api/mercadorias/'+mercadoriaId);
                },
                create: function create(mercadoria) {
                	return $http.post('/api/mercadorias', mercadoria);
                },
                update: function update(mercadoria) {
                	return $http.put('/api/mercadorias/'+mercadoria.id, mercadoria);
                },
                remove: function remove(mercadoria) {
                	return $http.delete('/api/mercadorias/'+mercadoria.id);
                },
                search: function search(filters) {
                	console.log($.param(filters));
                	return $http.get('/api/mercadorias/search?'+$.param(filters));
                }
            }
        }]);
})(window.angular);
