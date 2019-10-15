package com.amdocs.fresher_onboarding_automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadCsv 
{
	
	public static int readHeaderColumnNumber(String heading, XSSFSheet sheet) {
		int cellColumnNumber = -1, columnIndex=0;
		 while(columnIndex < sheet.getRow(0).getLastCellNum())
	        {
			 Cell head = sheet.getRow(0).getCell(columnIndex);
			 
	        	if(head.toString().equalsIgnoreCase(heading)) {
	        		cellColumnNumber = columnIndex; 
	        		break;
	        	}
	        		columnIndex++;
	        }
		 return cellColumnNumber;
	    	
	}
	
	public static void send(XSSFSheet sheet, ArrayList<String> recipient_list)
	{
		ArrayList<Integer> mailSentList = new ArrayList<Integer>();
		boolean hasSent = false;
		int row=1;
		int rowTotal = sheet.getLastRowNum();

        if ((rowTotal > 0) || (sheet.getPhysicalNumberOfRows() > 0)) {
            rowTotal++;
        }
      
        while(row<rowTotal) {
        	
        	if(sheet.getRow(row).getCell(readHeaderColumnNumber("Welcome-kit",sheet)).toString().equalsIgnoreCase("no")) {
        		recipient_list.add(sheet.getRow(row).getCell(readHeaderColumnNumber("Emp_Mail_ID",sheet)).toString());
        		mailSentList.add(row);
        		recipient_list.add(sheet.getRow(row).getCell(readHeaderColumnNumber("Personal Email_ID",sheet)).toString());
        	}
        	row++;
        }
        row =1;
        SendMail mail = new SendMail();
        hasSent = mail.send_welcome_mail(recipient_list);
        if(hasSent)
        {
        	for(int eachRow : mailSentList) {
            		if(sheet.getRow(eachRow).getCell(readHeaderColumnNumber("Welcome-kit",sheet)).toString().equalsIgnoreCase("no")) 
            			sheet.getRow(eachRow).getCell(readHeaderColumnNumber("Welcome-kit",sheet)).setCellValue("yes");
            		
            	System.out.println(sheet.getRow(eachRow).getCell(readHeaderColumnNumber("Welcome-kit",sheet)));
            	
            	}
            	
            
        }
        
        	
	}
    public static void main( String[] args )
    
    {	
    	ArrayList<String> recipient_list = new ArrayList<String>();
    	
    	try {
    	
	    	FileInputStream file = new FileInputStream(new File("Freshers.xlsx"));
	    	
	    	
	        //Create Workbook instance holding reference to .xlsx file
	        XSSFWorkbook workbook = new XSSFWorkbook(file);
	
	        //Get first/desired sheet from the workbook
	        int numberOfSheets = workbook.getNumberOfSheets();
	        
	        for(int eachSheet = 0; eachSheet < numberOfSheets; eachSheet++)
	        {
	        	XSSFSheet sheet = workbook.getSheetAt(eachSheet);
	        
	        	send(sheet, recipient_list);
	        }
	        file.close();
	        FileOutputStream outputStream = new FileOutputStream("Freshers.xlsx");
	        workbook.write(outputStream);
	        
	        outputStream.close();
    	}
    	
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}
