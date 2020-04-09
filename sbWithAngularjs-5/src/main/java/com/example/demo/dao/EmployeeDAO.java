package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;


@Repository
public class EmployeeDAO {
	
	  static final Logger logger=LoggerFactory.getLogger(EmployeeDAO.class);

	private static final String DRIVERCLASS="org.sqlite.JDBC";  
	  
	public static Connection getConnection() {
		
		
		try {
			
			Class.forName(DRIVERCLASS);
		   	 con=DriverManager.getConnection(URL);
			
		}
		catch(Exception e) {
			logger.error("Connection Failed");
		}
		return con;
		
	}



	private static final String URL="jdbc:sqlite:C:/sqlite/gui/swarmhr.db";
	
	private static final String INSERT="insert into employees(empid , empno , empname , empposition) values(? , ? , ? , ?)";
	
	private static final String UPDATE="update employees set empno=?, empname=? , empposition=? where empId=?";
	
	private static final String DELETE="delete from employees where empid=?";
	
	private static final String GETBYID="select * from employees where empno=? ";
	
	private static final String GETALL="select * from employees";
	
  	static Connection con;
	
	
	
	  
 /*   static {
        initEmps();
    }
  
    private static void initEmps() {
        Employee emp1 = new Employee(1L, "Emp1", "Employee1", "JavaDeveloper");
        Employee emp2 = new Employee(2L, "Emp2", "Employee2", "Jr.JavaDeveloper");
        Employee emp3 = new Employee(3L, "Emp3", "Employee3", "PythonDeveloper");
        Employee emp4 = new Employee(3L, "Emp4", "Employee4", "Jr.PythonDeveloper");

        empMap.put(emp1.getEmpId(), emp1);
        empMap.put(emp2.getEmpId(), emp2);
        empMap.put(emp3.getEmpId(), emp3);
        empMap.put(emp4.getEmpId(), emp4);

    }
  
    public Long getMaxEmpId() {
        Set<Long> keys = empMap.keySet();
        Long max = 0L;
        for (Long key : keys) {
            if (key > max) {
                max = key;
            }
        }
        return max;
    } */
	
	
	 
    public Employee getEmployee(String empNo) throws Exception {
    	
    
    	logger.info("Entered Into 'getEmployee'");
    	
    	con=getConnection();
    	
    	Employee emp=new Employee();
    	
    try {
    	
   	 PreparedStatement pst=con.prepareStatement(GETBYID);
   	ResultSet rs=pst.executeQuery();
		
		while(rs.next()) {
			
		    emp.setEmpId(rs.getLong(1));
			emp.setEmpName(rs.getString(2));
			emp.setPosition(rs.getString(3));
			emp.setEmpNo(rs.getString(4));
			
		}	
   	 pst.close();
 
    }
    
    catch(Exception e) {
   	 
       logger.error("Error in 'addEmployee'");
   	e.printStackTrace();
   	
    }
    finally {
    	con.close();
    }
    
        return emp;
    }
  
    
    
    
    
    public void  addEmployee(Employee emp) throws Exception {
    	
    	logger.info("Entered Into 'addEmployee'");
    	
       con=getConnection();
      
         try {
        	
        	if(emp.getEmpId() != null || emp.getEmpId() != 0) {
        	 
        	 PreparedStatement pst=con.prepareStatement(INSERT);
        	 
        	 
        	 pst.setLong  (1, emp.getEmpId()); 
        	 pst.setString(2, emp.getEmpNo());
        	 pst.setString(3, emp.getEmpName());
        	 pst.setString(4, emp.getPosition());
        	 
        	 pst.executeUpdate();
        	 System.out.println("Data Inserted SuccussFully");
        	 pst.close();
        	
        	}
        	
        	else if (emp.getEmpId() != null || emp.getEmpId() > 0){
        		
        		updateEmployee(emp);
        		
        	}
        			 
         }
         
         catch(Exception e) {
        	 
            logger.error("Error in 'addEmployee'");
        	e.printStackTrace();
         }
         
         finally {
        	 con.close();
         }
         
       }
   	
  	 
	
        
  
  
    
    public void updateEmployee(Employee emp1) throws Exception {
    	logger.info("Entered Into 'updateEmployee'");
        
      
    	con=getConnection();
        
        	try {
        		
    			
    			PreparedStatement pst=con.prepareStatement(UPDATE);
    			
    	     pst.setString(1, emp1.getEmpNo() );	
           	 pst.setString(2, emp1.getEmpName());
           	 pst.setString(3, emp1.getPosition());
             pst.setLong(4, emp1.getEmpId());
    			
    			
    			pst.executeUpdate();
    			
    			System.out.println("Table Updated successfully");
        		
        	}
        	catch(Exception e){
        		
        		logger.error("Error in 'updateEmployee'");
        		e.printStackTrace();
        	}
			
        	finally {
        		con.close();
        	}
        	
        
       
    }
  
    public void deleteEmployee(String  empId) throws Exception {
    	logger.info("Entered Into 'deleteEmployee'");
    	
    	con=getConnection();
    try {
			
			
			PreparedStatement pst=con.prepareStatement(DELETE);
			 pst.setString(1,empId) ;	
			pst.executeUpdate();
			
			System.out.println("Table deleted successfully");
			
		}
		catch(Exception e) {
			
			System.out.println("Error in deleteValues");
			e.printStackTrace();
		}
    	
    finally {
    	con.close();
    }
    	
        
    }
  
    
    
    public List<Employee> getAllEmployees() throws Exception {
    	logger.info("Entered Into 'getAllEmployees'");
    	
    	con=getConnection();
    	
    	List<Employee> list=new ArrayList<>();
    	
    	try {
			
 			PreparedStatement pst=con.prepareStatement(GETALL);
 			ResultSet rs=pst.executeQuery();
 			
 			while(rs.next()) {
 				
 				Employee emp=new Employee();
 				
 			    emp.setEmpId(rs.getLong(1));
 				emp.setEmpName(rs.getString(2));
 				emp.setPosition(rs.getString(3));
 				emp.setEmpNo(rs.getString(4));
 				list.add(emp);
 				
 			}	
 	   	 pst.close();
 		}
 		catch(Exception e) {
 			
 		}
    	
        return list;
    }


}
