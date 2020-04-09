//var app = angular.module("CandidateManagement", []);
var app = angular.module("CandidateManagement", ['ui.bootstrap']);
//var app = angular.module("CandidateManagement", ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);
 
// Controller Part
app.controller("CandidateController", function($scope, $http) {
	
      /*$scope.filteredTodos = [];*/
	  $scope.filteredEmps = [];
	  $scope.itemsPerPage = 4;
	  $scope.currentPage = 1;
	  $scope.totalRowsCount = "";
	  $scope.begin = "";
	  $scope.end = "";
	  


    $scope.candidates = [];
    $scope.disabledCandidates = [];
    $scope.candidateForm = {
        candidateId:"",
        firstName: "",
        lastName: "" ,
        email:"" ,
        phone:"",
        dob:"",
        jobTitle:"",
        phone:"",
        city:"",
        state:"",
        country:"",
        pinCode:"",
        createdBy:"",
        createdDate:"",
        updatedDate:"",
        updatedBy:"",
        activeStatus:""
        
        
    };

      $scope.figureOutTodosToDisplay = function() {
    	  
    	  console.log($scope.currentPage+","+$scope.itemsPerPage);
		 $scope.begin = (($scope.currentPage - 1) * $scope.itemsPerPage);
		 $scope.end = $scope.begin + $scope.itemsPerPage;
		 console.log($scope.begin+","+$scope.end);
	    
		 //$scope.filteredEmps = $scope.employees.slice(begin, end);	 
	        $http({
	            method: 'GET',
	            url: '/getcandidate/'+$scope.begin+"/"+$scope.end
	        }).then(
	            function(res) { // success
	                $scope.candidates = res.data;                
	               // $scope.figureOutTodosToDisplay();
	                _getRowsCount();
	            },
	            function(res) { // error
	                console.log("Error: " + res.status + " : " + res.data);
	            }
	        );
	    
	  };
	  
	  /*$scope.makeTodos();*/ 
	  //$scope.figureOutTodosToDisplay();

	  $scope.pageChanged = function() {
	    $scope.figureOutTodosToDisplay();
	  };
    
    
	  _getRowsCount(); 
    
  
 
    // Now load the data from server
    _refreshCandidateData();
   
    //SearchCandidateData
    $scope.searchCandidateData = function(candidate) {
      	 console.log("Search invoked : ");
      	 
      	 var id = document.getElementById("candidateId").value;
      	 //alert(id);
      	 
        $http({
            method: 'GET',
            url: '/searchcandidate/'+id
        }).then(
            function(res) { // success
           	// console.log(res.data);
                $scope.candidates = res.data;
                //console.log(employees);
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
      };
   
    // HTTP POST/PUT methods for add/edit employee  
    // Call: http://localhost:8080/employee
    $scope.submitCandidate = function() {
 
 
        $http({
            method: "post",
            url: "/savecandidate",
            data: angular.toJson($scope.candidateForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function(response) {
            $scope.candidates = response.data;
            
            $scope.$digest();
           }, function(response) {
               $scope.errortext = response.statusText;
           });
    };

   
 
    $scope.createCandidate = function() {
        _clearFormData();
    }
 
    
    $scope.deleteCandidate = function(candidate) {
        $http({
            method: 'DELETE',
            url: '/deletecandidate?candidateId=' + candidate.candidateId
        }).then(_success, _error);
    };
    
    $scope.disableCandidate = function(candidate){
    	$http({
    		method:'PUT',
    		url:'/disableCandidate?candidateId=' + candidate.candidateId
    	}).then(_success,_error);
    };
 
    
    // In case of edit
    $scope.editCandidate = function(candidate) {
    	$scope.candidateForm =candidate;
        
    };

     function _getRowsCount() {    	
        $http({
            method: 'GET',
            url: '/getcandidateCount'
        }).then(
            function(res) { // success
                $scope.totalRowsCount = res.data;
                console.log($scope.totalRowsCount);
               /* $scope.figureOutTodosToDisplay();*/
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
     }
 
    // Private Method  
    // HTTP GET- get all candidates collection
    // Call: http://localhost:8080/getallcandidates
    function _refreshCandidateData() {
        $scope.begin=$scope.currentPage;
    	$scope.end=$scope.itemsPerPage;
    	$scope.begin = (($scope.currentPage - 1) * $scope.itemsPerPage);
		 $scope.end = $scope.begin + $scope.itemsPerPage;
        $http({
            method: 'GET',
            url: '/getcandidate/'+$scope.begin+"/"+$scope.end
        }).then(
            function(res) { // success
                $scope.candidates = res.data;
                console.log(candidates);
               
                _getRowsCount();
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }
    /*function _getRowsCount() {    	
        $http({
            method: 'GET',
            url: '/getcandidateCount'
        }).then(
            function(res) { // success
                $scope.totalRowsCount = res.data;
                console.log($scope.totalRowsCount);
                $scope.figureOutTodosToDisplay();
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }
 
    // Private Method  
    // HTTP GET- get all employees collection
    // Call: http://localhost:8080/employees
    function _refreshCandidateData() {   
    	$scope.begin=$scope.currentPage;
    	$scope.end=$scope.itemsPerPage;
    	 $scope.begin = (($scope.currentPage - 1) * $scope.itemsPerPage);
		 $scope.end = $scope.begin + $scope.itemsPerPage;
        $http({
            method: 'GET',
            url: '/getcandidate/'+$scope.begin+"/"+$scope.end
        }).then(
            function(res) { // success
                $scope.candidets = res.data;     
                console.log(candidates);
                $scope.figureOutTodosToDisplay();
                _getRowsCount();
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }
    */
    
    // getDisabledCandidates
    
    function getDisabledCandidates() {
        $http({
            method: 'GET',
            url: '/getDisabledCandidates'
        }).then(
            function(res2) { // success
                $scope.disabledCandidates = res2.data;
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }
    
    
    
 
    function _success(res) {
        _refreshCandidateData();
        getDisabledCandidates();
        _clearFormData();
    }
    
    
    function _success2(res2) {
    	getDisabledCandidates();
        _clearFormData();
    }
    
 
    function _error(res)     {
        var data = res.data;
        var status = res.status;
        var header = res.header;
        var config = res.config;
        alert("Error: " + status + ":" + data);
    }
 
    // Clear the form
    function _clearFormData() {
        $scope.candidateForm.candidateId = "";
        $scope.candidateForm.firstName = "";
        $scope.candidateForm.lastName = "";
        $scope.candidateForm.email = "";
        $scope.candidateForm.phone = "";
        $scope.candidateForm.dob = "";
        $scope.candidateForm.jobTitle="";
        $scope.candidateForm.activeStatus = "";
        $scope.candidateForm.city = "";
        $scope.candidateForm.state = "";
        $scope.candidateForm.country = "";
        $scope.candidateForm.createdDate="";
        $scope.candidateForm.createdBy = "";
        
        
    };
});