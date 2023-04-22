function initVars(scope, window) {
	scope.user = JSON.parse(localStorage.getItem('user'));
}
function initView(scope) {
    scope.activeAlert = false;
    scope.activeLoginAlert = false;
    scope.alertCreateAccount = false;
}


function click(button, scope, http){
    switch (button) {
	    case "home" :
	    	localStorage.setItem('user', JSON.stringify(scope.user));
	    	localStorage.setItem('isLoggedIn', "true");
        	window.location.href = "../index.html";
        	break;
        case "my-account" :
        	window.location.href = "../pages/my_account.html";
        	break;
    }
}


var app = angular.module('listTicketsApp', []);
app.controller('listTicketsCtrl', function($scope,$http, $q, $timeout, $window) {
    initVars($scope, $window);
    initView($scope);
    $scope.doClick=function(button) {click(button, $scope,$http);}
    console.log($scope.user)
    var request = $http.get("../rest/searchticket", {params: $scope.user}).then(function(response) {
        if (response.status == 200 || response.status == 204) {
        	console.log("Success on searching tickets."); 
        	$scope.tickets = response.data;
        	console.log($scope.tickets);
            localStorage.setItem('tickets', JSON.stringify($scope.tickets));
        }
        else console.log("Error on searching tickets.");
    });
});