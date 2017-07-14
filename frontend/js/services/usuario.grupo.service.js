angular.module('app').factory('UsuarioGrupoService', ['$http', function ($http) {

    let porta = 9090;
    let url = `http://localhost:${porta}/api/usuarios-grupo`;

    function remover(idGrupo) {
        return $http.delete(`${url}/remover/${idGrupo}`);
    }

    return ({
        remover: remover
    });
}]);
