function initVars(scope, window) {
	scope.flights = JSON.parse(localStorage.getItem('flights'));
	console.log(scope.flights);
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
	        scope.isLoggedIn = Boolean(localStorage.getItem('isLoggedIn'));
	        scope.activeLoginDiv = !scope.isLoggedIn;
	        scope.alertCreateAccount = !scope.isLoggedIn;
	        break;
	    case "register" :
	    	window.location.href = "pages/register.html";
	        break;
    }
}


var app = angular.module('listFlightsApp', []);
app.controller('listFlightsCtrl', function($scope,$http, $q, $timeout, $window) {
    initVars($scope, $window);
    initView($scope);
    $scope.isLoggedIn = JSON.parse(localStorage.getItem('isLoggedIn'));
    $scope.doClick=function(button) {click(button, $scope,$http);}
    $scope.doBook=function(flight) {book(flight, $scope,$http);}
});