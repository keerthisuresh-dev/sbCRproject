package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Candidate;



@Repository
public class CandidateDAO {
	
	  static final Logger logger=LoggerFactory.getLogger(CandidateDAO.class);

	private static final String DRIVERCLASS="org.sqlite.JDBC";  
	  
	
	private static final String URL="jdbc:sqlite:C:/sqlite/gui/Test.db";
	
	private static final String INSERT="insert into candidate(id , fname ,lname ,email ,phone ,dob ,jobtitle ,city , state ,country ,pincode ,activestatus ,createddate ,createdby) values(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
	
	private static final String UPDATE="update candidate set  fname=? ,lname=? ,email=?,phone=?,dob=?,jobtitle=?,city=?,state=?,country=?,pincode=?,activestatus=?,updateddate=?,updatedby=? where Id=?";
	
	private static final String DELETE="delete from candidate where id=?";
	 
	


	private static final String GETBYID="select * from candidate where  activestatus=true and id=?";
	
	private static final String GETALL="select * from candidate where activestatus=true order by id asc LIMIT ?, ?";
	
	private static final String GETALLCount="select count(*) as rowsCount from candidate where activestatus=true order by id asc";
	
	private static final String GETDISABLEDCANDIDATES="select * from candidate where activestatus=false ";

	private static final String DISABLE="update candidate set activestatus=false where id=?";
	
	private static final String ENABLE="update candidate set activestatus=true where id=?";
	
	private static final String SEARCH="select * from candidate where id=?";
	
  	static Connection con;
	
  	
	
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



	// Retrieving Candidate by Status
	 
    public Candidate getCandidate(boolean activeStatus) throws Exception {
    	
    
    	logger.info("Entered Into 'getEmployee'");
    	
    	con=getConnection();
    	
    	Candidate cnd=new Candidate();
    	
    try {
    	
   	 PreparedStatement pst=con.prepareStatement(GETBYID);
   	ResultSet rs=pst.executeQuery();
		
		while(rs.next()) {
			
			cnd.setCandidateId(rs.getLong(1));
			cnd.setFirstName(rs.getString(2));
			cnd.setLastName(rs.getString(3));
			cnd.setEmail(rs.getString(4));
			cnd.setPhone(rs.getLong(5));
			cnd.setDob(rs.getString(6));
			cnd.setJobTitle(rs.getString(7));
			cnd.setCity(rs.getString(8));
			cnd.setState(rs.getString(9));
			cnd.setCountry(rs.getString(10));
			cnd.setPinCode(rs.getString(11));
			cnd.setCreatedDate(rs.getString(12));
			cnd.setCreatedBy(rs.getString(13));
			cnd.setUpdatedDate(rs.getString(14));
			cnd.setUpdatedBy(rs.getString(15));
			cnd.setActiveStatus(rs.getBoolean(16));
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
    
        return cnd;
    }
  
    
    
    // Adding New Candidate (or) Updating existed Candidate
    
    public Candidate  addCandidate(Candidate cnd) throws Exception {
    	logger.info("Entered Into 'addEmployee'");
    	System.out.println("Checking Status...");
    	
    	
       con=getConnection();
      
         try {
        	
        	 PreparedStatement pst1=con.prepareStatement(GETBYID);
        	 pst1.setLong(1, cnd.getCandidateId());
        	 ResultSet rs=pst1.executeQuery();
        	 
             if(rs.next()){
        		 
        		 logger.info("Entered to Update the Data");
        		 
        		 PreparedStatement pst=con.prepareStatement(UPDATE);
        		
               	 pst.setString(1, cnd.getFirstName());
               	 pst.setString(2, cnd.getLastName());
               	 pst.setString(3, cnd.getEmail());              	
               	 pst.setLong(4,   cnd.getPhone());
               	 pst.setString(5, cnd.getDob());
               	 pst.setString(6, cnd.getJobTitle());
               	 pst.setString(7, cnd.getCity());
               	 pst.setString(8, cnd.getState());
               	 pst.setString(9, cnd.getCountry());
               	 pst.setString(10, cnd.getPinCode());
               	 pst.setBoolean(11, cnd.isActiveStatus());
               	 pst.setString(12, cnd.getUpdatedDate());
               	 pst.setString(13, cnd.getUpdatedBy());
               	 pst.setLong  (14, cnd.getCandidateId()); 
        		 
        			pst.executeUpdate();
        			
        			logger.info("Data Updated Successfully");
        		 
        	 }
        	 else  {
        		 
        		 logger.info("Saving the New Candidate Data ");
        		 
        		 PreparedStatement pst2=con.prepareStatement(INSERT);
        		 
        		 
        		 
        		 pst2.setLong  (1, cnd.getCandidateId()); 
        		 pst2.setString(2, cnd.getFirstName());
        		 pst2.setString(3, cnd.getLastName());
        		 pst2.setString(4, cnd.getEmail());
        		 pst2.setLong(5,   cnd.getPhone());
        		 pst2.setString(6, cnd.getDob());
        		 pst2.setString(7, cnd.getJobTitle());
        		 pst2.setString(8, cnd.getCity());
        		 pst2.setString(9, cnd.getState());
        		 pst2.setString(10, cnd.getCountry());
        		 pst2.setString(11, cnd.getPinCode());
        		 pst2.setBoolean(12, cnd.isActiveStatus());
        		 pst2.setString(13, cnd.getCreatedDate());
        		 pst2.setString(14, cnd.getCreatedBy());
        		 
        		 pst2.executeUpdate();
        		 
        		 System.out.println("Data Inserted SuccussFully");
        		 
        		 pst2.close();
        	 }
        	
        	 pst1.close();
        	
         }
         
         catch(Exception e) {
        	 
            logger.error("Error in 'addEmployee'");
        	e.printStackTrace();
         }
         
         finally {
        	 con.close();
         }
		return cnd;
         
       }
   	
    
    // Update Candidate
    
    public void updateCandidate(Candidate cnd) throws Exception {
    	logger.info("Entered Into 'updateEmployee'");
        
      
    	con=getConnection();
        
        	try {
        		
    			
    			PreparedStatement pst=con.prepareStatement(UPDATE);
    			
           	 pst.setString(1, cnd.getFirstName());
           	 pst.setString(2, cnd.getLastName());
           	 pst.setString(3, cnd.getEmail());
           	 pst.setString(4, cnd.getEmail());
           	 pst.setLong(5,   cnd.getPhone());
           	 pst.setString(6, cnd.getDob());
           	 pst.setString(7, cnd.getJobTitle());
           	 pst.setString(8, cnd.getState());
           	 pst.setString(9, cnd.getCountry());
           	 pst.setString(10, cnd.getPinCode());
           	 pst.setBoolean(11, cnd.isActiveStatus());
           	 pst.setString(12, cnd.getCreatedDate());
           	 pst.setString(13, cnd.getCreatedBy());
    		 pst.setLong  (14, cnd.getCandidateId()); 

    			
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
    
    
    
    // Disable Candidate
    
    public void disableCandidate( long candidateId) throws Exception{
    	
    	logger.info("Entered Into 'disableCandidate'");
    	
    	con=getConnection();
    	
    	try {
    		
    		PreparedStatement pst=con.prepareStatement(DISABLE);
    		
    		pst.setLong(1, candidateId);
    		
    		pst.executeUpdate();
    		
    		logger.info("Candidate with Id "+candidateId +" is Disabled");
    	}
    	
    	catch(Exception e) {
    		
    		logger.error("Error In 'disableCandidate'");
    		
    		System.out.println(e.getMessage());
    		
    		e.printStackTrace();
    	}
    	
    	finally {
    		con.close();
    	}
    
    }
    
    
    
    // Enable Candidate 
    
  public void enableCandidate( long candidateId) throws Exception{
    	
    	logger.info("Entered Into 'enableCandidate()'");
    	
    	con=getConnection();
    	
    	try {
    		
    		PreparedStatement pst=con.prepareStatement(ENABLE);
    		
    		pst.setLong(1, candidateId);
    		
    		pst.executeUpdate();
    		
    		logger.info("Candidate with Id "+candidateId +" was Enabled");
    	}
    	
    	catch(Exception e) {
    		
    		logger.error("Error In 'enableCandidate()'");
    		
    		System.out.println(e.getMessage());
    		
    		e.printStackTrace();
    	}
    	
    	finally {
    		con.close();
    	}
    
    }
    
    
    
    // Delete Candidate 
  
    public void deleteCandidate(Long  candidateId) throws Exception {
    	logger.info("Entered Into 'deleteEmployee'");
    	
    	con=getConnection();
    try {
			
			
			PreparedStatement pst=con.prepareStatement(DELETE);
			 pst.setLong(1,candidateId) ;	
			pst.executeUpdate();
			
			logger.info("Candidate with "+candidateId+" was deleted successfully");
			
		}
		catch(Exception e) {
			
			System.out.println("Error in deleteCandidates");
			e.printStackTrace();
		}
    	
    finally {
    	con.close();
    }
       
    }
    
    
    // Get Disabled Candidates
    
    public List<Candidate> getDisabledCandidates() throws Exception {
    	logger.info("Entered Into 'getDisabledCandidates()'");
    	
    	con=getConnection();
    	
    	List<Candidate> list=new ArrayList<>();
    	
    	try {
			
 			PreparedStatement pst=con.prepareStatement(GETDISABLEDCANDIDATES);
 			ResultSet rs=pst.executeQuery();
 			
 			while(rs.next()) {
 				
 				Candidate cnd=new Candidate();
 				
 				cnd.setCandidateId(rs.getLong(1));
 				cnd.setFirstName(rs.getString(2));
 				cnd.setLastName(rs.getString(3));
 				cnd.setEmail(rs.getString(4));
 				cnd.setPhone(rs.getLong(5));
 				cnd.setDob(rs.getString(6));
 				cnd.setJobTitle(rs.getString(7));
 				cnd.setCity(rs.getString(8));
 				cnd.setState(rs.getString(9));
 				cnd.setCountry(rs.getString(10));
 				cnd.setPinCode(rs.getString(11));
 				cnd.setActiveStatus(rs.getBoolean(12));
 				cnd.setCreatedDate(rs.getString(13));
 				cnd.setCreatedBy(rs.getString(14));
 				cnd.setUpdatedDate(rs.getString(15));
 				cnd.setUpdatedBy(rs.getString(16));
 				
 				
 				list.add(cnd);
 				
 			}	
 	   	 pst.close();
 		}
 		catch(Exception e) {
 			
 			logger.error("ERROR In getDisabledCandidates()");
 			
 			e.printStackTrace();
 			
 		}
    	finally {
    		con.close();
    	}
    	
        return list;
    }


    
    
    
    
    // Get All Candidates
    
    public List<Candidate> getEnabledCandidates() throws Exception {
    	logger.info("Entered Into 'getAllCandidates'");
    	
    	con=getConnection();
    	
    	List<Candidate> list=new ArrayList<>();
    	
    	try {
			
 			PreparedStatement pst=con.prepareStatement(GETALL);
 			ResultSet rs=pst.executeQuery();
 			
 			while(rs.next()) {
 				
 				Candidate cnd=new Candidate();
 				
 				cnd.setCandidateId(rs.getLong(1));
 				cnd.setFirstName(rs.getString(2));
 				cnd.setLastName(rs.getString(3));
 				cnd.setEmail(rs.getString(4));
 				cnd.setPhone(rs.getLong(5));
 				cnd.setDob(rs.getString(6));
 				cnd.setJobTitle(rs.getString(7));
 				cnd.setCity(rs.getString(8));
 				cnd.setState(rs.getString(9));
 				cnd.setCountry(rs.getString(10));
 				cnd.setPinCode(rs.getString(11));
 				cnd.setActiveStatus(rs.getBoolean(12));
 				cnd.setCreatedDate(rs.getString(13));
 				cnd.setCreatedBy(rs.getString(14));
 				cnd.setUpdatedDate(rs.getString(15));
 				cnd.setUpdatedBy(rs.getString(16));
 				
 				
 				list.add(cnd);
 				
 			}	
 	   	 pst.close();
 		}
 		catch(Exception e) {
 			
           logger.error("ERROR In getEnabledCandidates()");
 			
 			e.printStackTrace();
 		}
    	
    	finally {
    		con.close();
    	}
    	
        return list;
    }

    public List<Candidate> getAllCandidates(int pageNo, int pageSize) throws Exception {
    	logger.info("Entered Into 'getAllcandidates'");
    	
    	con=getConnection();
    	
    	List<Candidate> list=new ArrayList<>();
    	
    	try {
			
 			PreparedStatement pst=con.prepareStatement(GETALL);
 			pst.setInt(1,pageNo);
 			pst.setInt(2,pageSize);
 			ResultSet rs=pst.executeQuery();
 			 			 			
 			while(rs.next()) {
 				
 				Candidate cnd=new Candidate();
 				
 				cnd.setCandidateId(rs.getLong(1));
 				cnd.setFirstName(rs.getString(2));
 				cnd.setLastName(rs.getString(3));
 				cnd.setEmail(rs.getString(4));
 				cnd.setPhone(rs.getLong(5));
 				cnd.setDob(rs.getString(6));
 				cnd.setJobTitle(rs.getString(7));
 				cnd.setCity(rs.getString(8));
 				cnd.setState(rs.getString(9));
 				cnd.setCountry(rs.getString(10));
 				cnd.setCreatedDate(rs.getString(11));
 				cnd.setCreatedBy(rs.getString(14));
 				list.add(cnd);
 				
 			}	
 	   	 pst.close();
 		}
 		catch(Exception e) {
 			
 		}
    	
        return list;
    }
    
    public static  Integer getAllCandidatesCount() throws Exception {
    	logger.info("Entered Into 'getAllEmployees'");
    	
    	con=getConnection();
    	
    	List<Candidate> list=new ArrayList<>();
    	int rowsCount=0;
    	try {
			
 			PreparedStatement pst=con.prepareStatement(GETALLCount); 			
 			ResultSet rs=pst.executeQuery(); 	
 			rowsCount = rs.getInt("rowsCount");  			 			
 				
 	   	 pst.close();
 		}
 		catch(Exception e) {
 			
 		}
    	
        return rowsCount;
    }
    public static List<Candidate> searchCandidate( int id)throws Exception {
		

    	logger.info("Entered Into 'getAllEmployees'");
    	
    	con=getConnection();
    	
    	List<Candidate> list=new ArrayList<>();
    	
    	try {
			
 			PreparedStatement pst=con.prepareStatement(SEARCH);
 			if(id>0)
 				pst.setLong(1,id);
 			ResultSet rs=pst.executeQuery();
 			
 			while(rs.next()) {
 				
 				Candidate cnd=new Candidate();
 				
 				cnd.setCandidateId(rs.getLong(1));
 				cnd.setFirstName(rs.getString(2));
 				cnd.setLastName(rs.getString(3));
 				cnd.setEmail(rs.getString(4));
 				cnd.setPhone(rs.getLong(5));
 				cnd.setDob(rs.getString(6));
 				cnd.setJobTitle(rs.getString(7));
 				cnd.setCity(rs.getString(8));
 				cnd.setState(rs.getString(9));
 				cnd.setCountry(rs.getString(10));
 				cnd.setCreatedDate(rs.getString(11));
 				cnd.setCreatedBy(rs.getString(14));
 				list.add(cnd);
 				
 			}	
 	   	 pst.close();
 		}
 		catch(Exception e) {
 			
 		}
    	
        return list;
    
	}

	public static void main(String[] args) throws Exception {
		/*
		 * List<Candidate> candidateList=null; try { candidateList = (List<Candidate>)
		 * searchCandidate(445); } catch (Exception e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } if(candidateList!=null &&
		 * candidateList.size()>0) {
		 * System.out.println("Result : "+candidateList.size()); }else {
		 * System.out.println("Result Not found "); }
		 */
		int rowsCount  = getAllCandidatesCount();
		System.out.println(rowsCount);
	}
    	  
    	 
   
    	
    }     
	





	



