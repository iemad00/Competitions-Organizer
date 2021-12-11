import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
    
    public boolean deleteAllCompetitiors(int compIndex) throws Exception {
    	sh = wb.getSheetAt(compIndex);
		int end = sh.getLastRowNum();
    	if(end == 4)
    		return false;
    	
		for(int i = 5; i <= end;i++) {
			try {
				sh.removeRow(sh.getRow(i));
				
			}catch(Exception e) {}
		}		
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
		return true;
    }
    
    public boolean addStudent(int compIndex,int stID, String stName,String stMajor) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);
		
		//To determine if there are any same id number before
		if(idInComp(compIndex,stID))
			return false;
		
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
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return -1;
    	
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
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(!isTeamBased(compIndex))
    		return false;
    	if(stID.length != stName.length || stID.length != stMajor.length) //if there are miss information
    		return false;
    	
		int end = sh.getLastRowNum();
    	sh = wb.getSheetAt(compIndex);
    	
    	//to determine if there same team name before
		for(int i = 5; i <= end;i++) {
			if(String.valueOf(sh.getRow(i).getCell(5)).equals(teamName))
				return false;
		}
		
		
    	int howManyTeams= howManyTeams(compIndex);
    	for(int i = 0;i<stName.length;i++) {
        	
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
    
    private boolean idInComp(int compIndex,int stID) {
    	sh = wb.getSheetAt(compIndex);
		int end = sh.getLastRowNum();
		
		//To determine if there are any same id number before
		for(int i = 5; i <= end;i++) { 
			try {
				if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == stID)
					return true;
			}catch(Exception e){}
		}
		
    	return false;
    }
    
    public boolean editStudent(int compIndex,int pastStID,int newStID, String stName,String stMajor) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);		
		//To determine if there are any same id number before
		if(!idInComp(compIndex,pastStID))
			return false;
		
		int end = sh.getLastRowNum();

		
		int rowNb = -1;
		for(int i = 5;i <= end;i++) {
			try {
				if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == pastStID)
					rowNb = Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(0))) + 4;
			}catch(Exception e) {}

		}


		
		if(pastStID!=newStID)
		for(int i = 5;i<=end;i++) {
			if(i != rowNb)
				try {
					if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == newStID)
						return false;
				}catch(Exception e) {}
		}
		
		row = sh.getRow(rowNb);
    	cell = row.createCell(0);
		cell.setCellValue(rowNb-4);
		
    	cell = row.createCell(1);
		cell.setCellValue(newStID);

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
    
    public boolean editStudentInTeam(int compIndex,int pastStID,int newStID, String stName,String stMajor) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(!isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);		
		//To determine if there are any same id number before
		if(!idInComp(compIndex,pastStID))
			return false;
		
		int end = sh.getLastRowNum();

		
		int rowNb = -1;
		for(int i = 5;i <= end;i++) {
			try {
				if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == pastStID)
					rowNb = Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(0))) + 4;
			}catch(Exception e) {}

		}

		if(pastStID!=newStID)
		for(int i = 5;i<=end;i++) {
			if(i != rowNb)
				try {
					if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == newStID)
						return false;
				}catch(Exception e) {}
		}
		
		row = sh.getRow(rowNb);
    	cell = row.createCell(0);
		cell.setCellValue(rowNb-4);
		
    	cell = row.createCell(1);
		cell.setCellValue(newStID);

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
    
    public boolean editTeamName(int compIndex,String oldName,String newName) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(!isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		
		if(!teamInComp(compIndex,oldName)) //if the team name not there
			return false;
	
		if(teamInComp(compIndex,newName)) //if the team name complicated
			return false;
		
		for(int i = 5; i <= end; i++) {
			if(String.valueOf(sh.getRow(i).getCell(5)).equals(oldName)) {
				sh.getRow(i).createCell(5).setCellValue(newName);
			}
		}
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;  	
    }
    
    public boolean deleteComp(int compIndex) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	wb.removeSheetAt(compIndex);		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
    public boolean rankStudent(int compIndex,int stID,int rank) throws IOException {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);		
		//if the id wrong
		if(!idInComp(compIndex,stID))
			return false;
    	
		int end = sh.getLastRowNum();		
		int rowNb = -1;
		for(int i = 5;i <= end;i++) {
			try {
				if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == stID)
					rowNb = Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(0))) + 4;
			}catch(Exception e) {}

		}
		
		row = sh.getRow(rowNb);
    	cell = row.createCell(4);
		cell.setCellValue(rank);
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
    private boolean teamInComp(int compIndex,String teamName) {
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		for(int i = 5; i <= end;i++) {
			if(String.valueOf(sh.getRow(i).getCell(5)).equals(teamName))
				return true;
		}
		return false;
    }
    
    public boolean rankTeam(int compIndex,String teamName,int rank) throws IOException {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(! isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();


		
		if(!teamInComp(compIndex,teamName)) //if the team name not there
			return false;
		
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }

}
