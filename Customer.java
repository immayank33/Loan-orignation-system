package com.brainmetnors.los.customer;

public class Customer {
	private int id;
	private int stage;
	private int score;
	private double emi;
	private int roi;
	
    private PersonalDetail personalDetail;
    private Address address;
    private LoanDetail loandetail;
    
    
	public double getEmi() {
		return emi;
	}
	public void setEmi(double emi) {
		this.emi = emi;
	}
	public int getRoi() {
		return roi;
	}
	public void setRoi(int roi) {
		this.roi = roi;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PersonalDetail getPersonalDetail() {
		return personalDetail;
	}
	public void setPersonalDetail(PersonalDetail personalDetail) {
		this.personalDetail = personalDetail;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public LoanDetail getLoandetail() {
		return loandetail;
	}
	public void setLoandetail(LoanDetail loandetail) {
		this.loandetail = loandetail;
	}
    
	
	
}
