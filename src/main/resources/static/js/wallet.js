var invoice = angular.module('invoice', []);

invoice.controller('WalletController', function($scope, $http){

        $scope.wallet = {};
        
        $scope.reload = function () {
            $http.get("/wallet").success(function(response) {
                $scope.wallet = response;
            });
            setTimeout($scope.reload, 3000);
        }

        setTimeout($scope.reload, 3000)
});