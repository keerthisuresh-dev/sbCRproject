package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadController {
	
	public static String UploadDirectory=System.getProperty("user.dir")+"/uploads";
	 @RequestMapping("/")
	 public String UploadPage(Model model)
	 {
		 return "requirement";
	 }
	 @RequestMapping("/upload")
	 public String upload(Model model,@RequestParam("files") MultipartFile[] files)
	 {
		 StringBuilder filesNames=new StringBuilder();
		 for(MultipartFile file: files)
		 {
			 Path fileNameAndPath=Paths.get(UploadDirectory,file.getOriginalFilename());
			 filesNames.append(file.getOriginalFilename());
			 try
			 {
			 Files.write(fileNameAndPath, file.getBytes());
			 }
			 catch(IOException e)
			 {
				 e.printStackTrace();
			 }
		 }
		 model.addAttribute("msg","Successfully uploaded files " +filesNames.toString());
		 return "uploadstatusview";
	 }

}
