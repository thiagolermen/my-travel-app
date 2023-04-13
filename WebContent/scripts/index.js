function initVars(scope) {
            scope.user = new Object();
            scope.preliminary = new Object();
            
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
                    if(scope.loginForm.$error.required){
                        http.post("rest/loginauthentication", scope.user).then(function(response) {
                            if (response.status == 200) console.log("Success on logging in."); 
                            else console.log("Error logging in.");
                        });
                    }
                    break;
                case "search-flight" :
                    scope.activeAlert = true;
                    if(!scope.mainForm.$error.required){
                        scope.preliminary.oneWay = Boolean(scope.preliminary.oneWay)
                        http.post("rest/searchflight", scope.preliminary).then(function(response) {
                            if (response.status == 200 || response.status == 204) console.log("Success on adding search information"); 
                            else console.log("Error on adding search information");
                        });
                    }
                    break;
            }
        }


        var app = angular.module('homeApp', []);
        app.controller('homeCtrl', function($scope,$http) {
            initVars($scope);
             initView($scope);
            $scope.doClick=function(button) {click(button, $scope,$http);}
        });