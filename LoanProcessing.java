package com.brainmetnors.los.processing;
import static com.brainmetnors.los.utils.Utility.scanner;
import static com.brainmetnors.los.customer.LoanConstants.*;
import static com.brainmetnors.los.utils.Utility.applicationId;
import static com.brainmetnors.los.db.NegativeCustomers.getNegativeCustomer;

import java.util.ArrayList;

import com.brainmetnors.los.customer.Address;
import com.brainmetnors.los.customer.Customer;
import com.brainmetnors.los.customer.LoanDetail;
import com.brainmetnors.los.customer.PersonalDetail;

import static com.brainmetnors.los.customer.stageConstants.*;
abstract public class LoanProcessing {

	static ArrayList<Customer> customersList=new ArrayList<Customer>();
	
	public static void getStage(int id){
		boolean flag=false;
		for(Customer customer :customersList){
			if(customer.getId()==id){
				moveToStage(customer);
				flag=true;
				break;
			}
		}
		if(flag==false)
		System.out.println("please crosscheck your application id....");
		
		return;
	}
	public static void moveToStage(Customer customer){
		char choice;
		int stage=customer.getStage();
		switch(stage){
		case    SOURCING:System.out.println("you are in stage :SOURCING");
						System.out.println("Do you want to move to next stage(y/n");
						choice=scanner.next().charAt(0);
						if(choice=='y')
						qde(customer);
						else{
							return;
						}
						stage=customer.getStage();	
		case	QDE:System.out.println("you are in stage :QDE");
						System.out.println("Do you want to move to next stage(y/n");
						choice=scanner.next().charAt(0);
						if(choice=='y')
						dedupe(customer);
						else{
							return;
						}
						stage=customer.getStage();
		case	DEDUPE:System.out.println("you are in stage :DEDUPE");
						System.out.println("Do you want to move to next stage(y/n");
						choice=scanner.next().charAt(0);
						if(choice=='y')
							scoring(customer);
						else{
							return;
						}
						stage=customer.getStage();
		case	SCORING:System.out.println("you are in stage :SCORING");
						System.out.println("Do you want to move to next stage(y/n");
						choice=scanner.next().charAt(0);
						if(choice=='y')
							approve(customer);
						else{
							return;
						}
						stage=customer.getStage();
		case	APPROVAL:System.out.println("you are in stage :APPROVAL");
					    	System.out.println("your appliaction is approved");
					    	break;
		case	REJECT:System.out.println("you are in stage :REJECT");
						    System.out.println("your appliaction is rejected");
		}
	
	}
	public static void qde(Customer customer){
		System.out.println("first name: "+customer.getPersonalDetail().getFirstName()
				+" last name: "+customer.getPersonalDetail().getLastName()
				+" phone: "+customer.getPersonalDetail().getPhone()
				+" loan amount: "+customer.getLoandetail().getAmount()
				+" loan type: "+customer.getLoandetail().getLoanType()
				+" loan duration: "+customer.getLoandetail().getDuration() );
	 	
		//qde
	
		System.out.println("enter income");
		double income=scanner.nextDouble();
		System.out.println("enter liability");
		double liability=scanner.nextDouble();
		
		System.out.println("enter adhar number");
		scanner.nextLine();
		String adharNo=scanner.nextLine();
		System.out.println("enter pan number");
		String panNo=scanner.nextLine();
		System.out.println("enter city");
	    String city=scanner.nextLine();
	    System.out.println("enter state");
		String state=scanner.nextLine();
		
		Address address =new Address();
		address.setCity(city);
		address.setState(state);
		
		customer.getPersonalDetail().setAdharNo(adharNo);
		customer.getPersonalDetail().setIncome(income);
		customer.getPersonalDetail().setLiability(liability);
		customer.getPersonalDetail().setPanNo(panNo);
		customer.setAddress(address);
		
		
		customer.setStage(QDE);
		
	}
	public static void dedupe(Customer customer){
		ArrayList<Customer> neagtiveCustomerList=getNegativeCustomer();
		int negativeScore=0;
		int maxnegative=0;
		for(Customer negCustomer:neagtiveCustomerList){
			negativeScore=0;
			
			if(customer.getPersonalDetail().getAdharNo()==negCustomer.getPersonalDetail().getAdharNo()){
				negativeScore+=2;
			}
			if(customer.getPersonalDetail().getPanNo()==negCustomer.getPersonalDetail().getPanNo()){
				negativeScore+=2;
			}
			if(customer.getPersonalDetail().getPhone()==negCustomer.getPersonalDetail().getPhone()){
				negativeScore+=2;
			}
			if(customer.getPersonalDetail().getEmail()==negCustomer.getPersonalDetail().getEmail()){
				negativeScore+=2;
			}
			if(customer.getPersonalDetail().getFirstName()==negCustomer.getPersonalDetail().getFirstName() && 
					customer.getPersonalDetail().getLastName()==negCustomer.getPersonalDetail().getLastName()	){
				negativeScore+=2;
			}
			
			if(maxnegative<negativeScore)
				maxnegative=negativeScore;
			
		}
		
		if(maxnegative >0 ){
			customer.setScore(maxnegative*(-1));
			customer.setStage(REJECT);
		}
		else{
			customer.setStage(DEDUPE);	
		}
		
	}
	public static void scoring(Customer customer){
		int score=0;
		if(customer.getPersonalDetail().getAge()<=35 && customer.getPersonalDetail().getAge()>21){
			score+=5;
		}
		if(customer.getPersonalDetail().getIncome()>200000){
			score+=5;
		}
		
		customer.setScore(score);
		customer.setStage(SCORING);
		
		
	}
	public static void approve(Customer customer){
		
		double loanApproved=customer.getLoandetail().getAmount()*customer.getScore()/10;
		
		
		
		System.out.println("hello "+customer.getPersonalDetail().getFirstName()+" "+customer.getPersonalDetail().getLastName()
				+" your loam amount is "+loanApproved);
		
		System.out.println("Do you want to continue with this amount ...(y/n)");
		char choice=scanner.next().charAt(0);
		
		if(choice=='y'){
			//emi
			boolean resultEmi=getEmi(customer);
			if(resultEmi==false){
				customer.setStage(REJECT);
				return;
			}
			else{
				System.out.println("your monthly installments : "+customer.getEmi());
			}
		}
		else{
			customer.setStage(REJECT);
			return;
		}
		customer.setStage(APPROVAL);
	}
	
	static boolean getEmi(Customer customer){
		
		double emi=0;
		
		if(customer.getLoandetail().getLoanType().equalsIgnoreCase(HOME_LOAN)){
			customer.setRoi(ROI_HOME_LOAN);
		}
		else if(customer.getLoandetail().getLoanType().equalsIgnoreCase(AUTO_LOAN)){
			customer.setRoi(ROI_AUTO_LOAN);
		}
		else if(customer.getLoandetail().getLoanType().equalsIgnoreCase(PERSONAL_LOAN)){
			customer.setRoi(ROI_PERSONAL_LOAN);
		}
		else{
			//customer.setStage(REJECT);
			return false;
		}
		double monthyPrincial=customer.getLoandetail().getAmount()/(customer.getLoandetail().getDuration()*12);
		
		
		customer.setEmi(monthyPrincial+monthyPrincial*customer.getRoi()/100);
		return true;
	}
	public static void sourcing(){
		
		Customer customer=new Customer();
		customer.setId(++applicationId);
		
		
		System.out.println("enter your first name");
		scanner.nextLine();
		String firstName=scanner.nextLine();
		System.out.println("enter your last name ");
		String lastName=scanner.nextLine();
		System.out.println("enter your phone number");
		String phone=scanner.nextLine();
		
		System.out.println("enter loan type (HL AL PL)");
		String loanType=scanner.nextLine();
 
		System.out.println("enter your age");
		int age=scanner.nextInt();
		
		System.out.println("enter your Loan amount");
		double amount=scanner.nextDouble();
		System.out.println("enter loan duration");
		int duration =scanner.nextInt();
		
		PersonalDetail personalDetail=new PersonalDetail();
		personalDetail.setFirstName(firstName);
		personalDetail.setLastName(lastName);
		personalDetail.setPhone(phone);
		personalDetail.setAge(age);
		LoanDetail loanDetail=new LoanDetail();
		loanDetail.setAmount(amount);
		loanDetail.setDuration(duration);
		loanDetail.setLoanType(loanType);
		
		
		customer.setLoandetail(loanDetail);
		customer.setPersonalDetail(personalDetail);
		
		customer.setStage(SOURCING);
		customersList.add(customer);
		System.out.println("your application ID is: "+customer.getId());
		
	}
	
	
}
