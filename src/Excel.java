import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class Excel {
    private FileInputStream inFile;
    private FileOutputStream outFile;
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    
    Excel() throws Exception{
		inFile = new FileInputStream("Competitions Participations.xlsx");
		wb = WorkbookFactory.create(inFile);
    }
    
    
    //It will return the names of sheets as an array
    public String[] compNames() {   	
		int nbOfSheets = wb.getNumberOfSheets();
    	String[] names = new String[nbOfSheets];
    	for(int i = 0;i<nbOfSheets;i++) {
    		sh = wb.getSheetAt(i);
    		names[i] = sh.getSheetName();

    	}   	
    	return names;
    }
    
    //Returns number of sheets
    public int numOfComps() {
    	return wb.getNumberOfSheets();
    }
    
    //This method gives you an array of competition information, the first index represent the competition name,second> URL, third> Date
    public String[] compInfo(int compIndex) {
    	String[] info = new String[3];
    	sh = wb.getSheetAt(compIndex);
    	info[0] = String.valueOf(sh.getRow(0).getCell(1)); //Comp name
    	info[1] = String.valueOf(sh.getRow(1).getCell(1)); //Comp URL
    	info[2] = String.valueOf(sh.getRow(2).getCell(1)); //Comp Date
    	return info;
    }
    
    //==========================================================
    //To get a specific info
    public String compName(int compIndex) {
    	sh = wb.getSheetAt(compIndex);
    	return (String.valueOf(sh.getRow(0).getCell(1)));
    }   
    public String getSheetName(int compIndex) {
    	return wb.getSheetName(compIndex);
    }
    public int compIndex(String sheetName) {
    	sh = wb.getSheet(sheetName);
		int nbOfSheets = wb.getNumberOfSheets();
		for(int i=0;i<nbOfSheets;i++) {
			if(wb.getSheetAt(i) == wb.getSheet(sheetName))
				return i;
		}		
    	return -1; //if the sheetName not found
    } 
    public String compURL(int compIndex) {
    	sh = wb.getSheetAt(compIndex);
    	return (String.valueOf(sh.getRow(1).getCell(1)));
    }
    public String compDate(int compIndex) {
    	sh = wb.getSheetAt(compIndex);
    	return (String.valueOf(sh.getRow(2).getCell(1)));
    }
    //==========================================================
       
    //Here I check the cell that shows team name, if it appear true, else false
    public boolean isTeamBased(int compIndex) {
    	sh = wb.getSheetAt(compIndex);
    	if(sh.getRow(4).getCell(5) != null)
    		return true;
    	return false;
    }
    
    
    public boolean createComp(String sheetName,String compName,String URL,String date, boolean isTeamBased) throws Exception {
    	if(compIndex(sheetName) != -1) //if there is same sheet name
    		return false;
    	
    	sh = wb.createSheet(sheetName);
    	
        
        sh.setColumnWidth(0, 4500);
        sh.setColumnWidth(1, 4000);
        sh.setColumnWidth(2, 4500);
        sh.setColumnWidth(3, 4000);

        
		row = sh.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("Competition Name");		
		cell = row.createCell(1);
		cell.setCellValue(compName);
		
		row = sh.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("Competition Link");		
		cell = row.createCell(1);
		cell.setCellValue(URL);
		
		row = sh.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("competition date");	
		cell = row.createCell(1);
		cell.setCellValue(date);
		
		row = sh.createRow(4);
		cell = row.createCell(1);
		cell.setCellValue("Student ID");	
		cell = row.createCell(2);
		cell.setCellValue("Student Name");	
		cell = row.createCell(3);
		cell.setCellValue("Major");	
		if(isTeamBased) {
	        sh.setColumnWidth(4, 4000);
	        sh.setColumnWidth(5, 4000);
			cell = row.createCell(4);
			cell.setCellValue("Team");	
			cell = row.createCell(5);
			cell.setCellValue("Team Name");
			cell = row.createCell(6);
			cell.setCellValue("Rank");
		}else {
			cell = row.createCell(4);
			cell.setCellValue("Rank");
		}
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	
    	return true;
    }
    
    public boolean EditComp(int compIndex,String sheetName,String compName,String URL,String date, boolean isTeamBased) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();

    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(!wb.getSheetName(compIndex).equals(sheetName)) {//if the sheet name didn't change, ignore
    		if(compIndex(sheetName) != -1) //if there is same sheet name   			
    			return false;
    			
    	}
    		  	
    	sh = wb.getSheetAt(compIndex);
    	wb.setSheetName(compIndex, sheetName);
        sh.setColumnWidth(0, 4500);
        sh.setColumnWidth(1, 4000);
        sh.setColumnWidth(2, 4500);
        sh.setColumnWidth(3, 4000);
       
		row = sh.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("Competition Name");		
		cell = row.createCell(1);
		cell.setCellValue(compName);
		
		row = sh.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("Competition Link");		
		cell = row.createCell(1);
		cell.setCellValue(URL);
		
		row = sh.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("competition date");	
		cell = row.createCell(1);
		cell.setCellValue(date);
		
		if(isTeamBased(compIndex) != isTeamBased) { //Here to remove the competitors, if the type changed
			int end = sh.getLastRowNum();
			for(int i = 5; i <= end;i++) {
				sh.removeRow(sh.getRow(i));
			}
		}	
		
		row = sh.createRow(4);
		cell = row.createCell(1);
		cell.setCellValue("Student ID");	
		cell = row.createCell(2);
		cell.setCellValue("Student Name");	
		cell = row.createCell(3);
		cell.setCellValue("Major");	

		if(isTeamBased) {
	        sh.setColumnWidth(4, 4000);
	        sh.setColumnWidth(5, 4000);
			cell = row.createCell(4);
			cell.setCellValue("Team");	
			cell = row.createCell(5);
			cell.setCellValue("Team Name");
			cell = row.createCell(6);
			cell.setCellValue("Rank");
		}else {
			cell = row.createCell(4);
			cell.setCellValue("Rank");
		}
		

		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	
    	return true;
    }
    
    public boolean removeAllCompetitiors(int compIndex) throws Exception {
		int end = sh.getLastRowNum();
		sh = wb.getSheetAt(compIndex);
    	if(end == 4)
    		return false;
    	
		for(int i = 5; i <= end;i++) {
			sh.removeRow(sh.getRow(i));
		}		
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
		return true;
    }
    
    public boolean addStudent(int compIndex,int stID, String stName,String stMajor) throws Exception {
    	if(isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);
		row = sh.createRow(sh.getLastRowNum() + 1);
    	cell = row.createCell(0);
		cell.setCellValue(sh.getLastRowNum() - 4);
		
    	cell = row.createCell(1);
		cell.setCellValue(stID);

    	cell = row.createCell(2);
		cell.setCellValue(stName);
		
    	cell = row.createCell(3);
		cell.setCellValue(stMajor);
		
		
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
    private int howManyTeams(int compIndex) {
    	if(!isTeamBased(compIndex))
    		return 0;
    	int temp =0;
    	String teamName = null;
    	sh = wb.getSheetAt(compIndex);
    	for(int i = 5; i<=sh.getLastRowNum();i++) {
    		String nextTeam = String.valueOf(sh.getRow(i).getCell(5));
    		if(teamName != nextTeam) {
    			temp++;
    			teamName = nextTeam;
    		}
    	}
    	return temp;
    }
    
    public boolean addTeam(int compIndex,int[] stID,String[] stName,String[] stMajor,String teamName) throws Exception {
    	if(!isTeamBased(compIndex))
    		return false;
    	if(stID.length != stName.length || stID.length != stMajor.length) //if there are miss information
    		return false;
    	
    	int howManyTeams= howManyTeams(compIndex);
    	for(int i = 0;i<stName.length;i++) {
        	sh = wb.getSheetAt(compIndex);
    		row = sh.createRow(sh.getLastRowNum() + 1);
        	cell = row.createCell(0);
    		cell.setCellValue(sh.getLastRowNum() - 4);
    		
        	cell = row.createCell(1);
    		cell.setCellValue(stID[i]);

        	cell = row.createCell(2);
    		cell.setCellValue(stName[i]);
    		
        	cell = row.createCell(3);
    		cell.setCellValue(stMajor[i]);
    		
        	cell = row.createCell(4);
    		cell.setCellValue(howManyTeams + 1);
    		
        	cell = row.createCell(5);
    		cell.setCellValue(teamName);
    	}	
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
    public boolean deleteComp(int compIndex) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex >= nbOfSheets) //if there is same sheet name
    		return false;
    	wb.removeSheetAt(compIndex);		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
    
    //=========================================================
    //=========================================================
    //=========================================================
    //=========================================================
    //=========================================================

    //Ignore it, just for testing
	public static void main(String[] args) throws Exception {				
		Excel e = new Excel();		
		
	//	System.out.println(e.createComp("test1", "Emad Test", "www.google.com", "13/OCT/2021",true));
	//	System.out.println(e.createComp("test2", "Emad Test", "www.google.com", "13/OCT/2021",false));
	//	System.out.println(e.EditComp(1,"CyberHub", "emad albalawi", "www.google.com", "13/OCT/2021", false));
		//System.out.println(e.removeAllCompetitiors(1));
		
		String[] names = {"Emad Albalawi", "Khaled Alnobi","Saleh altalhi"};
		int[] id = {201960090,201943090,201844290};
		String[] majors = {"SWE", "CS","CE"};
	//	System.out.println(e.addTeam(0, id, names, majors, "Hunters"));
		System.out.println(e.addStudent(1, 201960090, "emad albalawi", "Hunters"));
	}
	
    
    
}
