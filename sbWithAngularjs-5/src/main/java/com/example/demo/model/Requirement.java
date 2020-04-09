package com.example.demo.model;

import java.util.Arrays;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class Requirement {
	 private long reqId;
	private String reqName;
	private String jobTitle;
	private String duration;
	private String payRate;
	private String billRate;
	private String billRateFrequency;
	private String startDate;
	private String workAutherization;
	private String priority;
	private String interviewMode;
	private String noOfPositions;
	private String address;
	private String city;
	private String country;
	private String date;
	private String zipCode;
	private String company;
	private String ContractType;
	private String shareRequirement;
		private String skills;
		private String degree;
		private String experience;
		private String requirementNote;
		private String publishJob;
		private String createdDate;
		

		private boolean status;
		private String clientDetails;
		private String fileName;
		private String fileType;
		
	
		
		private byte[] FileData;
		private boolean deleted;
		
		public Requirement() {
			super();
		}
		
			
		public Requirement(long reqId, String reqName, String jobTitle, String duration, String payRate,
				String billRate, String billRateFrequency, String startDate, String workAutherization, String priority,
				String interviewMode, String noOfPositions, String address, String city, String country, String date,
				String zipCode, String company, String contractType, String shareRequirement, String skills,
				String degree, String experience, String requirementNote, String publishJob, String createdDate,
				boolean status, String clientDetails, String fileName, String fileType, byte[] fileData,
				boolean deleted) {
			super();
			this.reqId = reqId;
			this.reqName = reqName;
			this.jobTitle = jobTitle;
			this.duration = duration;
			this.payRate = payRate;
			this.billRate = billRate;
			this.billRateFrequency = billRateFrequency;
			this.startDate = startDate;
			this.workAutherization = workAutherization;
			this.priority = priority;
			this.interviewMode = interviewMode;
			this.noOfPositions = noOfPositions;
			this.address = address;
			this.city = city;
			this.country = country;
			this.date = date;
			this.zipCode = zipCode;
			this.company = company;
			ContractType = contractType;
			this.shareRequirement = shareRequirement;
			this.skills = skills;
			this.degree = degree;
			this.experience = experience;
			this.requirementNote = requirementNote;
			this.publishJob = publishJob;
			this.createdDate = createdDate;
			this.status = status;
			this.clientDetails = clientDetails;
			this.fileName = fileName;
			this.fileType = fileType;
			FileData = fileData;
			this.deleted = deleted;
		}


		public long getReqId() {
			return reqId;
		}
		public void setReqId(long reqId) {
			this.reqId = reqId;
		}
		public String getReqName() {
			return reqName;
		}
		public void setReqName(String reqName) {
			this.reqName = reqName;
		}
		public String getJobTitle() {
			return jobTitle;
		}
		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public String getBillRate() {
			return billRate;
		}
		public void setBillRate(String billRate) {
			this.billRate = billRate;
		}
		public String getBillRateFrequency() {
			return billRateFrequency;
		}
		public void setBillRateFrequency(String billRateFrequency) {
			this.billRateFrequency = billRateFrequency;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getWorkAutherization() {
			return workAutherization;
		}
		public void setWorkAutherization(String workAutherization) {
			this.workAutherization = workAutherization;
		}
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}
		public String getInterviewMode() {
			return interviewMode;
		}
		public void setInterviewMode(String interviewMode) {
			this.interviewMode = interviewMode;
		}
		public String getNoOfPositions() {
			return noOfPositions;
		}
		public void setNoOfPositions(String noOfPositions) {
			this.noOfPositions = noOfPositions;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getZipCode() {
			return zipCode;
		}
		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getContractType() {
			return ContractType;
		}
		public void setContractType(String contractType) {
			ContractType = contractType;
		}
		
		
		public String getShareRequirement() {
			return shareRequirement;
		}
		public void setShareRequirement(String shareRequirement) {
			this.shareRequirement = shareRequirement;
		}
		public String getSkills() {
			return skills;
		}
		public void setSkills(String skills) {
			this.skills = skills;
		}
		public String getDegree() {
			return degree;
		}
		public void setDegree(String degree) {
			this.degree = degree;
		}
		public String getExperience() {
			return experience;
		}
		public void setExperience(String experience) {
			this.experience = experience;
		}
		public String getRequirementNote() {
			return requirementNote;
		}
		public void setRequirementNote(String requirementNote) {
			this.requirementNote = requirementNote;
		}
		public String getPublishJob() {
			return publishJob;
		}
		public void setPublishJob(String publishJob) {
			this.publishJob = publishJob;
		}
		public String getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public String getClientDetails() {
			return clientDetails;
		}
		public void setClientDetails(String clientDetails) {
			this.clientDetails = clientDetails;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFileType() {
			return fileType;
		}
		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
		
		public byte[] getFileData() {
			return FileData;
		}
		
		public void setFileData(byte[] fileData) {
			this.FileData = fileData;
		}
		public boolean isDeleted() {
			return deleted;
		}
		

		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}
		
		public String getPayRate() {
			return payRate;
		}
		public void setPayRate(String payRate) {
			this.payRate = payRate;
		}
		@Override
		public String toString() {
			return "Requirement [reqId=" + reqId + ", reqName=" + reqName + ", jobTitle=" + jobTitle + ", duration="
					+ duration + ", payRate=" + payRate + ", billRate=" + billRate + ", billRateFrequency="
					+ billRateFrequency + ", startDate=" + startDate + ", workAutherization=" + workAutherization
					+ ", priority=" + priority + ", interviewMode=" + interviewMode + ", noOfPositions=" + noOfPositions
					+ ", address=" + address + ", city=" + city + ", country=" + country + ", date=" + date
					+ ", zipCode=" + zipCode + ", company=" + company + ", ContractType=" + ContractType
					+ ", shareRequirement=" + shareRequirement + ", skills=" + skills + ", degree=" + degree
					+ ", experience=" + experience + ", requirementNote=" + requirementNote + ", publishJob="
					+ publishJob + ", createdDate=" + createdDate + ", status=" + status + ", clientDetails="
					+ clientDetails + ", fileName=" + fileName + ", fileType=" + fileType + ", FileData="
					+ Arrays.toString(FileData) + ", deleted=" + deleted + "]";
		}
		
		
		
	  
}
