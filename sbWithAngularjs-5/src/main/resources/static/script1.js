
  var app = angular.module('myApp', ['ui.bootstrap']);

  app.controller('ListController', function($scope){
  
  $scope.curPage = 1,
  $scope.itemsPerPage = 3,
  $scope.maxSize = 5;
    
    this.items = itemsDetails;
  
  
  $scope.numOfPages = function () {
    return Math.ceil(itemsDetails.length / $scope.itemsPerPage);
    
  };
  
    $scope.$watch('curPage + numPerPage', function() {
    var begin = (($scope.curPage - 1) * $scope.itemsPerPage),
    end = begin + $scope.itemsPerPage;
    
    $scope.filteredItems = itemsDetails.slice(begin, end);
  });
  });

  var itemsDetails = [
    { 
    	
    	        candidateId:'1',
    	        firstName: 'yyy',
    	        lastName: 'yyy' ,
    	        email:'yyy@gmail.com' 	,
    	        phone:'345678',
    	        dob:'89877'    ,
    	        jobTitle:'fghjb',
    	        
    	        city:'fghj',
    	        state:'cvbnm',
    	        country:'dfgh',
    	        createdBy:'cvbn',
    	        createdDate:'45678',
    	        activeStatus:'true',
    	        deleted:"F"
     
      },
    { 
    	 
    		        candidateId:'2',
    		        firstName: 'dfghjkl',
    		        lastName: 'fghj' ,
    		        email:'gh@gmail.com',
    		        phone:'98766789' ,
    		        dob:'56789'    ,
    		        jobTitle:'fghjk',
    		        
    		        city:'ghjk',
    		        state:'kjhgf',
    		        country:'yh',
    		        createdBy:'dfghj',
    		        createdDate:'456789',
    		        activeStatus:'false',
    		        deleted:'T'
     
      
      
    
      
     
      }
  ];
