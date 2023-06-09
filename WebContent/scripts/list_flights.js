function initVars(scope, window) {
	scope.flights = JSON.parse(localStorage.getItem('flights'));
	if(localStorage.getItem("returnFlights") != null){
		if (JSON.parse(localStorage.getItem("returnFlights")) !== null){	
			scope.returnFlights = JSON.parse(localStorage.getItem("returnFlights"));
			aux = []
			for (var i = 0; i < scope.returnFlights.length; i++) { 
				aux.push({"departureFlight" : scope.flights[i], "returnFlight" : scope.returnFlights[i]});
			}
			scope.flights = aux;
			localStorage.setItem("returnFlights", null);
			scope.returnFlights = null;
		}
	}
	scope.isOneWay = JSON.parse(localStorage.getItem('isOneWay'));
}
function initView(scope) {
	scope.isLoggedIn = false;
    scope.activeLoginDiv = false;
    scope.activeAlert = false;
    scope.activeLoginAlert = false;
    scope.alertCreateAccount = false;
}

function book(flight, scope, http){
	if(scope.isLoggedIn){
		scope.user = JSON.parse(localStorage.getItem('user'));
		localStorage.setItem('flight', JSON.stringify(flight));
		localStorage.setItem('isOneWay', JSON.stringify(scope.isOneWay));
		window.location.href = "book_flight.html";
	}else{
		scope.alertCreateAccount = true;
	}
}

function click(button, scope, http){
    switch (button) {
	    case "login" :
	        scope.activeLoginDiv = true;
	        break;
	    case "main" :
	        scope.activeLoginDiv = false;
	        break;
	    case "submit" :
	        scope.activeLoginAlert = true;
	        if(!scope.loginForm.$error.required){
	            http.get("../rest/loginauthentication", {params: scope.user}).then(function(response) {
	                if (response.status == 200 && !response.data) {
	                	console.log("Success on logging in.");
	                	localStorage.setItem('user', JSON.stringify(scope.user));
	                	localStorage.setItem('isLoggedIn', "true");
	                } 
	                else console.log("Error logging in.");
	            });
	        }
	        scope.isLoggedIn = (localStorage.getItem('isLoggedIn') === 'true');
	        scope.activeLoginDiv = !scope.isLoggedIn;
	        scope.alertCreateAccount = !scope.isLoggedIn;
	        break;
	    case "register" :
	    	window.location.href = "../pages/register.html";
	        break;
	    case "my-tickets" :
        	window.location.href = "../pages/list_tickets.html";
        	break;
        case "my-account" :
        	window.location.href = "../pages/my_account.html";
        	break;
    }
}


var app = angular.module('listFlightsApp', []);
app.controller('listFlightsCtrl', function($scope,$http, $q, $timeout, $window) {
    initVars($scope, $window);
    initView($scope);
    initView($scope);
    $scope.isLoggedIn = JSON.parse(localStorage.getItem('isLoggedIn'));
    $scope.doClick=function(button) {click(button, $scope,$http);}
    $scope.doBook=function(flight) {book(flight, $scope,$http);}
});