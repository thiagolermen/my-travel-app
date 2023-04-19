function initVars(scope) {
}
function initView(scope) {
}

function click(button, scope, http){
    switch (button) {
        case "book" :
    }
}


var app = angular.module('listApp', []);
app.controller('listCtrl', function($scope,$http) {
    initVars($scope);
    initView($scope);
    $scope.doClick=function(button) {click(button, $scope,$http);}
});