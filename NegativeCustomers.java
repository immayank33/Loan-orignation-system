package com.brainmetnors.los.db;
import java.util.ArrayList;

import com.brainmetnors.los.customer.*;
abstract public class NegativeCustomers {
	
	static public  ArrayList<Customer> getNegativeCustomer(){
		
		ArrayList<Customer> negativeCustomerList=new ArrayList<Customer>();
		Customer c=new Customer();
		 PersonalDetail  personalDetail=new  PersonalDetail();
		 personalDetail.setAdharNo("12345");
		 personalDetail.setEmail("priya@gmail.com");
		 personalDetail.setFirstName("priya");
		 personalDetail.setIncome(12000);
		 personalDetail.setPanNo("67890");
		 personalDetail.setPhone("12455-676576");
		 c.setPersonalDetail(personalDetail);
	
		 negativeCustomerList.add(c);
		 
		 Customer c2=new Customer();
		 PersonalDetail  personalDetail2=new  PersonalDetail();
		 personalDetail2.setAdharNo("12345");
		 personalDetail2.setEmail("priya@gmail.com");
		 personalDetail2.setFirstName("priya");
		 personalDetail2.setIncome(12000);
		 personalDetail2.setPanNo("67890");
		 personalDetail2.setPhone("12455-676576");
		 c2.setPersonalDetail(personalDetail);
		 
		 negativeCustomerList.add(c);
		 
		 return negativeCustomerList;
	}
	
}
