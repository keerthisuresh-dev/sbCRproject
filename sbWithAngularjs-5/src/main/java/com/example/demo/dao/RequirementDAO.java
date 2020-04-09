package com.example.demo.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Requirement;
import com.itextpdf.text.Font;

@Repository
public class RequirementDAO {
	

      static final Logger logger=LoggerFactory.getLogger(RequirementDAO.class);

		private static final String DRIVERCLASS="org.sqlite.JDBC";  
		  
		public static Connection getConnection() {
			
			
			try {
				
				Class.forName(DRIVERCLASS);
			   	 con=DriverManager.getConnection(URL1);
				
			}
			catch(Exception e) {
				logger.error("Connection Failed");
			}
			return con;
			
		}
		/*reqId:"",
		reqName:"",
		jobTitle:"",
		duration:"",
		payRate:"",
		billRate:"",
		billRateFrequency:"",
		startDate:"",
		workAutherization:"",
		priority:"",
		interviewMode:"",
		noOfPositions:"",
		address:"",
		city:"",
		country:"",
		date:"",
		zipCode:"",
		company:"",
		ContractType:"",
		expensesPaid:","
		remoteJob:"",
		travelJob:"",
		shareRequirement:"",
		skills:"",
		 degree:"",
		 experience:"",
		 requirementNote:"",
		 publishJob:"",
		 createdDate:"",
		status:"",
		clientDetails:"",
		fileName:"", 
		fileType:"",
		FileData:"",
		deleted:""*/
		  


		private static final String URL1="jdbc:sqlite:C:/sqlite/gui/Demo.db";
		
		private static final String INSERT="insert into requirement( jobTitle,duration,payRate,billRate , billRateFrequency ,startDate,"
				+"workAutherization,priority,interviewMode,noOfPositions,status,address,city,country,zipCode,company,ContractType,skills,degree,experience,FileData,fileType,requirementNote) values( ?,?, ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,?, ?,?,?,?,?,?,?,?,?)";
		
		private static final String UPDATE="update requirement set jobTitle=?,duration=?,payRate=?,billRate=?,billRateFrequency=?,startDate=?,workAutherization=?,priority=?,interviewMode=?,noOfPositions=?,"
				+ "address=?,city=?,country=?,zipCode=?,company=?,ContractType=?,skills=?,degree=?,experience=? where reqId=?";
		
		 //private static final String DELETE="delete from candidate where id=?"; 
		
		private static final String DELETE="update requirement set deleted='T' where reqId=?";
		
		private static final String SEARCH="select * from requirement where reqId=?";
		
		private static final String GETBYID="select * from requirement where status=true and deleted='F' ";
		private static final String GETBYFILENAME="select jobTitle,country,reqId,billRate,ContractType,clientDetails,priority,CreatedDate,status,fileType,FileData from requirement where status=true and deleted='F' AND fileType=?";
		private static final String GETALL="select jobTitle,country,reqId,billRate,ContractType,clientDetails,priority,CreatedDate,status,fileType from requirement where status=true and deleted='F' order by reqId asc LIMIT ?, ?";
		
		private static final String GETALLCount="select count(*) as rowsCount from requirement where status=true and deleted='F' order by reqId asc";
		
	  	static Connection con;
	
		
		 
 public Requirement getRequirement(boolean Status) throws Exception {
	    	
	    
	    	logger.info("Entered Into 'getEmployee'");
	    	
	    	con=getConnection();
	    	
	    	Requirement req=new Requirement();
	    	
	    try {
	    	
	   	 PreparedStatement pst=con.prepareStatement(GETBYID);
	   	ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {
				
				req.setJobTitle(rs.getString(3));
				req.setCountry(rs.getString(15));
				req.setReqId(rs.getLong(1));
				req.setBillRate(rs.getString(6));
				req.setContractType(rs.getString(19));
				req.setRequirementNote(rs.getString(27));
				req.setPriority(rs.getString(10));
				req.setCreatedDate(rs.getString(29));
				req.setStatus(rs.getBoolean(31));
				req.setFileName(rs.getString(33));
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
	    
	        return req;
	    }
	  
	    
	    
	    
	    
	    public void  addReqWithFile(Requirement req,MultipartFile file) throws Exception {
	    	
	    	logger.info("Entered Into 'addEmployee'");
	    	
	       con=getConnection();
	      
	         try {
	        	
	        	
	        	 /*private static final String INSERT="insert into requirement( jobTitle,duraion,payRate,billRate , billRateFrequency ,startDate,"
	     				+"workAutherization,priority,interviewMode,noOfPositions,address,city,country,zipCode,company,ContractType,skills,degree,experience,"
	     				+ "fileName,fileType,FileData,deleted) values( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?,?,?,?,?,?,?,?,?,?,?)";*/
	     		
	        	 if(req!=null && req.getReqId()==0) {
	        		 PreparedStatement pst=con.prepareStatement(INSERT);
	 	        	 
		 	        	
		 				// pst.setLong  (1, req.getCandidateId()); 
		 	        	 pst.setString(1, req.getJobTitle());
		 	        	 pst.setString(2, req.getDuration());
		 	        	 pst.setString(3, req.getPayRate());
		 	        	 pst.setString(4,   req.getBillRate());
		 	        	 pst.setString(5, req.getBillRateFrequency());
		 	        	 pst.setString(6, req.getStartDate());
		 	        	 pst.setString(7, req.getWorkAutherization());
		 	        	 pst.setString(8, req.getPriority());
		 	        	 pst.setString(9, req.getInterviewMode());
		 	        	 pst.setString(10, req.getNoOfPositions());
		 	        	 pst.setBoolean(11, req.isStatus());
		 	        	 pst.setString(12, req.getAddress());
		 	        	 pst.setString(13, req.getCity());
		 	        	 pst.setString(14, req.getCountry()); 
		 	        	 pst.setString(15, req.getZipCode());
		 	        	 pst.setString(16, req.getCompany());
		 	        	 pst.setString(17, req.getContractType());
		 	        	 pst.setString(18, req.getSkills());
		 	        	 pst.setString(19, req.getDegree());  
		 	        	 pst.setString(20, req.getExperience());
		 	        	
		 	        	 pst.setBytes(21, file.getBytes());
		 	        	pst.setString(22, file.getOriginalFilename());
		 	        	
		 	        	pst.setString(23, req.getRequirementNote());
		 	        	
		 	        	
		 	        

	        	 pst.executeUpdate();
	        	 System.out.println("Data Inserted SuccussFully");
	        	 
	        	 pst.close();
	        	
	        	 }else {
	        		 updateRequirement(req);
	        	 }
		 
	         }catch (Exception e) {
				// TODO: handle exception
	        	 e.printStackTrace();
			}
	    }
	    
	    public Blob getBlobData(MultipartFile file) throws IOException, SQLException {
	        byte[] bytes = file.getBytes();
	        return new SerialBlob(bytes);
	    }
	         public void  addRequirement(Requirement req) throws Exception {
	 	    	
	 	    	logger.info("Entered Into 'addEmployee'");
	 	    	
	 	       con=getConnection();
	 	      
	 	         try {
	 	        	
	 	        	
	 	        	 /*private static final String INSERT="insert into requirement( jobTitle,duraion,payRate,billRate , billRateFrequency ,startDate,"
	 	     				+"workAutherization,priority,interviewMode,noOfPositions,address,city,country,zipCode,company,ContractType,skills,degree,experience,"
	 	     				+ "fileName,fileType,FileData,deleted) values( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?,?,?,?,?,?,?,?,?,?,?)";*/
	 	     		
	 	        	 if(req!=null && req.getReqId()==0) {
	 	        		 
	 	        	 
	 	        	 PreparedStatement pst=con.prepareStatement(INSERT);
	 	        	 
	 	        	
	 				// pst.setLong  (1, req.getCandidateId()); 
	 	        	 pst.setString(1, req.getJobTitle());
	 	        	 pst.setString(2, req.getDuration());
	 	        	 pst.setString(3, req.getPayRate());
	 	        	 pst.setString(4,   req.getBillRate());
	 	        	 pst.setString(5, req.getBillRateFrequency());
	 	        	 pst.setString(6, req.getStartDate());
	 	        	 pst.setString(7, req.getWorkAutherization());
	 	        	 pst.setString(8, req.getPriority());
	 	        	 pst.setString(9, req.getInterviewMode());
	 	        	 pst.setString(10, req.getNoOfPositions());
	 	        	 pst.setBoolean(11, req.isStatus());
	 	        	 pst.setString(12, req.getAddress());
	 	        	 pst.setString(13, req.getCity());
	 	        	 pst.setString(14, req.getCountry()); 
	 	        	 pst.setString(15, req.getZipCode());
	 	        	 pst.setString(16, req.getCompany());
	 	        	 pst.setString(17, req.getContractType());
	 	        	 
	 	        	 
	 	        	 
	 	        	 pst.setString(18, req.getSkills());
	 	        	 pst.setString(19, req.getDegree());  
	 	        	 pst.setString(20, req.getExperience());
	 	        	
	 	        	pst.setString(22, req.getRequirementNote());
	 	        	
	 	        	  
	 	        	 
	 	        	 
	 	        	 
	 	        	 pst.executeUpdate();
	 	        	 System.out.println("Data Inserted SuccussFully");
	 	        	 
	 	        	 pst.close();
	 	        	
	 	        	 }else {
	 	        		 updateRequirement(req);
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
	   	
	  	 
		
	   public void updateRequirement(Requirement req) throws Exception {
	    	logger.info("Entered Into 'updateEmployee'"+req.toString());
	        
	    	logger.info("Update Called for candidate ID : "+req.getReqId());
	    	con=getConnection();
	        
	        	try {
	        		
	       //fname=?,lname=?,email=?,phone=?,dob=?,jobtitle=?,city=?,state=?,country=?,createddate=?,createdby=? where id=?
	        		
	        			
	    			PreparedStatement pst=con.prepareStatement(UPDATE);
	    			
	    		 //pst.setLong  (1, req.getCandidateId()); 
	           	 pst.setString(1, req.getJobTitle());
	           	 pst.setString(2, req.getDuration());
	           	 pst.setString(3, req.getPayRate());           	 
	           	 pst.setString(4, req.getBillRateFrequency());
	           	 pst.setString(5, req.getStartDate());
	           	 pst.setString(6, req.getWorkAutherization());
	           	pst.setString(7, req.getPriority());
	           	pst.setString(8, req.getInterviewMode());
	           	pst.setString(9, req.getNoOfPositions());
	           	pst.setString(10, req.getAddress());
	           	pst.setString(11, req.getCity());
	           	 pst.setString(12, req.getCountry()); 
	           	pst.setString(13, req.getZipCode());
	           	pst.setString(14, req.getCompany());
	           	pst.setString(15, req.getContractType());
	           	pst.setString(16, req.getSkills());
	           	pst.setString(17, req.getDegree());
	           	pst.setString(18, req.getExperience());
	           	 pst.setLong(19,req.getReqId());
	           	 
	    			
	    			
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
	  
	    public void deleteRequirement(Long  reqId) throws Exception {
	    	logger.info("Entered Into 'deleteEmployee'");
	    	
	    	con=getConnection();
	    try {
				
				
				PreparedStatement pst=con.prepareStatement(DELETE);
				 pst.setLong(1,reqId) ;	
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
	    
	    /*public void searchRequirement(Long  reqId) throws Exception {
	    	logger.info("Entered Into 'searchEmployee'");
	    	
	    	con=getConnection();
	    try {
				
				
				PreparedStatement pst=con.prepareStatement(SEARCH);
				 pst.setLong(1,reqId) ;	
				pst.executeUpdate();
				
				System.out.println("Table searched successfully");
				
			}
			catch(Exception e) {
				
				System.out.println("Error in searchingValues");
				e.printStackTrace();
			}
	    	
	    finally {
	    	con.close();
	    }
	    	
	    }  */
	  
	    
	    
	  public List<Requirement> getAllRequirements(int pageNo, int pageSize) throws Exception {
	    	logger.info("Entered Into 'getAllEmployees'");
	    	
	    	con=getConnection();
	    	
	    	List<Requirement> list=new ArrayList<>();
	    	
	    	try {
				
	 			PreparedStatement pst=con.prepareStatement(GETALL);
	 			pst.setInt(1,pageNo);
	 			pst.setInt(2,pageSize);
	 			ResultSet rs=pst.executeQuery();
//"select jobTitle,country,reqId,billRate,ContractType,clientDetails,priority,CreatedDate,status,fileType from requirement where status=true and deleted='F' order by reqId asc LIMIT ?, ?";
	 			while(rs.next()) {
	 				
	 				Requirement req=new Requirement();
	 				
	 				req.setJobTitle(rs.getString(1));
					req.setCountry(rs.getString(2));
					req.setReqId(rs.getLong(3));
					req.setBillRate(rs.getString(4));
					req.setContractType(rs.getString(5));
					req.setClientDetails(rs.getString(6));
					req.setPriority(rs.getString(7));
					req.setCreatedDate(rs.getString(8));
					req.setStatus(rs.getBoolean(9));
					req.setFileType(rs.getString(10));
	 				
	 				
	 				
	 				list.add(req);
	 				
	 			}	
	 	   	 pst.close();
	 		}
	 		catch(Exception e) {
	 			
	 		}
	    	
	        return list;
	    }
	    
	    public static Integer getAllRequirementsCount() throws Exception {
	    	logger.info("Entered Into 'getAllEmployees'");
	    	
	    	con=getConnection();
	    	
	    	List<Requirement> list=new ArrayList<>();
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


	  //Excel View
		public ByteArrayInputStream buildExcelDocument(Map<String, Object> model,
	                Workbook workbook,
	                HttpServletRequest request,
	                HttpServletResponse response) throws Exception {

	// change the file name
	response.setHeader("Content-Disposition", "attachment; filename=\"requirement.xls\"");

	ByteArrayOutputStream out = new ByteArrayOutputStream();
	@SuppressWarnings("unchecked")
	List<Requirement> users = (List<Requirement>) model.get("users");

	// create excel xls sheet
	Sheet sheet = workbook.createSheet("User Detail");
	sheet.setDefaultColumnWidth(30);

	// create style for header cells
	CellStyle style = workbook.createCellStyle();
	org.apache.poi.ss.usermodel.Font font = workbook.createFont();
	font.setFontName("Arial");
			/*
			 * style.setFillForegroundColor(HSSFColor.BLUE);
			 * style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			 */
	font.setBold(true);
	//font.setColor(HSSFColor.WHITE.index);
	style.setFont(font);


	// create header row
	Row header = sheet.createRow(0);
	header.createCell(0).setCellValue("JobTitle");
	header.getCell(0).setCellStyle(style);
	header.createCell(1).setCellValue("Country");
	header.getCell(1).setCellStyle(style);
	header.createCell(2).setCellValue("Requirement Id");
	header.getCell(2).setCellStyle(style);
	header.createCell(3).setCellValue("Bill Rate");
	header.getCell(3).setCellStyle(style);
	header.createCell(4).setCellValue("Contract Type");
	header.getCell(4).setCellStyle(style);
	header.createCell(5).setCellValue("Client Details");
	header.getCell(5).setCellStyle(style);
	header.createCell(6).setCellValue("Priority");
	header.getCell(6).setCellStyle(style);
	header.createCell(7).setCellValue("Created Time");
	header.getCell(7).setCellStyle(style);
	header.createCell(8).setCellValue("Status");
	header.getCell(8).setCellStyle(style);
	header.createCell(9).setCellValue("Attachments");
	header.getCell(9).setCellStyle(style);


	int rowCount = 1;

	for(Requirement req : users){
	Row userRow =  sheet.createRow(rowCount++);
	userRow.createCell(0).setCellValue(req.getJobTitle());
	userRow.createCell(1).setCellValue(req.getCountry());
	userRow.createCell(2).setCellValue(req.getReqId());
	userRow.createCell(3).setCellValue(req.getBillRate());
	userRow.createCell(4).setCellValue(req.getContractType());
	userRow.createCell(5).setCellValue(req.getClientDetails());
	userRow.createCell(6).setCellValue(req.getPriority());
	userRow.createCell(7).setCellValue(req.getCreatedDate());
	userRow.createCell(8).setCellValue(req.isStatus());
	userRow.createCell(9).setCellValue(req.getFileName());

	}
	//OutputStream out = response.getOutputStream();
	workbook.write(response.getOutputStream());
	return new ByteArrayInputStream(out.toByteArray());

	}


		public static List<Requirement> searchRequirement( int reqId)throws Exception {
			

	    	logger.info("Entered Into 'getAllEmployees'");
	    	
	    	con=getConnection();
	    	
	    	List<Requirement> list=new ArrayList<>();
	    	
	    	try {
				
	 			PreparedStatement pst=con.prepareStatement(SEARCH);
	 			if(reqId>0)
	 				pst.setLong(1,reqId);
	 			ResultSet rs=pst.executeQuery();
	 			
	 			while(rs.next()) {
	 				
	 				Requirement req=new Requirement();
	 				
	 				req.setJobTitle(rs.getString(1));
					req.setCountry(rs.getString(2));
					req.setReqId(rs.getLong(3));
					req.setBillRate(rs.getString(4));
					req.setContractType(rs.getString(5));
					req.setClientDetails(rs.getString(6));
					req.setPriority(rs.getString(7));
					req.setCreatedDate(rs.getString(8));
					req.setStatus(rs.getBoolean(9));
	 				list.add(req);
	 				
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
		/*
		 * List<Requirement> reqList = getReqByFileName("monalisha_sbapp.pdf");
		 * if(reqList!=null && reqList.size()>0)
		 * System.out.println("File Size : "+reqList.size()); else
		 * System.out.println("File Not Found ...");
		 */
		}
		
		
		public static List<Requirement> listUsers() throws Exception {
			logger.info("Entered Into 'getAllEmployees'");
				    	
				    	con=getConnection();
				        List<Requirement> users = new ArrayList<>();
				        

				        //create dummy users
				        //users.add(new Requirement());
				        //users.add(new Requirement(2, "Jovan Srovoki", "jovan@srovoki.me", "Russia", 21));
				        //users.add(new Requirement(3, "Atta", "atta@gmail.com", "Pakistan", 29));
				        try {
					    	
				   	   	 PreparedStatement pst=con.prepareStatement(GETBYID);
				   	   	ResultSet rs=pst.executeQuery();
				   			
				   			while(rs.next()) {
				   				Requirement req=new Requirement();
				   				req.setJobTitle(rs.getString(3));
				   				req.setCountry(rs.getString(15));
				   				req.setReqId(rs.getLong(1));
				   				req.setCity(rs.getString(14));
				   				req.setBillRate(rs.getString(6));
				   				req.setContractType(rs.getString(19));
				   				req.setRequirementNote(rs.getString(27));
				   				req.setPriority(rs.getString(10));
				   				req.setCreatedDate(rs.getString(29));
				   				req.setStatus(rs.getBoolean(31));
				   				req.setFileName(rs.getString(33));	   				
				   				users.add(req);
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
				   	    
				   	     
				   	 
				        return users;
				    }

public static Requirement getReqByFileName(String fileName)throws Exception {
			

	    	logger.info("Entered Into 'getAllEmployees'");
	    	
	    	con=getConnection();
	    	
	    	List<Requirement> list=new ArrayList<>();
	    	
	    	try {
				
	 			PreparedStatement pst=con.prepareStatement(GETBYFILENAME);
	 			if(fileName!=null && fileName.length()>0)
	 				pst.setString(1,fileName);
	 			ResultSet rs=pst.executeQuery();
	 			
	 			while(rs.next()) {
	 				
	 				Requirement req=new Requirement();
	 					 				
	 				req.setJobTitle(rs.getString(1));
					req.setCountry(rs.getString(2));
					req.setReqId(rs.getLong(3));
					req.setBillRate(rs.getString(4));
					req.setContractType(rs.getString(5));
					req.setClientDetails(rs.getString(6));
					req.setPriority(rs.getString(7));
					req.setCreatedDate(rs.getString(8));
					req.setStatus(rs.getBoolean(9));
					req.setFileType(rs.getString(10));
					req.setFileData(rs.getBytes(11));
	 					 				
	 				list.add(req);
	 				
	 			}	
	 	   	 pst.close();
	 		}
	 		catch(Exception e) {
	 			
	 		}
	    	if(list!=null && list.size()>0)
	    		return list.get(0);
	    	return null;
	    
		}

public static List<Requirement> findAllUsers() throws Exception {
logger.info("Entered Into 'getAllEmployees'");
	    	
	    	con=getConnection();
	        List<Requirement> users = new ArrayList<>();
	        

	        //create dummy users
	        //users.add(new Requirement());
	        //users.add(new Requirement(2, "Jovan Srovoki", "jovan@srovoki.me", "Russia", 21));
	        //users.add(new Requirement(3, "Atta", "atta@gmail.com", "Pakistan", 29));
	        try {
		    	
	   	   	 PreparedStatement pst=con.prepareStatement(GETBYID);
	   	   	ResultSet rs=pst.executeQuery();
	   			
	   			while(rs.next()) {
	   				Requirement req=new Requirement();
	   				req.setJobTitle(rs.getString(3));
	   				req.setCountry(rs.getString(15));
	   				req.setReqId(rs.getLong(1));
	   				req.setCity(rs.getString(14));
	   				req.setBillRate(rs.getString(6));
	   				req.setContractType(rs.getString(19));
	   				req.setRequirementNote(rs.getString(27));
	   				req.setPriority(rs.getString(10));
	   				req.setCreatedDate(rs.getString(29));
	   				req.setStatus(rs.getBoolean(31));
	   				req.setFileName(rs.getString(33));	   	
	   				users.add(req);
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
			return users;
	   	    
	   	      }     
	   
	    	
	    }     
		





		





