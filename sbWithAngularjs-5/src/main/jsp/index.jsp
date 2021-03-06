<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html  xmlns:th="http://www.thymeleaf.org">

<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular.js"></script>
       <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" >
       
      <script th:src="@{/main.js}"></script>
      <link th:href="@{/main.css}" rel="stylesheet" />
</head>
<body style="background-color:lightgrey" ng-app="EmployeeManagement" ng-controller="EmployeeController"> <center><br>
      <h3 class="text-primary">
        <marquee behavior="alternate" direction="left">Spring Boot Crud Operation Application</marquee>
      </h3><br>
      <form  ng-submit="submitEmployee()">
         <table class="text-primary font-weight-bold" border="0">
           
              <tr class="text-primary">
               <td>Emp Id</td>
               <td><input type="text" ng-model="employeeForm.empId"></td>
            </tr>
            <tr>
               <td>Emp No</td>
               <td><input type="text" ng-model="employeeForm.empNo"  /></td>
            </tr>
            <tr>
               <td>Emp Name</td>
               <td><input type="text" ng-model="employeeForm.empName"  /></td>
            </tr>
            <tr>
             <td>Emp Position</td>
               <td><input type="text" ng-model="employeeForm.position"  /></td>
            </tr>
            <tr>
               <td >
                  <input type="submit" value="Submit" class="btn blue-button btn-primary" />
               </td>
               <td >
                  <input type="reset" value="Reset" class="btn  btn-danger" />
               </td>
            </tr>
         </table>
      </form>
      <br/>
      <a class="create-button" ng-click="createEmployee()">Create Employee</a>
      <table class=" table table-responsive-sm w-50 table-secondary">
         <tr class="bg-warning">
           <th>Emp Id </th>
            <th>Emp No</th>
            <th>Emp Name</th>
            <th>Emp Role </th>
            <th>Edit</th>
            <th>Delete</th>
         </tr>
         <!-- $scope.employees -->
         <tr class="bg-light text-primary " ng-repeat="employee in employees">
            <td> {{ employee.empId }}</td>
            <td> {{ employee.empNo }}</td>
            <td> {{ employee.empName }}</td>
            <td> {{employee.position}}</td>
            <td>
            <a  ng-click="editEmployee(employee)" class="btn edit-button btn-light font-weight-bold">Edit</a>
            </td>
            <td>
            <a  ng-click="deleteEmployee(employee)" class="btn delete-button btn-light font-weight-bold">Delete</a>
            </td>
         </tr>
      </table>
      </center>
   </body>
</html>