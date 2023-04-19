function initVars(scope) {
	scope.user = new Object();
	scope.preliminary = new Object();
	scope.flights = new Object();
	
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
    scope.isLoggedIn = false;
    scope.activeLoginDiv = false;
    scope.activeAlert = false;
    scope.activeLoginAlert = false;
}
                    
function loadAllAirports(scope,http){
	http.get("rest/listairports").then(function(response) {
		if (response.status == 200 || response.status == 204) {	
			scope.airports = response.data.map(function (state) {
		        return {
		          value: state.airportCountry.toLowerCase(),
		          display: state.airportCountry + " (" + state.airportIataCode + ")"
		        };
		      });
		} else {
			scope.airports = []
			console.log("Failed to list airports.");
		}
	});
}

function createFilterFor(query) {
	var lowercaseQuery = query.toLowerCase();
	
	return function filterFn(state) {
	  return (state.value.indexOf(lowercaseQuery) === 0);
	};
}

function queryAirport (query, scope, q, timeout) {
	var results = query ? scope.airports.filter(createFilterFor(query)) : scope.airports;
	var deferred = q.defer();
	timeout(function () { deferred.resolve(results); }, Math.random() * 1000, false);
	return deferred.promise;
}

function click(button, scope, http){
    switch (button) {
        case "login" :
            scope.activeLoginDiv = true;
            break;
        case "main" :
            scope.activeLoginDiv = false;
            break;
//        case "submit" :
//            scope.activeLoginAlert = true;
//            if(scope.loginForm.$error.required){
//                http.post("rest/loginauthentication", scope.user).then(function(response) {
//                    if (response.status == 200) console.log("Success on logging in."); 
//                    else console.log("Error logging in.");
//                });
//            }
//            break;
        case "search-flight" :
            scope.activeAlert = true;
            if(!scope.mainForm.$error.required){
                scope.preliminary.oneWay = Boolean(scope.preliminary.oneWay)
                http.get("rest/searchflight", scope.preliminary).then(function(response) {
                    if (response.status == 200 || response.status == 204) {
                    	console.log("Success on adding search information"); 
                    	scope.flights = response.data;
                    	console.log(scope.flights);
                    }
                    else console.log("Error on adding search information");
                });
            }
            break;
    }
}

var app = angular.module('homeApp', ['ngMaterial', 'ngMessages']);
app.controller('homeCtrl', function($scope,$http, $q, $timeout) {
    initVars($scope);
    initView($scope);
    loadAllAirports($scope, $http);
    $scope.doClick=function(button) {click(button, $scope,$http);}
    $scope.doQueryAirport = function(query) {return queryAirport(query, $scope, $q, $timeout)}
});