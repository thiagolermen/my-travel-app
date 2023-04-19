function initVars(scope, window) {
	scope.flights = JSON.parse(localStorage.getItem('flights'));
	console.log(scope.flights);
}
function initView(scope) {
}

function click(button, scope, http){
    switch (button) {
        case "book" :
    }
}


var app = angular.module('listFlightsApp', []);
app.controller('listFlightsCtrl', function($scope,$http, $q, $timeout, $window) {
    initVars($scope, $window);
    initView($scope);
    //$scope.doClick=function(button) {click(button, $scope,$http);}
});