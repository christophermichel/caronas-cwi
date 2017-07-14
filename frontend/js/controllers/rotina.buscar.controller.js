angular.module('app').controller('RotinaBuscarController',
['$scope', 'RotinaService', 'MapService', 'SolicitacoesService', '$q', '$location', 'toastr',
function ($scope, RotinaService, MapService, SolicitacoesService, $q, $location, toastr) {

  console.log($location.absUrl());
  $header = $location.absUrl();
  listar();

  $scope.matches = [];
  $scope.procurarMatchs = procurarMatchs;
  $scope.enviarSolicitacao = enviarSolicitacao;
  $scope.buscarDiaSemana = buscarDiaSemana;

  function listar() {
    RotinaService.listarPorPassageiro(true).then(response => {
      $scope.rotinas = response.data;
    })
  }

  function procurarMatchs(rotina) {
    $scope.rotinaAtual = rotina;
    RotinaService
    .getRotinasMatchHorarioEComVaga(rotina.idRotina)
    .then(response => {
      let listaDeRotinasMotorista = response.data;
      matrix(rotina, listaDeRotinasMotorista)
      .then(res => {
        let respostaMatrix = res;
        obterRotinasComMatchDistancia(rotina.idRotina, respostaMatrix);
        distancias = [];
      });
    })
  }

  function matrix(rotinaPassageiro, listaDeRotinasMotorista) {
    var matrixDeferred = $q.defer()
    var distancias = [];
    let matrizMotoristas = montarArraysMatriz(listaDeRotinasMotorista);
    let matrizPassageiro = [{ lat: rotinaPassageiro.idOrigem.latitude, lng: rotinaPassageiro.idOrigem.longitude }];

    for (var i = (matrizMotoristas.length/25); i>=0; i--) {
      if (matrizMotoristas.length > 25) {
        matrizAuxiliar = matrizMotoristas.splice(0, 25);
      }

      else {
        matrizAuxiliar = matrizMotoristas;
      }

      new google.maps.DistanceMatrixService().getDistanceMatrix({
        origins: matrizAuxiliar,
        destinations: matrizPassageiro,
        travelMode: 'DRIVING',
        unitSystem: google.maps.UnitSystem.METRIC,
        avoidHighways: false,
        avoidTolls: false
      }, function (response) {
        let matriz = response;
        let i = 0;
        for (var linha in matriz.rows) {
          var distanciaRetorno = matriz.rows[i].elements[0].distance.value;
          distancias.push({ rotina: listaDeRotinasMotorista[i], distancia: distanciaRetorno });
          i++;
        }
        i = 0;

        matrixDeferred.resolve(distancias);
      })};
      return matrixDeferred.promise;
    }

    function obterRotinasComMatchDistancia(idRotina, listaDistanciaRotina) {
      RotinaService.getRotinasComMatchDistancia(idRotina, listaDistanciaRotina).then(function (response) {
        $scope.matches = response.data;
        var matchsFormatoParaPlotarNoMapa = montarArraysMatriz($scope.matches);
        MapService.mapaMatch(matchsFormatoParaPlotarNoMapa);
        let pontosMapa = montarArraysMatriz($scope.matches);
      })
    };

    function montarArraysMatriz (lista) {
      let auxiliar = [];
      lista.forEach(function (elemento) {
        let objetoMatrix = { lat: elemento.idOrigem.latitude, lng: elemento.idOrigem.longitude };
        auxiliar.push(objetoMatrix);
      });
      return auxiliar;
    }

    function enviarSolicitacao(match) {
      let solicitacao = {usuarioAlvo: match.usuario, rotinaUsuarioDono: $scope.rotinaAtual};
      let solicitacaoDTO = {solicitacao: solicitacao, rotinaMotorista: match}
      SolicitacoesService.enviar(solicitacaoDTO).then(res => toastr.success('Solicitação enviada com sucesso'));
    }

    function buscarDiaSemana(match) {
      let ordenador = {
        "domingo": 1,
        "segunda": 2,
        "terca": 3,
        "quarta": 4,
        "quinta": 5,
        "sexta": 6,
        "sabado": 7
      };

      let diasSemana = [];
      diasSemana = match.rotinaDiaSemanaList.map(rds => rds.diaSemana.nome);
      diasSemana.sort((a, b) => ordenador[a] > ordenador[b]);
      return diasSemana.shift() + ' - ' + diasSemana.pop();
    }

    // TODO: Implementar utilização de Selecionar ou remover método
    function selecionar(idRotina) {
      if (isUndefinedOrNull(idRotina)) {
        console.log('undefined or null');
        return;
      }
      RotinaService
      .selecionar(idRotina)
      .then(response => {
        console.log(response);
      });
    }

    function isUndefinedOrNull(object) {
      return (angular.isUndefined(object) || object === null);
    }

    $scope.formatarData = function tresLetras (match) {
      let ordenador = {
        "domingo": 1,
        "segunda": 2,
        "terca": 3,
        "quarta": 4,
        "quinta": 5,
        "sexta": 6,
        "sabado": 7
      };

      let diasSemana = [];
      diasSemana = match.rotinaDiaSemanaList.map(rds => rds.diaSemana.nome);
      diasSemana.sort((a, b) => ordenador[a] > ordenador[b]);
      return (diasSemana.shift().substring(0,3) + ' - ' + diasSemana.pop().substring(0,3)).toUpperCase();
    }

  }]);
