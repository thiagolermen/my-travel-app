function initVars(scope, window) {
	scope.ticket = new Object();
	scope.ticket.extraLuggage = false;
	scope.ticket.transportFromAirport = false;
	scope.isLoggedIn = JSON.parse(localStorage.getItem('isLoggedIn'));
	scope.user = JSON.parse(localStorage.getItem('user'));
	scope.isOneWay = JSON.parse(localStorage.getItem('isOneWay'));
	scope.flight = JSON.parse(localStorage.getItem('flight'));
	scope.data = {mealType: ["VEGETARIAN", "VEGAN", "BEEF", "CHICKEN", "FISH", "FRUIT"]};
	scope.extraLuggageSelected = false;
	scope.transportFromAirportSelected = false;
	if (scope.isOneWay) {
		scope.ticket.price = scope.flight.price;
	} else {
		scope.ticket.price = scope.flight.departureFlight.price + scope.flight.returnFlight.price;
	}
}
function initView(scope) {
	scope.isLoggedIn = false;
    scope.activeLoginDiv = false;
    scope.activeAlert = false;
    scope.activeLoginAlert = false;
    scope.alertCreateAccount = false;
}

function click(button, scope, http){
    switch (button) {
    	case "extraLuggage":
    		if (scope.ticket.extraLuggage) {
    			scope.extraLuggageSelected = true;
    			scope.ticket.price += 20;
    		} else {
    			scope.extraLuggageSelected = false;
    			scope.ticket.price -= 20;
    		}
    		break;
    	case "transportFromAirport":
    		if (scope.ticket.transportFromAirport) {
    			scope.transportFromAirportSelected = true;
    			scope.ticket.price += 15;
    		} else {
    			scope.transportFromAirportSelected = false;
    			scope.ticket.price -= 15;
    		}
    		break;
	    case "book" :
	    	if (scope.isOneWay) {
	    		http.post("../rest/bookflightoneway", {
		    	    "user": scope.user,
		    	    "departureFlight": scope.flight,
		    	    "returnFlight": null,
		    	    "departureTicket": scope.ticket,
		    	    "returnTicket": null,
		    	    }).then(function(response) {
	                if (response.status == 200 || response.status == 204) console.log("Success on booking flight."); 
	                else console.log("Error on booking flight..");
	            });
	    	} else {
	    		http.post("../rest/bookflightroundtrip", {
		    	    "user": scope.user,
		    	    "departureFlight": scope.flight.departureFlight,
		    	    "returnFlight": scope.flight.returnFlight,
		    	    "departureTicket": scope.ticket,
		    	    "returnTicket": scope.ticket,
		    	    }).then(function(response) {
	                if (response.status == 200 || response.status == 204) console.log("Success on booking flight."); 
	                else console.log("Error on booking flight..");
	            });
	    	}
	    	
	    	window.location.href = "../index.html";
            break;
    }
}


var app = angular.module('bookFlightApp', []);
app.controller('bookFlightCtrl', function($scope,$http, $q, $timeout, $window) {
    initVars($scope, $window);
    initView($scope);
    $scope.doClick=function(button) {click(button, $scope,$http);}
});