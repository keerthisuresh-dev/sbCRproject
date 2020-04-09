package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CandidateDAO;
import com.example.demo.model.Candidate;


  
@RestController
public class MainRestController {
	
  static final Logger logger=LoggerFactory.getLogger(MainRestController.class);
	
	@Autowired
    private CandidateDAO candidateDAO;
 
    @RequestMapping(value = "/getcandidate/{pageNo}/{pageSize}",   method = RequestMethod.GET,    produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<Candidate> getAllCandidates(@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize) throws Exception {
    	logger.info("Entered Into 'getCadidtes List'");
        List<Candidate> list = candidateDAO.getAllCandidates(pageNo,pageSize);
        return list;
    }
   
    @RequestMapping(value = "/getcandidateCount",   method = RequestMethod.GET,    produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Integer getAllCandidatesCount() throws Exception {
    	logger.info("Entered Into 'getCadidtes List'");
        Integer rowsCount = CandidateDAO.getAllCandidatesCount();
        return rowsCount;
    }
    
    @RequestMapping(value = "/getEnabledCandidates",   method = RequestMethod.GET,    produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<Candidate> getAllCandidates() throws Exception {
    	logger.info("Entered Into Controller 'getallcandidates()'");
        List<Candidate> list = candidateDAO.getEnabledCandidates();
        return list;
    }
    
    //search candidate
    
 // search employee
    @RequestMapping(value = "/searchcandidate/{candidateId}",   method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<Candidate> searchCandidate(@PathVariable("candidate") int id) throws Exception {
    	
    	logger.info("Entered Into 'searchEmployee'");
    	List<Candidate> list=null;
        System.out.println("(Service Side) Searching employee with Id: " + id);
        if(id>0) {
        	list=CandidateDAO.searchCandidate(id);
        }else {
        	//list=employeeDAO.getAllCandidates();
        }
        
        return list;
    }
    
    
    // Get Enabled Candidates
    
    @RequestMapping(value = "/getDisabledCandidates",   method = RequestMethod.GET,    produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<Candidate> getDisabledCandidates() throws Exception {
    	
    	logger.info("Entered Into Controller 'getDisabledCandidates()'");
    	
        List<Candidate> list = candidateDAO.getDisabledCandidates();
        
        return list;
    }
  
    
    
    @RequestMapping(value = "/candidate/{candidateId}", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Candidate getCandidateById(@PathVariable("candidateId") boolean activeStatus) throws Exception {
    	logger.info("Entered Into Controller 'getCandidateById()'");
    	
        return candidateDAO.getCandidate(activeStatus);
    }
  
    
    
   //Save Employee  Method
    
    @RequestMapping(value = "/savecandidate",   method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,  MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Candidate addCandidate(@RequestBody Candidate cnd) throws Exception {
    	
    	logger.info("Entered Into Controller 'addCandidate()'");
  
        System.out.println("(Service Side) Creating candidate with candidateId: " + cnd.getCandidateId());
        
        
        cnd = candidateDAO.addCandidate(cnd);
			return  cnd; 
    }
    
 
    
    
    
    
    // Update Employee
    
    @RequestMapping(value = "/updatecandidate", method = RequestMethod.PUT,  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public void updateCandidate(@RequestBody Candidate cnd) throws Exception {
    	
    	logger.info("Entered Into Controller 'updateCandidate()'");
    	candidateDAO.updateCandidate(cnd);
        System.out.println("(Service Side) Editing candidate with candidateId: " + cnd.getCandidateId());
      
    }
    
    
    
    
    //Disabling Candidate
    
    @RequestMapping(value="/disableCandidate" ,method=RequestMethod.PUT , produces= {MediaType.APPLICATION_JSON_VALUE ,MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public void disableCandidate(@RequestParam Long candidateId) throws Exception{
    	logger.info("Entered into  Controller 'disableCandidate()'");
    	
    	candidateDAO.disableCandidate(candidateId);
    	
    }
    
    
    // Enable Candidate
    
    @ResponseBody
    public void enableCandidate(@RequestParam Long candidateId) throws Exception{
    	logger.info("Entered into Controller 'enableCandidate()'");
    	
    	candidateDAO.enableCandidate(candidateId);
    	
    }
    
    
    
    //deleting Candidate
   
    @RequestMapping(value = "/deletecandidate",  method = RequestMethod.DELETE,  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public void deleteCandidate(@RequestParam Long candidateId) throws Exception {
    	
    	logger.info("Entered Into Controller 'deleteCandidate()'");
    	
        System.out.println("(Service Side) Deleting candidate with Id: " + candidateId);
  
        candidateDAO.deleteCandidate(candidateId);
    }

}
