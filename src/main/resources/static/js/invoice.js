var invoice = angular.module('invoice', []);

invoice.controller('InvoiceController', function($scope, $http, $interval){

        $scope.invoices = []
        $scope.displayInvoice = null;
        $scope.isPaying = false;
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
            $scope.displayInvoice = null;
            $scope.isPaying = false;
            $http.get('/invoices').success(function(response) {
                $scope.invoices = response;
            });
        }

        $scope.showInvoice = function(item){
            $http.get("/invoices/" + item.id).success(function(response) {
                $scope.displayInvoice = response;
                $scope.isPaying = false;
            });
        }


        $scope.payInvoice = function(item){
            $http.get("/invoices/" + item.id).success(function(response) {
                $scope.displayInvoice = response;
                $scope.isPaying = true;
                $scope.refreshList();
            });
        }

        $scope.refreshList = function(){
            $http.get('/invoices').success(function(response) {
                $scope.invoices = response;
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
            $scope.isPaying = false;
            $scope.displayInvoice = null;
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

        $scope.refreshDisplayInvoice = function(){
            if($scope.displayInvoice != null) {
                $http.get("/invoices/" + $scope.displayInvoice.id).success(function(response) {
                    $scope.displayInvoice = response;
                    $scope.refreshList();
                });
            }
            setTimeout($scope.refreshDisplayInvoice, 3000);
        }

        setTimeout($scope.refreshDisplayInvoice, 3000);

        $scope.reload();
});