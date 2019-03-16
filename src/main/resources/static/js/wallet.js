var invoice = angular.module('invoice', []);

invoice.controller('WalletController', function($scope, $http){

        $scope.wallet = {};
        
        $scope.reload = function () {
            $http.get("/wallet").success(function(response) {
                $scope.wallet = response;
            });
        }

        $scope.reload();
});