function initVars(scope, window) {
	scope.user = JSON.parse(localStorage.getItem('user'));
}
function initView(scope) {
    
}


function click(button, scope, http){
    switch (button) {
	    case "home" :
	    	localStorage.setItem('user', JSON.stringify(scope.user));
	    	localStorage.setItem('isLoggedIn', "true");
        	window.location.href = "../index.html";
        	break;
	    case "my-tickets" :
	    	localStorage.setItem('user', JSON.stringify(scope.user));
	    	localStorage.setItem('isLoggedIn', "true");
        	window.location.href = "../pages/list_tickets.html";
        	break;
	    case "delete-account":
	    	if(scope.user.email != "admin@admin"){
	    		http.post("../rest/deleteaccount", scope.userBd).then(function(response) {
	    	        if (response.status == 200 || response.status == 204) {
	    	        	console.log("Success on deleting account."); 
	    	        	scope.userBd = null;
	    	        } else console.log("Error on searching tickets.");
	    		});
		    	localStorage.setItem('user', null);
		    	localStorage.setItem('isLoggedIn', "false");
		    	window.location.href = "../index.html";
	    	} else {
	    		console.log("Impossible to dele account");
	    	}
	    	break;
	    case "log-out" :
	    	scope.userBd = null;
	    	localStorage.clear();
	    	window.location.href = "../index.html";
        	break;
    }
}


var app = angular.module('myAccountApp', []);
app.controller('myAccountCtrl', function($scope,$http, $q, $timeout, $window) {
    initVars($scope, $window);
    initView($scope);
    console.log($scope.user)
    $scope.doClick=function(button) {click(button, $scope,$http);}
    var request = $http.get("../rest/myaccount", {params: $scope.user}).then(function(response) {
        if (response.status == 200 || response.status == 204) {
        	console.log("Success on searching tickets."); 
        	$scope.userBd = response.data;
        	console.log($scope.userBd);
        }
        else console.log("Error on searching tickets.");
    });
});