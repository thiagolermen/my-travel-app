function initVars(scope, window) {
	scope.user = JSON.parse(localStorage.getItem('user'));
}
function initView(scope) {
    scope.activeAlert = false;
    scope.activeLoginAlert = false;
    scope.alertCreateAccount = false;
}

function printPdf(ticket) {
	
	var props = {
			outputType: jsPDFInvoiceTemplate.OutputType.Save,
    	    returnJsPDFDocObject: true,
    	    fileName: "ticket",
    	    orientationLandscape: false,
    	    compress: true,
    	    logo: {
    	        src: "../assets/logo-blue-my-travel.png",
    	        type: 'PNG', //optional, when src= data:uri (nodejs case)
    	        width: 70, //aspect ratio = width/height
    	        height: 25,
    	        margin: {
    	            top: 0, //negative or positive num, from the current position
    	            left: 0 //negative or positive num, from the current position
    	        }
    	    },
    	    stamp: {
                inAllPages: true,
                src: "https://raw.githubusercontent.com/edisonneza/jspdf-invoice-template/demo/images/qr_code.jpg",
                width: 20, //aspect ratio = width/height
                height: 20,
                margin: {
                    top: 100, //negative or positive num, from the current position
                    left: 0 //negative or positive num, from the current position
                }
            },
    	    business: {},
    	    contact: {
    	        label: "Ticket issued for:",
    	        name: ticket.firstName + " " + ticket.lastName,
    	        address: "Passport: " + ticket.passportNumber,
    	        phone: "Birth date: " + ticket.birthDateString,
    	    },
    	    invoice: {
    	        label: "Ticket # " ,
    	        num: ticket.ticketId,
    	        headerBorder: false,
    	        tableBodyBorder: false,
    	        header: [
    	          { title: "Departure date",}, 
    	          { title: "Departure airport",}, 
    	          { title: "Arrival airport"}, 
    	          { title: "Seat"},
    	          { title: "Aircraft"},
    	          { title: "Price"}
    	        ],
    	        table: Array.from(Array(1), (item, index)=>([
    	            ticket.flight.departureDate,
    	            ticket.flight.departureAirport.airportName,
    	            ticket.flight.arrivalAirport.airportName,
    	            ticket.seatNumber,
    	            ticket.flight.aircraft.aircraftName,
    	            "€" + ticket.price
    	        ])),
    	    },
    	    footer: {text: "The ticket is created on a computer and is valid without the signature and stamp.",},
    	    pageEnable: true,
    	    pageLabel: "Page "
	};  
	
	var pdfObject = jsPDFInvoiceTemplate.default(props);
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
    $scope.printPdf=function(ticket) {printPdf(ticket);}
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