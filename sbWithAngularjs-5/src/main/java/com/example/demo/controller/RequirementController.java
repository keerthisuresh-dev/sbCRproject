package com.example.demo.controller;


import java.io.File;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//import com.example.demo.dao.ExcelView;
import com.example.demo.dao.RequirementDAO;
import com.example.demo.model.Requirement;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.opencsv.CSVWriter;
//import com.opencsv.bean.StatefulBeanToCsv;
//import com.opencsv.bean.StatefulBeanToCsvBuilder;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


@RestController
public class RequirementController {
	
	private RequirementDAO reqdao;

	public RequirementController(RequirementDAO reqdao) {
        this.reqdao = reqdao;
    }

   

		
	 static final Logger logger=LoggerFactory.getLogger(RequirementController.class);
		
		@Autowired
	    private RequirementDAO requirementDAO;
		
	   
	    @RequestMapping(value = "/getrequirement/{pageNo}/{pageSize}",   method = RequestMethod.GET,    produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public List<Requirement> getAllRequirements(@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize) throws Exception {
	    	logger.info("Entered Into 'getCadidtes List'");
	        List<Requirement> list = requirementDAO.getAllRequirements(pageNo,pageSize);
	        return list;
	    }
	    
	    @RequestMapping(value = "/getrequirementCount",   method = RequestMethod.GET,    produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public Integer getAllRequirementsCount() throws Exception {
	    	logger.info("Entered Into 'getCadidtes List'");
	        Integer rowsCount = requirementDAO.getAllRequirementsCount();
	        return rowsCount;
	    }
	    @RequestMapping(value = "/download/export-users",   method = RequestMethod.GET,    produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	   // @GetMapping("/export-users")
	    public void exportCSV(HttpServletResponse response) throws Exception {
	    	
	    	Calendar currDate = Calendar.getInstance();
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    	
	        //set file name and content type
	        String filename = "Requirement_Details_"+dateFormat.format(currDate.getTime())+".csv";

	        response.setContentType("text/csv");
	        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
	                "attachment; filename=\"" + filename + "\"");

	        //create a csv writer
		/*
		 * StatefulBeanToCsv<Requirement> writer = new
		 * StatefulBeanToCsvBuilder<Requirement>(response.getWriter())
		 * .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
		 * .withSeparator(CSVWriter.DEFAULT_SEPARATOR) .withOrderedResults(false)
		 * .build();
		 */
	        
	        StringBuffer sb = new StringBuffer();
			sb.append("Req Id");
			sb.append(",");
			sb.append("Job Title");
			sb.append(",");
			sb.append("Bill Rate");
			sb.append(",");
			sb.append("City");
			sb.append(",");
			sb.append("Country");		
			sb.append("\n");
			List<Requirement> reqList = RequirementDAO.listUsers();
			if(reqList!=null && reqList.size()>0) {
				for (Requirement req : reqList) {
					
					sb.append(req.getReqId());
					sb.append(",");
					
					sb.append(req.getJobTitle());
					sb.append(",");
					
					sb.append(req.getBillRate()==null?"":req.getBillRate());
					sb.append(",");
					
					sb.append(req.getCity()==null?"":req.getCity());
					sb.append(",");
					
					sb.append(req.getCountry()==null?"":req.getCountry());
					sb.append(",");
					
					sb.append("\n");				
				}
			}
			OutputStream out = response.getOutputStream();
			out.write(sb.toString().getBytes());
	        
	        //write all users to csv file
	        //writer.write(reqdao.listUsers());
	            
			
	    }
	
	   
	       
	    @RequestMapping(value = "/download/excel", method = RequestMethod.GET)
	    public void download(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    	Map<String, Object> model = new HashMap<String, Object>();
	    	model.put("users", RequirementDAO.findAllUsers());
	        //model.addAttribute("users", RequirementDAO.findAllUsers());
	        Workbook workbook = new HSSFWorkbook();	        
	        requirementDAO.buildExcelDocument(model, workbook, request,response);
	        
	    }
	    @RequestMapping(value = "/requirement/{reqId}", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public Requirement getrequirement(@PathVariable("reqId") boolean Status) throws Exception {
	    	logger.info("Entered Into 'getrequirementById'");
	    	
	        return requirementDAO.getRequirement(Status);
	    }
	  
	    
	    
	   //Save requirement  Method
	    
	    @RequestMapping(value = "/saverequirement",   method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public Requirement addRequirement(@RequestBody Requirement req) throws Exception {
	    	
	    	logger.info("Entered Into 'addrequirement'");
	  
	        System.out.println("(Service Side) Creating requirement with reqNo: " + req.getReqId());
	        
	        
	       // List<Requirement> list=getAllRequirements();
	        
	             requirementDAO.addRequirement(req);
				return  req; 
	    }
	   /* @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	    public @ResponseBody ResponseMetadata handleFileUpload(@RequestParam(value="file") MultipartFile file) throws IOException {
	        return documentService.save(file);
	    }

	    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	    public HttpEntity getDocument(@PathVariable Long id) {
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
	        return new ResponseEntity(documentService.getDocumentFile(id), httpHeaders, HttpStatus.OK);
	    }

	    @RequestMapping(method = RequestMethod.GET)
	    public @ResponseBody List getDocument() {
	        return documentService.findAll();
	    }*/
	    
	   @RequestMapping(value = "/uploadfile",   method = RequestMethod.POST)
	    @ResponseBody
	    public void uploadFile(@RequestParam(value="file") MultipartFile file,@RequestParam(value="user") String userForm) throws Exception {
		   
		   logger.info("Entered Into 'Upload File Ctrl'"+file.getContentType());
		   logger.info("Entered Into 'Upload File Ctrl'"+file.getSize());
		   System.out.println("userForm Data : "+userForm.toString());	
		   //requirementDAO.addRequirement(req,file);
		   	ObjectMapper objectMapper = new ObjectMapper();
		    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		   
		   Requirement reqObj = objectMapper.readValue(userForm,Requirement.class);
		   reqObj.setFileData(file.getBytes());
		   
		   if(reqObj!=null)
			   System.out.println("Form Data : "+reqObj.toString());
		   requirementDAO.addReqWithFile(reqObj,file);  
		   
		
		  /*String UPLOADED_FOLDER = "F://";
		  
		  try { if(file!=null)
		  System.out.println("Name"+file.getOriginalFilename()+"\t\t Size : "+file.
		  getBytes());
		 logger.info("Entered Into 'Upload File Ctrl'"+file.getContentType());
		 
		  System.out.println("(Service Side) Creating requirement with empNo: ");
		  
		  byte[] bytes = file.getBytes(); Path path = Paths.get(UPLOADED_FOLDER +
		  file.getOriginalFilename()); Files.write(path, bytes);
		
		 }catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
		 
	    	 
	    	
	        
	       // List<Requirement> list=getAllRequirements();
	        
	            // requirementDAO.addRequirement(req);
				 
	    }*/
	   }
	   @GetMapping("/download/{filename:.+}")
	    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
		   
	        String UPLOADED_FOLDER= "F://";
			File file = new File(UPLOADED_FOLDER + "//" + filename);
	        if (!file.exists()) {
	            throw new RuntimeException("File not found");
	        }
	        Resource resource = new UrlResource(file.toURI());
	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
	                .body(resource);
	    }
	   
	   @GetMapping("/download/{fileName:.+}/db")
	   public ResponseEntity downloadFromDB(@PathVariable String fileName) throws Exception {
	   	Requirement req = requirementDAO.getReqByFileName(fileName);
	   	return ResponseEntity.ok()
	   			.contentType(MediaType.parseMediaType("application/octet-stream"))
	   			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
	   			.body(req.getFileData());
	   }
	   
	   // search requirement
	    @RequestMapping(value = "/searchrequirement/{reqId}",   method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public List<Requirement> searchRequirement(@PathVariable("reqId") int reqId) throws Exception {
	    	
	    	logger.info("Entered Into 'searchrequirement'");
	    	List<Requirement> list=null;
	        System.out.println("(Service Side) Searching requirement with Id: " + reqId);
	        if(reqId>0) {
	        	list=requirementDAO.searchRequirement(reqId);
	        }else {
	        	//list=requirementDAO.getAllRequirements();
	        }
	        
	        return list;
	    }
	    
	   /* @RequestMapping(value = "/excelview",   method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public List<Requirement> excelView() throws Exception {
	    	
	    	logger.info("Entered Into 'excelview'");
	    	List<Requirement> list=null;
	        System.out.println("(Service Side) Searching requirement with Id: " );
	        excelview.buildExcelDocument(null, null, null, null);
	        /*if(reqId>0) {
	        	list=requirementDAO.searchRequirement(reqId);
	        }else {
	        	//list=requirementDAO.getAllRequirements();
	        }
	       <option value="1">US</option>
	        return list;
	    }
	   */
	    
	    
	    
	    // Update requirement
	    
	   @RequestMapping(value = "/requirement", method = RequestMethod.PUT,  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public void updateRequirement(@RequestBody Requirement cnd) throws Exception {
	    	System.out.println("Edit Requirement entered...");
	    	logger.info("Entered Into 'updaterequirement'");
	    	requirementDAO.updateRequirement(cnd);
	        System.out.println("(Service Side) Editing requirement with Id: " + cnd.getReqId());
	  
	    }
	  
	   
	    @RequestMapping(value = "/deleterequirement/{reqId}",  method = RequestMethod.DELETE,  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public void deleteRequirement(@PathVariable("reqId") Long reqId) throws Exception {
	    	logger.info("Entered Into 'deleterequirement'");
	        System.out.println("(Service Side) Deleting requirement with Id: " + reqId);
	  
	        requirementDAO.deleteRequirement(reqId);
	    }
	    
	    //; filename=file.pdf
	    @RequestMapping(value = "/api/reports/{fileName}", method = RequestMethod.GET)	    
	    public @ResponseBody byte[] getOpenedEventsInPdf(HttpServletResponse response, @PathVariable String fileName) throws Exception {
	        response.setHeader("Content-Disposition", "inline");
	        response.setContentType("application/pdf");
	        Requirement req = requirementDAO.getReqByFileName(fileName);
	    // get file in bytearray from my custom service in backend
	        byte[] file = req.getFileData();
	        return file;
	    }

	}



