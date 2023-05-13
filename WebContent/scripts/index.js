function initVars(scope) {
	if(localStorage.getItem("user") != null){
		if (localStorage.getItem("user").startsWith("{")){
			if (JSON.parse(localStorage.getItem("user")) !== null){	
				scope.user = JSON.parse(localStorage.getItem("user"));
			}
		}
	} else {
		 scope.user = new Object();
	}
	scope.preliminary = new Object();
	scope.flights = new Object();
	scope.airports = new Object();
	
	scope.data = {
	    nbPassengersOptions: [
	        "1",
	        "2",
	        "3",
	        "4",
	        "5"
	    ]
	};
}
function initView(scope) {
	if (localStorage.getItem("isLoggedIn") === null){
		scope.isLoggedIn = false;
	} else {
		scope.isLoggedIn = JSON.parse(localStorage.getItem("isLoggedIn"));
	}
    scope.activeLoginDiv = false;
    scope.activeAlert = false;
    scope.activeLoginAlert = false;
}
                    
function loadAllAirports(scope,http){
	var result;
	http.get("rest/listairports").then(function(response) {
		if (response.status == 200 || response.status == 204) {	
			result = response.data.map(function (state) {
		        return {
		          value: state.airportCountry.toLowerCase(),
		          display: state.airportCountry + " (" + state.airportIataCode + ")"
		        };
		      });
			localStorage.setItem('airports', JSON.stringify(result));
		} else {
			result = []
			localStorage.setItem('airports', JSON.stringify(result));
			console.log("Failed to list airports.");
		}
	});
	return result;
}

function createFilterFor(query) {
	var lowercaseQuery = query.toLowerCase();
	
	return function filterFn(state) {
	  return (state.value.indexOf(lowercaseQuery) === 0);
	};
}

function queryAirport (query, scope, q, timeout) {
	var results = query ? scope.airports.filter(createFilterFor(query)) : scope.airports;
	return results;
//	console.log(results);
//	var deferred = q.defer();
//	timeout(function () { deferred.resolve(results); }, Math.random() * 1000, false);
//	return deferred.promise;
}

function click(button, scope, http, window){
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
                http.get("rest/loginauthentication", {params: scope.user}).then(function(response) {
                    if (response.status == 200 && !response.data) {
                    	console.log("Success on logging in.");
                    	scope.isLoggedIn = true;
                    	localStorage.setItem('user', JSON.stringify(scope.user));
                    	localStorage.setItem('isLoggedIn', "true");
                    } else {
                    	scope.isLoggedIn = false;
                    	console.log("Error logging in.");
                    	localStorage.setItem('isLoggedIn', "false");
                    }
                });
            }
            scope.isLoggedIn = (localStorage.getItem('isLoggedIn') === 'true');
            scope.activeLoginDiv = !scope.isLoggedIn;
            break;
        case "register" :
        	window.location.href = "pages/register.html";
            break;
        case "search-flight" :
            scope.activeAlert = true;
            if(!scope.mainForm.$error.required){
                scope.preliminary.oneWay = Boolean(scope.preliminary.oneWay)
                http.get("rest/searchflight", {params: scope.preliminary}).then(function(response) {
                    if (response.status == 200 || response.status == 204) {
                    	console.log("Success on adding search information"); 
                    	scope.flights = response.data;
                    	console.log(scope.flights);
                        localStorage.setItem('flights', JSON.stringify(scope.flights));
                        window.location.href = "pages/list_flights.html";
                    }
                    else console.log("Error on adding search information");
                });
            }

            break;
        case "my-tickets" :
        	window.location.href = "pages/list_tickets.html";
        	scope.user = JSON.parse(localStorage.getItem('user'));
        	scope.isLoggedIn = Boolean(localStorage.getItem('isLoggedIn'));
        	break;
        case "my-account" :
        	window.location.href = "pages/my_account.html";
        	scope.user = JSON.parse(localStorage.getItem('user'));
        	scope.isLoggedIn = Boolean(localStorage.getItem('isLoggedIn'));
        	break;
    }
}

var app = angular.module('homeApp', ['ngMaterial', 'ngMessages']);
app.controller('homeCtrl', function($scope,$http, $q, $timeout, $window) {
    initVars($scope);
    initView($scope);
    $http.get("rest/loginauthentication", {params: $scope.user}).then(function(response) {
        if (response.status == 200 && !response.data) {
        	$scope.isLoggedIn = true;
        	localStorage.setItem('user', JSON.stringify($scope.user));
        	localStorage.setItem('isLoggedIn', "true");
        } else {
        	$scope.isLoggedIn = false;
        	localStorage.setItem('user', {});
        	localStorage.setItem('isLoggedIn', "false");
        }
    });
    $http.get("rest/listairports").then(function(response) {
    	var result;
		if (response.status == 200 || response.status == 204) {	
			result = response.data.map(function (state) {
		        return {
		          value: state.airportCountry.toLowerCase(),
		          display: state.airportCountry + " (" + state.airportIataCode + ")"
		        };
		      });
			$scope.airports = result;
			$scope.doClick=function(button) {click(button, $scope,$http, $window);}
		    $scope.doQueryAirport = function(query) {return queryAirport(query, $scope, $q, $timeout)}
		} else {
			result = []
			localStorage.setItem('airports', JSON.stringify(result));
			console.log("Failed to list airports.");
		}
	});
});