var invoice = angular.module('invoice', []);

invoice.controller('InvoiceController', function($scope, $http){

        $scope.invoices = []

        $scope.invoice = {
            items: [],
            date: Date.now(),
            status: "NEW",
            paid: 0,
            totalPaid: 0,
            id: 0
        };

        $scope.state = "list";


        $scope.reload = function(){
            $http.get('/invoices').success(function(response) {
                $scope.invoices = response;
                console.log($scope.invoices);
            });
        }

        $scope.save = function () {
            $scope.invoice.totalAmount = $scope.total();
            $scope.invoice.totalPaid = 0;
            $scope.invoice.status = 'NEW';
            $http.post('/invoices', $scope.invoice).success(function(response) {
                $scope.reload();
            });
            $scope.cancel();
            $scope.reload();
        }

        $scope.cancel = function () {
            $scope.resetInvoice();
            $scope.state = 'list';
        }

        $scope.newInvoice = function () {
            $scope.state = 'new';
            $scope.resetInvoice();
        }

        $scope.resetInvoice = function() {
            $scope.invoice = {
                items: [],
                date: Date.now(),
                status: "New",
                paid: 0,
                totalPaid: 0,
                id: 0
            };
        }

        $scope.add = function(){
            $scope.invoice.items.push({
                name: "",
                description: "",
                qty: 1,
                price: 0.0
            });
        }

        $scope.remove = function(index){
            $scope.invoice.items.splice(index, 1);
        }
        $scope.total = function(){
            var total = 0;
            angular.forEach($scope.invoice.items, function(item){
                total += item.qty * item.price;
            })
            return total;
        }

        $scope.reload();
});