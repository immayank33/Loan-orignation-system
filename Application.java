package com.brainmetnors.los;
import static com.brainmetnors.los.utils.Utility.scanner;

import com.brainmetnors.los.processing.LoanProcessing;
public class Application {
	public static void main(String []args){
		//file read=>arraylist
		System.out.println("LOAN ORIGNATION SYSTEM");

		while(true){
			System.out.println("Type application number or 0(Initiate application process) -1 for exit");
			int applicationNumber=scanner.nextInt();
			
			if(applicationNumber ==0){
			//sourcing 
				 LoanProcessing.sourcing();
			}
			else if(applicationNumber ==-1)
			{
				//file data
				
				return;
			}
			else{
				LoanProcessing.getStage(applicationNumber);
			}
		}
		
	}
}
