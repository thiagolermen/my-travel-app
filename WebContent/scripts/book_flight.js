function initVars(scope, window) {
	scope.isLoggedIn = JSON.parse(localStorage.getItem('isLoggedIn'));
	scope.user = JSON.parse(localStorage.getItem('user'));
	scope.flight = JSON.parse(localStorage.getItem('flight'));
	scope.data = {mealType: ["VEGETARIAN", "VEGAN", "BEEF", "CHICKEN", "FISH", "FRUIT"]};
	console.log(scope.user);
	console.log(scope.flight);
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
	    case "book" :
            http.post("../rest/bookflight", scope.user).then(function(response) {
                if (response.status == 200 || response.status == 204) console.log("Success on booking flight."); 
                else console.log("Error on booking flight..");
            });
            break;
    }
}


var app = angular.module('bookFlightApp', []);
app.controller('bookFlightCtrl', function($scope,$http, $q, $timeout, $window) {
    initVars($scope, $window);
    initView($scope);
    $scope.doClick=function(button) {click(button, $scope,$http);}
});